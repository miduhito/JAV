package DAO;

import DTO.CaLamDTO;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class CaLamDAO implements CRUD<CaLamDTO>{
    CaLamDTO caLamDTO;
    public CaLamDAO(){}
    public connectDatabase connDB = new connectDatabase();

    @Override
    public ArrayList<CaLamDTO> getData(){
        ArrayList<CaLamDTO> danhSachCaLam = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM calam ORDER BY gioBD";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachCaLam = new ArrayList<CaLamDTO>();
                while (rs.next()) {
                    caLamDTO = new CaLamDTO();
                    caLamDTO.setMaCa(rs.getString("maCa"));
                    caLamDTO.setMoTa(rs.getString("moTa"));
                    caLamDTO.setGioBD(rs.getString("gioBD"));
                    caLamDTO.setGioKT(rs.getString("gioKT"));
                    caLamDTO.setTrangThai(rs.getBoolean("trangThai"));
                    danhSachCaLam.add(caLamDTO);
                }
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return danhSachCaLam;
    }

    @Override
    public boolean add(CaLamDTO caLam) {
        boolean success;
        try {
            if (connDB.openConnectDB()) {
                if (checkDuplicate(caLam, "Add")){
                    return false;
                }
                String query = "INSERT INTO calam (maCa, moTa, gioBD, gioKT, trangThai) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, caLam.getMaCa());
                pstmt.setString(2, caLam.getMoTa());
                pstmt.setString(3, caLam.getGioBD());
                pstmt.setString(4, caLam.getGioKT());
                pstmt.setBoolean(5, caLam.getTrangThai());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Thêm ca làm thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm ca làm thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                connDB.closeConnectDB();
                success = true;
            } else {
                success = false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm ca làm: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            success = false;
            ex.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean update(CaLamDTO caLam) {
        boolean success = false;
        try {
            if (checkDuplicate(caLam, "Update")){
                return false;
            }
            if (connDB.openConnectDB()) {
                String query = "UPDATE calam SET moTa = ?, gioBD = ?, gioKT = ? WHERE maCa = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, caLam.getMoTa());
                pstmt.setString(2, caLam.getGioBD());
                pstmt.setString(3, caLam.getGioKT());
                pstmt.setString(4, caLam.getMaCa());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cập nhật ca làm thành công!" , "Success", JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Cập nhật ca làm thất bại! Không tìm thấy ca làm với mã: " + caLam.getMaCa(),
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
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật ca làm: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean delete(String maCaLam) {
        boolean success = false;
        try {
            if (connDB.openConnectDB()){
                String query = "DELETE FROM calam WHERE maCa = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, maCaLam);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa ca làm thành công!" , "Success", JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "Xóa ca làm thất bại! Không tìm thấy ca làm với mã: " + maCaLam,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                connDB.closeConnectDB();
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa ca làm: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return success;
    }

    @Override
    public String generateID() {
        String newMaCa = "C1"; // default value
        try {
            if (connDB.openConnectDB()) {
                String query = "SELECT maCa FROM calam ORDER BY maCa DESC LIMIT 1";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String lastMaCa = rs.getString("maCa");

                    // seperate prefix "C"
                    String numberPart = lastMaCa.substring(1);
                    int number = Integer.parseInt(numberPart);

                    number++;

                    newMaCa = "C" + number;
                }
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo mã ca làm mới: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return newMaCa;
    }

    @Override
    public boolean checkDuplicate(CaLamDTO caLam, String Function){
        boolean isDuplicate = false;
            try {
                if(connDB.openConnectDB()){
                    String query = Function.equals("Add") ?
                            "SELECT COUNT(*) FROM calam WHERE gioBD = ? AND gioKT = ? AND trangThai = true" :
                            "SELECT COUNT(*) FROM calam WHERE gioBD = ? AND gioKT = ? AND trangThai = true AND maCa != ?";

                    PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                    pstmt.setString(1, caLam.getGioBD());
                    pstmt.setString(2, caLam.getGioKT());
                    if (Function.equals("Update")){
                        pstmt.setString(3, caLam.getMaCa());
                    }

                    ResultSet rs = pstmt.executeQuery();
                    if(rs.next()){
                        int count = rs.getInt(1);
                        if (count > 0){
                            isDuplicate = true;
                            JOptionPane.showMessageDialog(null,
                                    "Đã tồn tại ca làm với giờ bắt đầu: " + caLam.getGioBD() +
                                            " và giờ kết thúc " + caLam.getGioKT(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra trùng lặp: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        return isDuplicate;
    }
}
