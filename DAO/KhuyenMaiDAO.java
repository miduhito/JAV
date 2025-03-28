package DAO;

import DTO.KhuyenMaiDTO;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class KhuyenMaiDAO {
    KhuyenMaiDTO KhuyenMaiDTO;

    public KhuyenMaiDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<KhuyenMaiDTO>getDuLieuQuanLiKhuyenMai(){
        ArrayList<KhuyenMaiDTO> danhSachKhuyenMai = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM khuyenmai";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachKhuyenMai = new ArrayList<KhuyenMaiDTO>();
                while (rs.next()) {
                    KhuyenMaiDTO = new KhuyenMaiDTO();
                    KhuyenMaiDTO.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    KhuyenMaiDTO.setMoTaKhuyenMai(rs.getString("tenKhuyenMai"));
                    KhuyenMaiDTO.setDonViKhuyenMai(rs.getString("donViKhuyenMai"));
                    KhuyenMaiDTO.setNgayBatDau(rs.getString("ngayBatDau"));
                    KhuyenMaiDTO.setNgayKetThuc(rs.getString("ngayKetThuc"));
                    KhuyenMaiDTO.setDieuKienApDung(rs.getString("dieuKienApDung"));
                    danhSachKhuyenMai.add(KhuyenMaiDTO);
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
        return danhSachKhuyenMai;
    }
}

