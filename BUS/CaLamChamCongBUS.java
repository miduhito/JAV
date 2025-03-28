package BUS;

import DAO.CaLamChamCongDAO;
import DTO.CaLamDTO;
import java.util.ArrayList;

public class CaLamChamCongBUS {
    ArrayList<CaLamDTO> danhSachCaLam;
    CaLamChamCongDAO clccDAO = new CaLamChamCongDAO();

    public ArrayList<CaLamDTO> getDuLieuCaLam (){
        danhSachCaLam  = new ArrayList<>();
        danhSachCaLam = clccDAO.getDuLieuQuanLiCaLamChamCong();
        return danhSachCaLam;
    }
}
