package DAO;

import DTO.ThucAnDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import BUS.ThucAnBUS.DatabaseUtil;

public class ThucAnDAO {
    
    // Kết nối database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fastfood?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root", "3182004Lam_");
    }
    

    public List<ThucAnDTO> getAllThucAn() {
        List<ThucAnDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ThucAn";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ThucAnDTO ta = new ThucAnDTO();
                ta.setMaThucAn(rs.getString("maThucAn"));
                ta.setTenThucAn(rs.getString("tenThucAn"));
                ta.setMoTa(rs.getString("moTa"));
                ta.setLoaiMonAn(rs.getString("loaiMonAn"));
                ta.setGia(rs.getDouble("gia"));
                ta.setMaCongThuc(rs.getString("maCongThuc"));
                ta.setSoLuong(rs.getInt("soLuong"));
                ta.setAnhThucAn(rs.getString("anhThucAn"));
                list.add(ta);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void insertThucAn(ThucAnDTO ta) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM ThucAn WHERE maThucAn = ?";
        String sqlInsert = "INSERT INTO ThucAn (maThucAn, tenThucAn, moTa, loaiMonAn, gia, maCongThuc, soLuong, anhThucAn) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
            stmtCheck.setString(1, ta.getMaThucAn());
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.err.println("Mã thức ăn " + ta.getMaThucAn() + " đã tồn tại!");
                    return;
                }
            }

            stmtInsert.setString(1, ta.getMaThucAn());
            stmtInsert.setString(2, ta.getTenThucAn());
            stmtInsert.setString(3, ta.getMoTa());
            stmtInsert.setString(4, ta.getLoaiMonAn());
            stmtInsert.setDouble(5, ta.getGia());
            stmtInsert.setString(6, ta.getMaCongThuc());
            stmtInsert.setInt(7, ta.getSoLuong());
            stmtInsert.setString(8, ta.getAnhThucAn());
            stmtInsert.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm thức ăn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateThucAn(ThucAnDTO ta) throws SQLException {
        String sqlUpdate = "UPDATE ThucAn SET tenThucAn = ?, moTa = ?, loaiMonAn = ?, anhThucAn = ?, gia = ? WHERE maThucAn = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
            stmtUpdate.setString(1, ta.getTenThucAn());
            stmtUpdate.setString(2, ta.getMoTa());
            stmtUpdate.setString(3, ta.getLoaiMonAn());
            stmtUpdate.setString(4, ta.getAnhThucAn());
            stmtUpdate.setString(5, String.valueOf(ta.getGia()));
            stmtUpdate.setString(6, ta.getMaThucAn());
            int rowsAffected = stmtUpdate.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Không tìm thấy thức ăn để cập nhật!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thức ăn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteThucAn(String maThucAn) throws SQLException {
        String sqlCheck = "SELECT soLuong FROM ThucAn WHERE maThucAn = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, maThucAn);
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next()) {
                    int soLuong = rs.getInt("soLuong");
                    if (soLuong > 0) {
                        System.err.println("Không thể xóa thức ăn vì số lượng lớn hơn 0!");
                        return;
                    }
                } else {
                    System.err.println("Không tìm thấy thức ăn để xóa!");
                    return;
                }
            }

            String sqlDelete = "DELETE FROM ThucAn WHERE maThucAn = ?";
            try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
                stmtDelete.setString(1, maThucAn);
                int rowsAffected = stmtDelete.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("Không tìm thấy thức ăn để xóa!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa thức ăn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> getAllCongThuc() throws SQLException {
        List<String> congThucList = new ArrayList<>();
        String sql = "SELECT maCongThuc, tenCongThuc FROM CongThuc";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                congThucList.add(rs.getString("maCongThuc") + " - " + rs.getString("tenCongThuc"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn danh sách công thức: " + e.getMessage());
            e.printStackTrace();
        }
        return congThucList;
    }

    // Lấy thông tin thức ăn theo mã
    public ThucAnDTO getThucAnById(String maThucAn) throws SQLException {
        ThucAnDTO thucAn = null;
        String sqlSelect = "SELECT * FROM ThucAn WHERE maThucAn = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sqlSelect)) {
        pstmt.setString(1, maThucAn);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            thucAn = new ThucAnDTO();
            thucAn.setMaThucAn(maThucAn);
            thucAn.setTenThucAn(rs.getString("tenThucAn"));
            thucAn.setLoaiMonAn(rs.getString("loaiMonAn"));
            thucAn.setMoTa(rs.getString("moTa"));
            thucAn.setSoLuong(Integer.parseInt(rs.getString("soLuong")));
            thucAn.setGia(Double.parseDouble(rs.getString("gia")));
            thucAn.setMaCongThuc(rs.getString("maCongThuc"));
            thucAn.setAnhThucAn(rs.getString("anhThucAn"));
        }
        }
        return thucAn;
    }

    public String generateMaThucAn() {
        String newMaThucAn = "TA001"; // Giá trị mặc định nếu bảng trống
        try {
            String query = "SELECT maThucAn FROM ThucAn ORDER BY maThucAn DESC LIMIT 1"; // Lấy mã lớn nhất
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaThucAn = rs.getString("maThucAn");

                // Tách phần số từ mã
                String numberPart = lastMaThucAn.substring(2); // Bỏ "TA", lấy phần số
                int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên

                // Tăng số lên 1
                number++;

                // Tạo mã mới với định dạng TAxxx (3 chữ số)
                newMaThucAn = String.format("TA%03d", number);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã thức ăn mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaThucAn; // Trả về mã mới
    }

    public void updateNguyenLieu(String maNguyenLieu, Double soLuongSuDung) throws SQLException {
        String sqlCheck = "SELECT soLuong FROM nguyenlieu WHERE maNguyenLieu = ?";
        String sqlUpdate = "UPDATE nguyenlieu SET soLuong = soLuong - ? WHERE maNguyenLieu = ?";

        Double soLuongHienCo;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, maNguyenLieu);
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next()) {
                    soLuongHienCo = rs.getDouble("soLuong");
                    if (soLuongHienCo < soLuongSuDung) {
                        throw new SQLException("Không đủ số lượng nguyên liệu " + maNguyenLieu + "!");
                    }
                } else {
                    throw new SQLException("Không tìm thấy nguyên liệu " + maNguyenLieu + "!");
                }
            }
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
            stmtUpdate.setDouble(1, soLuongSuDung);
            stmtUpdate.setString(2, maNguyenLieu);
            stmtUpdate.executeUpdate();
        }
    }

    public void updateSoLuongThucAn(String maThucAn, int soLuong) {
        String sql = "UPDATE ThucAn SET soLuong = soLuong - ? WHERE maThucAn = ?";

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setDouble(1, soLuong);
            pstmt.setString(2, maThucAn);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Không tìm thấy thức ăn để cập nhật!");
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}