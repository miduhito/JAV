package DAO;

import DTO.CongThucDTO;
import DTO.ChiTietCongThucDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CongThucDAO {
    private Connection conn;

    public CongThucDAO() throws SQLException {
        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/fastfood?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", 
            "root", 
            "3182004Lam_"
        );
    }

    public List<CongThucDTO> getAllCongThuc() throws SQLException {
        List<CongThucDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM CongThuc";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CongThucDTO ct = new CongThucDTO();
                ct.setMaCongThuc(rs.getString("maCongThuc"));
                ct.setTenCongThuc(rs.getString("tenCongThuc"));
                ct.setMoTa(rs.getString("moTa"));
                list.add(ct);
            }
        }
        return list;
    }

    public String insertCongThuc(CongThucDTO ct, List<ChiTietCongThucDTO> chiTietList) {
        String sqlInsert = "INSERT INTO CongThuc (maCongThuc, tenCongThuc, moTa) VALUES (?, ?, ?)";
        String sqlCheck = "SELECT COUNT(*) FROM CongThuc WHERE maCongThuc = ?";

        try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
            stmtCheck.setString(1, ct.getMaCongThuc());
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return "Mã công thức " + ct.getMaCongThuc() + " đã tồn tại!";
                }
            }

            stmtInsert.setString(1, ct.getMaCongThuc());
            stmtInsert.setString(2, ct.getTenCongThuc());
            stmtInsert.setString(3, ct.getMoTa());
            stmtInsert.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        for (ChiTietCongThucDTO chiTiet : chiTietList) {
            chiTiet.setMaCongThuc(ct.getMaCongThuc());
            insertChiTietCongThuc(chiTiet);
        }
        return "";
    }

    public String updateCongThuc(CongThucDTO ct, List<ChiTietCongThucDTO> chiTietList) {
        String sqlUpdate = "UPDATE CongThuc SET tenCongThuc = ?, moTa = ? WHERE maCongThuc = ?";

        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
            stmtUpdate.setString(1, ct.getTenCongThuc());
            stmtUpdate.setString(2, ct.getMoTa());
            stmtUpdate.setString(3, ct.getMaCongThuc());
            int rowsAffected = stmtUpdate.executeUpdate();
            if (rowsAffected == 0) {
                return "Không tìm thấy công thức để cập nhật!";
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        deleteChiTietCongThuc(ct.getMaCongThuc());
        for (ChiTietCongThucDTO chiTiet : chiTietList) {
            chiTiet.setMaCongThuc(ct.getMaCongThuc());
            insertChiTietCongThuc(chiTiet);
        }
        return "";
    }

    public String deleteCongThuc(String maCongThuc) {
        // Kiểm tra xem công thức có nằm trong thức ăn nào không
        String sqlCheckThucAn = "SELECT COUNT(*) FROM ThucAn WHERE maCongThuc = ?";
        try (PreparedStatement stmtCheckThucAn = conn.prepareStatement(sqlCheckThucAn)) {
            stmtCheckThucAn.setString(1, maCongThuc);
            try (ResultSet rs = stmtCheckThucAn.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return "Không thể xóa công thức vì nó đang được sử dụng trong một thức ăn!";
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        // Xóa chi tiết công thức
        String sqlDeleteChiTiet = "DELETE FROM ChiTietCongThuc WHERE maCongThuc = ?";
        try (PreparedStatement stmtDeleteChiTiet = conn.prepareStatement(sqlDeleteChiTiet)) {
            stmtDeleteChiTiet.setString(1, maCongThuc);
            stmtDeleteChiTiet.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        // Xóa công thức
        String sqlDeleteCongThuc = "DELETE FROM CongThuc WHERE maCongThuc = ?";
        try (PreparedStatement stmtDeleteCongThuc = conn.prepareStatement(sqlDeleteCongThuc)) {
            stmtDeleteCongThuc.setString(1, maCongThuc);
            int rowsAffected = stmtDeleteCongThuc.executeUpdate();
            if (rowsAffected == 0) {
                return "Không tìm thấy công thức để xóa!";
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    // Các phương thức khác (getAllCongThuc, insertCongThuc, updateCongThuc, v.v.) nếu có
    

    public List<ChiTietCongThucDTO> getChiTietCongThuc(String maCongThuc) throws SQLException {
        List<ChiTietCongThucDTO> list = new ArrayList<>();
        String sql = "SELECT maNguyenLieu, soLuong, donViTinh " +
                    "FROM ChiTietCongThuc " +
                    "WHERE maCongThuc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maCongThuc);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietCongThucDTO ct = new ChiTietCongThucDTO();
                    ct.setMaCongThuc(maCongThuc);
                    ct.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    ct.setSoLuong(rs.getDouble("soLuong"));
                    ct.setDonViTinh(rs.getString("donViTinh"));
                    list.add(ct);
                }
            }
        }
        return list;
    }

    public void insertChiTietCongThuc(ChiTietCongThucDTO ct) {
        String sql = "INSERT INTO ChiTietCongThuc (maCongThuc, maNguyenLieu, soLuong, donViTinh) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ct.getMaCongThuc());
            stmt.setString(2, ct.getMaNguyenLieu());
            stmt.setDouble(3, ct.getSoLuong());
            stmt.setString(4, ct.getDonViTinh());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteChiTietCongThuc(String maCongThuc) {
        String sqlDelete = "DELETE FROM ChiTietCongThuc WHERE maCongThuc = ?";
        try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
            stmtDelete.setString(1, maCongThuc);
            stmtDelete.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String generateMaCongThuc() {
        String newMaCongThuc = "CT001"; // Giá trị mặc định nếu bảng trống
        try {
            String query = "SELECT maCongThuc FROM CongThuc ORDER BY maCongThuc DESC LIMIT 1"; // Lấy mã lớn nhất
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaCongThuc = rs.getString("maCongThuc");

                // Tách phần số từ mã
                String numberPart = lastMaCongThuc.substring(2); // Bỏ "CT", lấy phần số
                int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên

                // Tăng số lên 1
                number++;

                // Tạo mã mới với định dạng CTxxx (3 chữ số)
                newMaCongThuc = String.format("CT%03d", number);
            }

            rs.close();
            stmt.close();            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã công thức làm mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaCongThuc; // Trả về mã mới
    }
}