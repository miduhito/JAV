package DAO;

import DTO.ChiTietKhuyenMaiDTO;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietKhuyenMaiDAO{
    ChiTietKhuyenMaiDTO ChiTietKhuyenMaiDTO;

    public connectDatabase connDB = new connectDatabase();

    public ChiTietKhuyenMaiDAO(){}

    public ArrayList<ChiTietKhuyenMaiDTO> getData(){
        ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM chitietkhuyenmai WHERE trangThai = true";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachChiTietKhuyenMai = new ArrayList<>();
                while (rs.next()) {
                    ChiTietKhuyenMaiDTO = new ChiTietKhuyenMaiDTO();
                    ChiTietKhuyenMaiDTO.setMaKhuyenMai("maKhuyenMai");
                    ChiTietKhuyenMaiDTO.setMaThucAn(rs.getString("maThucAn"));
                    ChiTietKhuyenMaiDTO.setGiaTriKhuyenMai(rs.getDouble("giaTriKhuyenMai"));

                    danhSachChiTietKhuyenMai.add(ChiTietKhuyenMaiDTO);
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
        return danhSachChiTietKhuyenMai;
    }

    public ArrayList<ChiTietKhuyenMaiDTO> getDataByIdSub(String id) {
        ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTiet = null;
        String query = "SELECT * FROM chitietkhuyenmai WHERE maKhuyenMai = ? AND trangThai = true";

        if (connDB.openConnectDB()){
            try {
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                danhSachChiTiet = new ArrayList<>();
                while (rs.next()) {
                    ChiTietKhuyenMaiDTO chiTiet = new ChiTietKhuyenMaiDTO();
                    chiTiet.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    chiTiet.setMaThucAn(rs.getString("maThucAn"));
                    chiTiet.setGiaTriKhuyenMai(rs.getDouble("giaTriKhuyenMai"));
                    chiTiet.setTrangThai(rs.getBoolean("trangThai"));
                    danhSachChiTiet.add(chiTiet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connDB.closeConnectDB();
            }
        }
        return danhSachChiTiet;
    }

    public ChiTietKhuyenMaiDTO getDataById(String id, String maThucAn) {
        ChiTietKhuyenMaiDTO chiTiet = null;
        String query = "SELECT * FROM chitietkhuyenmai WHERE maKhuyenMai = ? AND maThucAn = ? AND trangThai = true";

        if (connDB.openConnectDB()){
            try {
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, maThucAn);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    chiTiet = new ChiTietKhuyenMaiDTO();
                    chiTiet.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    chiTiet.setMaThucAn(rs.getString("maThucAn"));
                    chiTiet.setGiaTriKhuyenMai(rs.getDouble("giaTriKhuyenMai"));
                    chiTiet.setTrangThai(rs.getBoolean("trangThai"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connDB.closeConnectDB();
            }
        }
        return chiTiet;
    }

    
    public boolean add(ChiTietKhuyenMaiDTO entity) {
        String query = "INSERT INTO chitietkhuyenmai (maKhuyenMai, maThucAn, giaTriKhuyenMai, trangThai) VALUES (?, ?, ?, ?)";
        boolean result = false;

        if (!checkDuplicate(entity, "Add")){
            if (connDB.openConnectDB()) {
                try {
                    System.out.println(entity);
                    PreparedStatement ps = connDB.conn.prepareStatement(query);
                    ps.setString(1, entity.getMaKhuyenMai());
                    ps.setString(2, entity.getMaThucAn());
                    ps.setDouble(3, entity.getGiaTriKhuyenMai());
                    ps.setBoolean(4, entity.getTrangThai());

                    result = ps.executeUpdate() > 0;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết khuyến mãi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } finally {
                    connDB.closeConnectDB();
                }
            }
        }
        return result;
    }

    
    public boolean update(ChiTietKhuyenMaiDTO entity) {
        String query = "UPDATE chitietkhuyenmai SET giaTriKhuyenMai = ?, trangThai = ? WHERE maKhuyenMai = ? AND maThucAn = ?";
        boolean result = false;

            if (connDB.openConnectDB()) {
                try {
                    PreparedStatement ps = connDB.conn.prepareStatement(query);
                    ps.setDouble(1, entity.getGiaTriKhuyenMai());
                    ps.setBoolean(2, entity.getTrangThai());
                    ps.setString(3, entity.getMaKhuyenMai());
                    ps.setString(4, entity.getMaThucAn());
                    result = ps.executeUpdate() > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connDB.closeConnectDB();
                }
            }
        return result;
    }

    
    public boolean delete(String id, String maThucAn) {
        String query = "DELETE FROM chitietkhuyenmai WHERE maKhuyenMai = ? and maThucAn = ?";
        boolean result = false;
        if (connDB.openConnectDB()) {
            try {
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, maThucAn);

                result = ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connDB.closeConnectDB();
            }
        }
        return result;
    }

    
    public boolean hide(String id) {
        String query = "UPDATE chitietkhuyenmai SET trangThai = false WHERE maKhuyenMai = ?";
        boolean result = false;

        if (connDB.openConnectDB()) {
            try {
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                result = ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connDB.closeConnectDB();
            }
        }
        return result;
    }

    
    public boolean checkDuplicate(ChiTietKhuyenMaiDTO entity, String Function) {
        boolean isDuplicate = false;

        String query = "SELECT COUNT(*) FROM chitietkhuyenmai WHERE maKhuyenMai = ? AND maThucAn = ? AND trangThai = true ";
        if ("Update".equals(Function)) {
            query += " AND NOT (maKhuyenMai = ? AND maThucAn = ?)";
        }

        if (connDB.openConnectDB()) {
            try {
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, entity.getMaKhuyenMai());
                ps.setString(2, entity.getMaThucAn());

                if ("Update".equals(Function)) {
                    ps.setString(3, entity.getMaKhuyenMai());
                    ps.setString(4, entity.getMaThucAn());
                }
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if ( count > 0 ){
                        isDuplicate = true;
                        JOptionPane.showMessageDialog(null, "Chi tiết đã tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connDB.closeConnectDB();
            }
        }
        return isDuplicate;
    }

    
    public String generateID() {
        return "";
    }


}

