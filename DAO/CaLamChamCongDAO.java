package DAO;

import DTO.CaLamDTO;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class CaLamChamCongDAO {
    CaLamDTO caLamDTO;

    public CaLamChamCongDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<CaLamDTO>getDuLieuQuanLiCaLamChamCong(){
        ArrayList<CaLamDTO> danhSachCaLam = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM calam";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachCaLam = new ArrayList<CaLamDTO>();
                while (rs.next()) {
                    caLamDTO = new CaLamDTO();
                    caLamDTO.setMaCa(rs.getString("maCa"));
                    caLamDTO.setMoTa(rs.getString("moTa"));
                    caLamDTO.setGioBD(rs.getString("gioBD"));
                    caLamDTO.setGioKT(rs.getString("gioKT"));
                    caLamDTO.setTrangThai(rs.getBoolean("trangThai"));
                    danhSachCaLam.add(caLamDTO);
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
        return danhSachCaLam;
    }
}
