package BUS;

import DAO.ChiTietKhuyenMaiDAO;
import DTO.ChiTietKhuyenMaiDTO;
import Interface.BUS_SubInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Objects;

public class ChiTietKhuyenMaiBUS implements BUS_SubInterface<ChiTietKhuyenMaiDTO> {
    ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai;;
    ChiTietKhuyenMaiDAO chiTietKhuyenMaiDAO = new ChiTietKhuyenMaiDAO();
    KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
    ThucAnBUS thucAnBUS = new ThucAnBUS();

    @Override
    public ArrayList<ChiTietKhuyenMaiDTO> getData(){
        danhSachChiTietKhuyenMai  = new ArrayList<>();
        danhSachChiTietKhuyenMai = chiTietKhuyenMaiDAO.getData();
        return danhSachChiTietKhuyenMai;
    }

    @Override
    public ArrayList<ChiTietKhuyenMaiDTO> getDataById(String id) {
        return chiTietKhuyenMaiDAO.getDataById(id);
    }

    @Override
    public boolean add(ChiTietKhuyenMaiDTO entity) {
        if (!regexInput(entity)) {
            return false;
        }
        return chiTietKhuyenMaiDAO.add(entity);
    }

    @Override
    public boolean update(ChiTietKhuyenMaiDTO entity) {
        if (!regexInput(entity)) {
            return false;
        }
        return chiTietKhuyenMaiDAO.update(entity);
    }

    @Override
    public boolean delete(String id, String maThucAn) {
        return chiTietKhuyenMaiDAO.delete(id, maThucAn);
    }

    @Override
    public boolean hide(String id, String maThucAn) {
        return chiTietKhuyenMaiDAO.hide(id, maThucAn);
    }

    @Override
    public boolean regexInput(ChiTietKhuyenMaiDTO entity) {
        String donViKhuyenMai = khuyenMaiBUS.getDonViKhuyenMai(entity.getMaKhuyenMai());
        if (donViKhuyenMai == null) {
            System.out.println("Không tìm thấy mã khuyến mãi: " + entity.getMaKhuyenMai());
            return false;
        }

        double giaTriKhuyenMai = entity.getGiaTriKhuyenMai();

        if (entity.getMaThucAn() == null || entity.getMaThucAn().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn thức ăn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (entity.getGiaTriKhuyenMai() == 0) {
            JOptionPane.showMessageDialog(null, "Giá trị khuyến mãi không được bằng 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (donViKhuyenMai.equals("Phần trăm")) {
            if (giaTriKhuyenMai > 100) {
                JOptionPane.showMessageDialog(null,"Giá trị khuyến mãi không hợp lệ: Phần trăm không được lớn hơn 100.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } else if (donViKhuyenMai.equals("Số tiền")) {
            if (giaTriKhuyenMai > 100000) {
                JOptionPane.showMessageDialog(null, "Giá trị khuyến mãi không hợp lệ: Số tiền không được lớn hơn 100,000 VNĐ.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } else {
            System.out.println("Đơn vị khuyến mãi không hợp lệ: " + donViKhuyenMai);
            return false;
        }
    }

    public void loadDataTable(DefaultTableModel tableModel, String maKhuyenMai){
        ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai = getDataById(maKhuyenMai);
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (danhSachChiTietKhuyenMai != null) {
            System.out.println(khuyenMaiBUS.getDataById(maKhuyenMai).getDonViKhuyenMai());
            for (ChiTietKhuyenMaiDTO chiTietKhuyenMai : danhSachChiTietKhuyenMai) {
                Object[] row = {
                        chiTietKhuyenMai.getMaThucAn(),
                        thucAnBUS.getDataById(chiTietKhuyenMai.getMaThucAn()).getTenThucAn(),
                        chiTietKhuyenMai.getGiaTriKhuyenMai(),
                        Objects.equals(khuyenMaiBUS.getDataById(maKhuyenMai).getDonViKhuyenMai(), "Phần trăm") ? "%" : "VNĐ"
                };
                tableModel.addRow(row);
            }
        }
    }


}
