package Custom;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateComparison {
    public static int compareDateWithToday(Date inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date today = sdf.parse(sdf.format(new Date()));

            Date formattedInputDate = sdf.parse(sdf.format(inputDate));

            return formattedInputDate.compareTo(today);
//            Trả về 0 nếu inputDate là ngày hiện tại.
//            Trả về >0 nếu inputDate là ngày tương lai.
//            Trả về <0 nếu inputDate là ngày quá khứ

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            return Integer.MIN_VALUE;
        }
    }
}
