package DAO;

import DTO.LichLamDTO;
import Interface.DAO_Interface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class LichLamDAO implements DAO_Interface<LichLamDTO> {
    public connectDatabase connDB = new connectDatabase();

    public LichLamDAO() {}

    @Override
    public ArrayList<LichLamDTO> getData() {
        ArrayList<LichLamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM lichLamViec WHERE trangThai = 1";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    LichLamDTO lichLam = new LichLamDTO();
                    lichLam.setMaLichLam(rs.getString("maLichLam"));
                    lichLam.setNgayLam(rs.getDate("ngayLamViec"));
                    lichLam.setMaNhanVien(rs.getString("maNhanVien"));
                    lichLam.setMaCaLam(rs.getString("maCaLam"));
                    lichLam.setTrangThai(rs.getBoolean("trangThai"));
                    list.add(lichLam);
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return list;
    }

    @Override
    public boolean add(LichLamDTO entity) {
        String sql = "INSERT INTO lichLamViec (maLichLam, ngayLamViec, maNhanVien, maCaLam, trangThai) VALUES (?, ?, ?, ?, ?)";

        if (checkDuplicate(entity, "Add")){
            return false;
        }
        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, entity.getMaLichLam());
                pstmt.setDate(2, new java.sql.Date(entity.getNgayLam().getTime()));
                pstmt.setString(3, entity.getMaNhanVien());
                pstmt.setString(4, entity.getMaCaLam());
                pstmt.setBoolean(5, entity.getTrangThai());

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Thêm lịch thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm lịch thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public boolean update(LichLamDTO entity) {
        String sql = "UPDATE lichLamViec SET ngayLamViec = ?, maNhanVien = ?, maCaLam = ?, trangThai = ? WHERE maLichLam = ?";

        if (checkDuplicate(entity, "Update")){
            return false;
        }

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setDate(1, new java.sql.Date(entity.getNgayLam().getTime()));
                pstmt.setString(2, entity.getMaNhanVien());
                pstmt.setString(3, entity.getMaCaLam());
                pstmt.setBoolean(4, entity.getTrangThai());
                pstmt.setString(5, entity.getMaLichLam());

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            if (connDB.openConnectDB()) {
                String checkQuery = "SELECT ngayLamViec FROM lichLamViec WHERE maLichLam = ?";
                PreparedStatement checkStmt = connDB.conn.prepareStatement(checkQuery);
                checkStmt.setString(1, id);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    Date ngayLamViec = rs.getDate("ngayLamViec");
                    java.util.Calendar calNgayLam = java.util.Calendar.getInstance();
                    calNgayLam.setTime(ngayLamViec);
                    calNgayLam.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    calNgayLam.set(java.util.Calendar.MINUTE, 0);
                    calNgayLam.set(java.util.Calendar.SECOND, 0);
                    calNgayLam.set(java.util.Calendar.MILLISECOND, 0);

                    java.util.Calendar calHienTai = java.util.Calendar.getInstance();
                    calHienTai.setTime(new Date());
                    calHienTai.set(java.util.Calendar.HOUR_OF_DAY, 0);
                    calHienTai.set(java.util.Calendar.MINUTE, 0);
                    calHienTai.set(java.util.Calendar.SECOND, 0);
                    calHienTai.set(java.util.Calendar.MILLISECOND, 0);

                    // So sánh ngày
                    if (calNgayLam.getTime().before(calHienTai.getTime())) {
                        // Nếu ngày làm đã qua, không cho phép xóa
                        JOptionPane.showMessageDialog(null,
                                "Không thể xóa lịch làm vì ngày làm đã qua!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        rs.close();
                        checkStmt.close();
                        connDB.closeConnectDB();
                        return false;
                    }
                } else {
                    // Nếu không tìm thấy lịch làm với mã id
                    JOptionPane.showMessageDialog(null,
                            "Không tìm thấy lịch làm với mã: " + id,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    rs.close();
                    checkStmt.close();
                    connDB.closeConnectDB();
                    return false;
                }
                rs.close();
                checkStmt.close();

                // Bước 2: Nếu ngày làm chưa qua, tiến hành xóa
                String deleteQuery = "DELETE FROM lichLamViec WHERE maLichLam = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(deleteQuery);
                pstmt.setString(1, id);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Xóa lịch làm thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Xóa lịch làm thất bại! Không tìm thấy lịch làm với mã: " + id,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                connDB.closeConnectDB();
                return rowsAffected > 0;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi xóa lịch làm: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public boolean hide(String id){
        boolean success = false;
        try {
            if (connDB.openConnectDB()) {
                String deleteQuery = "UPDATE lichlamviec SET trangThai = 0 WHERE maLichLam = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(deleteQuery);
                pstmt.setString(2, id);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Vô hiệu lịch làm thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Vô hiệu lịch làm thất bại! Không tìm thấy ca làm với mã: " + id,
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
                    "Lỗi khi vô hiệu lịch làm: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean checkDuplicate(LichLamDTO entity, String Function) {
        String sql = "SELECT COUNT(*) FROM lichLamViec WHERE ngayLamViec = ? AND maNhanVien = ? AND trangThai = true";
        System.out.println(entity);
        if (Function.equals("Update")) {
            sql += " AND maLichLam != ?";
        }

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setDate(1, new java.sql.Date(entity.getNgayLam().getTime()));
                pstmt.setString(2, entity.getMaNhanVien());
                if (Function.equals("Update")) {
                    pstmt.setString(3, entity.getMaLichLam());
                }

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0){
                        JOptionPane.showMessageDialog(null,
                            "Nhân viên đã có ca làm trong ngày, không thể thêm ca làm cùng ngày",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return true;
                    }
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }

    @Override
    public String generateID() {
        String sql = "SELECT maLichLam FROM lichLamViec ORDER BY maLichLam DESC LIMIT 1";
        String prefix = "LL";
        int newNumber = 1;

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String lastID = rs.getString("maLichLam");
                    String numberPart = lastID.substring(2);
                    newNumber = Integer.parseInt(numberPart) + 1;
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return String.format("%s%03d", prefix, newNumber);
    }

    public ArrayList<LichLamDTO> getDataByDate(Date date){
        ArrayList<LichLamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM lichLamViec WHERE ngayLamViec = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setDate(1, new java.sql.Date(date.getTime()));
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    LichLamDTO lichLam = new LichLamDTO();
                    lichLam.setMaLichLam(rs.getString("maLichLam"));
                    lichLam.setNgayLam(rs.getDate("ngayLamViec"));
                    lichLam.setMaNhanVien(rs.getString("maNhanVien"));
                    lichLam.setMaCaLam(rs.getString("maCaLam"));
                    lichLam.setTrangThai(rs.getBoolean("trangThai"));
                    list.add(lichLam);
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return list;
    }

    @Override
    public LichLamDTO getDataById(String Id){
        LichLamDTO lichLam = new LichLamDTO();
        String sql = "SELECT * FROM lichLamViec WHERE maLichLam = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, Id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    lichLam.setMaLichLam(rs.getString("maLichLam"));
                    lichLam.setNgayLam(rs.getDate("ngayLamViec"));
                    lichLam.setMaNhanVien(rs.getString("maNhanVien"));
                    lichLam.setMaCaLam(rs.getString("maCaLam"));
                    lichLam.setTrangThai(rs.getBoolean("trangThai"));
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return lichLam;
    }

}