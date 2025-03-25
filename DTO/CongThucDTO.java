package DTO;

public class CongThucDTO {
    private String maCongThuc;
    private String tenCongThuc;
    private String moTa;

    public CongThucDTO() {}
    public CongThucDTO(String maCongThuc, String tenCongThuc, String moTa) {
        this.maCongThuc = maCongThuc;
        this.tenCongThuc = tenCongThuc;
        this.moTa = moTa;
    }

    public String getMaCongThuc() {
        return this.maCongThuc;
    }
    
    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }
    
    public String getTenCongThuc() {
        return this.tenCongThuc;
    }
    
    public void setTenCongThuc(String tenCongThuc) {
        this.tenCongThuc = tenCongThuc;
    }
    
    public String getMoTa() {
        return this.moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }        
}
