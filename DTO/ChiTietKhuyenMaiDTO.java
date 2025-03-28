package DTO;

public class ChiTietKhuyenMaiDTO {
    private String maKhuyenMai;
    private String maSanPham;
    private Double giaTriKhuyenMai;


    public ChiTietKhuyenMaiDTO() {}
    public ChiTietKhuyenMaiDTO(String maKhuyenMai, String maHoaDon, Double  giaTriKhuyenMai) {
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

    public Double getGiaTriKhuyenMai() {
        return giaTriKhuyenMai;
    }

    public void setGiaTriKhuyenMai(Double giaTriKhuyenMai) {
        this.giaTriKhuyenMai = giaTriKhuyenMai;
    }
}
