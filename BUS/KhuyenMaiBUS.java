package BUS;

import DAO.KhuyenMaiDAO;
import DTO.KhuyenMaiDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // tên khuyến mãi không được chỉ chứa số
        if (entity.getTenKhuyenMai() == null || entity.getTenKhuyenMai().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Tên khuyến mãi không được để trống!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // kiểm tra nếu tên chỉ chứa số (không hợp lệ)
        if (Pattern.matches("^[0-9]+$", entity.getTenKhuyenMai().trim())) {
            JOptionPane.showMessageDialog(null,
                    "Tên khuyến mãi không được chỉ chứa số!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

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
        if (entity.getDieuKienApDung() == null || entity.getDieuKienApDung().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng nhập mô tả điều kiện áp dụng!",
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
                        khuyenMai.getDieuKienApDung()
                };
                tableModel.addRow(row);
            }
        }
    }

    public String getDonViKhuyenMai(String maKhuyenMai) {
        return khuyenMaiDAO.getDonViKhuyenMai(maKhuyenMai);
    }
}
