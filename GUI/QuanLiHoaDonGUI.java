package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;

public class QuanLiHoaDonGUI extends RoundedPanel{
    // Table model chứa dữ liệu khách hàng hiển thị trong bảng
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };

    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private Popup popup; // DÙng để hiển thị một menu gợi ý khi gõ số điện thoại khách hàng vào thanh text trong phần sửa hóa đơn

    public QuanLiHoaDonGUI() {
        super(50, 50, Color.decode("#F5ECE0"));  // RoundedPanel với góc bo 50px
        this.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Quản Lý Hóa Đơn", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        this.add(title, BorderLayout.NORTH);

        // Spacer
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(0, 15));
        gapPanel.setOpaque(true);
        gapPanel.setBackground(Color.decode("#F5ECE0"));
        this.add(gapPanel, BorderLayout.CENTER);

        // Panel chứa bảng
        RoundedPanel hdtablePanel = new RoundedPanel(50, 50, Color.WHITE);
        hdtablePanel.setLayout(new BorderLayout());
        hdtablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hdtablePanel.setPreferredSize(new Dimension(0, 400));

        // Control panel chứa thanh tìm kiếm và nút
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thanh tìm kiếm và icon kính lúp
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập thông tin hóa đơn để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                hoaDonBUS.searchTable(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hoaDonBUS.searchTable(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                hoaDonBUS.searchTable(tableModel, searchField.getText());
            }
        });

        JLabel searchIcon = new JLabel();
        ImageIcon search = new ImageIcon("Resources\\Image\\MagnifyingGlass.png");
        Image scaledIcon = search.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchIcon.setIcon(new ImageIcon(scaledIcon));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);
        controlPanel.add(searchPanel, BorderLayout.WEST);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon =Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);

        // Nút sửa hóa đơn
        MyButton editBillButton = new MyButton("Sửa hóa đơn", editIcon);
        editBillButton.setPreferredSize(new Dimension(180, 40));
        editBillButton.setBackground(Color.decode("#EC5228"));
        editBillButton.setForeground(Color.WHITE);
        editBillButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editBillButton.setFocusPainted(false);
        editBillButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editBillButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editBillButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Nút xóa hóa đơn
        MyButton deleteBillButton = new MyButton("Xóa hóa đơn", deleteIcon);
        deleteBillButton.setPreferredSize(new Dimension(180, 40));
        deleteBillButton.setBackground(Color.decode("#EC5228"));
        deleteBillButton.setForeground(Color.WHITE);
        deleteBillButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteBillButton.setFocusPainted(false);
        deleteBillButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteBillButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteBillButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Panel chứa các nút thao tác
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editBillButton);
        buttonPanel.add(deleteBillButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        hdtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Tạo bảng và cấu hình hiển thị
        tableModel.setColumnIdentifiers(new Object[]{"Mã hóa đơn", "Mã nhân viên", "Số điện thoại khách", "Ngày lập", "Tổng tiền", "PTTT"});
        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFocusable(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if(row != -1) {
                        String maHoaDon = table.getValueAt(row, 0).toString();
                        FormXemChiTietHoaDon(maHoaDon);
                    }
                }
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        hdtablePanel.add(scrollPane, BorderLayout.CENTER);

        this.add(hdtablePanel, BorderLayout.SOUTH);

        // Load dữ liệu bảng qua BUS
        hoaDonBUS.refreshTableData(tableModel);

        // Sự kiện sửa nhân viên: gọi sang BUS để lấy dữ liệu nhân viên cần sửa
        editBillButton.addActionListener(e -> {
            editBillButton.setEnabled(false);
            FormSuaHoaDon(editBillButton, table);
        });

        // Sự kiện xóa nhân viên
        deleteBillButton.addActionListener(e -> {
            XoaHoaDon(table);
        });
    }

    // Form sửa hóa đơn: sử dụng BUS để lấy dữ liệu hóa đơn dựa trên mã và cập nhật lại
    private void FormSuaHoaDon(JButton editCustomButton, JTable table) {
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maHoaDon = tableModel.getValueAt(selectedRow, 0).toString();

            // Gọi BUS lấy thông tin khách hàng qua mã (trả về Map<String,String>)
            HoaDonDTO empData = hoaDonBUS.getHoaDonById(maHoaDon);
            if (empData == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn cần chỉnh sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                editCustomButton.setEnabled(true);
                return;
            }
            Date ngayLap = new Date();
            String maKhachHang = empData.getMaKhachHang();
            String sdtKhachHang = khBUS.getKhachHangById(maKhachHang).getSDT();
            String maNhanVien = empData.getMaNhanVien();
            try {
                ngayLap = dbFormat.parse(empData.getNgayLap());
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            Double tongTien = empData.getTongTien();
            String pttt = empData.getPTTT();

            JFrame formSuaHD = new JFrame("Sửa Hóa Đơn");
            formSuaHD.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            formSuaHD.setSize(800, 600);
            formSuaHD.setLayout(new BorderLayout());

            // Header
            RoundedPanel suaHDHeader = new RoundedPanel(30, 30, Color.WHITE);
            suaHDHeader.setLayout(new BorderLayout());
            JLabel suaHDTitle = new JLabel("Sửa Hóa Đơn", SwingConstants.CENTER);
            suaHDTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
            suaHDTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            suaHDHeader.add(suaHDTitle, BorderLayout.CENTER);
            formSuaHD.add(suaHDHeader, BorderLayout.NORTH);

            //Tạo icon warning dấu chấm thang
            ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

            // Center panel các input
            RoundedPanel suaHDCenter = new RoundedPanel(30, 30, Color.WHITE);
            suaHDCenter.setLayout(new GridLayout(6, 3, 10, 10));
            suaHDCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Mã hóa đơn
            JLabel maHDLabel = new JLabel("Mã hóa đơn:");
            JTextField maHDField = new JTextField(maHoaDon);
            maHDField.setEditable(false);
            maHDField.setBackground(Color.LIGHT_GRAY);
            JLabel emptyLabel1 = new JLabel();

            // Số điện thoại khách hàng
            JLabel sdtKHLabel = new JLabel("Số điện thoại:");
            JTextField sdtKHField = new JTextField(sdtKhachHang);
            JButton btnChonSDT = new JButton("Chọn số điện thoại");
            btnChonSDT.addActionListener(e -> showPhoneSelectionDialog(sdtKHField));
            JPanel SDTField = new JPanel();
            SDTField.setLayout(new BorderLayout()); // Sử dụng BorderLayout để kiểm soát kích thước
            
            sdtKHField.setPreferredSize(new Dimension(SDTField.getWidth() - 25, SDTField.getHeight())); // Chiếm gần hết không gian, trừ nút
            btnChonSDT.setPreferredSize(new Dimension(25, SDTField.getHeight())); // Định kích thước nút nhỏ
            
            SDTField.add(sdtKHField, BorderLayout.CENTER); // Đặt TextField ở giữa, chiếm phần lớn diện tích
            SDTField.add(btnChonSDT, BorderLayout.EAST);   // Đặt nút bên phải

            JLabel emptyLabel2 = new JLabel();
            
            JLabel customerPhoneErrorLabel = new JLabel();
            customerPhoneErrorLabel.setPreferredSize(new Dimension(60, 60));
            customerPhoneErrorLabel.setIcon(new ImageIcon(scaledImage));
            customerPhoneErrorLabel.setForeground(Color.RED);
            customerPhoneErrorLabel.setVisible(false);
            customerPhoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Mã nhân viên
            JLabel maNVLabel = new JLabel("Mã nhân viên");
            JTextField maNVField = new JTextField(maNhanVien);
            JButton btnChonMaNV = new JButton("🔍"); // Biểu tượng nhỏ cho nút chọn

            JPanel maNVPanel = new JPanel();
            maNVPanel.setLayout(new BorderLayout());
            maNVPanel.add(maNVField, BorderLayout.CENTER);
            maNVPanel.add(btnChonMaNV, BorderLayout.EAST);

            btnChonMaNV.addActionListener(e -> showMaNVSelectionDialog(maNVField));


            JLabel employeeIDErrorLabel = new JLabel();
            employeeIDErrorLabel.setPreferredSize(new Dimension(60, 60));
            employeeIDErrorLabel.setIcon(new ImageIcon(scaledImage));
            employeeIDErrorLabel.setForeground(Color.RED);
            employeeIDErrorLabel.setVisible(false);
            employeeIDErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Ngày lập
            JLabel ngayLapLabel = new JLabel("Ngày lập:");
            JDateChooser ngayLapField = new JDateChooser();
            ngayLapField.setDateFormatString("yyyy-MM-dd");
            ngayLapField.setDate(ngayLap);
            JLabel createdDateErrorLabel = new JLabel();
            createdDateErrorLabel.setPreferredSize(new Dimension(60, 60));
            createdDateErrorLabel.setIcon(new ImageIcon(scaledImage));
            createdDateErrorLabel.setForeground(Color.RED);
            createdDateErrorLabel.setVisible(false);
            createdDateErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Tổng tiền
            JLabel totalLabel = new JLabel("Tổng tiền:");
            JTextField totalField = new JTextField(String.valueOf(tongTien));
            totalField.setEditable(false);
            totalField.setBackground(Color.LIGHT_GRAY);
            JLabel emptyLabel3 = new JLabel();

            // Phương thức thanh toán
            String[] ptttList = {"Tiền mặt", "Thẻ tín dụng", "Mã QR"};
            JLabel ptttLabel = new JLabel("Phương thức thanh toán:");
            JComboBox<String> ptttField = new JComboBox<>(ptttList);
            ptttField.setSelectedItem(pttt);
            JLabel emptyLabel4 = new JLabel();

            suaHDCenter.add(maHDLabel);       suaHDCenter.add(maHDField);       suaHDCenter.add(emptyLabel1);
            suaHDCenter.add(sdtKHLabel);        suaHDCenter.add(SDTField);        suaHDCenter.add(emptyLabel2);
            suaHDCenter.add(maNVLabel);     suaHDCenter.add(maNVPanel);       suaHDCenter.add(employeeIDErrorLabel);
            suaHDCenter.add(ngayLapLabel);          suaHDCenter.add(ngayLapField);          suaHDCenter.add(createdDateErrorLabel);
            suaHDCenter.add(totalLabel);        suaHDCenter.add(totalField);        suaHDCenter.add(emptyLabel3);
            suaHDCenter.add(ptttLabel);        suaHDCenter.add(ptttField);         suaHDCenter.add(emptyLabel4);
            
            formSuaHD.add(suaHDCenter, BorderLayout.CENTER);

            RoundedPanel suaHDFooter = new RoundedPanel(30, 30, Color.WHITE);
            suaHDFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton saveButton = new JButton("Lưu");
            saveButton.setPreferredSize(new Dimension(100, 40));
            saveButton.setBackground(Color.decode("#EC5228"));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            saveButton.addActionListener(event -> {
                // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
                customerPhoneErrorLabel.setVisible(false);
                employeeIDErrorLabel.setVisible(false);
                createdDateErrorLabel.setVisible(false);
                
                boolean isValid = true;

                // Validate số điện thoại khách hàng
                String cusPhone = sdtKHField.getText();
                int check = 0; // Đánh dấu liệu số điện thoại có tồn tại không nếu không thì 0 và ngược lại
                if(!cusPhone.isEmpty()){
                    for(KhachHangDTO khach: khBUS.getAllKhachHang()) {
                        if(khach.getSDT().equals(cusPhone)) {
                            check = check +1;
                        }
                    }
                    if(check == 0) {
                        isValid = false;
                        customerPhoneErrorLabel.setToolTipText("Khách hàng không tồn tại.");
                        customerPhoneErrorLabel.setVisible(true);
                    }
                }

                // Validate mã nhân viên
                String empID = maNVField.getText().trim();
                List<String> empIDErrors = new ArrayList<>();
                if(empID.isEmpty()) {
                    empIDErrors.add("Mã nhân viên không được để trống.");
                } else {
                    if(nvBUS.getNhanVienById(empID) == null) {
                        empIDErrors.add("Mã nhân viên không tồn tại.");
                    }
                }
                if(!empIDErrors.isEmpty()) {
                    isValid = false;
                    StringBuilder empIDTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                    for(String err: empIDErrors){
                        empIDTooltip.append("<li>").append(err).append("</li>");
                    }
                    empIDTooltip.append("</ul></html>");
                    employeeIDErrorLabel.setToolTipText(empIDTooltip.toString());
                    employeeIDErrorLabel.setVisible(true);
                }

                // Validate ngày lập
                String createdDate = dbFormat.format(ngayLapField.getDate());
                Date today = new Date();
                List<String> createdDateErrors = new ArrayList<>();
                if(createdDate.isEmpty()) {
                    createdDateErrors.add("Ngày lập không được trống.");
                } else {
                    if(ngayLapField.getDate().after(today)) {
                        createdDateErrors.add("Ngày nhập không được lớn hơn ngày hôm nay");
                    }
                }
                if(!createdDateErrors.isEmpty()) {
                    isValid = false;
                    StringBuilder createdDateTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                    for(String err: createdDateErrors){
                        createdDateTooltip.append("<li>").append(err).append("</li>");
                    }
                    createdDateTooltip.append("</ul></html>");
                    createdDateErrorLabel.setToolTipText(createdDateTooltip.toString());
                    createdDateErrorLabel.setVisible(true);
                }

                if(isValid) {
                // Gọi hàm BUS cập nhật thông tin khách hàng
                    hoaDonBUS.updateHoaDon(
                            maHoaDon,
                            dbFormat.format(ngayLapField.getDate()),
                            maNVField.getText(),
                            sdtKHField.getText(),
                            tongTien,
                            ptttField.getSelectedItem().toString()
                    );
                    hoaDonBUS.refreshTableData(tableModel);
                    formSuaHD.dispose();
                }
            });
            suaHDFooter.add(saveButton);
            formSuaHD.add(suaHDFooter, BorderLayout.SOUTH);

            formSuaHD.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    editCustomButton.setEnabled(true);
                }
            });
            formSuaHD.setVisible(true);
        } else {
            editCustomButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, 
                    "Vui lòng chọn khách hàng cần cập nhật thông tin!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showMaNVSelectionDialog(JTextField maNVField) {
        JDialog dialog = new JDialog((Frame) null, "Chọn mã nhân viên", true);
        dialog.setSize(300, 400);
        dialog.setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> maNVList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(maNVList);
        JButton btnConfirm = new JButton("Lưu");

        // Cập nhật danh sách ban đầu
        for (NhanVienDTO item : nvBUS.getAllNhanVien()) {
            listModel.addElement(item.getMaNhanVien());
        }

        // Lọc danh sách theo đầu vào
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateList() {
                SwingUtilities.invokeLater(() -> {
                    listModel.clear();
                    String input = searchField.getText().toLowerCase();
                    for (NhanVienDTO item : nvBUS.getAllNhanVien()) {
                        if (item.getMaNhanVien().toLowerCase().contains(input)) {
                            listModel.addElement(item.getMaNhanVien());
                        }
                    }
                });
            }

            @Override public void insertUpdate(DocumentEvent e) { updateList(); }
            @Override public void removeUpdate(DocumentEvent e) { updateList(); }
            @Override public void changedUpdate(DocumentEvent e) { updateList(); }
        });

        // Xử lý khi chọn mã nhân viên và lưu
        btnConfirm.addActionListener(e -> {
            String selectedMaNV = maNVList.getSelectedValue();
            if (selectedMaNV != null) {
                maNVField.setText(selectedMaNV);
            }
            dialog.dispose(); // Đóng cửa sổ
        });

        dialog.add(searchField, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(btnConfirm, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void showPhoneSelectionDialog(JTextField sdtKHField) {
        JDialog dialog = new JDialog((Frame) null, "Danh sách số điện thoại", true);
        dialog.setSize(300, 400);
        dialog.setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> phoneList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(phoneList);
        JButton btnConfirm = new JButton("Xác nhận");

        // Cập nhật danh sách ban đầu
        for (KhachHangDTO item : khBUS.getAllKhachHang()) {
            listModel.addElement(item.getSDT());
        }

        // Lọc danh sách theo đầu vào
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateList() {
                SwingUtilities.invokeLater(() -> {
                    listModel.clear();
                    String input = searchField.getText().toLowerCase();
                    for (KhachHangDTO item : khBUS.getAllKhachHang()) {
                        if (item.getSDT().toLowerCase().contains(input)) {
                            listModel.addElement(item.getSDT());
                        }
                    }
                });
            }

            @Override public void insertUpdate(DocumentEvent e) { updateList(); }
            @Override public void removeUpdate(DocumentEvent e) { updateList(); }
            @Override public void changedUpdate(DocumentEvent e) { updateList(); }
        });

        // Xử lý xác nhận
        btnConfirm.addActionListener(e -> {
            String selectedPhone = phoneList.getSelectedValue();
            if (selectedPhone != null) {
                sdtKHField.setText(selectedPhone);
            }
            dialog.dispose(); // Đóng cửa sổ
        });

        dialog.add(searchField, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(btnConfirm, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void XoaHoaDon(JTable table) {
        int selectedRow = table.getSelectedRow();

        if(selectedRow != -1) {
            String maHoaDon = tableModel.getValueAt(selectedRow, 0).toString();
            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn có mã " + maHoaDon + "?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                hoaDonBUS.deleteHoaDon(maHoaDon);
                hoaDonBUS.refreshTableData(tableModel);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void FormXemChiTietHoaDon(String maHoaDon) {
        JFrame formXemChiTietHD = new JFrame("Chi Tiết Hóa Đơn");
        formXemChiTietHD.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        formXemChiTietHD.setSize(800, 600);
        formXemChiTietHD.setLayout(new BorderLayout());

        DefaultTableModel detailTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };

        // Gọi BUS lấy thông tin hóa đơn qua mã
        List<ChiTietHoaDonDTO> empData = new ArrayList<>();
        for(ChiTietHoaDonDTO ct: chiTietHoaDonBUS.getAllChiTietHoaDon()) {
            if(ct.getMaHoaDon().equals(maHoaDon)) {
                empData.add(ct);
            }
        }
        if (empData.isEmpty()) {
            JOptionPane.showMessageDialog(formXemChiTietHD, "Không tìm thấy hóa đơn!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        // Header
        RoundedPanel chiTietHDHeader = new RoundedPanel(30, 30, Color.WHITE);
        chiTietHDHeader.setLayout(new BorderLayout());
        JLabel chiTietHDTitle = new JLabel("Chi Tiết Hóa Đơn", SwingConstants.CENTER);
        chiTietHDTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        chiTietHDTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        chiTietHDHeader.add(chiTietHDTitle, BorderLayout.CENTER);
        formXemChiTietHD.add(chiTietHDHeader, BorderLayout.NORTH);

        // Center panel chi tiết hóa đơn
        RoundedPanel chiTietHDCenter = new RoundedPanel(30, 30, Color.WHITE);
        chiTietHDCenter.setLayout(new BorderLayout());
        chiTietHDCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chiTietHDCenter.setPreferredSize(new Dimension(0, 400));

        detailTableModel.setColumnIdentifiers(new Object[]{"Mã thức ăn", "Số lượng bán", "Thành tiền"});
        JTable detailTable = new JTable(detailTableModel);
        detailTable.setRowHeight(35);
        detailTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailTable.setShowGrid(false);
        detailTable.setIntercellSpacing(new Dimension(0, 0));
        detailTable.setFocusable(false);

        JTableHeader detailHeader = detailTable.getTableHeader();
        detailHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailHeader.setBackground(Color.WHITE);
        detailHeader.setForeground(Color.BLACK);
        detailHeader.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        detailScrollPane.setBorder(BorderFactory.createEmptyBorder());
        detailScrollPane.setBackground(Color.WHITE);
        detailScrollPane.getViewport().setBackground(Color.WHITE);

        chiTietHoaDonBUS.refreshDetailTableData(detailTableModel, maHoaDon);

        chiTietHDCenter.add(detailScrollPane, BorderLayout.CENTER);
        
        formXemChiTietHD.add(chiTietHDCenter, BorderLayout.CENTER);

        formXemChiTietHD.setVisible(true);
    }
}
