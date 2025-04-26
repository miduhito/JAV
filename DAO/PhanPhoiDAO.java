package DAO;

import DTO.NguyenLieuDTO;
import DTO.PhanPhoiDTO;
import Interface.DAO_SubInterface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhanPhoiDAO implements DAO_SubInterface<PhanPhoiDTO> {
    PhanPhoiDTO phanPhoiDTO;

    public PhanPhoiDAO(){}

    public connectDatabase connDB = new connectDatabase();

    public ArrayList<PhanPhoiDTO> getData(){
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

    @Override
    public ArrayList<PhanPhoiDTO> getDataById(String id) {
        ArrayList<PhanPhoiDTO> danhSachPhanPhoi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                danhSachPhanPhoi = new ArrayList<>();

                String query = "SELECT * FROM PhanPhoi WHERE maNCC = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PhanPhoiDTO phanPhoi = new PhanPhoiDTO();
                    phanPhoi.setMaNhaCungCap(rs.getString("maNCC"));
                    phanPhoi.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    phanPhoi.setGiaNhap(rs.getDouble("giaNhap"));
                    danhSachPhanPhoi.add(phanPhoi);
                }
                rs.close();
                pstmt.close();
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

    @Override
    public PhanPhoiDTO getDataById(String id, String pair_id) {
        return null;
    }

    public PhanPhoiDTO getDataByIdSub(String id){
        PhanPhoiDTO phanPhoi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(connDB.openConnectDB()){
                phanPhoi = new PhanPhoiDTO();

                String query = "SELECT * FROM PhanPhoi WHERE maNguyenLieu = ?";
                PreparedStatement pstmt = connDB.conn.prepareStatement(query);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    phanPhoi.setMaNhaCungCap(rs.getString("maNCC"));
                    phanPhoi.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    phanPhoi.setGiaNhap(rs.getDouble("giaNhap"));
                }
                rs.close();
                pstmt.close();
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
        return phanPhoi;
    }

    @Override
    public boolean add(PhanPhoiDTO entity) {
        return false;
    }

    public boolean insertPhanPhoi(String maNhaCungCap, List<NguyenLieuDTO> danhSachNguyenLieu) {
        String insertSQL = "INSERT INTO phanphoi (maNCC, maNguyenLieu, giaNhap) VALUES (?, ?, ?)";
        String deleteSQL = "DELETE FROM phanphoi WHERE maNCC = ? AND maNguyenLieu = ?";
        String checkSQL = "SELECT maNguyenLieu, giaNhap FROM phanphoi WHERE maNCC = ?";
        
        if (danhSachNguyenLieu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Chưa chọn nguyên liệu cho nhà cung cấp", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        try {
            if (connDB.openConnectDB()) {
                // Lấy danh sách nguyên liệu hiện tại từ cơ sở dữ liệu
                PreparedStatement checkStmt = connDB.conn.prepareStatement(checkSQL);
                checkStmt.setString(1, maNhaCungCap);
                ResultSet rs = checkStmt.executeQuery();
    
                List<NguyenLieuDTO> currentDBList = new ArrayList<>();
                while (rs.next()) {
                    NguyenLieuDTO nguyenLieu = new NguyenLieuDTO();
                    nguyenLieu.setMaNguyenLieu(rs.getString("maNguyenLieu"));
                    nguyenLieu.setGia(rs.getDouble("giaNhap"));
                    currentDBList.add(nguyenLieu);
                }
                rs.close();
                checkStmt.close();
    
                // Tìm các phần tử cần thêm vào cơ sở dữ liệu (có trong danhSachNguyenLieu nhưng không có trong cơ sở dữ liệu)
                List<NguyenLieuDTO> toInsert = new ArrayList<>(danhSachNguyenLieu);
                toInsert.removeIf(item -> currentDBList.stream()
                    .anyMatch(dbItem -> dbItem.getMaNguyenLieu().equals(item.getMaNguyenLieu())));
    
                // Tìm các phần tử cần xóa khỏi cơ sở dữ liệu (có trong cơ sở dữ liệu nhưng không có trong danhSachNguyenLieu)
                List<NguyenLieuDTO> toDelete = new ArrayList<>(currentDBList);
                toDelete.removeIf(item -> danhSachNguyenLieu.stream()
                    .anyMatch(inputItem -> inputItem.getMaNguyenLieu().equals(item.getMaNguyenLieu())));
    
                // Xử lý thêm dữ liệu
                PreparedStatement insertStmt = connDB.conn.prepareStatement(insertSQL);
                for (NguyenLieuDTO nguyenLieu : toInsert) {
                    insertStmt.setString(1, maNhaCungCap);
                    insertStmt.setString(2, nguyenLieu.getMaNguyenLieu());
                    insertStmt.setDouble(3, nguyenLieu.getGia());
                    insertStmt.executeUpdate();
                }
                insertStmt.close();
    
                // Xử lý xóa dữ liệu
                PreparedStatement deleteStmt = connDB.conn.prepareStatement(deleteSQL);
                for (NguyenLieuDTO nguyenLieu : toDelete) {
                    deleteStmt.setString(1, maNhaCungCap);
                    deleteStmt.setString(2, nguyenLieu.getMaNguyenLieu());
                    deleteStmt.executeUpdate();
                }
                deleteStmt.close();
    
                // Kiểm tra nếu không cần thêm hay xóa, thông báo
                if (toInsert.isEmpty() && toDelete.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nhà cung cấp đã có các nguyên liệu này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Dữ liệu đã được cập nhật thành công.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    
        return true;
    }

    @Override
    public boolean update(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String maNhaCungCap, String maNguyenLieu) {
        boolean success = false;
        try {
            if(connDB.openConnectDB()) {
                String sql = "DELETE FROM phanphoi WHERE maNCC = ? AND maNguyenLieu = ?";

                PreparedStatement pstmt = connDB.conn.prepareStatement(sql);
                pstmt.setString(1, maNhaCungCap);
                pstmt.setString(2, maNguyenLieu);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Xóa nguyên liệu của nhà cung cấp thành công!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    success = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Xóa nguyên liệu của nhà cung cấp thất bại! Không tìm thấy mã nguyên liệu của nhà cung cấp ",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                connDB.closeConnectDB();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến cơ sở dữ liệu!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi xóa nguyên liệu của nhà cung cấp: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    @Override
    public boolean hide(String id, String pair_id) {
        return false;
    }

    @Override
    public boolean checkDuplicate(PhanPhoiDTO entity, String Function) {
        return false;
    }
}
