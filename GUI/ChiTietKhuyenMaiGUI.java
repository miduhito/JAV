package GUI;

import BUS.ChiTietKhuyenMaiBUS;
import BUS.ThucAnBUS;
import Custom.MyButton;
import Custom.MyLabel;
import Custom.RobotoFont;
import Custom.Utilities;
import DTO.ChiTietKhuyenMaiDTO;
import DTO.ThucAnDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class ChiTietKhuyenMaiGUI extends JFrame{
    private JTable chiTietTable;
    private DefaultTableModel chiTietTableModel;
    private JFrame formThemChiTietKhuyenMai;
    private JFrame formSuaChiTietKhuyenMai;
    private JTextField maThucAnField;
    private JComboBox<String> thucAnBox;
    private JTextField thucAnField;
    private JTextField giaTriKhuyenMaiField;
    private String maKhuyenMai;
    private ChiTietKhuyenMaiBUS chiTietKhuyenMaiBUS;
    private ThucAnBUS thucAnBUS;

    public ChiTietKhuyenMaiGUI(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
        chiTietKhuyenMaiBUS = new ChiTietKhuyenMaiBUS();
        thucAnBUS = new ThucAnBUS();
        initComponents();
    }

    private void initComponents() {
        setTitle("Chi tiết khuyến mãi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleForm = new JLabel("Chi tiết khuyến mãi", SwingConstants.CENTER);
        titleForm.setFont(RobotoFont.getRobotoBold(24f));
        titleForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleForm, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách xung quanh

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Tạo khoảng cách dưới button

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);
        ImageIcon hideIcon = Utilities.loadAndResizeIcon("Resources\\Image\\Hide.png", 30, 30);

        MyButton themButton = new MyButton("Thêm", addIcon);
        themButton.setPreferredSize(new Dimension(130, 40));
        themButton.addActionListener(e -> formThemChiTietKhuyenMai());

        MyButton suaButton = new MyButton("Sửa", editIcon);
        suaButton.setPreferredSize(new Dimension(130, 40));
        suaButton.addActionListener(e -> formSuaChiTietKhuyenMai(chiTietTable));

        MyButton xoaButton = new MyButton("Xóa", deleteIcon);
        xoaButton.setPreferredSize(new Dimension(130, 40));
        xoaButton.addActionListener(e -> handleXoaChiTiet());

        MyButton anButton = new MyButton("Ẩn", hideIcon);
        anButton.setPreferredSize(new Dimension(130, 40));
        anButton.addActionListener(e -> handleAnChiTiet());

        buttonPanel.add(themButton);
        buttonPanel.add(suaButton);
        buttonPanel.add(xoaButton);
        buttonPanel.add(anButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Giá khuyến mãi", "Đơn vị"};
        chiTietTableModel = new DefaultTableModel(columnNames, 0);
        chiTietTable = new JTable(chiTietTableModel);
        chiTietTable.setRowHeight(30);
        chiTietTable.setFont(RobotoFont.getRobotoRegular(14f));

        JTableHeader header = chiTietTable.getTableHeader();
        header.setFont(RobotoFont.getRobotoBold(14f));
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));

        formatTableUI(header);

        JScrollPane scrollPane = new JScrollPane(chiTietTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        scrollPane.setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        chiTietKhuyenMaiBUS.loadDataTable(chiTietTableModel, maKhuyenMai);

        setVisible(true);
    }

    private void formThemChiTietKhuyenMai(){
        formThemChiTietKhuyenMai = new JFrame();
        formThemChiTietKhuyenMai.setTitle("Thêm chi tiết khuyến mãi");
        formThemChiTietKhuyenMai.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemChiTietKhuyenMai.setSize(600, 350);
        formThemChiTietKhuyenMai.setLocationRelativeTo(null); // Căn giữa màn hình
        formThemChiTietKhuyenMai.setLayout(new BorderLayout());
        formThemChiTietKhuyenMai.getContentPane().setBackground(Color.WHITE);

        MyLabel titleForm = new MyLabel("Thêm chi tiết khuyến mãi", 24f, "Bold");
        titleForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel tenThucAnLabel = new MyLabel("Thức Ăn:", 14f, "Regular");
        ArrayList<ThucAnDTO> dataThucAn = new ArrayList<>(thucAnBUS.getAllThucAn());
        ArrayList<String> listThucAn = new ArrayList<>();
        for (ThucAnDTO thucAn: dataThucAn){
            listThucAn.add(thucAn.getMaThucAn() + ". " + thucAn.getTenThucAn());
        }
        thucAnBox = new JComboBox<>(listThucAn.toArray(new String[0]));
        thucAnBox.setEditable(true);

        JLabel giaTriKhuyenMaiLabel = new MyLabel("Giá trị khuyến mãi:", 14f, "Regular");
        giaTriKhuyenMaiField = new JTextField();

        MyButton themButton = new MyButton("Thêm");
        themButton.setPreferredSize(new Dimension(130, 40));
        themButton.addActionListener(e -> handleThemChiTiet(formThemChiTietKhuyenMai));

        JPanel formContent = new JPanel(new GridLayout(3, 2, 10, 10));
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel formFooter = new JPanel( new FlowLayout(FlowLayout.CENTER));
        formFooter.setLayout(new FlowLayout());
        formFooter.setBackground(Color.WHITE);
        formFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formFooter.add(themButton);

        formContent.add(tenThucAnLabel);
        formContent.add(thucAnBox);
        formContent.add(giaTriKhuyenMaiLabel);
        formContent.add(giaTriKhuyenMaiField);

        formThemChiTietKhuyenMai.add(formContent, BorderLayout.CENTER);
        formThemChiTietKhuyenMai.add(titleForm, BorderLayout.NORTH);
        formThemChiTietKhuyenMai.add(formFooter, BorderLayout.SOUTH);
        formThemChiTietKhuyenMai.setVisible(true);
    }

    private void formSuaChiTietKhuyenMai(JTable chiTietTable){
        formSuaChiTietKhuyenMai = new JFrame();
        formSuaChiTietKhuyenMai.setTitle("Sửa chi tiết khuyến mãi");
        formSuaChiTietKhuyenMai.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSuaChiTietKhuyenMai.setSize(600, 350);
        formSuaChiTietKhuyenMai.setLocationRelativeTo(null);
        formSuaChiTietKhuyenMai.setLayout(new BorderLayout());
        formSuaChiTietKhuyenMai.getContentPane().setBackground(Color.WHITE);

        int selectedRow = chiTietTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maSanPham = (String) chiTietTable.getValueAt(selectedRow, 0);
        String tenSanPham = (String) chiTietTable.getValueAt(selectedRow, 1);
        double giaTriKhuyenMai = (double) chiTietTable.getValueAt(selectedRow, 2);


        MyLabel titleForm = new MyLabel("Sửa chi tiết khuyến mãi", 24f, "Bold");
        titleForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel tenThucAnLabel = new MyLabel("Thức Ăn:", 14f, "Regular");
        thucAnField = new JTextField(maSanPham + ". " + tenSanPham);
        thucAnField.setEditable(false);


        JLabel giaTriKhuyenMaiLabel = new MyLabel("Giá trị khuyến mãi:", 14f, "Regular");
        giaTriKhuyenMaiField = new JTextField(String.valueOf(giaTriKhuyenMai));

        MyButton luuButton = new MyButton("Lưu");
        luuButton.setPreferredSize(new Dimension(130, 40));
        luuButton.addActionListener(e -> handleSuaChiTiet());

        JPanel formContent = new JPanel(new GridLayout(3, 2, 10, 10));
        formContent.setBackground(Color.WHITE);
        formContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel formFooter = new JPanel( new FlowLayout(FlowLayout.CENTER));
        formFooter.setLayout(new FlowLayout());
        formFooter.setBackground(Color.WHITE);
        formFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formFooter.add(luuButton);

        formContent.add(tenThucAnLabel);
        formContent.add(thucAnField);
        formContent.add(giaTriKhuyenMaiLabel);
        formContent.add(giaTriKhuyenMaiField);

        formSuaChiTietKhuyenMai.add(formContent, BorderLayout.CENTER);
        formSuaChiTietKhuyenMai.add(titleForm, BorderLayout.NORTH);
        formSuaChiTietKhuyenMai.add(formFooter, BorderLayout.SOUTH);
        formSuaChiTietKhuyenMai.setVisible(true);
    }

    private void handleThemChiTiet(JFrame formThemChiTietKhuyenMai) {
        try {
            String giaTriKhuyenMaiStr = giaTriKhuyenMaiField.getText().trim();

            if (giaTriKhuyenMaiStr.isEmpty()) {
                JOptionPane.showMessageDialog(formSuaChiTietKhuyenMai, "Giá trị khuyến mãi không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietKhuyenMaiDTO chiTietKhuyenMaiDTO = new ChiTietKhuyenMaiDTO();
            chiTietKhuyenMaiDTO.setMaKhuyenMai(maKhuyenMai);
            String maThucAn = String.valueOf(thucAnBox.getSelectedItem()).split(". ")[0];
            chiTietKhuyenMaiDTO.setMaThucAn(maThucAn);
            chiTietKhuyenMaiDTO.setGiaTriKhuyenMai(Double.parseDouble(giaTriKhuyenMaiField.getText()));
            chiTietKhuyenMaiDTO.setTrangThai(true);

            if (chiTietKhuyenMaiBUS.add(chiTietKhuyenMaiDTO)) {
                JOptionPane.showMessageDialog(null, "Thêm chi tiết khuyến mãi thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                formThemChiTietKhuyenMai.dispose();
                chiTietKhuyenMaiBUS.loadDataTable(chiTietTableModel, maKhuyenMai);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace();
        }
    }

    // xử lý nút sửa
    private void handleSuaChiTiet() {
        try {
            String giaTriKhuyenMaiStr = giaTriKhuyenMaiField.getText().trim();
            if (giaTriKhuyenMaiStr.isEmpty()) {
                JOptionPane.showMessageDialog(formSuaChiTietKhuyenMai, "Giá trị khuyến mãi không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietKhuyenMaiDTO chiTietKhuyenMaiDTO = new ChiTietKhuyenMaiDTO();
            chiTietKhuyenMaiDTO.setMaKhuyenMai(maKhuyenMai);
            String maThucAn = thucAnField.getText().split(". ")[0];
            chiTietKhuyenMaiDTO.setMaThucAn(maThucAn);
            chiTietKhuyenMaiDTO.setGiaTriKhuyenMai(Double.parseDouble(giaTriKhuyenMaiField.getText()));
            chiTietKhuyenMaiDTO.setTrangThai(true);

            if (chiTietKhuyenMaiBUS.update(chiTietKhuyenMaiDTO)) {
                JOptionPane.showMessageDialog(null, "Sửa chi tiết khuyến mãi thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                formSuaChiTietKhuyenMai.dispose();
                chiTietKhuyenMaiBUS.loadDataTable(chiTietTableModel, maKhuyenMai);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace();
        }

    }

    // xử lý nút xóa
    private void handleXoaChiTiet() {
        int selectedRow = chiTietTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để xóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Xác nhận xóa chi tiết này ?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            String maThucAn = (String) chiTietTable.getValueAt(selectedRow, 0);
            if (chiTietKhuyenMaiBUS.delete(maKhuyenMai, maThucAn)){
                JOptionPane.showMessageDialog(null, "Xóa chi tiết thành công !", "Information", JOptionPane.INFORMATION_MESSAGE);
                chiTietKhuyenMaiBUS.loadDataTable(chiTietTableModel, maKhuyenMai);
            } else {
                JOptionPane.showMessageDialog(null, "Xóa chi tiết thất bại !", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // xử lý nút ẩn
    private void handleAnChiTiet() {
        int selectedRow = chiTietTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để ẩn!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(null, "Xác nhận ẩn chi tiết này ?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String maThucAn = (String) chiTietTable.getValueAt(selectedRow, 0);
            if (chiTietKhuyenMaiBUS.hide(maKhuyenMai, maThucAn)) {
                chiTietKhuyenMaiBUS.loadDataTable(chiTietTableModel, maKhuyenMai);
            } else {
                JOptionPane.showMessageDialog(null, "Ẩn chi tiết thất bại !", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void formatTableUI(JTableHeader header){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < chiTietTable.getColumnCount(); i++) {
            chiTietTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        header.setDefaultRenderer(headerRenderer);
    }
}
