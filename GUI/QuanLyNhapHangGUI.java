package GUI;

import Custom.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class QuanLyNhapHangGUI extends RoundedPanel {
    public JTable nguyenLieuTable,cartTable;
    public DefaultTableModel nguyenLieuTableModel, cartTableModel;
    public QuanLyNhapHangGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        setLayout(new GridBagLayout()); // Use GridBagLayout
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        String[] columnNames = {"Mã nguyên liệu", "Tên nguyên liệu", "Nhà cung cấp", "Giá nhập", "Đơn vị"};
        Object[][] data = {}; // Placeholder data
        nguyenLieuTableModel = new DefaultTableModel(columnNames, 0);
        nguyenLieuTable = new JTable(nguyenLieuTableModel);
        JScrollPane tableScrollPane = new JScrollPane(nguyenLieuTable);
        tableScrollPane.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScrollPane, gbc);

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Giỏ hàng"
        ));
        cartPanel.setBackground(Color.WHITE);

        String[] cartColumnNames = {"Mã nguyên liệu", "Tên nguyên liệu", "Số lượng", "Giá nhập"};
        Object[][] cartData = {};
        cartTableModel = new DefaultTableModel(cartColumnNames, 0);
        cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        cartPanel.setPreferredSize(new Dimension(0, 250));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        add(cartPanel, gbc);

        JPanel functionBox = new JPanel();
        functionBox.setBackground(Color.WHITE);
        functionBox.setLayout(new GridLayout(7,2, 5,20));
        functionBox.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        functionBox.add(new MyLabel("Mã nguyên liệu:", 12f, "Regular"));
        JTextField maNguyenLieuField = new JTextField();
        functionBox.add(maNguyenLieuField);

        functionBox.add(new MyLabel("Tên nguyên liệu:", 12f, "Regular"));
        JTextField tenNguyenLieuField = new JTextField();
        functionBox.add(tenNguyenLieuField);

        functionBox.add(new MyLabel("Nhà cung cấp:", 12f, "Regular"));
        JComboBox<String> nhaCungCapComboBox = new JComboBox<>(new String[]{"Nhà cung cấp 1", "Nhà cung cấp 2", "Nhà cung cấp 3"});
        functionBox.add(nhaCungCapComboBox);

        functionBox.add(new MyLabel("Giá nhập:", 12f, "Regular"));
        JTextField giaNhapField = new JTextField();
        functionBox.add(giaNhapField);

        functionBox.add(new MyLabel("Số lượng:", 12f, "Regular"));
        JTextField soLuongField = new JTextField();
        functionBox.add(soLuongField);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);
        ImageIcon viewDetailIcon = Utilities.loadAndResizeIcon("Resources\\Image\\ViewDetail.png", 30, 30);

        functionBox.add(new MyButton("Thêm vào phiếu nhập"));
        functionBox.add(new MyButton("Cập nhật phiếu nhập"));
        functionBox.add(new MyButton("Xóa"));
        functionBox.add(new MyButton("Tạo phiếu nhập"));

        JScrollPane functionScrollPane = new JScrollPane(functionBox);
        functionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        functionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        functionScrollPane.setPreferredSize(new Dimension(250, 0));
        functionScrollPane.setBackground(Color.WHITE);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(functionScrollPane, gbc);
        formatTableUI();
    }

    private void formatTableUI(){
        // format cart table
        cartTable.setBackground(Color.WHITE);
        cartTable.setFont(RobotoFont.getRobotoRegular(14f));

        JTableHeader headerCart = cartTable.getTableHeader();
        headerCart.setBackground(Color.WHITE);
        headerCart.setFont(RobotoFont.getRobotoBold(14f));

        DefaultTableCellRenderer centerRendererCart = new DefaultTableCellRenderer();
        centerRendererCart.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < cartTable.getColumnCount(); i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRendererCart);
        }

        DefaultTableCellRenderer headerRendererCart = (DefaultTableCellRenderer) cartTable.getTableHeader().getDefaultRenderer();
        headerRendererCart.setHorizontalAlignment(SwingConstants.CENTER);
        cartTable.getTableHeader().setDefaultRenderer(headerRendererCart);

        // format information table
        nguyenLieuTable.setBackground(Color.WHITE);
        nguyenLieuTable.setFont(RobotoFont.getRobotoRegular(14f));

        JTableHeader headerInformation = nguyenLieuTable.getTableHeader();
        headerInformation.setBackground(Color.WHITE);
        headerInformation.setFont(RobotoFont.getRobotoBold(14f));

        DefaultTableCellRenderer centerRendererInformation = new DefaultTableCellRenderer();
        centerRendererInformation.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < nguyenLieuTable.getColumnCount(); i++) {
            nguyenLieuTable.getColumnModel().getColumn(i).setCellRenderer(centerRendererInformation);
        }

        DefaultTableCellRenderer headerRendererInformation = (DefaultTableCellRenderer) nguyenLieuTable.getTableHeader().getDefaultRenderer();
        headerRendererInformation.setHorizontalAlignment(SwingConstants.CENTER);
        nguyenLieuTable.getTableHeader().setDefaultRenderer(headerRendererInformation);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testGUI = new JFrame();
            testGUI.setSize(1000, 550);
            testGUI.add(new QuanLyNhapHangGUI());
            testGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testGUI.setVisible(true);
        });
    }
}