package DAO;

import DTO.ChiTietKhuyenMaiDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietKhuyenMaiDAO {
    DTO.ChiTietKhuyenMaiDTO ChiTietKhuyenMaiDTO;

    public ChiTietKhuyenMaiDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<ChiTietKhuyenMaiDTO> getDuLieuQuanLiChiTietKhuyenMai(){
        ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM chitietkhuyenmai";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachChiTietKhuyenMai = new ArrayList<ChiTietKhuyenMaiDTO>();
                while (rs.next()) {
                    ChiTietKhuyenMaiDTO = new ChiTietKhuyenMaiDTO();
                    ChiTietKhuyenMaiDTO.setMaKhuyenMai("maKhuyenMai");
                    ChiTietKhuyenMaiDTO.setMaSanPham(rs.getString("maSanPham"));
                    ChiTietKhuyenMaiDTO.setGiaTriKhuyenMai(rs.getFloat("giaTriKhuyenMai"));

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
}

