package DTO;

public class KhachHangDTO {
    private String maKhachHang;
    private String tenKhachHang;
    private String gioiTinh;
    private String SDT;
    private String email;
    private String diaChi;
    private int soDiem;

    public KhachHangDTO() {}
    public KhachHangDTO(String maKhachHang, String tenKhachHang, String gioiTinh, String SDT, String email, String diaChi, int soDiem) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.SDT = SDT;
        this.email = email;
        this.diaChi = diaChi;
        this.soDiem = soDiem;
    }

    public String getMaKhachHang() {
        return this.maKhachHang;
    }
    
    public void setMaKH(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    
    public String getTenKhachHang() {
        return this.tenKhachHang;
    }
    
    public void setTenKH(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getGioiTinh() {
        return this.gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public String getSDT() {
        return this.SDT;
    }
    
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDiaChi() {
        return this.diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    public int getSoDiem() {
        return this.soDiem;
    }
    
    public void setSoDiem(int soDiem) {
        this.soDiem = soDiem;
    }
}