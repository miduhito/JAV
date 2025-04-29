package DAO;

import DTO.NguyenLieuDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class NguyenLieuDAO {
    private Connection conn;

    public NguyenLieuDAO() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fastfood?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", 
                "root", 
                "root"
            );
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<NguyenLieuDTO> getAllNguyenLieu() {
        List<NguyenLieuDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NguyenLieu";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NguyenLieuDTO nl = new NguyenLieuDTO();
                nl.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                nl.setTenNguyenLieu(rs.getString("tenNguyenLieu"));
                nl.setLoaiNguyenLieu(rs.getString("loaiNguyenLieu"));
                nl.setNgayNhap(rs.getString("ngayNhap"));
                nl.setNgayHetHan(rs.getString("ngayHetHan"));
                nl.setSoLuong(rs.getDouble("soLuong"));
                nl.setDonViDo(rs.getString("donViDo"));
                nl.setGia(rs.getDouble("gia"));
                list.add(nl);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public String insertNguyenLieu(NguyenLieuDTO nl) {
        String sqlInsert = "INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, loaiNguyenLieu, ngayNhap, ngayHetHan, soLuong, donViDo, gia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCheck = "SELECT COUNT(*) FROM NguyenLieu WHERE maNguyenLieu = ?";
    
        try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
            // Kiểm tra trùng mã nguyên liệu
            stmtCheck.setString(1, nl.getMaNguyenLieu());
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return "Mã nguyên liệu " + nl.getMaNguyenLieu() + " đã tồn tại!";
                }
            }
    
            // Thêm nguyên liệu
            stmtInsert.setString(1, nl.getMaNguyenLieu());
            stmtInsert.setString(2, nl.getTenNguyenLieu());
            stmtInsert.setString(3, nl.getLoaiNguyenLieu());
            stmtInsert.setString(4, nl.getNgayNhap());
            stmtInsert.setString(5, nl.getNgayHetHan());
            stmtInsert.setDouble(6, nl.getSoLuong());
            stmtInsert.setString(7, nl.getDonViDo());
            stmtInsert.setDouble(8, nl.getGia());
            stmtInsert.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public void updateNguyenLieu(NguyenLieuDTO nl) throws SQLException {
        String sqlUpdate = "UPDATE NguyenLieu SET tenNguyenLieu = ?, loaiNguyenLieu = ?, ngayNhap = ?, ngayHetHan = ?, donViDo = ?, gia = ? WHERE maNguyenLieu = ?";
    
        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
            stmtUpdate.setString(1, nl.getTenNguyenLieu());
            stmtUpdate.setString(2, nl.getLoaiNguyenLieu());
            stmtUpdate.setString(3, nl.getNgayNhap());
            stmtUpdate.setString(4, nl.getNgayHetHan());
            stmtUpdate.setString(5, nl.getDonViDo());
            stmtUpdate.setDouble(6, nl.getGia());
            stmtUpdate.setString(7, nl.getMaNguyenLieu());
            int rowsAffected = stmtUpdate.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy nguyên liệu để cập nhật!");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteNguyenLieu(String maNguyenLieu) throws SQLException {
        // Kiểm tra số lượng
        String sqlCheckSoLuong = "SELECT soLuong FROM NguyenLieu WHERE maNguyenLieu = ?";
        try (PreparedStatement stmtCheckSoLuong = conn.prepareStatement(sqlCheckSoLuong)) {
            stmtCheckSoLuong.setString(1, maNguyenLieu);
            try (ResultSet rs = stmtCheckSoLuong.executeQuery()) {
                if (rs.next()) {
                    int soLuong = rs.getInt("soLuong");
                    if (soLuong > 0) {
                        throw new SQLException("Không thể xóa nguyên liệu vì số lượng lớn hơn 0!");
                    }
                } else {
                    throw new SQLException("Không tìm thấy nguyên liệu để xóa!");
                }
            }
        }

        // Kiểm tra xem nguyên liệu có nằm trong công thức nào không
        // String sqlCheckCongThuc = "SELECT COUNT(*) FROM ChiTietCongThuc WHERE maNguyenLieu = ?";
        // try (PreparedStatement stmtCheckCongThuc = conn.prepareStatement(sqlCheckCongThuc)) {
        //     stmtCheckCongThuc.setString(1, maNguyenLieu);
        //     try (ResultSet rs = stmtCheckCongThuc.executeQuery()) {
        //         if (rs.next() && rs.getInt(1) > 0) {
        //             throw new SQLException("Không thể xóa nguyên liệu vì nó đang được sử dụng trong một công thức!");
        //         }
        //     }
        // }

        // Xóa nguyên liệu
        String sqlDelete = "DELETE FROM NguyenLieu WHERE maNguyenLieu = ?";
        try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
            stmtDelete.setString(1, maNguyenLieu);
            int rowsAffected = stmtDelete.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy nguyên liệu để xóa!");
            }
        }
    }

    // Lấy thông tin nguyên liệu theo mã
    public NguyenLieuDTO getNguyenLieuById(String maNguyenLieu) throws SQLException {
        NguyenLieuDTO nguyenLieu = null;
        String sqlSelect = "SELECT * FROM NguyenLieu WHERE maNguyenLieu = ?"; 
        try (PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
        pstmt.setString(1, maNguyenLieu);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            nguyenLieu = new NguyenLieuDTO();
            nguyenLieu.setMaNguyenLieu(maNguyenLieu);
            nguyenLieu.setTenNguyenLieu(rs.getString("tenNguyenLieu"));
            nguyenLieu.setLoaiNguyenLieu(rs.getString("loaiNguyenLieu"));
            nguyenLieu.setNgayNhap(rs.getString("ngayNhap"));
            nguyenLieu.setNgayHetHan(rs.getString("ngayHetHan"));
            nguyenLieu.setSoLuong(Double.parseDouble(rs.getString("soLuong")));
            nguyenLieu.setDonViDo(rs.getString("donViDo"));
            nguyenLieu.setGia(Double.parseDouble(rs.getString("gia")));
        }
        }
        return nguyenLieu;
    }

    public void updateAmount(String maNguyenLieu, Double soLuong) {
        String sql = "UPDATE NguyenLieu SET soLuong = soLuong + ? WHERE maNguyenLieu = ?";
        System.out.println("Đã cập nhật sl nguyên lịu");
        System.out.println(maNguyenLieu);
        System.out.println(soLuong);
        try (PreparedStatement stmtUpdate = conn.prepareStatement(sql)) {
    
            stmtUpdate.setDouble(1, soLuong);
            stmtUpdate.setString(2, maNguyenLieu);
            int rowsAffected = stmtUpdate.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy nguyên liệu để cập nhật!");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public String generateMaNguyenLieu() {
        String newMaNguyenLieu = "NL001"; // Giá trị mặc định nếu bảng trống
        try {
            String query = "SELECT maNguyenLieu FROM NguyenLieu ORDER BY maNguyenLieu DESC LIMIT 1"; // Lấy mã lớn nhất
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaNguyenLieu = rs.getString("maNguyenLieu");

                // Tách phần số từ mã
                String numberPart = lastMaNguyenLieu.substring(2); // Bỏ "NL", lấy phần số
                int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên

                // Tăng số lên 1
                number++;

                // Tạo mã mới với định dạng NLxxx (3 chữ số)
                newMaNguyenLieu = String.format("NL%03d", number);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã nguyên liệu mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaNguyenLieu; // Trả về mã mới
    }
}