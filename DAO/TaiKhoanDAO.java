package DAO;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
public class TaiKhoanDAO {
    public connectDatabase con = new connectDatabase();
    public ArrayList<TaiKhoanDTO> getdata(){
        ArrayList<TaiKhoanDTO> account = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(con.openConnectDB()){
                String query = "SELECT * FROM taikhoan";
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
            // TODO: handle exception
        public static void main(String[] args){
            TaiKhoanDAO t = new TaiKhoanDAO();
            for(TaiKhoanDTO d : t.getdata()){
                System.out.println(d.getTenDangNhap()+d.getMatKhau()+d.getVaiTro()+d.getTrangThai()+d.getNgayTao());

            }
        }
}
