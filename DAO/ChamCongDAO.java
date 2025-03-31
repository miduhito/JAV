package DAO;

import DTO.LichLamDTO;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChamCongDAO{
    public connectDatabase connDB = new connectDatabase();

    public ChamCongDAO() {}

    public ArrayList<LichLamDTO> getLichLamViec(String maNhanVien, int thang, int nam) {
        ArrayList<LichLamDTO> lichLamList = new ArrayList<>();
        String query = "SELECT * FROM lichlamviec WHERE MONTH(ngayLamViec) = ? AND YEAR(ngayLamViec) = ? AND trangThai=true " +
                (maNhanVien != null ? "AND maNhanVien = ? " : "") +
                "ORDER BY ngayLamViec ASC";

        try {
            if (connDB.openConnectDB()){
                PreparedStatement stmt = connDB.conn.prepareStatement(query);
                stmt.setInt(1, thang);
                stmt.setInt(2, nam);
                if (maNhanVien != null) {
                    stmt.setString(3, maNhanVien);
                }

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    LichLamDTO lichLam = new LichLamDTO();
                    lichLam.setMaLichLam(rs.getString("maLichLam"));
                    lichLam.setMaNhanVien(rs.getString("maNhanVien"));
                    lichLam.setNgayLam(rs.getDate("ngayLamViec"));
                    lichLam.setMaCaLam(rs.getString("maCaLam"));
                    lichLam.setTrangThai(rs.getBoolean("trangThai"));
                    lichLamList.add(lichLam);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return lichLamList;
    }
}
