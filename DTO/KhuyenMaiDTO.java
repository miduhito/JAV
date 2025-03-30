package DTO;

public class KhuyenMaiDTO {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private String moTaKhuyenMai;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String donViKhuyenMai;
    private String dieuKienApDung;

    public KhuyenMaiDTO() {}
    public KhuyenMaiDTO(String maKhuyenMai, String tenKhuyenMai, String moTaKhuyenMai, String ngayBatDau, String ngayKetThuc, String donViKhuyenMai, String dieuKienApDung) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.moTaKhuyenMai = moTaKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.donViKhuyenMai = donViKhuyenMai;
        this.dieuKienApDung = dieuKienApDung;
    }

    public String getDonViKhuyenMai() {
        return donViKhuyenMai;
    }

    public void setDonViKhuyenMai(String donViKhuyenMai) {
        this.donViKhuyenMai = donViKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return this.maKhuyenMai;
    }
    
    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }
    
    public String getTenKhuyenMai() {
        return this.tenKhuyenMai;
    }
    
    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }
    
    public String getMoTaKhuyenMai() {
        return this.moTaKhuyenMai;
    }
    
    public void setMoTaKhuyenMai(String moTaKhuyenMai) {
        this.moTaKhuyenMai = moTaKhuyenMai;
    }
    
    public String getNgayBatDau() {
        return this.ngayBatDau;
    }
    
    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    
    public String getNgayKetThuc() {
        return this.ngayKetThuc;
    }
    
    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    public String getDieuKienApDung() {
        return this.dieuKienApDung;
    }
    
    public void setDieuKienApDung(String dieuKienApDung) {
        this.dieuKienApDung = dieuKienApDung;
    }    
}
