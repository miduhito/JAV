package Custom;

import javax.swing.*;
import java.awt.*;

// Lớp nút bo tròn (RoundedButton)
public class RoundedButton extends JButton {
    private int arcWidth;  // Độ cong của góc ngang
    private int arcHeight; // Độ cong của góc dọc

    public RoundedButton(String text, int arcWidth, int arcHeight) {
        super(text);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setFocusPainted(false); // Tắt viền focus khi bấm
        setContentAreaFilled(false); // Loại bỏ nền mặc định
        setBorderPainted(false); // Loại bỏ viền mặc định
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền nút
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // Vẽ viền nút
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        // Vẽ chữ của nút
        super.paintComponent(g2);
    }
}