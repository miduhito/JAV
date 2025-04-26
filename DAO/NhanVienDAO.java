package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import DTO.NhanVienDTO;

public class NhanVienDAO {

    // Kết nối database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fastfood?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root", "3182004Lam_");
    }

    // Lấy tất cả nhân viên
    public List<NhanVienDTO> getAllNhanVien() {
        List<NhanVienDTO> nhanVienList = new ArrayList<>();
        String sql = "SELECT nv.maNhanVien, nv.tenNhanVien, cv.tenChucVu, nv.trangThai, nv.SDT, nv.email, nv.ngaySinh, nv.gioiTinh, nv.diaChi, nv.tenDangNhap " +
                    "FROM NhanVien nv INNER JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu";
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVienDTO nhanVien = new NhanVienDTO();
                nhanVien.setMaNhanVien(rs.getString("maNhanVien"));
                nhanVien.setTenNhanVien(rs.getString("tenNhanVien"));
                nhanVien.setSDT(rs.getString("SDT"));
                nhanVien.setEmail(rs.getString("email"));
                nhanVien.setNgaySinh(rs.getString("ngaySinh"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setDiaChi(rs.getString("diaChi"));
                nhanVien.setTenChucVu(rs.getString("tenChucVu"));
                nhanVien.setTrangThai(rs.getString("trangThai"));
                nhanVien.setTenDangNhap(rs.getString("tenDangNhap"));
                nhanVienList.add(nhanVien);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nhanVienList;
    }

    // Lấy danh sách chức vụ
    public Map<String, String> getAllChucVu() {
        Map<String, String> chucVuMap = new HashMap<>();
        String sql = "SELECT maChucVu, tenChucVu FROM ChucVu";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                chucVuMap.put(rs.getString("tenChucVu"), rs.getString("maChucVu"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return chucVuMap;
    }

    // Thêm nhân viên
    public void insertNhanVien(String maNhanVien, String tenNhanVien, String sdt, String email, String ngaySinh,
                               String gioiTinh, String diaChi, String trangThai, String maChucVu) throws SQLException {
        String sqlInsert = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, SDT, email, ngaySinh, gioiTinh, diaChi, trangThai, maChucVu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            
            pstmt.setString(1, maNhanVien);
            pstmt.setString(2, tenNhanVien);
            pstmt.setString(3, sdt);
            pstmt.setString(4, email);
            pstmt.setString(5, ngaySinh);
            pstmt.setString(6, gioiTinh);
            pstmt.setString(7, diaChi);
            pstmt.setString(8, trangThai);
            pstmt.setString(9, maChucVu);
            pstmt.executeUpdate();
        }
    }

    // Cập nhật thông tin nhân viên
    public void updateNhanVien(String maNhanVien, String tenNhanVien, String SDT, String email, String ngaySinh,
                               String gioiTinh, String diaChi, String trangThai, String maChucVu) throws SQLException {
        String sqlUpdate = "UPDATE NhanVien SET tenNhanVien = ?, SDT = ?, email = ?, ngaySinh = ?, gioiTinh = ?, diaChi = ?, trangThai = ?, maChucVu = ? WHERE maNhanVien = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, tenNhanVien);
            pstmt.setString(2, SDT);
            pstmt.setString(3, email);
            pstmt.setString(4, ngaySinh);
            pstmt.setString(5, gioiTinh);
            pstmt.setString(6, diaChi);
            pstmt.setString(7, trangThai);
            pstmt.setString(8, maChucVu);
            pstmt.setString(9, maNhanVien);
            pstmt.executeUpdate();
        }
    }

    // Xóa nhân viên 
    public void deleteNhanVien(String maNhanVien) throws SQLException {
        String sqlCheckStatus = "SELECT trangThai FROM NhanVien WHERE maNhanVien = ?";
        String sqlDelete = "DELETE FROM NhanVien WHERE maNhanVien = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmtCheckStatus = conn.prepareStatement(sqlCheckStatus);
            PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {

            // Kiểm tra trạng thái của nhân viên
            pstmtCheckStatus.setString(1, maNhanVien);
            try (ResultSet rs = pstmtCheckStatus.executeQuery()) {
                if (rs.next()) {
                    String trangThai = rs.getString("trangThai");
                    if ("Đang làm việc".equals(trangThai)) {
                        System.out.println("Không thể xóa nhân viên đang làm việc.");
                        return; // Thoát phương thức nếu trạng thái là "Đang làm việc"
                    }
                } else {
                    System.out.println("Không tìm thấy nhân viên với mã nhân viên: " + maNhanVien);
                    return; // Thoát phương thức nếu không tìm thấy nhân viên
                }
            }

            // Thực hiện xóa nếu trạng thái phù hợp
            pstmtDelete.setString(1, maNhanVien);
            pstmtDelete.executeUpdate();
            System.out.println("Xóa nhân viên thành công.");
        }
    }

    // Lấy thông tin nhân viên theo mã
    public NhanVienDTO getNhanVienById(String maNhanVien) throws SQLException {
        NhanVienDTO nhanVien = null;
        String sqlSelect = "SELECT nv.maNhanVien, nv.tenNhanVien, cv.tenChucVu, " +
                   "nv.trangThai, nv.SDT, nv.email, nv.ngaySinh, nv.gioiTinh, nv.diaChi, nv.tenDangNhap " +
                   "FROM NhanVien nv " +
                   "INNER JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu " +
                   "WHERE nv.maNhanVien = ?";
        try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
        pstmt.setString(1, maNhanVien);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            nhanVien = new NhanVienDTO();
            nhanVien.setMaNhanVien(rs.getString("maNhanVien"));
            nhanVien.setTenNhanVien(rs.getString("tenNhanVien"));
            nhanVien.setSDT(rs.getString("SDT"));
            nhanVien.setEmail(rs.getString("email"));
            nhanVien.setNgaySinh(rs.getString("ngaySinh"));
            nhanVien.setGioiTinh(rs.getString("gioiTinh"));
            nhanVien.setDiaChi(rs.getString("diaChi"));
            nhanVien.setTrangThai(rs.getString("trangThai"));
            nhanVien.setTenChucVu(rs.getString("tenChucVu"));
            nhanVien.setTenDangNhap(rs.getString("tenDangNhap"));
        }
        }
        return nhanVien;
    }

    // Lấy tên chức vụ từ mã chức vụ
    public String getTenChucVu(String maChucVu) throws SQLException {
        String tenChucVu = "";
        String sql = "SELECT tenChucVu FROM ChucVu WHERE maChucVu = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maChucVu);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tenChucVu = rs.getString("tenChucVu");
            }
        }
        return tenChucVu;
    }

    public String generateMaNhanVien() {
        String newMaNhanVien = "NV001"; // Giá trị mặc định nếu bảng trống
        try {
            String query = "SELECT maNhanVien FROM NhanVien ORDER BY maNhanVien DESC LIMIT 1"; // Lấy mã lớn nhất
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaNhanVien = rs.getString("maNhanVien");

                // Tách phần số từ mã
                String numberPart = lastMaNhanVien.substring(2); // Bỏ "NV", lấy phần số
                int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên

                // Tăng số lên 1
                number++;

                // Tạo mã mới với định dạng NVxxx (3 chữ số)
                newMaNhanVien = String.format("NV%03d", number);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã nhân viên mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaNhanVien; // Trả về mã mới
    }
}