package BUS;

import DAO.HoaDonDAO;
import DAO.ChiTietHoaDonDAO;
import DAO.ThucAnDAO;
import DTO.ChiTietHoaDonDTO;
import DTO.ChiTietKhuyenMaiDTO;
import DTO.KhuyenMaiDTO;
import DTO.NhanVienDTO;
import DTO.ThucAnDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class ThanhToanBUS {
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
    private ThucAnDAO thucAnDAO;
    private ChiTietKhuyenMaiBUS chiTietKhuyenMaiBUS;
    private KhuyenMaiBUS khuyenMaiBUS;
    private NhanVienBUS nhanVienBUS;

    public ThanhToanBUS() {
        hoaDonDAO = new HoaDonDAO();
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
        thucAnDAO = new ThucAnDAO();
        chiTietKhuyenMaiBUS = new ChiTietKhuyenMaiBUS();
        khuyenMaiBUS = new KhuyenMaiBUS();
        nhanVienBUS = new NhanVienBUS();
    }

    // Hàm khởi tạo mã hóa đơn tự động
    public String generateMaHoaDon() {
        return hoaDonDAO.generateMaHoaDon(); 
    }

    public String getTodayDate() {
        // Lấy ngày hôm nay
        LocalDate today = LocalDate.now();

        // Định dạng ngày theo kiểu dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Trả về ngày đã định dạng
        return today.format(formatter);
    }

    public static String getCurrentTime() {
        // Lấy thời gian hiện tại
        LocalTime now = LocalTime.now();

        // Định dạng thời gian theo kiểu hh:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Trả về thời gian đã định dạng
        return now.format(formatter);
    }


    // Lấy danh sách thức ăn từ cơ sở dữ liệu
    public List<ThucAnDTO> getDanhSachThucAn() {
        return thucAnDAO.getAllThucAn();
    }

    // Thêm chi tiết hóa đơn vào danh sách
    public void themChiTietHoaDon(ThucAnDTO thucAn, List<ChiTietHoaDonDTO> danhSachChiTietHoaDon) {
        // Kiểm tra nếu thức ăn đã có trong danh sách thanh toán
        for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
            if (chiTiet.getMaThucAn().equals(thucAn.getMaThucAn())) {
                chiTiet.setSoLuongBan(chiTiet.getSoLuongBan() + 1); // Tăng số lượng bán
                chiTiet.setThanhTien(chiTiet.getSoLuongBan() * thucAn.getGia()); // Tính lại thành tiền
                return;
            }
        }

        // Nếu chưa có, tạo mới chi tiết hóa đơn
        ChiTietHoaDonDTO chiTietMoi = new ChiTietHoaDonDTO();
        chiTietMoi.setMaThucAn(thucAn.getMaThucAn());
        chiTietMoi.setSoLuongBan(1); // Mặc định là 1
        chiTietMoi.setThanhTien(thucAn.getGia());
        danhSachChiTietHoaDon.add(chiTietMoi);
    }

    // Lưu thông tin hóa đơn và chi tiết hóa đơn vào cơ sở dữ liệu
    public void luuHoaDon(String maHoaDon, String maNhanVien, String sdtKhachHang, String ngayLap, double tongTien, List<ChiTietHoaDonDTO> danhSachChiTietHoaDon, String pttt) {
        try {
            // Lưu hóa đơn
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dbFormat.format(inputFormat.parse(ngayLap));
            hoaDonDAO.insertHoaDon(maHoaDon, formattedDate, maNhanVien, sdtKhachHang, tongTien,pttt);

            // Lưu từng chi tiết hóa đơn
            for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                chiTiet.setMaHoaDon(maHoaDon); // Gắn mã hóa đơn cho chi tiết
                chiTietHoaDonDAO.insertChiTietHoaDon(chiTiet.getMaHoaDon(), chiTiet.getMaThucAn(), chiTiet.getSoLuongBan(), chiTiet.getThanhTien());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Lấy thông tin 
    public List<String> getNhanVien() {
        List<String> nhanVienList = new ArrayList<>(); // Khởi tạo danh sách trống mặc định
        try {
            String today = getTodayDate();
    
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayFormattedDate = dbFormat.format(inputFormat.parse(today));
    
            nhanVienList = hoaDonDAO.getNhanVien(todayFormattedDate, getCurrentTime());
            if (nhanVienList == null || nhanVienList.isEmpty()) {
                nhanVienList = hoaDonDAO.getAllNhanVienInShift(todayFormattedDate, getCurrentTime());
            } 
        } catch (Exception ex) {
            // Ghi lỗi vào console
            System.err.println("Có lỗi xảy ra khi lấy danh sách nhân viên: " + ex.getMessage());
            ex.printStackTrace();
    
            // Xử lý lỗi: Trả về danh sách trống
        }
        return nhanVienList; // Trả về danh sách (dù có lỗi sẽ trả về danh sách trống)
    }

    public void loadMaNhanVienToBox(JComboBox<String> box) {
        for (NhanVienDTO nv : nhanVienBUS.getAllNhanVien()) {
            if(nv.getTenChucVu().equals("Cashier")) {
                box.addItem(nv.getMaNhanVien());
            }
        }
    }

    public void loadKhuyenMaiTable(DefaultTableModel promotionsModel, List<String> selectedFoodList) {
        promotionsModel.setRowCount(0);
        List<KhuyenMaiDTO> danhSachKhuyenMai = khuyenMaiBUS.getData();

        if(danhSachKhuyenMai != null && !selectedFoodList.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for(KhuyenMaiDTO km: danhSachKhuyenMai) {
                for(ChiTietKhuyenMaiDTO item: chiTietKhuyenMaiBUS.getDataById(km.getMaKhuyenMai())) {
                    if(selectedFoodList.contains(item.getMaThucAn())) {
                        Object[] row = {
                            km.getMaKhuyenMai(),
                            km.getTenKhuyenMai(),
                            dateFormat.format(km.getNgayBatDau()),
                            dateFormat.format(km.getNgayKetThuc()),
                            Objects.equals(km.getDonViKhuyenMai(), "Phần trăm") ? "%" : "VNĐ" ,
                            km.getDieuKienApDung()
                        };
                        promotionsModel.addRow(row);
                        break;
                    }
                }
            }
        }
    }
}