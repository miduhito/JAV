package GUI;

import BUS.KhuyenMaiBUS;
import Custom.*;
import DTO.KhuyenMaiDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import java.util.List;

public class QuanLiKhuyenMaiGUI extends RoundedPanel {
    private final KhuyenMaiBUS khuyenMaiBUS;
    private JTable khuyenMaiTable;
    private DefaultTableModel tableModel;
    private JTextField maKhuyenMaiField;
    private JTextField tenKhuyenMaiField, filterInput;
    private JDateChooser ngayBatDauChooser;
    private JDateChooser ngayKetThucChooser;
    private JComboBox<String> donViKhuyenMaiComboBox;
    private JTextField dieuKienApDungField;
    private JFrame formThemKhuyenMai;
    private JFrame formSuaKhuyenMai;
    private MyButton advancedSearchButton;

    private JLabel emptyLabel1;
    private JLabel emptyLabel2;
    private JLabel promotionStartDateErrorLabel;
    private JLabel promotionEndDateErrorLabel;
    private JLabel promotionNameErrorLabel;
    private JLabel promotionConditionErrorLabel;

    public QuanLiKhuyenMaiGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        khuyenMaiBUS = new KhuyenMaiBUS();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        MyLabel titleLabel = new MyLabel("Quản lý khuyến mãi", 24f, "Bold");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setPreferredSize(new Dimension(getWidth(), 70 ));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new WrapLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 20, 20);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 20, 20);
        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 20, 20);
        ImageIcon hideIcon = Utilities.loadAndResizeIcon("Resources\\Image\\Hide.png", 20, 20);
        ImageIcon viewDetailIcon = Utilities.loadAndResizeIcon("Resources\\Image\\ViewDetail.png", 20, 20);

        // các nút chức năng
        MyButton addButton = new MyButton("Thêm khuyến mãi", addIcon);
        MyButton updateButton = new MyButton("Sửa thông tin", editIcon);
        MyButton deleteButton = new MyButton("Xóa", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(120, 30));
        MyButton hideButton = new MyButton("Ẩn khuyến mãi", hideIcon);
        MyButton detailButton = new MyButton("Xem chi tiết", viewDetailIcon);

        // tìm kiếm textBox và tìm kiếm nâng cao
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        MyLabel filterLabel = new MyLabel("Tìm mã khuyến mãi:", 14f, "Bold");
        filterInput = new JTextField();
        filterInput.setPreferredSize(new Dimension(120,30));
        filterInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filterPanel.add(filterLabel);
        filterPanel.add(filterInput);

        ImageIcon searchIcon = Utilities.loadAndResizeIcon("Resources\\Image\\MagnifyingGlass.png", 20, 20);
        advancedSearchButton = new MyButton("Tìm kiếm nâng cao", searchIcon);
        advancedSearchButton.setPreferredSize(new Dimension(185, 30));
        advancedSearchButton.setFont(RobotoFont.getRobotoBold(14f));
        filterPanel.add(advancedSearchButton);

        buttonPanel.add(filterPanel);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(hideButton);
        buttonPanel.add(detailButton);


        // bảng hiển thị khuyến mãi
        String[] columnNames = {"Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Đơn vị khuyến mãi", "Điều kiện áp dụng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        khuyenMaiTable = new JTable(tableModel);
        khuyenMaiTable.setRowHeight(35);
        khuyenMaiTable.setFont(RobotoFont.getRobotoRegular(12f));
        khuyenMaiTable.getTableHeader().setFont(RobotoFont.getRobotoBold(14f));
        khuyenMaiTable.getTableHeader().setVisible(true);

        // center chứa buttonPanel + bảng
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(khuyenMaiTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBackground(Color.WHITE);

        // add buttonPanel và scrollPane vô centerPanel
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // add centerPanel vô CENTER của BorderLayout
        add(centerPanel, BorderLayout.CENTER);

        khuyenMaiBUS.loadTableData(tableModel);
        formatTableUI();

        // ActionListener cho mấy nút
        filterInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterTable(); }

            private void filterTable() {
                String filterText = filterInput.getText().trim().toLowerCase();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tableModel.setRowCount(0);
                for (KhuyenMaiDTO khuyenMai : khuyenMaiBUS.getData()) {
                    String maKhuyenMai = khuyenMai.getMaKhuyenMai();
                    if (filterText.isEmpty() || maKhuyenMai.contains(filterText)) {
                        Vector<String> rowData = new Vector<>();
                        rowData.add(khuyenMai.getMaKhuyenMai());
                        rowData.add(khuyenMai.getTenKhuyenMai());
                        rowData.add(dateFormat.format(khuyenMai.getNgayBatDau()));
                        rowData.add(dateFormat.format(khuyenMai.getNgayKetThuc()));
                        rowData.add(Objects.equals(khuyenMai.getDonViKhuyenMai(), "Phần trăm") ? "%" : "VNĐ");
                        rowData.add(khuyenMai.getDieuKienApDung());
                        tableModel.addRow(rowData);
                    }
                }
            }
        });

        advancedSearchButton.addActionListener(e -> showAdvancedSearchDialog());
        addButton.addActionListener(e -> FormThemKhuyenMai());
        updateButton.addActionListener(e -> FormSuaKhuyenMai(tableModel));
        deleteButton.addActionListener(e -> handleXoaKhuyenMai());
        hideButton.addActionListener(e -> handleAnKhuyenMai());
        detailButton.addActionListener(e -> handleXemChiTietKhuyenMai());
    }

    private void FormThemKhuyenMai(){
        formThemKhuyenMai = new JFrame();
        formThemKhuyenMai.setTitle("Thêm khuyến mãi");
        formThemKhuyenMai.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemKhuyenMai.setSize(700, 600);
        formThemKhuyenMai.setLocationRelativeTo(null); // Căn giữa màn hình
        formThemKhuyenMai.setLayout(new BorderLayout());
        formThemKhuyenMai.getContentPane().setBackground(Color.WHITE);

        emptyLabel1 = new JLabel();
        emptyLabel2 = new JLabel();
        promotionStartDateErrorLabel = new JLabel();
        promotionEndDateErrorLabel = new JLabel();
        promotionNameErrorLabel = new JLabel();
        promotionConditionErrorLabel = new JLabel();

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        MyLabel titleForm = new MyLabel("Thêm khuyến mãi", 24f, "Bold");
        titleForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        MyLabel maKhuyenMaiLabel = new  MyLabel("Mã khuyến mãi:", 14f, "Regular");
        maKhuyenMaiField = new JTextField(khuyenMaiBUS.generateID());
        maKhuyenMaiField.setEditable(false);
        
        MyLabel tenKhuyenMaiLabel = new  MyLabel("Tên khuyến mãi:", 14f, "Regular");
        tenKhuyenMaiField = new JTextField();
        // promotionNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        promotionNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        promotionNameErrorLabel.setForeground(Color.RED);
        promotionNameErrorLabel.setVisible(false); // ẩn ban đầu
        promotionNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MyLabel ngayBatDauLabel = new  MyLabel("Ngày bắt đầu:", 14f, "Regular");
        ngayBatDauChooser = new JDateChooser();
        ngayBatDauChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) ngayBatDauChooser.getDateEditor().getUiComponent()).setEditable(false);
       
        MyLabel ngayKetThucLabel = new  MyLabel("Ngày kết thúc:", 14f, "Regular");
        ngayKetThucChooser = new JDateChooser();
        ngayKetThucChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) ngayKetThucChooser.getDateEditor().getUiComponent()).setEditable(false);
        
        MyLabel donViKhuyenMaiLabel = new MyLabel("Đơn vị khuyến mãi:", 14f, "Regular");
        String[] donViOptions = {"%", "Số tiền"};
        donViKhuyenMaiComboBox = new JComboBox<>(donViOptions);

        MyLabel dieuKienApDungLabel = new  MyLabel("Điều kiện áp dụng:", 14f, "Regular");
        dieuKienApDungField = new JTextField();
        promotionConditionErrorLabel.setIcon(new ImageIcon(scaledImage));
        promotionConditionErrorLabel.setForeground(Color.RED);
        promotionConditionErrorLabel.setVisible(false); // ẩn ban đầu
        promotionConditionErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MyButton themButton = new MyButton("Thêm");
        themButton.setPreferredSize(new Dimension(130, 40));
        themButton.addActionListener(e -> handleThemKhuyenMai(formThemKhuyenMai));

        // Thêm các thành phần vào formThemKhuyenMai
        JPanel formContent = new JPanel(new GridLayout(7, 3, 10, 10));
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel formFooter = new JPanel( new FlowLayout(FlowLayout.CENTER));
        formFooter.setLayout(new FlowLayout());
        formFooter.setBackground(Color.WHITE);
        formFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formFooter.add(themButton);

        formContent.add(maKhuyenMaiLabel);
        formContent.add(maKhuyenMaiField);
        formContent.add(emptyLabel1);

        formContent.add(tenKhuyenMaiLabel);
        formContent.add(tenKhuyenMaiField);
        formContent.add(promotionNameErrorLabel);

        formContent.add(ngayBatDauLabel);
        formContent.add(ngayBatDauChooser);
        formContent.add(promotionStartDateErrorLabel);

        formContent.add(ngayKetThucLabel);
        formContent.add(ngayKetThucChooser);
        formContent.add(promotionEndDateErrorLabel);

        formContent.add(donViKhuyenMaiLabel);
        formContent.add(donViKhuyenMaiComboBox);
        formContent.add(emptyLabel2);

        formContent.add(dieuKienApDungLabel);
        formContent.add(dieuKienApDungField);
        formContent.add(promotionConditionErrorLabel);

        formThemKhuyenMai.add(formContent, BorderLayout.CENTER);
        formThemKhuyenMai.add(titleForm, BorderLayout.NORTH);
        formThemKhuyenMai.add(formFooter, BorderLayout.SOUTH);
        formThemKhuyenMai.setVisible(true);
    }

    private void FormSuaKhuyenMai(DefaultTableModel tableModel){
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để sửa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getDataById(maKhuyenMai);
        if (khuyenMai == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        emptyLabel1 = new JLabel();
        emptyLabel2 = new JLabel();
        promotionNameErrorLabel = new JLabel();
        promotionConditionErrorLabel = new JLabel();
        promotionStartDateErrorLabel = new JLabel();
        promotionEndDateErrorLabel = new JLabel();

        formSuaKhuyenMai = new JFrame();
        formSuaKhuyenMai.setTitle("Sửa thông tin khuyến mãi");
        formSuaKhuyenMai.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSuaKhuyenMai.setSize(700, 600);
        formSuaKhuyenMai.setLocationRelativeTo(null); // Căn giữa màn hình
        formSuaKhuyenMai.setLayout(new BorderLayout());
        formSuaKhuyenMai.getContentPane().setBackground(Color.WHITE);

        MyLabel titleForm = new MyLabel("Sửa thông tin khuyến mãi", 24f, "Bold");
        titleForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        MyLabel maKhuyenMaiLabel = new MyLabel("Mã khuyến mãi:", 14f, "Regular");
        maKhuyenMaiField = new JTextField(khuyenMai.getMaKhuyenMai());
        maKhuyenMaiField.setEditable(false);

        MyLabel tenKhuyenMaiLabel = new MyLabel("Tên khuyến mãi:", 14f, "Regular");
        tenKhuyenMaiField = new JTextField(khuyenMai.getTenKhuyenMai());
        promotionNameErrorLabel.setPreferredSize(new Dimension(60, 60));
        promotionNameErrorLabel.setIcon(new ImageIcon(scaledImage));
        promotionNameErrorLabel.setForeground(Color.RED);
        promotionNameErrorLabel.setVisible(false); // ẩn ban đầu
        promotionNameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MyLabel ngayBatDauLabel = new MyLabel("Ngày bắt đầu:", 14f, "Regular");
        ngayBatDauChooser = new JDateChooser();
        ngayBatDauChooser.setDateFormatString("yyyy-MM-dd");
        ngayBatDauChooser.setDate(khuyenMai.getNgayBatDau());
        ((JTextField) ngayBatDauChooser.getDateEditor().getUiComponent()).setEditable(false);

        MyLabel ngayKetThucLabel = new MyLabel("Ngày kết thúc:", 14f, "Regular");
        ngayKetThucChooser = new JDateChooser();
        ngayKetThucChooser.setDateFormatString("yyyy-MM-dd");
        ngayKetThucChooser.setDate(khuyenMai.getNgayKetThuc());
        ((JTextField) ngayKetThucChooser.getDateEditor().getUiComponent()).setEditable(false);

        MyLabel donViKhuyenMaiLabel = new MyLabel("Đơn vị khuyến mãi:", 14f, "Regular");
        String[] donViOptions = {"%", "Số tiền"};
        donViKhuyenMaiComboBox = new JComboBox<>(donViOptions);
        donViKhuyenMaiComboBox.setSelectedItem(khuyenMai.getDonViKhuyenMai());

        MyLabel dieuKienApDungLabel = new MyLabel("Điều kiện áp dụng:", 14f, "Regular");
        dieuKienApDungField = new JTextField(khuyenMai.getDieuKienApDung());
        promotionConditionErrorLabel.setIcon(new ImageIcon(scaledImage));
        promotionConditionErrorLabel.setForeground(Color.RED);
        promotionConditionErrorLabel.setVisible(false); // ẩn ban đầu
        promotionConditionErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MyButton luuButton = new MyButton("Lưu");
        luuButton.setPreferredSize(new Dimension(130, 40));
        luuButton.addActionListener(e -> handleSuaKhuyenMai(formSuaKhuyenMai));

        JPanel formContent = new JPanel(new GridLayout(7, 3, 10, 10));
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel formFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formFooter.setBackground(Color.WHITE);
        formFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formFooter.add(luuButton);

        formContent.add(maKhuyenMaiLabel);
        formContent.add(maKhuyenMaiField);
        formContent.add(emptyLabel1);

        formContent.add(tenKhuyenMaiLabel);
        formContent.add(tenKhuyenMaiField);
        formContent.add(promotionNameErrorLabel);

        formContent.add(ngayBatDauLabel);
        formContent.add(ngayBatDauChooser);
        formContent.add(promotionStartDateErrorLabel);

        formContent.add(ngayKetThucLabel);
        formContent.add(ngayKetThucChooser);
        formContent.add(promotionEndDateErrorLabel);

        formContent.add(donViKhuyenMaiLabel);
        formContent.add(donViKhuyenMaiComboBox);
        formContent.add(emptyLabel2);

        formContent.add(dieuKienApDungLabel);
        formContent.add(dieuKienApDungField);
        formContent.add(promotionConditionErrorLabel);

        formSuaKhuyenMai.add(formFooter, BorderLayout.SOUTH);
        formSuaKhuyenMai.add(formContent, BorderLayout.CENTER);
        formSuaKhuyenMai.add(titleForm, BorderLayout.NORTH);
        formSuaKhuyenMai.setVisible(true);
    }

    // xử lý thêm khuyến mãi
    private void handleThemKhuyenMai(JFrame formThemKhuyenMai) {
        try {
            promotionNameErrorLabel.setVisible(false);
            promotionStartDateErrorLabel.setVisible(false);

            Boolean isValid = true;

            // Validate tên khuyến mãi
            String promotionName = tenKhuyenMaiField.getText().trim();
            List<String> promotionNameErrors = new ArrayList<>();
            if(promotionName.isEmpty()) {
                promotionNameErrors.add("Tên khuyến mãi không được để trống.");
            }
            if(promotionName.matches("^[0-9]+$")) {
                promotionNameErrors.add("Tên khuyến mãi không được chỉ chứa số.");
            }
            if(!promotionNameErrors.isEmpty()) {
                isValid = false;
                StringBuilder promotionNameTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
                for(String err: promotionNameErrors){
                    promotionNameTooltip.append("<li>").append(err).append("</li>");
                }
                promotionNameTooltip.append("</ul></html>");
                promotionNameErrorLabel.setToolTipText(promotionNameTooltip.toString());
                promotionNameErrorLabel.setVisible(true);
            }

            // Validate điều kiện khuyến mãi
            String promotionCondition = dieuKienApDungField.getText().trim();
            if(promotionCondition.isEmpty()) {
                isValid = false;
                promotionConditionErrorLabel.setToolTipText("Điều kiện áp dụng không được để trống.");
                promotionConditionErrorLabel.setVisible(true);
            }
            
            if(isValid) {
                KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO();
                khuyenMai.setMaKhuyenMai(maKhuyenMaiField.getText());
                khuyenMai.setTenKhuyenMai(tenKhuyenMaiField.getText());
                khuyenMai.setNgayBatDau(ngayBatDauChooser.getDate());
                khuyenMai.setNgayKetThuc(ngayKetThucChooser.getDate());
                khuyenMai.setDonViKhuyenMai(Objects.equals(donViKhuyenMaiComboBox.getSelectedItem(), "%") ? "Phần trăm" : "Số tiền");
                khuyenMai.setDieuKienApDung(dieuKienApDungField.getText());
                khuyenMai.setTrangThai(true);

                if (khuyenMaiBUS.add(khuyenMai)) {
                    JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    khuyenMaiBUS.loadTableData(tableModel);
                    formThemKhuyenMai.dispose();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // xử lý sửa khuyến mãi
    private void handleSuaKhuyenMai(JFrame formSuaKhuyenMai) {
        promotionNameErrorLabel.setVisible(false);
        promotionConditionErrorLabel.setVisible(false);

        Boolean isValid = true;

        // Validate tên khuyến mãi
        String promotionName = tenKhuyenMaiField.getText().trim();
        List<String> promotionNameErrors = new ArrayList<>();
        if(promotionName.isEmpty()) {
            promotionNameErrors.add("Tên khuyến mãi không được để trống.");
        }
        if(promotionName.matches("^[0-9]+$")) {
            promotionNameErrors.add("Tên khuyến mãi không được chỉ chứa số.");
        }
        if(!promotionNameErrors.isEmpty()) {
            isValid = false;
            StringBuilder promotionNameTooltip = new StringBuilder("<html><ul style='margin:0;padding-left:16px;'>");
            for(String err: promotionNameErrors){
                promotionNameTooltip.append("<li>").append(err).append("</li>");
            }
            promotionNameTooltip.append("</ul></html>");
            promotionNameErrorLabel.setToolTipText(promotionNameTooltip.toString());
            promotionNameErrorLabel.setVisible(true);
        }

        // Validate điều kiện khuyến mãi
        String promotionCondition = dieuKienApDungField.getText().trim();
        if(promotionCondition.isEmpty()) {
            isValid = false;
            promotionConditionErrorLabel.setToolTipText("Điều kiện áp dụng không được để trống.");
            promotionConditionErrorLabel.setVisible(true);
        }

        if(isValid) {
            KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO();
            khuyenMai.setMaKhuyenMai(maKhuyenMaiField.getText());
            khuyenMai.setTenKhuyenMai(tenKhuyenMaiField.getText());
            khuyenMai.setNgayBatDau(ngayBatDauChooser.getDate());
            khuyenMai.setNgayKetThuc(ngayKetThucChooser.getDate());
            khuyenMai.setDonViKhuyenMai(Objects.equals(donViKhuyenMaiComboBox.getSelectedItem(), "%") ? "Phần trăm" : "Số tiền");
            khuyenMai.setDieuKienApDung(dieuKienApDungField.getText());

            if (khuyenMaiBUS.update(khuyenMai)) {
                JOptionPane.showMessageDialog(null, "Sửa khuyến mãi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                khuyenMaiBUS.loadTableData(tableModel);
                formSuaKhuyenMai.dispose(); // Đóng form
            }
        }
    }

    // xử lý xóa khuyến mãi
    private void handleXoaKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khuyến mãi để xóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa khuyến mãi ?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (khuyenMaiBUS.delete(maKhuyenMai)) {
                JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                khuyenMaiBUS.loadTableData(tableModel); // Cập nhật lại bảng
            }
        }
    }

    // xử lý ẩn khuyến mãi
    private void handleAnKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khuyến mãi để ẩb\n!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn ẩn khuyến mãi ?",
                "Xác nhận ẩn", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (khuyenMaiBUS.hide(maKhuyenMai)) {
                khuyenMaiBUS.loadTableData(tableModel); // Cập nhật lại bảng
            }
        }
    }

    // xử lý xem chi tiết khuyến mãi
    private void handleXemChiTietKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để xem chi tiết!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        // Render form chi tiết khuyến mãi
        new ChiTietKhuyenMaiGUI(maKhuyenMai);
    }

    private void formatTableUI(){
        JTableHeader header = khuyenMaiTable.getTableHeader();
        header.setBackground(Color.WHITE);

        TableColumnModel columnModel = khuyenMaiTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80); // Mã khuyến mãi
        columnModel.getColumn(1).setPreferredWidth(120); // Tên khuyến mãi
        columnModel.getColumn(2).setPreferredWidth(80); // Ngày bắt đầu
        columnModel.getColumn(3).setPreferredWidth(80); // Ngày kết thúc
        columnModel.getColumn(4).setPreferredWidth(110); // Đơn vị
        columnModel.getColumn(5).setPreferredWidth(180); // Điều kiện áp dụng

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < khuyenMaiTable.getColumnCount(); i++) {
            khuyenMaiTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) khuyenMaiTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        khuyenMaiTable.getTableHeader().setDefaultRenderer(headerRenderer);
    }

    private void showAdvancedSearchDialog() {
        JFrame dialog = new JFrame("Tìm kiếm nâng cao");
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setBackground(Color.WHITE);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0 ,20));


        // mã khuyến mãi field
        JPanel maKhuyenMaiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maKhuyenMaiPanel.setBackground(Color.WHITE);
        MyLabel maKhuyenMaiLabel = new MyLabel("Mã khuyến mãi:", 14f, "Bold");
        JTextField maKhuyenMaiField = new JTextField();
        maKhuyenMaiField.setPreferredSize(new Dimension(180, 30));
        maKhuyenMaiField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        maKhuyenMaiPanel.add(maKhuyenMaiLabel);
        maKhuyenMaiPanel.add(maKhuyenMaiField);
        fieldsPanel.add(maKhuyenMaiPanel);

        // tên khuyến mãi field
        JPanel tenKhuyenMaiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tenKhuyenMaiPanel.setBackground(Color.WHITE);
        MyLabel tenKhuyenMaiLabel = new MyLabel("Tên khuyến mãi:", 14f, "Bold");
        JTextField tenKhuyenMaiField = new JTextField();
        tenKhuyenMaiField.setPreferredSize(new Dimension(180, 30));
        tenKhuyenMaiField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tenKhuyenMaiPanel.add(tenKhuyenMaiLabel);
        tenKhuyenMaiPanel.add(tenKhuyenMaiField);
        fieldsPanel.add(tenKhuyenMaiPanel);

        // đơn vị khuyến mãi field
        JPanel donviPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        donviPanel.setBackground(Color.WHITE);
        MyLabel donviLabel = new MyLabel("Đơn vị khuyến mãi:", 14f, "Bold");

        JComboBox<String> donviCombo = new JComboBox<>(new String[]{" ", "%", "VNĐ"});
        donviPanel.add(donviLabel);
        donviPanel.add(donviCombo);
        fieldsPanel.add(donviPanel);

        // ngày bắt đầu & kết thúc field
        JPanel ngayBatDauPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ngayBatDauPanel.setBackground(Color.WHITE);
        JPanel ngayKetThucPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ngayKetThucPanel.setBackground(Color.WHITE);

        JDateChooser startDate = new JDateChooser();
        JDateChooser endDate = new JDateChooser();
        startDate.setPreferredSize(new Dimension(180, 30));
        startDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        endDate.setPreferredSize(new Dimension(180,30));
        endDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ((JTextField) startDate.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) endDate.getDateEditor().getUiComponent()).setEditable(false);

        ngayBatDauPanel.add(new MyLabel("Ngày bắt đầu sau: ", 14f, "Bold"));
        ngayBatDauPanel.add(startDate);
        ngayKetThucPanel.add(new MyLabel("Ngày kết thúc trước: ", 14f, "Bold"));
        ngayKetThucPanel.add(endDate);

        fieldsPanel.add(ngayBatDauPanel);
        fieldsPanel.add(ngayKetThucPanel);

        // nút tìm kiếm
        JPanel searchButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20 ));
        searchButtonPanel.setBackground(Color.WHITE);
        MyButton searchButton = new MyButton("Tìm kiếm");
        searchButton.setPreferredSize(new Dimension(170, 35));
        searchButton.setFont(RobotoFont.getRobotoBold(14f));
        searchButtonPanel.add(searchButton);

        dialog.add(fieldsPanel, BorderLayout.CENTER);
        dialog.add(searchButtonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String maKhuyenMai = maKhuyenMaiField.getText().trim();
            String tenKhuyenMai = tenKhuyenMaiField.getText().trim();
            String donVi = String.valueOf(donviCombo.getSelectedItem());
            donVi = Objects.equals(donVi, "%") ? "Phần trăm" : Objects.equals(donVi, "VNĐ") ? "Số tiền" : "";
//          donVi = donVi == "%" ? "Phần trăm" : donVi == "VNĐ" ? "Số tiền" : "";
            Date startDateValue = startDate.getDate();
            Date endDateValue = endDate.getDate();

            System.out.println(maKhuyenMai);
            System.out.println(tenKhuyenMai);
            System.out.println(donVi);
            System.out.println(startDateValue);
            System.out.println(endDateValue);


            ArrayList<KhuyenMaiDTO> filteredList = khuyenMaiBUS.advancedSearch(
                    maKhuyenMai.isEmpty() ? null : maKhuyenMai,
                    tenKhuyenMai.isEmpty() ? null : maKhuyenMai,
                    startDateValue,
                    endDateValue,
                    donVi
            );

            // Update table with filtered data
            tableModel.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (KhuyenMaiDTO khuyenMai : filteredList) {
                tableModel.addRow(new Object[]{
                        khuyenMai.getMaKhuyenMai(),
                        khuyenMai.getTenKhuyenMai(),
                        dateFormat.format(khuyenMai.getNgayBatDau()),
                        dateFormat.format(khuyenMai.getNgayKetThuc()),
                        Objects.equals(khuyenMai.getDonViKhuyenMai(), "Phần trăm") ? "%" : "VNĐ" ,
                        khuyenMai.getDieuKienApDung()
                });
            }
            dialog.dispose();
        });
        dialog.setVisible(true);
    }
}

