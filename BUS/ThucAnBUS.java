package BUS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.NguyenLieuDAO;
import DAO.ThucAnDAO;
import DTO.NhanVienDTO;
import DTO.ThucAnDTO;

public class ThucAnBUS {
    private ThucAnDAO thucAnDAO;
    private NguyenLieuDAO nguyenLieuDAO;

    public ThucAnBUS() {
        thucAnDAO = new ThucAnDAO();
        nguyenLieuDAO = new NguyenLieuDAO();
    }

    public List<ThucAnDTO> getAllThucAn() {
        return thucAnDAO.getAllThucAn();
    }

    public void addThucAn(ThucAnDTO ta) throws SQLException {
        // Thêm thức ăn
        thucAnDAO.insertThucAn(ta);

        // Trừ nguyên liệu dựa trên công thức và số lượng
        updateNguyenLieuTheoCongThuc(ta.getMaCongThuc(), ta.getSoLuong());
    }

    public void updateThucAn(ThucAnDTO ta) throws SQLException {
        thucAnDAO.updateThucAn(ta);
    }

    public void deleteThucAn(String maThucAn) throws SQLException {
        thucAnDAO.deleteThucAn(maThucAn);
    }

    public List<String> getAllCongThuc() throws SQLException {
        return thucAnDAO.getAllCongThuc();
    }

    public String generateMaThucAn() throws SQLException {
        return thucAnDAO.generateMaThucAn();
    }

    public void updateSoLuongThucAn(String maThucAn, int soLuong) {
        thucAnDAO.updateSoLuongThucAn(maThucAn, soLuong);
    }

    private void updateNguyenLieuTheoCongThuc(String maCongThuc, int soLuongThucAn) throws SQLException {
        String sqlSelect = "SELECT maNguyenLieu, soLuong FROM ChiTietCongThuc WHERE maCongThuc = ?";
        List<Map<String, Object>> nguyenLieuList = new ArrayList<>();

        System.out.println(maCongThuc);
        System.out.println(soLuongThucAn);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {
            stmtSelect.setString(1, maCongThuc);
            try (ResultSet rs = stmtSelect.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> nguyenLieu = new HashMap<>();
                    nguyenLieu.put("maNguyenLieu", rs.getString("maNguyenLieu"));
                    nguyenLieu.put("soLuong", rs.getDouble("soLuong"));
                    nguyenLieuList.add(nguyenLieu);
                }
            }
        }

        for (Map<String, Object> nguyenLieu : nguyenLieuList) {
            String maNguyenLieu = (String) nguyenLieu.get("maNguyenLieu");
            Double soLuongMotPhan = (Double) nguyenLieu.get("soLuong");
            System.out.println(maNguyenLieu);
            System.out.println(soLuongMotPhan);
            Double soLuongCanTru = soLuongMotPhan * soLuongThucAn;
            updateNguyenLieu(maNguyenLieu, soLuongCanTru);
        }
    }

    // Lấy thông tin thức ăn theo mã và trả về dưới dạng Map
    public ThucAnDTO getThucAnById(String maThucAn) {
        try {
            return thucAnDAO.getThucAnById(maThucAn);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin thức ăn!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ThucAnDTO();
        }
    }

    public void updateNguyenLieu(String maNguyenLieu, Double soLuongSuDung) throws SQLException {
        thucAnDAO.updateNguyenLieu(maNguyenLieu, soLuongSuDung);
    }

    public List<ThucAnDTO> findThucAn(String searchText) {
        try {
            List<ThucAnDTO> taSearch = new ArrayList<>();
            // Lặp qua tất cả thức ăn từ thucAnDAO
            for (ThucAnDTO ta : thucAnDAO.getAllThucAn()) {
                // Kiểm tra từng thuộc tính của ThucAnDTO
                if ((ta.getMaThucAn() != null && ta.getMaThucAn().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ta.getTenThucAn() != null && ta.getTenThucAn().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ta.getMoTa() != null && ta.getMoTa().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ta.getLoaiMonAn() != null && ta.getLoaiMonAn().toLowerCase().contains(searchText.toLowerCase())) ||
                    (String.valueOf(ta.getGia()) != null && String.valueOf(ta.getGia()).contains(searchText)) ||
                    (String.valueOf(ta.getSoLuong()) != null && String.valueOf(ta.getSoLuong()).contains(searchText)) ||
                    (ta.getAnhThucAn() != null && ta.getAnhThucAn().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ta.getMaCongThuc() != null && ta.getMaCongThuc().toLowerCase().contains(searchText.toLowerCase()))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    taSearch.add(ta);
                }
            }
            return taSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin thức ăn", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findThucAn(searchText).isEmpty()) {
                for(ThucAnDTO emp: findThucAn(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaThucAn(),
                        emp.getTenThucAn(),
                        emp.getMoTa(),
                        emp.getLoaiMonAn(),
                        emp.getGia(),
                        emp.getMaCongThuc(),
                        emp.getSoLuong()
                    });
                }
            }
            else {
                refreshTableData(table);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            refreshTableData(table);
        }
    }

    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<ThucAnDTO> thucAnList = getAllThucAn();
        for (ThucAnDTO ta : thucAnList) {
            tableModel.addRow(new Object[]{
                    ta.getMaThucAn(),
                    ta.getTenThucAn(),
                    ta.getMoTa(),
                    ta.getLoaiMonAn(),
                    ta.getGia(),
                    ta.getMaCongThuc(),
                    ta.getSoLuong()
            });
        }
    }

    public class DatabaseUtil {
        private static final String URL = "jdbc:mysql://localhost:3306/fastfood?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        private static final String USER = "root";
        private static final String PASSWORD = "3182004Lam_";
    
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
}
