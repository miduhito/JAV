package DTO;

public class NhanVienDTO {
    private String maNhanVien;
    private String tenNhanVien;
    private String SDT;
    private String email;
    private String ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String trangThai;
    private String maChucVu;
    private String tenDangNhap;

    public NhanVienDTO() {}
    public NhanVienDTO(String maNhanVien, String tenNhanVien, String SDT, String email, String ngaySinh, String gioiTinh, String diaChi, String trangThai, String maChucVu, String tenDangNhap) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.SDT =SDT;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.maChucVu = maChucVu;
        this.tenDangNhap = tenDangNhap;
    }

    public String getMaNV() {
        return this.maNhanVien;
    }

    public void setMaNV(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNV() {
        return this.tenNhanVien;
    }

    public void setTenNV(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
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

    public String getNgaySinh() {
        return this.ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return this.gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTrangThai() {
        return this.trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaChucVu() {
        return this.maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenDangNhap() {
        return this.tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
}
