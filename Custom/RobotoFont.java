package Custom;

import java.awt.*;
import java.io.InputStream;

public class RobotoFont {
    private static Font RobotoRegular;
    private static Font RobotoBold;
    private static Font RobotoItalic;

    static {
        try {
            InputStream regularStream = RobotoFont.class.getResourceAsStream("/Resources/Fonts/Roboto-Regular.ttf");
            InputStream boldStream = RobotoFont.class.getResourceAsStream("/Resources/Fonts/Roboto-Bold.ttf");
            InputStream italicStream = RobotoFont.class.getResourceAsStream("/Resources/Fonts/Roboto-Italic.ttf");

            if (regularStream == null || boldStream == null || italicStream == null) {
                throw new Exception("Không tìm thấy file font Roboto trong resources/fonts/");
            }

            RobotoRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            RobotoBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            RobotoItalic = Font.createFont(Font.TRUETYPE_FONT, italicStream);

            // Đăng ký font với hệ thống đồ họa
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(RobotoRegular);
            ge.registerFont(RobotoBold);
            ge.registerFont(RobotoItalic);

            regularStream.close();
            boldStream.close();
            italicStream.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi tải font Roboto: " + e.getMessage());
            e.printStackTrace();
            // Dùng font mặc định nếu lỗi
            RobotoRegular = new Font("Segoe UI", Font.PLAIN, 14);
            RobotoBold = new Font("Segoe UI", Font.BOLD, 14);
            RobotoItalic = new Font("Segoe UI", Font.ITALIC, 14);
        }
    }

    public static Font getRobotoRegular(float size) {
        return RobotoRegular.deriveFont(size);
    }

    public static Font getRobotoBold(float size) {
        return RobotoBold.deriveFont(size);
    }

    public static Font getRobotoItalic(float size) {
        return RobotoItalic.deriveFont(size);
    }
}
