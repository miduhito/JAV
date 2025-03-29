package DAO;

import DTO.LichLamDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class LichLamDAO implements CRUD<LichLamDTO> {
    private LichLamDTO LichLamDTO;
    public connectDatabase connDB = new connectDatabase();

    public LichLamDAO() {}

    @Override
    public ArrayList<LichLamDTO> getData() {
        ArrayList<LichLamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM lichLamViec";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    LichLamDTO lichLam = new LichLamDTO();
                    lichLam.setMaLichLam(rs.getString("maLichLam"));
                    lichLam.setNgayLam(rs.getDate("ngayLamViec"));
                    lichLam.setMaNhanVien(rs.getString("maNhanVien"));
                    lichLam.setMaCaLam(rs.getString("maCaLam"));
                    lichLam.setTrangThai(rs.getBoolean("trangThai"));
                    list.add(lichLam);
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connDB.closeConnectDB();
        }
        return list;
    }

    @Override
    public boolean add(LichLamDTO entity) {
        String sql = "INSERT INTO lichLamViec (maLichLam, ngayLamViec, maNhanVien, maCaLam, trangThai) VALUES (?, ?, ?, ?, ?)";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, entity.getMaLichLam());
                pstmt.setDate(2, new java.sql.Date(entity.getNgayLam().getTime()));
                pstmt.setString(3, entity.getMaNhanVien());
                pstmt.setString(4, entity.getMaCaLam());
                pstmt.setBoolean(5, entity.getTrangThai());

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public boolean update(LichLamDTO entity) {
        String sql = "UPDATE lichLamViec SET ngayLamViec = ?, maNhanVien = ?, maCaLam = ?, trangThai = ? WHERE maLichLam = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setDate(1, new java.sql.Date(entity.getNgayLam().getTime()));
                pstmt.setString(2, entity.getMaNhanVien());
                pstmt.setString(3, entity.getMaCaLam());
                pstmt.setBoolean(4, entity.getTrangThai());
                pstmt.setString(5, entity.getMaLichLam());

                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM lichLamViec WHERE maLichLam = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, id);
                int rowsAffected = pstmt.executeUpdate();
                pstmt.close();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public boolean checkDuplicate(LichLamDTO entity, String Function) {
        String sql = "SELECT COUNT(*) FROM lichLamViec WHERE ngayLamViec = ? AND maNhanVien = ?";

        if (Function.equals("update")) {
            sql += " AND maLichLam != ?";
        }

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setDate(1, new java.sql.Date(entity.getNgayLam().getTime()));
                pstmt.setString(2, entity.getMaNhanVien());
                if (Function.equals("update")) {
                    pstmt.setString(3, entity.getMaLichLam());
                }

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    rs.close();
                    pstmt.close();
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connDB.closeConnectDB();
        }
        return false;
    }

    @Override
    public String generateID() {
        String sql = "SELECT maLichLam FROM lichLamViec ORDER BY maLichLam DESC LIMIT 1";
        String prefix = "LL";
        int newNumber = 1;

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String lastID = rs.getString("maLichLam");
                    String numberPart = lastID.substring(2);
                    newNumber = Integer.parseInt(numberPart) + 1;
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connDB.closeConnectDB();
        }

        return String.format("%s%03d", prefix, newNumber);
    }
}