package GUI;

import BUS.*;
import Custom.*;
import DTO.*;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import static javax.swing.BoxLayout.Y_AXIS;

public class QuanLyNhapHangGUI extends RoundedPanel {
    public JTable nguyenLieuTable,cartTable, phieuNhapTable;
    public DefaultTableModel nguyenLieuTableModel, cartTableModel, phieuNhapTableModel;
    public MyLabel titleHeader, donViLabel;
    public MyButton addButton, editButton, deleteButton, confirmButton, viewDetailButton, hideButton, advancedSearchButton;
    public JPanel headerPanel, swicthButtonPanel, contentPanel, fieldBox1, fieldBox2, fieldBox3, fieldBox4, fieldBox5, buttonBox1, buttonBox2, buttonBox3, buttonBox4;
    public JPanel functionPanel;
    public JTextField maNguyenLieuField, tenNguyenLieuField, giaNhapField, soLuongField, filterInput;
    public JComboBox<String> nhaCungCapComboBox;
    public PhieuNhapBUS phieuNhapBUS;
    public ChiTietPhieuNhapBUS chiTietPhieuNhapBUS;
    public NhanVienBUS nhanVienBUS;
    public NhaCungCapBUS nhaCungCapBUS;
    public NguyenLieuBUS nguyenLieuBUS;
    public PhanPhoiBUS phanPhoiBUS;
    public JButton nhapHangButton, phieuNhapButton;
    public JScrollPane tableScrollPane;
    public String providerLockInValue;

    public QuanLyNhapHangGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        setLayout(new BorderLayout());
        initComponents();
        phieuNhapBUS = new PhieuNhapBUS();
        chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
        nhanVienBUS = new NhanVienBUS();
        nhaCungCapBUS = new NhaCungCapBUS();
        nguyenLieuBUS = new NguyenLieuBUS();
        phanPhoiBUS = new PhanPhoiBUS();
        providerLockInValue = "";
    }

    private void initComponents() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        titleHeader = new MyLabel("Quản lý nhập hàng", 24f, "Bold");
        titleHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
        headerPanel.add(titleHeader, BorderLayout.CENTER);
        headerPanel.setBackground(Color.decode("#F5ECE0"));

        nhapHangButton = new JButton("Nhập hàng");
        nhapHangButton.setFont(RobotoFont.getRobotoBold(12f));

        phieuNhapButton = new JButton("Danh sách phiếu nhập");
        phieuNhapButton.setFont(RobotoFont.getRobotoBold(12f));

        swicthButtonPanel = new JPanel();
        swicthButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        swicthButtonPanel.setBackground(Color.decode("#F5ECE0"));
        swicthButtonPanel.add(nhapHangButton);
        swicthButtonPanel.add(phieuNhapButton);
        headerPanel.add(swicthButtonPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        showNhapHangGUI();
    }

    private void showNhapHangGUI(){
        contentPanel.removeAll();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        titleHeader.setText("Quản lý nhập hàng");

        String[] columnNames = {"Mã NL", "Tên NL", "Nhà cung cấp", "Giá nhập", "Tồn kho", "Đơn vị"};
        nguyenLieuTableModel = new DefaultTableModel(columnNames, 0);
        nguyenLieuTable = new JTable(nguyenLieuTableModel);
        TableColumnModel columnModel = nguyenLieuTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(4).setPreferredWidth(110);
        columnModel.getColumn(4).setPreferredWidth(80);
        tableScrollPane = new JScrollPane(nguyenLieuTable);
        tableScrollPane.setBackground(Color.WHITE);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Danh sách nguyên liệu ( Nhà cung cấp: chưa chọn )"
        ));

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

        JPanel functionBox = new JPanel();
        functionBox.setBackground(Color.WHITE);
        functionBox.setLayout(new GridLayout(7, 2, 5, 0));
        functionBox.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        MyLabel maNguyenLieuLabel = new MyLabel("Mã nguyên liệu:", 14f, "Bold");
        maNguyenLieuField = new JTextField(SwingConstants.CENTER);
        maNguyenLieuField.setPreferredSize(new Dimension(150, 30));
        maNguyenLieuField.setEditable(false);
        fieldBox1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox1.setBackground(Color.WHITE);
        fieldBox1.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        fieldBox1.add(maNguyenLieuField);
        functionBox.add(maNguyenLieuLabel);
        functionBox.add(fieldBox1);

        MyLabel tenNguyenLieuLabel = new MyLabel("Tên nguyên liệu:", 14f, "Bold");
        tenNguyenLieuField = new JTextField();
        tenNguyenLieuField.setPreferredSize(new Dimension(150, 30));
        tenNguyenLieuField.setEditable(false);
        fieldBox2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox2.setBackground(Color.WHITE);
        fieldBox2.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        fieldBox2.add(tenNguyenLieuField);
        functionBox.add(tenNguyenLieuLabel);
        functionBox.add(fieldBox2);

        nhaCungCapBUS = new NhaCungCapBUS();
        ArrayList<NhaCungCapDTO> danhSachNhaCungCap = nhaCungCapBUS.getData();
        Vector<String> nhaCungCapData = new Vector<>();
        for (NhaCungCapDTO nhaCungCap: danhSachNhaCungCap){
            nhaCungCapData.add(nhaCungCap.getTenNhaCungCap());
        }

        MyLabel nhaCungCapLabel = new MyLabel("Nhà cung cấp:", 14f, "Bold");
        nhaCungCapComboBox = new JComboBox<>(nhaCungCapData);
        nhaCungCapComboBox.setPreferredSize(new Dimension(150, 30));
        fieldBox3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox3.setBackground(Color.WHITE);
        fieldBox3.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        fieldBox3.add(nhaCungCapComboBox);
        functionBox.add(nhaCungCapLabel);
        functionBox.add(fieldBox3);

        MyLabel giaNhapLabel = new MyLabel("Giá nhập:", 14f, "Bold");
        giaNhapField = new JTextField();
        giaNhapField.setPreferredSize(new Dimension(150, 30));
        giaNhapField.setEditable(false);
        fieldBox4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox4.setBackground(Color.WHITE);
        fieldBox4.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        fieldBox4.add(giaNhapField);
        functionBox.add(giaNhapLabel);
        functionBox.add(fieldBox4);

        MyLabel soLuongLabel = new MyLabel("Số lượng:", 14f, "Bold");
        soLuongLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        soLuongField = new JTextField();
        soLuongField.setPreferredSize(new Dimension(60, 30));
        soLuongField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        donViLabel = new MyLabel("", 14f, "Bold");
        fieldBox5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldBox5.setBackground(Color.WHITE);
        fieldBox5.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        fieldBox5.add(soLuongField);
        fieldBox5.add(donViLabel);
        functionBox.add(soLuongLabel);
        functionBox.add(fieldBox5);

        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 25, 20);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 20, 20);
        ImageIcon deleteIcon = Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 20, 20);
        ImageIcon confirmIcon = Utilities.loadAndResizeIcon("Resources\\Image\\Confirm.png", 20, 20);

        buttonBox1 = new JPanel();
        buttonBox1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonBox1.setBackground(Color.WHITE);
        addButton = new MyButton("Thêm vào phiếu", addIcon);
        addButton.setBackground(Color.LIGHT_GRAY);
        addButton.setPreferredSize(new Dimension(165, 40));
        buttonBox1.add(addButton);

        buttonBox2 = new JPanel();
        buttonBox2.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonBox2.setBackground(Color.WHITE);
        editButton = new MyButton("Cập nhật", editIcon);
        editButton.setBackground(Color.LIGHT_GRAY);
        editButton.setPreferredSize(new Dimension(165, 40));
        buttonBox2.add(editButton);

        buttonBox3 = new JPanel();
        buttonBox3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonBox3.setBackground(Color.WHITE);
        deleteButton = new MyButton("Xóa chi tiết", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(165, 40));
        buttonBox3.add(deleteButton);

        buttonBox4 = new JPanel();
        buttonBox4.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonBox4.setBackground(Color.WHITE);
        confirmButton = new MyButton("Tạo phiếu nhập", confirmIcon);
        confirmButton.setPreferredSize(new Dimension(165, 40));
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

        formatTableUI(nguyenLieuTable);
        formatTableUI(cartTable);
        loadTableActionListener();
        loadButtonActionListener();
        phieuNhapBUS = new PhieuNhapBUS();
        phieuNhapBUS.loadDataTableNguyenLieu(nguyenLieuTableModel);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void formatTableUI(JTable table){
        // format table
        table.setBackground(Color.WHITE);
        table.setFont(RobotoFont.getRobotoRegular(12f));
        table.setRowHeight(30);

        JTableHeader headerCart = table.getTableHeader();
        headerCart.setBackground(Color.WHITE);
        headerCart.setFont(RobotoFont.getRobotoBold(14f));

        DefaultTableCellRenderer centerRendererCart = new DefaultTableCellRenderer();
        centerRendererCart.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRendererCart);
        }

        DefaultTableCellRenderer headerRendererCart = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRendererCart.setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setDefaultRenderer(headerRendererCart);
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
                    String giaNhap = nguyenLieuTable.getValueAt(selectedRow, 3).toString();
                    String donVi = giaNhap.split("/")[1];
                    donViLabel.setText(donVi);

                    maNguyenLieuField.setText(maNguyenLieu);
                    tenNguyenLieuField.setText(tenNguyenLieu);
                    giaNhapField.setText(giaNhap);
                    soLuongField.setText("");
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
                    String donVi = giaNhap.split("/")[1];
                    donViLabel.setText(donVi);

                    maNguyenLieuField.setText(maNguyenLieu);
                    tenNguyenLieuField.setText(tenNguyenLieu);
                    soLuongField.setText(soLuong.split(" ")[0]);
                    giaNhapField.setText(giaNhap);
                }
            }
        });

    }

    public void loadButtonActionListener(){
        // nút nhập hàng
        nhapHangButton.addActionListener(_ -> showNhapHangGUI());

        // nút danh sách phiếu nhập
        phieuNhapButton.addActionListener(_ -> showPhieuNhapGUI());

        // comboBox nhà cung cấp
        nhaCungCapComboBox.addActionListener(_ -> lockInProvider());


        // nút thêm
        addButton.addActionListener(_ -> {
            String maNguyenLieu = maNguyenLieuField.getText().trim();
            String tenNguyenLieu = tenNguyenLieuField.getText().trim();
            String giaNhap = giaNhapField.getText().trim();
            String soLuong = soLuongField.getText().trim() + " " + donViLabel.getText();

            if (Objects.equals(providerLockInValue, "")){
                providerLockInValue = (String) nguyenLieuTable.getValueAt(nguyenLieuTable.getSelectedRow(), 2);;
            }


            if (maNguyenLieu.isEmpty() || tenNguyenLieu.isEmpty() || giaNhap.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!soLuong.split(" ")[0].matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Integer.parseInt(soLuong.split(" ")[0]) == 0){
                JOptionPane.showMessageDialog(null, "Số lượng phải là số hợp lệ (khác 0)!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int soLuongMoi = Integer.parseInt(soLuong.split(" ")[0]);
            boolean daTonTai = false;

            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                if (cartTableModel.getValueAt(i, 0).equals(maNguyenLieu)) {
                    int soLuongHienTai = Integer.parseInt(cartTableModel.getValueAt(i, 2).toString().split(" ")[0]);
                    cartTableModel.setValueAt(soLuongHienTai + soLuongMoi + " " + donViLabel.getText(), i, 2);
                    daTonTai = true;
                    break;
                }
            }

            if (!daTonTai) {
                cartTableModel.addRow(new Object[]{maNguyenLieu, tenNguyenLieu, soLuongMoi + " " + donViLabel.getText(), giaNhap});
            }

            lockInProvider(providerLockInValue);
            nhaCungCapComboBox.setEnabled(false);
            resetTableSelectionAndField();

        });


        // nút cập nhật
        editButton.addActionListener(_ -> {
            int selectedRow = cartTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn chi tiết phiếu nhập cần cập nhật!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String soLuongMoi = soLuongField.getText().trim().split(" ")[0];
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
                        phieuNhapBUS.loadDataTableNguyenLieu(nguyenLieuTableModel);
                        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.BLACK),
                                "Danh sách nguyên liệu ( Nhà cung cấp: chưa chọn )"
                        ));
                        providerLockInValue = "";
                    }
                    nguyenLieuTable.clearSelection();
                    cartTable.clearSelection();
                }
                return;
            }

            cartTableModel.setValueAt(soLuongMoi + " " + donViLabel.getText(), selectedRow, 2);
            resetTableSelectionAndField();
        });

        // nút xóa
        deleteButton.addActionListener(_ -> {
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
                    phieuNhapBUS.loadDataTableNguyenLieu(nguyenLieuTableModel);
                    tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Color.BLACK),
                            "Danh sách nguyên liệu ( Nhà cung cấp: chưa chọn )"
                    ));
                    providerLockInValue = "";
                }
                resetTableSelectionAndField();
            }
        });


        // nút xác nhận
        confirmButton.addActionListener(_ -> {
            int response = JOptionPane.showConfirmDialog(null,
                    "Bạn có chắc chắn muốn tạo phiếu nhập mới?",
                    "Xác nhận tạo phiếu nhập",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                try {
                    if (cartTable.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null,
                                "Phiếu nhập mới chưa có chi tiết nào!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
                    phieuNhap.setMaPhieuNhap(phieuNhapBUS.generateID());
                    phieuNhap.setNgayNhap(new Date());
                    phieuNhap.setMaNhanVien("NV001");
                    String tenNCC = (String) nhaCungCapComboBox.getSelectedItem();
                    phieuNhap.setMaNhaCungCap(nhaCungCapBUS.getDataByName(tenNCC).getMaNhaCungCap());
                    phieuNhap.setTrangThai(true);

                    ArrayList<ChiTietPhieuNhapDTO> chiTietList = new ArrayList<>();
                    double tongTien = 0.0;

                    for (int i = 0; i < cartTable.getRowCount(); i++) {
                        ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO();

                        String maNguyenLieu = (String) cartTable.getValueAt(i, 0); // Mã nguyên liệu
                        int soLuongNhap = Integer.parseInt(cartTable.getValueAt(i, 2).toString().split(" ")[0]); // Số lượng
                        double giaNhap = Double.parseDouble(cartTable.getValueAt(i, 3).toString().split("/")[0]); // Giá nhập

                        double thanhTien = giaNhap * soLuongNhap;

                        chiTiet.setMaPhieuNhap(phieuNhap.getMaPhieuNhap());
                        chiTiet.setMaNguyenLieu(maNguyenLieu);
                        chiTiet.setGiaNhap(giaNhap);
                        chiTiet.setSoLuongNhap(soLuongNhap);
                        chiTiet.setThanhTien(thanhTien);
                        chiTiet.setGhiChu("");
                        chiTiet.setTrangThai(true);

                        chiTietList.add(chiTiet);

                        tongTien += thanhTien;
                    }

                    phieuNhap.setTongTien(tongTien);

                    boolean success = phieuNhapBUS.add(phieuNhap);
                    if (success) {
                        for (ChiTietPhieuNhapDTO chiTiet : chiTietList) {
                            chiTietPhieuNhapBUS.add(chiTiet);
                        }

                        chiTietPhieuNhapBUS.closeConnectDB();

                        JOptionPane.showMessageDialog(null,
                                "Tạo phiếu nhập thành công! Mã phiếu nhập: " + phieuNhap.getMaPhieuNhap(),
                                "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);

                        cartTableModel.setRowCount(0);
                        resetTableSelectionAndField();
                        phieuNhapBUS.loadDataTableNguyenLieu(nguyenLieuTableModel);
                        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Color.BLACK),
                                "Danh sách nguyên liệu ( Nhà cung cấp: chưa chọn )"
                        ));
                        nhaCungCapComboBox.setEnabled(true);
                        providerLockInValue = "";

                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Lỗi khi tạo phiếu nhập!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Đã xảy ra lỗi: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }

    private void lockInProvider(){
        String selectedValue = (String) nhaCungCapComboBox.getSelectedItem();
        NhaCungCapDTO nhaCungCap = nhaCungCapBUS.getDataByName(selectedValue);

        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Danh sách nguyên liệu (" + nhaCungCap.getTenNhaCungCap() + ")"
        ));

        phieuNhapBUS.loadDataTableNguyenLieu(nguyenLieuTableModel, nhaCungCap.getMaNhaCungCap());
    }

    // dùng khi thực hiện thêm sản phẩm của nhà cung cấp nào đó
    private void lockInProvider(String tenNhaCungCap){
        NhaCungCapDTO nhaCungCap = nhaCungCapBUS.getDataByName(tenNhaCungCap);
        nhaCungCapComboBox.setSelectedItem(nhaCungCap.getTenNhaCungCap());
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Danh sách nguyên liệu (" + tenNhaCungCap + ")"
        ));

        phieuNhapBUS.loadDataTableNguyenLieu(nguyenLieuTableModel, nhaCungCap.getMaNhaCungCap());
    }



    private void resetTableSelectionAndField(){
        maNguyenLieuField.setText("");
        tenNguyenLieuField.setText("");
        soLuongField.setText("");
        giaNhapField.setText("");
        cartTable.clearSelection();
        nguyenLieuTable.clearSelection();
    }

    private void showPhieuNhapGUI(){
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        titleHeader.setText("Quản lý phiếu nhập");

        ImageIcon viewDetailIcon = Utilities.loadAndResizeIcon("Resources\\Image\\ViewDetail.png", 20, 20);
        viewDetailButton = new MyButton("Xem chi tiết", viewDetailIcon);
        viewDetailButton.setPreferredSize(new Dimension(165, 35));

        ImageIcon hideButtonIcon = Utilities.loadAndResizeIcon("Resources\\Image\\Hide.png", 20, 20);
        hideButton = new MyButton("Ẩn phiếu nhập", hideButtonIcon);
        hideButton.setPreferredSize(new Dimension(165, 35));

        // Basic search input
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        MyLabel filterLabel = new MyLabel("Tìm mã phiếu nhập:", 14f, "Bold");
        filterInput = new JTextField();
        filterInput.setPreferredSize(new Dimension(160,35));
        filterInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filterPanel.add(filterLabel);
        filterPanel.add(filterInput);

        // Advanced search button
        ImageIcon searchIcon = Utilities.loadAndResizeIcon("Resources\\Image\\MagnifyingGlass.png", 20, 20);
        advancedSearchButton = new MyButton("Tìm kiếm nâng cao", searchIcon);
        advancedSearchButton.setPreferredSize(new Dimension(165, 35));
        advancedSearchButton.setFont(RobotoFont.getRobotoBold(12f));
        filterPanel.add(advancedSearchButton);

        functionPanel = new JPanel();
        functionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        functionPanel.setBackground(Color.WHITE);
        functionPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
        functionPanel.add(filterPanel);
        functionPanel.add(viewDetailButton);
        functionPanel.add(hideButton);

        String[] columnNames = {"Mã phiếu nhập", "Ngày nhập", "Nhân viên nhập", "Nhà cung cấp", "Tổng tiền"};
        phieuNhapTableModel = new DefaultTableModel(columnNames, 0);
        phieuNhapTable = new JTable(phieuNhapTableModel);
        TableColumnModel tableColumnModel = phieuNhapTable.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(80);  // Mã phiếu
        tableColumnModel.getColumn(1).setPreferredWidth(120); // Ngày nhập
        tableColumnModel.getColumn(2).setPreferredWidth(200); // Nhân viên nhập
        tableColumnModel.getColumn(3).setPreferredWidth(200); // Nhà cung cấp
        tableColumnModel.getColumn(4).setPreferredWidth(120);  // Tổng tiền
        JScrollPane phieuNhapScrollPane = new JScrollPane(phieuNhapTable);
        phieuNhapScrollPane.setBackground(Color.WHITE);

        contentPanel.add(functionPanel, BorderLayout.NORTH);
        contentPanel.add(phieuNhapScrollPane, BorderLayout.CENTER);

        phieuNhapBUS.loadDataPhieuNhap(phieuNhapTableModel);
        formatTableUI(phieuNhapTable);
        loadPhieuNhapButtonActionListener();

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void loadPhieuNhapButtonActionListener() {
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
                phieuNhapTableModel.setRowCount(0);
                for (PhieuNhapDTO phieuNhap : phieuNhapBUS.getData()) {
                    String maPhieuNhap = phieuNhap.getMaPhieuNhap();
                    if (filterText.isEmpty() || maPhieuNhap.contains(filterText)) {
                        Vector<String> rowData = new Vector<>();
                        String maNhanVien = phieuNhap.getMaNhanVien();
                        String maNCC = phieuNhap.getMaNhaCungCap();
                        rowData.add(phieuNhap.getMaPhieuNhap());
                        rowData.add(dateFormat.format(phieuNhap.getNgayNhap()));
                        rowData.add(maNhanVien + " - " + nhanVienBUS.getDataById(maNhanVien).getTenNV());
                        rowData.add(maNCC + " - " + nhaCungCapBUS.getDataById(maNCC).getTenNhaCungCap());
                        rowData.add(String.valueOf(phieuNhap.getTongTien()));
                        phieuNhapTableModel.addRow(rowData);
                    }
                }
            }
        });

        advancedSearchButton.addActionListener(e -> showAdvancedSearchDialog());

        viewDetailButton.addActionListener(_ ->{
            int selectedRow = phieuNhapTable.getSelectedRow();
            if (selectedRow == -1 ){
                JOptionPane.showMessageDialog(null, "Error", "Vui lòng chọn phiếu nhập cần ẩn !", JOptionPane.ERROR_MESSAGE);
                return;
            }
            showDetailForm((String) phieuNhapTable.getValueAt(selectedRow, 0));
        });

        hideButton.addActionListener(_ ->{
            int selectedRow = phieuNhapTable.getSelectedRow();
            if (selectedRow == -1 ){
                JOptionPane.showMessageDialog(null, "Error", "Vui lòng chọn phiếu nhập cần ẩn !", JOptionPane.ERROR_MESSAGE);
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn ẩn phiếu nhập này chứ ?", "Xác nhận ẩn", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = phieuNhapBUS.hide((String) phieuNhapTable.getValueAt(selectedRow, 0));
                if (result){
                    JOptionPane.showMessageDialog(null,  "Ẩn phiếu nhập thành công !", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
                    phieuNhapBUS.loadDataPhieuNhap(phieuNhapTableModel);
                } else {
                    JOptionPane.showMessageDialog(null,  "Đã có lỗi khi ẩn phiếu nhập !", "Thông báo",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showDetailForm(String maPhieuNhap){
        nguyenLieuBUS = new NguyenLieuBUS();
        PhieuNhapDTO phieuNhap = phieuNhapBUS.getDataById(maPhieuNhap);
        NhanVienDTO nhanVienNhap = nhanVienBUS.getDataById(phieuNhap.getMaNhanVien());
        NhaCungCapDTO nhaCungCap = nhaCungCapBUS.getDataById(phieuNhap.getMaNhaCungCap());
        ArrayList<ChiTietPhieuNhapDTO> danhSachChiTietPhieuNhap;
        danhSachChiTietPhieuNhap = chiTietPhieuNhapBUS.getDataById(maPhieuNhap);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JFrame detailForm = new JFrame();
        detailForm.setSize(550,600);
        detailForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailForm.getContentPane().setBackground(Color.WHITE);
        detailForm.setLocationRelativeTo(null);
        detailForm.setLayout(new BorderLayout());

        MyLabel detailFormHeader = new MyLabel("Chi Tiết Phiếu Nhập", 24f, "Bold");
        detailFormHeader.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JPanel formContentPanel = new JPanel();
        formContentPanel.setLayout(new BorderLayout());
        formContentPanel.setBackground(Color.WHITE);

        // information panel
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout( new BoxLayout(informationPanel, Y_AXIS));
        informationPanel.setBackground(Color.WHITE);

        JPanel maPhieuNhapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maPhieuNhapPanel.setBackground(Color.WHITE);
        maPhieuNhapPanel.add(new MyLabel("Mã phiếu nhập: ", 11f, "Bold"));
        JTextField maPhieuNhapField = new JTextField(maPhieuNhap, SwingConstants.CENTER);
        maPhieuNhapField.setEditable(false);
        maPhieuNhapField.setPreferredSize(new Dimension(220, 30));
        maPhieuNhapPanel.add(maPhieuNhapField);

        JPanel ngayNhapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ngayNhapPanel.setBackground(Color.WHITE);
        ngayNhapPanel.add(new MyLabel("Ngày nhập: ", 11f, "Bold"));
        JTextField ngayNhapField = new JTextField(sdf.format(phieuNhap.getNgayNhap()));
        ngayNhapField.setEditable(false);
        ngayNhapField.setPreferredSize(new Dimension(220, 30));
        ngayNhapPanel.add(ngayNhapField);

        JPanel nhanVienNhapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nhanVienNhapPanel.setBackground(Color.WHITE);
        nhanVienNhapPanel.add(new MyLabel("Nhân viên nhập: ", 11f, "Bold"));
        JTextField nhanVienNhapField = new JTextField(nhanVienNhap.getTenNV() + " (Mã nhân viên:  " + nhanVienNhap.getMaNV() + ")" );
        nhanVienNhapField.setEditable(false);
        nhanVienNhapField.setPreferredSize(new Dimension(220, 30));
        nhanVienNhapPanel.add(nhanVienNhapField);

        JPanel nhaCungCapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nhaCungCapPanel.setBackground(Color.WHITE);
        nhaCungCapPanel.add(new MyLabel("Nhà cung cấp: ", 11f, "Bold"));
        JTextField nhaCungCapField = new JTextField(nhaCungCap.getTenNhaCungCap() + " (" + nhaCungCap.getMaNhaCungCap() + ")" );
        nhaCungCapField.setEditable(false);
        nhaCungCapField.setPreferredSize(new Dimension(220, 30));
        nhaCungCapPanel.add(nhaCungCapField);

        // add information panel
        informationPanel.add(maPhieuNhapPanel);
        informationPanel.add(ngayNhapPanel);
        informationPanel.add(nhanVienNhapPanel);
        informationPanel.add(nhaCungCapPanel);
        formContentPanel.add(informationPanel, BorderLayout.NORTH);

        // table panel
        String[] columnNames = {"Mã NL"," Tên NL", "Số lượng nhập", "Giá nhập", "Thành tiền"};
        DefaultTableModel chiTietPhieuNhapTableModel = new DefaultTableModel(columnNames, 0);
        JTable chiTietPhieuNhapTable = new JTable(chiTietPhieuNhapTableModel);
        TableColumnModel tableColumnModel = chiTietPhieuNhapTable.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(80);
        tableColumnModel.getColumn(1).setPreferredWidth(170);
        tableColumnModel.getColumn(2).setPreferredWidth(130);
        tableColumnModel.getColumn(3).setPreferredWidth(100);
        tableColumnModel.getColumn(4).setPreferredWidth(120);

        for (ChiTietPhieuNhapDTO chiTietPhieuNhap: danhSachChiTietPhieuNhap){
            NguyenLieuDTO nguyenLieu = nguyenLieuBUS.getDataById(chiTietPhieuNhap.getMaNguyenLieu());
            Vector<String> rowData = new Vector<>();
            rowData.add(nguyenLieu.getMaNguyenLieu());
            rowData.add(nguyenLieu.getTenNguyenLieu());
            rowData.add(chiTietPhieuNhap.getSoLuongNhap() + " " + nguyenLieu.getDonViDo());
            rowData.add(phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getGiaNhap() + "/" + nguyenLieu.getDonViDo());
            rowData.add(String.valueOf(chiTietPhieuNhap.getSoLuongNhap() * phanPhoiBUS.getDataByIdSub(nguyenLieu.getMaNguyenLieu()).getGiaNhap()));
            chiTietPhieuNhapTableModel.addRow(rowData);
        }

        formatTableUI(chiTietPhieuNhapTable);

        JScrollPane chiTietPhieuNhapScrollPane = new JScrollPane();
        chiTietPhieuNhapScrollPane.setBackground(Color.WHITE);
        chiTietPhieuNhapScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        chiTietPhieuNhapScrollPane.add(new MyLabel("Danh sách nguyên liệu nhập", 20f, "Bold"));
        chiTietPhieuNhapScrollPane.setViewportView(chiTietPhieuNhapTable);

        // add scrollPane
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(chiTietPhieuNhapScrollPane, BorderLayout.CENTER);

        MyLabel tongTienLabel = new MyLabel("Tổng tiền: " + phieuNhap.getTongTien(), 14f, "Bold");
        tongTienLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,20));
        tongTienLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tablePanel.add(tongTienLabel, BorderLayout.SOUTH);

        // add table panel
        formContentPanel.add(tablePanel, BorderLayout.CENTER);

        // footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        MyButton closeButton = new MyButton("Đóng");
        closeButton.addActionListener(_-> detailForm.dispose());
        footerPanel.add(closeButton);

        // add components
        detailForm.add(detailFormHeader, BorderLayout.NORTH);
        detailForm.add(formContentPanel, BorderLayout.CENTER);
        detailForm.add(footerPanel, BorderLayout.SOUTH);

        detailForm.setVisible(true);
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


        // mã phiếu field
        JPanel maPhieuNhapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maPhieuNhapPanel.setBackground(Color.WHITE);
        MyLabel maPhieuNhapLabel = new MyLabel("Mã phiếu nhập:", 14f, "Bold");
        JTextField maPhieuNhapField = new JTextField();
        maPhieuNhapField.setPreferredSize(new Dimension(180, 30));
        maPhieuNhapField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        maPhieuNhapPanel.add(maPhieuNhapLabel);
        maPhieuNhapPanel.add(maPhieuNhapField);
        fieldsPanel.add(maPhieuNhapPanel);

        // nhân viên field
        JPanel nhanVienPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nhanVienPanel.setBackground(Color.WHITE);
        MyLabel nhanVienLabel = new MyLabel("Nhân viên nhập:", 14f, "Bold");

        ArrayList<NhanVienDTO> danhSachNhanVien = nhanVienBUS.getData();
        Vector<String> nhanVienData = new Vector<>();
        for (NhanVienDTO nhanVien: danhSachNhanVien){
            nhanVienData.add(nhanVien.getMaNV() + " - " + nhanVien.getTenNV());
        }
        JComboBox<String> nhanVienCombo = new JComboBox<>(nhanVienData);
        nhanVienCombo.setPreferredSize(new Dimension(180,30 ));
        nhanVienPanel.add(nhanVienLabel);
        nhanVienPanel.add(nhanVienCombo);
        fieldsPanel.add(nhanVienPanel);

        // nhà cung cấp field
        JPanel nhaCungCapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nhaCungCapPanel.setBackground(Color.WHITE);
        MyLabel nhaCungCapLabel = new MyLabel("Nhà cung cấp:", 14f, "Bold");

        ArrayList<NhaCungCapDTO> danhSachNCC = nhaCungCapBUS.getData();
        Vector<String> nhanCungCapData = new Vector<>();
        for (NhaCungCapDTO nhaCungCap: danhSachNCC){
            nhanCungCapData.add(nhaCungCap.getMaNhaCungCap() + " - " + nhaCungCap.getTenNhaCungCap());
        }
        JComboBox<String> nhaCungCapCombo = new JComboBox<>(nhanCungCapData);
        nhaCungCapPanel.add(nhaCungCapLabel);
        nhaCungCapPanel.add(nhaCungCapCombo);
        fieldsPanel.add(nhaCungCapPanel);

        // ngày nhập field
        JPanel ngayNhapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ngayNhapPanel.setBackground(Color.WHITE);
        JPanel ngayNhapPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ngayNhapPanel2.setBackground(Color.WHITE);

        JDateChooser startDate = new JDateChooser();
        JDateChooser endDate = new JDateChooser();
        startDate.setPreferredSize(new Dimension(180, 30));
        startDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        endDate.setPreferredSize(new Dimension(180,30));
        endDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ngayNhapPanel.add(new MyLabel("Từ ngày: ", 14f, "Bold"));
        ngayNhapPanel.add(startDate);
        ngayNhapPanel2.add(new MyLabel("Đến ngày: ", 14f, "Bold"));
        ngayNhapPanel2.add(endDate);

        fieldsPanel.add(ngayNhapPanel);
        fieldsPanel.add(ngayNhapPanel2);

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
            String maPhieuNhap = maPhieuNhapField.getText().trim();
            String maNhanVien = String.valueOf(nhanVienCombo.getSelectedItem()).split(" - ")[0];
            String maNhaCungCap = String.valueOf(nhaCungCapCombo.getSelectedItem()).split(" - ")[0];
            java.util.Date startDateValue = startDate.getDate();
            java.util.Date endDateValue = endDate.getDate();

            ArrayList<PhieuNhapDTO> filteredList = phieuNhapBUS.advancedSearch(
                    maPhieuNhap.isEmpty() ? null : maPhieuNhap,
                    maNhanVien.equals("Chọn nhân viên") ? null : maNhanVien,
                    maNhaCungCap.equals("Chọn nhà cung cấp") ? null : maNhaCungCap,
                    startDateValue,
                    endDateValue
            );

            // Update table with filtered data
            phieuNhapTableModel.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (PhieuNhapDTO phieuNhap : filteredList) {
                String maNV = phieuNhap.getMaNhanVien();
                String maNCC = phieuNhap.getMaNhaCungCap();
                phieuNhapTableModel.addRow(new Object[]{
                    phieuNhap.getMaPhieuNhap(),
                    dateFormat.format(phieuNhap.getNgayNhap()),
                    maNV + " - " + nhanVienBUS.getDataById(maNV).getTenNV(),
                    maNCC + " - " + nhaCungCapBUS.getDataById(maNCC).getTenNhaCungCap(),
                    String.valueOf(phieuNhap.getTongTien())
                });
            }
            dialog.dispose();
        });
        dialog.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testGUI = new JFrame();
            testGUI.setSize(1000,550);
            testGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testGUI.setLocationRelativeTo(null);
            testGUI.add(new QuanLyNhapHangGUI());
            testGUI.setVisible(true);
        });
    }
}