package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import Interface.BUS_SubInterface;

import java.util.ArrayList;

public class ChiTietPhieuNhapBUS implements BUS_SubInterface<ChiTietPhieuNhapDTO> {
    ArrayList<ChiTietPhieuNhapDTO> danhSachChiTietPhieuNhap;
    ChiTietPhieuNhapDAO ctpnDAO;
    NguyenLieuBUS nguyenLieuBUS;

    public ChiTietPhieuNhapBUS(){
        danhSachChiTietPhieuNhap = new ArrayList<>();
        ctpnDAO = new ChiTietPhieuNhapDAO();
        nguyenLieuBUS = new NguyenLieuBUS();
    }

    public ArrayList<ChiTietPhieuNhapDTO> getData(){
        danhSachChiTietPhieuNhap = new ArrayList<>();
        danhSachChiTietPhieuNhap = ctpnDAO.getData();
        return danhSachChiTietPhieuNhap;
    }

    @Override
    public ArrayList<ChiTietPhieuNhapDTO> getDataById(String id) {
        return ctpnDAO.getDataById(id);
    }

    @Override
    public boolean add(ChiTietPhieuNhapDTO entity) {
        if (regexInput(entity)){
            nguyenLieuBUS.updateAmount(entity.getMaNguyenLieu(), entity.getSoLuongNhap());
            return ctpnDAO.add(entity);
        }
        return false;
    }

    @Override
    public boolean update(ChiTietPhieuNhapDTO entity) {
        if (regexInput(entity)){
            return ctpnDAO.update(entity);
        }
        return false;
    }

    @Override
    public boolean delete(String id, String pair_id) {
        return ctpnDAO.delete(id, pair_id);
    }

    @Override
    public boolean hide(String id, String pair_id) {
        return ctpnDAO.hide(id, pair_id);
    }

    @Override
    public boolean regexInput(ChiTietPhieuNhapDTO entity) {
        return true;
    }

    public void closeConnectDB(){
        ChiTietPhieuNhapDAO chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
        chiTietPhieuNhapDAO.closeConnectDB();
    }

}
