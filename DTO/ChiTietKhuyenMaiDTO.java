package DTO;

public class ChiTietKhuyenMaiDTO {
    private String maKhuyenMai;
    private String maThucAn;
    private Double giaTriKhuyenMai;
    private Boolean trangThai;


    public ChiTietKhuyenMaiDTO() {}

    public ChiTietKhuyenMaiDTO(String maKhuyenMai, String maHoaDon, Double giaTriKhuyenMai, Boolean trangThai) {
        this.maKhuyenMai = maKhuyenMai;
        this.maThucAn = maHoaDon;
        this.giaTriKhuyenMai = giaTriKhuyenMai;
        this.trangThai = trangThai;
    }

    public String getMaKhuyenMai() {
        return this.maKhuyenMai;
    }
    
    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }
    
    public String getMaThucAn() {
        return this.maThucAn;
    }
    
    public void setMaThucAn(String maThucAn) {
        this.maThucAn = maThucAn;
    }

    public Double getGiaTriKhuyenMai() {
        return giaTriKhuyenMai;
    }

    public void setGiaTriKhuyenMai(Double giaTriKhuyenMai) {
        this.giaTriKhuyenMai = giaTriKhuyenMai;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
