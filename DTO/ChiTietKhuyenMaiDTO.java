package DTO;

public class ChiTietKhuyenMaiDTO {
    private String maKhuyenMai;
    private String maSanPham;
    private Float giaTriKhuyenMai;


    public ChiTietKhuyenMaiDTO() {}
    public ChiTietKhuyenMaiDTO(String maKhuyenMai, String maHoaDon, Float giaTriKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
        this.maSanPham = maHoaDon;
        this.giaTriKhuyenMai = giaTriKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return this.maKhuyenMai;
    }
    
    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }
    
    public String getMaSanPham() {
        return this.maSanPham;
    }
    
    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public Float getGiaTriKhuyenMai() {
        return giaTriKhuyenMai;
    }

    public void setGiaTriKhuyenMai(Float giaTriKhuyenMai) {
        this.giaTriKhuyenMai = giaTriKhuyenMai;
    }
}
