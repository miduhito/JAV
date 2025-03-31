package Custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


// LỚP PHỤ HỖ TRỢ TÍNH CÔNG NHÂN VIÊN
public class EmployeeData {
    public String maNV;
    public String tenNV;
    public String chucVu;
    public List<String> ngayLam;
    public List<Integer> gioCong;
    public int tongGioCong;
    public double luongTheoGio;

    public EmployeeData(String maNV, String tenNV, String chucVu, double luongTheoGio) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.chucVu = chucVu;
        this.ngayLam = new ArrayList<>();
        this.gioCong = new ArrayList<>();
        this.tongGioCong = 0;
        this.luongTheoGio = luongTheoGio;
    }

    public void addWorkingDay(String day) {
        ngayLam.add(day);
    }

    public void addDayWorkingHours(int hours){
        gioCong.add(hours);
    }

    public void addWorkingHours(int hours) {
        tongGioCong += hours;
    }
}
