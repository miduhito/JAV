package GUI;

import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.KhachHangDTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.annotation.Documented;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class QuanLiKhachHangGUI extends RoundedPanel{
    // Table model chứa dữ liệu khách hàng hiển thị trong bảng
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };
    private KhachHangBUS khBUS = new KhachHangBUS();

    public QuanLiKhachHangGUI() {
        super(50, 50, Color.decode("#F5ECE0"));  // RoundedPanel với góc bo 50px
        this.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Quản Lý Khách Hàng", SwingConstants.LEFT);
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
        RoundedPanel khtablePanel = new RoundedPanel(50, 50, Color.WHITE);
        khtablePanel.setLayout(new BorderLayout());
        khtablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        khtablePanel.setPreferredSize(new Dimension(0, 400));

        // Control panel chứa thanh tìm kiếm và nút
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thanh tìm kiếm và icon kính lúp
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập thông tin khách hàng để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                khBUS.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                khBUS.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                khBUS.searchTableData(tableModel, searchField.getText());
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

        // Nút sửa khách hàng
        MyButton editCustomButton = new MyButton("Sửa khách hàng", editIcon);
        editCustomButton.setPreferredSize(new Dimension(180, 40));
        editCustomButton.setBackground(Color.decode("#EC5228"));
        editCustomButton.setForeground(Color.WHITE);
        editCustomButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editCustomButton.setFocusPainted(false);
        editCustomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editCustomButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editCustomButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Nút thêm khách hàng
        MyButton addCustomButton = new MyButton("Thêm khách hàng", addIcon);
        addCustomButton.setPreferredSize(new Dimension(180, 40));
        addCustomButton.setBackground(Color.decode("#EC5228"));
        addCustomButton.setForeground(Color.WHITE);
        addCustomButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addCustomButton.setFocusPainted(false);
        addCustomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addCustomButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addCustomButton.setBackground(Color.decode("#EC5228"));
            }
        });
        addCustomButton.addActionListener(e -> {
            addCustomButton.setEnabled(false);
            FormThemKhachHang(addCustomButton);
        });

        // Nút xóa khách hàng
        MyButton deleteCustomButton = new MyButton("Xóa khách hàng", deleteIcon);
        deleteCustomButton.setPreferredSize(new Dimension(180, 40));
        deleteCustomButton.setBackground(Color.decode("#EC5228"));
        deleteCustomButton.setForeground(Color.WHITE);
        deleteCustomButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteCustomButton.setFocusPainted(false);
        deleteCustomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteCustomButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteCustomButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Panel chứa các nút thao tác
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editCustomButton);
        buttonPanel.add(addCustomButton);
        buttonPanel.add(deleteCustomButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        khtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Tạo bảng và cấu hình hiển thị
        tableModel.setColumnIdentifiers(new Object[]{"Mã khách hàng", "Tên khách hàng", "Email", "Số điểm"});
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
                        String maKhachHang = table.getValueAt(row, 0).toString();
                        FormXemChiTietKhachHang(maKhachHang);
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
        khtablePanel.add(scrollPane, BorderLayout.CENTER);

        this.add(khtablePanel, BorderLayout.SOUTH);

        // Load dữ liệu bảng qua BUS
        khBUS.refreshTableData(tableModel);

        // Sự kiện sửa nhân viên: gọi sang BUS để lấy dữ liệu nhân viên cần sửa
        editCustomButton.addActionListener(e -> {
            editCustomButton.setEnabled(false);
            FormSuaKhachHang(editCustomButton, table);
        });

        // Sự kiện xóa nhân viên
        deleteCustomButton.addActionListener(e -> {
            XoaKhachHang(table);
        });
    }

    // Form thêm khách hàng
    private void FormThemKhachHang(JButton addCustomButton) {
        JFrame formThemKH = new JFrame("Thêm Khách Hàng");
        formThemKH.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Set the close operation correctly
        formThemKH.setSize(800, 600); // Set the window size
        formThemKH.setLayout(new BorderLayout()); // Set the layout

        // Header
        RoundedPanel themKHHeader = new RoundedPanel(30, 30, Color.WHITE);
        themKHHeader.setLayout(new BorderLayout());
        JLabel themKHTitle = new JLabel("Thêm Khách Hàng", SwingConstants.CENTER);
        themKHTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themKHTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themKHHeader.add(themKHTitle, BorderLayout.CENTER);
        formThemKH.add(themKHHeader, BorderLayout.NORTH);

        /*
        Sử dụng GridLayout(9,3,10,10) để mỗi dòng có:
            - Cột 1: Label mô tả field.
            - Cột 2: Ô nhập liệu (hoặc combobox).
            - Cột 3: Error icon (hoặc JLabel trống nếu không cần hiển thị lỗi).
        */
        RoundedPanel themKHCenter = new RoundedPanel(30, 30, Color.WHITE);
        themKHCenter.setLayout(new GridLayout(6, 3, 10, 10));
        themKHCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // ----- Dòng 1: Mã khách hàng (read-only, không cần kiểm tra nên không có icon) -----
        JLabel maKHLabel = new JLabel("Mã khách hàng:");
        JTextField maKHField = new JTextField(khBUS.generateMaKhachHang());
        maKHField.setEditable(false);
        maKHField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel(); // không hiển thị icon
        emptyLabel1.setPreferredSize(new Dimension(60, 60));
        themKHCenter.add(maKHLabel);
        themKHCenter.add(maKHField);
        themKHCenter.add(emptyLabel1);

        // ----- Dòng 2: Tên khách hàng -----
        JLabel tenKHLabel = new JLabel("Tên khách hàng:");
        JTextField tenKHField = new JTextField();
        // Tạo icon lỗi cho tên khách hàng
        JLabel nameErrorLabel = new JLabel();
        nameErrorLabel.setPreferredSize(new Dimension(60, 60));
        nameErrorLabel.setIcon(new ImageIcon(scaledImage));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setVisible(false); // ẩn ban đầu
        nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themKHCenter.add(tenKHLabel);
        themKHCenter.add(tenKHField);
        themKHCenter.add(nameErrorLabel);

        // ----- Dòng 3: Giới tính (không cần kiểm tra) -----
        JLabel gioiTinhLabel = new JLabel("Giới tính:");
        JComboBox<String> gioiTinhBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setPreferredSize(new Dimension(60, 60));
        themKHCenter.add(gioiTinhLabel);
        themKHCenter.add(gioiTinhBox);
        themKHCenter.add(emptyLabel2);

        // ----- Dòng 4: Số điện thoại -----
        JLabel sdtLabel = new JLabel("Số điện thoại:");
        JTextField sdtField = new JTextField();
        JLabel phoneErrorLabel = new JLabel();
        phoneErrorLabel.setPreferredSize(new Dimension(60, 60));
        phoneErrorLabel.setIcon(new ImageIcon(scaledImage));
        phoneErrorLabel.setForeground(Color.RED);
        phoneErrorLabel.setVisible(false);
        phoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themKHCenter.add(sdtLabel);
        themKHCenter.add(sdtField);
        themKHCenter.add(phoneErrorLabel);

        // ----- Dòng 5: Email -----
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel emailErrorLabel = new JLabel();
        emailErrorLabel.setPreferredSize(new Dimension(60, 60));
        emailErrorLabel.setIcon(new ImageIcon(scaledImage));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setVisible(false);
        emailErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themKHCenter.add(emailLabel);
        themKHCenter.add(emailField);
        themKHCenter.add(emailErrorLabel);

        // ----- Dòng 6: Địa chỉ -----
        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        JTextField diaChiField = new JTextField();
        // Nếu địa chỉ cần có thì dùng addressErrorLabel để setToolTipText
        JLabel addressErrorLabel = new JLabel();
        addressErrorLabel.setPreferredSize(new Dimension(60, 60));
        addressErrorLabel.setIcon(new ImageIcon(scaledImage));
        addressErrorLabel.setForeground(Color.RED);
        addressErrorLabel.setVisible(false);
        addressErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themKHCenter.add(diaChiLabel);
        themKHCenter.add(diaChiField);
        themKHCenter.add(addressErrorLabel);

        formThemKH.add(themKHCenter, BorderLayout.CENTER);

        // Footer chứa nút Lưu
        RoundedPanel themKHFooter = new RoundedPanel(30, 30, Color.WHITE);
        themKHFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
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
            String name = tenKHField.getText().trim();
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
                    // Gọi hàm thêm nhân viên của BUS.
                    // Lưu ý: Bạn cần đảm bảo rằng hàm insertNhanVien() của BUS chấp nhận kiểu dữ liệu đúng.
                    khBUS.insertKhachHang(
                            maKHField.getText(),
                            tenKHField.getText(),
                            gioiTinhBox.getSelectedItem().toString(),
                            sdtField.getText(),
                            emailField.getText(),
                            diaChiField.getText(),
                            khBUS.generateSoDiem()
                    );
                    khBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemKH, "Thêm khách hàng thành công!");
                    formThemKH.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formThemKH, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        themKHFooter.add(saveButton);
        formThemKH.add(themKHFooter, BorderLayout.SOUTH);

        formThemKH.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addCustomButton.setEnabled(true);
            }
        });
        formThemKH.setVisible(true);
    }

    // Form sửa khách hàng: sử dụng BUS để lấy dữ liệu khách hàng dựa trên mã và cập nhật lại
    private void FormSuaKhachHang(JButton editCustomButton, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maKhachHang = tableModel.getValueAt(selectedRow, 0).toString();

            // Gọi BUS lấy thông tin khách hàng qua mã (trả về Map<String,String>)
            KhachHangDTO empData = khBUS.getKhachHangById(maKhachHang);
            if (empData == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng cần chỉnh sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                editCustomButton.setEnabled(true);
                return;
            }
            String tenKhachHang = empData.getTenKhachHang();
            String gioiTinh = empData.getGioiTinh();
            String SDT = empData.getSDT();
            String email = empData.getEmail();
            String diaChi = empData.getDiaChi();
            int soDiem = empData.getSoDiem();
            System.out.println(soDiem);

            JFrame formSuaKH = new JFrame("Sửa Khách Hàng");
            formSuaKH.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            formSuaKH.setSize(800, 600);
            formSuaKH.setLayout(new BorderLayout());

            // Header
            RoundedPanel suaKHHeader = new RoundedPanel(30, 30, Color.WHITE);
            suaKHHeader.setLayout(new BorderLayout());
            JLabel suaKHTitle = new JLabel("Sửa Khách Hàng", SwingConstants.CENTER);
            suaKHTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
            suaKHTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            suaKHHeader.add(suaKHTitle, BorderLayout.CENTER);
            formSuaKH.add(suaKHHeader, BorderLayout.NORTH);

            //Tạo icon warning dấu chấm thang
            ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

            // Center panel các input
            RoundedPanel suaKHCenter = new RoundedPanel(30, 30, Color.WHITE);
            suaKHCenter.setLayout(new GridLayout(7, 3, 10, 10));
            suaKHCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel maKHLabel = new JLabel("Mã khách hàng:");
            JTextField maKHField = new JTextField(maKhachHang);
            maKHField.setEditable(false);
            maKHField.setBackground(Color.LIGHT_GRAY);
            JLabel emptyLabel1 = new JLabel();

            JLabel tenKHLabel = new JLabel("Tên khách hàng:");
            JTextField tenKHField = new JTextField(tenKhachHang);
            JLabel nameErrorLabel = new JLabel();
            nameErrorLabel.setPreferredSize(new Dimension(60, 60));
            nameErrorLabel.setIcon(new ImageIcon(scaledImage));
            nameErrorLabel.setForeground(Color.RED);
            nameErrorLabel.setVisible(false);
            nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel gioiTinhLabel = new JLabel("Giới tính:");
            JComboBox<String> gioiTinhBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
            gioiTinhBox.setSelectedItem(gioiTinh);
            JLabel emptyLabel2 = new JLabel();

            JLabel SDTLabel = new JLabel("Số điện thoại:");
            JTextField SDTField = new JTextField(SDT);
            JLabel phoneErrorLabel = new JLabel();
            phoneErrorLabel.setPreferredSize(new Dimension(60, 60));
            phoneErrorLabel.setIcon(new ImageIcon(scaledImage));
            phoneErrorLabel.setForeground(Color.RED);
            phoneErrorLabel.setVisible(false);
            phoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel emailLabel = new JLabel("Email:");
            JTextField emailField = new JTextField(email);
            JLabel emailErrorLabel = new JLabel();
            emailErrorLabel.setPreferredSize(new Dimension(60, 60));
            emailErrorLabel.setIcon(new ImageIcon(scaledImage));
            emailErrorLabel.setForeground(Color.RED);
            emailErrorLabel.setVisible(false);
            emailErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel diaChiLabel = new JLabel("Địa chỉ:");
            JTextField diaChiField = new JTextField(diaChi);
            JLabel addressErrorLabel = new JLabel();
            addressErrorLabel.setPreferredSize(new Dimension(60, 60));
            addressErrorLabel.setIcon(new ImageIcon(scaledImage));
            addressErrorLabel.setForeground(Color.RED);
            addressErrorLabel.setVisible(false);
            addressErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel soDiemLabel = new JLabel("Số điểm");
            JTextField soDiemField = new JTextField(String.valueOf(soDiem));
            JLabel pointErrorLabel = new JLabel();
            pointErrorLabel.setPreferredSize(new Dimension(60, 60));
            pointErrorLabel.setIcon(new ImageIcon(scaledImage));
            pointErrorLabel.setForeground(Color.RED);
            pointErrorLabel.setVisible(false);
            pointErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            suaKHCenter.add(maKHLabel);       suaKHCenter.add(maKHField);       suaKHCenter.add(emptyLabel1);
            suaKHCenter.add(tenKHLabel);        suaKHCenter.add(tenKHField);        suaKHCenter.add(nameErrorLabel);
            suaKHCenter.add(gioiTinhLabel);     suaKHCenter.add(gioiTinhBox);       suaKHCenter.add(emptyLabel2);
            suaKHCenter.add(SDTLabel);          suaKHCenter.add(SDTField);          suaKHCenter.add(phoneErrorLabel);
            suaKHCenter.add(emailLabel);        suaKHCenter.add(emailField);        suaKHCenter.add(emailErrorLabel);
            suaKHCenter.add(diaChiLabel);       suaKHCenter.add(diaChiField);       suaKHCenter.add(addressErrorLabel);
            suaKHCenter.add(soDiemLabel);       suaKHCenter.add(soDiemField);       suaKHCenter.add(pointErrorLabel);
            formSuaKH.add(suaKHCenter, BorderLayout.CENTER);

            RoundedPanel suaKHFooter = new RoundedPanel(30, 30, Color.WHITE);
            suaKHFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
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

                // Validate Tên khách hàng: không được để trống
                String name = tenKHField.getText().trim();
                if(name.isEmpty()){
                    isValid = false;
                    nameErrorLabel.setToolTipText("Tên khách hàng không được để trống.");
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
                // Gọi hàm BUS cập nhật thông tin khách hàng
                    khBUS.updateKhachHang(
                            maKhachHang,
                            tenKHField.getText(),
                            gioiTinhBox.getSelectedItem().toString(),
                            SDTField.getText(),
                            emailField.getText(),
                            diaChiField.getText(),
                            Integer.parseInt(soDiemField.getText())
                    );
                    khBUS.refreshTableData(tableModel);
                    formSuaKH.dispose();
                }
            });
            suaKHFooter.add(saveButton);
            formSuaKH.add(suaKHFooter, BorderLayout.SOUTH);

            formSuaKH.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    editCustomButton.setEnabled(true);
                }
            });
            formSuaKH.setVisible(true);
        } else {
            editCustomButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, 
                    "Vui lòng chọn khách hàng cần cập nhật thông tin!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void XoaKhachHang(JTable table) {
        int selectedRow = table.getSelectedRow();

        if(selectedRow != -1) {
            String maKhachHang = tableModel.getValueAt(selectedRow, 0).toString();
            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng có mã " + maKhachHang + "?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                khBUS.deleteKhachHang(maKhachHang);
                khBUS.refreshTableData(tableModel);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void FormXemChiTietKhachHang(String maKhachHang) {
        JFrame formXemChiTietKH = new JFrame("Chi Tiết Khách Hàng");
        formXemChiTietKH.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        formXemChiTietKH.setSize(800, 600);
        formXemChiTietKH.setLayout(new BorderLayout());

        // Gọi BUS lấy thông tin khách hàng qua mã (trả về Map<String,String>)
        KhachHangDTO empData = khBUS.getKhachHangById(maKhachHang);
        if (empData == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String tenKhachHang = empData.getTenKhachHang();
        String gioiTinh = empData.getGioiTinh();
        String SDT = empData.getSDT();
        String email = empData.getEmail();
        String diaChi = empData.getDiaChi();
        String soDiem = String.valueOf(empData.getSoDiem());

        // Header
        RoundedPanel chiTietKHHeader = new RoundedPanel(30, 30, Color.WHITE);
        chiTietKHHeader.setLayout(new BorderLayout());
        JLabel chiTietKHTitle = new JLabel("Chi Tiết Khách Hàng", SwingConstants.CENTER);
        chiTietKHTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        chiTietKHTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        chiTietKHHeader.add(chiTietKHTitle, BorderLayout.CENTER);
        formXemChiTietKH.add(chiTietKHHeader, BorderLayout.NORTH);

        // Center panel các input
        RoundedPanel chiTietKHCenter = new RoundedPanel(30, 30, Color.WHITE);
        chiTietKHCenter.setLayout(new GridLayout(9, 2, 10, 10));
        chiTietKHCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel maKHLabel = new JLabel("Mã khách hàng:");
        JLabel maKHField = new JLabel(maKhachHang);

        JLabel tenKHLabel = new JLabel("Tên khách hàng:");
        JLabel tenKHField = new JLabel(tenKhachHang);

        JLabel gioiTinhLabel = new JLabel("Giới tính:");
        JLabel gioiTinhField = new JLabel(gioiTinh);

        JLabel SDTLabel = new JLabel("Số điện thoại:");
        JLabel SDTField = new JLabel(SDT);

        JLabel emailLabel = new JLabel("Email:");
        JLabel emailField = new JLabel(email);

        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        JLabel diaChiField = new JLabel(diaChi);

        JLabel soDiemLabel = new JLabel("Số điểm:");
        JLabel soDiemField = new JLabel(soDiem);

        chiTietKHCenter.add(maKHLabel);       chiTietKHCenter.add(maKHField);
        chiTietKHCenter.add(tenKHLabel);        chiTietKHCenter.add(tenKHField);
        chiTietKHCenter.add(gioiTinhLabel);     chiTietKHCenter.add(gioiTinhField);
        chiTietKHCenter.add(SDTLabel);          chiTietKHCenter.add(SDTField);
        chiTietKHCenter.add(emailLabel);        chiTietKHCenter.add(emailField);
        chiTietKHCenter.add(diaChiLabel);       chiTietKHCenter.add(diaChiField);
        chiTietKHCenter.add(soDiemLabel);       chiTietKHCenter.add(soDiemField);
        formXemChiTietKH.add(chiTietKHCenter, BorderLayout.CENTER);

        formXemChiTietKH.setVisible(true);
    }
}
