package BUS;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.ChiTietHoaDonDAO;
import DTO.ChiTietHoaDonDTO;

public class ChiTietHoaDonBUS {
    ArrayList<ChiTietHoaDonDTO> danhSachChiTietHoaDon;
    ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    HoaDonBUS HoaDonBUS = new HoaDonBUS();
    ThucAnBUS thucAnBUS = new ThucAnBUS();

    public List<ChiTietHoaDonDTO> getAllChiTietHoaDon() {
        return chiTietHoaDonDAO.getAllChiTietHoaDon();
    }

    public void insertChiTietHoaDon(String maHoaDon, String maThucAn, int soLuongBan, Double thanhTien) {
        chiTietHoaDonDAO.insertChiTietHoaDon(maHoaDon, maThucAn, soLuongBan, soLuongBan);
    }

    public void updateChiTietHoaDon(String maHoaDon, String maThucAn, int soLuongBan, Double thanhTien) {
        chiTietHoaDonDAO.updateChiTietHoaDon(maHoaDon, maThucAn, soLuongBan, soLuongBan);
    }

    public void deleteChiTietHoaDon(String maHoaDon, String maThucAn) {
        chiTietHoaDonDAO.deleteChiTietHoaDon(maHoaDon, maThucAn);
    }

    public ChiTietHoaDonDTO getChiTietHoaDonById(String maHoaDon, String maThucAn) {
        return chiTietHoaDonDAO.getChiTietHoaDonById(maHoaDon, maThucAn);
    }

    public List<ChiTietHoaDonDTO> getAllChiTietHoaDon(String maHoaDon, String maThucAn) {
        return chiTietHoaDonDAO.getAllChiTietHoaDon();
    }

    public void refreshDetailTableData(DefaultTableModel table, String maHoaDon) {
        table.setRowCount(0); // Xóa dữ liệu bảng cũ
        try {
            for (ChiTietHoaDonDTO emp : chiTietHoaDonDAO.getAllChiTietHoaDon()) {
                if(emp.getMaHoaDon().equals(maHoaDon)) {
                    table.addRow(new Object[]{
                            emp.getMaThucAn(),
                            emp.getSoLuongBan(),
                            emp.getThanhTien()
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
