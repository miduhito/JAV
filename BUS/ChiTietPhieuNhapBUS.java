package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;

import java.util.ArrayList;

public class ChiTietPhieuNhapBUS {
    ArrayList<ChiTietPhieuNhapDTO> danhSachChiTietPhieuNhap;
    ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();

    public ArrayList<ChiTietPhieuNhapDTO> getDuLieuKhuyenMai (){
        danhSachChiTietPhieuNhap = new ArrayList<>();
        danhSachChiTietPhieuNhap = ctpnDAO.getDuLieuQuanLiChiTietPhieuNhap();
        return danhSachChiTietPhieuNhap;
    }
}
