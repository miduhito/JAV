package DTO;

public class ThucAnDTO {
    private String maThucAn;
    private String tenThucAn;
    private String moTa;
    private String loaiMonAn;
    private Double gia;
    private int soLuong;
    private String anhThucAn;
    private String maCongThuc;

    public ThucAnDTO() {}
    public ThucAnDTO(String maThucAn, String tenThucAn, String moTa, String loaiMonAn, Double gia, int soLuong, String anhThucAn, String maCongThuc) {
        this.maThucAn = maThucAn;
        this.tenThucAn = tenThucAn;
        this.moTa = moTa;
        this.loaiMonAn = loaiMonAn;
        this.gia = gia;
        this.soLuong = soLuong;
        this.anhThucAn = anhThucAn;
        this.maCongThuc = maCongThuc;
    }

    public String getMaThucAn() {
        return this.maThucAn;
    }
    
    public void setMaThucAn(String maThucAn) {
        this.maThucAn = maThucAn;
    }
    
    public String getTenThucAn() {
        return this.tenThucAn;
    }
    
    public void setTenThucAn(String tenThucAn) {
        this.tenThucAn = tenThucAn;
    }
    
    public String getMoTa() {
        return this.moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public String getLoaiMonAn() {
        return this.loaiMonAn;
    }
    
    public void setLoaiMonAn(String loaiMonAn) {
        this.loaiMonAn = loaiMonAn;
    }
    
    public Double getGia() {
        return this.gia;
    }
    
    public void setGia(Double gia) {
        this.gia = gia;
    }    

    public int getSoLuong() {
        return this.soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getAnhThucAn() {
        return this.anhThucAn;
    }

    public void setAnhThucAn(String anhThucAn) {
        this.anhThucAn = anhThucAn;
    }

    public String getMaCongThuc() {
        return this.maCongThuc;
    }

    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }
}
