package Custom;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int arcWidth;  // Độ cong ngang của góc
    private int arcHeight; // Độ cong dọc của góc
    private Color backgroundColor; // Màu nền tùy chỉnh

    public RoundedPanel(int arcWidth, int arcHeight, Color backgroundColor) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.backgroundColor = backgroundColor;
        setOpaque(false); // Đảm bảo vẽ nền tùy chỉnh thay vì nền mặc định
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ phần nền bo góc
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
    }
}