package DAO;

import DTO.CaLamDTO;
import DTO.NguyenLieuDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.PreparedStatement;
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
        NguyenLieuDTO nguyenLieu = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM nguyenLieu WHERE maNguyenLieu = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nguyenLieu = new NguyenLieuDTO();
                    nguyenLieu.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    nguyenLieu.setTenNguyenLieu(rs.getString("tenNguyenLieu"));
                    nguyenLieu.setLoaiNguyenLieu(rs.getString("loaiNguyenLieu"));
                    nguyenLieu.setNgayNhap(rs.getDate("ngayNhap"));
                    nguyenLieu.setNgayHetHan(rs.getDate("ngayHetHan"));
                    nguyenLieu.setSoLuong(rs.getDouble("soLuong"));
                    nguyenLieu.setDonViDo(rs.getString("donViDo"));
                }
                rs.close();
                pstmt.close();
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
        return nguyenLieu;
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

    public void updateAmount(String maNguyenLieu, double soLuong){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()){
                String sql = "UPDATE nguyenLieu SET soLuong = soLuong + ? WHERE maNguyenLieu = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(sql);

                ps.setDouble(1, soLuong);
                ps.setString(2, maNguyenLieu);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    return;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Không tìm thấy nguyên liệu với mã: " + maNguyenLieu,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                try {
                    ps.close();
                    connDB.closeConnectDB();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
