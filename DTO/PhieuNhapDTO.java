package DTO;

public class PhieuNhapDTO {
    private String maPhieuNhap;
    private String ngayNhap;
    private String maNhanVien;

    public PhieuNhapDTO() {}
    public PhieuNhapDTO(String maPhieuNhap, String ngayNhap, String maNhanVien) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.maNhanVien = maNhanVien;
    }

    public String getMaPhieuNhap() {
        return this.maPhieuNhap;
    }
    
    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }
    
    public String getNgayNhap() {
        return this.ngayNhap;
    }
    
    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
    public String getMaNhanVien() {
        return this.maNhanVien;
    }
    
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }    
}
