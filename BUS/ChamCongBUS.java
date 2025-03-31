package BUS;

import DAO.ChamCongDAO;
import DTO.CaLamDTO;
import DTO.ChucVuDTO;
import DTO.LichLamDTO;

import javax.swing.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ChamCongBUS {
    private final ChamCongDAO chamCongDAO;
    private final NhanVienBUS nhanVienBUS;
    private final CaLamBUS caLamBUS;
    private final ChucVuBUS chucVuBUS;

    public ChamCongBUS() {
        chamCongDAO = new ChamCongDAO();
        nhanVienBUS = new NhanVienBUS();
        caLamBUS = new CaLamBUS();
        chucVuBUS = new ChucVuBUS();
    }

    public ArrayList<Object[]> tinhChamCong(String maNhanVien, int thang, int nam) {
        List<LichLamDTO> lichLamList = chamCongDAO.getLichLamViec(maNhanVien, thang, nam);
        if (lichLamList.isEmpty()){
            JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu làm việc !");
        }
        ArrayList<Object[]> chamCongData = new ArrayList<>();

        int stt = 1;

        for (LichLamDTO lich : lichLamList) {
            String tenNhanVien = nhanVienBUS.getDataById(lich.getMaNhanVien()).getTenNV();
            CaLamDTO caLam = caLamBUS.getDataById(lich.getMaCaLam());

            ChucVuDTO chucVu = chucVuBUS.getDataById(nhanVienBUS.getDataById(lich.getMaNhanVien()).getMaChucVu());
            String tenChucVu = chucVu.getTenChucVu();
            double luongTheoGio = chucVu.getLuongTheoGio();

            int gioBD = Integer.parseInt(caLam.getGioBD().split(":")[0]);
            int gioKT = Integer.parseInt(caLam.getGioKT().split(":")[0]);
            int gioCong = gioKT - gioBD;

            Object[] row = { stt++, lich.getMaNhanVien(), tenNhanVien, tenChucVu, lich.getNgayLam(), gioCong, luongTheoGio};
            chamCongData.add(row);
        }

        return chamCongData;
    }
}
