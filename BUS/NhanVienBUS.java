package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import Interface.BUS_Interface;

import java.util.ArrayList;

public class NhanVienBUS implements BUS_Interface<NhanVienDTO> {
    private NhanVienDAO nvDAO = new NhanVienDAO();;

    public NhanVienBUS() {}

    @Override
    public ArrayList<NhanVienDTO> getData() {
        return nvDAO.getData();
    }

    @Override
    public NhanVienDTO getDataById(String id) {
        return nvDAO.getDataById(id);
    }

    @Override
    public boolean add(NhanVienDTO entity) {
        return false;
    }

    @Override
    public boolean update(NhanVienDTO entity) {
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
    public boolean regexInput(NhanVienDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
