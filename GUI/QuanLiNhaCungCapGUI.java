package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BUS.NguyenLieuBUS;
import BUS.NhaCungCapBUS;
import BUS.PhanPhoiBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.KhachHangDTO;
import DTO.NguyenLieuDTO;
import DTO.NhaCungCapDTO;
import DTO.PhanPhoiDTO;

public class QuanLiNhaCungCapGUI extends RoundedPanel {
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };
    private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();
    private NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    private PhanPhoiBUS phanPhoiBUS = new PhanPhoiBUS();

    public QuanLiNhaCungCapGUI() {
        super(50, 50, Color.decode("#F5ECE0"));  // RoundedPanel với góc bo 50px
        this.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Quản Lý Nhà Cung Cấp", SwingConstants.LEFT);
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
        RoundedPanel ncctablePanel = new RoundedPanel(50, 50, Color.WHITE);
        ncctablePanel.setLayout(new BorderLayout());
        ncctablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ncctablePanel.setPreferredSize(new Dimension(0, 400));

        // Control panel chứa thanh tìm kiếm và nút
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thanh tìm kiếm và icon kính lúp
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập thông tin nhà cung cấp để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                nhaCungCapBUS.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                nhaCungCapBUS.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                nhaCungCapBUS.searchTableData(tableModel, searchField.getText());
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

        // Nút sửa nhà cung cấp
        MyButton editProviderButton = new MyButton("Sửa nhà cung cấp", editIcon);
        editProviderButton.setPreferredSize(new Dimension(180, 40));
        editProviderButton.setBackground(Color.decode("#EC5228"));
        editProviderButton.setForeground(Color.WHITE);
        editProviderButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editProviderButton.setFocusPainted(false);
        editProviderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editProviderButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editProviderButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Nút thêm nhà cung cấp
        MyButton addProviderButton = new MyButton("Thêm nhà cung cấp", addIcon);
        addProviderButton.setPreferredSize(new Dimension(180, 40));
        addProviderButton.setBackground(Color.decode("#EC5228"));
        addProviderButton.setForeground(Color.WHITE);
        addProviderButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addProviderButton.setFocusPainted(false);
        addProviderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addProviderButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addProviderButton.setBackground(Color.decode("#EC5228"));
            }
        });
        addProviderButton.addActionListener(e -> {
            addProviderButton.setEnabled(false);
            FormThemNhaCungCap(addProviderButton);
        });

        // Nút xóa nhà cung cấp
        MyButton deleteProviderButton = new MyButton("Xóa nhà cung cấp", deleteIcon);
        deleteProviderButton.setPreferredSize(new Dimension(180, 40));
        deleteProviderButton.setBackground(Color.decode("#EC5228"));
        deleteProviderButton.setForeground(Color.WHITE);
        deleteProviderButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteProviderButton.setFocusPainted(false);
        deleteProviderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteProviderButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteProviderButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Panel chứa các nút thao tác
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editProviderButton);
        buttonPanel.add(addProviderButton);
        buttonPanel.add(deleteProviderButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        ncctablePanel.add(controlPanel, BorderLayout.NORTH);

        // Tạo bảng và cấu hình hiển thị
        tableModel.setColumnIdentifiers(new Object[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Email", "Số điện thoại", "Địa chỉ", "Trạng thái"});
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
                        String maNhaCungCap = table.getValueAt(row, 0).toString();
                        phanphoi(maNhaCungCap);
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
        ncctablePanel.add(scrollPane, BorderLayout.CENTER);

        this.add(ncctablePanel, BorderLayout.SOUTH);

        // Load dữ liệu bảng qua BUS
        nhaCungCapBUS.refreshTableData(tableModel);

        // Sự kiện sửa nhân viên: gọi sang BUS để lấy dữ liệu nhân viên cần sửa
        editProviderButton.addActionListener(e -> {
            editProviderButton.setEnabled(false);
            FormSuaNhaCungCap(editProviderButton, table);
        });

        // Sự kiện xóa nhân viên
        deleteProviderButton.addActionListener(e -> {
            XoaNhaCungCap(table);
        });
    }

    // Form thêm khách hàng
    private void FormThemNhaCungCap(JButton addProviderButton) {
        JFrame formThemNCC = new JFrame("Thêm Nhà Cung Cấp");
        formThemNCC.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Set the close operation correctly
        formThemNCC.setSize(800, 600); // Set the window size
        formThemNCC.setLayout(new BorderLayout()); // Set the layout

        // Header
        RoundedPanel themNCCHeader = new RoundedPanel(30, 30, Color.WHITE);
        themNCCHeader.setLayout(new BorderLayout());
        JLabel themNCCTitle = new JLabel("Thêm Nhà Cung Cấp", SwingConstants.CENTER);
        themNCCTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themNCCTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themNCCHeader.add(themNCCTitle, BorderLayout.CENTER);
        formThemNCC.add(themNCCHeader, BorderLayout.NORTH);

        /*
        Sử dụng GridLayout(9,3,10,10) để mỗi dòng có:
            - Cột 1: Label mô tả field.
            - Cột 2: Ô nhập liệu (hoặc combobox).
            - Cột 3: Error icon (hoặc JLabel trống nếu không cần hiển thị lỗi).
        */
        RoundedPanel themNCCCenter = new RoundedPanel(30, 30, Color.WHITE);
        themNCCCenter.setLayout(new GridLayout(5, 3, 10, 10));
        themNCCCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // ----- Dòng 1: Mã nhà cung cấp (read-only, không cần kiểm tra nên không có icon) -----
        JLabel maNCCLabel = new JLabel("Mã nhà cung cấp:");
        JTextField maNCCField = new JTextField(nhaCungCapBUS.generateID());
        maNCCField.setEditable(false);
        maNCCField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel(); // không hiển thị icon
        emptyLabel1.setPreferredSize(new Dimension(60, 60));
        themNCCCenter.add(maNCCLabel);
        themNCCCenter.add(maNCCField);
        themNCCCenter.add(emptyLabel1);

        // ----- Dòng 2: Tên nhà cung cấp -----
        JLabel tenNCCLabel = new JLabel("Tên nhà cung cấp:");
        JTextField tenNCCField = new JTextField();
        // Tạo icon lỗi cho tên nhà cung cấp
        JLabel nameErrorLabel = new JLabel();
        nameErrorLabel.setPreferredSize(new Dimension(60, 60));
        nameErrorLabel.setIcon(new ImageIcon(scaledImage));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setVisible(false); // ẩn ban đầu
        nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNCCCenter.add(tenNCCLabel);
        themNCCCenter.add(tenNCCField);
        themNCCCenter.add(nameErrorLabel);

        // ----- Dòng 3: Email -----
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel emailErrorLabel = new JLabel();
        emailErrorLabel.setPreferredSize(new Dimension(60, 60));
        emailErrorLabel.setIcon(new ImageIcon(scaledImage));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setVisible(false);
        emailErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNCCCenter.add(emailLabel);
        themNCCCenter.add(emailField);
        themNCCCenter.add(emailErrorLabel);

        // ----- Dòng 4: Số điện thoại -----
        JLabel sdtLabel = new JLabel("Số điện thoại:");
        JTextField sdtField = new JTextField();
        JLabel phoneErrorLabel = new JLabel();
        phoneErrorLabel.setPreferredSize(new Dimension(60, 60));
        phoneErrorLabel.setIcon(new ImageIcon(scaledImage));
        phoneErrorLabel.setForeground(Color.RED);
        phoneErrorLabel.setVisible(false);
        phoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNCCCenter.add(sdtLabel);
        themNCCCenter.add(sdtField);
        themNCCCenter.add(phoneErrorLabel);

        // ----- Dòng 5: Địa chỉ -----
        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        JTextField diaChiField = new JTextField();
        // Nếu địa chỉ cần có thì dùng addressErrorLabel để setToolTipText
        JLabel addressErrorLabel = new JLabel();
        addressErrorLabel.setPreferredSize(new Dimension(60, 60));
        addressErrorLabel.setIcon(new ImageIcon(scaledImage));
        addressErrorLabel.setForeground(Color.RED);
        addressErrorLabel.setVisible(false);
        addressErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNCCCenter.add(diaChiLabel);
        themNCCCenter.add(diaChiField);
        themNCCCenter.add(addressErrorLabel);

        formThemNCC.add(themNCCCenter, BorderLayout.CENTER);

        // Footer chứa nút Lưu
        RoundedPanel themNCCFooter = new RoundedPanel(30, 30, Color.WHITE);
        themNCCFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
            nameErrorLabel.setVisible(false);
            phoneErrorLabel.setVisible(false);
            emailErrorLabel.setVisible(false);
            
            boolean isValid = true;

            // Validate Tên khách hàng: không được để trống
            String name = tenNCCField.getText().trim();
            if(name.isEmpty()){
                isValid = false;
                nameErrorLabel.setToolTipText("Tên khách hàng không được để trống.");
                nameErrorLabel.setVisible(true);
            }

            // Validate Số điện thoại
            String phone = sdtField.getText().trim();
            java.util.List<String> phoneErrors = new java.util.ArrayList<>();
            if(phone.isEmpty()){
                phoneErrors.add("Số điện thoại không được để trống.");
            }
            if(!phone.matches("\\d+")){
                phoneErrors.add("Số điện thoại chỉ được chứa chữ số.");
            }
            if(!phone.matches("\\d{10}")){
                phoneErrors.add("Số điện thoại phải có đúng 10 chữ số.");
            }
            if(!phoneErrors.isEmpty()){
                isValid = false;
                StringBuilder phoneTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: phoneErrors){
                    phoneTooltip.append("<li>").append(err).append("</li>");
                }
                phoneTooltip.append("</ul></html>");
                phoneErrorLabel.setToolTipText(phoneTooltip.toString());
                phoneErrorLabel.setVisible(true);
            }

            // Validate Email
            String email = emailField.getText().trim();
            java.util.List<String> emailErrors = new java.util.ArrayList<>();
            if(email.isEmpty()){
                emailErrors.add("Email không được để trống.");
            } else {
                // Định dạng email cơ bản
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                if(!email.matches(emailRegex)){
                    emailErrors.add("Email không đúng định dạng.");
                }
            }
            if(!emailErrors.isEmpty()){
                isValid = false;
                StringBuilder emailTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: emailErrors){
                    emailTooltip.append("<li>").append(err).append("</li>");
                }
                emailTooltip.append("</ul></html>");
                emailErrorLabel.setToolTipText(emailTooltip.toString());
                emailErrorLabel.setVisible(true);
            }

            
            // Nếu tất cả dữ liệu hợp lệ, gọi vào BUS để lưu dữ liệu vào CSDL
            if(isValid) {
                try {
                    NhaCungCapDTO ncc = new NhaCungCapDTO();
                    ncc.setMaNhaCungCap(maNCCField.getText());
                    ncc.setTenNhaCungCap(tenNCCField.getText());
                    ncc.setEmail(emailField.getText());
                    ncc.setSDT(sdtField.getText());
                    ncc.setDiaChi(diaChiField.getText());
                    ncc.setTrangThai(true);

                    nhaCungCapBUS.add(ncc);
                    nhaCungCapBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemNCC, "Thêm nhà cung cấp thành công!");
                    formThemNCC.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formThemNCC, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        themNCCFooter.add(saveButton);
        formThemNCC.add(themNCCFooter, BorderLayout.SOUTH);

        formThemNCC.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addProviderButton.setEnabled(true);
            }
        });
        formThemNCC.setVisible(true);
    }

    // Form sửa nhà cung cấp: sử dụng BUS để lấy dữ liệu nhà cung cấp dựa trên mã và cập nhật lại
    private void FormSuaNhaCungCap(JButton editProviderButton, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNhaCungCap = tableModel.getValueAt(selectedRow, 0).toString();

            // Gọi BUS lấy thông tin nhà cung cấp qua mã (trả về Map<String,String>)
            NhaCungCapDTO empData = nhaCungCapBUS.getDataById(maNhaCungCap);
            if (empData == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung cấp cần chỉnh sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                editProviderButton.setEnabled(true);
                return;
            }
            String tenNhaCungCap = empData.getTenNhaCungCap();
            String email = empData.getEmail();
            String SDT = empData.getSDT();
            String diaChi = empData.getDiaChi();
            boolean trangThai = empData.isTrangThai();

            JFrame formSuaNCC = new JFrame("Sửa Nhà Cung Cấp");
            formSuaNCC.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            formSuaNCC.setSize(800, 600);
            formSuaNCC.setLayout(new BorderLayout());

            // Header
            RoundedPanel suaNCCHeader = new RoundedPanel(30, 30, Color.WHITE);
            suaNCCHeader.setLayout(new BorderLayout());
            JLabel suaNCCTitle = new JLabel("Sửa Nhà Cung Cấp", SwingConstants.CENTER);
            suaNCCTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
            suaNCCTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            suaNCCHeader.add(suaNCCTitle, BorderLayout.CENTER);
            formSuaNCC.add(suaNCCHeader, BorderLayout.NORTH);

            //Tạo icon warning dấu chấm thang
            ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

            // Center panel các input
            RoundedPanel suaNCCCenter = new RoundedPanel(30, 30, Color.WHITE);
            suaNCCCenter.setLayout(new GridLayout(7, 3, 10, 10));
            suaNCCCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel maNCCLabel = new JLabel("Mã nhà cung cấp:");
            JTextField maNCCField = new JTextField(maNhaCungCap);
            maNCCField.setEditable(false);
            maNCCField.setBackground(Color.LIGHT_GRAY);
            JLabel emptyLabel1 = new JLabel();

            JLabel tenNCCLabel = new JLabel("Tên nhà cung cấp:");
            JTextField tenNCCField = new JTextField(tenNhaCungCap);
            JLabel nameErrorLabel = new JLabel();
            nameErrorLabel.setPreferredSize(new Dimension(60, 60));
            nameErrorLabel.setIcon(new ImageIcon(scaledImage));
            nameErrorLabel.setForeground(Color.RED);
            nameErrorLabel.setVisible(false);
            nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel emailLabel = new JLabel("Email:");
            JTextField emailField = new JTextField(email);
            JLabel emailErrorLabel = new JLabel();
            emailErrorLabel.setPreferredSize(new Dimension(60, 60));
            emailErrorLabel.setIcon(new ImageIcon(scaledImage));
            emailErrorLabel.setForeground(Color.RED);
            emailErrorLabel.setVisible(false);
            emailErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel SDTLabel = new JLabel("Số điện thoại:");
            JTextField SDTField = new JTextField(SDT);
            JLabel phoneErrorLabel = new JLabel();
            phoneErrorLabel.setPreferredSize(new Dimension(60, 60));
            phoneErrorLabel.setIcon(new ImageIcon(scaledImage));
            phoneErrorLabel.setForeground(Color.RED);
            phoneErrorLabel.setVisible(false);
            phoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel diaChiLabel = new JLabel("Địa chỉ:");
            JTextField diaChiField = new JTextField(diaChi);
            JLabel addressErrorLabel = new JLabel();
            addressErrorLabel.setPreferredSize(new Dimension(60, 60));
            addressErrorLabel.setIcon(new ImageIcon(scaledImage));
            addressErrorLabel.setForeground(Color.RED);
            addressErrorLabel.setVisible(false);
            addressErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel trangThaiLabel = new JLabel("Trạng thái:");
            JComboBox<String> trangThaiField = new JComboBox<>();
            JLabel emptyLabel2 = new JLabel();

            suaNCCCenter.add(maNCCLabel);       suaNCCCenter.add(maNCCField);       suaNCCCenter.add(emptyLabel1);
            suaNCCCenter.add(tenNCCLabel);        suaNCCCenter.add(tenNCCField);        suaNCCCenter.add(nameErrorLabel);
            suaNCCCenter.add(emailLabel);        suaNCCCenter.add(emailField);        suaNCCCenter.add(emailErrorLabel);
            suaNCCCenter.add(SDTLabel);          suaNCCCenter.add(SDTField);          suaNCCCenter.add(phoneErrorLabel);
            suaNCCCenter.add(diaChiLabel);       suaNCCCenter.add(diaChiField);       suaNCCCenter.add(addressErrorLabel);
            suaNCCCenter.add(trangThaiLabel);       suaNCCCenter.add(trangThaiField);       suaNCCCenter.add(emptyLabel2);
            formSuaNCC.add(suaNCCCenter, BorderLayout.CENTER);

            RoundedPanel suaNCCFooter = new RoundedPanel(30, 30, Color.WHITE);
            suaNCCFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton saveButton = new JButton("Lưu");
            saveButton.setPreferredSize(new Dimension(100, 40));
            saveButton.setBackground(Color.decode("#EC5228"));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            saveButton.addActionListener(event -> {
                // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
                nameErrorLabel.setVisible(false);
                phoneErrorLabel.setVisible(false);
                emailErrorLabel.setVisible(false);
                
                boolean isValid = true;

                // Validate Tên nhà cung cấp: không được để trống
                String name = tenNCCField.getText().trim();
                if(name.isEmpty()){
                    isValid = false;
                    nameErrorLabel.setToolTipText("Tên nhà cung cấp không được để trống.");
                    nameErrorLabel.setVisible(true);
                }

                // Validate Số điện thoại
                String phone = SDTField.getText().trim();
                java.util.List<String> phoneErrors = new java.util.ArrayList<>();
                if(phone.isEmpty()){
                    phoneErrors.add("Số điện thoại không được để trống.");
                }
                if(!phone.matches("\\d+")){
                    phoneErrors.add("Số điện thoại chỉ được chứa chữ số.");
                }
                if(!phone.matches("\\d{10}")){
                    phoneErrors.add("Số điện thoại phải có đúng 10 chữ số.");
                }
                if(!phoneErrors.isEmpty()){
                    isValid = false;
                    StringBuilder phoneTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                    for(String err: phoneErrors){
                        phoneTooltip.append("<li>").append(err).append("</li>");
                    }
                    phoneTooltip.append("</ul></html>");
                    phoneErrorLabel.setToolTipText(phoneTooltip.toString());
                    phoneErrorLabel.setVisible(true);
                }

                // Validate Email
                String mail = emailField.getText().trim();
                java.util.List<String> emailErrors = new java.util.ArrayList<>();
                if(mail.isEmpty()){
                    emailErrors.add("Email không được để trống.");
                } else {
                    // Định dạng email cơ bản
                    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                    if(!mail.matches(emailRegex)){
                        emailErrors.add("Email không đúng định dạng.");
                    }
                }
                if(!emailErrors.isEmpty()){
                    isValid = false;
                    StringBuilder emailTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                    for(String err: emailErrors){
                        emailTooltip.append("<li>").append(err).append("</li>");
                    }
                    emailTooltip.append("</ul></html>");
                    emailErrorLabel.setToolTipText(emailTooltip.toString());
                    emailErrorLabel.setVisible(true);
                }

                if(isValid) {
                    NhaCungCapDTO ncc = new NhaCungCapDTO();
                    ncc.setMaNhaCungCap(maNhaCungCap);
                    ncc.setTenNhaCungCap(tenNCCField.getText());
                    ncc.setEmail(emailField.getText());
                    ncc.setSDT(SDTField.getText());
                    ncc.setDiaChi(diaChiField.getText());
                    ncc.setTrangThai(trangThaiField.getSelectedItem().toString().equals("Đang hoạt động") ? true : false);

                    nhaCungCapBUS.update(ncc);
                    nhaCungCapBUS.refreshTableData(tableModel);
                    formSuaNCC.dispose();
                }
            });
            suaNCCFooter.add(saveButton);
            formSuaNCC.add(suaNCCFooter, BorderLayout.SOUTH);

            formSuaNCC.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    editProviderButton.setEnabled(true);
                }
            });
            formSuaNCC.setVisible(true);
        } else {
            editProviderButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, 
                    "Vui lòng chọn nhà cung cấp cần cập nhật thông tin!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void XoaNhaCungCap(JTable table) {
        int selectedRow = table.getSelectedRow();

        if(selectedRow != -1) {
            String maNhaCungCap = tableModel.getValueAt(selectedRow, 0).toString();
            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhà cung cấp có mã " + maNhaCungCap + "?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                nhaCungCapBUS.delete(maNhaCungCap);
                nhaCungCapBUS.refreshTableData(tableModel);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void phanphoi(String maNhaCungCap) {
        JFrame frame = new JFrame("Phân phối");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel tìm kiếm và danh sách nguyên liệu
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Tìm kiếm nguyên liệu:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // Bảng danh sách nguyên liệu
        String[] columnNames = {"Mã nguyên liệu", "Tên nguyên liệu", "Giá nhập", "Chọn"};
        
        DefaultTableModel tableModel = new DefaultTableModel(new Object[0][4], columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }
        };
        JTable nguyenLieuTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(nguyenLieuTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Gọi loadDataTable ban đầu để hiển thị toàn bộ dữ liệu
        phanPhoiBUS.loadDataTable(tableModel, "", maNhaCungCap);

        // Xử lý sự kiện nút tìm kiếm
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            phanPhoiBUS.loadDataTable(tableModel, searchText, maNhaCungCap); // Gọi loadDataTable với giá trị tìm kiếm
        });


        // Nút lưu
        JPanel savePanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Lưu");
        savePanel.add(saveButton);
        frame.add(savePanel, BorderLayout.SOUTH);

        // Xử lý sự kiện nút Lưu
        saveButton.addActionListener(e -> {
            List<NguyenLieuDTO> selectedNguyenLieu = new ArrayList<>();
            for (int i = 0; i < nguyenLieuTable.getRowCount(); i++) {
                Boolean isSelected = (Boolean) nguyenLieuTable.getValueAt(i, 3);
                if (isSelected != null && isSelected) {
                    selectedNguyenLieu.add(nguyenLieuBUS.getNguyenLieuById( (String) nguyenLieuTable.getValueAt(i, 0))); // Lấy mã nguyên liệu
                }
            }

            if (selectedNguyenLieu.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn ít nhất một nguyên liệu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                phanPhoiBUS.insertPhanPhoi(maNhaCungCap, selectedNguyenLieu);
                JOptionPane.showMessageDialog(frame, "Lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}
