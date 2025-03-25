package DTO;

public class ChiTietKhuyenMaiDTO {
    private String maKhuyenMai;
    private String maHoaDon;

    public ChiTietKhuyenMaiDTO() {}
    public ChiTietKhuyenMaiDTO(String maKhuyenMai, String maHoaDon) {
        this.maKhuyenMai = maKhuyenMai;
        this.maHoaDon = maHoaDon;
    }

    public String getMaKhuyenMai() {
        return this.maKhuyenMai;
    }
    
    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }
    
    public String getMaHoaDon() {
        return this.maHoaDon;
    }
    
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }    
}
