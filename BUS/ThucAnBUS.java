package BUS;

import DAO.ThucAnDAO;
import DTO.ThucAnDTO;
import Interface.BUS_Interface;

import java.util.ArrayList;

public class ThucAnBUS implements BUS_Interface<ThucAnDTO> {
    ThucAnDAO thucAnDAO;

    public ThucAnBUS(){
        thucAnDAO = new ThucAnDAO();
    }

    @Override
    public ArrayList<ThucAnDTO> getData() {
        return thucAnDAO.getData();
    }

    @Override
    public ThucAnDTO getDataById(String id) {
        return thucAnDAO.getDataById(id);
    }

    @Override
    public boolean add(ThucAnDTO entity) {
        return false;
    }

    @Override
    public boolean update(ThucAnDTO entity) {
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
    public boolean regexInput(ThucAnDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
