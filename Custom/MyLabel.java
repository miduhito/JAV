package Custom;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyLabel extends JLabel {
    public MyLabel(String content, float fontSize, String fontStyle) {
        super(content, SwingConstants.CENTER);

        if (Objects.equals(fontStyle, "Regular")){
            setFont(RobotoFont.getRobotoRegular(fontSize));
        }
        if(Objects.equals(fontStyle, "Bold")){
            setFont(RobotoFont.getRobotoBold(fontSize));
        }
        setForeground(Color.BLACK);
    }
}
