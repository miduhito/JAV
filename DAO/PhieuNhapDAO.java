package DAO;

import DTO.PhieuNhapDTO;
import Interface.DAO_Interface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class PhieuNhapDAO implements DAO_Interface<PhieuNhapDTO> {
    PhieuNhapDTO phieuNhapDTO;

    public PhieuNhapDAO(){}

    public connectDatabase connDB = new connectDatabase();

    @Override
    public ArrayList<PhieuNhapDTO> getData(){
        ArrayList<PhieuNhapDTO> danhSachPhieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                String query = "SELECT * FROM phieunhap WHERE trangThai = true";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                danhSachPhieuNhap = new ArrayList<>();
                while (rs.next()) {
                    phieuNhapDTO = new PhieuNhapDTO();
                    phieuNhapDTO.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    phieuNhapDTO.setNgayNhap(rs.getDate("ngayNhap"));
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

    @Override
    public PhieuNhapDTO getDataById(String id) {
        PhieuNhapDTO phieuNhap = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT * FROM phieunhap WHERE maPhieuNhap = ? AND trangThai = true";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    phieuNhap = new PhieuNhapDTO();
                    phieuNhap.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                    phieuNhap.setNgayNhap(rs.getDate("ngayNhap"));
                    phieuNhap.setMaNhanVien(rs.getString("maNhanVien"));
                    phieuNhap.setMaNhaCungCap(rs.getString("maNCC"));
                    phieuNhap.setTongTien(rs.getDouble("tongTien"));
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
        return phieuNhap;
    }

    @Override
    public boolean add(PhieuNhapDTO entity) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "INSERT INTO phieunhap (maPhieuNhap, ngayNhap, maNhanVien, maNCC, tongTien, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, entity.getMaPhieuNhap());
                ps.setDate(2, new java.sql.Date(entity.getNgayNhap().getTime()));
                ps.setString(3, entity.getMaNhanVien());
                ps.setString(4, entity.getMaNhaCungCap());
                ps.setDouble(5, entity.getTongTien());
                ps.setBoolean(6, true);
                result = ps.executeUpdate() > 0;
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn!", "Error", JOptionPane.ERROR_MESSAGE);
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
    public boolean update(PhieuNhapDTO entity) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "UPDATE phieunhap SET ngayNhap = ?, maNhanVien = ?, maNCC = ?, tongTien = ? WHERE maPhieuNhap = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setDate(1, new java.sql.Date(entity.getNgayNhap().getTime()));
                ps.setString(2, entity.getMaNhanVien());
                ps.setString(3, entity.getMaNhaCungCap());
                ps.setDouble(4, entity.getTongTien());
                ps.setString(5, entity.getMaPhieuNhap());
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
    public boolean delete(String id) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "DELETE FROM phieunhap WHERE maPhieuNhap = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
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
    public boolean hide(String id) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "UPDATE phieunhap SET trangThai = false WHERE maPhieuNhap = ?";
                PreparedStatement ps = connDB.conn.prepareStatement(query);
                ps.setString(1, id);
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
    public boolean checkDuplicate(PhieuNhapDTO entity, String Function) {
        return false;
    }

    @Override
    public String generateID() {
        String newID = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connDB.openConnectDB()) {
                String query = "SELECT maPhieuNhap FROM phieunhap ORDER BY maPhieuNhap DESC LIMIT 1";
                Statement stmt = connDB.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String lastID = rs.getString("maPhieuNhap");
                    int number = Integer.parseInt(lastID.substring(2)) + 1;
                    newID = String.format("PN%03d", number);
                } else {
                    newID = "PN001";
                }
//                rs.close();
//                stmt.close();
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
        return newID;
    }

    public ArrayList<PhieuNhapDTO> advancedSearch(String maPhieuNhap, String maNhanVien, String maNhaCungCap, Date startDate, Date endDate) {
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connDB.openConnectDB();

            StringBuilder sql = new StringBuilder("SELECT * FROM phieunhap WHERE 1=1");
            ArrayList<Object> params = new ArrayList<>();

            if (maPhieuNhap != null && !maPhieuNhap.isEmpty()) {
                sql.append(" AND maPhieuNhap LIKE ?");
                params.add("%" + maPhieuNhap + "%");
            }
            if (maNhanVien != null && !maNhanVien.isEmpty()) {
                sql.append(" AND maNhanVien = ?");
                params.add(maNhanVien);
            }
            if (maNhaCungCap != null && !maNhaCungCap.isEmpty()) {
                sql.append(" AND maNCC = ?");
                params.add(maNhaCungCap);
            }
            if (startDate != null) {
                sql.append(" AND ngayNhap >= ?");
                params.add(new java.sql.Date(startDate.getTime()));
            }
            if (endDate != null) {
                sql.append(" AND ngayNhap <= ?");
                params.add(new java.sql.Date(endDate.getTime()));
            }

            ps = connDB.conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                PhieuNhapDTO dto = new PhieuNhapDTO();
                dto.setMaPhieuNhap(rs.getString("maPhieuNhap"));
                dto.setNgayNhap(rs.getDate("ngayNhap"));
                dto.setMaNhanVien(rs.getString("maNhanVien"));
                dto.setMaNhaCungCap(rs.getString("maNCC"));
                dto.setTongTien(rs.getDouble("tongTien"));
                dto.setTrangThai(rs.getBoolean("trangThai"));
                result.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}

