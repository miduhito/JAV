package Custom;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(140, 200, 255); // Màu của phần "Thumb"
        this.trackColor = new Color(240, 247, 250); // Màu nền của thanh cuộn
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        // Làm thanh scrollbar mỏng hơn (chiều rộng là 8px)
        return new Dimension(5, 8);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton(); // Loại bỏ nút "giảm" (-)
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton(); // Loại bỏ nút "tăng" (+)
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(thumbColor);
        // Điều chỉnh kích thước thumb để phù hợp với độ mỏng
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(trackColor);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }
}