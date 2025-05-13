package BUS;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import DAO.TaiKhoanDAO;
public class TaiKhoanBUS {
    private static TaiKhoanDAO tk = new TaiKhoanDAO();
    public static String tenDangNhap;
    public int kiemtra(String username, String password){
        
        ArrayList<TaiKhoanDTO> DSTK = tk.dataDangNhap();
        for(TaiKhoanDTO t : DSTK){
            if(t.getTenDangNhap().equals(username)&&t.getMatKhau().equals(password)){
                tenDangNhap = t.getTenDangNhap();
                return 1;
            }
        }
        return 0;
    }
    public ArrayList<NhanVienDTO> Kotendangnhap(){
        ArrayList<NhanVienDTO> ds=tk.getdatanv();
        return ds;
    }
    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu bảng cũ
        try {
            for (TaiKhoanDTO emp : tk.getdata()) {
                tableModel.addRow(new Object[]{
                        emp.getTenDangNhap(),
                        emp.getMatKhau(),
                        emp.getVaiTro(),
                        emp.getTrangThai(),
                        emp.getNgayTao(),
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối đến cơ sở dữ liệu!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void insertTaiKhoan(String tenDangNhap,String matKhau,Date ngayTao,String trangThai,String vaiTro,String maNV){
        TaiKhoanDAO tk = new TaiKhoanDAO();
        tk.insertTaiKhoan(tenDangNhap,matKhau,ngayTao,trangThai,vaiTro,maNV);

    }
    public boolean checkduplicate(String tenDangNhap){
        for(TaiKhoanDTO t : tk.getdata()){
            if(t.getTenDangNhap().equals(tenDangNhap)){
                return false;
            }
        }
        return true;
    }
    public void deleteTaiKhoan(String tendDangNhap){    
        //System.out.println(tendDangNhap);
        tk.deleteTaiKhoan(tendDangNhap);

    }
    public NhanVienDTO timNV(String tenDangNhap){
        return tk.timNV(tenDangNhap);
    }
    public TaiKhoanDTO timTK(String tenDangNhap){
        TaiKhoanDTO acc = new TaiKhoanDTO();
        ArrayList<TaiKhoanDTO> tkList = tk.getdata();
        for(TaiKhoanDTO t:tkList){
            if(tenDangNhap.equals(t.getTenDangNhap())){
                acc.setTenDangNhap(t.getTenDangNhap());
                acc.setMatKhau(t.getMatKhau());
                acc.setNgayTao(t.getNgayTao());
                acc.setTrangThai(t.getTrangThai());
                acc.setVaiTro(t.getVaiTro());
                break;
            }
        }
        return acc;
    }
    public void editTaiKhoan(String tenDangNhap,String matKhau,String trangThai,String vaiTro,String tenDangNhapCu,String maNV){
        tk.editTaiKhoan(tenDangNhap, matKhau, trangThai, vaiTro,tenDangNhapCu,maNV);
    }
    public void searchTableData(DefaultTableModel table, String searchText) {
        table.setRowCount(0);
        try {
            if(!findTaiKhoan(searchText).isEmpty()) {
                for(TaiKhoanDTO emp: findTaiKhoan(searchText)) {
                    table.addRow(new Object[] {
                        emp.getTenDangNhap(),
                        emp.getMatKhau(),
                        emp.getVaiTro(),
                        emp.getTrangThai(),
                        emp.getNgayTao(),
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
    public ArrayList<TaiKhoanDTO> findTaiKhoan(String searchText) {
        try {
            ArrayList<TaiKhoanDTO> tkSearch = new ArrayList<>();
            // Lặp qua tất cả nhân viên từ nhanVienDAO
            for (TaiKhoanDTO t : tk.getdata()) {
                // Kiểm tra từng thuộc tính của NhanVienDTO
                if ((t.getTenDangNhap() != null && t.getTenDangNhap().toLowerCase().contains(searchText.toLowerCase())) ||
                    (t.getMatKhau() != null && t.getMatKhau().toLowerCase().contains(searchText.toLowerCase())) ||
                    (t.getNgayTao() != null && t.getNgayTao().toString().contains(searchText.toLowerCase())) ||
                    (t.getTrangThai() != null && t.getTrangThai().toLowerCase().contains(searchText.toLowerCase())) ||
                    (t.getVaiTro() != null && t.getVaiTro().toLowerCase().contains(searchText.toLowerCase()))) {
                    // Nếu bất kỳ thuộc tính nào chứa chuỗi tìm kiếm, thêm vào danh sách kết quả
                    tkSearch.add(t);
                }
            }
            return tkSearch;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy thông tin của tất cả nhân viên", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }
    public void transferdata(TaiKhoanDTO tk ,TaiKhoanDTO data){
        tk.setTenDangNhap(data.getTenDangNhap());
        tk.setMatKhau(data.getMatKhau());
        tk.setNgayTao(data.getNgayTao());
        tk.setTrangThai(data.getTrangThai());
        tk.setVaiTro(data.getVaiTro());
    }
    // public static void main(String[] args) {
    //     TaiKhoanDAO dao = new TaiKhoanDAO();
    //     ArrayList<NhanVienDTO> ds = dao.getdatanv();
    //     TaiKhoanBUS t = new TaiKhoanBUS();
    //     t.Kotendangnhap();
    //     for(NhanVienDTO nv : t.Kotendangnhap()){
    //         System.out.println(nv.getTenNV()+nv.getMaNV());

    //     }
    // }
}
