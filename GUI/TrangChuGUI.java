package GUI;

import Custom.MyButton;
import Custom.RoundedButton;
import Custom.RoundedPanel;
import DTO.TaiKhoanDTO;

import javax.swing.*;

import BUS.TaiKhoanBUS;
import Custom.Utilities;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrangChuGUI extends JPanel {
    // dynamicContentPanel - vùng chứa giao diện động (như QuanLiNhanVienGUI)
    private final JPanel dynamicContentPanel = new JPanel(new BorderLayout());
    private TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
    public TrangChuGUI(TaiKhoanDTO taikhoan,JFrame frame) {
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
        ImageIcon icon = new ImageIcon("Resources\\Image\\FastFoodIcon.png");
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
        //     addButtonToPanel(buttonPanel, "Quản lí nhân viên", "Resources\\Image\\EmployeeIcon.png");
        //     addButtonToPanel(buttonPanel, "Quản lí khách hàng", "Resources\\Image\\CustomerIcon.png");
        //     addButtonToPanel(buttonPanel, "Quản lí thức ăn", "Resources\\Image\\ThucAn.png");
        //     addButtonToPanel(buttonPanel, "Quản lí công thức", "Resources\\Image\\CongThuc.png");
        //     addButtonToPanel(buttonPanel, "Quản lí nguyên liệu", "Resources\\Image\\NguyenLieu.png");
        //     addButtonToPanel(buttonPanel, "Quản lí tài khoản", "Resources\\Image\\AccountIcon.png");
        //     addButtonToPanel(buttonPanel, "Quản lí ca - lịch làm", "Resources\\Image\\Shift.png");
        //     addButtonToPanel(buttonPanel, "Chấm công", "Resources\\Image\\Timekeeping.png");
        //     addButtonToPanel(buttonPanel, "Quản lí nhập hàng", "Resources\\Image\\EntryProduct.png");
        //     addButtonToPanel(buttonPanel, "Quản lí khuyến mãi", "Resources\\Image\\Promotion.png");
        //     addButtonToPanel(buttonPanel, "Thanh toán", "Resources\\Image\\Payment.png");
        //     addButtonToPanel(buttonPanel, "Thống kê", "Resources\\Image\\Statistic.png");
        System.out.println(taikhoan.getVaiTro());
        switch (taikhoan.getVaiTro()) {
            case "Admin":
                addButtonToPanel(buttonPanel, "Quản lí tài khoản", "Resources\\Image\\AccountIcon.png");
                break;
            case "Cashier":
                addButtonToPanel(buttonPanel, "Quản lí khách hàng", "Resources\\Image\\CustomerIcon.png");
                addButtonToPanel(buttonPanel, "Quản lí hóa đơn", "Resources\\Image\\Bill.png");
                addButtonToPanel(buttonPanel, "Thanh toán", "Resources\\Image\\Payment.png");
                addButtonToPanel(buttonPanel, "Thống kê", "Resources\\Image\\Statistic.png");
                addButtonToPanel(buttonPanel, "Quản lí tài khoản", "Resources\\Image\\AccountIcon.png");
                break;
            case "Accountant":
                addButtonToPanel(buttonPanel, "Quản lí nhập hàng", "Resources\\Image\\EntryProduct.png");
                addButtonToPanel(buttonPanel, "Quản lí khuyến mãi", "Resources\\Image\\Promotion.png");
                addButtonToPanel(buttonPanel, "Quản lí hóa đơn", "Resources\\Image\\Bill.png");
                addButtonToPanel(buttonPanel, "Thanh toán", "Resources\\Image\\Payment.png");
                addButtonToPanel(buttonPanel, "Thống kê", "Resources\\Image\\Statistic.png");
                addButtonToPanel(buttonPanel, "Quản lí tài khoản", "Resources\\Image\\AccountIcon.png");
                break;
            case "StorageManager":
                addButtonToPanel(buttonPanel, "Quản lí thức ăn", "Resources\\Image\\ThucAn.png");
                addButtonToPanel(buttonPanel, "Quản lí công thức", "Resources\\Image\\CongThuc.png");
                addButtonToPanel(buttonPanel, "Quản lí nguyên liệu", "Resources\\Image\\NguyenLieu.png");
                addButtonToPanel(buttonPanel, "Quản lí nhập hàng", "Resources\\Image\\EntryProduct.png");
                addButtonToPanel(buttonPanel, "Thống kê", "Resources\\Image\\Statistic.png");
                addButtonToPanel(buttonPanel, "Quản lí nhà cung cấp", "Resources\\Image\\Provider.png");
                addButtonToPanel(buttonPanel, "Quản lí tài khoản", "Resources\\Image\\AccountIcon.png");
                break;
            case "Manager":
                addButtonToPanel(buttonPanel, "Quản lí nhân viên", "Resources\\Image\\EmployeeIcon.png");
                addButtonToPanel(buttonPanel, "Quản lí khách hàng", "Resources\\Image\\CustomerIcon.png");
                addButtonToPanel(buttonPanel, "Quản lí thức ăn", "Resources\\Image\\ThucAn.png");
                addButtonToPanel(buttonPanel, "Quản lí công thức", "Resources\\Image\\CongThuc.png");
                addButtonToPanel(buttonPanel, "Quản lí nguyên liệu", "Resources\\Image\\NguyenLieu.png");
                addButtonToPanel(buttonPanel, "Quản lí ca - lịch làm", "Resources\\Image\\Shift.png");
                addButtonToPanel(buttonPanel, "Chấm công", "Resources\\Image\\Timekeeping.png");
                addButtonToPanel(buttonPanel, "Quản lí khuyến mãi", "Resources\\Image\\Promotion.png");
                addButtonToPanel(buttonPanel, "Quản lí hóa đơn", "Resources\\Image\\Bill.png");
                addButtonToPanel(buttonPanel, "Thống kê", "Resources\\Image\\Statistic.png");
                addButtonToPanel(buttonPanel, "Quản lí tài khoản", "Resources\\Image\\AccountIcon.png");
                break;
            default:
                break;
        }
        
        // Thêm các nút khác nếu cần:
        // addButtonToPanel(buttonPanel, "Quản lí thức ăn", Color.WHITE);
        // addButtonToPanel(buttonPanel, "Thanh toán", Color.WHITE);
        // addButtonToPanel(buttonPanel, "Thống kê", Color.WHITE);
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout());
        RoundedButton exitButton = new RoundedButton("Đăng xuất", 10,10);
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setBackground(Color.decode("#000000"));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitButton.setFocusPainted(false);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(Color.decode("#7e7f80"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.decode("#000000"));
            }
        });
        exitButton.addActionListener(e -> {
            int pane = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pane == JOptionPane.YES_OPTION) {
                frame.dispose(); // Đóng frame hiện tại
                DangNhapGUI dangnhap = new DangNhapGUI();
                dangnhap.showDangNhapGUI();
            }
        });
        exitPanel.add(exitButton);
        buttonPanel.add(exitPanel,BorderLayout.SOUTH);
        // Thêm buttonPanel vào vùng CENTER của menuPanel
        menuPanel.add(buttonPanel, BorderLayout.CENTER);

        // Thêm menuPanel vào vùng WEST của TrangChuGUI
        this.add(menuPanel, BorderLayout.WEST);

        // -----------------------
        // Tạo contentPanel bao quanh hai vùng:
        // 1. helloPanel ở NORTH (hiển thị "Hello" và thời gian)
        // 2. dynamicContentPanel ở CENTER (hiển thị giao diện động)
        // -----------------------
        // contentPanel - vùng bao ngoài có bo tròn
        RoundedPanel contentPanel = new RoundedPanel(40, 40, Color.decode("#F5ECE0"));
        contentPanel.setLayout(new BorderLayout());
        
        // Tạo helloPanel sử dụng BorderLayout để tách vị trí trái – phải
        RoundedPanel helloPanel = new RoundedPanel(30, 30, Color.WHITE);
        helloPanel.setLayout(new BorderLayout());
        helloPanel.setBackground(Color.WHITE);
        helloPanel.setPreferredSize(new Dimension(0, 70));
        
        // Label "Hello" ở bên trái
        TaiKhoanBUS tk = new TaiKhoanBUS();
        tk.transferdata(taiKhoanDTO, taikhoan);
        String a = (tk.timNV(taikhoan.getTenDangNhap())).getTenNhanVien();
        JLabel helloLabel = new JLabel("Hello "+ a);
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
        Timer timer = new Timer(1000, e -> clockLabel.setText(LocalTime.now().format(timeFormatter)));
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
    private void addButtonToPanel(JPanel buttonPanel, String buttonText, String iconPath) {
        JButton button = new JButton(buttonText);
        
        // Đặt icon cho nút
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Thay đổi kích thước icon nếu cần
        button.setIcon(new ImageIcon(scaledIcon)); // Đặt icon vào nút
        
        // Căn chữ nút về bên trái
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(Color.WHITE);
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
                button.setBackground(Color.WHITE);
            }
        });
    
        button.addActionListener(e -> {
            if (buttonText.equals("Quản lí nhân viên")) {
                showQuanLiNhanVien();
            }
            if (buttonText.equals("Quản lí tài khoản")){
                showQuanLiTaiKhoan(taiKhoanDTO);
            }
            if (buttonText.equals("Quản lí ca - lịch làm")){
                showQuanLiCaLam();
            }
            if (buttonText.equals("Chấm công")){
                showChamCong();
            }
            if (buttonText.equals("Quản lí nhập hàng")){
                showQuanLiNhapHang();
            }
            if (buttonText.equals("Quản lí khuyến mãi")){
                showQuanLiKhuyenMai();
            }
            if (buttonText.equals("Quản lí thức ăn")) {
                showQuanLiThucAn();
            }
            if (buttonText.equals("Quản lí công thức")) {
                showQuanLiCongThuc();
            }
            if (buttonText.equals("Quản lí nguyên liệu")) {
                showQuanLiNguyenLieu();
            }
            if (buttonText.equals("Thanh toán")) {
                showThanhToan();
            }
            if (buttonText.equals("Thống kê")) {
                showThongKe();
            }
            if (buttonText.equals("Quản lí khách hàng")) {
                showQuanLiKhachHang();
            }
            if (buttonText.equals("Quản lí hóa đơn")) {
                showQuanLiHoaDon();
            }
            if (buttonText.equals("Quản lí nhà cung cấp")) {
                showQuanLiNhaCungCap();
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

    private void showQuanLiTaiKhoan(TaiKhoanDTO taiKhoanDTO){
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiTaiKhoanGUI(taiKhoanDTO), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiCaLam(){
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiCaLamGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showChamCong(){
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new ChamCongGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiNhapHang() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLyNhapHangGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiKhuyenMai(){
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiKhuyenMaiGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiThucAn() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiThucAnGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiCongThuc() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiCongThucGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiNguyenLieu() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiNguyenLieuGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showThanhToan() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new ThanhToanGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showThongKe() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new ThongKeGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiKhachHang() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiKhachHangGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiHoaDon() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiHoaDonGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiNhaCungCap() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiNhaCungCapGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }
}