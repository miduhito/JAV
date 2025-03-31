package BUS;

import DAO.ChiTietKhuyenMaiDAO;
import DTO.ChiTietKhuyenMaiDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ChiTietKhuyenMaiBUS{
    ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai;;
    ChiTietKhuyenMaiDAO chiTietKhuyenMaiDAO = new ChiTietKhuyenMaiDAO();
    KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
    ThucAnBUS thucAnBUS = new ThucAnBUS();

    
    public ArrayList<ChiTietKhuyenMaiDTO> getData(){
        danhSachChiTietKhuyenMai  = new ArrayList<>();
        danhSachChiTietKhuyenMai = chiTietKhuyenMaiDAO.getData();
        return danhSachChiTietKhuyenMai;
    }

    
    public ChiTietKhuyenMaiDTO getDataById(String id){
        return null;
    }

    public ArrayList<ChiTietKhuyenMaiDTO> getDataByIdSub(String id) {
        return chiTietKhuyenMaiDAO.getDataByIdSub(id);
    }

    
    public boolean add(ChiTietKhuyenMaiDTO entity) {
        if (!regexInput(entity)) {
            return false;
        }
        return chiTietKhuyenMaiDAO.add(entity);
    }

    
    public boolean update(ChiTietKhuyenMaiDTO entity) {
        if (!regexInput(entity)) {
            return false;
        }
        return chiTietKhuyenMaiDAO.update(entity);
    }

    
    public boolean delete(String id, String maThucAn) {
        return chiTietKhuyenMaiDAO.delete(id, maThucAn);
    }

    
    public boolean hide(String id) {
        return chiTietKhuyenMaiDAO.hide(id);
    }

    
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

    
    public String generateID() {
        return "";
    }

    public void loadDataTable(DefaultTableModel tableModel, String maKhuyenMai){
        ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai = getDataByIdSub(maKhuyenMai);
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (danhSachChiTietKhuyenMai != null) {
            for (ChiTietKhuyenMaiDTO chiTietKhuyenMai : danhSachChiTietKhuyenMai) {
                Object[] row = {
                        chiTietKhuyenMai.getMaThucAn(),
                        thucAnBUS.getDataById(chiTietKhuyenMai.getMaThucAn()).getTenThucAn(),
                        chiTietKhuyenMai.getGiaTriKhuyenMai()
                };
                tableModel.addRow(row);
            }
        }
    }


}
