package BUS;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private KhachHangBUS khBUS = new KhachHangBUS();

    public HoaDonDTO getHoaDonById(String maHoaDon) {
        return hoaDonDAO.getHoaDonById(maHoaDon);
    }

    public List<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public void insertHoaDon(String maHoaDon, String ngayLap, String maNhanVien, String sdtKhachHang, Double tongTien, String pttt) {
        hoaDonDAO.insertHoaDon(maHoaDon, ngayLap, maNhanVien, sdtKhachHang, tongTien, pttt);
    }

    public void updateHoaDon(String maHoaDon, String ngayLap, String maNhanVien, String sdtKhachHang, Double tongTien, String pttt) {
        hoaDonDAO.updateHoaDon(maHoaDon, ngayLap, maNhanVien, sdtKhachHang, tongTien, pttt);
    }

    public void deleteHoaDon(String maHoaDon) {
        hoaDonDAO.deleteHoaDon(maHoaDon);
    }

    public List<HoaDonDTO> findHoaDon(String searchText) {
        try {
            List<HoaDonDTO> hdSearch = new ArrayList<>();
            // Lặp qua tất cả nhân viên từ nhanVienDAO
            for (HoaDonDTO hd : hoaDonDAO.getAllHoaDon()) {
                // Kiểm tra từng thuộc tính của NhanVienDTO
                if ((hd.getMaHoaDon() != null && hd.getMaHoaDon().toLowerCase().contains(searchText.toLowerCase())) ||
                    (hd.getMaKhachHang() != null && hd.getMaKhachHang().toLowerCase().contains(searchText.toLowerCase())) ||
                    (hd.getMaNhanVien() != null && hd.getMaNhanVien().toLowerCase().contains(searchText.toLowerCase())) ||
                    (hd.getNgayLap() != null && hd.getNgayLap().toLowerCase().contains(searchText.toLowerCase())) ||
                    (hd.getPTTT() != null && hd.getPTTT().toLowerCase().contains(searchText.toLowerCase())) ||
                    (hd.getTongTien() != null && String.valueOf(hd.getTongTien()).toLowerCase().contains(searchText.toLowerCase()))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    hdSearch.add(hd);
                }
            }
            return hdSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin của tất cả hóa đơn", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void searchTable(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findHoaDon(searchText).isEmpty()) {
                for(HoaDonDTO emp: findHoaDon(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaHoaDon(),
                        emp.getMaNhanVien(),
                        emp.getMaKhachHang(),
                        emp.getNgayLap(),
                        emp.getTongTien(),
                        emp.getPTTT()
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

    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu bảng cũ
        for (HoaDonDTO emp : hoaDonDAO.getAllHoaDon()) {
            String sdtKhach = "";
            System.out.println(emp.getMaKhachHang());
            if(emp.getMaKhachHang() != null) {
                sdtKhach = khBUS.getKhachHangById(emp.getMaKhachHang()).getSDT();
            }
            tableModel.addRow(new Object[]{
                    emp.getMaHoaDon(),
                    emp.getMaNhanVien(),
                    sdtKhach,
                    emp.getNgayLap(),
                    emp.getTongTien(),
                    emp.getPTTT()
            });
        }
        
    }
}
