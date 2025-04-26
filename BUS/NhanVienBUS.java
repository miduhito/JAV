package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class NhanVienBUS {

    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();

    // Tạo mã nhân viên tự động (ví dụ: NV001, NV002,...)
    public String generateMaNhanVien() {
        return nhanVienDAO.generateMaNhanVien();
    }

    // Cập nhật dữ liệu cho bảng hiển thị (nạp lại dữ liệu từ DB)
    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu bảng cũ
        try {
            for (NhanVienDTO emp : nhanVienDAO.getAllNhanVien()) {
                tableModel.addRow(new Object[]{
                        emp.getMaNhanVien(),
                        emp.getTenNhanVien(),
                        emp.getTenChucVu(),
                        emp.getTrangThai()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load danh sách chức vụ từ DB vào JComboBox
    public void loadChucVuToBox(JComboBox<String> box) {
        Map<String, String> chucVuMap = nhanVienDAO.getAllChucVu();
        for (String tenChucVu : chucVuMap.keySet()) {
            box.addItem(tenChucVu);
        }
    }

    // Thêm nhân viên vào DB
    public void insertNhanVien(String maNhanVien, String tenNhanVien, String sdt, String email, String ngaySinh,
                               String gioiTinh, String diaChi, String trangThai, String tenChucVu) {
        try {
            Map<String, String> chucVuMap = nhanVienDAO.getAllChucVu();
            String maChucVu = chucVuMap.get(tenChucVu);
            if (maChucVu == null) {
                throw new IllegalArgumentException("Chức vụ không tồn tại: " + tenChucVu);
            }

            nhanVienDAO.insertNhanVien(maNhanVien, tenNhanVien, sdt, email, ngaySinh, gioiTinh, diaChi, trangThai, maChucVu);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cập nhật thông tin nhân viên
    public void updateNhanVien(String maNhanVien, String tenNhanVien, String SDT, String email, String ngaySinh,
                               String gioiTinh, String diaChi, String trangThai, String tenChucVu) {
        try {
            Map<String, String> chucVuMap = nhanVienDAO.getAllChucVu();
            String maChucVu = chucVuMap.get(tenChucVu);
            if (maChucVu == null) {
                throw new IllegalArgumentException("Chức vụ không tồn tại: " + tenChucVu);
            }

            nhanVienDAO.updateNhanVien(maNhanVien, tenNhanVien, SDT, email, ngaySinh, gioiTinh, diaChi, trangThai, maChucVu);
            JOptionPane.showMessageDialog(null, "Đã cập nhật thông tin nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật thông tin!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa nhân viên
    public void deleteNhanVien(String maNhanVien) {
        try {
            nhanVienDAO.deleteNhanVien(maNhanVien);
            JOptionPane.showMessageDialog(null, "Đã xóa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy thông tin nhân viên theo mã và trả về dưới dạng Map
    public NhanVienDTO getNhanVienById(String maNhanVien) {
        try {
            return nhanVienDAO.getNhanVienById(maNhanVien);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin nhân viên!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new NhanVienDTO();
        }
    }

    // Lấy tên chức vụ từ mã chức vụ
    public String getTenChucVu(String maChucVu) {
        try {
            return nhanVienDAO.getTenChucVu(maChucVu);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin chức vụ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    public List<NhanVienDTO> findNhanVien(String searchText) {
        try {
            List<NhanVienDTO> nvSearch = new ArrayList<>();
            // Lặp qua tất cả nhân viên từ nhanVienDAO
            for (NhanVienDTO nv : nhanVienDAO.getAllNhanVien()) {
                // Kiểm tra từng thuộc tính của NhanVienDTO
                if ((nv.getMaNhanVien() != null && nv.getMaNhanVien().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getTenNhanVien() != null && nv.getTenNhanVien().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getSDT() != null && nv.getSDT().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getEmail() != null && nv.getEmail().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getNgaySinh() != null && nv.getNgaySinh().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getGioiTinh() != null && nv.getGioiTinh().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getDiaChi() != null && nv.getDiaChi().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getTrangThai() != null && nv.getTrangThai().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getTenChucVu() != null && nv.getTenChucVu().toLowerCase().contains(searchText.toLowerCase())) ||
                    (nv.getTenDangNhap() != null && nv.getTenDangNhap().toLowerCase().contains(searchText.toLowerCase()))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    nvSearch.add(nv);
                }
            }
            return nvSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin của tất cả nhân viên", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findNhanVien(searchText).isEmpty()) {
                for(NhanVienDTO emp: findNhanVien(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaNhanVien(),
                        emp.getTenNhanVien(),
                        emp.getTenChucVu(),
                        emp.getTrangThai()
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

    public List<NhanVienDTO> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }
}