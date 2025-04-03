package BUS;

import DAO.NguyenLieuDAO;
import DTO.NguyenLieuDTO;
import Interface.BUS_Interface;

import java.util.ArrayList;

public class NguyenLieuBUS implements BUS_Interface<NguyenLieuDTO> {
    NguyenLieuDAO nguyenLieuDAO;

    public NguyenLieuBUS(){
        nguyenLieuDAO = new NguyenLieuDAO();
    }

    @Override
    public ArrayList<NguyenLieuDTO> getData() {
        return nguyenLieuDAO.getData();
    }

    @Override
    public NguyenLieuDTO getDataById(String id) {
        return null;
    }

    @Override
    public boolean add(NguyenLieuDTO entity) {
        return false;
    }

    @Override
    public boolean update(NguyenLieuDTO entity) {
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
    public boolean regexInput(NguyenLieuDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
