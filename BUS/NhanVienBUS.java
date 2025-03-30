package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

import java.util.ArrayList;

public class NhanVienBUS implements CRUD<NhanVienDTO> {
    private NhanVienDAO nvDAO = new NhanVienDAO();;

    public NhanVienBUS() {}

    public String getTenNhanVienById(String maNhanVien) {
        return nvDAO.getTenNhanVienById(maNhanVien);
    }

    @Override
    public ArrayList<NhanVienDTO> getData() {
        return nvDAO.getData();
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
    public boolean regexInput(NhanVienDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return "";
    }
}
