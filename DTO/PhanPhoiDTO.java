package DTO;

public class PhanPhoiDTO {
    private String maNhaCungCap;
    private String maNguyenLieu;
    private Double giaNhap;


    public PhanPhoiDTO(){}

    public PhanPhoiDTO(String maNhaCungCap, String maNguyenLieu, Double donGia) {
        this.maNhaCungCap = maNhaCungCap;
        this.maNguyenLieu = maNguyenLieu;
        this.giaNhap = donGia;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }
}
