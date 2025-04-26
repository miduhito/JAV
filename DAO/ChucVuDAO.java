package DAO;

import DTO.ChucVuDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChucVuDAO implements DAO_Interface<ChucVuDTO> {

    private final connectDatabase connDB = new connectDatabase();

    public ChucVuDAO() {}

    @Override
    public ArrayList<ChucVuDTO> getData() {
        ArrayList<ChucVuDTO> danhSachChucVu = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM chucvu WHERE trangThai = 1";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachChucVu = new ArrayList<>();
                while (rs.next()) {
                    ChucVuDTO chucVuDTO = new ChucVuDTO();
                    chucVuDTO.setMaChucVu(rs.getString("maChucVu"));
                    chucVuDTO.setTenChucVu(rs.getString("tenChucVu"));
                    chucVuDTO.setTrangThai(rs.getBoolean("trangThai"));
                    chucVuDTO.setLuongTheoGio(rs.getDouble("luongTheoGio"));
                    danhSachChucVu.add(chucVuDTO);
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
        return danhSachChucVu;
    }

    @Override
    public ChucVuDTO getDataById(String id) {
        ChucVuDTO chucVuDTO = null;
        String query = "SELECT * FROM chucvu WHERE maChucVu = ? AND trangThai = 1 ";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    chucVuDTO = new ChucVuDTO();
                    chucVuDTO.setMaChucVu(rs.getString("maChucVu"));
                    chucVuDTO.setTenChucVu(rs.getString("tenChucVu"));
                    chucVuDTO.setTrangThai(rs.getBoolean("trangThai"));
                    chucVuDTO.setLuongTheoGio(rs.getDouble("luongTheoGio"));
                    System.out.println(chucVuDTO);
                }
                rs.close();
                pstmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return chucVuDTO;
    }

    @Override
    public boolean add(ChucVuDTO entity) {
        try {
            if (checkDuplicate(entity, "Add")){
                return false;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "INSERT INTO chucvu (maChucVu, tenChucVu, trangThai, luongTheoGio) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, entity.getMaChucVu());
                pstmt.setString(2, entity.getTenChucVu());
                pstmt.setBoolean(3, entity.getTrangThai());
                pstmt.setDouble(4, entity.getLuongTheoGio());

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
    public boolean update(ChucVuDTO entity) {
        try {
            if (checkDuplicate(entity, "Update")){
                return false;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "UPDATE chucvu SET tenChucVu = ?, trangThai = ?, luongTheoGio = ? WHERE maChucVu = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, entity.getTenChucVu());
                pstmt.setBoolean(2, entity.getTrangThai());
                pstmt.setDouble(3, entity.getLuongTheoGio());
                pstmt.setString(4, entity.getMaChucVu());

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
                String query = "UPDATE chucvu SET trangThai = 0 WHERE maChucVu = ?";
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
                String deleteQuery = "UPDATE chucvu SET trangThai = 0 WHERE maChucVu = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(deleteQuery);
                pstmt.setString(1, id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Ẩn chức vụ thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Ẩn chức vụ thất bại! Không tìm thấy chức vụ với mã: " + id,
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
                    "Lỗi khi ẩn chức vụ: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean checkDuplicate(ChucVuDTO entity, String Function) {
        boolean isDuplicate = false;
        try {
            if (connDB.openConnectDB()) {
                String query = Function.equals("Add") ?
                        "SELECT COUNT(*) FROM chucvu WHERE tenChucVu = ? AND trangThai = true" :
                        "SELECT COUNT(*) FROM chucvu WHERE tenChucVu = ? AND trangThai = true AND maChucVu != ?";

                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, entity.getTenChucVu());
                if (Function.equals("Update")) {
                    pstmt.setString(2, entity.getMaChucVu());
                }

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        isDuplicate = true;
                        JOptionPane.showMessageDialog(null,
                                "Đã tồn tại chức vụ với tên: " + entity.getTenChucVu(),
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
        String newMaChucVu = "CV1";
        try {
            if (connDB.openConnectDB()) {
                String query = "SELECT maChucVu FROM chucvu ORDER BY maChucVu DESC LIMIT 1";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String lastMaChucVu = rs.getString("maChucVu");

                    String numberPart = lastMaChucVu.substring(2); // Bỏ "CV"
                    int number = Integer.parseInt(numberPart);

                    number++;

                    newMaChucVu = "CV" + number;
                }
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tạo mã chức vụ mới: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return newMaChucVu;
    }
}
