package BUS;
import DAO.HoaDonDAO;

import java.util.ArrayList;
import java.util.List;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;

public class findHoaDon {
    private HoaDonBUS hoaDonBUS =new HoaDonBUS();
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    public findHoaDon(){}
 
    public List<HoaDonDTO> timHoaDon(String text) {
        List<HoaDonDTO> hoaDonList = new ArrayList<>();
        for(HoaDonDTO hd: hoaDonBUS.getAllHoaDon()) {
            if(hd.getMaHoaDon().toLowerCase().trim().equals(text.toLowerCase()) ||
                hd.getMaKhachHang().toLowerCase().trim().equals(text.toLowerCase()) ||
                hd.getMaNhanVien().toLowerCase().trim().equals(text.toLowerCase()) ||
                hd.getPTTT().toLowerCase().trim().equals(text.toLowerCase()) ||
                hd.getNgayLap().toLowerCase().trim().equals(text.toLowerCase()) ||
                String.valueOf(hd.getTongTien()).equals(text.toLowerCase())) {
                    hoaDonList.add(hd);
                }
        }
        return hoaDonList;
    }
}
