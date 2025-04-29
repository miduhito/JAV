package GUI;

import BUS.NhanVienBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.NhanVienDTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.annotation.Documented;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class QuanLiNhanVienGUI extends RoundedPanel {
    // Table model chứa dữ liệu nhân viên hiển thị trong bảng
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };
    private NhanVienBUS nvBUS = new NhanVienBUS();

    public QuanLiNhanVienGUI() {
        super(50, 50, Color.decode("#F5ECE0"));  // RoundedPanel với góc bo 50px
        this.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Quản Lý Nhân Viên", SwingConstants.LEFT);
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
        RoundedPanel nvtablePanel = new RoundedPanel(50, 50, Color.WHITE);
        nvtablePanel.setLayout(new BorderLayout());
        nvtablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nvtablePanel.setPreferredSize(new Dimension(0, 400));

        // Control panel chứa thanh tìm kiếm và nút
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thanh tìm kiếm và icon kính lúp
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập thông tin nhân viên để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                nvBUS.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                nvBUS.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                nvBUS.searchTableData(tableModel, searchField.getText());
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
        
        // Nút sửa nhân viên
        MyButton editEmployeeButton = new MyButton("Sửa nhân viên", editIcon);
        editEmployeeButton.setPreferredSize(new Dimension(180, 40));
        editEmployeeButton.setBackground(Color.decode("#EC5228"));
        editEmployeeButton.setForeground(Color.WHITE);
        editEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editEmployeeButton.setFocusPainted(false);
        editEmployeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editEmployeeButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editEmployeeButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Nút thêm nhân viên
        MyButton addEmployeeButton = new MyButton("Thêm nhân viên", addIcon);
        addEmployeeButton.setPreferredSize(new Dimension(180, 40));
        addEmployeeButton.setBackground(Color.decode("#EC5228"));
        addEmployeeButton.setForeground(Color.WHITE);
        addEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addEmployeeButton.setFocusPainted(false);
        addEmployeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addEmployeeButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addEmployeeButton.setBackground(Color.decode("#EC5228"));
            }
        });
        addEmployeeButton.addActionListener(e -> {
            addEmployeeButton.setEnabled(false);
            FormThemNhanVien(addEmployeeButton);
        });

        // Nút xóa nhân viên
        MyButton deleteEmployeeButton = new MyButton("Xóa nhân viên", deleteIcon);
        deleteEmployeeButton.setPreferredSize(new Dimension(180, 40));
        deleteEmployeeButton.setBackground(Color.decode("#EC5228"));
        deleteEmployeeButton.setForeground(Color.WHITE);
        deleteEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteEmployeeButton.setFocusPainted(false);
        deleteEmployeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteEmployeeButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteEmployeeButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // Panel chứa các nút thao tác
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        nvtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Tạo bảng và cấu hình hiển thị
        tableModel.setColumnIdentifiers(new Object[]{"Mã nhân viên", "Tên nhân viên", "Tên chức vụ", "Trạng thái"});
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
                        String maNhanVien = table.getValueAt(row, 0).toString();
                        FormXemChiTietNhanVien(maNhanVien);
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
        nvtablePanel.add(scrollPane, BorderLayout.CENTER);

        this.add(nvtablePanel, BorderLayout.SOUTH);

        // Load dữ liệu bảng qua BUS
        nvBUS.refreshTableData(tableModel);

        // Sự kiện sửa nhân viên: gọi sang BUS để lấy dữ liệu nhân viên cần sửa
        editEmployeeButton.addActionListener(e -> {
            editEmployeeButton.setEnabled(false);
            FormSuaNhanVien(editEmployeeButton, table);
        });

        // Sự kiện xóa nhân viên
        deleteEmployeeButton.addActionListener(e -> {
            XoaNhanVien(table);
        });
    }

    // Form thêm nhân viên
    private void FormThemNhanVien(JButton addEmployeeButton) {
        JFrame formThemNV = new JFrame("Thêm Nhân Viên");
        formThemNV.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Set the close operation correctly
        formThemNV.setSize(800, 600); // Set the window size
        formThemNV.setLayout(new BorderLayout()); // Set the layout

        // Header
        RoundedPanel themNVHeader = new RoundedPanel(30, 30, Color.WHITE);
        themNVHeader.setLayout(new BorderLayout());
        JLabel themNVTitle = new JLabel("Thêm Nhân Viên", SwingConstants.CENTER);
        themNVTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themNVTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themNVHeader.add(themNVTitle, BorderLayout.CENTER);
        formThemNV.add(themNVHeader, BorderLayout.NORTH);

        /*
        Sử dụng GridLayout(9,3,10,10) để mỗi dòng có:
            - Cột 1: Label mô tả field.
            - Cột 2: Ô nhập liệu (hoặc combobox).
            - Cột 3: Error icon (hoặc JLabel trống nếu không cần hiển thị lỗi).
        */
        RoundedPanel themNVCenter = new RoundedPanel(30, 30, Color.WHITE);
        themNVCenter.setLayout(new GridLayout(9, 3, 10, 10));
        themNVCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // ----- Dòng 1: Mã nhân viên (read-only, không cần kiểm tra nên không có icon) -----
        JLabel maNVLabel = new JLabel("Mã nhân viên:");
        JTextField maNVField = new JTextField(nvBUS.generateMaNhanVien());
        maNVField.setEditable(false);
        maNVField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel(); // không hiển thị icon
        emptyLabel1.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(maNVLabel);
        themNVCenter.add(maNVField);
        themNVCenter.add(emptyLabel1);

        // ----- Dòng 2: Tên nhân viên -----
        JLabel tenNVLabel = new JLabel("Tên nhân viên:");
        JTextField tenNVField = new JTextField();
        // Tạo icon lỗi cho tên nhân viên
        JLabel nameErrorLabel = new JLabel();
        nameErrorLabel.setPreferredSize(new Dimension(60, 60));
        nameErrorLabel.setIcon(new ImageIcon(scaledImage));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setVisible(false); // ẩn ban đầu
        nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(tenNVLabel);
        themNVCenter.add(tenNVField);
        themNVCenter.add(nameErrorLabel);

        // ----- Dòng 3: Số điện thoại -----
        JLabel sdtLabel = new JLabel("Số điện thoại:");
        JTextField sdtField = new JTextField();
        JLabel phoneErrorLabel = new JLabel();
        phoneErrorLabel.setPreferredSize(new Dimension(60, 60));
        phoneErrorLabel.setIcon(new ImageIcon(scaledImage));
        phoneErrorLabel.setForeground(Color.RED);
        phoneErrorLabel.setVisible(false);
        phoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(sdtLabel);
        themNVCenter.add(sdtField);
        themNVCenter.add(phoneErrorLabel);

        // ----- Dòng 4: Email -----
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel emailErrorLabel = new JLabel();
        emailErrorLabel.setPreferredSize(new Dimension(60, 60));
        emailErrorLabel.setIcon(new ImageIcon(scaledImage));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setVisible(false);
        emailErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(emailLabel);
        themNVCenter.add(emailField);
        themNVCenter.add(emailErrorLabel);

        // ----- Dòng 5: Ngày sinh -----
        JLabel ngaySinhLabel = new JLabel("Ngày sinh:");
        JDateChooser ngaySinhField = new JDateChooser();
        ngaySinhField.setDateFormatString("yyyy-MM-dd");
        JLabel birthErrorLabel = new JLabel();
        birthErrorLabel.setPreferredSize(new Dimension(60, 60));
        birthErrorLabel.setIcon(new ImageIcon(scaledImage));
        birthErrorLabel.setForeground(Color.RED);
        birthErrorLabel.setVisible(false);
        birthErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(ngaySinhLabel);
        themNVCenter.add(ngaySinhField);
        themNVCenter.add(birthErrorLabel);

        // ----- Dòng 6: Giới tính (không cần kiểm tra) -----
        JLabel gioiTinhLabel = new JLabel("Giới tính:");
        JComboBox<String> gioiTinhBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(gioiTinhLabel);
        themNVCenter.add(gioiTinhBox);
        themNVCenter.add(emptyLabel2);

        // ----- Dòng 7: Địa chỉ -----
        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        JTextField diaChiField = new JTextField();
        JLabel addressErrorLabel = new JLabel();
        addressErrorLabel.setPreferredSize(new Dimension(60, 60));
        addressErrorLabel.setIcon(new ImageIcon(scaledImage));
        addressErrorLabel.setForeground(Color.RED);
        addressErrorLabel.setVisible(false);
        addressErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(diaChiLabel);
        themNVCenter.add(diaChiField);
        themNVCenter.add(addressErrorLabel);

        // ----- Dòng 8: Trạng thái -----
        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JComboBox<String> trangThaiField = new JComboBox<>(new String[]{"Đang làm việc", "Đã nghỉ việc"});
        JLabel emptyLabel4 = new JLabel();
        emptyLabel4.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(trangThaiLabel);
        themNVCenter.add(trangThaiField);
        themNVCenter.add(emptyLabel4);

        // ----- Dòng 9: Chức vụ -----
        JLabel chucVuLabel = new JLabel("Chức vụ:");
        JComboBox<String> chucVuBox = new JComboBox<>();
        nvBUS.loadChucVuToBox(chucVuBox);
        JLabel emptyLabel5 = new JLabel();
        emptyLabel5.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(chucVuLabel);
        themNVCenter.add(chucVuBox);
        themNVCenter.add(emptyLabel5);

        formThemNV.add(themNVCenter, BorderLayout.CENTER);

        // Footer chứa nút Lưu
        RoundedPanel themNVFooter = new RoundedPanel(30, 30, Color.WHITE);
        themNVFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
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
            birthErrorLabel.setVisible(false);
            addressErrorLabel.setVisible(false);
            
            boolean isValid = true;

            // Validate Tên nhân viên: không được để trống
            String name = tenNVField.getText().trim();
            if(name.isEmpty()){
                isValid = false;
                nameErrorLabel.setToolTipText("Tên nhân viên không được để trống.");
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

            // Validate Ngày sinh (dd/MM/yyyy)
            Date birth = ngaySinhField.getDate();
            java.util.List<String> birthErrors = new java.util.ArrayList<>();
            if(birth == null){
                birthErrors.add("Ngày sinh không được để trống.");
            }
            if(!birthErrors.isEmpty()){
                isValid = false;
                StringBuilder birthTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: birthErrors){
                    birthTooltip.append("<li>").append(err).append("</li>");
                }
                birthTooltip.append("</ul></html>");
                birthErrorLabel.setToolTipText(birthTooltip.toString());
                birthErrorLabel.setVisible(true);
            }

            // Validate địa chỉ
            String address = diaChiField.getText().trim();
            if(address.isEmpty()) {
                isValid = false;
                addressErrorLabel.setToolTipText("Địa chỉ không được để trống.");
                addressErrorLabel.setVisible(true);
            }
            
            // Nếu tất cả dữ liệu hợp lệ, gọi vào BUS để lưu dữ liệu vào CSDL
            if(isValid) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    // Gọi hàm thêm nhân viên của BUS.
                    // Lưu ý: Bạn cần đảm bảo rằng hàm insertNhanVien() của BUS chấp nhận kiểu dữ liệu đúng.
                    nvBUS.insertNhanVien(
                            maNVField.getText(),
                            tenNVField.getText(),
                            sdtField.getText(), // Nếu cần chuyển đổi về số thì xử lý tại BUS
                            emailField.getText(),
                            sdf.format(ngaySinhField.getDate()),
                            gioiTinhBox.getSelectedItem().toString(),
                            diaChiField.getText(),
                            trangThaiField.getSelectedItem().toString(),
                            chucVuBox.getSelectedItem().toString()
                    );
                    nvBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemNV, "Thêm nhân viên thành công!");
                    formThemNV.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formThemNV, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        themNVFooter.add(saveButton);
        formThemNV.add(themNVFooter, BorderLayout.SOUTH);

        formThemNV.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addEmployeeButton.setEnabled(true);
            }
        });
        formThemNV.setVisible(true);
    }

    // Form sửa nhân viên: sử dụng BUS để lấy dữ liệu nhân viên dựa trên mã và cập nhật lại
    private void FormSuaNhanVien(JButton editEmployeeButton, JTable table) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maNhanVien = tableModel.getValueAt(selectedRow, 0).toString();

                // Gọi BUS lấy thông tin nhân viên qua mã (trả về Map<String,String>)
                NhanVienDTO empData = nvBUS.getNhanVienById(maNhanVien);
                if (empData == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên cần chỉnh sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                    editEmployeeButton.setEnabled(true);
                    return;
                }
                String tenNhanVien = empData.getTenNhanVien();
                String SDT = empData.getSDT();
                String email = empData.getEmail();
                String ngaySinh = empData.getNgaySinh();
                
                SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                Date formattedNgaySinh = dbFormat.parse(ngaySinh);
                
                String gioiTinh = empData.getGioiTinh();
                String diaChi = empData.getDiaChi();
                String trangThai = empData.getTrangThai();
                String tenChucVu = empData.getTenChucVu();

                JFrame formSuaNV = new JFrame("Sửa Nhân Viên");
                formSuaNV.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                formSuaNV.setSize(800, 600);
                formSuaNV.setLayout(new BorderLayout());

                // Header
                RoundedPanel suaNVHeader = new RoundedPanel(30, 30, Color.WHITE);
                suaNVHeader.setLayout(new BorderLayout());
                JLabel suaNVTitle = new JLabel("Sửa Nhân Viên", SwingConstants.CENTER);
                suaNVTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
                suaNVTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                suaNVHeader.add(suaNVTitle, BorderLayout.CENTER);
                formSuaNV.add(suaNVHeader, BorderLayout.NORTH);

                //Tạo icon warning dấu chấm thang
                ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
                Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

                // Center panel các input
                RoundedPanel suaNVCenter = new RoundedPanel(30, 30, Color.WHITE);
                suaNVCenter.setLayout(new GridLayout(10, 3, 10, 10));
                suaNVCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JLabel maNVLabel = new JLabel("Mã nhân viên:");
                JTextField maNVField = new JTextField(maNhanVien);
                maNVField.setEditable(false);
                maNVField.setBackground(Color.LIGHT_GRAY);
                JLabel emptyLabel1 = new JLabel();
                emptyLabel1.setPreferredSize(new Dimension(60, 60));
                suaNVCenter.add(maNVLabel);       
                suaNVCenter.add(maNVField);
                suaNVCenter.add(emptyLabel1);

                JLabel tenNVLabel = new JLabel("Tên nhân viên:");
                JTextField tenNVField = new JTextField(tenNhanVien);
                JLabel employeeNameErrorLabel = new JLabel();
                employeeNameErrorLabel.setPreferredSize(new Dimension(60, 60));
                employeeNameErrorLabel.setIcon(new ImageIcon(scaledImage));
                employeeNameErrorLabel.setForeground(Color.RED);
                employeeNameErrorLabel.setVisible(false);
                employeeNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                suaNVCenter.add(tenNVLabel);        
                suaNVCenter.add(tenNVField);
                suaNVCenter.add(employeeNameErrorLabel);

                JLabel SDTLabel = new JLabel("Số điện thoại:");
                JTextField SDTField = new JTextField(SDT);
                JLabel employeePhoneErrorLabel = new JLabel();
                employeePhoneErrorLabel.setPreferredSize(new Dimension(60, 60));
                employeePhoneErrorLabel.setIcon(new ImageIcon(scaledImage));
                employeePhoneErrorLabel.setForeground(Color.RED);
                employeePhoneErrorLabel.setVisible(false);
                employeePhoneErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                suaNVCenter.add(SDTLabel);          
                suaNVCenter.add(SDTField);
                suaNVCenter.add(employeePhoneErrorLabel);

                JLabel emailLabel = new JLabel("Email:");
                JTextField emailField = new JTextField(email);
                JLabel employeeEmailErrorLabel = new JLabel();
                employeeEmailErrorLabel.setPreferredSize(new Dimension(60, 60));
                employeeEmailErrorLabel.setIcon(new ImageIcon(scaledImage));
                employeeEmailErrorLabel.setForeground(Color.RED);
                employeeEmailErrorLabel.setVisible(false);
                employeeEmailErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                suaNVCenter.add(emailLabel);        
                suaNVCenter.add(emailField);
                suaNVCenter.add(employeeEmailErrorLabel);

                JLabel ngaySinhLabel = new JLabel("Ngày sinh:");
                JDateChooser ngaySinhField = new JDateChooser();
                ngaySinhField.setDateFormatString("yyyy-MM-dd");
                ngaySinhField.setDate(formattedNgaySinh);
                JLabel employeeBirthErrorLabel = new JLabel();
                employeeBirthErrorLabel.setPreferredSize(new Dimension(60, 60));
                employeeBirthErrorLabel.setIcon(new ImageIcon(scaledImage));
                employeeBirthErrorLabel.setForeground(Color.RED);
                employeeBirthErrorLabel.setVisible(false);
                employeeBirthErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                suaNVCenter.add(ngaySinhLabel);     
                suaNVCenter.add(ngaySinhField);
                suaNVCenter.add(employeeBirthErrorLabel);

                JLabel gioiTinhLabel = new JLabel("Giới tính:");
                JComboBox<String> gioiTinhBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
                gioiTinhBox.setSelectedItem(gioiTinh);
                JLabel emptyLabel2 = new JLabel();
                emptyLabel2.setPreferredSize(new Dimension(60, 60));
                suaNVCenter.add(gioiTinhLabel);     
                suaNVCenter.add(gioiTinhBox);
                suaNVCenter.add(emptyLabel2);

                JLabel diaChiLabel = new JLabel("Địa chỉ:");
                JTextField diaChiField = new JTextField(diaChi);
                JLabel employeeAddressErrorLabel = new JLabel();
                employeeAddressErrorLabel.setPreferredSize(new Dimension(60, 60));
                employeeAddressErrorLabel.setIcon(new ImageIcon(scaledImage));
                employeeAddressErrorLabel.setForeground(Color.RED);
                employeeAddressErrorLabel.setVisible(false);
                employeeAddressErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                suaNVCenter.add(diaChiLabel);       
                suaNVCenter.add(diaChiField);
                suaNVCenter.add(employeeAddressErrorLabel);

                JLabel trangThaiLabel = new JLabel("Trạng thái:");
                JComboBox<String> trangThaiField = new JComboBox<>(new String[]{"Đang làm việc", "Đã nghỉ việc"});
                trangThaiField.setSelectedItem(trangThai);
                JLabel emptyLabel3 = new JLabel();
                emptyLabel3.setPreferredSize(new Dimension(60, 60));
                suaNVCenter.add(trangThaiLabel);    
                suaNVCenter.add(trangThaiField);
                suaNVCenter.add(emptyLabel3);

                JLabel chucVuLabel = new JLabel("Chức vụ:");
                JComboBox<String> chucVuBox = new JComboBox<>();
                nvBUS.loadChucVuToBox(chucVuBox);
                chucVuBox.setSelectedItem(tenChucVu);
                JLabel emptyLabel4 = new JLabel();
                emptyLabel4.setPreferredSize(new Dimension(60, 60));
                suaNVCenter.add(chucVuLabel);       
                suaNVCenter.add(chucVuBox);
                suaNVCenter.add(emptyLabel4);

                
                formSuaNV.add(suaNVCenter, BorderLayout.CENTER);

                RoundedPanel suaNVFooter = new RoundedPanel(30, 30, Color.WHITE);
                suaNVFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
                JButton saveButton = new JButton("Lưu");
                saveButton.setPreferredSize(new Dimension(100, 40));
                saveButton.setBackground(Color.decode("#EC5228"));
                saveButton.setForeground(Color.WHITE);
                saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
                saveButton.addActionListener(event -> {
                    // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
                    employeeNameErrorLabel.setVisible(false);
                    employeePhoneErrorLabel.setVisible(false);
                    employeeEmailErrorLabel.setVisible(false);
                    employeeBirthErrorLabel.setVisible(false);
                    employeeAddressErrorLabel.setVisible(false);

                    boolean isValid = true;

                    // Validate Tên nhân viên: không được để trống
                    String name = tenNVField.getText().trim();
                    if(name.isEmpty()){
                        isValid = false;
                        employeeNameErrorLabel.setToolTipText("Tên nhân viên không được để trống.");
                        employeeNameErrorLabel.setVisible(true);
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
                        employeePhoneErrorLabel.setToolTipText(phoneTooltip.toString());
                        employeePhoneErrorLabel.setVisible(true);
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
                        employeeEmailErrorLabel.setToolTipText(emailTooltip.toString());
                        employeeEmailErrorLabel.setVisible(true);
                    }

                    // Validate Ngày sinh
                    Date birth = ngaySinhField.getDate();
                    java.util.List<String> birthErrors = new java.util.ArrayList<>();
                    if(birth == null){
                        birthErrors.add("Ngày sinh không được để trống.");
                    }
                    if(!birthErrors.isEmpty()){
                        isValid = false;
                        StringBuilder birthTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                        for(String err: birthErrors){
                            birthTooltip.append("<li>").append(err).append("</li>");
                        }
                        birthTooltip.append("</ul></html>");
                        employeeBirthErrorLabel.setToolTipText(birthTooltip.toString());
                        employeeBirthErrorLabel.setVisible(true);
                    }

                    // Validate địa chỉ
                    String address = diaChiField.getText().trim();
                    if(address.isEmpty()) {
                        isValid = false;
                        employeeAddressErrorLabel.setToolTipText("Địa chỉ không được để trống.");
                        employeeAddressErrorLabel.setVisible(true);
                    }

                    if(isValid) {
                    // Gọi hàm BUS cập nhật thông tin nhân viên
                        nvBUS.updateNhanVien(
                                maNhanVien,
                                tenNVField.getText(),
                                SDTField.getText(),
                                emailField.getText(),
                                dbFormat.format(ngaySinhField.getDate()),
                                gioiTinhBox.getSelectedItem().toString(),
                                diaChiField.getText(),
                                trangThaiField.getSelectedItem().toString(),
                                chucVuBox.getSelectedItem().toString()
                        );
                        nvBUS.refreshTableData(tableModel);
                        formSuaNV.dispose();
                    }
                });
                suaNVFooter.add(saveButton);
                formSuaNV.add(suaNVFooter, BorderLayout.SOUTH);

                formSuaNV.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        editEmployeeButton.setEnabled(true);
                    }
                });
                formSuaNV.setVisible(true);
            } else {
                editEmployeeButton.setEnabled(true);
                JOptionPane.showMessageDialog(null, 
                        "Vui lòng chọn nhân viên cần cập nhật thông tin!", 
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void XoaNhanVien(JTable table) {
        int selectedRow = table.getSelectedRow();

        if(selectedRow != -1) {
            String maNhanVien = tableModel.getValueAt(selectedRow, 0).toString();
            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên có mã " + maNhanVien + "?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                String success = nvBUS.deleteNhanVien(maNhanVien);
                if(success == "") {
                    JOptionPane.showMessageDialog(null, "Đã xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    nvBUS.refreshTableData(tableModel);
                } else {
                    JOptionPane.showMessageDialog(null, success, "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void FormXemChiTietNhanVien(String maNhanVien) {
        JFrame formXemChiTietNV = new JFrame("Chi Tiết Nhân Viên");
        formXemChiTietNV.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        formXemChiTietNV.setSize(800, 600);
        formXemChiTietNV.setLayout(new BorderLayout());

        // Gọi BUS lấy thông tin nhân viên qua mã (trả về Map<String,String>)
        NhanVienDTO empData = nvBUS.getNhanVienById(maNhanVien);
        if (empData == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String tenNhanVien = empData.getTenNhanVien();
        String SDT = empData.getSDT();
        String email = empData.getEmail();
        String ngaySinh = empData.getNgaySinh();
        String gioiTinh = empData.getGioiTinh();
        String diaChi = empData.getDiaChi();
        String trangThai = empData.getTrangThai();
        String tenChucVu = empData.getTenChucVu();
        String tenDangNhap = empData.getTenDangNhap();

        // Header
        RoundedPanel chiTietNVHeader = new RoundedPanel(30, 30, Color.WHITE);
        chiTietNVHeader.setLayout(new BorderLayout());
        JLabel chiTietNVTitle = new JLabel("Chi Tiết Nhân Viên", SwingConstants.CENTER);
        chiTietNVTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        chiTietNVTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        chiTietNVHeader.add(chiTietNVTitle, BorderLayout.CENTER);
        formXemChiTietNV.add(chiTietNVHeader, BorderLayout.NORTH);

        // Center panel các input
        RoundedPanel chiTietNVCenter = new RoundedPanel(30, 30, Color.WHITE);
        chiTietNVCenter.setLayout(new GridLayout(9, 2, 10, 10));
        chiTietNVCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel maNVLabel = new JLabel("Mã nhân viên:");
        JLabel maNVField = new JLabel(maNhanVien);

        JLabel tenNVLabel = new JLabel("Tên nhân viên:");
        JLabel tenNVField = new JLabel(tenNhanVien);

        JLabel SDTLabel = new JLabel("Số điện thoại:");
        JLabel SDTField = new JLabel(SDT);

        JLabel emailLabel = new JLabel("Email:");
        JLabel emailField = new JLabel(email);

        JLabel ngaySinhLabel = new JLabel("Ngày sinh:");
        JLabel ngaySinhField = new JLabel(ngaySinh);

        JLabel gioiTinhLabel = new JLabel("Giới tính:");
        JLabel gioiTinhField = new JLabel(gioiTinh);

        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        JLabel diaChiField = new JLabel(diaChi);

        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JLabel trangThaiField = new JLabel(trangThai);

        JLabel chucVuLabel = new JLabel("Chức vụ:");
        JLabel chucVuField = new JLabel(tenChucVu);

        chiTietNVCenter.add(maNVLabel);       chiTietNVCenter.add(maNVField);
        chiTietNVCenter.add(tenNVLabel);        chiTietNVCenter.add(tenNVField);
        chiTietNVCenter.add(SDTLabel);          chiTietNVCenter.add(SDTField);
        chiTietNVCenter.add(emailLabel);        chiTietNVCenter.add(emailField);
        chiTietNVCenter.add(ngaySinhLabel);     chiTietNVCenter.add(ngaySinhField);
        chiTietNVCenter.add(gioiTinhLabel);     chiTietNVCenter.add(gioiTinhField);
        chiTietNVCenter.add(diaChiLabel);       chiTietNVCenter.add(diaChiField);
        chiTietNVCenter.add(trangThaiLabel);    chiTietNVCenter.add(trangThaiField);
        chiTietNVCenter.add(chucVuLabel);       chiTietNVCenter.add(chucVuField);
        formXemChiTietNV.add(chiTietNVCenter, BorderLayout.CENTER);

        formXemChiTietNV.setVisible(true);
    }
}