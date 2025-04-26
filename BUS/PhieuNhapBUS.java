package BUS;

import DAO.PhieuNhapDAO;
import DTO.KhuyenMaiDTO;
import DTO.NguyenLieuDTO;
import DTO.PhanPhoiDTO;
import DTO.PhieuNhapDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class PhieuNhapBUS implements BUS_Interface<PhieuNhapDTO> {
    ArrayList<PhieuNhapDTO> danhSachPhieuNhap;
    ArrayList<NguyenLieuDTO> danhSachNguyenLieu;
    NguyenLieuBUS nguyenLieuBUS;
    PhanPhoiBUS phanPhoiBUS;
    NhaCungCapBUS nhaCungCapBUS;
    NhanVienBUS nhanVienBUS;
    PhieuNhapDAO phieuNhapDAO;

    public PhieuNhapBUS(){
        danhSachPhieuNhap = new ArrayList<>();
        danhSachNguyenLieu = new ArrayList<>();
        nguyenLieuBUS = new NguyenLieuBUS();
        phanPhoiBUS = new PhanPhoiBUS();
        nhaCungCapBUS = new NhaCungCapBUS();
        nhanVienBUS = new NhanVienBUS();
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
    public void loadDataTableNguyenLieu(DefaultTableModel tableModel){
        danhSachNguyenLieu = new ArrayList<>(nguyenLieuBUS.getAllNguyenLieu());
        List<PhanPhoiDTO> phanPhoiList = new ArrayList<>(phanPhoiBUS.getData()); 
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        // if (danhSachNguyenLieu != null) {
        //     for (NguyenLieuDTO nguyenLieu: danhSachNguyenLieu) {
        //         String maNCC = phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getMaNhaCungCap();
        //         Object[] row = {
        //                 nguyenLieu.getMaNguyenLieu(),
        //                 nguyenLieu.getTenNguyenLieu(),
        //                 nhaCungCapBUS.getDataById(maNCC).getTenNhaCungCap(),
        //                 phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getGiaNhap() + "/" + nguyenLieu.getDonViDo(),
        //                 nguyenLieu.getSoLuong(),
        //                 nguyenLieu.getDonViDo()
        //         };
        //         tableModel.addRow(row);
        //     }
        // } else {
        //     JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu nguyên liệu", "Error", JOptionPane.ERROR_MESSAGE);
        // }
        if (phanPhoiList != null && danhSachNguyenLieu != null) {
            for (PhanPhoiDTO phanphoi: phanPhoiList) {
                String tenNguyenLieu = nguyenLieuBUS.getNguyenLieuById(phanphoi.getMaNguyenLieu()).getTenNguyenLieu();
                String tenNCC = nhaCungCapBUS.getDataById(phanphoi.getMaNhaCungCap()).getTenNhaCungCap();
                Double soLuong = nguyenLieuBUS.getNguyenLieuById(phanphoi.getMaNguyenLieu()).getSoLuong();
                String donViDo = nguyenLieuBUS.getNguyenLieuById(phanphoi.getMaNguyenLieu()).getDonViDo();

                Object[] row = {
                    phanphoi.getMaNguyenLieu(),
                    tenNguyenLieu,
                    tenNCC,
                    phanphoi.getGiaNhap() + "/" + donViDo,
                    soLuong,
                    donViDo
                };
                tableModel.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu nguyên liệu", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // load khi chọn nhà cung cấp
    public void loadDataTableNguyenLieu(DefaultTableModel tableModel, String maNhaCungCap){
        danhSachNguyenLieu = new ArrayList<>(nguyenLieuBUS.getAllNguyenLieu());
        List<PhanPhoiDTO> phanPhoiList = new ArrayList<>(phanPhoiBUS.getData());
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        // if (danhSachNguyenLieu != null) {
        //     for (NguyenLieuDTO nguyenLieu: danhSachNguyenLieu) {
        //         String maNCC = phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getMaNhaCungCap();
        //         if (maNCC.equals(maNhaCungCap)){
        //             Object[] row = {
        //                     nguyenLieu.getMaNguyenLieu(),
        //                     nguyenLieu.getTenNguyenLieu(),
        //                     nhaCungCapBUS.getDataById(maNCC).getTenNhaCungCap(),
        //                     phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getGiaNhap() + "/" + nguyenLieu.getDonViDo(),
        //                     nguyenLieu.getSoLuong(),
        //                     nguyenLieu.getDonViDo()
        //             };
        //             tableModel.addRow(row);
        //         }
        //     }
        // } else {
        //     JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu nguyên liệu", "Error", JOptionPane.ERROR_MESSAGE);
        // }
        if (phanPhoiList != null && danhSachNguyenLieu != null) {
            for (PhanPhoiDTO phanphoi: phanPhoiList) {
                String tenNguyenLieu = nguyenLieuBUS.getNguyenLieuById(phanphoi.getMaNguyenLieu()).getTenNguyenLieu();
                String tenNCC = nhaCungCapBUS.getDataById(phanphoi.getMaNhaCungCap()).getTenNhaCungCap();
                Double soLuong = nguyenLieuBUS.getNguyenLieuById(phanphoi.getMaNguyenLieu()).getSoLuong();
                String donViDo = nguyenLieuBUS.getNguyenLieuById(phanphoi.getMaNguyenLieu()).getDonViDo();
                if (phanphoi.getMaNhaCungCap().equals(maNhaCungCap)) {
                    Object[] row = {
                        phanphoi.getMaNguyenLieu(),
                        tenNguyenLieu,
                        tenNCC,
                        phanphoi.getGiaNhap() + "/" + donViDo,
                        soLuong,
                        donViDo
                    };
                    tableModel.addRow(row);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu nguyên liệu", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void loadDataPhieuNhap(DefaultTableModel tableModel){
        danhSachPhieuNhap = new ArrayList<>();
        danhSachPhieuNhap = phieuNhapDAO.getData();
        tableModel.setRowCount(0);

        if (danhSachPhieuNhap != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (PhieuNhapDTO phieuNhap: danhSachPhieuNhap){
                Vector<String> rowData = new Vector<>();
                String maNhanVien = phieuNhap.getMaNhanVien();
                String maNCC = phieuNhap.getMaNhaCungCap();
                rowData.add(phieuNhap.getMaPhieuNhap());
                rowData.add(dateFormat.format(phieuNhap.getNgayNhap()));
                rowData.add(maNhanVien + " - " + nhanVienBUS.getNhanVienById(maNhanVien).getTenNhanVien());
                rowData.add(maNCC + " - " + nhaCungCapBUS.getDataById(maNCC).getTenNhaCungCap());
                rowData.add(String.valueOf(phieuNhap.getTongTien()));
                tableModel.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu phiếu nhập", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<PhieuNhapDTO> advancedSearch(String maPhieuNhap, String maNhanVien, String maNhaCungCap, Date startDate, Date endDate) {
        return phieuNhapDAO.advancedSearch(maPhieuNhap, maNhanVien, maNhaCungCap, startDate, endDate);
    }

}
