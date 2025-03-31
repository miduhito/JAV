package BUS;

import DAO.ChucVuDAO;
import DTO.ChucVuDTO;

import java.util.ArrayList;

public class ChucVuBUS implements CRUD<ChucVuDTO> {
    private ArrayList<ChucVuDTO> danhSachChucVu;
    private final ChucVuDAO chucVuDAO;

    public ChucVuBUS() {
        chucVuDAO = new ChucVuDAO();
        danhSachChucVu = new ArrayList<>();
    }

    @Override
    public ArrayList<ChucVuDTO> getData() {
        danhSachChucVu = chucVuDAO.getData();
        return danhSachChucVu;
    }

    @Override
    public ChucVuDTO getDataById(String id) {
        return chucVuDAO.getDataById(id);
    }

    @Override
    public boolean add(ChucVuDTO newChucVu) {
        if (regexInput(newChucVu)) {
            return chucVuDAO.add(newChucVu);
        }
        return false;
    }

    @Override
    public boolean update(ChucVuDTO newChucVu) {
        if (regexInput(newChucVu)) {
            return chucVuDAO.update(newChucVu);
        }
        return false;
    }

    @Override
    public boolean delete(String maChucVu) {
        return chucVuDAO.delete(maChucVu);
    }

    @Override
    public String generateID() {
        return chucVuDAO.generateID();
    }

    @Override
    public boolean regexInput(ChucVuDTO entity) {
        return true;
        // Regex Input chức vụ tự sửa sau
    }
}
