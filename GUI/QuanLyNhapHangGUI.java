package GUI;

import BUS.PhieuNhapBUS;
import Custom.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class QuanLyNhapHangGUI extends RoundedPanel {
    public JTable nguyenLieuTable,cartTable;
    public DefaultTableModel nguyenLieuTableModel, cartTableModel;
    public MyButton addButton, editButton, deleteButton, confirmButton;
    public JPanel headerPanel, contentPanel, fieldBox1, fieldBox2, fieldBox3, fieldBox4, fieldBox5, buttonBox1, buttonBox2, buttonBox3, buttonBox4;
    public JTextField maNguyenLieuField, tenNguyenLieuField, giaNhapField, soLuongField;
    public JComboBox<String> nhaCungCapComboBox;
    public PhieuNhapBUS phieuNhapBUS;

    public QuanLyNhapHangGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        setLayout(new BorderLayout()); // Use GridBagLayout
        initComponents();
    }

    private void initComponents() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        MyLabel titleHeader = new MyLabel("Quản lý nhập hàng", 24f, "Bold");
        headerPanel.add(titleHeader);
        headerPanel.setBackground(Color.decode("#F5ECE0"));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 0,13,0));

        add(headerPanel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        String[] columnNames = {"Mã NL", "Tên NL", "Nhà cung cấp", "Giá nhập", "Đơn vị"};
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
        contentPanel.add(tableScrollPane, gbc);

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Phiếu nhập mới"
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
        contentPanel.add(cartPanel, gbc);

        // Cột bên phải
        JPanel functionBox = new JPanel();
        functionBox.setBackground(Color.WHITE);
        functionBox.setLayout(new GridLayout(7,2, 5,   0));
        functionBox.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        MyLabel maNguyenLieuLabel = new MyLabel("Mã nguyên liệu:", 14f, "Bold");
        maNguyenLieuField = new JTextField(SwingConstants.CENTER);
        maNguyenLieuField.setPreferredSize(new Dimension(150, 30));
        maNguyenLieuField.setEditable(false);
        fieldBox1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox1.setBackground(Color.WHITE);
        fieldBox1.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));
        fieldBox1.add(maNguyenLieuField);
        functionBox.add(maNguyenLieuLabel);
        functionBox.add(fieldBox1);

        MyLabel tenNguyenLieuLabel = new MyLabel("Tên nguyên liệu:", 14f, "Bold");
        tenNguyenLieuField = new JTextField();
        tenNguyenLieuField.setPreferredSize(new Dimension(150, 30));
        tenNguyenLieuField.setEditable(false);
        fieldBox2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox2.setBackground(Color.WHITE);
        fieldBox2.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        fieldBox2.add(tenNguyenLieuField);
        functionBox.add(tenNguyenLieuLabel);
        functionBox.add(fieldBox2);

        MyLabel nhaCungCapLabel = new MyLabel("Nhà cung cấp:", 14f, "Bold");
        nhaCungCapComboBox = new JComboBox<>(new String[]{"Nhà cung cấp 1", "Nhà cung cấp 2", "Nhà cung cấp 3"});
        nhaCungCapComboBox.setPreferredSize(new Dimension(150, 30));
        fieldBox3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox3.setBackground(Color.WHITE);
        fieldBox3.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        fieldBox3.add(nhaCungCapComboBox);
        functionBox.add(nhaCungCapLabel);
        functionBox.add(fieldBox3);

        MyLabel giaNhapLabel = new MyLabel("Giá nhập:", 14f, "Bold");
        giaNhapField = new JTextField();
        giaNhapField.setPreferredSize(new Dimension(150,30));
        giaNhapField.setEditable(false);
        fieldBox4 = new JPanel(new FlowLayout(FlowLayout.LEFT));;
        fieldBox4.setBackground(Color.WHITE);
        fieldBox4.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        fieldBox4.add(giaNhapField);
        functionBox.add(giaNhapLabel);
        functionBox.add(fieldBox4);

        MyLabel soLuongLabel = new MyLabel("Số lượng:", 14f, "Bold");
        soLuongField = new JTextField();
        soLuongField.setPreferredSize(new Dimension(150,30));
        fieldBox5 = new JPanel(new FlowLayout(FlowLayout.LEFT));;
        fieldBox5.setBackground(Color.WHITE);
        fieldBox5.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));
        fieldBox5.add(soLuongField);
        functionBox.add(soLuongLabel);
        functionBox.add(fieldBox5);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 20, 20);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 20, 20);
        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 20, 20);
        ImageIcon confirmIcon = Utilities.loadAndResizeIcon("Resources\\Image\\Confirm.png", 20, 20);

        // add panel
        buttonBox1 = new JPanel();
        buttonBox1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonBox1.setBackground(Color.WHITE);
        addButton = new MyButton("Thêm vào phiếu", addIcon);
        addButton.setBackground(Color.LIGHT_GRAY);
        addButton.setPreferredSize(new Dimension(150, 40));
        buttonBox1.add(addButton);

        // edit panel
        buttonBox2 = new JPanel();
        buttonBox2.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonBox2.setBackground(Color.WHITE);
        editButton = new MyButton("Cập nhật", editIcon);
        editButton.setBackground(Color.LIGHT_GRAY);
        editButton.setPreferredSize(new Dimension(150, 40));
        buttonBox2.add(editButton);


        // delete panel
        buttonBox3 = new JPanel();
        buttonBox3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonBox3.setBackground(Color.WHITE);
        deleteButton = new MyButton("Xóa chi tiết", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(150, 40));
        buttonBox3.add(deleteButton);


        // confirm panel
        buttonBox4 = new JPanel();
        buttonBox4.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonBox4.setBackground(Color.WHITE);
        confirmButton = new MyButton("Tạo phiếu nhập", confirmIcon);
        confirmButton.setPreferredSize(new Dimension(150, 40));
        buttonBox4.add(confirmButton);


        functionBox.add(buttonBox1);
        functionBox.add(buttonBox2);
        functionBox.add(buttonBox3);
        functionBox.add(buttonBox4);

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
        contentPanel.add(functionScrollPane, gbc);
        add(contentPanel, BorderLayout.CENTER);

        formatTableUI();
        loadTableActionListener();
        loadButtonActionListener();

        phieuNhapBUS = new PhieuNhapBUS();
        phieuNhapBUS.loadDataTable(nguyenLieuTableModel);

    }

    private void formatTableUI(){
        // format cart table
        cartTable.setBackground(Color.WHITE);
        cartTable.setFont(RobotoFont.getRobotoRegular(12f));

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
        TableColumnModel columnModel = nguyenLieuTable.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(80);  // Mã NL
        columnModel.getColumn(1).setPreferredWidth(120); // Tên NL
        columnModel.getColumn(2).setPreferredWidth(200); // Nhà cung cấp
        columnModel.getColumn(3).setPreferredWidth(100); // Giá nhập
        columnModel.getColumn(4).setPreferredWidth(80);  // Đơn vị

        nguyenLieuTable.setBackground(Color.WHITE);
        nguyenLieuTable.setFont(RobotoFont.getRobotoRegular(12f));

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

    private void loadTableActionListener(){
        // bảng nguyên liệu
        nguyenLieuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = nguyenLieuTable.getSelectedRow();
                if (selectedRow != -1) {
                    cartTable.clearSelection();
                    String maNguyenLieu = nguyenLieuTable.getValueAt(selectedRow, 0).toString();
                    String tenNguyenLieu = nguyenLieuTable.getValueAt(selectedRow, 1).toString();
                    String nhaCungCap = nguyenLieuTable.getValueAt(selectedRow, 2).toString();
                    String giaNhap = nguyenLieuTable.getValueAt(selectedRow, 3).toString();

                    maNguyenLieuField.setText(maNguyenLieu);
                    tenNguyenLieuField.setText(tenNguyenLieu);
                    giaNhapField.setText(giaNhap);

                    boolean found = false;
                    for (int i = 0; i < nhaCungCapComboBox.getItemCount(); i++) {
                        if (nhaCungCapComboBox.getItemAt(i).equals(nhaCungCap)) {
                            nhaCungCapComboBox.setSelectedIndex(i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        nhaCungCapComboBox.setSelectedIndex(0);
                    }
                    soLuongField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // bảng giỏ hàng
        cartTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    nguyenLieuTable.clearSelection();
                    String maNguyenLieu = (String) cartTableModel.getValueAt(selectedRow, 0);
                    String tenNguyenLieu = (String) cartTableModel.getValueAt(selectedRow, 1);
                    String soLuong = cartTableModel.getValueAt(selectedRow, 2).toString();
                    String giaNhap = cartTableModel.getValueAt(selectedRow, 3).toString();

                    maNguyenLieuField.setText(maNguyenLieu);
                    tenNguyenLieuField.setText(tenNguyenLieu);
                    soLuongField.setText(soLuong);
                    giaNhapField.setText(giaNhap);
                }
            }
        });

    }

    public void loadButtonActionListener(){
        // nút thêm
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maNguyenLieu = maNguyenLieuField.getText().trim();
                String tenNguyenLieu = tenNguyenLieuField.getText().trim();
                String giaNhap = giaNhapField.getText().trim();
                String soLuong = soLuongField.getText().trim();

                if (maNguyenLieu.isEmpty() || tenNguyenLieu.isEmpty() || giaNhap.isEmpty() || soLuong.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!soLuong.matches("\\d+") || !giaNhap.matches("\\d+(\\.\\d+)?" )) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (Integer.parseInt(soLuong) == 0){
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số hợp lệ (khác 0)!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int soLuongMoi = Integer.parseInt(soLuong);
                boolean daTonTai = false;

                for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                    if (cartTableModel.getValueAt(i, 0).equals(maNguyenLieu)) {
                        int soLuongHienTai = Integer.parseInt(cartTableModel.getValueAt(i, 2).toString());
                        cartTableModel.setValueAt(soLuongHienTai + soLuongMoi, i, 2);
                        daTonTai = true;
                        break;
                    }
                }

                if (!daTonTai) {
                    cartTableModel.addRow(new Object[]{maNguyenLieu, tenNguyenLieu, soLuongMoi, giaNhap});
                }

                maNguyenLieuField.setText("");
                tenNguyenLieuField.setText("");
                giaNhapField.setText("");
                soLuongField.setText("");
                nhaCungCapComboBox.setEnabled(false);
                nguyenLieuTable.clearSelection();
                cartTable.clearSelection();

            }
        });


        // nút cập nhật
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn chi tiết phiếu nhập cần cập nhật!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String soLuongMoi = soLuongField.getText().trim();
                if (soLuongMoi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Số lượng không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!soLuongMoi.matches("\\d+")){
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (Integer.parseInt(soLuongMoi) == 0){
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nguyên liệu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        cartTableModel.removeRow(selectedRow);
                        if (cartTableModel.getRowCount() <= 0){
                            nhaCungCapComboBox.setEnabled(true);
                        }
                        nguyenLieuTable.clearSelection();
                        cartTable.clearSelection();
                        return;
                    } else {
                        return;
                    }
                }

                cartTableModel.setValueAt(soLuongMoi, selectedRow, 2);
                maNguyenLieuField.setText("");
                tenNguyenLieuField.setText("");
                soLuongField.setText("");
                giaNhapField.setText("");
                nguyenLieuTable.clearSelection();
                cartTable.clearSelection();
            }
        });

        // nút xóa
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn chi tiết phiếu nhập cần xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nguyên liệu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    cartTableModel.removeRow(selectedRow);
                    if (cartTableModel.getRowCount() <= 0){
                        nhaCungCapComboBox.setEnabled(true);
                    }
                    nguyenLieuTable.clearSelection();
                    cartTable.clearSelection();
                }
            }
        });
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