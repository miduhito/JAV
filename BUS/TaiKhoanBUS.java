package BUS;
import java.util.ArrayList;
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
}
