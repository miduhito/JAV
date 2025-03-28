package Custom;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class PoppinsFont {
    private static Font poppinsRegular;
    private static Font poppinsBold;
    private static Font poppinsItalic;

    static {
        try {
            InputStream regularStream = PoppinsFont.class.getResourceAsStream("/Resources/Fonts/Poppins-Regular.ttf");
            InputStream boldStream = PoppinsFont.class.getResourceAsStream("/Resources/Fonts/Poppins-Bold.ttf");
            InputStream italicStream = PoppinsFont.class.getResourceAsStream("/Resources/Fonts/Poppins-Italic.ttf");

            if (regularStream == null || boldStream == null || italicStream == null) {
                throw new Exception("Không tìm thấy file font Poppins trong resources/fonts/");
            }

            poppinsRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            poppinsBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            poppinsItalic = Font.createFont(Font.TRUETYPE_FONT, italicStream);

            // Đăng ký font với hệ thống đồ họa
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsRegular);
            ge.registerFont(poppinsBold);
            ge.registerFont(poppinsItalic);

            regularStream.close();
            boldStream.close();
            italicStream.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi tải font Poppins: " + e.getMessage());
            e.printStackTrace();
            // Dùng font mặc định nếu lỗi
            poppinsRegular = new Font("Segoe UI", Font.PLAIN, 12);
            poppinsBold = new Font("Segoe UI", Font.BOLD, 12);
            poppinsItalic = new Font("Segoe UI", Font.ITALIC, 12);
        }
    }

    public static Font getPoppinsRegular(float size) {
        return poppinsRegular.deriveFont(size);
    }

    public static Font getPoppinsBold(float size) {
        return poppinsBold.deriveFont(size);
    }

    public static Font getPoppinsItalic(float size) {
        return poppinsItalic.deriveFont(size);
    }
}