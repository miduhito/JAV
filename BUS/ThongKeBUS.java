package BUS;

import DAO.HoaDonDAO;
import DAO.ChiTietHoaDonDAO;
import DTO.ThongKeDTO;

import java.time.LocalDate; // [THÊM]
import java.time.format.DateTimeFormatter; // [THÊM]
import java.time.temporal.ChronoUnit; // [THÊM]
import java.util.List;

public class ThongKeBUS {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ThongKeBUS() {
        hoaDonDAO = new HoaDonDAO();
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    // [SỬA] Đã xóa tham số 'thoiGian' và sửa tên tham số ngày
    public List<ThongKeDTO> getThongKeData(String loaiThongKe, String startDateStr, String endDateStr) {
        
        // [THÊM] Logic tự động quyết định cách nhóm
        String groupingMode = "Ngày"; // Mặc định là "Ngày"
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

            if (daysBetween > 730) {
                groupingMode = "Năm"; // Hơn 2 năm
            } else if (daysBetween > 90) { 
                groupingMode = "Tháng"; // Hơn 3 tháng (90 ngày)
            } else if (daysBetween > 31) {
                groupingMode = "Tuần"; // Hơn 1 tháng (31 ngày)
            }
            // Nếu ít hơn hoặc bằng 31 ngày, giữ nguyên "Ngày"
            
        } catch (Exception e) {
            e.printStackTrace();
            // Giữ mặc định là "Ngày" nếu có lỗi parse
        }

        // [SỬA] Truyền groupingMode (thay vì thoiGian) vào DAO
        switch (loaiThongKe) {
            case "Doanh thu":
                return hoaDonDAO.getDoanhThuTheoKhoangThoiGian(groupingMode, startDateStr, endDateStr);
            case "Số lượng hóa đơn":
                return hoaDonDAO.getHoaDonTheoKhoangThoiGian(groupingMode, startDateStr, endDateStr);
            case "Số lượng khách hàng":
                return hoaDonDAO.getKhachHangTheoKhoangThoiGian(groupingMode, startDateStr, endDateStr);
            case "Sản phẩm bán chạy":
                // "Sản phẩm bán chạy" không cần nhóm, nó chỉ lọc theo ngày
                return chiTietHoaDonDAO.getSanPhamBanChay(startDateStr, endDateStr);
            default:
                return List.of();
        }
    }
}