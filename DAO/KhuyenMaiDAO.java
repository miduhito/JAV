package DAO;

import DTO.KhuyenMaiDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class KhuyenMaiDAO implements DAO_Interface<KhuyenMaiDTO> {
    public connectDatabase connDB = new connectDatabase();

    public KhuyenMaiDAO() {}

    @Override
    public ArrayList<KhuyenMaiDTO> getData() {
        ArrayList<KhuyenMaiDTO> danhSachKhuyenMai = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM khuyenmai";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachKhuyenMai = new ArrayList<>();
                while (rs.next()) {
                    KhuyenMaiDTO khuyenMaiDTO = new KhuyenMaiDTO();
                    khuyenMaiDTO.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    khuyenMaiDTO.setTenKhuyenMai(rs.getString("tenKhuyenMai"));
                    khuyenMaiDTO.setDonViKhuyenMai(rs.getString("donViKhuyenMai"));
                    khuyenMaiDTO.setNgayBatDau(rs.getDate("ngayBatDau"));
                    khuyenMaiDTO.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                    khuyenMaiDTO.setDieuKienApDung(rs.getString("dieuKienApDung"));
                    danhSachKhuyenMai.add(khuyenMaiDTO);
                }
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return danhSachKhuyenMai;
    }

    @Override
    public KhuyenMaiDTO getDataById(String id) {
        KhuyenMaiDTO khuyenMaiDTO = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM khuyenmai WHERE maKhuyenMai = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    khuyenMaiDTO = new KhuyenMaiDTO();
                    khuyenMaiDTO.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    khuyenMaiDTO.setTenKhuyenMai(rs.getString("tenKhuyenMai"));
                    khuyenMaiDTO.setDonViKhuyenMai(rs.getString("donViKhuyenMai"));
                    khuyenMaiDTO.setNgayBatDau(rs.getDate("ngayBatDau"));
                    khuyenMaiDTO.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                    khuyenMaiDTO.setDieuKienApDung(rs.getString("dieuKienApDung"));
                }
                rs.close();
                pstmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return khuyenMaiDTO;
    }

    @Override
    public boolean add(KhuyenMaiDTO entity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "INSERT INTO khuyenmai (maKhuyenMai, tenKhuyenMai, donViKhuyenMai, ngayBatDau, ngayKetThuc, dieuKienApDung) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, entity.getMaKhuyenMai());
                pstmt.setString(2, entity.getTenKhuyenMai());
                pstmt.setString(3, entity.getDonViKhuyenMai());
                pstmt.setDate(4, new java.sql.Date(entity.getNgayBatDau().getTime()));
                pstmt.setDate(5, new java.sql.Date(entity.getNgayKetThuc().getTime()));
                pstmt.setString(6, entity.getDieuKienApDung());

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                connDB.closeConnectDB();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean update(KhuyenMaiDTO entity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "UPDATE khuyenmai SET tenKhuyenMai = ?, donViKhuyenMai = ?, ngayBatDau = ?, ngayKetThuc = ?, dieuKienApDung = ? WHERE maKhuyenMai = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, entity.getTenKhuyenMai());
                pstmt.setString(2, entity.getDonViKhuyenMai());
                pstmt.setDate(3, new java.sql.Date(entity.getNgayBatDau().getTime()));
                pstmt.setDate(4, new java.sql.Date(entity.getNgayKetThuc().getTime()));
                pstmt.setString(5, entity.getDieuKienApDung());
                pstmt.setString(6, entity.getMaKhuyenMai());

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                connDB.closeConnectDB();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "DELETE FROM khuyenmai WHERE maKhuyenMai = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                connDB.closeConnectDB();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean hide(String id) {
        boolean success = false;
        try {
            if (connDB.openConnectDB()) {
                String deleteQuery = "UPDATE khuyenmai SET trangThai = 0 WHERE maKhuyenMai = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(deleteQuery);
                pstmt.setString(1, id);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Ẩn khuyến mãi thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Ẩn khuyến mãi thất bại! Không tìm thấy khuyến mãi với mã: " + id,
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
                    "Lỗi khi ẩn khuyến mãi: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean checkDuplicate(KhuyenMaiDTO entity, String Function) {
        boolean isDuplicate = false;
        try {
            if (connDB.openConnectDB()) {
                String query = Function.equals("Add") ?
                        "SELECT COUNT(*) FROM khuyenmai WHERE tenKhuyenMai = ? AND ngayBatDau = ? AND ngayKetThuc = ?" :
                        "SELECT COUNT(*) FROM khuyenmai WHERE tenKhuyenMai = ? AND ngayBatDau = ? AND ngayKetThuc = ? AND maKhuyenMai != ?";

                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, entity.getTenKhuyenMai());
                pstmt.setDate(2, new java.sql.Date(entity.getNgayBatDau().getTime()));
                pstmt.setDate(3, new java.sql.Date(entity.getNgayKetThuc().getTime()));
                if (Function.equals("Update")) {
                    pstmt.setString(4, entity.getMaKhuyenMai());
                }

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        isDuplicate = true;
                        JOptionPane.showMessageDialog(null,
                                "Đã tồn tại khuyến mãi với tên: " + entity.getTenKhuyenMai() +
                                        ", ngày bắt đầu: " + entity.getNgayBatDau() +
                                        ", ngày kết thúc: " + entity.getNgayKetThuc(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi kiểm tra trùng lặp: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return isDuplicate;
    }

    @Override
    public String generateID() {
        String newMaKhuyenMai = "KM001";
        try {
            if (connDB.openConnectDB()) {
                String query = "SELECT maKhuyenMai FROM khuyenmai ORDER BY maKhuyenMai DESC LIMIT 1";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String lastMaKhuyenMai = rs.getString("maKhuyenMai");

                    String numberPart = lastMaKhuyenMai.substring(2); // Bỏ "KM"
                    int number = Integer.parseInt(numberPart);

                    number++;

                    newMaKhuyenMai = "KM" + String.format("%03d", number);
                }
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tạo mã khuyến mãi mới: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaKhuyenMai;
    }

    public String getDonViKhuyenMai(String maKhuyenMai) {
        KhuyenMaiDTO khuyenMaiDTO = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM khuyenmai WHERE maKhuyenMai = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, maKhuyenMai);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    khuyenMaiDTO = new KhuyenMaiDTO();
                    khuyenMaiDTO.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    khuyenMaiDTO.setTenKhuyenMai(rs.getString("tenKhuyenMai"));
                    khuyenMaiDTO.setDonViKhuyenMai(rs.getString("donViKhuyenMai"));
                    khuyenMaiDTO.setNgayBatDau(rs.getDate("ngayBatDau"));
                    khuyenMaiDTO.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                    khuyenMaiDTO.setDieuKienApDung(rs.getString("dieuKienApDung"));
                }
                rs.close();
                pstmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return khuyenMaiDTO.getDonViKhuyenMai();
    }
}