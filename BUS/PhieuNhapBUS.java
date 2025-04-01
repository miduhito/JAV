package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import java.util.ArrayList;

public class PhieuNhapBUS implements BUS_Interface<PhieuNhapDTO> {
    ArrayList<PhieuNhapDTO> danhSachPhieuNhap;
    PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();


    @Override
    public ArrayList<PhieuNhapDTO> getData (){
        danhSachPhieuNhap = new ArrayList<>();
        danhSachPhieuNhap = phieuNhapDAO.getData();
        return danhSachPhieuNhap;
    }

    @Override
    public PhieuNhapDTO getDataById(String id) {
        return phieuNhapDAO.getDataById(id);
    }

    @Override
    public boolean add(PhieuNhapDTO entity) {
        if (regexInput(entity)) {
            return phieuNhapDAO.add(entity);
        }
        return false;
    }

    @Override
    public boolean update(PhieuNhapDTO entity) {
        if (regexInput(entity)) {
            return phieuNhapDAO.update(entity);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return phieuNhapDAO.delete(id);
    }

    @Override
    public boolean hide(String id) {
        return phieuNhapDAO.hide(id);
    }

    @Override
    public boolean regexInput(PhieuNhapDTO entity) {
        if (entity.getMaPhieuNhap() == null || entity.getMaPhieuNhap().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã phiếu nhập không được để trống!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (entity.getMaNhaCungCap() == null || entity.getMaNhaCungCap().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã nhà cung cấp không được để trống!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (entity.getMaNhanVien() == null || entity.getMaNhanVien().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên không được để trống!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public String generateID() {
        return phieuNhapDAO.generateID();
    }
}
