package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

import java.util.ArrayList;

public class PhieuNhapBUS {
    ArrayList<PhieuNhapDTO> danhSachPhieuNhap;
    PhieuNhapDAO ctpnDAO = new PhieuNhapDAO();

    public ArrayList<PhieuNhapDTO> getDuLieuKhuyenMai (){
        danhSachPhieuNhap = new ArrayList<>();
        danhSachPhieuNhap = ctpnDAO.getDuLieuQuanLiPhieuNhap();
        return danhSachPhieuNhap;
    }
}
