package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    // Tạo mã khách hàng tự động (ví dụ: KH001, KH002,...)
    public String generateMaKhachHang() {
        return khachHangDAO.generateMaKhachHang();
    }

    public int generateSoDiem() {
        return 0;
    }

    // Cập nhật dữ liệu cho bảng hiển thị (nạp lại dữ liệu từ DB)
    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu bảng cũ
        try {
            for (KhachHangDTO emp : khachHangDAO.getAllKhachHang()) {
                tableModel.addRow(new Object[]{
                        emp.getMaKhachHang(),
                        emp.getTenKhachHang(),
                        emp.getEmail(),
                        emp.getSoDiem()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Thêm khách hàng vào DB
    public void insertKhachHang(String maKhachHang, String tenKhachHang, String gioiTinh, String sdt, String email,
                                String diaChi, int soDiem) {
        try {
            khachHangDAO.insertKhachHang(maKhachHang, tenKhachHang, gioiTinh, sdt, email, diaChi, soDiem);
            JOptionPane.showMessageDialog(null, "Đã thêm khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách hàng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cập nhật thông tin khách hàng
    public void updateKhachHang(String maKhachHang, String tenKhachHang, String gioiTinh, String SDT, String email,
                             String diaChi, int soDiem) {
        try {
            khachHangDAO.updateKhachHang(maKhachHang, tenKhachHang, gioiTinh, SDT, email, diaChi, soDiem);
            JOptionPane.showMessageDialog(null, "Đã cập nhật thông tin khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật thông tin!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa khách hàng
    public void deleteKhachHang(String maKhachHang) {
        try {
            khachHangDAO.deleteKhachHang(maKhachHang);
            JOptionPane.showMessageDialog(null, "Đã xóa khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa khách hàng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy thông tin khách hàng theo mã và trả về dưới dạng Map
    public KhachHangDTO getKhachHangById(String maKhachHang) {
        try {
            return khachHangDAO.getKhachHangById(maKhachHang);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin khách hàng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new KhachHangDTO();
        }
    }

    public List<KhachHangDTO> findKhachHang(String searchText) {
        try {
            List<KhachHangDTO> khSearch = new ArrayList<>();
            // Lấy tất cả khách hàng từ khachHangDAO
            for (KhachHangDTO kh : khachHangDAO.getAllKhachHang()) {
                // Kiểm tra các thuộc tính của KhachHangDTO
                if ((kh.getMaKhachHang() != null && kh.getMaKhachHang().toLowerCase().contains(searchText.toLowerCase())) ||
                    (kh.getTenKhachHang() != null && kh.getTenKhachHang().toLowerCase().contains(searchText.toLowerCase())) ||
                    (kh.getGioiTinh() != null && kh.getGioiTinh().toLowerCase().contains(searchText.toLowerCase())) ||
                    (kh.getSDT() != null && kh.getSDT().toLowerCase().contains(searchText.toLowerCase())) ||
                    (kh.getEmail() != null && kh.getEmail().toLowerCase().contains(searchText.toLowerCase())) ||
                    (kh.getDiaChi() != null && kh.getDiaChi().toLowerCase().contains(searchText.toLowerCase())) ||
                    (String.valueOf(kh.getSoDiem()).contains(searchText))) { // Tìm trong số điểm (int)
                    // Nếu bất kỳ thuộc tính nào chứa searchText, thêm vào danh sách kết quả
                    khSearch.add(kh);
                }
            }
            return khSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm thông tin khách hàng", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findKhachHang(searchText).isEmpty()) {
                for(KhachHangDTO emp: findKhachHang(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaKhachHang(),
                        emp.getTenKhachHang(),
                        emp.getEmail(),
                        emp.getSoDiem()
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

    public List<KhachHangDTO> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }
}
