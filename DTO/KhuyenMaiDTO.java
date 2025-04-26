package DTO;

import java.util.Date;

public class KhuyenMaiDTO {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private String moTaKhuyenMai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String donViKhuyenMai;
    private String dieuKienApDung;
    private Boolean trangThai;

    public KhuyenMaiDTO() {}
    public KhuyenMaiDTO(String maKhuyenMai, String tenKhuyenMai, String moTaKhuyenMai, Date ngayBatDau, Date ngayKetThuc, String donViKhuyenMai, String dieuKienApDung, boolean trangThai) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.moTaKhuyenMai = moTaKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.donViKhuyenMai = donViKhuyenMai;
        this.dieuKienApDung = dieuKienApDung;
        this.trangThai = trangThai;
    }

    public String getDonViKhuyenMai() {
        return donViKhuyenMai;
    }

    public void setDonViKhuyenMai(String donViKhuyenMai) {
        this.donViKhuyenMai = donViKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return this.maKhuyenMai;
    }
    
    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }
    
    public String getTenKhuyenMai() {
        return this.tenKhuyenMai;
    }
    
    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }
    
    public String getMoTaKhuyenMai() {
        return this.moTaKhuyenMai;
    }
    
    public void setMoTaKhuyenMai(String moTaKhuyenMai) {
        this.moTaKhuyenMai = moTaKhuyenMai;
    }
    
    public Date getNgayBatDau() {
        return this.ngayBatDau;
    }
    
    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    
    public Date getNgayKetThuc() {
        return this.ngayKetThuc;
    }
    
    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    public String getDieuKienApDung() {
        return this.dieuKienApDung;
    }
    
    public void setDieuKienApDung(String dieuKienApDung) {
        this.dieuKienApDung = dieuKienApDung;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
