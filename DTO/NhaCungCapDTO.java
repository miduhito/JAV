package DTO;

public class NhaCungCapDTO {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String email;
    private String SDT;
    private String diaChi;
    private boolean trangThai;

    public NhaCungCapDTO() {}
    public NhaCungCapDTO(String maNhaCungCap, String tenNhaCungCap, String email, String SDT, String diaChi, boolean trangThai) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.email = email;
        this.SDT = SDT;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    public String getMaNhaCungCap() {
        return this.maNhaCungCap;
    }
    
    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }
    
    public String getTenNhaCungCap() {
        return this.tenNhaCungCap;
    }
    
    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSDT() {
        return this.SDT;
    }
    
    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    public String getDiaChi() {
        return this.diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
