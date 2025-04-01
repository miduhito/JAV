package BUS;

import DAO.PhanPhoiDAO;
import DTO.PhanPhoiDTO;
import DTO.PhieuNhapDTO;
import Interface.BUS_SubInterface;

import java.util.ArrayList;

public class PhanPhoiBUS implements BUS_SubInterface<PhanPhoiDTO> {
    ArrayList<PhanPhoiDTO> danhSachPhanPhoi;
    PhanPhoiDAO ppDAO = new PhanPhoiDAO();

    @Override
    public ArrayList<PhanPhoiDTO> getData(){
        danhSachPhanPhoi = new ArrayList<>();
        danhSachPhanPhoi = ppDAO.getData();
        return danhSachPhanPhoi;
    }

    // get PhanPhoi data with maNCC
    @Override
    public ArrayList<PhanPhoiDTO> getDataById(String id) {
        return ppDAO.getDataById(id);
    }

    // get PhanPhoi data with maNguyenLieu
    public PhanPhoiDTO getDataByIdSub(String id){
        return ppDAO.getDataByIdSub(id);
    }

    @Override
    public boolean add(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean update(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id, String pair_id) {
        return false;
    }

    @Override
    public boolean hide(String id, String pair_id) {
        return false;
    }

    @Override
    public boolean regexInput(PhanPhoiDTO entity) {
        return false;
    }
}
