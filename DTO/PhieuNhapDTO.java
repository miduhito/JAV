package DTO;

import java.util.Date;

public class PhieuNhapDTO {
    private String maPhieuNhap;
    private Date ngayNhap;
    private String maNhanVien;
    private String maNhaCungCap;
    private double tongTien;
    private boolean trangThai;

    public PhieuNhapDTO(){}

    public PhieuNhapDTO(String maPhieuNhap, String maNhanVien, Date ngayNhap, String maNhaCungCap, double tongTien, boolean trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNhanVien = maNhanVien;
        this.ngayNhap = ngayNhap;
        this.maNhaCungCap = maNhaCungCap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
