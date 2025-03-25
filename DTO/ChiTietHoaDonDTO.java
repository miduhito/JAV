package DTO;

public class ChiTietHoaDonDTO {
    private String maHoaDon;
    private String maThucAn;
    private int soLuongBan;
    private Double thanhTien;

    public ChiTietHoaDonDTO() {}
    public ChiTietHoaDonDTO(String maHoaDon, String maThucAn, int soLuongBan, Double thanhTien) {
        this.maHoaDon = maHoaDon;
        this.maThucAn = maThucAn;
        this.soLuongBan = soLuongBan;
        this.thanhTien = thanhTien;
    }

    public String getMaHoaDon() {
        return this.maHoaDon;
    }
    
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    public String getMaThucAn() {
        return this.maThucAn;
    }
    
    public void setMaThucAn(String maThucAn) {
        this.maThucAn = maThucAn;
    }
    
    public int getSoLuongBan() {
        return this.soLuongBan;
    }
    
    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
    
    public Double getThanhTien() {
        return this.thanhTien;
    }
    
    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }    
}
