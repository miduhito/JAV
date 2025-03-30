package DAO;

import DTO.PhieuNhapDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhieuNhapDAO {
    PhieuNhapDTO phieuNhapDTO;

    public PhieuNhapDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<PhieuNhapDTO> getDuLieuQuanLiPhieuNhap(){
        ArrayList<PhieuNhapDTO> danhSachPhieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM phieunhap";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachPhieuNhap = new ArrayList<PhieuNhapDTO>();
                while (rs.next()) {
                    phieuNhapDTO = new PhieuNhapDTO();
                    phieuNhapDTO.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    phieuNhapDTO.setNgayNhap(rs.getString("ngayNhap"));
                    phieuNhapDTO.setMaNhanVien(rs.getString("maNhanVien"));
                    phieuNhapDTO.setMaNhaCungCap(rs.getString("maNCC"));
                    phieuNhapDTO.setTongTien(rs.getDouble("tongTien"));
                    danhSachPhieuNhap.add(phieuNhapDTO);
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
        return danhSachPhieuNhap;
    }
}
