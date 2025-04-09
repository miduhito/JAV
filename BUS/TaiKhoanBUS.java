package BUS;
import java.util.ArrayList;
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
        ArrayList<NhanVienDTO> ds = new ArrayList<NhanVienDTO>();
        TaiKhoanDAO tk = new TaiKhoanDAO();
        ds=tk.getdatanv();
        return ds;
    }
    public void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu bảng cũ
        TaiKhoanDAO taikhoan = new TaiKhoanDAO();
        try {
            for (TaiKhoanDTO emp : taikhoan.getdata()) {
                tableModel.addRow(new Object[]{
                        emp.getTenDangNhap(),
                        emp.getMatKhau(),
                        emp.getNgayTao(),
                        emp.getTrangThai(),
                        emp.getVaiTro(),
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
        TaiKhoanDAO tk = new TaiKhoanDAO();
        for(TaiKhoanDTO t : tk.getdata()){
            if(t.getTenDangNhap().equals(tenDangNhap)){
                return false;
            }
        }
        return true;
    }
    public void deleteTaiKhoan(String tendDangNhap){    
        TaiKhoanDAO tk = new TaiKhoanDAO();
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
    public void editTaiKhoan(String tenDangNhap,String matKhau,String trangThai,String vaiTro,String tenDangNhapCu){
        tk.editTaiKhoan(tenDangNhap, matKhau, trangThai, vaiTro,tenDangNhapCu);
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
