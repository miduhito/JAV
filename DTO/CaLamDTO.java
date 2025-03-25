package DTO;

public class CaLamDTO {
    private String maCa;
    private String moTa;
    private String gioBD;
    private String gioKT;
    private String trangThai;

    public CaLamDTO() {}
    public CaLamDTO(String maCa, String moTa, String gioBD, String gioKT, String trangThai) {
        this.maCa = maCa;
        this.moTa = moTa;
        this.gioBD = gioBD;
        this.gioKT = gioKT;
        this.trangThai = trangThai;
    }

    public String getMaCa() {
        return this.maCa;
    }
    
    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }
    
    public String getMoTa() {
        return this.moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public String getGioBD() {
        return this.gioBD;
    }
    
    public void setGioBD(String gioBD) {
        this.gioBD = gioBD;
    }
    
    public String getGioKT() {
        return this.gioKT;
    }
    
    public void setGioKT(String gioKT) {
        this.gioKT = gioKT;
    }
    
    public String getTrangThai() {
        return this.trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }    
}
