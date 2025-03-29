package BUS;

import DAO.CaLamChamCongDAO;
import DTO.CaLamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

public class CaLamChamCongBUS {
    ArrayList<CaLamDTO> danhSachCaLam;
    CaLamChamCongDAO clccDAO = new CaLamChamCongDAO();

    public ArrayList<CaLamDTO> getDuLieu (){
        danhSachCaLam  = new ArrayList<>();
        danhSachCaLam = clccDAO.getDuLieu();
        return danhSachCaLam;
    }

    public boolean add(CaLamDTO newCaLam){
        // SAVE TO DB
        if (regexInput(newCaLam)){
            return clccDAO.add(newCaLam);
        }
        return false;
    }

    public boolean update(CaLamDTO newCaLam){
        if (regexInput(newCaLam)){
            return clccDAO.update(newCaLam);
        }
        return false;
    }

    public String generateMaCaLam(){
        return clccDAO.generateMaCaLam();
    }

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

    public void loadDanhSachCaLamTable(DefaultTableModel tableModelCL){
        tableModelCL.setRowCount(0); // xóa dữ liêu cũ

        ArrayList<CaLamDTO> danhSachCaLam = getDuLieu();
        for(CaLamDTO calam : danhSachCaLam){
            Vector<Object> vec = new Vector<>();
            vec.add(calam.getMaCa());
            vec.add(calam.getMoTa());
            vec.add(calam.getGioBD());
            vec.add(calam.getGioKT());
            vec.add(calam.getTrangThai() ? "Hiệu lực" : "Không hiệu lực");
            tableModelCL.addRow(vec); // thêm thông tin thành 1 hàng vào bảng
        }
    }
}
