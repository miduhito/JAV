package DTO;

import java.util.Date;

public class NguyenLieuDTO {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String loaiNguyenLieu;
    private Date ngayNhap;
    private Date ngayHetHan;
    private Double soLuong;
    private String donViDo;

    public NguyenLieuDTO() {}
    public NguyenLieuDTO(String maNguyenLieu, String tenNguyenLieu, String loaiNguyenLieu, Date ngayNhap, Date ngayHetHan, Double soLuong, String donViDo) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.loaiNguyenLieu = loaiNguyenLieu;
        this.ngayNhap = ngayNhap;
        this.ngayHetHan = ngayHetHan;
        this.soLuong = soLuong;
        this.donViDo = donViDo;
    }

    public String getMaNguyenLieu() {
        return this.maNguyenLieu;
    }
    
    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }
    
    public String getTenNguyenLieu() {
        return this.tenNguyenLieu;
    }
    
    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }
    
    public String getLoaiNguyenLieu() {
        return this.loaiNguyenLieu;
    }
    
    public void setLoaiNguyenLieu(String loaiNguyenLieu) {
        this.loaiNguyenLieu = loaiNguyenLieu;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public Double getSoLuong() {
        return this.soLuong;
    }
    
    public void setSoLuong(Double soLuong) {
        this.soLuong = soLuong;
    }
    
    public String getDonViDo() {
        return this.donViDo;
    }
    
    public void setDonViDo(String donViDo) {
        this.donViDo = donViDo;
    }

    @Override
    public String toString() {
        return "NguyenLieuDTO{" +
                "maNguyenLieu='" + maNguyenLieu + '\'' +
                ", tenNguyenLieu='" + tenNguyenLieu + '\'' +
                ", loaiNguyenLieu='" + loaiNguyenLieu + '\'' +
                ", ngayNhap='" + ngayNhap + '\'' +
                ", ngayHetHan='" + ngayHetHan + '\'' +
                ", soLuong=" + soLuong +
                ", donViDo='" + donViDo + '\'' +
                '}';
    }
}
