package DAO;

import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import Interface.DAO_Interface;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NhaCungCapDAO implements DAO_Interface<NhaCungCapDTO> {
    private final connectDatabase connDB = new connectDatabase();

    public NhaCungCapDAO(){}


    @Override
    public ArrayList<NhaCungCapDTO> getData() {
        ArrayList<NhaCungCapDTO> danhSachNhaCungCap = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE trangThai = true";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    NhaCungCapDTO nhaCungCap = new NhaCungCapDTO();
                    nhaCungCap.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                    nhaCungCap.setTenNhaCungCap(rs.getString("tenNhaCungCap"));
                    nhaCungCap.setDiaChi(rs.getString("diaChi"));
                    nhaCungCap.setEmail(rs.getString("email"));
                    nhaCungCap.setSDT(rs.getString("SDT"));
                    nhaCungCap.setTrangThai(rs.getBoolean("trangThai"));
                    danhSachNhaCungCap.add(nhaCungCap);
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return danhSachNhaCungCap;
    }

    @Override
    public NhaCungCapDTO getDataById(String id) {
        NhaCungCapDTO nhaCungCap = new NhaCungCapDTO();
        String sql = "SELECT * FROM nhacungcap WHERE maNhaCungCap = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nhaCungCap.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                    nhaCungCap.setTenNhaCungCap(rs.getString("tenNhaCungCap"));
                    nhaCungCap.setDiaChi(rs.getString("diaChi"));
                    nhaCungCap.setEmail(rs.getString("email"));
                    nhaCungCap.setSDT(rs.getString("SDT"));
                    nhaCungCap.setTrangThai(rs.getBoolean("trangThai"));
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return nhaCungCap;
    }

    @Override
    public boolean add(NhaCungCapDTO nhaCungCap) {
        boolean success;
        try {
            if(connDB.openConnectDB()) {
                if(checkDuplicate(nhaCungCap, "add")) {
                    return false;
                }
                String sql = "INSERT INTO nhacungcap (maNhaCungCap, tenNhaCungCap, email, SDT, diaChi, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, nhaCungCap.getMaNhaCungCap());
                pstmt.setString(2, nhaCungCap.getTenNhaCungCap());
                pstmt.setString(3, nhaCungCap.getEmail());
                pstmt.setString(4, nhaCungCap.getSDT());
                pstmt.setString(5, nhaCungCap.getDiaChi());
                pstmt.setBoolean(6, nhaCungCap.isTrangThai());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                connDB.closeConnectDB();
                success = true;
            } else {
                success = false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà cung cấp: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        }
        return success;       
    }

    @Override
    public boolean update(NhaCungCapDTO nhaCungCap) {
        boolean success = false;
        try {
            if(checkDuplicate(nhaCungCap, "update")) {
                return false;
            }
            if(connDB.openConnectDB()) {
                String sql = "UPDATE nhacungcap set tenNhaCungCap = ?, email = ?, SDT = ?, diaChi = ?, trangThai = ? WHERE maNhaCungCap = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, nhaCungCap.getTenNhaCungCap());
                pstmt.setString(2, nhaCungCap.getEmail());
                pstmt.setString(3, nhaCungCap.getSDT());
                pstmt.setString(4, nhaCungCap.getDiaChi());
                pstmt.setBoolean(5, nhaCungCap.isTrangThai());
                pstmt.setString(6, nhaCungCap.getMaNhaCungCap());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật nhà cung cấp thành công!" , "Success", JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Cập nhật nhà cung cấp thất bại! Không tìm thấy nhà cung cấp với mã: " + nhaCungCap.getMaNhaCungCap(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                connDB.closeConnectDB();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật nhà cung cấp: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean delete(String maNhaCungCap) {
        boolean success = false;
        try {
            if(connDB.openConnectDB()) {
                String checksql = "SELECT COUNT(*) FROM phieunhap WHERE maNhaCungCap = ?";
                PreparedStatement checkpstmt = connDB.conn.prepareStatement(checksql);
                checkpstmt.setString(1, maNhaCungCap);
                ResultSet rs = checkpstmt.executeQuery();

                if(rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(null,
                                "Không thể xóa nhà cung cấp vì mã nhà cung cấp này vẫn đang được lưu trong phiếu nhập!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        rs.close();
                        checkpstmt.close();
                        connDB.closeConnectDB();
                        return false;
                    }
                }
                rs.close();
                checkpstmt.close();

                String sql = "DELETE FROM nhacungcap WHERE maNhaCungCap = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, maNhaCungCap);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Xóa nhà cung cấp thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Xóa nhà cung cấp thất bại! Không tìm thấy nhà cung cấp với mã: " + maNhaCungCap,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                connDB.closeConnectDB();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi xóa nhà cung cấp: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean hide(String id) {
        boolean success = false;
        try {
            if (connDB.openConnectDB()) {
                String deleteQuery = "UPDATE nhacungcap SET trangThai = 0 WHERE maNhaCungCap = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(deleteQuery);
                pstmt.setString(1, id);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Ẩn nhà cung cấp thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Ẩn nhà cung cấp thất bại! Không tìm thấy nhà cung cấp với mã: " + id,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                connDB.closeConnectDB();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi ẩn nhà cung cấp: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean checkDuplicate(NhaCungCapDTO entity, String function) {
        String sql = "";

        // Phân loại kiểm tra theo thao tác
        if ("add".equalsIgnoreCase(function)) {
            // Kiểm tra nếu mã nhà cung cấp đã tồn tại
            sql = "SELECT COUNT(*) AS count FROM NhaCungCap WHERE maNhaCungCap = ?";
        } else if ("update".equalsIgnoreCase(function)) {
            // Kiểm tra nếu mã nhà cung cấp đã tồn tại nhưng bỏ qua bản ghi hiện tại
            sql = "SELECT COUNT(*) AS count FROM NhaCungCap WHERE maNhaCungCap = ? AND maNhaCungCap != ?";
        } else {
            throw new IllegalArgumentException("Hàm không hợp lệ: " + function);
        }
        try (PreparedStatement pstmt = connDB.conn.prepareStatement(sql)) {

            // Gán tham số cho câu lệnh SQL
            pstmt.setString(1, entity.getMaNhaCungCap());

            // Nếu là thao tác "update", thêm tham số để bỏ qua bản ghi hiện tại
            if ("update".equalsIgnoreCase(function)) {
                pstmt.setString(2, entity.getMaNhaCungCap());
            }

            // Thực thi truy vấn và kiểm tra kết quả
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Trả về true nếu tìm thấy bản ghi trùng lặp
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false; // Trả về false nếu không tìm thấy bản ghi hoặc có lỗi
    }


    @Override
    public String generateID() {
        String newMaNhaCungCap = "NCC001"; // Giá trị mặc định nếu bảng trống
        try {
            if (connDB.openConnectDB()) {
                String query = "SELECT maNhaCungCap FROM nhacungcap ORDER BY maNhaCungCap DESC LIMIT 1"; // Lấy mã lớn nhất
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
    
                if (rs.next()) {
                    String lastMaNhaCungCap = rs.getString("maNhaCungCap");
    
                    // Tách phần số từ mã
                    String numberPart = lastMaNhaCungCap.substring(3); // Bỏ "NCC", lấy phần số
                    int number = Integer.parseInt(numberPart); // Chuyển phần số thành số nguyên
    
                    // Tăng số lên 1
                    number++;
    
                    // Tạo mã mới với định dạng NCCxxx (3 chữ số)
                    newMaNhaCungCap = String.format("NCC%03d", number);
                }
    
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã nhà cung cấp làm mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaNhaCungCap; // Trả về mã mới
    }

    public NhaCungCapDTO getDataByName(String name){
        NhaCungCapDTO nhaCungCap = new NhaCungCapDTO();
        String sql = "SELECT * FROM nhacungcap WHERE tenNhaCungCap = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nhaCungCap.setMaNhaCungCap(rs.getString("maNhaCungCap"));
                    nhaCungCap.setTenNhaCungCap(rs.getString("tenNhaCungCap"));
                    nhaCungCap.setDiaChi(rs.getString("diaChi"));
                    nhaCungCap.setEmail(rs.getString("email"));
                    nhaCungCap.setSDT(rs.getString("SDT"));
                    nhaCungCap.setTrangThai(rs.getBoolean("trangThai"));
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return nhaCungCap;
    }

}
