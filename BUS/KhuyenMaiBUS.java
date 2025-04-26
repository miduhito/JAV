package BUS;

import DAO.KhuyenMaiDAO;
import DTO.KhuyenMaiDTO;
import DTO.PhieuNhapDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class KhuyenMaiBUS implements BUS_Interface<KhuyenMaiDTO> {
    ArrayList<KhuyenMaiDTO> danhSachKhuyenMai;
    KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();

    public KhuyenMaiBUS(){
        khuyenMaiDAO = new KhuyenMaiDAO();
        danhSachKhuyenMai = new ArrayList<>();
    }
    @Override
    public ArrayList<KhuyenMaiDTO> getData() {
        danhSachKhuyenMai = new ArrayList<>();
        danhSachKhuyenMai = khuyenMaiDAO.getData();
        return danhSachKhuyenMai;
    }

    @Override
    public KhuyenMaiDTO getDataById(String id) {
        return khuyenMaiDAO.getDataById(id);
    }

    @Override
    public boolean add(KhuyenMaiDTO entity) {
        if (regexInput(entity)) {
            return khuyenMaiDAO.add(entity);
        }
        return false;
    }

    @Override
    public boolean update(KhuyenMaiDTO entity) {
        if (regexInput(entity)) {
            return khuyenMaiDAO.update(entity);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return khuyenMaiDAO.delete(id);
    }

    @Override
    public boolean hide(String id) {
        return khuyenMaiDAO.hide(id);
    }

    @Override
    public boolean regexInput(KhuyenMaiDTO entity) {
        // ngày bắt đầu không được nhỏ hơn ngày kết thúc
        if (entity.getNgayBatDau() == null || entity.getNgayKetThuc() == null) {
            JOptionPane.showMessageDialog(null,
                    "Ngày bắt đầu và ngày kết thúc không được để trống!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (entity.getNgayBatDau().after(entity.getNgayKetThuc())) {
            JOptionPane.showMessageDialog(null,
                    "Ngày bắt đầu không được lớn hơn ngày kết thúc!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // ngày kết thúc không được nhỏ hơn ngày hiện tại
        Date currentDate = new Date();
        if (entity.getNgayKetThuc().before(currentDate)) {
            JOptionPane.showMessageDialog(null,
                    "Ngày kết thúc không được nhỏ hơn hoặc bằng ngày hiện tại!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public String generateID() {
        return khuyenMaiDAO.generateID();
    }

    public void loadTableData(DefaultTableModel tableModel) {
        ArrayList<KhuyenMaiDTO> danhSachKhuyenMai = getData();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (danhSachKhuyenMai != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (KhuyenMaiDTO khuyenMai : danhSachKhuyenMai) {
                Object[] row = {
                        khuyenMai.getMaKhuyenMai(),
                        khuyenMai.getTenKhuyenMai(),
                        dateFormat.format(khuyenMai.getNgayBatDau()),
                        dateFormat.format(khuyenMai.getNgayKetThuc()),
                        Objects.equals(khuyenMai.getDonViKhuyenMai(), "Phần trăm") ? "%" : "VNĐ" ,
                        khuyenMai.getDieuKienApDung()
                };
                tableModel.addRow(row);
            }
        }
    }

    public String getDonViKhuyenMai(String maKhuyenMai) {
        return khuyenMaiDAO.getDonViKhuyenMai(maKhuyenMai);
    }

    public ArrayList<KhuyenMaiDTO> advancedSearch(String maKhuyenMai, String tenKhuyenMai, Date startDate, Date endDate, String donVi) {
        return khuyenMaiDAO.advancedSearch(maKhuyenMai, tenKhuyenMai, startDate, endDate, donVi);
    }

    public List<KhuyenMaiDTO> findKhuyenMai(String searchText) {
        try {
            List<KhuyenMaiDTO> kmSearch = new ArrayList<>();
            // Lặp qua tất cả khuyến mãi từ khuyenMaiDAO
            for (KhuyenMaiDTO km : khuyenMaiDAO.getData()) {
                // Kiểm tra từng thuộc tính của KhuyenMaiDTO
                if ((km.getMaKhuyenMai() != null && km.getMaKhuyenMai().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getTenKhuyenMai() != null && km.getTenKhuyenMai().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getMoTaKhuyenMai() != null && km.getMoTaKhuyenMai().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getDonViKhuyenMai() != null && km.getDonViKhuyenMai().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getDieuKienApDung() != null && km.getDieuKienApDung().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getNgayBatDau() != null && km.getNgayBatDau().toString().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getNgayKetThuc() != null && km.getNgayKetThuc().toString().toLowerCase().contains(searchText.toLowerCase())) ||
                    (km.getTrangThai() != null && km.getTrangThai().toString().toLowerCase().contains(searchText.toLowerCase()))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    kmSearch.add(km);
                }
            }
            return kmSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin của tất cả khuyến mãi", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }
}
