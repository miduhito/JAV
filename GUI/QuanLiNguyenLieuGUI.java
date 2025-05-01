package GUI;

import BUS.NguyenLieuBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.NguyenLieuDTO;
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
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class QuanLiNguyenLieuGUI extends RoundedPanel {
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };
    private JTable table;
    private NguyenLieuBUS nguyenLieuBUS;

    public QuanLiNguyenLieuGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        this.setLayout(new BorderLayout());
        nguyenLieuBUS = new NguyenLieuBUS();

        // Title
        JLabel title = new JLabel("Quản Lý Nguyên Liệu", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        this.add(title, BorderLayout.NORTH);

        // Add spacing between title and content
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(0, 15));
        gapPanel.setOpaque(true);
        gapPanel.setBackground(Color.decode("#F5ECE0"));
        this.add(gapPanel, BorderLayout.CENTER);

        // Panel to hold table content
        RoundedPanel nvtablePanel = new RoundedPanel(50, 50, Color.WHITE);
        nvtablePanel.setLayout(new BorderLayout());
        nvtablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nvtablePanel.setPreferredSize(new Dimension(0, 400));

        // Control panel for search field and buttons
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search field
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập tên nguyên liệu để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(Color.BLUE);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                nguyenLieuBUS.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                nguyenLieuBUS.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                nguyenLieuBUS.searchTableData(tableModel, searchField.getText());
            }
        });

        // Create an icon label
        JLabel searchIcon = new JLabel();
        ImageIcon search = new ImageIcon("Resources\\Image\\MagnifyingGlass.png");
        Image scaledIcon = search.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchIcon.setIcon(new ImageIcon(scaledIcon));

        // Add search field and icon to a panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setOpaque(true);
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);
        controlPanel.add(searchPanel, BorderLayout.WEST);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon =Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);

        // "Edit Employee" button
        MyButton editEmployeeButton = new MyButton("Sửa nguyên liệu", editIcon);
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
        editEmployeeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nguyên liệu để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            editEmployeeButton.setEnabled(false);
            FormSuaNguyenLieu(editEmployeeButton, selectedRow);
        });

        // "Add Employee" button
        MyButton addIngredientButton = new MyButton("Thêm nguyên liệu", addIcon);
        addIngredientButton.setPreferredSize(new Dimension(180, 40));
        addIngredientButton.setBackground(Color.decode("#EC5228"));
        addIngredientButton.setForeground(Color.WHITE);
        addIngredientButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addIngredientButton.setFocusPainted(false);
        addIngredientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addIngredientButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addIngredientButton.setBackground(Color.decode("#EC5228"));
            }
        });
        addIngredientButton.addActionListener(e -> {
            addIngredientButton.setEnabled(false);
            FormThemNguyenLieu(addIngredientButton);
        });

        // "Delete Employee" button
        MyButton deleteIngredientButton = new MyButton("Xóa nguyên liệu", deleteIcon);
        deleteIngredientButton.setPreferredSize(new Dimension(180, 40));
        deleteIngredientButton.setBackground(Color.decode("#EC5228"));
        deleteIngredientButton.setForeground(Color.WHITE);
        deleteIngredientButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteIngredientButton.setFocusPainted(false);
        deleteIngredientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteIngredientButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteIngredientButton.setBackground(Color.decode("#EC5228"));
            }
        });
        deleteIngredientButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nguyên liệu để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nguyên liệu này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maNguyenLieu = tableModel.getValueAt(selectedRow, 0).toString();
            String success = nguyenLieuBUS.deleteNguyenLieu(maNguyenLieu);
            if(success == "") {
                nguyenLieuBUS.refreshTableData(tableModel);
                JOptionPane.showMessageDialog(null, "Xóa nguyên liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, success, "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(addIngredientButton);
        buttonPanel.add(deleteIngredientButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        nvtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Table content
        tableModel.setColumnIdentifiers(new Object[]{
                "Mã nguyên liệu", "Tên nguyên liệu", "Loại nguyên liệu", "Ngày nhập", "Ngày hết hạn", "Số lượng", "Đơn vị đo"
        });

        // Load data from database
        nguyenLieuBUS.refreshTableData(tableModel);

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFocusable(false);

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
    }

    public void FormThemNguyenLieu(JButton addEmployeeButton) {
        JFrame formThemNL = new JFrame("Thêm Nguyên Liệu");
        formThemNL.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemNL.setSize(800, 600);
        formThemNL.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel themNLHeader = new RoundedPanel(30, 30, Color.WHITE);
        themNLHeader.setLayout(new BorderLayout());
        JLabel themNLTitle = new JLabel("Thêm Nguyên Liệu", SwingConstants.CENTER);
        themNLTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themNLTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themNLHeader.add(themNLTitle, BorderLayout.CENTER);
        formThemNL.add(themNLHeader, BorderLayout.NORTH);

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // Center panel for form inputs
        RoundedPanel themNLCenter = new RoundedPanel(30, 30, Color.WHITE);
        themNLCenter.setLayout(new GridLayout(7, 3, 10, 10));
        themNLCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input Fields
        // Mã nguyên liệu
        JLabel maNLLabel = new JLabel("Mã nguyên liệu:");
        JTextField maNLField = new JTextField(nguyenLieuBUS.generateMaNguyenLieu());
        maNLField.setEditable(false);
        maNLField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel();
        emptyLabel1.setPreferredSize(new Dimension(60, 60));

        // Tên nguyên liệu
        JLabel tenNLLabel = new JLabel("Tên nguyên liệu:");
        JTextField tenNLField = new JTextField();
        JLabel ingredientNameErrorLabel = new JLabel();
        ingredientNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        ingredientNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        ingredientNameErrorLabel.setForeground(Color.RED);
        ingredientNameErrorLabel.setVisible(false);
        ingredientNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Loại nguyên liệu
        JLabel loaiNLLabel = new JLabel("Loại nguyên liệu:");
        JComboBox<String> loaiNLBox = new JComboBox<>(new String[]{"Thực phẩm tươi sống", "Đồ uống", "Nước sốt"});
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setPreferredSize(new Dimension(60, 60));

        // Ngày nhập
        JLabel ngayNhapLabel = new JLabel("Ngày nhập:");
        JDateChooser ngayNhapField = new JDateChooser();
        ngayNhapField.setDateFormatString("yyyy-MM-dd");
        ((JTextField) ngayNhapField.getDateEditor().getUiComponent()).setEditable(false);
        JLabel importDateErrorLabel = new JLabel();
        importDateErrorLabel.setPreferredSize(new Dimension(60, 60));
        importDateErrorLabel.setIcon(new ImageIcon(scaledImage));
        importDateErrorLabel.setForeground(Color.RED);
        importDateErrorLabel.setVisible(false);
        importDateErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Ngày hết hạn
        JLabel ngayHetHanLabel = new JLabel("Ngày hết hạn:");
        JDateChooser ngayHetHanField = new JDateChooser();
        ngayHetHanField.setDateFormatString("yyyy-MM-dd");
        ((JTextField) ngayHetHanField.getDateEditor().getUiComponent()).setEditable(false);
        JLabel expDateErrorLabel = new JLabel();
        expDateErrorLabel.setPreferredSize(new Dimension(60, 60));
        expDateErrorLabel.setIcon(new ImageIcon(scaledImage));
        expDateErrorLabel.setForeground(Color.RED);
        expDateErrorLabel.setVisible(false);
        expDateErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Đơn vị đo
        JLabel donViDoLabel = new JLabel("Đơn vị đo:");
        JComboBox<String> donViDoBox = new JComboBox<>(new String[]{"gram", "quả", "kg", "thùng", "chai"});
        JLabel emptyLabel3 = new JLabel();
        emptyLabel3.setPreferredSize(new Dimension());

        // Giá nguyên liệu
        JLabel giaLabel = new JLabel("Giá nguyên liệu");
        JTextField giaField = new JTextField();
        JLabel ingredientPriceErrorLabel = new JLabel();
        ingredientPriceErrorLabel.setPreferredSize(new Dimension(60, 60));
        ingredientPriceErrorLabel.setIcon(new ImageIcon(scaledImage));
        ingredientPriceErrorLabel.setForeground(Color.RED);
        ingredientPriceErrorLabel.setVisible(false);
        ingredientPriceErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add components to Center Panel
        themNLCenter.add(maNLLabel);
        themNLCenter.add(maNLField);
        themNLCenter.add(emptyLabel1);

        themNLCenter.add(tenNLLabel);
        themNLCenter.add(tenNLField);
        themNLCenter.add(ingredientNameErrorLabel);

        themNLCenter.add(loaiNLLabel);
        themNLCenter.add(loaiNLBox);
        themNLCenter.add(emptyLabel2);

        themNLCenter.add(ngayNhapLabel);
        themNLCenter.add(ngayNhapField);
        themNLCenter.add(importDateErrorLabel);

        themNLCenter.add(ngayHetHanLabel);
        themNLCenter.add(ngayHetHanField);
        themNLCenter.add(expDateErrorLabel);

        themNLCenter.add(donViDoLabel);
        themNLCenter.add(donViDoBox);
        themNLCenter.add(emptyLabel3);

        themNLCenter.add(giaLabel);
        themNLCenter.add(giaField);
        themNLCenter.add(ingredientPriceErrorLabel);

        formThemNL.add(themNLCenter, BorderLayout.CENTER);

        // Footer panel with Save button
        RoundedPanel themNLFooter = new RoundedPanel(30, 30, Color.WHITE);
        themNLFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            ingredientNameErrorLabel.setVisible(false);
            importDateErrorLabel.setVisible(false);
            expDateErrorLabel.setVisible(false);
            ingredientPriceErrorLabel.setVisible(false);

            boolean isValid = true;

            // Validate tên nguyên liệu
            String ingredientName = tenNLField.getText().trim();
            if(ingredientName.isEmpty()) {
                isValid = false;
                ingredientNameErrorLabel.setToolTipText("Tên nguyên liệu không được trống.");
                ingredientNameErrorLabel.setVisible(true);
            }

            // Validate ngày nhập nguyên liệu
            Date importDate = ngayNhapField.getDate();
            List<String> importDateErrors = new ArrayList<>();
            if(importDate == null) {
                importDateErrors.add("Ngày nhập không được để trống.");
            }                
            if(!importDateErrors.isEmpty()) {
                isValid = false;
                StringBuilder importDateToolTip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: importDateErrors) {
                    importDateToolTip.append("<li>").append(err).append("</li>");
                }
                importDateToolTip.append("</ul></html>");
                importDateErrorLabel.setToolTipText(importDateToolTip.toString());
                importDateErrorLabel.setVisible(true);
            }

            // Validate ngày hết hạn
            Date expDate = ngayHetHanField.getDate();
            List<String> expDateErrors = new ArrayList<>();
            if(expDate == null) {
                expDateErrors.add("Ngày hết hạn không được để trống.");
            }
            if(!expDateErrors.isEmpty()) {
                isValid = false;
                StringBuilder expDateToolTip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: expDateErrors) {
                    expDateToolTip.append("<li>").append(err).append("</li>");
                }
                expDateToolTip.append("</ul></html>");
                expDateErrorLabel.setToolTipText(expDateToolTip.toString());
                expDateErrorLabel.setVisible(true);
            }

            // Vailidate ngày hết hạn phải lớn hơn ngày nhập nguyên liệu
            importDate = ngayNhapField.getDate();
            expDate = ngayHetHanField.getDate();
            if(importDateErrors.isEmpty() && expDateErrors.isEmpty()) {
                if(importDate.after(expDate)) {
                    isValid = false;
                    importDateErrorLabel.setToolTipText("Ngày nhập phải nhỏ hơn ngày hết hạn.");
                    importDateErrorLabel.setVisible(true);
                }
            }

            // Validate giá nguyên liệu
            String ingredientPrice = giaField.getText().trim();
            List<String> ingredientPriceErrors = new ArrayList<>();
            if(ingredientPrice.isEmpty()) {
                ingredientPriceErrors.add("Giá nguyên liệu không được để trống.");
            } else if(!ingredientPrice.matches("\\d+")) {
                ingredientPriceErrors.add("Giá nguyên liệu phải là số");
            } else if(Integer.parseInt(ingredientPrice) <= 0) {
                ingredientPriceErrors.add("Giá nguyên liệu phải lớn hơn 0");
            }
            if(!ingredientPriceErrors.isEmpty()) {
                isValid = false;
                StringBuilder ingredientPriceToolTip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: ingredientPriceErrors) {
                    ingredientPriceToolTip.append("<li>").append(err).append("</li>");
                }
                ingredientPriceToolTip.append("</ul></html>");
                ingredientPriceErrorLabel.setToolTipText(ingredientPriceToolTip.toString());
                ingredientPriceErrorLabel.setVisible(true);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if(isValid) {
                NguyenLieuDTO nl = new NguyenLieuDTO();
                nl.setMaNguyenLieu(maNLField.getText());
                nl.setTenNguyenLieu(tenNLField.getText());
                nl.setLoaiNguyenLieu(loaiNLBox.getSelectedItem().toString());
                nl.setNgayNhap(sdf.format(ngayNhapField.getDate()));
                nl.setNgayHetHan(sdf.format(ngayHetHanField.getDate()));
                nl.setSoLuong(0.0);
                nl.setDonViDo(donViDoBox.getSelectedItem().toString());
                nl.setGia(Double.parseDouble(giaField.getText()));

                String success = nguyenLieuBUS.addNguyenLieu(nl);
                if(success == "") {
                    nguyenLieuBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemNL, "Thêm nguyên liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    formThemNL.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, success, "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        themNLFooter.add(saveButton);
        formThemNL.add(themNLFooter, BorderLayout.SOUTH);

        formThemNL.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addEmployeeButton.setEnabled(true);
            }
        });

        formThemNL.setVisible(true);
    }

    public void FormSuaNguyenLieu(JButton editEmployeeButton, int selectedRow) {
        try {
            JFrame formSuaNL = new JFrame("Sửa Nguyên Liệu");
            formSuaNL.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            formSuaNL.setSize(800, 600);
            formSuaNL.setLayout(new BorderLayout());

            // Header panel
            RoundedPanel suaNLHeader = new RoundedPanel(30, 30, Color.WHITE);
            suaNLHeader.setLayout(new BorderLayout());
            JLabel suaNLTitle = new JLabel("Sửa Nguyên Liệu", SwingConstants.CENTER);
            suaNLTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
            suaNLTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            suaNLHeader.add(suaNLTitle, BorderLayout.CENTER);
            formSuaNL.add(suaNLHeader, BorderLayout.NORTH);

            //Tạo icon warning dấu chấm thang
            ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

            // Center panel for form inputs
            RoundedPanel suaNLCenter = new RoundedPanel(30, 30, Color.WHITE);
            suaNLCenter.setLayout(new GridLayout(7, 3, 10, 10));
            suaNLCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Lấy dữ liệu từ hàng được chọn
            String maNguyenLieu = tableModel.getValueAt(selectedRow, 0).toString();
            NguyenLieuDTO empData = nguyenLieuBUS.getNguyenLieuById(maNguyenLieu);
            if (empData == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nguyên liệu cần chỉnh sửa!", "Error", JOptionPane.ERROR_MESSAGE);
                editEmployeeButton.setEnabled(true);
                return;
            }
            
            String tenNguyenLieu = empData.getTenNguyenLieu();
            String loaiNguyenLieu = empData.getLoaiNguyenLieu();

            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

            String ngayNhap = empData.getNgayNhap();
            String ngayHetHan = empData.getNgayHetHan();
            Date formattedNgayNhap = dbFormat.parse(ngayNhap);
            Date formattedNgayHetHan = dbFormat.parse(ngayHetHan);

            String donViDo = empData.getDonViDo();
            int gia = (int) Math.round(empData.getGia());
            Double soLuong = empData.getSoLuong();

            // Input Fields
            // Mã nguyên liệu
            JLabel maNLLabel = new JLabel("Mã nguyên liệu:");
            JTextField maNLField = new JTextField(maNguyenLieu);
            maNLField.setEditable(false);
            maNLField.setBackground(Color.LIGHT_GRAY);
            JLabel emptyLabel1 = new JLabel();
            emptyLabel1.setPreferredSize(new Dimension(60, 60));


            // Tên nguyên liệu
            JLabel tenNLLabel = new JLabel("Tên nguyên liệu:");
            JTextField tenNLField = new JTextField(tenNguyenLieu);
            JLabel ingredientNameErrorLabel = new JLabel();
            ingredientNameErrorLabel.setPreferredSize(new Dimension(60, 60));
            ingredientNameErrorLabel.setIcon(new ImageIcon(scaledImage));
            ingredientNameErrorLabel.setForeground(Color.RED);
            ingredientNameErrorLabel.setVisible(false);
            ingredientNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Loại nguyên liệu
            JLabel loaiNLLabel = new JLabel("Loại nguyên liệu:");
            JComboBox<String> loaiNLBox = new JComboBox<>(new String[]{"Thực phẩm tươi sống", "Đồ uống", "Nước sốt"});
            loaiNLBox.setSelectedItem(loaiNguyenLieu);
            JLabel emptyLabel2 = new JLabel();
            emptyLabel2.setPreferredSize(new Dimension(60, 60));

            // Ngày nhập
            JLabel ngayNhapLabel = new JLabel("Ngày nhập:");
            JDateChooser ngayNhapField = new JDateChooser();
            ngayNhapField.setDateFormatString("yyyy-MM-dd");
            ngayNhapField.setDate(formattedNgayNhap);
            ((JTextField) ngayNhapField.getDateEditor().getUiComponent()).setEditable(false);
            JLabel importDateErrorLabel = new JLabel();
            importDateErrorLabel.setPreferredSize(new Dimension(60, 60));
            importDateErrorLabel.setIcon(new ImageIcon(scaledImage));
            importDateErrorLabel.setForeground(Color.RED);
            importDateErrorLabel.setVisible(false);
            importDateErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Ngày hết hạn
            JLabel ngayHetHanLabel = new JLabel("Ngày hết hạn:");
            JDateChooser ngayHetHanField = new JDateChooser();
            ngayHetHanField.setDateFormatString("yyyy-MM-dd");
            ngayHetHanField.setDate(formattedNgayHetHan);
            ((JTextField) ngayHetHanField.getDateEditor().getUiComponent()).setEditable(false);
            JLabel expDateErrorLabel = new JLabel();
            expDateErrorLabel.setPreferredSize(new Dimension(60, 60));
            expDateErrorLabel.setIcon(new ImageIcon(scaledImage));
            expDateErrorLabel.setForeground(Color.RED);
            expDateErrorLabel.setVisible(false);
            expDateErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Đơn vị đo
            JLabel donViDoLabel = new JLabel("Đơn vị đo:");
            JComboBox<String> donViDoBox = new JComboBox<>(new String[]{"gram", "quả", "kg", "thùng", "chai"});
            donViDoBox.setSelectedItem(donViDo);
            JLabel emptyLabel3 = new JLabel();
            emptyLabel3.setPreferredSize(new Dimension(60, 60));

            // Giá nguyên liệu
            JLabel giaLabel = new JLabel("Giá nguyên liệu:");
            JTextField giaField = new JTextField(String.valueOf(gia));
            JLabel ingredientPriceErrorLabel = new JLabel();
            ingredientPriceErrorLabel.setPreferredSize(new Dimension(60, 60));
            ingredientPriceErrorLabel.setIcon(new ImageIcon(scaledImage));
            ingredientPriceErrorLabel.setForeground(Color.RED);
            ingredientPriceErrorLabel.setVisible(false);
            ingredientPriceErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Add components to Center Panel
            suaNLCenter.add(maNLLabel);
            suaNLCenter.add(maNLField);
            suaNLCenter.add(emptyLabel1);

            suaNLCenter.add(tenNLLabel);
            suaNLCenter.add(tenNLField);
            suaNLCenter.add(ingredientNameErrorLabel);

            suaNLCenter.add(loaiNLLabel);
            suaNLCenter.add(loaiNLBox);
            suaNLCenter.add(emptyLabel2);

            suaNLCenter.add(ngayNhapLabel);
            suaNLCenter.add(ngayNhapField);
            suaNLCenter.add(importDateErrorLabel);

            suaNLCenter.add(ngayHetHanLabel);
            suaNLCenter.add(ngayHetHanField);
            suaNLCenter.add(expDateErrorLabel);

            suaNLCenter.add(donViDoLabel);
            suaNLCenter.add(donViDoBox);
            suaNLCenter.add(emptyLabel3);

            suaNLCenter.add(giaLabel);
            suaNLCenter.add(giaField);
            suaNLCenter.add(ingredientPriceErrorLabel);
            formSuaNL.add(suaNLCenter, BorderLayout.CENTER);

            // Footer panel with Save button
            RoundedPanel suaNLFooter = new RoundedPanel(30, 30, Color.WHITE);
            suaNLFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton saveButton = new JButton("Lưu");
            saveButton.setPreferredSize(new Dimension(100, 40));
            saveButton.setBackground(Color.decode("#EC5228"));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            saveButton.addActionListener(e -> {
                try {
                    ingredientNameErrorLabel.setVisible(false);
                    importDateErrorLabel.setVisible(false);
                    expDateErrorLabel.setVisible(false);
                    ingredientPriceErrorLabel.setVisible(false);

                    boolean isValid = true;

                    // Validate tên nguyên liệu
                    String ingredientName = tenNLField.getText().trim();
                    if(ingredientName.isEmpty()) {
                        isValid = false;
                        ingredientNameErrorLabel.setToolTipText("Tên nguyên liệu không được để trống.");
                        ingredientNameErrorLabel.setVisible(true);
                    }

                    // Validate ngày nhập
                    Date importDate = ngayNhapField.getDate();
                    List<String> importDateErrors = new ArrayList<>();
                    if(importDate == null) {
                        importDateErrors.add("Ngày nhập nguyên liệu không được để trống.");
                    }
                    if(!importDateErrors.isEmpty()) {
                        isValid = false;
                        StringBuilder importDateToolTip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                        for(String err: importDateErrors) {
                            importDateToolTip.append("<li>").append(err).append("</li>");
                        }
                        importDateToolTip.append("</ul></html>");
                        importDateErrorLabel.setToolTipText(importDateToolTip.toString());
                        importDateErrorLabel.setVisible(true);
                    }

                    // Validate ngày hết hạn
                    Date expDate = ngayHetHanField.getDate();
                    List<String> expDateErrors = new ArrayList<>();
                    if(expDate == null) {
                        expDateErrors.add("Ngày hết hạn không được trống.");
                    }
                    if(!expDateErrors.isEmpty()) {
                        isValid = false;
                        StringBuilder expDateToolTip = new StringBuilder("<html><ul style='margin:0;padding-left:16px'>");
                        for(String err: expDateErrors) {
                            expDateToolTip.append("<li>").append(err).append("</li>");
                        }
                        expDateToolTip.append("</ul></html>");
                        expDateErrorLabel.setToolTipText(expDateToolTip.toString());
                        expDateErrorLabel.setVisible(true);
                    }

                    // Validate ngày nhập phải nhỏ hơn ngày hết hạn
                    if(importDateErrors.isEmpty() && expDateErrors.isEmpty()) {
                        if(importDate.after(expDate)) {
                            isValid = false;
                            importDateErrorLabel.setToolTipText("Ngày nhập không được lớn hơn ngày hết hạn");
                            importDateErrorLabel.setVisible(true);
                        }
                    }

                    // Validate giá nguyên liệu
                    String ingredientPrice = giaField.getText().trim();
                    List<String> ingredientPriceErrors = new ArrayList<>();
                    if(ingredientPrice.isEmpty()) {
                        ingredientPriceErrors.add("Giá nguyên liệu không được để trống.");
                    } else if(!ingredientPrice.matches("\\d+")) {
                        ingredientPriceErrors.add("Giá nguyên liệu phải là số");
                    } else if(Integer.parseInt(ingredientPrice) <= 0) {
                        ingredientPriceErrors.add("Giá nguyên liệu phải lớn hơn 0");
                    }
                    if(!ingredientPriceErrors.isEmpty()) {
                        isValid = false;
                        StringBuilder ingredientPriceToolTip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                        for(String err: ingredientPriceErrors) {
                            ingredientPriceToolTip.append("<li>").append(err).append("</li>");
                        }
                        ingredientPriceToolTip.append("</ul></html>");
                        ingredientPriceErrorLabel.setToolTipText(ingredientPriceToolTip.toString());
                        ingredientPriceErrorLabel.setVisible(true);
                    }

                    if(isValid) {
                        NguyenLieuDTO nl = new NguyenLieuDTO();
                        nl.setMaNguyenLieu(maNLField.getText());
                        nl.setTenNguyenLieu(tenNLField.getText());
                        nl.setLoaiNguyenLieu(loaiNLBox.getSelectedItem().toString());
                        nl.setNgayNhap(dbFormat.format(ngayNhapField.getDate()));
                        nl.setNgayHetHan(dbFormat.format(ngayHetHanField.getDate()));
                        nl.setSoLuong(soLuong);
                        nl.setDonViDo(donViDoBox.getSelectedItem().toString());
                        nl.setGia(Double.parseDouble(giaField.getText()));

                        String success = nguyenLieuBUS.updateNguyenLieu(nl);
                        if(success == "") {
                            nguyenLieuBUS.refreshTableData(tableModel);
                            JOptionPane.showMessageDialog(formSuaNL, "Cập nhật nguyên liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            formSuaNL.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, success, "Thông báo", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(formSuaNL, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
            suaNLFooter.add(saveButton);
            formSuaNL.add(suaNLFooter, BorderLayout.SOUTH);

            formSuaNL.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    editEmployeeButton.setEnabled(true);
                }
            });

            formSuaNL.setVisible(true);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}