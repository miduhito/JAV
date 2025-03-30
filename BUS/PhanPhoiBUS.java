package BUS;

import DAO.PhanPhoiDAO;
import DTO.PhanPhoiDTO;

import java.util.ArrayList;

public class PhanPhoiBUS {
    ArrayList<PhanPhoiDTO> danhSachPhanPhoi;
    PhanPhoiDAO ppDAO = new PhanPhoiDAO();

    public ArrayList<PhanPhoiDTO> getDuLieuKhuyenMai (){
        danhSachPhanPhoi = new ArrayList<>();
        danhSachPhanPhoi = ppDAO.getDuLieuQuanLiPhanPhoi();
        return danhSachPhanPhoi;
    }
}
