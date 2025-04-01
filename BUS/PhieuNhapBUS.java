package BUS;

import DAO.PhieuNhapDAO;
import DTO.KhuyenMaiDTO;
import DTO.NguyenLieuDTO;
import DTO.PhieuNhapDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class PhieuNhapBUS implements BUS_Interface<PhieuNhapDTO> {
    ArrayList<PhieuNhapDTO> danhSachPhieuNhap;
    ArrayList<NguyenLieuDTO> danhSachNguyenLieu;
    NguyenLieuBUS nguyenLieuBUS;
    PhanPhoiBUS phanPhoiBUS;
    NhaCungCapBUS nhaCungCapBUS;
    PhieuNhapDAO phieuNhapDAO;

    public PhieuNhapBUS(){
        danhSachPhieuNhap = new ArrayList<>();
        danhSachNguyenLieu = new ArrayList<>();
        nguyenLieuBUS = new NguyenLieuBUS();
        phanPhoiBUS = new PhanPhoiBUS();
        nhaCungCapBUS = new NhaCungCapBUS();
        phieuNhapDAO = new PhieuNhapDAO();
    }



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

    // load data table Nguyên Liệu
    public void loadDataTable(DefaultTableModel tableModel){
        danhSachNguyenLieu = new ArrayList<>();
        danhSachNguyenLieu = nguyenLieuBUS.getData();
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (danhSachNguyenLieu != null) {
            for (NguyenLieuDTO nguyenLieu: danhSachNguyenLieu) {
                String maNCC = phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getMaNhaCungCap();
                Object[] row = {
                        nguyenLieu.getMaNguyenLieu(),
                        nguyenLieu.getTenNguyenLieu(),
                        nhaCungCapBUS.getDataById(maNCC).getTenNhaCungCap(),
                        phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getGiaNhap(),
                        nguyenLieu.getDonViDo()
                };
                tableModel.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu nguyên liệu", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadDataCart(){

    }


}
