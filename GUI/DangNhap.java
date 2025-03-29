package GUI;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.*;
public class DangNhap {
    private static JFrame frame = new JFrame("Login Page");
    public static void main(String[] args) {
        // Tạo khung chính
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.decode("#F5ECE0"));
        // Tạo các thành phần
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField("",15);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField("",15);
        userText.setText("midu");
        passwordText.setText("123");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor= GridBagConstraints.WEST;
        ImageIcon img = new ImageIcon("Image\\meme.jpg");
        Image scaledImage = img.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(scaledImage));
        JPanel p = new JPanel();
        p.add(iconLabel);
        JPanel LoginPanel = new JPanel();
        LoginPanel.setLayout(new FlowLayout());
        String Login = "Đăng Nhập";
        addButtonToPanel(LoginPanel,Login,Color.BLACK);
        // Thêm các thành phần vào khung
        gbc.gridx=0;gbc.gridy=0;gbc.gridheight=2;
        frame.add(p,gbc);
        gbc.gridx=1;gbc.gridy=0;gbc.gridheight=1;
        frame.add(userLabel,gbc);
        gbc.gridx=2;gbc.gridy=0;
        frame.add(userText,gbc);
        gbc.gridx=1;gbc.gridy=1;
        frame.add(passwordLabel,gbc);
        gbc.gridx=2;gbc.gridy=1;
        frame.add(passwordText,gbc);
        gbc.gridx=1;gbc.gridy=2;gbc.gridwidth=2;gbc.anchor=GridBagConstraints.CENTER;
        frame.add(LoginPanel,gbc);
        //Event
        // Hiển thị khung
        frame.setVisible(true);
    }
    public static void showTrangChu(){
        frame.dispose();
        JFrame f = new JFrame("Trang Chủ");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1200, 650);
        TrangChuGUI dashboard = new TrangChuGUI();
        f.add(dashboard);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    private static void addButtonToPanel(JPanel buttonPanel, String buttonText, Color backgroundColor) {
        JButton button = new JButton(buttonText);
        button.setForeground(Color.WHITE);
        // Đặt icon cho nút
        
        // Căn chữ nút về bên trái
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Ép kích thước của nút (200x40)
        button.setMaximumSize(new Dimension(250, 40));
        button.setPreferredSize(new Dimension(250, 40));
        button.setMinimumSize(new Dimension(250, 40));
    
        // Hiệu ứng hover để đổi màu
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#F5ECE0"));
                button.setForeground(Color.BLACK);
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.WHITE);
            }
        });
    
        // Nếu nút "Quản lí nhân viên" được bấm thì hiển thị giao diện QuanLiNhanVienGUI
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonText.equals("Đăng Nhập")) {
                    showTrangChu();
                }
            }
        });
    
        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}

