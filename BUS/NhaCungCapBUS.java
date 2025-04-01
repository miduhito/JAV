package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import Interface.BUS_Interface;

import java.util.ArrayList;

public class NhaCungCapBUS implements BUS_Interface<NhaCungCapDTO> {

    private NhaCungCapDAO nhaCungCapDAO;
    private ArrayList<NhaCungCapDTO> danhSachNhaCungCap;

    public NhaCungCapBUS(){
        nhaCungCapDAO = new NhaCungCapDAO();
        danhSachNhaCungCap = new ArrayList<>();
    }


    @Override
    public ArrayList<NhaCungCapDTO> getData() {
        return null;
    }

    @Override
    public NhaCungCapDTO getDataById(String id) {
        return nhaCungCapDAO.getDataById(id);
    }

    @Override
    public boolean add(NhaCungCapDTO entity) {
        return false;
    }

    @Override
    public boolean update(NhaCungCapDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean hide(String id) {
        return false;
    }

    @Override
    public boolean regexInput(NhaCungCapDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
