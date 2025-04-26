package BUS;

import Custom.DateComparison;
import DAO.LichLamDAO;
import DTO.LichLamDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class LichLamBUS implements BUS_Interface<LichLamDTO> {
    LichLamDAO llDAO = new LichLamDAO();
    CaLamBUS clccBUS = new CaLamBUS();
    NhanVienBUS nvBUS = new NhanVienBUS();
    
    @Override
    public ArrayList<LichLamDTO> getData() {
        return llDAO.getData();
    }

    @Override
    public LichLamDTO getDataById(String id){
        return llDAO.getDataById(id);
    }

    public ArrayList<LichLamDTO> getDataByDate(Date date) {
        return llDAO.getDataByDate(date);
    }

    @Override
    public boolean add(LichLamDTO newLichLam) {
        if (regexInput(newLichLam)){
            return llDAO.add(newLichLam);
        }
        return false;
    }

    @Override
    public boolean update(LichLamDTO newLichLam) {
        if (regexInput(newLichLam)){
            return llDAO.update(newLichLam);
        }
        return false;
    }

    @Override
    public boolean delete(String maLichLam) {
        return llDAO.delete(maLichLam);
    }

    @Override
    public boolean hide(String id) {
        return llDAO.hide(id);
    }

    @Override
    public boolean regexInput(LichLamDTO entity) {
        if (DateComparison.compareDateWithToday(entity.getNgayLam()) < 0 ){
            JOptionPane.showMessageDialog(null,
                    "Không thể thao tác lịch làm vì ngày làm đã qua! ",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public String generateID() {
        return llDAO.generateID();
    }


    // load theo date
    public void loadDataTable(DefaultTableModel tableModelLL, ArrayList<LichLamDTO> danhSachLichLam){
        tableModelLL.setRowCount(0);

        for(LichLamDTO LichLam : llDAO.getData()){
            Vector<Object> vec = new Vector<>();

            vec.add(LichLam.getMaLichLam());
            vec.add(LichLam.getNgayLam());
            vec.add(nvBUS.getNhanVienById(LichLam.getMaNhanVien()).getTenNhanVien());
            vec.add(clccBUS.getThoiGianCa(LichLam.getMaCaLam()));
            vec.add(LichLam.getTrangThai() ? "Hiệu lực" : "Không hiệu lực");
            tableModelLL.addRow(vec);
        }
    }
}
