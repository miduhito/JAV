package DTO;

public class PhanPhoiDTO {
    private String maNhaCungCap;
    private String maNguyenLieu;
    private int giaNhap;


    public PhanPhoiDTO(){}

    public PhanPhoiDTO(String maNhaCungCap, String maNguyenLieu, int donGia) {
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

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }
}
