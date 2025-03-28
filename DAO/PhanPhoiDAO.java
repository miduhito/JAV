package DAO;

import DTO.PhanPhoiDTO;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhanPhoiDAO {
    PhanPhoiDTO phanPhoiDTO;

    public PhanPhoiDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<PhanPhoiDTO> getDuLieuQuanLiPhanPhoi(){
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
}
