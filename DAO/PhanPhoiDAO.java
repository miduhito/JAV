package DAO;

import DTO.PhanPhoiDTO;
import Interface.DAO_SubInterface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhanPhoiDAO implements DAO_SubInterface<PhanPhoiDTO> {
    PhanPhoiDTO phanPhoiDTO;

    public PhanPhoiDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<PhanPhoiDTO> getData(){
        ArrayList<PhanPhoiDTO> danhSachPhanPhoi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM PhanPhoi";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachPhanPhoi = new ArrayList<PhanPhoiDTO>();
                while (rs.next()) {
                    phanPhoiDTO = new PhanPhoiDTO();
                    phanPhoiDTO.setMaNhaCungCap(rs.getString("maNCC"));
                    phanPhoiDTO.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    phanPhoiDTO.setGiaNhap(rs.getDouble("giaNhap"));
                    danhSachPhanPhoi.add(phanPhoiDTO);
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
        return danhSachPhanPhoi;
    }

    @Override
    public ArrayList<PhanPhoiDTO> getDataById(String id) {
        ArrayList<PhanPhoiDTO> danhSachPhanPhoi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                danhSachPhanPhoi = new ArrayList<>();

                String query = "SELECT * FROM PhanPhoi WHERE maNCC = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PhanPhoiDTO phanPhoi = new PhanPhoiDTO();
                    phanPhoi.setMaNhaCungCap(rs.getString("maNCC"));
                    phanPhoi.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    phanPhoi.setGiaNhap(rs.getDouble("giaNhap"));
                    danhSachPhanPhoi.add(phanPhoi);
                }
                rs.close();
                pstmt.close();
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
        return danhSachPhanPhoi;
    }

    @Override
    public PhanPhoiDTO getDataById(String id, String pair_id) {
        return null;
    }

    public PhanPhoiDTO getDataByIdSub(String id){
        PhanPhoiDTO phanPhoi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                phanPhoi = new PhanPhoiDTO();

                String query = "SELECT * FROM PhanPhoi WHERE maNguyenLieu = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    phanPhoi.setMaNhaCungCap(rs.getString("maNCC"));
                    phanPhoi.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    phanPhoi.setGiaNhap(rs.getDouble("giaNhap"));
                }
                rs.close();
                pstmt.close();
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
        return phanPhoi;
    }

    @Override
    public boolean add(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean update(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id, String pair_id) {
        return false;
    }

    @Override
    public boolean hide(String id, String pair_id) {
        return false;
    }

    @Override
    public boolean checkDuplicate(PhanPhoiDTO entity, String Function) {
        return false;
    }
}
