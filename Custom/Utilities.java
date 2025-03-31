package Custom;

import javax.swing.*;
import java.awt.*;

public class Utilities {
    public Utilities(){}

    public static ImageIcon loadAndResizeIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(path);

            Image image = icon.getImage();

            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            System.err.println("Không thể tải icon từ đường dẫn: " + path);
            e.printStackTrace();
            return null;
        }
    }
}
