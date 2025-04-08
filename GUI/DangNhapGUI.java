package GUI;
import javax.swing.*;
import BUS.TaiKhoanBUS;
import java.awt.*;
import java.awt.event.*;
public class DangNhapGUI {
    private static JFrame frame = new JFrame("Login Page");
    static JPanel LoginPanel = new JPanel();
    static BackgroundPanel Bg = new BackgroundPanel();
        
    static JLabel userLabel = new JLabel("Username:");
    static JTextField userText = new JTextField("",15);

    static JLabel passwordLabel = new JLabel("Password:");
    static JPasswordField passwordText = new JPasswordField("",15);
    @SuppressWarnings("FieldMayBeFinal")
        static class BackgroundPanel extends JPanel{
            private Image bgImg ;

            public BackgroundPanel(){
                String imgPath="Resources\\Image\\BG.png";
                bgImg = new ImageIcon(imgPath).getImage() ;
            }

            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g) ;
                g.drawImage(bgImg , 0 , 0 , this.getWidth() , this.getHeight() , this) ;
            }
    }
    public static void main(String[] args) {
        // Tạo khung chính
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        // Tạo các thành phần
        userText.setText("midu");
        passwordText.setText("12345");
        //Chinh sua
        LoginPanel.setOpaque(false) ;
        LoginPanel.setLayout(new GridBagLayout());

        userText.setPreferredSize(new Dimension(150 , 20)) ;
        passwordText.setPreferredSize(new Dimension(150 , 20)) ;
        userText.setBorder(null) ;
        passwordText.setBorder(null) ;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10 , 10 , 10 , 10) ;
        gbc.gridx=0;gbc.gridy=0;
        ImageIcon img = new ImageIcon("Resources\\Image\\meme.jpg");
        Image scaledImage = img.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(scaledImage));
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.add(iconLabel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout());
        String Login = "Đăng Nhập";
        addButtonToPanel(buttonPanel,Login,Color.BLACK);
        // Thêm các thành phần vào khung
        gbc.gridx=0;gbc.gridy=0;
        LoginPanel.add(userLabel,gbc);
        gbc.gridx=1;gbc.gridy=0;
        LoginPanel.add(userText,gbc);
        gbc.gridx=0;gbc.gridy=1;
        LoginPanel.add(passwordLabel,gbc);
        gbc.gridx=1;gbc.gridy=1;
        LoginPanel.add(passwordText,gbc);
        gbc.gridx=0;gbc.gridy=2;gbc.gridwidth=2;gbc.anchor=GridBagConstraints.CENTER;
        LoginPanel.add(buttonPanel,gbc);

        gbc.gridx=0;
        Bg.add(p,gbc);
        gbc.gridx=1;gbc.gridy=0;gbc.anchor=GridBagConstraints.CENTER;
        Bg.add(LoginPanel,gbc);
        frame.setContentPane(Bg);
        //Event
        // Hiển thị khung
        frame.setVisible(true);
        frame.setResizable(false);
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
                buttonPanel.setBackground(Color.decode("#F5ECE0"));
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.WHITE);
                buttonPanel.setBackground(backgroundColor);
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = TaiKhoanBUS.kiemtra(userText.getText(),passwordText.getText());
                if (i==1) {
                    showTrangChu();
                }
                else JOptionPane.showMessageDialog(null,"Nhap sai du lieu");
            }
        });
        buttonPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                buttonPanel.setBackground(Color.decode("#F5ECE0"));
                button.setBackground(Color.decode("#F5ECE0"));
                button.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e){
                buttonPanel.setBackground(backgroundColor);
                button.setBackground(backgroundColor);
                button.setForeground(Color.WHITE);
            }
        });
        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
}

