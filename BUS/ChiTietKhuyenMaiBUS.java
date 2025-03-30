package BUS;

import DAO.ChiTietKhuyenMaiDAO;
import DTO.ChiTietKhuyenMaiDTO;

import java.util.ArrayList;

public class ChiTietKhuyenMaiBUS {
    ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKhuyenMai;;
    ChiTietKhuyenMaiDAO ctkmDAO = new ChiTietKhuyenMaiDAO();

    public ArrayList<ChiTietKhuyenMaiDTO> getDuLieuChiTietKhuyenMai(){
        danhSachChiTietKhuyenMai  = new ArrayList<>();
        danhSachChiTietKhuyenMai = ctkmDAO.getDuLieuQuanLiChiTietKhuyenMai();
        return danhSachChiTietKhuyenMai;
    }
}
