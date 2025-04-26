package BUS;

import DAO.CaLamDAO;
import DTO.CaLamDTO;
import Interface.BUS_Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.List;
import java.util.ArrayList;
import java.util.Vector;

public class CaLamBUS implements BUS_Interface<CaLamDTO> {
    private ArrayList<CaLamDTO> danhSachCaLam;
    private final CaLamDAO clDAO;

    public CaLamBUS(){
        danhSachCaLam  = new ArrayList<>();
        clDAO = new CaLamDAO();
    }

    @Override
    public ArrayList<CaLamDTO> getData (){
        return clDAO.getData();
    }

    @Override
    public CaLamDTO getDataById(String id){
        return clDAO.getDataById(id);
    }

    @Override
    public boolean add(CaLamDTO newCaLam){
        // SAVE TO DB
        if (regexInput(newCaLam)){
            return clDAO.add(newCaLam);
        }
        return false;
    }

    @Override
    public boolean update(CaLamDTO newCaLam){
        if (regexInput(newCaLam)){
            return clDAO.update(newCaLam);
        }
        return false;
    }

    @Override
    public boolean delete(String maCaLam){
        return clDAO.delete(maCaLam);
    }

    @Override
    public boolean hide(String id) {
        return clDAO.hide(id);
    }

    @Override
    public String generateID(){
        return clDAO.generateID();
    }

    @Override
    public boolean regexInput(CaLamDTO caLam){
        // GUI REGEX
        String moTa = caLam.getMoTa();
        int gioBatDau = Integer.parseInt(caLam.getGioBD().split(":")[0]);
        int gioKetThuc = Integer.parseInt(caLam.getGioKT().split(":")[0]);

        if(moTa.equals("")){
            JOptionPane.showMessageDialog(null,
                    "Mô tả không được để trống",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gioBatDau >= gioKetThuc) {
            JOptionPane.showMessageDialog(null,
                    "Giờ bắt đầu ca phải nhỏ hơn giờ kết thúc ca",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gioKetThuc - gioBatDau < 4){
            JOptionPane.showMessageDialog(null,
                    "Một ca làm phải tối thiểu 4 tiếng",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gioKetThuc - gioBatDau > 8){
            JOptionPane.showMessageDialog(null,
                    "Một ca làm chỉ được tối đa 8 tiếng",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void loadDataTable(DefaultTableModel tableModelCL){
        tableModelCL.setRowCount(0); // xóa dữ liêu cũ
        for(CaLamDTO calam : getData()){
            // Vector<Object> vec = new Vector<>();
            // vec.add(calam.getMaCa().toString());
            // vec.add(calam.getMoTa().toString());
            // vec.add(calam.getGioBD().toString());
            // vec.add(calam.getGioKT().toString());
            // vec.add(calam.getTrangThai().toString());
            // tableModelCL.addRow(vec); // thêm thông tin thành 1 hàng vào bảng
            tableModelCL.addRow(new Object[]{
                calam.getMaCa(),
                calam.getMoTa(),
                calam.getGioBD(),
                calam.getGioKT(),
                calam.getTrangThai()
        });
        }
    }

    public String getThoiGianCa(String maCa){
        return clDAO.getThoiGianCaById(maCa);
    }
}
