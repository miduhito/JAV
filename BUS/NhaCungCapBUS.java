package BUS;

import DAO.NhaCungCapDAO;
import DTO.KhachHangDTO;
import DTO.NhaCungCapDTO;
import Interface.BUS_Interface;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NhaCungCapBUS implements BUS_Interface<NhaCungCapDTO> {

    private NhaCungCapDAO nhaCungCapDAO;
    private ArrayList<NhaCungCapDTO> danhSachNhaCungCap;

    public NhaCungCapBUS(){
        nhaCungCapDAO = new NhaCungCapDAO();
        danhSachNhaCungCap = new ArrayList<>();
    }


    @Override
    public ArrayList<NhaCungCapDTO> getData() {
        return nhaCungCapDAO.getData();
    }

    @Override
    public NhaCungCapDTO getDataById(String id) {
        return nhaCungCapDAO.getDataById(id);
    }

    @Override
    public boolean add(NhaCungCapDTO nhaCungCap) {
        return nhaCungCapDAO.add(nhaCungCap);
    }

    @Override
    public boolean update(NhaCungCapDTO nhaCungCap) {
        return nhaCungCapDAO.update(nhaCungCap);
    }

    @Override
    public boolean delete(String id) {
        return nhaCungCapDAO.delete(id);
    }

    @Override
    public boolean hide(String id) {
        return nhaCungCapDAO.hide(id);
    }

    @Override
    public boolean regexInput(NhaCungCapDTO entity) {
        return false;
    }

    @Override
    public String generateID() {
        return nhaCungCapDAO.generateID();
    }

    public NhaCungCapDTO getDataByName(String name){
        return nhaCungCapDAO.getDataByName(name);
    }

    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findNhaCungCap(searchText).isEmpty()) {
                for(NhaCungCapDTO emp: findNhaCungCap(searchText)) {
                    table.addRow(new Object[] {
                        emp.getMaNhaCungCap(),
                        emp.getTenNhaCungCap(),
                        emp.getEmail(),
                        emp.getSDT(),
                        emp.getDiaChi(),
                        emp.isTrangThai() ? "Đang hoạt động" : "Không hoạt động"
                    });
                }
            }
            else {
                refreshTableData(table);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            refreshTableData(table);
        }
    }

    // Cập nhật dữ liệu cho bảng hiển thị (nạp lại dữ liệu từ DB)
    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu bảng cũ
        try {
            for (NhaCungCapDTO emp : nhaCungCapDAO.getData()) {
                tableModel.addRow(new Object[]{
                        emp.getMaNhaCungCap(),
                        emp.getTenNhaCungCap(),
                        emp.getEmail(),
                        emp.getSDT(),
                        emp.getDiaChi(),
                        emp.isTrangThai() ? "Đang hoạt động" : "Không hoạt động"
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<NhaCungCapDTO> findNhaCungCap(String searchText) {
        try {
            List<NhaCungCapDTO> nccSearch = new ArrayList<>();
            // Lấy tất cả nhà cung cấp từ NhaCungCapDAO
            for (NhaCungCapDTO ncc : nhaCungCapDAO.getData()) {
                // Kiểm tra các thuộc tính của KhachHangDTO
                if ((ncc.getMaNhaCungCap() != null && ncc.getMaNhaCungCap().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ncc.getTenNhaCungCap() != null && ncc.getTenNhaCungCap().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ncc.getEmail() != null && ncc.getEmail().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ncc.getSDT() != null && ncc.getSDT().toLowerCase().contains(searchText.toLowerCase())) ||
                    (ncc.getDiaChi() != null && ncc.getDiaChi().toLowerCase().contains(searchText.toLowerCase()))) { // Tìm trong số điểm (int)
                    // Nếu bất kỳ thuộc tính nào chứa searchText, thêm vào danh sách kết quả
                    nccSearch.add(ncc);
                }
            }
            return nccSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm thông tin nhà cung cấp", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }
}
