package DTO;

public class ChucVuDTO {
    private String maChucVu;
    private String tenChucVu;
    private String trangThai;
    private double luongTheoGio;

    public ChucVuDTO() {}
    public ChucVuDTO(String maChucVu, String tenChucVu, String trangThai, double luongTheoGio) {
        this.maChucVu = maChucVu;
        this.tenChucVu = tenChucVu;
        this.trangThai = trangThai;
        this.luongTheoGio = luongTheoGio;
    }

    public String getMaChuVu() {
        return this.maChucVu;
    }
    
    public void setMaChuVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }
    
    public String getTenChucVu() {
        return this.tenChucVu;
    }
    
    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }
    
    public String getTrangThai() {
        return this.trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public double getLuongTheoGio() {
        return this.luongTheoGio;
    }
    
    public void setLuongTheoGio(double luongTheoGio) {
        this.luongTheoGio = luongTheoGio;
    }    
}
