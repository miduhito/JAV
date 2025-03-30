package DAO;

import DTO.ChiTietPhieuNhapDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO {
    ChiTietPhieuNhapDTO chiTietPhieuNhapDTO;

    public ChiTietPhieuNhapDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<ChiTietPhieuNhapDTO> getDuLieuQuanLiChiTietPhieuNhap(){
        ArrayList<ChiTietPhieuNhapDTO> danhSachChiTietPhieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM chitietphieunhap";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachChiTietPhieuNhap = new ArrayList<ChiTietPhieuNhapDTO>();
                while (rs.next()) {
                    chiTietPhieuNhapDTO = new ChiTietPhieuNhapDTO();
                    chiTietPhieuNhapDTO.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    chiTietPhieuNhapDTO.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    chiTietPhieuNhapDTO.setSoLuongNhap(rs.getInt("soLuongNhap"));
                    chiTietPhieuNhapDTO.setGiaNhap(rs.getDouble("giaNhap"));
                    chiTietPhieuNhapDTO.setThanhTien(rs.getDouble("thanhTien"));
                    danhSachChiTietPhieuNhap.add(chiTietPhieuNhapDTO);
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
        return danhSachChiTietPhieuNhap;
    }
}
