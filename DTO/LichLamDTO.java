package DTO;

import java.util.Date;

public class LichLamDTO {
    private String maLichLam;
    private String maNhanVien;
    private Date ngayLam;
    private String maCaLam;
    private Boolean trangThai;

    public LichLamDTO() {
    }

    public LichLamDTO(String maLichLam, String maNhanVien, Date ngayLam, String maCaLam, Boolean trangThai) {
        this.maLichLam = maLichLam;
        this.maNhanVien = maNhanVien;
        this.ngayLam = ngayLam;
        this.maCaLam = maCaLam;
        this.trangThai = trangThai;
    }

    public String getMaLichLam() {
        return maLichLam;
    }

    public void setMaLichLam(String maLichLam) {
        this.maLichLam = maLichLam;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(Date ngayLam) {
        this.ngayLam = ngayLam;
    }

    public String getMaCaLam() {
        return maCaLam;
    }

    public void setMaCaLam(String maCaLam) {
        this.maCaLam = maCaLam;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "LichLamDTO{" +
                "maLichLam='" + maLichLam + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", ngayLam=" + ngayLam +
                ", maCaLam='" + maCaLam + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
