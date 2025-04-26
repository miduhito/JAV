package BUS;

import DAO.HoaDonDAO;
import DAO.ChiTietHoaDonDAO;
import DTO.ThongKeDTO;

import java.util.List;

public class ThongKeBUS {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ThongKeBUS() {
        hoaDonDAO = new HoaDonDAO();
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    public List<ThongKeDTO> getThongKeData(String loaiThongKe, String thoiGian, String startDate, String endDate) {
        switch (loaiThongKe) {
            case "Doanh thu":
                return hoaDonDAO.getDoanhThuTheoKhoangThoiGian(thoiGian, startDate, endDate);
            case "Số lượng hóa đơn":
                return hoaDonDAO.getHoaDonTheoKhoangThoiGian(thoiGian, startDate, endDate);
            case "Số lượng khách hàng":
                return hoaDonDAO.getKhachHangTheoKhoangThoiGian(thoiGian, startDate, endDate);
            case "Sản phẩm bán chạy":
                return chiTietHoaDonDAO.getSanPhamBanChay(startDate, endDate);
            default:
                return List.of();
        }
    }
}