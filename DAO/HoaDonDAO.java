package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DTO.HoaDonDTO;
import DTO.ThongKeDTO;

public class HoaDonDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fastfood?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "3182004Lam_");
    }

    public List<HoaDonDTO> getAllHoaDon() {
        List<HoaDonDTO> hoaDonList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayLap(rs.getString("ngayLap"));
                hoaDon.setMaNhanVien(rs.getString("maNhanVien"));
                hoaDon.setMaKhachHang(rs.getString("maKhachHang"));
                hoaDon.setTongTien(rs.getDouble("tongTien"));
                hoaDon.setPTTT(rs.getString("pttt"));

                hoaDonList.add(hoaDon);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return hoaDonList;
    }

    public void insertHoaDon(String maHoaDon, String ngayLap, String maNhanVien, String sdtKhachHang, double tongTien, String pttt) {
        String sqlGetMaKhachHang = "SELECT maKhachHang FROM KhachHang WHERE sdt = ?";
        String sqlInsert = "INSERT INTO HoaDon (maHoaDon, ngayLap, maNhanVien, maKhachHang, tongTien, pttt) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmtGetMaKhachHang = conn.prepareStatement(sqlGetMaKhachHang);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
             
            // Lấy maKhachHang từ sdtKhachHang
            String maKhachHang = null; // Mặc định là NULL nếu không tìm thấy
            if (sdtKhachHang != null && !sdtKhachHang.trim().isEmpty()) {
                pstmtGetMaKhachHang.setString(1, sdtKhachHang);
                try (ResultSet rs = pstmtGetMaKhachHang.executeQuery()) {
                    if (rs.next()) {
                        maKhachHang = rs.getString("maKhachHang"); // Lấy maKhachHang nếu tồn tại
                    }
                }
            }
    
            // Thực hiện INSERT vào bảng HoaDon
            pstmtInsert.setString(1, maHoaDon);
            pstmtInsert.setString(2, ngayLap);
            pstmtInsert.setString(3, maNhanVien);
            pstmtInsert.setString(4, maKhachHang); // Có thể là NULL nếu không tìm thấy maKhachHang
            pstmtInsert.setDouble(5, tongTien);
            pstmtInsert.setString(6, pttt);
    
            pstmtInsert.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateHoaDon(String maHoaDon, String ngayLap, String maNhanVien, String sdtKhachHang, double tongTien, String pttt) {
        String sqlGetMaKhachHang = "SELECT maKhachHang FROM KhachHang WHERE sdt = ?";
        String sqlUpdate = "UPDATE HoaDon SET ngayLap = ?, maNhanVien = ?, maKhachHang = ?, tongTien = ?, pttt = ? WHERE maHoaDon = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmtGetMaKhachHang = conn.prepareStatement(sqlGetMaKhachHang);
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // Lấy maKhachHang từ sdtKhachHang
            String maKhachHang = null; // Mặc định là NULL nếu không tìm thấy
            if (sdtKhachHang != null && !sdtKhachHang.trim().isEmpty()) {
                pstmtGetMaKhachHang.setString(1, sdtKhachHang);
                try (ResultSet rs = pstmtGetMaKhachHang.executeQuery()) {
                    if (rs.next()) {
                        maKhachHang = rs.getString("maKhachHang"); // Lấy maKhachHang nếu tồn tại
                    }
                }
            }

            pstmt.setString(1, ngayLap);
            pstmt.setString(2, maNhanVien);
            pstmt.setString(3, maKhachHang);
            pstmt.setDouble(4, tongTien);
            pstmt.setString(5, pttt);
            pstmt.setString(6, maHoaDon);

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteHoaDon(String maHoaDon) {
        String sqlDeleteChiTiet = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ?";
        String sqlDeleteHoaDon = "DELETE FROM HoaDon WHERE maHoaDon = ?";
    
        try (Connection conn = getConnection();
             PreparedStatement pstmtChiTiet = conn.prepareStatement(sqlDeleteChiTiet);
             PreparedStatement pstmtHoaDon = conn.prepareStatement(sqlDeleteHoaDon)) {
    
            // Xóa các chi tiết của hóa đơn trước
            pstmtChiTiet.setString(1, maHoaDon);
            pstmtChiTiet.executeUpdate();
    
            // Xóa hóa đơn
            pstmtHoaDon.setString(1, maHoaDon);
            pstmtHoaDon.executeUpdate();
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public HoaDonDTO getHoaDonById(String maHoaDon) {
        HoaDonDTO hoaDon = new HoaDonDTO();
        String sqlSelect = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setString(1, maHoaDon);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayLap(rs.getString("ngayLap"));
                hoaDon.setMaNhanVien(rs.getString("maNhanVien"));
                hoaDon.setMaKhachHang(rs.getString("maKhachHang"));
                hoaDon.setTongTien(rs.getDouble("tongTien"));
                hoaDon.setPTTT(rs.getString("pttt"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return hoaDon;
    }

    // Hàm đếm số lượng hóa đơn trong bảng HoaDon
    public int getHoaDonCount() {
        int count = 0; // Biến lưu trữ số lượng hóa đơn
        String sql = "SELECT COUNT(*) AS count FROM HoaDon"; // Câu lệnh SQL đếm bản ghi

        try (Connection conn = getConnection(); // Mở kết nối
             Statement stmt = conn.createStatement(); // Tạo đối tượng Statement
             ResultSet rs = stmt.executeQuery(sql)) { // Thực thi truy vấn
            if (rs.next()) {
                count = rs.getInt("count"); // Lấy giá trị từ cột "count"
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi khi truy vấn cơ sở dữ liệu
        }

        return count; // Trả về số lượng hóa đơn
    }

    // Lấy danh sách mã nhân viên trong ca làm của ngày hôm nay có chức vụ là thanh toán
    public List<String> getNhanVien(String date, String time) {
        List<String> maNhanVienList = new ArrayList<>();
        String sql = "SELECT nv.maNhanVien " +
                    "FROM NhanVien nv " +
                    "INNER JOIN LichLamViec llv ON nv.maNhanVien = llv.maNhanVien " +
                    "INNER JOIN CaLam cl ON llv.maCaLam = cl.maCa " +
                    "WHERE nv.maChucVu = 'CV003' AND llv.ngayLamViec = ? AND cl.gioBD <= ? AND ? <= cl.gioKT";
        try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, time);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                maNhanVienList.add(rs.getString("maNhanVien"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Đã lấy ds nhân viên vô thanh toán");
        for(String mnv: maNhanVienList) {
            System.out.println(mnv);
        }
        return maNhanVienList;
    }

    public List<String> getAllNhanVienInShift(String date, String time) {
        List<String> maNhanVienList = new ArrayList<>();
        String sql = "SELECT nv.maNhanVien " +
                    "FROM NhanVien nv " +
                    "INNER JOIN LichLamViec llv ON nv.maNhanVien = llv.maNhanVien " +
                    "INNER JOIN CaLam cl ON llv.maCaLam = cl.maCa " +
                    "WHERE llv.ngayLamViec = ? AND cl.gioBD <= ? AND ? <= cl.gioKT";
        try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, date);
            pstmt.setString(2, time);
            pstmt.setString(3, time);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                maNhanVienList.add(rs.getString("maNhanVien"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return maNhanVienList;
    }

    // Lấy doanh thu theo ngày
    public List<ThongKeDTO> getDoanhThuTheoKhoangThoiGian(String thoiGian, String startDate, String endDate) {
        List<ThongKeDTO> thongKeList = new ArrayList<>();
        String groupByClause = "DATE(ngayLap)";
    
        // Điều chỉnh Group By dựa trên loại thời gian
        switch (thoiGian) {
            case "Tuần":
                groupByClause = "WEEK(ngayLap)";
                break;
            case "Tháng":
                groupByClause = "MONTH(ngayLap)";
                break;
            case "Năm":
                groupByClause = "YEAR(ngayLap)";
                break;
        }
    
        String sql = "SELECT " + groupByClause + " AS label, SUM(tongTien) AS doanhThu " +
                     "FROM HoaDon WHERE ngayLap BETWEEN ? AND ? GROUP BY " + groupByClause + " ORDER BY label";
    
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    thongKeList.add(new ThongKeDTO(rs.getString("label"), rs.getDouble("doanhThu")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return thongKeList;
    }

    public List<ThongKeDTO> getHoaDonTheoKhoangThoiGian(String thoiGian, String startDate, String endDate) {
        List<ThongKeDTO> thongKeList = new ArrayList<>();
        String groupByClause = "DATE(ngayLap)";
    
        // Điều chỉnh Group By dựa trên loại thời gian
        switch (thoiGian) {
            case "Tuần":
                groupByClause = "WEEK(ngayLap)";
                break;
            case "Tháng":
                groupByClause = "MONTH(ngayLap)";
                break;
            case "Năm":
                groupByClause = "YEAR(ngayLap)";
                break;
        }
    
        String sql = "SELECT " + groupByClause + " AS label, COUNT(maHoaDon) AS soHoaDon " +
                     "FROM HoaDon WHERE ngayLap BETWEEN ? AND ? GROUP BY " + groupByClause + " ORDER BY label";
    
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    thongKeList.add(new ThongKeDTO(rs.getString("label"), rs.getInt("soHoaDon")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return thongKeList;
    }

    public List<ThongKeDTO> getKhachHangTheoKhoangThoiGian(String thoiGian, String startDate, String endDate) {
        List<ThongKeDTO> thongKeList = new ArrayList<>();
        String groupByClause = "DATE(ngayLap)";
    
        // Điều chỉnh Group By dựa trên loại thời gian
        switch (thoiGian) {
            case "Tuần":
                groupByClause = "WEEK(ngayLap)";
                break;
            case "Tháng":
                groupByClause = "MONTH(ngayLap)";
                break;
            case "Năm":
                groupByClause = "YEAR(ngayLap)";
                break;
        }
    
        String sql = "SELECT " + groupByClause + " AS label, " +
                     "COUNT(DISTINCT CASE WHEN maKhachHang IS NULL THEN maHoaDon ELSE maKhachHang END) AS soKhachHang " +
                     "FROM HoaDon WHERE ngayLap BETWEEN ? AND ? GROUP BY " + groupByClause + " ORDER BY label";
    
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    thongKeList.add(new ThongKeDTO(rs.getString("label"), rs.getInt("soKhachHang")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        return thongKeList;
    }

    // Lấy số lượng hóa đơn theo ngày
    public List<ThongKeDTO> getHoaDonTheoNgay(String startDate, String endDate) {
        List<ThongKeDTO> thongKeList = new ArrayList<>();
        String sql = "SELECT DATE(ngayLap) AS ngay, COUNT(maHoaDon) AS soHoaDon " +
                     "FROM HoaDon " +
                     "WHERE ngayLap BETWEEN ? AND ? " +
                     "GROUP BY DATE(ngayLap) " +
                     "ORDER BY ngay";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String label = rs.getString("ngay");
                    double value = rs.getInt("soHoaDon");
                    thongKeList.add(new ThongKeDTO(label, value));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return thongKeList;
    }

    // Lấy số lượng khách hàng theo ngày
    public List<ThongKeDTO> getKhachHangTheoNgay(String startDate, String endDate) {
        List<ThongKeDTO> thongKeList = new ArrayList<>();
        String sql = "SELECT DATE(ngayLap) AS ngay, " +
                     "COUNT(DISTINCT CASE WHEN maKhachHang IS NULL THEN maHoaDon ELSE maKhachHang END) AS soKhachHang " +
                     "FROM HoaDon " +
                     "WHERE ngayLap BETWEEN ? AND ? " +
                     "GROUP BY DATE(ngayLap) " +
                     "ORDER BY ngay";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String label = rs.getString("ngay");
                    double value = rs.getInt("soKhachHang");
                    thongKeList.add(new ThongKeDTO(label, value));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return thongKeList;
    }

    public String generateMaHoaDon() {
        String newMaHoaDon = "HD001"; // Giá trị mặc định nếu bảng trống
        try {
            String query = "SELECT maHoaDon FROM HoaDon ORDER BY maHoaDon DESC LIMIT 1"; // Lấy mã lớn nhất
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaHoaDon = rs.getString("maHoaDon");

                // Tách phần số từ mã
                String numberPart = lastMaHoaDon.substring(2); // Bỏ "HD", lấy phần số
                int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên

                // Tăng số lên 1
                number++;

                // Tạo mã mới với định dạng HDxxx (3 chữ số)
                newMaHoaDon = String.format("HD%03d", number);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã hóa đơn làm mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaHoaDon; // Trả về mã mới
    }
}