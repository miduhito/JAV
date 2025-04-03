package DAO;

import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean add(NhaCungCapDTO entity) {
        return false;
    }

    @Override
    public boolean update(NhaCungCapDTO entity) {
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
    public boolean checkDuplicate(NhaCungCapDTO entity, String Function) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
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
