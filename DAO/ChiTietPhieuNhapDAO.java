package DAO;

import DTO.ChiTietPhieuNhapDTO;
import Interface.DAO_SubInterface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAO implements DAO_SubInterface<ChiTietPhieuNhapDTO> {
    ChiTietPhieuNhapDTO chiTietPhieuNhapDTO;

    public ChiTietPhieuNhapDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public void closeConnectDB(){
        connDB.closeConnectDB();
    }

    public ArrayList<ChiTietPhieuNhapDTO> getData(){
        ArrayList<ChiTietPhieuNhapDTO> danhSachChiTietPhieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM chitietphieunhap WHERE trangThai = true";
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
                    chiTietPhieuNhapDTO.setGhiChu(rs.getString("ghiChu"));
                    chiTietPhieuNhapDTO.setTrangThai(rs.getBoolean("trangThai"));
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

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> getDataById(String id) {
        ArrayList<ChiTietPhieuNhapDTO> danhSachChiTietPhieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM chitietphieunhap WHERE maPhieuNhap = ? AND trangThai = true";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                danhSachChiTietPhieuNhap = new ArrayList<ChiTietPhieuNhapDTO>();
                while (rs.next()) {
                    chiTietPhieuNhapDTO = new ChiTietPhieuNhapDTO();
                    chiTietPhieuNhapDTO.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    chiTietPhieuNhapDTO.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    chiTietPhieuNhapDTO.setSoLuongNhap(rs.getInt("soLuongNhap"));
                    chiTietPhieuNhapDTO.setGiaNhap(rs.getDouble("giaNhap"));
                    chiTietPhieuNhapDTO.setThanhTien(rs.getDouble("thanhTien"));
                    chiTietPhieuNhapDTO.setGhiChu(rs.getString("ghiChu"));
                    chiTietPhieuNhapDTO.setTrangThai(rs.getBoolean("trangThai"));
                    danhSachChiTietPhieuNhap.add(chiTietPhieuNhapDTO);
                }
                rs.close();
                ps.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return danhSachChiTietPhieuNhap;
    }

    @Override
    public ChiTietPhieuNhapDTO getDataById(String id, String pair_id) {
        ChiTietPhieuNhapDTO chiTietPhieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM chitietphieunhap WHERE maPhieuNhap = ? AND maNguyenLieu = ? AND trangThai = true";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, pair_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    chiTietPhieuNhap = new ChiTietPhieuNhapDTO();
                    chiTietPhieuNhap.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    chiTietPhieuNhap.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    chiTietPhieuNhap.setSoLuongNhap(rs.getInt("soLuongNhap"));
                    chiTietPhieuNhap.setGiaNhap(rs.getDouble("giaNhap"));
                    chiTietPhieuNhap.setThanhTien(rs.getDouble("thanhTien"));
                    chiTietPhieuNhap.setGhiChu(rs.getString("ghiChu"));
                    chiTietPhieuNhap.setTrangThai(rs.getBoolean("trangThai"));
                }
                rs.close();
                ps.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return chiTietPhieuNhap;
    }

    @Override
    public boolean add(ChiTietPhieuNhapDTO entity) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                if (checkDuplicate(entity, "Add")){
                    return false;
                }
                String query = "INSERT INTO chitietphieunhap (maPhieuNhap, maNguyenLieu, soLuongNhap, giaNhap, thanhTien, ghiChu, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, entity.getMaPhieuNhap());
                ps.setString(2, entity.getMaNguyenLieu());
                ps.setInt(3, entity.getSoLuongNhap());
                ps.setDouble(4, entity.getGiaNhap());
                ps.setDouble(5, entity.getThanhTien());
                ps.setString(6, entity.getGhiChu());
                ps.setBoolean(7, true);
                result = ps.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(ChiTietPhieuNhapDTO entity) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "UPDATE chitietphieunhap SET soLuongNhap = ?, giaNhap = ?, thanhTien = ?, ghiChu = ?, trangThai = ? WHERE maPhieuNhap = ? AND maNguyenLieu = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setInt(1, entity.getSoLuongNhap());
                ps.setDouble(2, entity.getGiaNhap());
                ps.setDouble(3, entity.getThanhTien());
                ps.setString(4, entity.getGhiChu());
                ps.setBoolean(5, entity.isTrangThai());
                ps.setString(6, entity.getMaPhieuNhap());
                ps.setString(7, entity.getMaNguyenLieu());
                result = ps.executeUpdate() > 0;
                ps.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(String id, String pair_id) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "DELETE FROM chitietphieunhap WHERE maPhieuNhap = ? AND maNguyenLieu = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, pair_id);
                result = ps.executeUpdate() > 0;
                ps.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean hide(String id, String pair_id) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "UPDATE chitietphieunhap SET trangThai = false WHERE maPhieuNhap = ? AND maNguyenLieu = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ps.setString(2, pair_id);
                result = ps.executeUpdate() > 0;
                ps.close();
                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean checkDuplicate(ChiTietPhieuNhapDTO entity, String Function) {
        boolean isDuplicate = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT COUNT(*) FROM chitietphieunhap WHERE maPhieuNhap = ? AND maNguyenLieu = ? AND trangThai = true";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, entity.getMaPhieuNhap());
                ps.setString(2, entity.getMaNguyenLieu());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if(count > 0){
                        JOptionPane.showMessageDialog(null,
                                "Nguyên liệu đã được thêm ! ", "Error", JOptionPane.ERROR_MESSAGE);
                        return true;
                    }
                }
//                rs.close();
//                ps.close();
//                connDB.closeConnectDB();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy class driver", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return isDuplicate;
    }
}
