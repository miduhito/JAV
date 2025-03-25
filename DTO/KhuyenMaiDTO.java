package DTO;

public class KhuyenMaiDTO {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private String hinhThucKhuyenMai;
    private String ngayBatDau;
    private String ngayKetThuc;
    private Double giaTriKhuyenMai;
    private String dieuKienApDung;

    public KhuyenMaiDTO() {}
    public KhuyenMaiDTO(String maKhuyenMai, String tenKhuyenMai, String hinhThucKhuyenMai, String ngayBatDau, String ngayKetThuc, Double giaTriKhuyenMai, String dieuKienApDung) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.hinhThucKhuyenMai = hinhThucKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giaTriKhuyenMai = giaTriKhuyenMai;
        this.dieuKienApDung = dieuKienApDung;
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
    
    public String getHinhThucKhuyenMai() {
        return this.hinhThucKhuyenMai;
    }
    
    public void setHinhThucKhuyenMai(String hinhThucKhuyenMai) {
        this.hinhThucKhuyenMai = hinhThucKhuyenMai;
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
    
    public Double getGiaTriKhuyenMai() {
        return this.giaTriKhuyenMai;
    }
    
    public void setGiaTriKhuyenMai(Double giaTriKhuyenMai) {
        this.giaTriKhuyenMai = giaTriKhuyenMai;
    }
    
    public String getDieuKienApDung() {
        return this.dieuKienApDung;
    }
    
    public void setDieuKienApDung(String dieuKienApDung) {
        this.dieuKienApDung = dieuKienApDung;
    }    
}
