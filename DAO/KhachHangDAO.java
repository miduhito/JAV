package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DTO.KhachHangDTO;

public class KhachHangDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fastfood?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "3182004Lam_");
    }

    public List<KhachHangDTO> getAllKhachHang() {
        List<KhachHangDTO> khachHangList = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKH(rs.getString("maKhachHang"));
                khachHang.setTenKH(rs.getString("tenKhachHang"));
                khachHang.setGioiTinh(rs.getString("gioiTinh"));
                khachHang.setSDT(rs.getString("SDT"));
                khachHang.setEmail(rs.getString("email"));
                khachHang.setDiaChi(rs.getString("diaChi"));
                khachHang.setSoDiem(rs.getInt("soDiem"));

                khachHangList.add(khachHang);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return khachHangList;
    }

    public void insertKhachHang(String maKhachHang, String tenKhachHang, String gioiTinh, String SDT, String email, String diaChi, int soDiem) throws SQLException {
        String sqlInsert = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, gioiTinh, SDT, email, diaChi, soDiem) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, maKhachHang);
            pstmt.setString(2, tenKhachHang);
            pstmt.setString(3, gioiTinh);
            pstmt.setString(4, SDT);
            pstmt.setString(5, email);
            pstmt.setString(6, diaChi);
            pstmt.setInt(7, soDiem);

            pstmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateKhachHang(String maKhachHang, String tenKhachHang, String gioiTinh, String SDT, String email, String diaChi, int soDiem) throws SQLException{
        String sqlUpdate = "UPDATE KhachHang SET tenKhachHang = ?, gioiTinh = ?, SDT = ?, email = ?, diaChi = ?, soDiem = ? WHERE maKhachHang = ?";
        try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, tenKhachHang);
            pstmt.setString(2, gioiTinh);
            pstmt.setString(3, SDT);
            pstmt.setString(4, email);
            pstmt.setString(5, diaChi);
            pstmt.setInt(6, soDiem);
            pstmt.setString(7, maKhachHang);

            pstmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteKhachHang(String maKhachHang) throws SQLException{
        String sqlDelete = "DELETE FROM KhachHang WHERE maKhachHang = ?";
        try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setString(1, maKhachHang);

            pstmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public KhachHangDTO getKhachHangById(String maKhachHang) throws SQLException {
        KhachHangDTO khachHang = null;
        String sqlSelect = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setString(1, maKhachHang);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                khachHang = new KhachHangDTO();
                khachHang.setMaKH(rs.getString("maKhachHang"));
                khachHang.setTenKH(rs.getString("tenKhachHang"));
                khachHang.setGioiTinh(rs.getString("gioiTinh"));
                khachHang.setSDT(rs.getString("SDT"));
                khachHang.setEmail(rs.getString("email"));
                khachHang.setDiaChi(rs.getString("diaChi"));
                khachHang.setSoDiem(rs.getInt("soDiem"));
            }
        }
        return khachHang;
    }

    public String generateMaKhachHang() {
        String newMaKhachHang = "KH001"; // Giá trị mặc định nếu bảng trống
        try {
            String query = "SELECT maKhachHang FROM KhachHang ORDER BY maKhachHang DESC LIMIT 1"; // Lấy mã lớn nhất
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaKhachHang = rs.getString("maKhachHang");

                // Tách phần số từ mã
                String numberPart = lastMaKhachHang.substring(2); // Bỏ "CA", lấy phần số
                int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên

                // Tăng số lên 1
                number++;

                // Tạo mã mới với định dạng CAxxx (3 chữ số)
                newMaKhachHang = String.format("KH%03d", number);
            }

            rs.close();
            stmt.close();
                
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã khách hàng làm mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaKhachHang; // Trả về mã mới
    }
}
