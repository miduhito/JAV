package DTO;

public class KhachHangDTO {
    private String maKH;
    private String tenKH;
    private int SDT;
    private String email;
    private String diaChi;
    private int soDiem;
    private String tenDangNhap;

    public KhachHangDTO() {}
    public KhachHangDTO(String maKH, String tenKH, int SDT, String email, String diacChi, int soDiem, String tenDangNhap) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.SDT = SDT;
        this.email = email;
        this.diaChi = diacChi;
        this.soDiem = soDiem;
        this.tenDangNhap = tenDangNhap;
    }

    public String getMaKH() {
        return this.maKH;
    }
    
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
    
    public String getTenKH() {
        return this.tenKH;
    }
    
    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }
    
    public int getSDT() {
        return this.SDT;
    }
    
    public void setSDT(int SDT) {
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

    public String getTenDangNhap() {
        return this.tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
}