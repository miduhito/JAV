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

    public ThanhToanGUI() {
        super(50, 50, Color.decode("#F5ECE0")); // RoundedPanel với góc bo 50px
        this.setLayout(new BorderLayout());

        thanhToanBUS = new ThanhToanBUS();
        danhSachChiTietHoaDon = new ArrayList<>(); // Danh sách chi tiết hóa đơn
        thanhToanListModel = new DefaultListModel<>(); // Khởi tạo danh sách để hiển thị
        thucAnBUS = new ThucAnBUS();
        chiTietHD = new ArrayList<>();
        selectedFoodList = new ArrayList<>();

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
                    if (selectedIndex != -1) { // Kiểm tra xem có phần tử nào được chọn không
                        String selectedItem = thanhToanListModel.getElementAt(selectedIndex);
                        String foodSelectedNameParts[] = selectedItem.split("x");
                        String foodSelectedName = foodSelectedNameParts[0].split(" - Giá: ")[0]; // Lấy tên thức ăn
                        Double foodSelectedPrice = Double.parseDouble(foodSelectedNameParts[0].split(" - Giá: ")[1]); // Lấy giá thức ăn
                        int soLuong = Integer.parseInt(foodSelectedNameParts[1]);

                        Double donGia = foodSelectedPrice/soLuong;
                        Double gia = foodSelectedPrice - donGia;
                        soLuong = soLuong - 1;
                        thanhToanListModel.set(selectedIndex, foodSelectedName + " - Giá: " + gia + " x" + soLuong);
                        for(HashMap<String, Integer> item: chiTietHD) {
                            if(item.containsKey(selectedFoodList.get(selectedIndex))) {
                                item.put(selectedFoodList.get(selectedIndex), soLuong);
                                break;  
                            }
                        }
                        tongTienField.setText(String.valueOf(Integer.parseInt(tongTienField.getText()) - (int) Math.round(donGia)));
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
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(
                null, 
                "Bạn có chắc chắn muốn thanh toán?", 
                "Xác nhận thanh toán", 
                JOptionPane.YES_NO_OPTION
            );
        
            // Kiểm tra lựa chọn của người dùng
            if (confirm == JOptionPane.YES_OPTION) {
                thanhToanBUS.luuHoaDon(
                    maHoaDonField.getText(), // Mã hóa đơn
                    maNhanVienBox.getSelectedItem().toString(), // Mã nhân viên
                    sdtKhachHangField.getText(), // Số điện thoại khách hàng
                    ngayLapField.getText(), // Ngày lập
                    Double.parseDouble(tongTienField.getText()), // Tổng tiền
                    danhSachChiTietHoaDon // Chi tiết hóa đơn
                );
        
                for (HashMap<String, Integer> item : chiTietHD) {
                    for (HashMap.Entry<String, Integer> ta : item.entrySet()) {
                        String key = ta.getKey();       // Lấy key từ entrySet để lấy mã thức ăn
                        Integer value = ta.getValue(); // Lấy value từ entrySet để lấy số lượng thức ăn được mua
                        System.out.println(key);
                        System.out.println(value);
                
                        // Truyền key và value vào phương thức
                        thucAnBUS.updateSoLuongThucAn(key, value);
                    }
                }

                JOptionPane.showMessageDialog(null, "Hóa đơn đã được lưu!");
        
                // Reset các field
                maHoaDonField.setText(thanhToanBUS.generateMaHoaDon());
                ngayLapField.setText(thanhToanBUS.getTodayDate());
                sdtKhachHangField.setText("");
                tongTienField.setText("0");
                thanhToanListModel.clear();
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
        // Tạo panel chính chứa hai phần
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10)); // Sử dụng BorderLayout
        rightPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Thức Ăn"));
        rightPanel.setBackground(Color.WHITE);

        // Phần dưới: Danh sách thức ăn
        JPanel foodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Sử dụng FlowLayout hoặc GridLayout như trước

        // Phần trên: Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Sử dụng FlowLayout để căn chỉnh
        searchPanel.setBackground(Color.WHITE);
        JTextField searchField = new JTextField(); // Ô nhập tìm kiếm
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập tên thức ăn để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JButton searchButton = new JButton("Tìm Kiếm"); // Nút tìm kiếm
        searchButton.addActionListener(e -> {
            loadTableData(foodPanel, searchField.getText());
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        loadTableData(foodPanel, "");

        applyPromotion.addActionListener(e -> {
            FormApDungKhuyenMai(danhSachChiTietHoaDon, tongTienField);
        });

        // Thêm hai phần vào panel chính
        rightPanel.add(searchPanel, BorderLayout.NORTH); // Phần trên chứa thanh tìm kiếm
        rightPanel.add(foodPanel, BorderLayout.CENTER); // Phần dưới chứa danh sách thức ăn

        mainPanel.add(rightPanel);
    }

    public void loadTableData(JPanel foodPanel, String searchText) {
        // Xóa toàn bộ món ăn trong foodPanel
        foodPanel.removeAll();
        foodPanel.revalidate(); // Cập nhật lại bố cục
        foodPanel.repaint(); // Vẽ lại giao diện
    
        // Lấy danh sách thức ăn phù hợp với tìm kiếm
        List<ThucAnDTO> thucAnList;
        if (searchText.isEmpty()) { // Kiểm tra chuỗi rỗng đúng cách
            thucAnList = thanhToanBUS.getDanhSachThucAn();
        } else {
            thucAnList = thucAnBUS.findThucAn(searchText);
        }
    
        // Thêm các món ăn vào foodPanel
        for (ThucAnDTO thucAn : thucAnList) {
            ImageIcon foodImage = new ImageIcon(thucAn.getAnhThucAn());
            Image scaledImage = foodImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledFoodImage = new ImageIcon(scaledImage);
    
            JButton foodButton = new JButton(scaledFoodImage);
            foodButton.setToolTipText(thucAn.getTenThucAn());
            foodButton.setPreferredSize(new Dimension(100, 100));
            foodButton.setBorder(BorderFactory.createEmptyBorder());
    
            // Giữ nguyên sự kiện của foodButton
            foodButton.addActionListener(e -> {
                if (thucAn.getSoLuong() > 0) {
                    boolean flag = true;
                    for (int i = 0; i < thanhToanListModel.size(); i++) {
                        String[] foodSelectedNameParts = thanhToanListModel.get(i).split("x");
                        String foodSelectedName = foodSelectedNameParts[0].split(" - Giá: ")[0]; // Lấy tên thức ăn
                        Double foodSelectedPrice = Double.parseDouble(foodSelectedNameParts[0].split(" - Giá: ")[1]); // Lấy giá thức ăn
                        int soLuong = Integer.parseInt(foodSelectedNameParts[1]);
    
                        if (thucAn.getMaThucAn().equals(selectedFoodList.get(i))) {
                            flag = false;
                            soLuong++;
                            Double gia = foodSelectedPrice + thucAn.getGia();
                            thanhToanListModel.set(i, thucAn.getTenThucAn() + " - Giá: " + gia + " x" + soLuong);
                            for (HashMap<String, Integer> item : chiTietHD) {
                                if (item.containsKey(thucAn.getMaThucAn())) {
                                    item.put(thucAn.getMaThucAn(), soLuong);
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
                    tongTienField.setText(String.valueOf(Integer.parseInt(tongTienField.getText()) + (int) Math.round(thucAn.getGia())));
                } else {
                    JOptionPane.showMessageDialog(null, "Số lượng của thức ăn đã hết", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            });
    
            foodPanel.add(foodButton);
        }
    
        // Sau khi thêm lại dữ liệu, làm mới giao diện
        foodPanel.revalidate();
        foodPanel.repaint();
    }

    public void FormApDungKhuyenMai(List<ChiTietHoaDonDTO> danhSachChiTietHoaDon, JTextField tongTienField) {
        // Tạo JFrame
        JFrame formApplyPromotion = new JFrame("Áp dụng khuyến mãi");
        formApplyPromotion.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        formApplyPromotion.setSize(800, 600); // Kích thước form mặc định
        formApplyPromotion.setLayout(new BorderLayout(10, 10));
    
        // Title (Tiêu đề)
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel promotionTitle = new JLabel("Áp dụng khuyến mãi", SwingConstants.CENTER);
        promotionTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(promotionTitle);
        formApplyPromotion.add(titlePanel, BorderLayout.NORTH);
    
        // Main Panel chứa tất cả các thành phần bên trong
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout để căn chỉnh hợp lý
        formApplyPromotion.add(mainPanel, BorderLayout.CENTER);
    
        // Panel tìm kiếm
        JLabel searchIcon = new JLabel();
        ImageIcon search = new ImageIcon("Resources\\Image\\MagnifyingGlass.png");
        Image scaledIcon = search.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchIcon.setIcon(new ImageIcon(scaledIcon));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30)); // Kích thước thanh tìm kiếm
    
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);
    
        mainPanel.add(searchPanel, BorderLayout.NORTH);
    
        // Panel trung tâm: chứa bảng danh sách khuyến mãi và danh sách khuyến mãi đã chọn
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Sử dụng GridLayout để chia đều không gian
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    
        // Panel danh sách khuyến mãi
        JPanel promotionsPanel = new JPanel(new BorderLayout());
        promotionsPanel.setBackground(Color.WHITE);
        promotionsPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi")); // Viền tiêu đề
    
        DefaultTableModel promotionsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
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
        promotionsTable.setPreferredScrollableViewportSize(new Dimension(800, 150)); // Kích thước bảng danh sách khuyến mãi
    
        promotionsPanel.add(new JScrollPane(promotionsTable), BorderLayout.CENTER);
        centerPanel.add(promotionsPanel);
    
        // Panel danh sách khuyến mãi đã chọn
        JPanel selectedPromotionsPanel = new JPanel(new BorderLayout());
        selectedPromotionsPanel.setBackground(Color.WHITE);
        selectedPromotionsPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi đã chọn")); // Viền tiêu đề
    
        DefaultTableModel selectedPromotionsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        selectedPromotionsModel.setColumnIdentifiers(new Object[]{"Mã khuyến mãi", "Tên khuyến mãi", "Loại khuyến mãi", "Điều kiện"});

        JTable selectedPromotionsTable = new JTable(selectedPromotionsModel);
        selectedPromotionsTable.setRowHeight(35);
        selectedPromotionsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedPromotionsTable.setShowGrid(false);
        selectedPromotionsTable.setIntercellSpacing(new Dimension(0, 0));
        selectedPromotionsTable.setFocusable(false);
        selectedPromotionsTable.setBackground(Color.WHITE);
        selectedPromotionsTable.setPreferredScrollableViewportSize(new Dimension(800, 150)); // Kích thước bảng danh sách đã chọn
    
        selectedPromotionsPanel.add(new JScrollPane(selectedPromotionsTable), BorderLayout.CENTER);
        centerPanel.add(selectedPromotionsPanel);

        // Sự kiện thanh tìm kiếm
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchText = searchField.getText();
                KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
                List<KhuyenMaiDTO> danhSachKhuyenMai = khuyenMaiBUS.findKhuyenMai(searchText);
        
                // Xóa dữ liệu cũ
                promotionsModel.setRowCount(0);
        
                // Thêm dữ liệu mới
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

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searchText = searchField.getText();
                KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
                List<KhuyenMaiDTO> danhSachKhuyenMai = khuyenMaiBUS.findKhuyenMai(searchText);
        
                // Xóa dữ liệu cũ
                promotionsModel.setRowCount(0);
        
                // Thêm dữ liệu mới
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

            @Override
            public void changedUpdate(DocumentEvent e) {
                String searchText = searchField.getText();
                KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
                List<KhuyenMaiDTO> danhSachKhuyenMai = khuyenMaiBUS.findKhuyenMai(searchText);
        
                // Xóa dữ liệu cũ
                promotionsModel.setRowCount(0);
        
                // Thêm dữ liệu mới
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
    
        // Nút "Áp dụng"
        JButton applyButton = new JButton("Áp dụng");
        applyButton.setPreferredSize(new Dimension(100, 30));
        applyButton.addActionListener(e -> {
            KhuyenMaiBUS khuyenMaiBUS = new KhuyenMaiBUS();
            ChiTietKhuyenMaiBUS chiTietKhuyenMaiBUS = new ChiTietKhuyenMaiBUS();
            ThanhToanBUS thanhToanBUS = new ThanhToanBUS();

            double totalDiscount = 0; // Tổng giá trị giảm giá
            try {
                for (int i = 0; i < selectedPromotionsModel.getRowCount(); i++) {
                    String maKhuyenMai = (String) selectedPromotionsModel.getValueAt(i, 0); // Lấy mã khuyến mãi
                    KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getDataById(maKhuyenMai); // Lấy thông tin khuyến mãi

                    // Kiểm tra trạng thái khuyến mãi
                    if (!khuyenMai.getTrangThai()) {
                        JOptionPane.showMessageDialog(null, "Khuyến mãi " + khuyenMai.getTenKhuyenMai() + " đã hết hạn hoặc không khả dụng.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }

                    // Lấy danh sách chi tiết khuyến mãi (áp dụng cho từng món ăn)
                    ArrayList<ChiTietKhuyenMaiDTO> danhSachChiTietKM = chiTietKhuyenMaiBUS.getDataById(maKhuyenMai);

                    for (ChiTietHoaDonDTO chiTiet : danhSachChiTietHoaDon) {
                        double discountValue = 0;
                        for (ChiTietKhuyenMaiDTO chiTietKM : danhSachChiTietKM) {
                            if (chiTiet.getMaThucAn().equals(chiTietKM.getMaThucAn())) {
                                // Áp dụng giảm giá theo phần trăm hoặc số tiền
                                if (khuyenMai.getDonViKhuyenMai().equalsIgnoreCase("Phần trăm")) {
                                    discountValue = chiTiet.getThanhTien() * chiTietKM.getGiaTriKhuyenMai() / 100;
                                } else if (khuyenMai.getDonViKhuyenMai().equalsIgnoreCase("Số tiền")) {
                                    discountValue = chiTietKM.getGiaTriKhuyenMai();
                                }

                                // Đảm bảo giảm giá không vượt quá thành tiền
                                if (discountValue > chiTiet.getThanhTien()) {
                                    discountValue = chiTiet.getThanhTien();
                                }

                                // Cập nhật thành tiền sau khi áp dụng khuyến mãi
                                chiTiet.setThanhTien(chiTiet.getThanhTien() - discountValue);
                                totalDiscount += discountValue;
                            }
                        }

                        tongTienField.setText(String.valueOf(Double.parseDouble(tongTienField.getText()) - discountValue));
                    }
                }

                // Cập nhật tổng tiền sau khi áp dụng khuyến mãi
                // double newTotal = thanhToanBUS.tinhTongTien(danhSachChiTietHoaDon);
                // tongTienField.setText(String.valueOf(newTotal));

                JOptionPane.showMessageDialog(null, "Đã áp dụng khuyến mãi thành công!\nTổng tiền đã giảm: " + totalDiscount + " VNĐ.");
                formApplyPromotion.dispose(); // Đóng form khuyến mãi
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi áp dụng khuyến mãi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        mainPanel.add(applyButton, BorderLayout.SOUTH);
        formApplyPromotion.setVisible(true);
    
        // Sự kiện chọn khuyến mãi từ bảng
        promotionsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && promotionsTable.getSelectedRow() != -1) {
                int selectedRow = promotionsTable.getSelectedRow();
                String maKM = (String) promotionsModel.getValueAt(selectedRow, 0);
                String tenKM = (String) promotionsModel.getValueAt(selectedRow, 1);
                String loaiKM = (String) promotionsModel.getValueAt(selectedRow, 4);
                String dieuKien = (String) promotionsModel.getValueAt(selectedRow, 5);
    
                selectedPromotionsModel.addRow(new Object[]{maKM, tenKM, loaiKM, dieuKien});
            }
        });

        khuyenMaiBUS = new KhuyenMaiBUS();
        khuyenMaiBUS.loadTableData(promotionsModel);
    }

    // public void FormPhuongThucThanhToan() {
    //     // Tạo JFrame với kích thước hợp lý
    //     JFrame formPaymentMethod = new JFrame("Phương thức thanh toán");
    //     formPaymentMethod.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    //     formPaymentMethod.setSize(400, 300); // Điều chỉnh kích thước hợp lý
    //     formPaymentMethod.setLayout(new BorderLayout(10, 10));
    
    //     // Title (Tiêu đề)
    //     JPanel titlePanel = new JPanel();
    //     titlePanel.setBackground(Color.WHITE);
    //     JLabel paymentTitle = new JLabel("Phương thức thanh toán", SwingConstants.CENTER);
    //     paymentTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
    //     titlePanel.add(paymentTitle);
    //     formPaymentMethod.add(titlePanel, BorderLayout.NORTH);
    
    //     // Center Panel
    //     JPanel centerPanel = new JPanel();
    //     centerPanel.setLayout(new BorderLayout());
    
    //     JLabel ptttLabel = new JLabel("Chọn phương thức:");
    //     JComboBox<String> ptttBox = new JComboBox<>(new String[]{"Tiền mặt", "Thẻ Visa", "Mã QR"});
    //     ptttBox.setPreferredSize(new Dimension(150, 25)); // Điều chỉnh kích thước ComboBox
    
    //     JPanel dynamicPanel = new JPanel();
    //     dynamicPanel.setLayout(new GridLayout(3, 1));
    
    //     JLabel paymentDetailLabel = new JLabel("Chọn phương thức thanh toán.");
    //     JTextField inputField = new JTextField(15);
    //     JButton confirmTransactionButton = new JButton("Xác nhận thao tác");
    
    //     dynamicPanel.add(paymentDetailLabel);
    //     dynamicPanel.add(inputField);
    //     dynamicPanel.add(confirmTransactionButton);
    
    //     ptttBox.addActionListener(e -> {
    //         String selectedMethod = (String) ptttBox.getSelectedItem();
    //         switch (selectedMethod) {
    //             case "Tiền mặt":
    //                 paymentDetailLabel.setText("Nhập số tiền khách đưa:");
    //                 inputField.setVisible(true);
    //                 inputField.setText(""); // Cho phép nhập số tiền
    //                 break;
    //             case "Thẻ Visa":
    //                 paymentDetailLabel.setText("Quẹt/cắm thẻ và nhập mã PIN:");
    //                 inputField.setVisible(false); // Không cần nhập thông tin
    //                 inputField.setText(""); // Đảm bảo không có dữ liệu dư thừa
    //                 break;
    //             case "Mã QR":
    //                 paymentDetailLabel.setText("Quét mã QR trên ứng dụng:");
    //                 inputField.setVisible(false); // Không cần nhập thông tin
    //                 inputField.setText("");
    //                 break;
    //         }
    //     });
    
    //     JLabel transactionStatusLabel = new JLabel("");
    
    //     confirmTransactionButton.addActionListener(e -> {
    //         String selectedMethod = (String) ptttBox.getSelectedItem();
    //         switch (selectedMethod) {
    //             case "Tiền mặt":
    //                 transactionStatusLabel.setText("Tiền mặt đã xác nhận: " + inputField.getText() + " VND");
    //                 break;
    //             case "Thẻ Visa":
    //                 transactionStatusLabel.setText("Thẻ đã quẹt và tiền đã chuyển.");
    //                 break;
    //             case "Mã QR":
    //                 transactionStatusLabel.setText("Khách đã quét mã QR và tiền đã chuyển.");
    //                 break;
    //         }
    //     });
    
    //     dynamicPanel.add(transactionStatusLabel);
    
    //     centerPanel.add(ptttLabel, BorderLayout.NORTH);
    //     centerPanel.add(ptttBox, BorderLayout.CENTER);
    //     centerPanel.add(dynamicPanel, BorderLayout.SOUTH);
    
    //     // Nút xác nhận thanh toán
    //     JPanel buttonPanel = new JPanel();
    //     JButton confirmPaymentButton = new JButton("Xác nhận thanh toán");
    //     confirmPaymentButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
    //     buttonPanel.add(confirmPaymentButton);
    
    //     confirmPaymentButton.addActionListener(e -> {
    //         if (!transactionStatusLabel.getText().isEmpty()) {
    //             JOptionPane.showMessageDialog(formPaymentMethod, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    //         } else {
    //             JOptionPane.showMessageDialog(formPaymentMethod, "Vui lòng xác nhận thao tác thanh toán trước!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    //         }
    //     });
    
    //     formPaymentMethod.add(centerPanel, BorderLayout.CENTER);
    //     formPaymentMethod.add(buttonPanel, BorderLayout.SOUTH);
    
    //     formPaymentMethod.setVisible(true);
    // }
}