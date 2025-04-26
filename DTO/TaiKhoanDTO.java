package DTO;

import java.sql.Date;

public class TaiKhoanDTO {
    private String tenDangNhap;
    private String matKhau;
    private String trangThai;
    private Date ngayTao;
    private String vaiTro;

    public TaiKhoanDTO() {}
    public TaiKhoanDTO(String tenDangNhap, String matKhau, String trangThai, Date ngayTao, String vaiTro) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.vaiTro = vaiTro;
    }

    public String getTenDangNhap() {
        return this.tenDangNhap;
    }
    
    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
    
    public String getMatKhau() {
        return this.matKhau;
    }
    
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    public String getTrangThai() {
        return this.trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public Date getNgayTao() {
        return this.ngayTao;
    }
    
    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    public String getVaiTro() {
        return this.vaiTro;
    }
    
    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
