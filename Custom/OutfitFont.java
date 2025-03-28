package Custom;

import java.awt.*;
import java.io.InputStream;

public class OutfitFont {
    private static Font OutfitRegular;
    private static Font OutfitBold;
    private static Font OutfitItalic;

    static {
        try {
            InputStream regularStream = OutfitFont.class.getResourceAsStream("/Resources/Fonts/Outfit-Regular.ttf");
            InputStream boldStream = OutfitFont.class.getResourceAsStream("/Resources/Fonts/Outfit-Bold.ttf");

            if (regularStream == null || boldStream == null) {
                throw new Exception("Không tìm thấy file font Outfit trong resources/fonts/");
            }

            OutfitRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            OutfitBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);

            // Đăng ký font với hệ thống đồ họa
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(OutfitRegular);
            ge.registerFont(OutfitBold);

            regularStream.close();
            boldStream.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi tải font Outfit: " + e.getMessage());
            e.printStackTrace();
            // Dùng font mặc định nếu lỗi
            OutfitRegular = new Font("Segoe UI", Font.PLAIN, 14);
            OutfitBold = new Font("Segoe UI", Font.BOLD, 14);
        }
    }

    public static Font getOutfitRegular(float size) {
        return OutfitRegular.deriveFont(size);
    }

    public static Font getOutfitBold(float size) {
        return OutfitBold.deriveFont(size);
    }
}
