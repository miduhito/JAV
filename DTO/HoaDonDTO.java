package DTO;

public class HoaDonDTO {
    private String maHoaDon;
    private String ngayLap;
    private String maNhanVien;
    private String maKhachHang;
    private Double tongTien;
    private String pttt;

    public HoaDonDTO() {}
    public HoaDonDTO(String maHoaDon, String ngayLap, String maNhanVien, String maKhachHang, Double tongTien, String pttt) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.tongTien = tongTien;
        this.pttt = pttt;
    }

    public String getMaHoaDon() {
        return this.maHoaDon;
    }
    
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    public String getNgayLap() {
        return this.ngayLap;
    }
    
    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }
    
    public String getMaNhanVien() {
        return this.maNhanVien;
    }
    
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    
    public String getMaKhachHang() {
        return this.maKhachHang;
    }
    
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    
    public Double getTongTien() {
        return this.tongTien;
    }
    
    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }    

    public String getPTTT() {
        return this.pttt;
    }

    public void setPTTT(String pttt) {
        this.pttt = pttt;
    }
}
