package DTO;

public class NguyenLieuDTO {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String loaiNguyenLieu;
    private String ngayNhap;
    private String ngayHetHan;
    private Double soLuong;
    private String donViDo;

    public NguyenLieuDTO() {}
    public NguyenLieuDTO(String maNguyenLieu, String tenNguyenLieu, String loaiNguyenLieu, String ngayNhap, String ngayHetHan, Double soLuong, String donViDo) {
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
    
    public String getNgayNhap() {
        return this.ngayNhap;
    }
    
    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
    public String getNgayHetHan() {
        return this.ngayHetHan;
    }
    
    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
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
}
