package DAO;

import DTO.KhuyenMaiDTO;
import DTO.ThucAnDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ThucAnDAO implements DAO_Interface<ThucAnDTO> {
    public connectDatabase connDB = new connectDatabase();

    public ThucAnDAO(){}

    @Override
    public ArrayList<ThucAnDTO> getData() {
        ArrayList<ThucAnDTO> listThucAn = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM thucan";
                Statement pstmt = connDB.conn.createStatement();
                ResultSet rs = pstmt.executeQuery(query);
                while (rs.next()) {
                    ThucAnDTO thucAnDTO = new ThucAnDTO();
                    thucAnDTO.setMaThucAn(rs.getString("maThucAn"));
                    thucAnDTO.setTenThucAn(rs.getString("tenThucAn"));
                    thucAnDTO.setMoTa(rs.getString("moTa"));
                    thucAnDTO.setLoaiMonAn(rs.getString("loaiMonAn"));
                    thucAnDTO.setGia(rs.getDouble("gia"));
                    thucAnDTO.setMaCongThuc(rs.getString("maCongThuc"));
                    listThucAn.add(thucAnDTO);
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
        return listThucAn;
    }

    @Override
    public ThucAnDTO getDataById(String id) {
        ThucAnDTO thucAnDTO = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM thucan WHERE maThucAn = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    thucAnDTO = new ThucAnDTO();
                    thucAnDTO.setMaThucAn(rs.getString("maThucAn"));
                    thucAnDTO.setTenThucAn(rs.getString("tenThucAn"));
                    thucAnDTO.setMoTa(rs.getString("moTa"));
                    thucAnDTO.setLoaiMonAn(rs.getString("loaiMonAn"));
                    thucAnDTO.setGia(rs.getDouble("gia"));
                    thucAnDTO.setMaCongThuc(rs.getString("maCongThuc"));
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
        return thucAnDTO;
    }

    @Override
    public boolean add(ThucAnDTO entity) {
        return false;
    }

    @Override
    public boolean update(ThucAnDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean hide(String id) {
        return false;
    }

    @Override
    public boolean checkDuplicate(ThucAnDTO entity, String Function) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
