package BUS;

import DAO.KhuyenMaiDAO;
import DTO.KhuyenMaiDTO;

import java.util.ArrayList;

public class KhuyenMaiBUS {
    ArrayList<KhuyenMaiDTO> danhSachKhuyenMai;
    KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();

    public ArrayList<KhuyenMaiDTO> getDuLieuKhuyenMai (){
        danhSachKhuyenMai = new ArrayList<>();
        danhSachKhuyenMai = kmDAO.getDuLieuQuanLiKhuyenMai();
        return danhSachKhuyenMai;
    }
}
