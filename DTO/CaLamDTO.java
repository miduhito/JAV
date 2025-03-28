package DTO;

public class CaLamDTO {
    private String maCa;
    private String moTa;
    private String gioBD;
    private String gioKT;
    private Boolean trangThai;

    public CaLamDTO() {}
    public CaLamDTO(String maCa, String moTa, String gioBD, String gioKT, Boolean trangThai) {
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

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "CaLamDTO{" +
                "maCa='" + maCa + '\'' +
                ", moTa='" + moTa + '\'' +
                ", gioBD='" + gioBD + '\'' +
                ", gioKT='" + gioKT + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
