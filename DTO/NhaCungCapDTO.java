package DTO;

public class NhaCungCapDTO {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String email;
    private int SDT;
    private String diaChi;

    public NhaCungCapDTO() {}
    public NhaCungCapDTO(String maNhaCungCap, String tenNhaCungCap, String email, int SDT, String diaChi) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.email = email;
        this.SDT = SDT;
        this.diaChi = diaChi;
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
    
    public int getSDT() {
        return this.SDT;
    }
    
    public void setSDT(int SDT) {
        this.SDT = SDT;
    }
    
    public String getDiaChi() {
        return this.diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }    
}
