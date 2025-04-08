package BUS;
import java.util.ArrayList;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import DAO.TaiKhoanDAO;
public class TaiKhoanBUS {
    public static String tenDangNhap;
    public static  int kiemtra(String username, String password){
        TaiKhoanDAO taikhoan = new TaiKhoanDAO();
        ArrayList<TaiKhoanDTO> DSTK = taikhoan.getdata();
        for(TaiKhoanDTO t : DSTK){
            if(t.getTenDangNhap().equals(username)&&t.getMatKhau().equals(password)){
                tenDangNhap = t.getTenDangNhap();
                return 1;
            }
        }
        return 0;
    }
    public static ArrayList<NhanVienDTO> Kotendangnhap(){
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
    public void insertTaiKhoan(String tenDangNhap,String matKhau,Date ngayTao,String trangThai,String vaiTro){
        TaiKhoanDAO tk = new TaiKhoanDAO();
        tk.insertTaiKhoan(tenDangNhap,matKhau,ngayTao,trangThai,vaiTro);

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
