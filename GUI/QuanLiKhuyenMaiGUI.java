package GUI;

import BUS.KhuyenMaiBUS;
import Custom.*;
import DTO.KhuyenMaiDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Objects;

public class QuanLiKhuyenMaiGUI extends RoundedPanel {
    private final KhuyenMaiBUS khuyenMaiBUS;
    private JTable khuyenMaiTable;
    private DefaultTableModel tableModel;
    private JTextField maKhuyenMaiField;
    private JTextField tenKhuyenMaiField;
    private JDateChooser ngayBatDauChooser;
    private JDateChooser ngayKetThucChooser;
    private JComboBox<String> donViKhuyenMaiComboBox;
    private JTextField dieuKienApDungField;
    private JFrame formThemKhuyenMai;
    private JFrame formSuaKhuyenMai;

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
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);
        ImageIcon hideIcon = Utilities.loadAndResizeIcon("Resources\\Image\\Hide.png", 30, 30);
        ImageIcon viewDetailIcon = Utilities.loadAndResizeIcon("Resources\\Image\\ViewDetail.png", 30, 30);



        // các nút chức năng
        MyButton addButton = new MyButton("Thêm khuyến mãi", addIcon);
        MyButton updateButton = new MyButton("Sửa thông tin", editIcon);
        MyButton deleteButton = new MyButton("Xóa khuyến mãi", deleteIcon);
        MyButton hideButton = new MyButton("Ẩn khuyến mãi", hideIcon);
        MyButton detailButton = new MyButton("Xem chi tiết", viewDetailIcon);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(hideButton);
        buttonPanel.add(detailButton);

        // bảng hiển thị khuyến mãi
        String[] columnNames = {"Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Đơn vị khuyến mãi", "Điều kiện áp dụng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        khuyenMaiTable = new JTable(tableModel);
        khuyenMaiTable.setRowHeight(35);
        khuyenMaiTable.setFont(RobotoFont.getRobotoRegular(14f));
        khuyenMaiTable.getTableHeader().setFont(RobotoFont.getRobotoBold(14f));

        // center chứa buttonPanel + bảng
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(khuyenMaiTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBackground(Color.WHITE);

        // add buttonPanel và scrollPane vô centerPanel
        centerPanel.add(buttonPanel);
        centerPanel.add(scrollPane);

        // add centerPanel vô CENTER của BorderLayout
        add(centerPanel, BorderLayout.CENTER);

        khuyenMaiBUS.loadTableData(tableModel);
        formatTableUI();

        // ActionListener cho mấy nút
        addButton.addActionListener(_ -> FormThemKhuyenMai());
        updateButton.addActionListener(_ -> FormSuaKhuyenMai(tableModel));
        deleteButton.addActionListener(_ -> handleXoaKhuyenMai());
        hideButton.addActionListener(_ -> handleAnKhuyenMai());
        detailButton.addActionListener(_ -> handleXemChiTietKhuyenMai());
    }

    private void FormThemKhuyenMai(){
        formThemKhuyenMai = new JFrame();
        formThemKhuyenMai.setTitle("Thêm khuyến mãi");
        formThemKhuyenMai.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemKhuyenMai.setSize(700, 600);
        formThemKhuyenMai.setLocationRelativeTo(null); // Căn giữa màn hình
        formThemKhuyenMai.setLayout(new BorderLayout());
        formThemKhuyenMai.getContentPane().setBackground(Color.WHITE);

        MyLabel titleForm = new MyLabel("Thêm khuyến mãi", 24f, "Bold");
        titleForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        MyLabel maKhuyenMaiLabel = new  MyLabel("Mã khuyến mãi:", 14f, "Regular");
        maKhuyenMaiField = new JTextField(khuyenMaiBUS.generateID());
        maKhuyenMaiField.setEditable(false);

        MyLabel tenKhuyenMaiLabel = new  MyLabel("Tên khuyến mãi:", 14f, "Regular");
        tenKhuyenMaiField = new JTextField();

        MyLabel ngayBatDauLabel = new  MyLabel("Ngày bắt đầu:", 14f, "Regular");
        ngayBatDauChooser = new JDateChooser();
        ngayBatDauChooser.setDateFormatString("yyyy-MM-dd");

        MyLabel ngayKetThucLabel = new  MyLabel("Ngày kết thúc:", 14f, "Regular");
        ngayKetThucChooser = new JDateChooser();
        ngayKetThucChooser.setDateFormatString("yyyy-MM-dd");

        MyLabel donViKhuyenMaiLabel = new MyLabel("Đơn vị khuyến mãi:", 14f, "Regular");
        String[] donViOptions = {"%", "Số tiền"};
        donViKhuyenMaiComboBox = new JComboBox<>(donViOptions);

        MyLabel dieuKienApDungLabel = new  MyLabel("Điều kiện áp dụng:", 14f, "Regular");
        dieuKienApDungField = new JTextField();

        MyButton themButton = new MyButton("Thêm");
        themButton.setPreferredSize(new Dimension(130, 40));
        themButton.addActionListener(_ -> handleThemKhuyenMai(formThemKhuyenMai));

        // Thêm các thành phần vào formThemKhuyenMai
        JPanel formContent = new JPanel(new GridLayout(7, 2, 10, 10));
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel formFooter = new JPanel( new FlowLayout(FlowLayout.CENTER));
        formFooter.setLayout(new FlowLayout());
        formFooter.setBackground(Color.WHITE);
        formFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formFooter.add(themButton);

        formContent.add(maKhuyenMaiLabel);
        formContent.add(maKhuyenMaiField);
        formContent.add(tenKhuyenMaiLabel);
        formContent.add(tenKhuyenMaiField);
        formContent.add(ngayBatDauLabel);
        formContent.add(ngayBatDauChooser);
        formContent.add(ngayKetThucLabel);
        formContent.add(ngayKetThucChooser);
        formContent.add(donViKhuyenMaiLabel);
        formContent.add(donViKhuyenMaiComboBox);
        formContent.add(dieuKienApDungLabel);
        formContent.add(dieuKienApDungField);

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

        MyLabel ngayBatDauLabel = new MyLabel("Ngày bắt đầu:", 14f, "Regular");
        ngayBatDauChooser = new JDateChooser();
        ngayBatDauChooser.setDateFormatString("yyyy-MM-dd");
        ngayBatDauChooser.setDate(khuyenMai.getNgayBatDau());

        MyLabel ngayKetThucLabel = new MyLabel("Ngày kết thúc:", 14f, "Regular");
        ngayKetThucChooser = new JDateChooser();
        ngayKetThucChooser.setDateFormatString("yyyy-MM-dd");
        ngayKetThucChooser.setDate(khuyenMai.getNgayKetThuc());

        MyLabel donViKhuyenMaiLabel = new MyLabel("Đơn vị khuyến mãi:", 14f, "Regular");
        String[] donViOptions = {"%", "Số tiền"};
        donViKhuyenMaiComboBox = new JComboBox<>(donViOptions);
        donViKhuyenMaiComboBox.setSelectedItem(khuyenMai.getDonViKhuyenMai());

        MyLabel dieuKienApDungLabel = new MyLabel("Điều kiện áp dụng:", 14f, "Regular");
        dieuKienApDungField = new JTextField(khuyenMai.getDieuKienApDung());

        MyButton luuButton = new MyButton("Lưu");
        luuButton.setPreferredSize(new Dimension(130, 40));
        luuButton.addActionListener(_ -> handleSuaKhuyenMai(formSuaKhuyenMai));

        JPanel formContent = new JPanel(new GridLayout(7, 2, 10, 10));
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel formFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formFooter.setBackground(Color.WHITE);
        formFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formFooter.add(luuButton);

        formContent.add(maKhuyenMaiLabel);
        formContent.add(maKhuyenMaiField);
        formContent.add(tenKhuyenMaiLabel);
        formContent.add(tenKhuyenMaiField);
        formContent.add(ngayBatDauLabel);
        formContent.add(ngayBatDauChooser);
        formContent.add(ngayKetThucLabel);
        formContent.add(ngayKetThucChooser);
        formContent.add(donViKhuyenMaiLabel);
        formContent.add(donViKhuyenMaiComboBox);
        formContent.add(dieuKienApDungLabel);
        formContent.add(dieuKienApDungField);

        formSuaKhuyenMai.add(formFooter, BorderLayout.SOUTH);
        formSuaKhuyenMai.add(formContent, BorderLayout.CENTER);
        formSuaKhuyenMai.add(titleForm, BorderLayout.NORTH);
        formSuaKhuyenMai.setVisible(true);
    }

    // xử lý thêm khuyến mãi
    private void handleThemKhuyenMai(JFrame formThemKhuyenMai) {
        try {
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // xử lý sửa khuyến mãi
    private void handleSuaKhuyenMai(JFrame formSuaKhuyenMai) {
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < khuyenMaiTable.getColumnCount(); i++) {
            khuyenMaiTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) khuyenMaiTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        khuyenMaiTable.getTableHeader().setDefaultRenderer(headerRenderer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lí khuyến mãi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new QuanLiKhuyenMaiGUI());
            frame.setVisible(true);
        });
    }
}

