package DAO;

import DTO.CaLamDTO;
import DTO.NguyenLieuDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NguyenLieuDAO implements DAO_Interface<NguyenLieuDTO> {
    private connectDatabase connDB = new connectDatabase();
    public NguyenLieuDAO(){}

    @Override
    public ArrayList<NguyenLieuDTO> getData() {
        ArrayList<NguyenLieuDTO> danhSachNguyenLieu = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM nguyenLieu";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachNguyenLieu = new ArrayList<>();
                while (rs.next()) {
                    NguyenLieuDTO nguyenLieuDTO = new NguyenLieuDTO();
                    nguyenLieuDTO.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    nguyenLieuDTO.setTenNguyenLieu(rs.getString("tenNguyenLieu"));
                    nguyenLieuDTO.setLoaiNguyenLieu(rs.getString("loaiNguyenLieu"));
                    nguyenLieuDTO.setNgayNhap(rs.getDate("ngayNhap"));
                    nguyenLieuDTO.setNgayHetHan(rs.getDate("ngayHetHan"));
                    nguyenLieuDTO.setSoLuong(rs.getDouble("soLuong"));
                    nguyenLieuDTO.setDonViDo(rs.getString("donViDo"));
                    danhSachNguyenLieu.add(nguyenLieuDTO);
                }
                rs.close();
                stmt.close();
                connDB.closeConnectDB();
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return danhSachNguyenLieu;
    }

    @Override
    public NguyenLieuDTO getDataById(String id) {
        return null;
    }

    @Override
    public boolean add(NguyenLieuDTO entity) {
        return false;
    }

    @Override
    public boolean update(NguyenLieuDTO entity) {
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
    public boolean checkDuplicate(NguyenLieuDTO entity, String Function) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
