package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

import BUS.ChiTietKhuyenMaiBUS;
import BUS.KhuyenMaiBUS;
import BUS.ThanhToanBUS;
import BUS.ThucAnBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.ThucAnDTO;
import DTO.ChiTietHoaDonDTO;
import DTO.ChiTietKhuyenMaiDTO;
import DTO.KhuyenMaiDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThanhToanGUI extends RoundedPanel {
    private JTextField tongTienField;
    private JTextField ngayLapField, maHoaDonField, sdtKhachHangField;
    private DefaultListModel<String> thanhToanListModel;
    private JList<String> thanhToanList;
    private List<ChiTietHoaDonDTO> danhSachChiTietHoaDon;
    private ThanhToanBUS thanhToanBUS;
    private JComboBox<String> maNhanVienBox, ptttBox;
    private KhuyenMaiBUS khuyenMaiBUS;
    private ThucAnBUS thucAnBUS;
    private List<HashMap<String, Integer>> chiTietHD;
    private List<String> selectedFoodList;
    private List<String> selectedPromotions;
    private List<Integer> quantitySelectedPromotion;
    private ChiTietKhuyenMaiBUS chiTietKhuyenMaiBUS;

    public ThanhToanGUI() {
        super(50, 50, Color.decode("#F5ECE0")); // RoundedPanel với góc bo 50px
        this.setLayout(new BorderLayout());

        thanhToanBUS = new ThanhToanBUS();
        danhSachChiTietHoaDon = new ArrayList<>(); // Danh sách chi tiết hóa đơn
        thanhToanListModel = new DefaultListModel<>(); // Khởi tạo danh sách để hiển thị
        thucAnBUS = new ThucAnBUS();
        chiTietHD = new ArrayList<>();
        selectedFoodList = new ArrayList<>();
        selectedPromotions = new ArrayList<>();
        quantitySelectedPromotion = new ArrayList<>();
        chiTietKhuyenMaiBUS = new ChiTietKhuyenMaiBUS();
        khuyenMaiBUS = new KhuyenMaiBUS();

        // Tiêu đề
        JLabel title = new JLabel("Thanh Toán", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        this.add(title, BorderLayout.NORTH);

        // Panel chứa hai phần chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 10, 0));
        mainPanel.setBackground(Color.decode("#F5ECE0"));
        this.add(mainPanel, BorderLayout.CENTER);

        // Panel bên trái: Thông tin hóa đơn
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Hóa Đơn"));
        leftPanel.setBackground(Color.WHITE);

        // Trường nhập liệu
        leftPanel.add(new JLabel("Mã hóa đơn:"));
        maHoaDonField = new JTextField(thanhToanBUS.generateMaHoaDon()); // Tạm thời tạo mã hóa đơn
        maHoaDonField.setEditable(false);
        maHoaDonField.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(maHoaDonField);

        leftPanel.add(new JLabel("Ngày lập:"));
        ngayLapField = new JTextField(thanhToanBUS.getTodayDate()); // Mặc định là ngày hiện tại
        leftPanel.add(ngayLapField);

        leftPanel.add(new JLabel("Mã nhân viên:"));
        maNhanVienBox = new JComboBox<>();
        thanhToanBUS.loadMaNhanVienToBox(maNhanVienBox);
        leftPanel.add(maNhanVienBox);

        leftPanel.add(new JLabel("Số điện thoại khách hàng:"));
        sdtKhachHangField = new JTextField();
        leftPanel.add(sdtKhachHangField);

        leftPanel.add(new JLabel("Tổng tiền:"));
        tongTienField = new JTextField("0");
        tongTienField.setEditable(false);
        tongTienField.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(tongTienField);

        leftPanel.add(new JLabel("Phương thức thanh toán:"));
        String[] ptttList = {"Tiền mặt", "Thẻ tín dụng", "Mã QR"};
        ptttBox = new JComboBox<>(ptttList);
        leftPanel.add(ptttBox);

        // Danh sách thức ăn đã chọn
        thanhToanList = new JList<>(thanhToanListModel);
        thanhToanList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Kiểm tra nếu là nhấp đúp (double click)
                    int selectedIndex = thanhToanList.locationToIndex(e.getPoint()); // Lấy vị trí phần tử được chọn
                    if (selectedIndex != -1 && selectedIndex < selectedFoodList.size()) { // Kiểm tra hợp lệ
                        String selectedItem = thanhToanListModel.getElementAt(selectedIndex);
                        String[] foodSelectedNameParts = selectedItem.split("x");
                        String foodSelectedName = foodSelectedNameParts[0].split(" - Giá: ")[0]; // Lấy tên thức ăn
                        double foodSelectedPrice = Double.parseDouble(foodSelectedNameParts[0].split(" - Giá: ")[1]); // Lấy giá thức ăn
                        int soLuong = Integer.parseInt(foodSelectedNameParts[1]);
                        String maThucAn = selectedFoodList.get(selectedIndex);

                        if (soLuong > 0) {
                            double donGia = foodSelectedPrice / soLuong;
                            soLuong--;
                            double gia = foodSelectedPrice - donGia;
                            if (soLuong > 0) {
                                thanhToanListModel.set(selectedIndex, foodSelectedName + " - Giá: " + gia + " x" + soLuong);
                                // Cập nhật chiTietHD
                                for (HashMap<String, Integer> item : chiTietHD) {
                                    if (item.containsKey(maThucAn)) {
                                        item.put(maThucAn, soLuong);
                                        break;
                                    }
                                }
                                // Cập nhật danhSachChiTietHoaDon
                                for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                                    if (chiTiet.getMaThucAn().equals(maThucAn)) {
                                        chiTiet.setThanhTien(gia);
                                        break;
                                    }
                                }
                            } else {
                                thanhToanListModel.removeElementAt(selectedIndex);
                                selectedFoodList.remove(selectedIndex);
                                // Xóa khỏi chiTietHD
                                chiTietHD.removeIf(item -> item.containsKey(maThucAn));
                                // Xóa khỏi danhSachChiTietHoaDon
                                danhSachChiTietHoaDon.removeIf(chiTiet -> chiTiet.getMaThucAn().equals(maThucAn));

                                // Kiểm tra và xóa khuyến mãi liên quan
                                List<String> promotionsToRemove = new ArrayList<>();
                                List<Integer> quantitiesToRemove = new ArrayList<>();
                                for (int i = 0; i < selectedPromotions.size(); i++) {
                                    String maKhuyenMai = selectedPromotions.get(i);
                                    ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKM = chiTietKhuyenMaiBUS.getDataById(maKhuyenMai);
                                    boolean isApplicable = false;

                                    // Kiểm tra nếu khuyến mãi có áp dụng cho món ăn còn lại
                                    for (ChiTietKhuyenMaiDTO chiTietKM : danhSachChiTietKM) {
                                        for (HashMap<String, Integer> item : chiTietHD) {
                                            if (item.containsKey(chiTietKM.getMaThucAn())) {
                                                isApplicable = true;
                                                break;
                                            }
                                        }
                                        if (isApplicable) break;
                                    }

                                    // Nếu khuyến mãi không áp dụng cho món ăn nào còn lại, đánh dấu để xóa
                                    if (!isApplicable) {
                                        promotionsToRemove.add(maKhuyenMai);
                                        quantitiesToRemove.add(quantitySelectedPromotion.get(i));
                                    }
                                }

                                // Xóa các khuyến mãi không còn áp dụng
                                for (String maKhuyenMai : promotionsToRemove) {
                                    int index = selectedPromotions.indexOf(maKhuyenMai);
                                    selectedPromotions.remove(index);
                                    quantitySelectedPromotion.remove(index);
                                }
                            }
                            // Cập nhật tổng tiền (chưa tính khuyến mãi)
                            tongTienField.setText(String.valueOf(Integer.parseInt(tongTienField.getText()) - (int) Math.round(donGia)));
                        }
                    }
                }
            }
        });
        leftPanel.add(new JLabel("Danh sách món đã chọn:"));
        leftPanel.add(new JScrollPane(thanhToanList));

        // Spacer
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Nút thanh toán
        JButton payButton = new JButton("Thanh toán");
        payButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn thanh toán?",
                "Xác nhận thanh toán",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                thanhToanBUS.luuHoaDon(
                    maHoaDonField.getText(),
                    maNhanVienBox.getSelectedItem().toString(),
                    sdtKhachHangField.getText(),
                    ngayLapField.getText(),
                    Double.parseDouble(tongTienField.getText()),
                    danhSachChiTietHoaDon,
                    ptttBox.getSelectedItem().toString()
                );

                for (HashMap<String, Integer> item : chiTietHD) {
                    for (HashMap.Entry<String, Integer> ta : item.entrySet()) {
                        String key = ta.getKey();
                        Integer value = ta.getValue();
                        thucAnBUS.updateSoLuongThucAn(key, value);
                    }
                }

                JOptionPane.showMessageDialog(null, "Hóa đơn đã được lưu!");

                maHoaDonField.setText(thanhToanBUS.generateMaHoaDon());
                ngayLapField.setText(thanhToanBUS.getTodayDate());
                sdtKhachHangField.setText("");
                tongTienField.setText("0");
                thanhToanListModel.clear();
                selectedFoodList.clear();
                chiTietHD.clear();
                selectedPromotions.clear();
                quantitySelectedPromotion.clear();
                danhSachChiTietHoaDon.clear();
            } else {
                JOptionPane.showMessageDialog(null, "Thanh toán đã bị hủy.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JButton applyPromotion = new JButton("Áp dụng khuyến mãi");

        buttonPanel.add(payButton);
        buttonPanel.add(applyPromotion);
        leftPanel.add(buttonPanel);

        mainPanel.add(leftPanel);

        // Panel bên phải: Danh sách thức ăn
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Thức Ăn"));
        rightPanel.setBackground(Color.WHITE);

        JPanel foodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập tên thức ăn để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JButton searchButton = new JButton("Tìm Kiếm");
        searchButton.addActionListener(e -> {
            loadTableData(foodPanel, searchField.getText());
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        loadTableData(foodPanel, "");

        applyPromotion.addActionListener(e -> {
            FormApDungKhuyenMai(danhSachChiTietHoaDon, tongTienField);
        });

        rightPanel.add(searchPanel, BorderLayout.NORTH);
        rightPanel.add(foodPanel, BorderLayout.CENTER);

        mainPanel.add(rightPanel);
    }

    public void loadTableData(JPanel foodPanel, String searchText) {
        foodPanel.removeAll();
        foodPanel.revalidate();
        foodPanel.repaint();

        List<ThucAnDTO> thucAnList;
        if (searchText.isEmpty()) {
            thucAnList = thanhToanBUS.getDanhSachThucAn();
        } else {
            thucAnList = thucAnBUS.findThucAn(searchText);
        }

        for (ThucAnDTO thucAn : thucAnList) {
            ImageIcon foodImage = new ImageIcon(thucAn.getAnhThucAn());
            Image scaledImage = foodImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledFoodImage = new ImageIcon(scaledImage);

            JButton foodButton = new JButton(scaledFoodImage);
            foodButton.setToolTipText(thucAn.getTenThucAn());
            foodButton.setPreferredSize(new Dimension(100, 100));
            foodButton.setBorder(BorderFactory.createEmptyBorder());

            foodButton.addActionListener(e -> {
                if (thucAn.getSoLuong() > 0) {
                    boolean flag = true;
                    for (int i = 0; i < thanhToanListModel.size(); i++) {
                        String[] foodSelectedNameParts = thanhToanListModel.get(i).split("x");
                        String foodSelectedName = foodSelectedNameParts[0].split(" - Giá: ")[0];
                        double foodSelectedPrice = Double.parseDouble(foodSelectedNameParts[0].split(" - Giá: ")[1]);
                        int soLuong = Integer.parseInt(foodSelectedNameParts[1]);

                        if (thucAn.getMaThucAn().equals(selectedFoodList.get(i))) {
                            flag = false;
                            soLuong++;
                            double gia = foodSelectedPrice + thucAn.getGia();
                            thanhToanListModel.set(i, thucAn.getTenThucAn() + " - Giá: " + gia + " x" + soLuong);
                            for (HashMap<String, Integer> item : chiTietHD) {
                                if (item.containsKey(thucAn.getMaThucAn())) {
                                    item.put(thucAn.getMaThucAn(), soLuong);
                                    break;
                                }
                            }
                            // Cập nhật danhSachChiTietHoaDon
                            for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                                if (chiTiet.getMaThucAn().equals(thucAn.getMaThucAn())) {
                                    chiTiet.setThanhTien(gia);
                                    break;
                                }
                            }
                        }
                    }
                    if (flag) {
                        HashMap<String, Integer> ct = new HashMap<>();
                        thanhToanBUS.themChiTietHoaDon(thucAn, danhSachChiTietHoaDon);
                        thanhToanListModel.addElement(thucAn.getTenThucAn() + " - Giá: " + thucAn.getGia() + " x" + 1);
                        selectedFoodList.add(thucAn.getMaThucAn());
                        ct.put(thucAn.getMaThucAn(), 1);
                        chiTietHD.add(ct);
                    }
                    tongTienField.setText(String.valueOf((int) Double.parseDouble(tongTienField.getText()) + (int) Math.round(thucAn.getGia())));
                } else {
                    JOptionPane.showMessageDialog(null, "Số lượng của thức ăn đã hết", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            foodPanel.add(foodButton);
        }

        foodPanel.revalidate();
        foodPanel.repaint();
    }

    // Hàm lấy số lượng thức ăn
    private int getSoLuongThucAn(String maThucAn) {
        if (maThucAn == null || chiTietHD == null || chiTietHD.isEmpty()) {
            return 0;
        }
        for (HashMap<String, Integer> item : chiTietHD) {
            if (item != null && item.containsKey(maThucAn)) {
                Integer soLuong = item.get(maThucAn);
                return soLuong != null ? soLuong : 0;
            }
        }
        return 0;
    }

    public void FormApDungKhuyenMai(List<ChiTietHoaDonDTO> danhSachChiTietHoaDon, JTextField tongTienField) {
        JFrame formApplyPromotion = new JFrame("Áp dụng khuyến mãi");
        formApplyPromotion.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        formApplyPromotion.setSize(800, 600);
        formApplyPromotion.setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel promotionTitle = new JLabel("Áp dụng khuyến mãi", SwingConstants.CENTER);
        promotionTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(promotionTitle);
        formApplyPromotion.add(titlePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        formApplyPromotion.add(mainPanel, BorderLayout.CENTER);

        JLabel searchIcon = new JLabel();
        ImageIcon search = new ImageIcon("Resources\\Image\\MagnifyingGlass.png");
        Image scaledIcon = search.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchIcon.setIcon(new ImageIcon(scaledIcon));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);

        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);
        MyButton deletePromotionButton = new MyButton("Xóa", deleteIcon);
        deletePromotionButton.setPreferredSize(new Dimension(180, 40));
        deletePromotionButton.setBackground(Color.decode("#EC5228"));
        deletePromotionButton.setForeground(Color.WHITE);
        deletePromotionButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deletePromotionButton.setFocusPainted(false);
        deletePromotionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deletePromotionButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deletePromotionButton.setBackground(Color.decode("#EC5228"));
            }
        });

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        headerPanel.add(searchPanel);
        headerPanel.add(deletePromotionButton);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel promotionsPanel = new JPanel(new BorderLayout());
        promotionsPanel.setBackground(Color.WHITE);
        promotionsPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi"));

        DefaultTableModel promotionsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        promotionsModel.setColumnIdentifiers(new Object[]{"Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Loại khuyến mãi", "Điều kiện"});

        JTable promotionsTable = new JTable(promotionsModel);
        promotionsTable.setRowHeight(35);
        promotionsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        promotionsTable.setShowGrid(false);
        promotionsTable.setIntercellSpacing(new Dimension(0, 0));
        promotionsTable.setFocusable(false);
        promotionsTable.setBackground(Color.WHITE);
        promotionsTable.setPreferredScrollableViewportSize(new Dimension(800, 150));

        promotionsPanel.add(new JScrollPane(promotionsTable), BorderLayout.CENTER);
        centerPanel.add(promotionsPanel);

        JPanel selectedPromotionsPanel = new JPanel(new BorderLayout());
        selectedPromotionsPanel.setBackground(Color.WHITE);
        selectedPromotionsPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi đã chọn"));

        DefaultTableModel selectedPromotionsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        selectedPromotionsModel.setColumnIdentifiers(new Object[]{"Mã khuyến mãi", "Tên khuyến mãi", "Loại khuyến mãi", "Điều kiện", "Số lượng"});

        // Danh sách tạm thời để lưu khuyến mãi và giá trị giảm giá
        List<String> tempSelectedPromotions = new ArrayList<>(selectedPromotions);
        List<Integer> tempQuantitySelectedPromotion = new ArrayList<>(quantitySelectedPromotion);
        HashMap<String, Double> discountValues = new HashMap<>(); // Lưu discountValue cho mỗi maKhuyenMai

        // Tải khuyến mãi đã lưu vào selectedPromotionsModel
        if (!selectedPromotions.isEmpty()) {
            for (int i = 0; i < selectedPromotions.size(); i++) {
                String makm = selectedPromotions.get(i);
                KhuyenMaiDTO km = khuyenMaiBUS.getDataById(makm);
                if (km != null) {
                    String tenKM = km.getTenKhuyenMai();
                    String loaiKM = km.getDonViKhuyenMai();
                    String dieuKien = km.getDieuKienApDung();
                    int sl = quantitySelectedPromotion.get(i);
                    selectedPromotionsModel.addRow(new Object[]{makm, tenKM, loaiKM, dieuKien, sl});
                }
            }
        }

        JTable selectedPromotionsTable = new JTable(selectedPromotionsModel);
        selectedPromotionsTable.setRowHeight(35);
        selectedPromotionsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedPromotionsTable.setShowGrid(false);
        selectedPromotionsTable.setIntercellSpacing(new Dimension(0, 0));
        selectedPromotionsTable.setFocusable(false);
        selectedPromotionsTable.setBackground(Color.WHITE);
        selectedPromotionsTable.setPreferredScrollableViewportSize(new Dimension(800, 150));

        selectedPromotionsPanel.add(new JScrollPane(selectedPromotionsTable), BorderLayout.CENTER);
        centerPanel.add(selectedPromotionsPanel);

        // Hàm tính lại tổng tiền
        Runnable updateTotalPrice = () -> {
            // Reset thành tiền về giá gốc
            for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                ThucAnDTO thucAn = thucAnBUS.getThucAnById(chiTiet.getMaThucAn());
                if (thucAn == null) {
                    continue;
                }
                int soLuong = getSoLuongThucAn(chiTiet.getMaThucAn());
                chiTiet.setThanhTien(thucAn.getGia() * soLuong);
            }

            // Áp dụng khuyến mãi và lưu discountValue
            discountValues.clear();
            double totalDiscount = 0;
            for (int i = 0; i < selectedPromotionsModel.getRowCount(); i++) {
                String maKhuyenMai = (String) selectedPromotionsModel.getValueAt(i, 0);
                int quantity = (int) selectedPromotionsModel.getValueAt(i, 4);
                KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getDataById(maKhuyenMai);
                if (khuyenMai == null || !khuyenMai.getTrangThai()) {
                    continue;
                }

                ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKM = chiTietKhuyenMaiBUS.getDataById(maKhuyenMai);
                double promotionDiscount = 0;
                for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                    int soLuongThucAn = getSoLuongThucAn(chiTiet.getMaThucAn());
                    for (ChiTietKhuyenMaiDTO chiTietKM : danhSachChiTietKM) {
                        if (chiTiet.getMaThucAn().equals(chiTietKM.getMaThucAn())) {
                            double discountValue = 0;
                            if (khuyenMai.getDonViKhuyenMai().equalsIgnoreCase("Phần trăm")) {
                                discountValue = (chiTiet.getThanhTien() / (soLuongThucAn > 0 ? soLuongThucAn : 1)) * chiTietKM.getGiaTriKhuyenMai() / 100;
                            } else if (khuyenMai.getDonViKhuyenMai().equalsIgnoreCase("Số tiền")) {
                                discountValue = chiTietKM.getGiaTriKhuyenMai();
                            }

                            // Áp dụng giảm giá theo số lượng khuyến mãi
                            double appliedDiscount = discountValue * Math.min(quantity, soLuongThucAn);
                            if (appliedDiscount > chiTiet.getThanhTien()) {
                                appliedDiscount = chiTiet.getThanhTien();
                            }
                            chiTiet.setThanhTien(chiTiet.getThanhTien() - appliedDiscount);
                            promotionDiscount += appliedDiscount;
                        }
                    }
                }
                discountValues.put(maKhuyenMai, promotionDiscount / (quantity > 0 ? quantity : 1)); // Lưu discountValue trung bình mỗi lần áp dụng
                totalDiscount += promotionDiscount;
            }

            // Tính tổng tiền
            double newTotal = 0;
            for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                newTotal += chiTiet.getThanhTien();
            }
            tongTienField.setText(String.valueOf(Math.round(newTotal)));
        };

        // Sự kiện xóa khuyến mãi
        deletePromotionButton.addActionListener(e -> {
            int selectedIndex = selectedPromotionsTable.getSelectedRow();
            if (selectedIndex != -1) {
                String maKM = (String) selectedPromotionsModel.getValueAt(selectedIndex, 0);
                int currentQuantity = (int) selectedPromotionsModel.getValueAt(selectedIndex, 4);

                if (currentQuantity > 1) {
                    selectedPromotionsModel.setValueAt(currentQuantity - 1, selectedIndex, 4);
                    int index = tempSelectedPromotions.indexOf(maKM);
                    tempQuantitySelectedPromotion.set(index, currentQuantity - 1);
                } else {
                    selectedPromotionsModel.removeRow(selectedIndex);
                    int index = tempSelectedPromotions.indexOf(maKM);
                    tempSelectedPromotions.remove(index);
                    tempQuantitySelectedPromotion.remove(index);
                    discountValues.remove(maKM);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một khuyến mãi để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Sự kiện thanh tìm kiếm
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePromotionsTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePromotionsTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePromotionsTable();
            }

            private void updatePromotionsTable() {
                String searchText = searchField.getText();
                KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
                List<KhuyenMaiDTO> danhSachKhuyenMai = khuyenMaiBUS.findKhuyenMai(searchText);

                promotionsModel.setRowCount(0);

                for (KhuyenMaiDTO khuyenMai : danhSachKhuyenMai) {
                    promotionsModel.addRow(new Object[]{
                        khuyenMai.getMaKhuyenMai(),
                        khuyenMai.getTenKhuyenMai(),
                        khuyenMai.getNgayBatDau(),
                        khuyenMai.getNgayKetThuc(),
                        khuyenMai.getDonViKhuyenMai(),
                        khuyenMai.getDieuKienApDung()
                    });
                }
            }
        });

        // Sự kiện nhấp chuột để chọn khuyến mãi
        promotionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = promotionsTable.rowAtPoint(e.getPoint());
                if (row != -1) {
                    String maKM = (String) promotionsModel.getValueAt(row, 0);
                    String tenKM = (String) promotionsModel.getValueAt(row, 1);
                    String loaiKM = (String) promotionsModel.getValueAt(row, 4);
                    String dieuKien = (String) promotionsModel.getValueAt(row, 5);

                    // Tính tổng số lượng thức ăn đã chọn
                    int totalFoodQuantity = 0;
                    for (HashMap<String, Integer> item : chiTietHD) {
                        for (Integer quantity : item.values()) {
                            totalFoodQuantity += quantity;
                        }
                    }

                    // Tính tổng số lượng khuyến mãi hiện tại
                    int totalPromotionQuantity = tempQuantitySelectedPromotion.stream().mapToInt(Integer::intValue).sum();

                    if (totalPromotionQuantity >= totalFoodQuantity) {
                        JOptionPane.showMessageDialog(null, "Số lượng khuyến mãi đã đạt tối đa (bằng số lượng thức ăn)!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (tempSelectedPromotions.contains(maKM)) {
                        for (int i = 0; i < selectedPromotionsModel.getRowCount(); i++) {
                            if (selectedPromotionsModel.getValueAt(i, 0).equals(maKM)) {
                                int currentQuantity = (int) selectedPromotionsModel.getValueAt(i, 4);
                                if (totalPromotionQuantity + 1 <= totalFoodQuantity) {
                                    selectedPromotionsModel.setValueAt(currentQuantity + 1, i, 4);
                                    int index = tempSelectedPromotions.indexOf(maKM);
                                    tempQuantitySelectedPromotion.set(index, currentQuantity + 1);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Số lượng khuyến mãi đã đạt tối đa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                                }
                                break;
                            }
                        }
                    } else {
                        selectedPromotionsModel.addRow(new Object[]{maKM, tenKM, loaiKM, dieuKien, 1});
                        tempSelectedPromotions.add(maKM);
                        tempQuantitySelectedPromotion.add(1);
                    }
                }
            }
        });

        // Nút "Áp dụng"
        JButton applyButton = new JButton("Áp dụng");
        applyButton.setPreferredSize(new Dimension(100, 30));
        applyButton.addActionListener(e -> {
            // Reset thành tiền về giá gốc
            for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                ThucAnDTO thucAn = thucAnBUS.getThucAnById(chiTiet.getMaThucAn());
                if (thucAn == null) {
                    continue;
                }
                int soLuong = getSoLuongThucAn(chiTiet.getMaThucAn());
                chiTiet.setThanhTien(thucAn.getGia() * soLuong);
            }

            double totalDiscount = 0;
            try {
                for (int i = 0; i < selectedPromotionsModel.getRowCount(); i++) {
                    String maKhuyenMai = (String) selectedPromotionsModel.getValueAt(i, 0);
                    int quantity = (int) selectedPromotionsModel.getValueAt(i, 4);
                    KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getDataById(maKhuyenMai);
                    if (khuyenMai == null) {
                        JOptionPane.showMessageDialog(null, "Khuyến mãi " + maKhuyenMai + " không tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    if (!khuyenMai.getTrangThai()) {
                        JOptionPane.showMessageDialog(null, "Khuyến mãi " + khuyenMai.getTenKhuyenMai() + " đã hết hạn hoặc không khả dụng.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }

                    ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKM = chiTietKhuyenMaiBUS.getDataById(maKhuyenMai);
                    double promotionDiscount = 0;
                    for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                        int soLuongThucAn = getSoLuongThucAn(chiTiet.getMaThucAn());
                        for (ChiTietKhuyenMaiDTO chiTietKM : danhSachChiTietKM) {
                            if (chiTiet.getMaThucAn().equals(chiTietKM.getMaThucAn())) {
                                double discountValue = 0;
                                if (khuyenMai.getDonViKhuyenMai().equalsIgnoreCase("Phần trăm")) {
                                    discountValue = (chiTiet.getThanhTien() / (soLuongThucAn > 0 ? soLuongThucAn : 1)) * chiTietKM.getGiaTriKhuyenMai() / 100;
                                } else if (khuyenMai.getDonViKhuyenMai().equalsIgnoreCase("Số tiền")) {
                                    discountValue = chiTietKM.getGiaTriKhuyenMai();
                                }

                                // Áp dụng giảm giá theo số lượng khuyến mãi
                                double appliedDiscount = discountValue * Math.min(quantity, soLuongThucAn);
                                if (appliedDiscount > chiTiet.getThanhTien()) {
                                    appliedDiscount = chiTiet.getThanhTien();
                                }
                                chiTiet.setThanhTien(chiTiet.getThanhTien() - appliedDiscount);
                                promotionDiscount += appliedDiscount;
                            }
                        }
                    }
                    discountValues.put(maKhuyenMai, promotionDiscount / (quantity > 0 ? quantity : 1)); // Lưu discountValue
                    totalDiscount += promotionDiscount;
                }

                // Tính tổng tiền
                double newTotal = 0;
                for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                    newTotal += chiTiet.getThanhTien();
                }

                if (totalDiscount > newTotal) {
                    JOptionPane.showMessageDialog(null, "Tổng tiền giảm (" + totalDiscount + ") vượt quá tổng tiền hiện tại (" + newTotal + ")!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Cập nhật selectedPromotions và quantitySelectedPromotion
                selectedPromotions.clear();
                selectedPromotions.addAll(tempSelectedPromotions);
                quantitySelectedPromotion.clear();
                quantitySelectedPromotion.addAll(tempQuantitySelectedPromotion);

                tongTienField.setText(String.valueOf(Math.round(newTotal)));
                JOptionPane.showMessageDialog(null, "Đã áp dụng khuyến mãi thành công!\nTổng tiền đã giảm: " + totalDiscount + " VNĐ.");
                formApplyPromotion.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi áp dụng khuyến mãi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(applyButton, BorderLayout.SOUTH);
        formApplyPromotion.setVisible(true);

        thanhToanBUS.loadKhuyenMaiTable(promotionsModel, selectedFoodList);
    }
}