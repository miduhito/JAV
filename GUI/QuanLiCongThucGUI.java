package GUI;

import BUS.CongThucBUS;
import Custom.MyButton;
import Custom.Utilities;
import DTO.CongThucDTO;
import DTO.ChiTietCongThucDTO;
import DTO.NguyenLieuDTO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuanLiCongThucGUI extends RoundedPanel {
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };
    private JTable table;
    private CongThucBUS congThucBUS;

    public QuanLiCongThucGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        this.setLayout(new BorderLayout());

        try {
            congThucBUS = new CongThucBUS();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khởi tạo CongThucBUS: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Title
        JLabel title = new JLabel("Quản Lý Công Thức", SwingConstants.LEFT);
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
        searchField.setToolTipText("Nhập tên công thức để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(Color.BLUE);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                congThucBUS.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                congThucBUS.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                congThucBUS.searchTableData(tableModel, searchField.getText());
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
        MyButton editEmployeeButton = new MyButton("Sửa công thức", editIcon);
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
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một công thức để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            editEmployeeButton.setEnabled(false);
            try {
                FormSuaCongThuc(editEmployeeButton, selectedRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi mở form sửa công thức: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                editEmployeeButton.setEnabled(true);
            }
        });

        // "Add Employee" button
        MyButton addIngredientButton = new MyButton("Thêm công thức", addIcon);
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
            try {
                FormThemCongThuc(addIngredientButton);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi mở form thêm công thức: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                addIngredientButton.setEnabled(true);
            }
        });

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(addIngredientButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        // "Delete Recipe" button
        MyButton deleteRecipeButton = new MyButton("Xóa công thức", deleteIcon);
        deleteRecipeButton.setPreferredSize(new Dimension(180, 40));
        deleteRecipeButton.setBackground(Color.decode("#EC5228"));
        deleteRecipeButton.setForeground(Color.WHITE);
        deleteRecipeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteRecipeButton.setFocusPainted(false);
        deleteRecipeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteRecipeButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteRecipeButton.setBackground(Color.decode("#EC5228"));
            }
        });
        deleteRecipeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một công thức để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa công thức này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maCongThuc = tableModel.getValueAt(selectedRow, 0).toString();
            try {
                congThucBUS.deleteCongThuc(maCongThuc);
                congThucBUS.refreshTableData(tableModel);
                JOptionPane.showMessageDialog(null, "Xóa công thức thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa công thức: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm nút "Xóa" vào buttonPanel
        buttonPanel.add(deleteRecipeButton);

        nvtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Table content
        tableModel.setColumnIdentifiers(new Object[]{
                "Mã công thức", "Tên công thức", "Mô tả"
        });

        // Load data from database
        congThucBUS.refreshTableData(tableModel);

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

    public void FormThemCongThuc(JButton addEmployeeButton) throws SQLException {
        JFrame formThemCT = new JFrame("Thêm Công Thức");
        formThemCT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemCT.setSize(800, 600);
        formThemCT.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel themCTHeader = new RoundedPanel(30, 30, Color.WHITE);
        themCTHeader.setLayout(new BorderLayout());
        JLabel themCTTitle = new JLabel("Thêm Công Thức", SwingConstants.CENTER);
        themCTTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themCTTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themCTHeader.add(themCTTitle, BorderLayout.CENTER);
        formThemCT.add(themCTHeader, BorderLayout.NORTH);

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // Center panel for form inputs
        RoundedPanel themCTCenter = new RoundedPanel(30, 30, Color.WHITE);
        themCTCenter.setLayout(new BorderLayout());
        themCTCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Phần thông tin công thức
        JPanel recipeInfoPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        JLabel maCTLabel = new JLabel("Mã công thức:");
        JTextField maCTField = new JTextField(congThucBUS.generateMaCongThuc());
        maCTField.setEditable(false);
        maCTField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel();
        emptyLabel1.setPreferredSize(new Dimension(60, 60));

        JLabel tenCTLabel = new JLabel("Tên công thức:");
        JTextField tenCTField = new JTextField();
        JLabel recipeNameErrorLabel = new JLabel();
        recipeNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        recipeNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        recipeNameErrorLabel.setForeground(Color.RED);
        recipeNameErrorLabel.setVisible(false);
        recipeNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel moTaLabel = new JLabel("Mô tả:");
        JTextField moTaField = new JTextField();
        JLabel recipeDescripeErrorLabel = new JLabel();
        recipeDescripeErrorLabel.setPreferredSize(new Dimension(60, 60));
        recipeDescripeErrorLabel.setIcon(new ImageIcon(scaledImage));
        recipeDescripeErrorLabel.setForeground(Color.RED);
        recipeDescripeErrorLabel.setVisible(false);
        recipeDescripeErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        recipeInfoPanel.add(maCTLabel);
        recipeInfoPanel.add(maCTField);
        recipeInfoPanel.add(emptyLabel1);

        recipeInfoPanel.add(tenCTLabel);
        recipeInfoPanel.add(tenCTField);
        recipeInfoPanel.add(recipeNameErrorLabel);

        recipeInfoPanel.add(moTaLabel);
        recipeInfoPanel.add(moTaField);
        recipeInfoPanel.add(recipeDescripeErrorLabel);

        themCTCenter.add(recipeInfoPanel, BorderLayout.NORTH);

        // Phần danh sách nguyên liệu
        JPanel ingredientPanel = new JPanel(new BorderLayout());
        DefaultTableModel ingredientTableModel = new DefaultTableModel();
        ingredientTableModel.setColumnIdentifiers(new Object[]{"Mã NL", "Số lượng", "ĐV đo"});
        JTable ingredientTable = new JTable(ingredientTableModel);
        ingredientTable.setRowHeight(25);
        ingredientTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientTable);
        ingredientPanel.add(ingredientScrollPane, BorderLayout.CENTER);

        // Form thêm nguyên liệu
        JPanel addIngredientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JComboBox<String> ingredientCombo = new JComboBox<>();
        JTextField qtyField = new JTextField(5);
        JLabel unitLabel = new JLabel("gram");
        JButton addIngredientBtn = new JButton("Thêm");
        JButton removeIngredientBtn = new JButton("Xóa");

        // Tải danh sách nguyên liệu từ database
        loadIngredients(ingredientCombo);

        // Cập nhật đơn vị đo khi chọn nguyên liệu
        ingredientCombo.addActionListener(e -> {
            String selected = (String) ingredientCombo.getSelectedItem();
            if (selected != null && !selected.trim().isEmpty()) {
                try {
                    String maNL = selected.split(" - ")[0];
                    String unit = congThucBUS.getDonViDo(maNL);
                    unitLabel.setText(unit);
                } catch (SQLException ex) {
                    unitLabel.setText("gram");
                }
            } else {
                unitLabel.setText("gram");
            }
        });

        // Sự kiện nút thêm nguyên liệu vào công thức
        addIngredientBtn.addActionListener(e -> {
            String selected = (String) ingredientCombo.getSelectedItem();
            String qtyText = qtyField.getText();
            if (selected != null && !qtyText.isEmpty()) {
                try {
                    Double qty = Double.parseDouble(qtyText);
                    String[] parts = selected.split(" - ");
                    String unit = unitLabel.getText();
                    ingredientTableModel.addRow(new Object[]{parts[0], qty, unit});
                    qtyField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formThemCT, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Sự kiện nút xóa nguyên liệu khỏi công thức
        removeIngredientBtn.addActionListener(e -> {
            int selectedRow = ingredientTable.getSelectedRow();
            if (selectedRow != -1) {
                ingredientTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(formThemCT, "Vui lòng chọn một nguyên liệu để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        addIngredientPanel.add(new JLabel("Nguyên liệu:"));
        addIngredientPanel.add(ingredientCombo);
        addIngredientPanel.add(new JLabel("Số lượng:"));
        addIngredientPanel.add(qtyField);
        addIngredientPanel.add(unitLabel);
        addIngredientPanel.add(addIngredientBtn);
        addIngredientPanel.add(removeIngredientBtn);

        ingredientPanel.add(addIngredientPanel, BorderLayout.SOUTH);
        themCTCenter.add(ingredientPanel, BorderLayout.CENTER);

        formThemCT.add(themCTCenter, BorderLayout.CENTER);

        // Footer panel with Save button
        RoundedPanel themCTFooter = new RoundedPanel(30, 30, Color.WHITE);
        themCTFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            try {
                recipeNameErrorLabel.setVisible(false);
                recipeDescripeErrorLabel.setVisible(false);

                Boolean isValid = true;

                // Validate tên công thức
                String recipeName = tenCTField.getText().trim();
                if(recipeName.isEmpty()) {
                    isValid = false;
                    recipeNameErrorLabel.setToolTipText("Tên công thức không được trống.");
                    recipeNameErrorLabel.setVisible(true);
                }

                // Validate mô tả công thức
                String recipeDescripe = moTaField.getText().trim();
                if(recipeDescripe.isEmpty()) {
                    isValid = false;
                    recipeDescripeErrorLabel.setToolTipText("Mô tả công thức không được để trống");
                    recipeDescripeErrorLabel.setVisible(true);
                }

                if(isValid) {
                    CongThucDTO ct = new CongThucDTO();
                    ct.setMaCongThuc(maCTField.getText());
                    ct.setTenCongThuc(tenCTField.getText());
                    ct.setMoTa(moTaField.getText());

                    List<ChiTietCongThucDTO> chiTietList = new ArrayList<>();
                    for (int i = 0; i < ingredientTableModel.getRowCount(); i++) {
                        ChiTietCongThucDTO chiTiet = new ChiTietCongThucDTO();
                        chiTiet.setMaNguyenLieu((String) ingredientTableModel.getValueAt(i, 0));
                        chiTiet.setSoLuong((Double) ingredientTableModel.getValueAt(i, 1));
                        chiTiet.setDonViTinh((String) ingredientTableModel.getValueAt(i, 2));
                        chiTietList.add(chiTiet);
                    }

                    congThucBUS.addCongThuc(ct, chiTietList);
                    congThucBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemCT, "Thêm công thức thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    formThemCT.dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formThemCT, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(formThemCT, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        themCTFooter.add(saveButton);
        formThemCT.add(themCTFooter, BorderLayout.SOUTH);

        formThemCT.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addEmployeeButton.setEnabled(true);
            }
        });

        formThemCT.setVisible(true);
    }

    public void FormSuaCongThuc(JButton editEmployeeButton, int selectedRow) throws SQLException {
        JFrame formSuaCT = new JFrame("Sửa Công Thức");
        formSuaCT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSuaCT.setSize(800, 600);
        formSuaCT.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel suaCTHeader = new RoundedPanel(30, 30, Color.WHITE);
        suaCTHeader.setLayout(new BorderLayout());
        JLabel suaCTTitle = new JLabel("Sửa Công Thức", SwingConstants.CENTER);
        suaCTTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        suaCTTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        suaCTHeader.add(suaCTTitle, BorderLayout.CENTER);
        formSuaCT.add(suaCTHeader, BorderLayout.NORTH);

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // Center panel for form inputs
        RoundedPanel suaCTCenter = new RoundedPanel(30, 30, Color.WHITE);
        suaCTCenter.setLayout(new BorderLayout());
        suaCTCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Phần thông tin công thức
        JPanel recipeInfoPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        JLabel maCTLabel = new JLabel("Mã công thức:");
        String maCongThuc = tableModel.getValueAt(selectedRow, 0).toString();
        JTextField maCTField = new JTextField(maCongThuc);
        maCTField.setEditable(false);
        maCTField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel();
        emptyLabel1.setPreferredSize(new Dimension(60, 60));

        JLabel tenCTLabel = new JLabel("Tên công thức:");
        JTextField tenCTField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        JLabel recipeNameErrorLabel = new JLabel();
        recipeNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        recipeNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        recipeNameErrorLabel.setForeground(Color.RED);
        recipeNameErrorLabel.setVisible(false);
        recipeNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel moTaLabel = new JLabel("Mô tả:");
        JTextField moTaField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        JLabel recipeDescripeErrorLabel = new JLabel();
        recipeDescripeErrorLabel.setPreferredSize(new Dimension(60, 60));
        recipeDescripeErrorLabel.setIcon(new ImageIcon(scaledImage));
        recipeDescripeErrorLabel.setForeground(Color.RED);
        recipeDescripeErrorLabel.setVisible(false);
        recipeDescripeErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        recipeInfoPanel.add(maCTLabel);
        recipeInfoPanel.add(maCTField);
        recipeInfoPanel.add(emptyLabel1);

        recipeInfoPanel.add(tenCTLabel);
        recipeInfoPanel.add(tenCTField);
        recipeInfoPanel.add(recipeNameErrorLabel);

        recipeInfoPanel.add(moTaLabel);
        recipeInfoPanel.add(moTaField);
        recipeInfoPanel.add(recipeDescripeErrorLabel);

        suaCTCenter.add(recipeInfoPanel, BorderLayout.NORTH);

        // Phần danh sách nguyên liệu
        JPanel ingredientPanel = new JPanel(new BorderLayout());
        DefaultTableModel ingredientTableModel = new DefaultTableModel();
        ingredientTableModel.setColumnIdentifiers(new Object[]{"Mã NL", "Số lượng", "ĐV đo"});
        JTable ingredientTable = new JTable(ingredientTableModel);
        ingredientTable.setRowHeight(25);
        ingredientTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientTable);
        ingredientPanel.add(ingredientScrollPane, BorderLayout.CENTER);

        // Tải danh sách nguyên liệu hiện tại của công thức
        List<ChiTietCongThucDTO> chiTietList = congThucBUS.getChiTietCongThuc(maCongThuc);
        for (ChiTietCongThucDTO ct : chiTietList) {
            ingredientTableModel.addRow(new Object[]{
                    ct.getMaNguyenLieu(),
                    ct.getSoLuong(),
                    ct.getDonViTinh()
            });
        }

        // Form thêm nguyên liệu
        JPanel addIngredientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JComboBox<String> ingredientCombo = new JComboBox<>();
        JTextField qtyField = new JTextField(5);
        JLabel unitLabel = new JLabel("gram");
        JButton addIngredientBtn = new JButton("Thêm");
        JButton removeIngredientBtn = new JButton("Xóa");

        // Tải danh sách nguyên liệu từ database
        loadIngredients(ingredientCombo);

        // Cập nhật đơn vị đo khi chọn nguyên liệu
        ingredientCombo.addActionListener(e -> {
            String selected = (String) ingredientCombo.getSelectedItem();
            if (selected != null && !selected.trim().isEmpty()) {
                try {
                    String maNL = selected.split(" - ")[0];
                    String unit = congThucBUS.getDonViDo(maNL);
                    unitLabel.setText(unit);
                } catch (SQLException ex) {
                    unitLabel.setText("gram");
                }
            } else {
                unitLabel.setText("gram");
            }
        });

        addIngredientBtn.addActionListener(e -> {
            String selected = (String) ingredientCombo.getSelectedItem();
            String qtyText = qtyField.getText();
            if (selected != null && !qtyText.isEmpty()) {   
                try {
                    Double qty = Double.parseDouble(qtyText);
                    String[] parts = selected.split(" - ");
                    String unit = unitLabel.getText();
                    ingredientTableModel.addRow(new Object[]{parts[0], qty, unit});
                    qtyField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(formSuaCT, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeIngredientBtn.addActionListener(e -> {
            int selectedIngredientRow = ingredientTable.getSelectedRow();
            if (selectedIngredientRow != -1) {
                ingredientTableModel.removeRow(selectedIngredientRow);
            } else {
                JOptionPane.showMessageDialog(formSuaCT, "Vui lòng chọn một nguyên liệu để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        addIngredientPanel.add(new JLabel("Nguyên liệu:"));
        addIngredientPanel.add(ingredientCombo);
        addIngredientPanel.add(new JLabel("Số lượng:"));
        addIngredientPanel.add(qtyField);
        addIngredientPanel.add(unitLabel);
        addIngredientPanel.add(addIngredientBtn);
        addIngredientPanel.add(removeIngredientBtn);

        ingredientPanel.add(addIngredientPanel, BorderLayout.SOUTH);
        suaCTCenter.add(ingredientPanel, BorderLayout.CENTER);

        formSuaCT.add(suaCTCenter, BorderLayout.CENTER);

        // Footer panel with Save button
        RoundedPanel suaCTFooter = new RoundedPanel(30, 30, Color.WHITE);
        suaCTFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            try {
                recipeNameErrorLabel.setVisible(false);
                recipeDescripeErrorLabel.setVisible(false);

                Boolean isValid = true;

                // Validate tên công thức
                String recipeName = tenCTField.getText().trim();
                if(recipeName.isEmpty()) {
                    isValid = false;
                    recipeNameErrorLabel.setToolTipText("Tên công thức không được trống.");
                    recipeNameErrorLabel.setVisible(true);
                }

                // Validate mô tả công thức
                String recipeDescripe = moTaField.getText().trim();
                if(recipeDescripe.isEmpty()) {
                    isValid = false;
                    recipeDescripeErrorLabel.setToolTipText("Mô tả công thức không được để trống");
                    recipeDescripeErrorLabel.setVisible(true);
                }

                if(isValid) {
                    CongThucDTO ct = new CongThucDTO();
                    ct.setMaCongThuc(maCTField.getText());
                    ct.setTenCongThuc(tenCTField.getText());
                    ct.setMoTa(moTaField.getText());

                    List<ChiTietCongThucDTO> newChiTietList = new ArrayList<>();
                    for (int i = 0; i < ingredientTableModel.getRowCount(); i++) {
                        ChiTietCongThucDTO chiTiet = new ChiTietCongThucDTO();
                        chiTiet.setMaNguyenLieu((String) ingredientTableModel.getValueAt(i, 0));
                        chiTiet.setSoLuong((Double) ingredientTableModel.getValueAt(i, 1));
                        chiTiet.setDonViTinh((String) ingredientTableModel.getValueAt(i, 2));
                        newChiTietList.add(chiTiet);
                    }

                    congThucBUS.updateCongThuc(ct, newChiTietList);
                    congThucBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formSuaCT, "Cập nhật công thức thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    formSuaCT.dispose();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formSuaCT, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(formSuaCT, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        suaCTFooter.add(saveButton);
        formSuaCT.add(suaCTFooter, BorderLayout.SOUTH);

        formSuaCT.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                editEmployeeButton.setEnabled(true);
            }
        });

        formSuaCT.setVisible(true);
    }

    private void loadIngredients(JComboBox<String> comboBox) {
        try {
            List<NguyenLieuDTO> list = congThucBUS.getAllNguyenLieu();
            comboBox.removeAllItems();
            for (NguyenLieuDTO nl : list) {
                String item = nl.getMaNguyenLieu() + " - " + nl.getTenNguyenLieu();
                comboBox.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi tải danh sách nguyên liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}