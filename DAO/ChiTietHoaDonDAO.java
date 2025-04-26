package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.ChiTietHoaDonDTO;
import DTO.ThongKeDTO;

public class ChiTietHoaDonDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fastfood?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "3182004Lam_");
    }

    public List<ChiTietHoaDonDTO> getAllChiTietHoaDon() {
        List<ChiTietHoaDonDTO> chiTietHoaDonList = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietHoaDonDTO chiTietHoaDon = new ChiTietHoaDonDTO();
                chiTietHoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                chiTietHoaDon.setMaThucAn(rs.getString("maThucAn"));
                chiTietHoaDon.setSoLuongBan(rs.getInt("soLuongBan"));
                chiTietHoaDon.setThanhTien(rs.getDouble("thanhTien"));

                chiTietHoaDonList.add(chiTietHoaDon);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return chiTietHoaDonList;
    }

    public void insertChiTietHoaDon(String maHoaDon, String maThucAn, int soLuongBan, double thanhTien) {
        String sqlInsert = "INSERT INTO ChiTietHoaDon (maHoaDon, maThucAn, soLuongBan, thanhTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, maHoaDon);
            pstmt.setString(2, maThucAn);
            pstmt.setInt(3, soLuongBan);
            pstmt.setDouble(4, thanhTien);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateChiTietHoaDon(String maHoaDon, String maThucAn, int soLuongBan, double thanhTien) {
        String sqlUpdate = "UPDATE ChiTietHoaDon SET soLuongBan = ?, thanhTien = ? WHERE maHoaDon = ? AND maThucAn = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, soLuongBan);
            pstmt.setDouble(2, thanhTien);
            pstmt.setString(3, maHoaDon);
            pstmt.setString(4, maThucAn);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteChiTietHoaDon(String maHoaDon, String maThucAn) {
        String sqlDelete = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ? AND maThucAn = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setString(1, maHoaDon);
            pstmt.setString(2, maThucAn);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ChiTietHoaDonDTO getChiTietHoaDonById(String maHoaDon, String maThucAn) {
        ChiTietHoaDonDTO chiTietHoaDon = new ChiTietHoaDonDTO();
        String sqlSelect = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ? AND maThucAn = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setString(1, maHoaDon);
            pstmt.setString(2, maThucAn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                chiTietHoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                chiTietHoaDon.setMaThucAn(rs.getString("maThucAn"));
                chiTietHoaDon.setSoLuongBan(rs.getInt("soLuongBan"));
                chiTietHoaDon.setThanhTien(rs.getDouble("thanhTien"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return chiTietHoaDon;
    }

    // Lấy sản phẩm bán chạy theo khoảng thời gian
    public List<ThongKeDTO> getSanPhamBanChay(String startDate, String endDate) {
        List<ThongKeDTO> thongKeList = new ArrayList<>();
        String sql = "SELECT cthd.maThucAn, SUM(cthd.soLuongBan) AS tongSoLuong " +
                     "FROM ChiTietHoaDon cthd INNER JOIN HoaDon hd ON cthd.maHoaDon = hd.maHoaDon " +
                     "WHERE hd.ngayLap BETWEEN ? AND ? GROUP BY cthd.maThucAn ORDER BY tongSoLuong DESC LIMIT 10";
    
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    thongKeList.add(new ThongKeDTO(rs.getString("maThucAn"), rs.getInt("tongSoLuong")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return thongKeList;
    }
}