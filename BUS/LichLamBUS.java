package BUS;

import DAO.LichLamDAO;
import DTO.LichLamDTO;

import java.util.ArrayList;

public class LichLamBUS implements CRUD<LichLamDTO> {
    ArrayList<LichLamDTO> danhSachLichLam;
    LichLamDAO llDAO = new LichLamDAO();
    
    @Override
    public ArrayList<LichLamDTO> getData() {
        danhSachLichLam  = new ArrayList<>();
        danhSachLichLam = llDAO.getData();
        return danhSachLichLam;
    }

    @Override
    public boolean add(LichLamDTO newLichLam) {
        // SAVE TO DB
        if (regexInput(newLichLam)){
            return llDAO.add(newLichLam);
        }
        return false;
    }

    @Override
    public boolean update(LichLamDTO newLichLam) {
        if (regexInput(newLichLam)){
            return llDAO.update(newLichLam);
        }
        return false;
    }

    @Override
    public boolean delete(String maLichLam) {
        return llDAO.delete(maLichLam);
    }

    @Override
    public boolean regexInput(LichLamDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return llDAO.generateID();
    }
}
