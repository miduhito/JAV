package DTO;

public class ChiTietCongThucDTO {
    private String maCongThuc;
    private String maNguyenLieu;
    private Double soLuong;
    private String donViTinh;

    public ChiTietCongThucDTO() {}
    public ChiTietCongThucDTO(String maCongThuc, String maNguyenLieu, Double soLuong, String donViTinh) {
        this.maCongThuc = maCongThuc;
        this.maNguyenLieu = maNguyenLieu;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
    }

    public String getMaCongThuc() {
        return this.maCongThuc;
    }
    
    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }
    
    public String getMaNguyenLieu() {
        return this.maNguyenLieu;
    }
    
    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }
    
    public Double getSoLuong() {
        return this.soLuong;
    }
    
    public void setSoLuong(Double soLuong) {
        this.soLuong = soLuong;
    }
    
    public String getDonViTinh() {
        return this.donViTinh;
    }
    
    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }    
}
