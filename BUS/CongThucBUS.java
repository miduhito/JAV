package BUS;

import DAO.CongThucDAO;
import DAO.NguyenLieuDAO;
import DTO.CongThucDTO;
import DTO.ChiTietCongThucDTO;
import DTO.NguyenLieuDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CongThucBUS {
    private CongThucDAO congThucDAO;
    private NguyenLieuDAO nguyenLieuDAO;

    public CongThucBUS() throws SQLException {
        congThucDAO = new CongThucDAO();
        nguyenLieuDAO = new NguyenLieuDAO();
    }

    public List<CongThucDTO> getAllCongThuc() throws SQLException {
        return congThucDAO.getAllCongThuc();
    }

    public List<NguyenLieuDTO> getAllNguyenLieu() throws SQLException {
        return nguyenLieuDAO.getAllNguyenLieu();
    }

    public String getDonViDo(String maNguyenLieu) throws SQLException {
        List<NguyenLieuDTO> list = nguyenLieuDAO.getAllNguyenLieu();
        for (NguyenLieuDTO nl : list) {
            if (nl.getMaNguyenLieu().equals(maNguyenLieu)) {
                return nl.getDonViDo();
            }
        }
        return "gram";
    }

    public void addCongThuc(CongThucDTO ct, List<ChiTietCongThucDTO> chiTietList) throws SQLException {
        congThucDAO.insertCongThuc(ct);
        for (ChiTietCongThucDTO chiTiet : chiTietList) {
            chiTiet.setMaCongThuc(ct.getMaCongThuc());
            congThucDAO.insertChiTietCongThuc(chiTiet);
        }
    }

    public void updateCongThuc(CongThucDTO ct, List<ChiTietCongThucDTO> chiTietList) throws SQLException {
        congThucDAO.updateCongThuc(ct);
        congThucDAO.deleteChiTietCongThuc(ct.getMaCongThuc());
        for (ChiTietCongThucDTO chiTiet : chiTietList) {
            chiTiet.setMaCongThuc(ct.getMaCongThuc());
            congThucDAO.insertChiTietCongThuc(chiTiet);
        }
    }

    public List<ChiTietCongThucDTO> getChiTietCongThuc(String maCongThuc) throws SQLException {
        return congThucDAO.getChiTietCongThuc(maCongThuc);
    }

    public void deleteCongThuc(String maCongThuc) throws SQLException {
        congThucDAO.deleteCongThuc(maCongThuc); // Sau đó xóa công thức
    }

    public String generateMaCongThuc() throws SQLException {
        return congThucDAO.generateMaCongThuc();
    }

    public List<CongThucDTO> findCongThuc(String searchText) {
        try {
            List<CongThucDTO> ctSearch = new ArrayList<>();
            // Lặp qua tất cả công thức từ congThucDAO
            for (CongThucDTO ct : congThucDAO.getAllCongThuc()) {
                // Kiểm tra từng thuộc tính của CongThucDTO
                if ((ct.getMaCongThuc() != null && ct.getMaCongThuc().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ct.getTenCongThuc() != null && ct.getTenCongThuc().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ct.getMoTa() != null && ct.getMoTa().toLowerCase().contains(searchText.toLowerCase()))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    ctSearch.add(ct);
                }
            }
            return ctSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin công thức", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findCongThuc(searchText).isEmpty()) {
                for(CongThucDTO emp: findCongThuc(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaCongThuc(),
                        emp.getTenCongThuc(),
                        emp.getMoTa()
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
        try {
            List<CongThucDTO> list = getAllCongThuc();
            for (CongThucDTO ct : list) {
                tableModel.addRow(new Object[]{
                        ct.getMaCongThuc(),
                        ct.getTenCongThuc(),
                        ct.getMoTa()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi làm mới bảng: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}