package GUI;

import Custom.MyButton;
import Custom.RoundedButton;
import Custom.RoundedPanel;
import DTO.TaiKhoanDTO;
import BUS.TaiKhoanBUS;
import BUS.HoaDonBUS;
import BUS.ChiTietHoaDonBUS;
import BUS.ThucAnBUS;
import DAO.HoaDonDAO;
import DAO.ChiTietHoaDonDAO;
import DTO.ThongKeDTO;
import DTO.ThucAnDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrangChuGUI extends JPanel {
    private final JPanel dynamicContentPanel = new JPanel();
    private TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();

    public TrangChuGUI(TaiKhoanDTO taikhoan, JFrame frame) {
        this.setLayout(new BorderLayout());

        RoundedPanel menuPanel = new RoundedPanel(50, 50, Color.decode("#F5ECE0"));
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(250, 0));

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
        menuPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

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

        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout());
        RoundedButton exitButton = new RoundedButton("Đăng xuất", 10, 10);
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
                frame.dispose();
                DangNhapGUI dangnhap = new DangNhapGUI();
                dangnhap.showDangNhapGUI();
            }
        });
        exitPanel.add(exitButton);
        buttonPanel.add(exitPanel, BorderLayout.SOUTH);
        menuPanel.add(buttonPanel, BorderLayout.CENTER);

        this.add(menuPanel, BorderLayout.WEST);

        RoundedPanel contentPanel = new RoundedPanel(40, 40, Color.decode("#F5ECE0"));
        contentPanel.setLayout(new BorderLayout());

        RoundedPanel helloPanel = new RoundedPanel(30, 30, Color.WHITE);
        helloPanel.setLayout(new BorderLayout());
        helloPanel.setBackground(Color.WHITE);
        helloPanel.setPreferredSize(new Dimension(0, 70));

        TaiKhoanBUS tk = new TaiKhoanBUS();
        tk.transferdata(taiKhoanDTO, taikhoan);
        String a = (tk.timNV(taikhoan.getTenDangNhap())).getTenNhanVien();
        JLabel helloLabel = new JLabel("Hello " + a);
        helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        helloLabel.setForeground(Color.BLACK);
        helloLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        helloPanel.add(helloLabel, BorderLayout.WEST);

        JLabel clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        clockLabel.setForeground(Color.BLACK);
        clockLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        helloPanel.add(clockLabel, BorderLayout.EAST);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timer timer = new Timer(1000, e -> clockLabel.setText(LocalTime.now().format(timeFormatter)));
        timer.start();

        contentPanel.add(helloPanel, BorderLayout.NORTH);

        dynamicContentPanel.setLayout(new BoxLayout(dynamicContentPanel, BoxLayout.Y_AXIS));
        dynamicContentPanel.setOpaque(false);
        dynamicContentPanel.add(createWelcomePanel(a, taikhoan.getVaiTro()));
        contentPanel.add(dynamicContentPanel, BorderLayout.CENTER);

        this.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createWelcomePanel(String tenNhanVien, String vaiTro) {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel chứa ảnh
        RoundedPanel imagePanel = new RoundedPanel(20, 20, Color.WHITE);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(0, 200));
        imagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("Resources\\Image\\FastFoodIcon.png");
        Image scaledImage = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        welcomePanel.add(imagePanel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel chứa 3 panel thống kê
        JPanel statsContainer = new JPanel();
        statsContainer.setLayout(new GridLayout(1, 3, 10, 0));
        statsContainer.setOpaque(false);
        statsContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Lấy ngày hiện tại
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
        ThucAnBUS thucAnBUS = new ThucAnBUS();

        // Panel thống kê số hóa đơn
        RoundedPanel hoaDonPanel = new RoundedPanel(20, 20, Color.decode("#D1E7DD"));
        hoaDonPanel.setLayout(new BoxLayout(hoaDonPanel, BoxLayout.Y_AXIS));
        hoaDonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel hoaDonTitle = new JLabel("Tổng hóa đơn hôm nay");
        hoaDonTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hoaDonTitle.setForeground(Color.BLACK);
        hoaDonTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoaDonPanel.add(hoaDonTitle);

        int soHoaDon = 0;
        List<ThongKeDTO> hoaDonList = hoaDonDAO.getHoaDonTheoNgay(currentDate, currentDate);
        if (!hoaDonList.isEmpty()) {
            soHoaDon = (int) hoaDonList.get(0).getValue();
        }
        JLabel hoaDonValue = new JLabel(String.valueOf(soHoaDon));
        hoaDonValue.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        hoaDonValue.setForeground(Color.BLACK);
        hoaDonValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        hoaDonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        hoaDonPanel.add(hoaDonValue);
        statsContainer.add(hoaDonPanel);

        // Panel thống kê số khách hàng
        RoundedPanel khachHangPanel = new RoundedPanel(20, 20, Color.decode("#FFF3CD"));
        khachHangPanel.setLayout(new BoxLayout(khachHangPanel, BoxLayout.Y_AXIS));
        khachHangPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel khachHangTitle = new JLabel("Tổng khách hàng hôm nay");
        khachHangTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        khachHangTitle.setForeground(Color.BLACK);
        khachHangTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        khachHangPanel.add(khachHangTitle);

        int soKhachHang = 0;
        List<ThongKeDTO> khachHangList = hoaDonDAO.getKhachHangTheoNgay(currentDate, currentDate);
        if (!khachHangList.isEmpty()) {
            soKhachHang = (int) khachHangList.get(0).getValue();
        }
        JLabel khachHangValue = new JLabel(String.valueOf(soKhachHang));
        khachHangValue.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        khachHangValue.setForeground(Color.BLACK);
        khachHangValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        khachHangPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        khachHangPanel.add(khachHangValue);
        statsContainer.add(khachHangPanel);

        // Panel thống kê món ăn bán chạy
        RoundedPanel monAnPanel = new RoundedPanel(20, 20, Color.decode("#F8D7DA"));
        monAnPanel.setLayout(new BoxLayout(monAnPanel, BoxLayout.Y_AXIS));
        monAnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel monAnTitle = new JLabel("Món ăn bán chạy");
        monAnTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        monAnTitle.setForeground(Color.BLACK);
        monAnTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        monAnPanel.add(monAnTitle);

        String tenMonAn = "Chưa có dữ liệu";
        List<ThongKeDTO> sanPhamBanChay = chiTietHoaDonDAO.getSanPhamBanChay(currentDate, currentDate);
        if (!sanPhamBanChay.isEmpty()) {
            String maThucAn = sanPhamBanChay.get(0).getLabel();
            ThucAnDTO thucAn = thucAnBUS.getThucAnById(maThucAn);
            if (thucAn != null && thucAn.getTenThucAn() != null) {
                tenMonAn = thucAn.getTenThucAn();
            }
        }
        JLabel monAnValue = new JLabel(tenMonAn);
        monAnValue.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        monAnValue.setForeground(Color.BLACK);
        monAnValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        monAnPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        monAnPanel.add(monAnValue);
        statsContainer.add(monAnPanel);

        welcomePanel.add(statsContainer);

        return welcomePanel;
    }

    private void addButtonToPanel(JPanel buttonPanel, String buttonText, String iconPath) {
        JButton button = new JButton(buttonText);
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledIcon));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 40));
        button.setPreferredSize(new Dimension(250, 40));
        button.setMinimumSize(new Dimension(250, 40));
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
            if (buttonText.equals("Quản lí tài khoản")) {
                showQuanLiTaiKhoan(taiKhoanDTO);
            }
            if (buttonText.equals("Quản lí ca - lịch làm")) {
                showQuanLiCaLam();
            }
            if (buttonText.equals("Chấm công")) {
                showChamCong();
            }
            if (buttonText.equals("Quản lí nhập hàng")) {
                showQuanLiNhapHang();
            }
            if (buttonText.equals("Quản lí khuyến mãi")) {
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

    private void showQuanLiNhanVien() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiNhanVienGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiTaiKhoan(TaiKhoanDTO taiKhoanDTO) {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiTaiKhoanGUI(taiKhoanDTO), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showQuanLiCaLam() {
        dynamicContentPanel.removeAll();
        dynamicContentPanel.add(new QuanLiCaLamGUI(), BorderLayout.CENTER);
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }

    private void showChamCong() {
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

    private void showQuanLiKhuyenMai() {
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