package DAO;

import Interface.BUS_Interface;
import DTO.NhanVienDTO;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhanVienDAO implements BUS_Interface<NhanVienDTO> {
    public connectDatabase connDB = new connectDatabase();

    public NhanVienDAO() {}

    @Override
    public ArrayList<NhanVienDTO> getData() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nhanVien";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    NhanVienDTO nv = new NhanVienDTO();
                    nv.setMaNV(rs.getString("maNhanVien"));
                    nv.setTenNV(rs.getString("tenNhanVien"));
                    nv.setSDT(rs.getString("SDT"));
                    nv.setEmail(rs.getString("email"));
                    nv.setNgaySinh(rs.getString("ngaySinh"));
                    nv.setGioiTinh(rs.getString("gioiTinh"));
                    nv.setDiaChi(rs.getString("diaChi"));
                    nv.setTrangThai(rs.getString("trangThai"));
                    nv.setMaChucVu(rs.getString("maChucVu"));
                    nv.setTenDangNhap(rs.getString("tenDangNhap"));
                    list.add(nv);
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
    public NhanVienDTO getDataById(String id) {
        NhanVienDTO nv = new NhanVienDTO();
        String sql = "SELECT * FROM nhanVien WHERE maNhanVien = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    nv.setMaNV(rs.getString("maNhanVien"));
                    nv.setTenNV(rs.getString("tenNhanVien"));
                    nv.setSDT(rs.getString("SDT"));
                    nv.setEmail(rs.getString("email"));
                    nv.setNgaySinh(rs.getString("ngaySinh"));
                    nv.setGioiTinh(rs.getString("gioiTinh"));
                    nv.setDiaChi(rs.getString("diaChi"));
                    nv.setTrangThai(rs.getString("trangThai"));
                    nv.setMaChucVu(rs.getString("maChucVu"));
                    nv.setTenDangNhap(rs.getString("tenDangNhap"));
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return nv;
    }

    @Override
    public boolean add(NhanVienDTO entity) {
        return false;
    }

    @Override
    public boolean update(NhanVienDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean hide(String id) {
        return false;
    }

    @Override
    public boolean regexInput(NhanVienDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }

    public String getTenNhanVienById(String maNhanVien) {
        String tenNhanVien = null;
        String sql = "SELECT tenNhanVien FROM nhanVien WHERE maNhanVien = ?";

        try {
            if (connDB.openConnectDB()) {
                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, maNhanVien);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    tenNhanVien = rs.getString("tenNhanVien");
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        } finally {
            connDB.closeConnectDB();
        }
        return tenNhanVien;
    }
}