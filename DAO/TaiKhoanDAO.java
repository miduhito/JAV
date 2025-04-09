package DAO;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
public class TaiKhoanDAO {
    public connectDatabase con = new connectDatabase();
    public ArrayList<TaiKhoanDTO> dataDangNhap(){
        ArrayList<TaiKhoanDTO> accountList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(con.openConnectDB()){
                String query = "SELECT * FROM taikhoan WHERE trangThai='Active'";
                Statement s = con.conn.createStatement();
                ResultSet r = s.executeQuery(query);
                while (r.next()) {
                    TaiKhoanDTO acc = new TaiKhoanDTO();
                    acc.setTenDangNhap(r.getString("tenDangNhap"));
                    acc.setMatKhau(r.getString("matKhau"));
                    acc.setNgayTao(r.getDate("ngayTao"));
                    acc.setTrangThai(r.getString("trangThai"));
                    acc.setVaiTro(r.getString("vaiTro"));
                    accountList.add(acc);
                }
                r.close();
                s.close();
                con.closeConnectDB();

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
        return accountList;
    }
    public ArrayList<TaiKhoanDTO> getdata(){
        ArrayList<TaiKhoanDTO> account = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(con.openConnectDB()){
                String query = "SELECT * FROM taikhoan ORDER BY vaiTro";
                Statement s = con.conn.createStatement();
                ResultSet r = s.executeQuery(query);
                while (r.next()) {
                    TaiKhoanDTO acc = new TaiKhoanDTO();
                    acc.setTenDangNhap(r.getString("tenDangNhap"));
                    acc.setMatKhau(r.getString("matKhau"));
                    acc.setNgayTao(r.getDate("ngayTao"));
                    acc.setTrangThai(r.getString("trangThai"));
                    acc.setVaiTro(r.getString("vaiTro"));
                    account.add(acc);
                }
                r.close();
                s.close();
                con.closeConnectDB();

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
        return account;
        }
        public ArrayList<NhanVienDTO> getdatanv(){
            ArrayList<NhanVienDTO> account = new ArrayList<>();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                if(con.openConnectDB()){
                    String query = "SELECT * FROM nhanvien WHERE tenDangNhap IS NULL";
                    Statement s = con.conn.createStatement();
                    ResultSet r = s.executeQuery(query);
                    while (r.next()) {
                        NhanVienDTO acc = new NhanVienDTO();
                        acc.setTenNV(r.getString("tenNhanVien"));
                        acc.setMaNV(r.getString("maNhanVien"));
                        account.add(acc);
                    }
                    r.close();
                    s.close();
                    con.closeConnectDB();
    
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
            return account;
            }
            public void insertTaiKhoan(String tenDangNhap, String matKhau, Date ngayTao, String trangThai, String vaiTro, String maNV) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    if (con.openConnectDB()) {
                        String callQuery = "{CALL insertTaiKhoan(?, ?, ?, ?, ?, ?)}";
                        CallableStatement cstm = con.conn.prepareCall(callQuery);
                        cstm.setString(1, tenDangNhap);
                        cstm.setString(2, matKhau);
                        cstm.setDate(3, ngayTao);
                        cstm.setString(4, trangThai);
                        cstm.setString(5, vaiTro);
                        cstm.setString(6, maNV);
                        cstm.execute();
                        cstm.close();
                        con.closeConnectDB();
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Ghi lại thông tin lỗi
                } catch (Exception e) {
                    e.printStackTrace(); // Bắt các ngoại lệ khác
                }
            }
            public void deleteTaiKhoan(String tenDangNhap) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    if (con.openConnectDB()) {
                        CallableStatement cs = con.conn.prepareCall("{CALL deleteTaiKhoanProc(?)}");
                        cs.setString(1, tenDangNhap);
                        cs.execute();
                        cs.close();
                        con.closeConnectDB();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println(e);
                }
            }
        
        public NhanVienDTO timNV(String tenDangNhap){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                if(con.openConnectDB()){
                    String query = "SELECT * FROM nhanvien WHERE tenDangNhap=?";
                    PreparedStatement ps = con.conn.prepareStatement(query);
                    ps.setString(1, tenDangNhap);
                    ResultSet r = ps.executeQuery();
                    if(r.next()){
                        NhanVienDTO nv = new NhanVienDTO();
                        nv.setTenNV(r.getString("tenNhanVien"));
                        nv.setMaNV(r.getString("maNhanVien"));
                        return nv;
                    }
                r.close();
                con.closeConnectDB();
                }
            } catch (Exception e) {
                System.out.println(e);
                // TODO: handle exception
            }
            return null;
            
        }
        public void editTaiKhoan(String tenDangNhap, String matKhau, String trangThai, String vaiTro, String tenDangNhapCu) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                if (con.openConnectDB()) {
                    CallableStatement cs = con.conn.prepareCall("{CALL editTaiKhoan(?, ?, ?, ?, ?)}");
                    cs.setString(1, tenDangNhap);       // new username
                    cs.setString(2, matKhau);           // new password
                    cs.setString(3, trangThai);         // new status
                    cs.setString(4, vaiTro);            // new role
                    cs.setString(5, tenDangNhapCu);     // old username
        
                    cs.execute();
                    cs.close();
                    con.closeConnectDB();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println(e);
            }
        }        
            // TODO: handle exception
        // public static void main(String[] args){
        //     TaiKhoanDAO t = new TaiKhoanDAO();
        //     for(TaiKhoanDTO d : t.getdata()){
        //         System.out.println(d.getTenDangNhap()+d.getMatKhau()+d.getVaiTro()+d.getTrangThai()+d.getNgayTao());

        //     }
        // }
}
