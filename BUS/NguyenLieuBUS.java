package BUS;

import DAO.NguyenLieuDAO;
import DTO.NguyenLieuDTO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NguyenLieuBUS {
    private NguyenLieuDAO nguyenLieuDAO;

    public NguyenLieuBUS() {
        nguyenLieuDAO = new NguyenLieuDAO();
    }

    public List<NguyenLieuDTO> getAllNguyenLieu() {
        return nguyenLieuDAO.getAllNguyenLieu();
    }

    public void addNguyenLieu(NguyenLieuDTO nl) throws SQLException {
        // Gọi DAO để thêm nguyên liệu
        nguyenLieuDAO.insertNguyenLieu(nl);
    }

    public void updateNguyenLieu(NguyenLieuDTO nl) throws SQLException {
        nguyenLieuDAO.updateNguyenLieu(nl);
    }

    public void deleteNguyenLieu(String maNguyenLieu) throws SQLException {
        nguyenLieuDAO.deleteNguyenLieu(maNguyenLieu);
    }

    public String generateMaNguyenLieu() {
        return nguyenLieuDAO.generateMaNguyenLieu();
    }

    public NguyenLieuDTO getNguyenLieuById(String maNguyenLieu) {
        try {
            return nguyenLieuDAO.getNguyenLieuById(maNguyenLieu);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin nguyên liệu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new NguyenLieuDTO();
        }
    }

    public void updateAmount(String maNguyenLieu, Double soLuong) {
        nguyenLieuDAO.updateAmount(maNguyenLieu, soLuong);
    }

    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<NguyenLieuDTO> list = getAllNguyenLieu();
        for (NguyenLieuDTO nl : list) {
            tableModel.addRow(new Object[]{
                    nl.getMaNguyenLieu(),
                    nl.getTenNguyenLieu(),
                    nl.getLoaiNguyenLieu(),
                    nl.getNgayNhap(),
                    nl.getNgayHetHan(),
                    nl.getSoLuong(),
                    nl.getDonViDo()
            });
        }
    }

    public List<NguyenLieuDTO> findNguyenLieu(String searchText) {
        try {
            List<NguyenLieuDTO> nlSearch = new ArrayList<>();
            // Lặp qua tất cả nguyên liệu từ nguyenLieuDAO
            for (NguyenLieuDTO nl : nguyenLieuDAO.getAllNguyenLieu()) {
                // Kiểm tra từng thuộc tính của NguyenLieuDTO
                if ((nl.getMaNguyenLieu() != null && nl.getMaNguyenLieu().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nl.getTenNguyenLieu() != null && nl.getTenNguyenLieu().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nl.getLoaiNguyenLieu() != null && nl.getLoaiNguyenLieu().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nl.getNgayNhap() != null && nl.getNgayNhap().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nl.getNgayHetHan() != null && nl.getNgayHetHan().toLowerCase().contains(searchText.toLowerCase())) ||
                    (String.valueOf(nl.getSoLuong()) != null && String.valueOf(nl.getSoLuong()).contains(searchText)) ||
                    (nl.getDonViDo() != null && nl.getDonViDo().toLowerCase().contains(searchText.toLowerCase())) ||
                    (String.valueOf(nl.getGia()) != null && String.valueOf(nl.getGia()).contains(searchText))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    nlSearch.add(nl);
                }
            }
            return nlSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin nguyên liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findNguyenLieu(searchText).isEmpty()) {
                for(NguyenLieuDTO emp: findNguyenLieu(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaNguyenLieu(),
                        emp.getTenNguyenLieu(),
                        emp.getLoaiNguyenLieu(),
                        emp.getNgayNhap(),
                        emp.getNgayHetHan(),
                        emp.getSoLuong(),
                        emp.getDonViDo()
                    });
                }
            }
            else {
                refreshTableData(table);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            refreshTableData(table);
        }
    }
}