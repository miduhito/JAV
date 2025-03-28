package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrangChuGUI extends JPanel {
    // contentPanel - vùng bao ngoài có bo tròn
    private RoundedPanel contentPanel = new RoundedPanel(40, 40, Color.decode("#F5ECE0"));
    // dynamicContentPanel - vùng chứa giao diện động (như QuanLiNhanVienGUI)
    private JPanel dynamicContentPanel = new JPanel(new BorderLayout());

    public TrangChuGUI() {
        // Sử dụng BorderLayout cho TrangChuGUI
        this.setLayout(new BorderLayout());

        // Tạo menu bên trái (RoundedPanel)
        RoundedPanel menuPanel = new RoundedPanel(50, 50, Color.decode("#F5ECE0"));
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(250, 0));

        // Header Panel - chứa icon và tiêu đề ở trên cùng của menuPanel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        headerPanel.setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon("Image\\FastFoodIcon.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));
        headerPanel.add(iconLabel);

        JLabel headerLabel = new JLabel("Wc Johnal");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.BLACK);
        headerPanel.add(headerLabel);
        // Thêm headerPanel vào vùng NORTH của menuPanel
        menuPanel.add(headerPanel, BorderLayout.NORTH);

        // Button Panel - chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        // Thêm các nút chức năng vào buttonPanel
        addButtonToPanel(buttonPanel, "Quản lí nhân viên", Color.WHITE, "Image\\EmployeeIcon.png");
        addButtonToPanel(buttonPanel, "Quản lí khách hàng", Color.WHITE, "Image\\CustomerIcon.png");
        addButtonToPanel(buttonPanel, "Quản lí tài khoản", Color.WHITE, "Image\\AccountIcon.png");
        
        // Thêm các nút khác nếu cần:
        // addButtonToPanel(buttonPanel, "Quản lí thức ăn", Color.WHITE);
        // addButtonToPanel(buttonPanel, "Thanh toán", Color.WHITE);
        // addButtonToPanel(buttonPanel, "Thống kê", Color.WHITE);

        // Thêm buttonPanel vào vùng CENTER của menuPanel
        menuPanel.add(buttonPanel, BorderLayout.CENTER);

        // Thêm menuPanel vào vùng WEST của TrangChuGUI
        this.add(menuPanel, BorderLayout.WEST);

        // -----------------------
        // Tạo contentPanel bao quanh hai vùng:
        // 1. helloPanel ở NORTH (hiển thị "Hello" và thời gian)
        // 2. dynamicContentPanel ở CENTER (hiển thị giao diện động)
        // -----------------------
        contentPanel.setLayout(new BorderLayout());
        
        // Tạo helloPanel sử dụng BorderLayout để tách vị trí trái – phải
        RoundedPanel helloPanel = new RoundedPanel(30, 30, Color.WHITE);
        helloPanel.setLayout(new BorderLayout());
        helloPanel.setBackground(Color.WHITE);
        helloPanel.setPreferredSize(new Dimension(0, 70));
        
        // Label "Hello" ở bên trái
        JLabel helloLabel = new JLabel("Hello");
        helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        helloLabel.setForeground(Color.BLACK);
        // Thêm khoảng cách (padding) bên trái
        helloLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        helloPanel.add(helloLabel, BorderLayout.WEST);
        
        // Label hiển thị thời gian ở bên phải
        JLabel clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clockLabel.setForeground(Color.BLACK);
        // Thêm khoảng cách (padding) bên phải
        clockLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        helloPanel.add(clockLabel, BorderLayout.EAST);
        
        // Định dạng giờ theo mẫu HH:mm:ss
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clockLabel.setText(LocalTime.now().format(timeFormatter));
            }
        });
        timer.start();
        
        // Thêm helloPanel vào vùng NORTH của contentPanel
        contentPanel.add(helloPanel, BorderLayout.NORTH);

        // dynamicContentPanel dành cho giao diện động, để chèn QuanLiNhanVienGUI hay GUI khác
        dynamicContentPanel.setOpaque(false);
        contentPanel.add(dynamicContentPanel, BorderLayout.CENTER);

        // Thêm contentPanel vào vùng CENTER của TrangChuGUI
        this.add(contentPanel, BorderLayout.CENTER);
    }

    // Phương thức tạo nút với hiệu ứng hover và ActionListener
    private void addButtonToPanel(JPanel buttonPanel, String buttonText, Color backgroundColor, String iconPath) {
        JButton button = new JButton(buttonText);
        
        // Đặt icon cho nút
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Thay đổi kích thước icon nếu cần
        button.setIcon(new ImageIcon(scaledIcon)); // Đặt icon vào nút
        
        // Căn chữ nút về bên trái
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
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
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    
        // Nếu nút "Quản lí nhân viên" được bấm thì hiển thị giao diện QuanLiNhanVienGUI
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonText.equals("Quản lí nhân viên")) {
                    showQuanLiNhanVien();
                }
                // Bạn có thể xử lý ActionListener cho các nút khác ở đây
                if (buttonText.equals("Quản lí tài khoản")){
                    showQuanLiTaiKhoan();
                }
            }
        });
    
        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // Phương thức hiển thị giao diện QuanLiNhanVienGUI bên trong dynamicContentPanel
    private void showQuanLiNhanVien() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiNhanVienGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }
    private void showQuanLiTaiKhoan(){
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiTaiKhoan(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Trang Chủ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 650);
        TrangChuGUI dashboard = new TrangChuGUI();
        frame.add(dashboard);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}