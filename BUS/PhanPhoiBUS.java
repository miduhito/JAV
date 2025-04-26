package BUS;

import DAO.PhanPhoiDAO;
import DTO.NguyenLieuDTO;
import DTO.PhanPhoiDTO;
import DTO.PhieuNhapDTO;
import Interface.BUS_SubInterface;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

public class PhanPhoiBUS implements BUS_SubInterface<PhanPhoiDTO> {
    private ArrayList<PhanPhoiDTO> danhSachPhanPhoi;
    private PhanPhoiDAO ppDAO = new PhanPhoiDAO();
    private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();

    @Override
    public ArrayList<PhanPhoiDTO> getData(){
        danhSachPhanPhoi = new ArrayList<>();
        danhSachPhanPhoi = ppDAO.getData();
        return danhSachPhanPhoi;
    }

    // get PhanPhoi data with maNCC
    @Override
    public ArrayList<PhanPhoiDTO> getDataById(String id) {
        return ppDAO.getDataById(id);
    }

    // get PhanPhoi data with maNguyenLieu
    public PhanPhoiDTO getDataByIdSub(String id){
        return ppDAO.getDataByIdSub(id);
    }

    public boolean insertPhanPhoi(String maNhaCungCap, List<NguyenLieuDTO> danhSachNguyenLieu) {
        return ppDAO.insertPhanPhoi(maNhaCungCap, danhSachNguyenLieu);
    }

    @Override
    public boolean add(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean update(PhanPhoiDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String maNhaCungCap, String maNguyenLieu) {
        return ppDAO.delete(maNhaCungCap, maNguyenLieu);
    }

    @Override
    public boolean hide(String id, String pair_id) {
        return false;
    }

    @Override
    public boolean regexInput(PhanPhoiDTO entity) {
        return false;
    }

    public void loadDataTable(DefaultTableModel tableModel, String searchText, String maNhaCungCap) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        try {
            List<NguyenLieuDTO> nguyenLieuList = nguyenLieuBUS.getAllNguyenLieu();
            for (NguyenLieuDTO nguyenLieu : nguyenLieuList) {
                boolean success = getData().stream()
                        .anyMatch(pp -> pp.getMaNhaCungCap().equals(maNhaCungCap)
                                 && pp.getMaNguyenLieu().equals(nguyenLieu.getMaNguyenLieu()));
    
                // Kiểm tra nếu searchText khớp với maNguyenLieu hoặc tenNguyenLieu
                if (searchText.isEmpty() || nguyenLieu.getMaNguyenLieu().contains(searchText) 
                    || nguyenLieu.getTenNguyenLieu().contains(searchText)) {
                    tableModel.addRow(new Object[]{
                        nguyenLieu.getMaNguyenLieu(),
                        nguyenLieu.getTenNguyenLieu(),
                        nguyenLieu.getGia(),
                        success
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
