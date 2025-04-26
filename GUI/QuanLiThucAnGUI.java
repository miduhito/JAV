package GUI; 

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

import DTO.ThucAnDTO;
import BUS.ThucAnBUS;
import Custom.MyButton;
import Custom.Utilities;

public class QuanLiThucAnGUI extends RoundedPanel {
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
        }
    };
    private ThucAnBUS thucAnBUS;
    private JTable table;

    public QuanLiThucAnGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        this.setLayout(new BorderLayout());

        // Khởi tạo ThucAnBUS
        thucAnBUS = new ThucAnBUS();

        // Title
        JLabel title = new JLabel("Quản Lý Thức Ăn", SwingConstants.LEFT);
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
        RoundedPanel tablePanel = new RoundedPanel(50, 50, Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.setPreferredSize(new Dimension(0, 400));

        // Control panel for buttons
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search field
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập tên thức ăn để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(Color.BLUE);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                thucAnBUS.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                thucAnBUS.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                thucAnBUS.searchTableData(tableModel, searchField.getText());
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

        // "Edit Food" button
        MyButton editFoodButton = new MyButton("Sửa thức ăn", editIcon);
        editFoodButton.setPreferredSize(new Dimension(180, 40));
        editFoodButton.setBackground(Color.decode("#EC5228"));
        editFoodButton.setForeground(Color.WHITE);
        editFoodButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editFoodButton.setFocusPainted(false);
        editFoodButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editFoodButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editFoodButton.setBackground(Color.decode("#EC5228"));
            }
        });
        editFoodButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một thức ăn để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            editFoodButton.setEnabled(false);
            try {
                FormSuaThucAn(editFoodButton, selectedRow);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi mở form sửa thức ăn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                editFoodButton.setEnabled(true);
            }
        });

        // "Add Food" button
        MyButton addFoodButton = new MyButton("Thêm Thức Ăn", addIcon);
        addFoodButton.setPreferredSize(new Dimension(180, 40));
        addFoodButton.setBackground(Color.decode("#EC5228"));
        addFoodButton.setForeground(Color.WHITE);
        addFoodButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addFoodButton.setFocusPainted(false);
        addFoodButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addFoodButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addFoodButton.setBackground(Color.decode("#EC5228"));
            }
        });
        addFoodButton.addActionListener(e -> {
            addFoodButton.setEnabled(false);
            try {
                FormThemThucAn(addFoodButton);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi mở form thêm thức ăn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                addFoodButton.setEnabled(true);
            }
        });

        // "Delete Food" button
        MyButton deleteFoodButton = new MyButton("Xóa thức ăn", deleteIcon);
        deleteFoodButton.setPreferredSize(new Dimension(180, 40));
        deleteFoodButton.setBackground(Color.decode("#EC5228"));
        deleteFoodButton.setForeground(Color.WHITE);
        deleteFoodButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteFoodButton.setFocusPainted(false);
        deleteFoodButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteFoodButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deleteFoodButton.setBackground(Color.decode("#EC5228"));
            }
        });
        deleteFoodButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một thức ăn để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa thức ăn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maThucAn = tableModel.getValueAt(selectedRow, 0).toString();
            try {
                thucAnBUS.deleteThucAn(maThucAn);
                thucAnBUS.refreshTableData(tableModel);
                JOptionPane.showMessageDialog(null, "Xóa thức ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa thức ăn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(editFoodButton);
        buttonPanel.add(addFoodButton);
        buttonPanel.add(deleteFoodButton);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        tablePanel.add(controlPanel, BorderLayout.NORTH);

        // Table content
        tableModel.setColumnIdentifiers(new Object[]{
                "Mã Thức Ăn", "Tên Thức Ăn", "Mô Tả", "Loại Món Ăn", "Giá", "Mã Công Thức", "Số Lượng"
        });

        // Load data from database
        thucAnBUS.refreshTableData(tableModel);

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

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.SOUTH);
    }

    private void FormThemThucAn(JButton addFoodButton) throws SQLException {
        JFrame formThemTA = new JFrame("Thêm Thức Ăn");
        formThemTA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemTA.setSize(800, 600);
        formThemTA.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel themTAHeader = new RoundedPanel(30, 30, Color.WHITE);
        themTAHeader.setLayout(new BorderLayout());
        JLabel themTATitle = new JLabel("Thêm Thức Ăn", SwingConstants.CENTER);
        themTATitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themTATitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themTAHeader.add(themTATitle, BorderLayout.CENTER);
        formThemTA.add(themTAHeader, BorderLayout.NORTH);

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // Center panel for form inputs
        RoundedPanel themTACenter = new RoundedPanel(30, 30, Color.WHITE);
        themTACenter.setLayout(new GridLayout(8, 3, 10, 10));
        themTACenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Mã thức ăn
        JLabel maTALabel = new JLabel("Mã thức ăn:");
        JTextField maTAField = new JTextField(thucAnBUS.generateMaThucAn());
        maTAField.setEditable(false);
        maTAField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel();
        emptyLabel1.setPreferredSize(new Dimension(60, 60));

        // Tên thức ăn
        JLabel tenTALabel = new JLabel("Tên thức ăn:");
        JTextField tenTAField = new JTextField();
        JLabel foodNameErrorLabel = new JLabel();
        foodNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodNameErrorLabel.setForeground(Color.RED);
        foodNameErrorLabel.setVisible(false);
        foodNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Mô tả
        JLabel moTaLabel = new JLabel("Mô tả:");
        JTextField moTaField = new JTextField();
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setPreferredSize(new Dimension(60, 60));

        // Loại món ăn
        JLabel loaiMonAnLabel = new JLabel("Loại món ăn:");
        JComboBox<String> loaiMonAnCombo = new JComboBox<>(new String[]{"Món chính", "Món phụ", "Đồ uống"});
        JLabel emptyLabel3 = new JLabel();
        emptyLabel3.setPreferredSize(new Dimension(60, 60));

        // Giá
        JLabel giaLabel = new JLabel("Giá:");
        JTextField giaField = new JTextField();
        JLabel foodPriceErrorLabel = new JLabel();
        foodPriceErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodPriceErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodPriceErrorLabel.setForeground(Color.RED);
        foodPriceErrorLabel.setVisible(false);
        foodPriceErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Chọn công thức
        JLabel congThucLabel = new JLabel("Công thức:");
        JComboBox<String> congThucCombo = new JComboBox<>();
        List<String> congThucList = thucAnBUS.getAllCongThuc();
        for (String congThuc : congThucList) {
            congThucCombo.addItem(congThuc);
        }
        JLabel emptyLabel4 = new JLabel();
        emptyLabel4.setPreferredSize(new Dimension(60, 60));

        // Số lượng
        JLabel soLuongLabel = new JLabel("Số lượng:");
        JTextField soLuongField = new JTextField();
        JLabel foodQuantityErrorLabel = new JLabel();
        foodQuantityErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodQuantityErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodQuantityErrorLabel.setForeground(Color.RED);
        foodQuantityErrorLabel.setVisible(false);
        foodQuantityErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Ảnh thức ăn
        JLabel anhThucAnLabel = new JLabel("Đường dẫn file ảnh:");
        JTextField anhThucAnField = new JTextField(20);
        anhThucAnField.setEditable(false); // Không cho phép nhập thủ công
        JButton browseButton = new JButton("Chọn file");

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                anhThucAnField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JLabel foodImageErrorLabel = new JLabel();
        foodImageErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodImageErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodImageErrorLabel.setForeground(Color.RED);
        foodImageErrorLabel.setVisible(false);
        foodImageErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel chooseFilePanel = new JPanel();
        chooseFilePanel.setLayout(new BorderLayout());
        chooseFilePanel.add(anhThucAnField, BorderLayout.WEST);
        chooseFilePanel.add(browseButton, BorderLayout.EAST);

        themTACenter.add(maTALabel);
        themTACenter.add(maTAField);
        themTACenter.add(emptyLabel1);

        themTACenter.add(tenTALabel);
        themTACenter.add(tenTAField);
        themTACenter.add(foodNameErrorLabel);

        themTACenter.add(moTaLabel);
        themTACenter.add(moTaField);
        themTACenter.add(emptyLabel2);

        themTACenter.add(loaiMonAnLabel);
        themTACenter.add(loaiMonAnCombo);
        themTACenter.add(emptyLabel3);

        themTACenter.add(giaLabel);
        themTACenter.add(giaField);
        themTACenter.add(foodPriceErrorLabel);

        themTACenter.add(congThucLabel);
        themTACenter.add(congThucCombo);
        themTACenter.add(emptyLabel4);

        themTACenter.add(soLuongLabel);
        themTACenter.add(soLuongField);
        themTACenter.add(foodQuantityErrorLabel);

        themTACenter.add(anhThucAnLabel);
        themTACenter.add(chooseFilePanel);
        themTACenter.add(foodImageErrorLabel);

        formThemTA.add(themTACenter, BorderLayout.CENTER);

        // Footer panel with Save button
        RoundedPanel themTAFooter = new RoundedPanel(30, 30, Color.WHITE);
        themTAFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            try {
                // Ẩn thông báo lỗi ban đầu
                foodNameErrorLabel.setVisible(false);
                foodPriceErrorLabel.setVisible(false);
                foodQuantityErrorLabel.setVisible(false);
        
                boolean isValid = true;
        
                // Validate tên thức ăn
                String foodName = tenTAField.getText().trim();
                if (foodName.isEmpty()) {
                    isValid = false;
                    foodNameErrorLabel.setToolTipText("Tên thức ăn không được để trống.");
                    foodNameErrorLabel.setVisible(true);
                }
        
                // Validate giá
                String foodPrice = giaField.getText().trim();
                List<String> foodPriceErrors = new ArrayList<>();
                if (foodPrice.isEmpty()) {
                    foodPriceErrors.add("Giá thức ăn không được để trống.");
                } else if (!foodPrice.matches("\\d+")) {
                    foodPriceErrors.add("Giá thức ăn phải là số.");
                } else {
                    try {
                        if (Integer.parseInt(foodPrice) <= 0) {
                            foodPriceErrors.add("Giá thức ăn phải lớn hơn 0.");
                        }
                    } catch (NumberFormatException ex) {
                        foodPriceErrors.add("Giá thức ăn không hợp lệ.");
                    }
                }
                if (!foodPriceErrors.isEmpty()) {
                    isValid = false;
                    StringBuilder priceTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                    for (String err : foodPriceErrors) {
                        priceTooltip.append("<li>").append(err).append("</li>");
                    }
                    priceTooltip.append("</ul></html>");
                    foodPriceErrorLabel.setToolTipText(priceTooltip.toString());
                    foodPriceErrorLabel.setVisible(true);
                }
        
                // Validate số lượng thức ăn
                String foodQuantity = soLuongField.getText().trim();
                System.out.println("foodQuantity: " + foodQuantity);
                List<String> foodQuantityErrors = new ArrayList<>();
                if (foodQuantity.isEmpty()) {
                    foodQuantityErrors.add("Số lượng thức ăn không được để trống.");
                } else if (!foodQuantity.matches("\\d+")) {
                    foodQuantityErrors.add("Số lượng thức ăn phải là số.");
                } else {
                    try {
                        if (Integer.parseInt(foodQuantity) < 0) {
                            foodQuantityErrors.add("Số lượng thức ăn phải lớn hoặc bằng 0.");
                        }
                    } catch (NumberFormatException ex) {
                        foodQuantityErrors.add("Số lượng thức ăn không hợp lệ.");
                    }
                }
                if (!foodQuantityErrors.isEmpty()) {
                    isValid = false;
                    StringBuilder quantityTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                    for (String err : foodQuantityErrors) {
                        quantityTooltip.append("<li>").append(err).append("</li>");
                    }
                    quantityTooltip.append("</ul></html>");
                    foodQuantityErrorLabel.setToolTipText(quantityTooltip.toString());
                    foodQuantityErrorLabel.setVisible(true);
                }

                // Validate ảnh thức ăn
                String imageResult = anhThucAnField.getText();
                if(imageResult.isEmpty()) {
                    isValid = false;
                    foodImageErrorLabel.setToolTipText("Ảnh thức ăn không được trống");
                    foodImageErrorLabel.setVisible(true);
                }
                // String foodImage = anhThucAnField.getText().trim();
                // if(foodImage.isEmpty()) {
                //     isValid = false;
                //     foodImageErrorLabel.setToolTipText("Ảnh thức ăn không được trống");
                //     foodImageErrorLabel.setVisible(true);
                // }
        
                // Xử lý khi dữ liệu hợp lệ
                if (isValid) {
                    ThucAnDTO ta = new ThucAnDTO(); 
                    ta.setMaThucAn(maTAField.getText().trim());
                    ta.setTenThucAn(foodName);
                    ta.setMoTa(moTaField.getText().trim());
                    ta.setLoaiMonAn((String) loaiMonAnCombo.getSelectedItem());
                    ta.setGia(Double.parseDouble(foodPrice));
                    String selectedCongThuc = (String) congThucCombo.getSelectedItem();
                    ta.setMaCongThuc(selectedCongThuc.split(" - ")[0]);
                    ta.setSoLuong(Integer.parseInt(foodQuantity));
                    ta.setAnhThucAn(anhThucAnField.getText());
        
                    thucAnBUS.addThucAn(ta);
                    thucAnBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemTA, "Thêm thức ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    formThemTA.dispose();
                }
            } catch (Exception ex) {
                String errorMsg = "Lỗi cơ sở dữ liệu: " + ex.getMessage();
                JOptionPane.showMessageDialog(formThemTA, errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        themTAFooter.add(saveButton);
        formThemTA.add(themTAFooter, BorderLayout.SOUTH);

        formThemTA.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addFoodButton.setEnabled(true);
            }
        });

        formThemTA.setVisible(true);
    }

    private void FormSuaThucAn(JButton editFoodButton, int selectedRow) throws SQLException {
        JFrame formSuaTA = new JFrame("Sửa Thức Ăn");
        formSuaTA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSuaTA.setSize(800, 600);
        formSuaTA.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel suaTAHeader = new RoundedPanel(30, 30, Color.WHITE);
        suaTAHeader.setLayout(new BorderLayout());
        JLabel suaTATitle = new JLabel("Sửa Thức Ăn", SwingConstants.CENTER);
        suaTATitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        suaTATitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        suaTAHeader.add(suaTATitle, BorderLayout.CENTER);
        formSuaTA.add(suaTAHeader, BorderLayout.NORTH);

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // Center panel for form inputs
        RoundedPanel suaTACenter = new RoundedPanel(30, 30, Color.WHITE);
        suaTACenter.setLayout(new GridLayout(4, 3, 10, 10));
        suaTACenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String maThucAn = tableModel.getValueAt(selectedRow, 0).toString();
        ThucAnDTO thucAn = thucAnBUS.getThucAnById(maThucAn);

        // Mã thức ăn (không sửa được)
        JLabel maTALabel = new JLabel("Mã thức ăn:");
        JTextField maTAField = new JTextField(maThucAn);
        maTAField.setEditable(false);
        maTAField.setBackground(Color.LIGHT_GRAY);
        JLabel emptyLabel1 = new JLabel();
        emptyLabel1.setPreferredSize(new Dimension(60, 60));


        // Tên thức ăn
        JLabel tenTALabel = new JLabel("Tên thức ăn:");
        JTextField tenTAField = new JTextField(thucAn.getTenThucAn());
        JLabel foodNameErrorLabel = new JLabel();
        foodNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodNameErrorLabel.setForeground(Color.RED);
        foodNameErrorLabel.setVisible(false);
        foodNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Mô tả
        JLabel moTaLabel = new JLabel("Mô tả:");
        JTextField moTaField = new JTextField(thucAn.getMoTa());
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setPreferredSize(new Dimension(60, 60));

        // Loại món ăn
        JLabel loaiMonAnLabel = new JLabel("Loại món ăn:");
        JComboBox<String> loaiMonAnCombo = new JComboBox<>(new String[]{"Món chính", "Món phụ", "Đồ uống"});
        loaiMonAnCombo.setSelectedItem(thucAn.getLoaiMonAn());
        JLabel emptyLabel3 = new JLabel();
        emptyLabel3.setPreferredSize(new Dimension(60, 60));

        // Giá thức ăn
        JLabel giaMonAnLabel = new JLabel("Giá thức ăn:");
        JTextField giaMonAnField = new JTextField(String.valueOf(thucAn.getGia()));
        JLabel foodPriceErrorLabel = new JLabel();
        foodPriceErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodPriceErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodPriceErrorLabel.setForeground(Color.RED);
        foodPriceErrorLabel.setVisible(false);
        foodPriceErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Ảnh thức ăn
        JLabel anhMonAnLabel = new JLabel("Ảnh thức ăn:");
        JTextField anhMonAnField = new JTextField(thucAn.getAnhThucAn());
        JLabel foodImageErrorLabel = new JLabel();
        foodImageErrorLabel.setPreferredSize(new Dimension(60, 60));
        foodImageErrorLabel.setIcon(new ImageIcon(scaledImage));
        foodImageErrorLabel.setForeground(Color.RED);
        foodImageErrorLabel.setVisible(false);
        foodImageErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        suaTACenter.add(maTALabel);
        suaTACenter.add(maTAField);
        suaTACenter.add(emptyLabel1);

        suaTACenter.add(tenTALabel);
        suaTACenter.add(tenTAField);
        suaTACenter.add(foodNameErrorLabel);
        
        suaTACenter.add(moTaLabel);
        suaTACenter.add(moTaField);
        suaTACenter.add(emptyLabel2);

        suaTACenter.add(loaiMonAnLabel);
        suaTACenter.add(loaiMonAnCombo);
        suaTACenter.add(emptyLabel3);

        suaTACenter.add(giaMonAnLabel);
        suaTACenter.add(giaMonAnField);
        suaTACenter.add(foodPriceErrorLabel);

        suaTACenter.add(anhMonAnLabel);
        suaTACenter.add(anhMonAnField);
        suaTACenter.add(foodImageErrorLabel);

        formSuaTA.add(suaTACenter, BorderLayout.CENTER);

        // Footer panel with Save button
        RoundedPanel suaTAFooter = new RoundedPanel(30, 30, Color.WHITE);
        suaTAFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            try {
                ThucAnDTO ta = new ThucAnDTO();
                ta.setMaThucAn(maTAField.getText());
                ta.setTenThucAn(tenTAField.getText());
                ta.setMoTa(moTaField.getText());
                ta.setLoaiMonAn((String) loaiMonAnCombo.getSelectedItem());
                ta.setGia(Double.parseDouble(giaMonAnField.getText()));
                ta.setAnhThucAn(anhMonAnField.getText());

                thucAnBUS.updateThucAn(ta);
                thucAnBUS.refreshTableData(tableModel);
                JOptionPane.showMessageDialog(formSuaTA, "Sửa thức ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                formSuaTA.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(formSuaTA, "Lỗi khi sửa thức ăn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        suaTAFooter.add(saveButton);
        formSuaTA.add(suaTAFooter, BorderLayout.SOUTH);

        formSuaTA.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                editFoodButton.setEnabled(true);
            }
        });

        formSuaTA.setVisible(true);
    }


}