package DTO;

public class ChiTietPhieuNhapDTO {
    private String maPhieuNhap;
    private String maNguyenLieu;
    private Double giaNhap;
    private int soLuongNhap;
    private Double thanhTien;
    private String ghiChu;
    private boolean trangThai;

    public ChiTietPhieuNhapDTO() {}
    public ChiTietPhieuNhapDTO(String maPhieuNhap, int soLuongNhap, String maNguyenLieu, Double giaNhap, Double thanhTien, String ghiChu, boolean trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.soLuongNhap = soLuongNhap;
        this.maNguyenLieu = maNguyenLieu;
        this.giaNhap = giaNhap;
        this.thanhTien = thanhTien;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public String getMaPhieuNhap() {
        return this.maPhieuNhap;
    }
    
    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }
    
    public int getSoLuongNhap() {
        return this.soLuongNhap;
    }
    
    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }
    
    public String getMaNguyenLieu() {
        return this.maNguyenLieu;
    }
    
    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }
    
    public Double getGiaNhap() {
        return this.giaNhap;
    }
    
    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }
    
    public Double getThanhTien() {
        return this.thanhTien;
    }
    
    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
