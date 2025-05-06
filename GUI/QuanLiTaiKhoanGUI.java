package GUI;

import DAO.TaiKhoanDAO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import Custom.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.TabExpander;

import com.mysql.cj.xdevapi.Table;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;import java.text.ParseException;
import java.text.SimpleDateFormat;

public class QuanLiTaiKhoanGUI extends RoundedPanel {
    private DefaultTableModel tableModel = new DefaultTableModel();
    private static TaiKhoanBUS tk = new TaiKhoanBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    public QuanLiTaiKhoanGUI(TaiKhoanDTO taiKhoanDTO) {
        super(50, 50, Color.decode("#F5ECE0")); // RoundedPanel with corner radius 50px
        this.setLayout(new BorderLayout());

        // Title
        MyLabel labelChamCong = new MyLabel("Quản lí tài khoản", 24f, "Bold");
        labelChamCong.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        labelChamCong.setPreferredSize(new Dimension(getWidth(), 70)); // Padding

        this.add(labelChamCong, BorderLayout.NORTH);

        // Add spacing between title and content
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(0, 15)); // Spacer
        gapPanel.setOpaque(true); // Ensure it's not transparent
        gapPanel.setBackground(Color.decode("#F5ECE0"));
        this.add(gapPanel, BorderLayout.CENTER);
        
        // Panel to hold table content
        JPanel nvtablePanel = new JPanel();
        nvtablePanel.setBackground(Color.WHITE);
        nvtablePanel.setLayout(new BorderLayout());
        nvtablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around content
        nvtablePanel.setPreferredSize(new Dimension(0, 400)); // Maintain consistent height

        // Control panel for search field and button
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE); // Set background to white
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search field
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập tên tài khoản để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tk.searchTableData(tableModel, searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tk.searchTableData(tableModel, searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                tk.searchTableData(tableModel, searchField.getText());
            }
        });

        // Create an icon label
        JLabel searchIcon = new JLabel(); // Replace with the actual path to the icon
        ImageIcon search = new ImageIcon("Resources\\Image\\MagnifyingGlass.png");
        Image scaledIcon = search.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        searchIcon.setIcon(new ImageIcon(scaledIcon));

        // Add search field and icon to a panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Set horizontal gap to 0
        searchPanel.setBackground(Color.WHITE); // Set background to white
        searchPanel.setOpaque(true); // Ensure it's not transparent

        searchPanel.add(searchIcon); // Add the search icon
        searchPanel.add(searchField); // Add the search field

        // Add the search panel to the control panel
        if(taiKhoanDTO.getVaiTro().equals("Admin"))
        {controlPanel.add(searchPanel, BorderLayout.WEST);}
        
        //button image
        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon =Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);

        // "Edit Employee" button
        MyButton editAccountButton = new MyButton("Sửa tài khoản", editIcon);
        editAccountButton.setPreferredSize(new Dimension(180, 40));
        editAccountButton.setBackground(Color.decode("#EC5228"));
        editAccountButton.setForeground(Color.WHITE);
        editAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editAccountButton.setFocusPainted(false);
        editAccountButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editAccountButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                editAccountButton.setBackground(Color.decode("#EC5228"));
            }
        });

        // "Add Employee" button
        MyButton addAccountButton = new MyButton("Thêm tài khoản",addIcon);
        addAccountButton.setPreferredSize(new Dimension(180, 40));
        addAccountButton.setBackground(Color.decode("#EC5228"));
        addAccountButton.setForeground(Color.WHITE);
        addAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addAccountButton.setFocusPainted(false);
        addAccountButton.addActionListener(e -> {
            addAccountButton.setEnabled(false);
            FormThemTaiKhoan(addAccountButton);
        });
        addAccountButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addAccountButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                addAccountButton.setBackground(Color.decode("#EC5228"));
            }
        });
        //Them nut xoa tai khoan
        MyButton deleteAccountButton = new MyButton("Xóa tài khoản",deleteIcon);
        deleteAccountButton.setPreferredSize(new Dimension(180, 40));
        deleteAccountButton.setBackground(Color.decode("#EC5228"));
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteAccountButton.setFocusPainted(false);
        //Button effect
        deleteAccountButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteAccountButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteAccountButton.setBackground(Color.decode("#EC5228"));
            }
        });
        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Horizontal gap = 10
        buttonPanel.setBackground(Color.WHITE);

        // Add buttons to the panel
        if(taiKhoanDTO.getVaiTro().equals("Admin")){
            buttonPanel.add(addAccountButton);
            buttonPanel.add(editAccountButton);
            buttonPanel.add(deleteAccountButton);
        }
        else{
            buttonPanel.add(editAccountButton);
        }

        // Add the button panel to the control panel
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        nvtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Panel containing the table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(true); // Ensure it's not transparent
        // Table content
        tableModel.setColumnIdentifiers(new Object[]{
                "Tên đăng nhập", "Mật khẩu", "Vai trò", "Trạng thái", "Ngày tạo"
        });
        TaiKhoanDAO t = new TaiKhoanDAO();
        if(taiKhoanDTO.getVaiTro().equals("Admin"))
        {
            ArrayList<TaiKhoanDTO> list = new ArrayList<>();
            list = t.getdata();
            for(TaiKhoanDTO tk : list){
                tableModel.addRow(new Object[]{tk.getTenDangNhap(),tk.getMatKhau(),tk.getVaiTro(),tk.getTrangThai(),tk.getNgayTao()});
            }
        }
        else{
            tableModel.addRow(new Object[]{taiKhoanDTO.getTenDangNhap(),taiKhoanDTO.getMatKhau(),taiKhoanDTO.getVaiTro(),taiKhoanDTO.getTrangThai(),taiKhoanDTO.getNgayTao()});
        }
        JTable table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false); // Remove grid lines
        table.setIntercellSpacing(new Dimension(0, 0)); // No cell spacing
        table.setFocusable(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder()); // Remove border for header

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for scroll pane
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE); // Đặt nền Viewport

        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());


        // Add tableContainer to nvtablePanel
        nvtablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add nvtablePanel to main GUI panel
        this.add(nvtablePanel, BorderLayout.SOUTH);

        //mouse listener cho bang
        
        
        //Action cho nut delete
        deleteAccountButton.addActionListener(e->{
            int Select = table.getSelectedRow();
            if(Select!=-1){
                String tenDangNhap = tableModel.getValueAt(Select, 0).toString();
                FormXoaTaiKhoan(tenDangNhap);

            }
            else{
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        //Action cho nút edit 
        editAccountButton.addActionListener(e->{
            editAccountButton.setEnabled(false);
            int Select = table.getSelectedRow();
            if(Select!=-1){
                String tenDangNhap = tableModel.getValueAt(Select, 0).toString();
                FormSuaTaiKhoan(editAccountButton, tenDangNhap,taiKhoanDTO);
            }
            else{
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                editAccountButton.setEnabled(true);
            }
        });
    }
        
        
    private void FormThemTaiKhoan(JButton addAccountButton) {
        JFrame formThemTK = new JFrame("Thêm Tài Khoản");
        formThemTK.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Set the close operation correctly
        formThemTK.setSize(800, 600); // Set the window size
        formThemTK.setLayout(new BorderLayout()); // Set the layout

        // Header
        RoundedPanel themNVHeader = new RoundedPanel(30, 30, Color.WHITE);
        themNVHeader.setLayout(new BorderLayout());
        JLabel themNVTitle = new JLabel("Thêm Tài Khoản", SwingConstants.CENTER);
        themNVTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themNVTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themNVHeader.add(themNVTitle, BorderLayout.CENTER);
        formThemTK.add(themNVHeader, BorderLayout.NORTH);

        /*
        Sử dụng GridLayout(9,3,10,10) để mỗi dòng có:
            - Cột 1: Label mô tả field.
            - Cột 2: Ô nhập liệu (hoặc combobox).
            - Cột 3: Error icon (hoặc JLabel trống nếu không cần hiển thị lỗi).
        */
        RoundedPanel themNVCenter = new RoundedPanel(30, 30, Color.WHITE);
        themNVCenter.setLayout(new GridLayout(6, 3, 10, 10));
        themNVCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // ----- Dòng 1: Mã nhân viên (read-only, không cần kiểm tra nên không có icon) -----
        JLabel maNVLabel = new JLabel("Mã nhân viên:");
        JTextField maNVField = new JTextField();
        maNVField.setBackground(Color.LIGHT_GRAY);
        maNVField.setEditable(false);
        JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new GridLayout(2,2,10,10));
        browsePanel.setBackground(Color.WHITE);
        RoundedButton Browse = new RoundedButton("Browse", 20, 20);
        JLabel emptylabel1 = new JLabel();
        emptylabel1.setPreferredSize(new Dimension(60, 60));
        emptylabel1.setIcon(new ImageIcon(scaledImage));
        emptylabel1.setForeground(Color.RED);
        emptylabel1.setVisible(false); // ẩn ban đầu
        emptylabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JLabel emptylabel2 = new JLabel();
        browsePanel.add(emptylabel1);
        browsePanel.add(emptylabel2);
        browsePanel.add(Browse);
        Browse.setPreferredSize(new Dimension(130, 30));
        Browse.setBackground(Color.decode("#07b6f0"));
        Browse.setForeground(Color.WHITE);
        Browse.setFont(new Font("Segoe UI", Font.BOLD, 14));
        Browse.setFocusPainted(false);
        Browse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Browse.setBackground(Color.decode("#0095c7"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                Browse.setBackground(Color.decode("#07b6f0"));
            }
        });
        themNVCenter.add(maNVLabel);
        themNVCenter.add(maNVField);
        themNVCenter.add(browsePanel);

        // ----- Dòng 2: Tên đăng nhập -----
        JLabel tenDNLabel = new JLabel("Tên đăng nhập:");
        JTextField tenDNField = new JTextField();
        // Tạo icon lỗi cho tên tài khoản
        JLabel nameErrorLabel = new JLabel();
        nameErrorLabel.setPreferredSize(new Dimension(60, 60));
        nameErrorLabel.setIcon(new ImageIcon(scaledImage));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setVisible(false); // ẩn ban đầu
        nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(tenDNLabel);
        themNVCenter.add(tenDNField);
        themNVCenter.add(nameErrorLabel);

        Browse.addActionListener(e -> {
            Browse.setEnabled(false);
            TableNV(Browse,maNVField,tenDNField);
        });

        // ----- Dòng 3: Mật khẩu -----
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JTextField passwordField = new JTextField("12345@");
        JLabel passwordErrorLabel = new JLabel();
        passwordErrorLabel.setPreferredSize(new Dimension(60, 60));
        passwordErrorLabel.setIcon(new ImageIcon(scaledImage));
        passwordErrorLabel.setForeground(Color.RED);
        passwordErrorLabel.setVisible(false);
        passwordErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(passwordLabel);
        themNVCenter.add(passwordField);
        themNVCenter.add(passwordErrorLabel);

        // ----- Dòng 4: Ngày tạo -----
        JLabel ngayTaoLabel = new JLabel("Ngày tạo (dd/MM/yyyy):");
        java.util.Date Date = new java.util.Date();
        Date sqldate = new Date(Date.getTime());
        JTextField ngayTaoField = new JTextField(sqldate.toString());
        ngayTaoField.setBackground(Color.lightGray);
        ngayTaoField.setEditable(false);
        JLabel ngayTaoErrorLabel = new JLabel();
        ngayTaoErrorLabel.setPreferredSize(new Dimension(60, 60));
        ngayTaoErrorLabel.setIcon(new ImageIcon(scaledImage));
        ngayTaoErrorLabel.setForeground(Color.RED);
        ngayTaoErrorLabel.setVisible(false);
        ngayTaoErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(ngayTaoLabel);
        themNVCenter.add(ngayTaoField);
        themNVCenter.add(ngayTaoErrorLabel);

        // ----- Dòng 5: Trạng thái -----
        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JComboBox<String> trangThaiBox = new JComboBox<>(new String[]{"Active", "Inactive"});
        JLabel emptyLabel4 = new JLabel();
        emptyLabel4.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(trangThaiLabel);
        themNVCenter.add(trangThaiBox);
        themNVCenter.add(emptyLabel4);

        //----- Dòng 6: Vai trò -----
        // JLabel vaiTroLabel = new JLabel("Vai trò:");
        // JComboBox<String> vaiTroBox = new JComboBox<>(new String[]{"Admin","Manager","StorageManager","Cashier","Accountant"});
        // JLabel emptyLabel5 = new JLabel();
        // emptyLabel5.setPreferredSize(new Dimension(60, 60));
        // themNVCenter.add(vaiTroLabel);
        // themNVCenter.add(vaiTroBox);
        // themNVCenter.add(emptyLabel5);

        formThemTK.add(themNVCenter, BorderLayout.CENTER);

        // Footer chứa nút Lưu
        RoundedPanel themTKFooter = new RoundedPanel(30, 30, Color.WHITE);
        themTKFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        //nút lưu
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#30f222"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        //nút hủy
        JButton cancelButton = new JButton("Hủy");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setBackground(Color.decode("#de2002"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        saveButton.addActionListener(e -> {
            // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
            nameErrorLabel.setVisible(false);
            emptylabel1.setVisible(false);
            passwordErrorLabel.setVisible(false);
            
            boolean isValid = true;

            //Validate Mã nhân viên: không được để trống
            String ID = maNVField.getText().trim();
            if(ID.isEmpty()){
                isValid = false;
                emptylabel1.setToolTipText("Mã nhân viên không được để trống.");
                emptylabel1.setVisible(true);
            }

            // Validate Tên đăng nhập: không được để trống
            String name = tenDNField.getText().trim();
            if(name.isEmpty()){
                isValid = false;
                nameErrorLabel.setToolTipText("Tên tài khoản không được để trống.");
                nameErrorLabel.setVisible(true);
            }
            else if (!name.matches("[a-zA-Z0-9]+")) { // Kiểm tra không chứa ký tự đặc biệt
                isValid = false;
                nameErrorLabel.setToolTipText("Tên tài khoản không được chứa ký tự đặc biệt.");
                nameErrorLabel.setVisible(true);
            }

            // Validate Password: không được để trống
            String pass = passwordField.getText().trim();
            if(pass.isEmpty()){
                isValid= false;
                passwordErrorLabel.setToolTipText("Mật khẩu không được để trống.");
                passwordErrorLabel.setVisible(true);
            } else if (pass.length() <= 5) { // Kiểm tra độ dài mật khẩu
                isValid = false;
                passwordErrorLabel.setToolTipText("Mật khẩu phải dài hơn 5 ký tự.");
                passwordErrorLabel.setVisible(true);
            }
            if(isValid) {
                try {
                    java.util.Date utilDate = new java.util.Date();
                    Date sqlDate = new Date(utilDate.getTime());
                    if(tk.checkduplicate(tenDNField.getText())){
                        tk.insertTaiKhoan(
                            tenDNField.getText(),
                            passwordField.getText(),
                            sqlDate,
                            trangThaiBox.getSelectedItem().toString(),
                            nhanVienBUS.getNhanVienById(maNVField.getText()).getTenChucVu(),
                            maNVField.getText()
                    );
                    tk.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemTK, "Thêm tài khoản thành công!");
                    formThemTK.dispose();}
                    else{
                        JOptionPane.showMessageDialog(null, "Tên đăng nhập này đã tồn tại vui lòng chọn tên đăng nhập khác", "Cảnh báobáo", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formThemTK, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
    });       
        cancelButton.addActionListener(e->{
            formThemTK.dispose();
        });
        themTKFooter.add(saveButton);
        themTKFooter.add(cancelButton);
        formThemTK.add(themTKFooter, BorderLayout.SOUTH);

        formThemTK.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addAccountButton.setEnabled(true);
            }
        });
        formThemTK.setVisible(true);
    }

    //Bảng nhân viên chưa có tài khoản
    private void TableNV(JButton browse,JTextField field,JTextField tenDangNhap){
        JFrame TableFrame = new JFrame();
        JButton SelectButton = new JButton("Select");
        JPanel footerpanel = new JPanel();
        footerpanel.setLayout(new FlowLayout());
        TableFrame.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        JTable nvTable = new JTable(model);
        nvTable.setRowHeight(35);
        nvTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nvTable.setIntercellSpacing(new Dimension(0, 0)); // No cell spacing
        nvTable.setFocusable(false);
        model.addColumn("Mã nhân viên");
        model.addColumn("Tên nhân viên");
        model.setColumnIdentifiers(new Object[]{"Mã nhân viên","Tên nhân viên"});
        ArrayList<NhanVienDTO> DS = new ArrayList<NhanVienDTO>();
        DS = tk.Kotendangnhap();
        for(NhanVienDTO nv : DS){
            model.addRow(new Object[]{nv.getMaNhanVien(),nv.getTenNhanVien()});
        }
        footerpanel.add(SelectButton);
        JScrollPane scrollPane = new JScrollPane(nvTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for scroll pane
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE); // Đặt nền Viewport
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        
        TableFrame.add(footerpanel,BorderLayout.SOUTH);
        TableFrame.add(scrollPane,BorderLayout.CENTER);
        TableFrame.setVisible(true);
        TableFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                browse.setEnabled(true);
            }
        });
        
        SelectButton.addActionListener(e -> {
            int i = nvTable.getSelectedRow(); // Lấy chỉ số hàng đã chọn tại thời điểm nhấn nút
            if (i != -1) { // Kiểm tra xem có hàng nào được chọn không
                field.setText(model.getValueAt(i, 0).toString());
                TableFrame.dispose();
                tenDangNhap.setText(model.getValueAt(i, 0).toString());
                
            } else {
                JOptionPane.showMessageDialog(TableFrame, "Vui lòng chọn một tài khoản!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        TableFrame.setSize(600,400);
        TableFrame.setLocationRelativeTo(null);
        TableFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    //FormXoaTaiKhoan
    public void FormXoaTaiKhoan(String tenDangNhap){

        int pane;
        pane = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa tài khoản này của nhân viên "+tk.timNV(tenDangNhap).getTenNhanVien()+" không", "Question", JOptionPane.YES_NO_OPTION);
        if(pane==JOptionPane.YES_NO_OPTION){
            tk.deleteTaiKhoan(tenDangNhap);
            tk.refreshTableData(tableModel);
            JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
        }
    }


    //FormSuaTaiKhoan
    private void FormSuaTaiKhoan(JButton editButton,String tenDangNhap,TaiKhoanDTO taiKhoanDTO) {
        TaiKhoanDTO acc= tk.timTK(tenDangNhap);
        JFrame formsuaTK = new JFrame("Sửa Tài Khoản");
        formsuaTK.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Set the close operation correctly
        formsuaTK.setSize(800, 600); // Set the window size
        formsuaTK.setLayout(new BorderLayout()); // Set the layout

        // Header
        RoundedPanel suaHeader = new RoundedPanel(30, 30, Color.WHITE);
        suaHeader.setLayout(new BorderLayout());
        JLabel suaTKTitle = new JLabel("Sửa Tài Khoản", SwingConstants.CENTER);
        suaTKTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        suaTKTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        suaHeader.add(suaTKTitle, BorderLayout.CENTER);
        formsuaTK.add(suaHeader, BorderLayout.NORTH);

        /*
        Sử dụng GridLayout(9,3,10,10) để mỗi dòng có:
            - Cột 1: Label mô tả field.
            - Cột 2: Ô nhập liệu (hoặc combobox).
            - Cột 3: Error icon (hoặc JLabel trống nếu không cần hiển thị lỗi).
        */
        RoundedPanel suaTKCenter = new RoundedPanel(30, 30, Color.WHITE);
        suaTKCenter.setLayout(new GridLayout(6, 3, 10, 10));
        suaTKCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Tạo icon warning dấu chấm thang
        ImageIcon icon = new ImageIcon("Resources\\Image\\WarningMark.png");
        Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        // ----- Dòng 1: Mã nhân viên (read-only, không cần kiểm tra nên không có icon) -----
        JLabel maNVLabel = new JLabel("Mã nhân viên:");
        JTextField maNVField = new JTextField();
        maNVField.setText(tk.timNV(tenDangNhap).getMaNhanVien());
        maNVField.setBackground(Color.LIGHT_GRAY);
        maNVField.setEditable(false);
        JLabel emptylabel2 = new JLabel();
        suaTKCenter.add(maNVLabel);
        suaTKCenter.add(maNVField);
        suaTKCenter.add(emptylabel2);

        // ----- Dòng 2: Tên đăng nhập -----
        JLabel tenDNLabel = new JLabel("Tên đăng nhập:");
        JTextField tenDNField = new JTextField();
        tenDNField.setText(acc.getTenDangNhap());
        // Tạo icon lỗi cho tên tài khoản
        JLabel nameErrorLabel = new JLabel();
        nameErrorLabel.setPreferredSize(new Dimension(60, 60));
        nameErrorLabel.setIcon(new ImageIcon(scaledImage));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setVisible(false); // ẩn ban đầu
        nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        suaTKCenter.add(tenDNLabel);
        suaTKCenter.add(tenDNField);
        suaTKCenter.add(nameErrorLabel);

        // ----- Dòng 3: Mật khẩu -----
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JTextField passwordField = new JTextField();
        passwordField.setText(acc.getMatKhau());
        JLabel passwordErrorLabel = new JLabel();
        passwordErrorLabel.setPreferredSize(new Dimension(60, 60));
        passwordErrorLabel.setIcon(new ImageIcon(scaledImage));
        passwordErrorLabel.setForeground(Color.RED);
        passwordErrorLabel.setVisible(false);
        passwordErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        suaTKCenter.add(passwordLabel);
        suaTKCenter.add(passwordField);
        suaTKCenter.add(passwordErrorLabel);

        // ----- Dòng 4: Ngày tạo -----
        JLabel ngayTaoLabel = new JLabel("Ngày tạo (yyy-mm-dd):");
        JTextField ngayTaoField = new JTextField();
        ngayTaoField.setText(acc.getNgayTao().toString());
        ngayTaoField.setBackground(Color.lightGray);
        ngayTaoField.setEditable(false);
        JLabel ngayTaoErrorLabel = new JLabel();
        ngayTaoErrorLabel.setPreferredSize(new Dimension(60, 60));
        ngayTaoErrorLabel.setIcon(new ImageIcon(scaledImage));
        ngayTaoErrorLabel.setForeground(Color.RED);
        ngayTaoErrorLabel.setVisible(false);
        ngayTaoErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        suaTKCenter.add(ngayTaoLabel);
        suaTKCenter.add(ngayTaoField);
        suaTKCenter.add(ngayTaoErrorLabel);

        // ----- Dòng 5: Trạng thái -----
        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JComboBox<String> trangThaiBox = new JComboBox<>(new String[]{"Active", "Inactive"});
        trangThaiBox.setSelectedItem(acc.getTrangThai());
        JLabel emptyLabel4 = new JLabel();
        emptyLabel4.setPreferredSize(new Dimension(60, 60));
        suaTKCenter.add(trangThaiLabel);
        suaTKCenter.add(trangThaiBox);
        suaTKCenter.add(emptyLabel4);
        if(!taiKhoanDTO.getVaiTro().equals("Admin")){
            trangThaiBox.setEnabled(false);
        }

        // ----- Dòng 6: Vai trò -----
        JLabel vaiTroLabel = new JLabel("Vai trò:");
        JComboBox<String> vaiTroBox = new JComboBox<>(new String[]{"Admin","Manager","StorageManager","Cashier","Accountant"});
        vaiTroBox.setSelectedItem(acc.getVaiTro());
        JLabel emptyLabel5 = new JLabel();
        emptyLabel5.setPreferredSize(new Dimension(60, 60));
        suaTKCenter.add(vaiTroLabel);
        suaTKCenter.add(vaiTroBox);
        suaTKCenter.add(emptyLabel5);
        if(!taiKhoanDTO.getVaiTro().equals("Admin")){
            vaiTroBox.setEnabled(false);
        }
        formsuaTK.add(suaTKCenter, BorderLayout.CENTER);

        // Footer chứa nút Lưu
        RoundedPanel suaTKfooter = new RoundedPanel(30, 30, Color.WHITE);
        suaTKfooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        //nút lưu
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#30f222"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        //nút hủy
        JButton cancelButton = new JButton("Hủy");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setBackground(Color.decode("#de2002"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        saveButton.addActionListener(e -> {
            // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
            nameErrorLabel.setVisible(false);
            passwordErrorLabel.setVisible(false);
            
            boolean isValid = true;
            // Validate Tên đăng nhập: không được để trống
            String name = tenDNField.getText().trim();
            if(name.isEmpty()){
                isValid = false;
                nameErrorLabel.setToolTipText("Tên đăng nhập không được để trống.");
                nameErrorLabel.setVisible(true);
            }
            else if (!name.matches("[a-zA-Z0-9]+")) { // Kiểm tra không chứa ký tự đặc biệt
                isValid = false;
                nameErrorLabel.setToolTipText("Tên đăng nhập không được chứa ký tự đặc biệt.");
                nameErrorLabel.setVisible(true);
            }

            // Validate Password: không được để trống
            String pass = passwordField.getText().trim();
            if(pass.isEmpty()){
                isValid= false;
                passwordErrorLabel.setToolTipText("Mật khẩu không được để trống.");
                passwordErrorLabel.setVisible(true);
            } else if (pass.length() < 5) { // Kiểm tra độ dài mật khẩu
                isValid = false;
                passwordErrorLabel.setToolTipText("Mật khẩu phải dài hơn 5 ký tự.");
                passwordErrorLabel.setVisible(true);
            }
            if(isValid) {
                try {
                    //Neu ten dang nhap ko doi thi ko can check duplicate
                    if(!acc.getTenDangNhap().equals(tenDNField.getText())){
                        //Check duplicate
                        if(tk.checkduplicate(tenDNField.getText())){
                            //Check vai tro de refresh table
                            if(taiKhoanDTO.getVaiTro().equals("Admin")){
                                tk.editTaiKhoan(tenDNField.getText(), passwordField.getText(), trangThaiBox.getSelectedItem().toString(), vaiTroBox.getSelectedItem().toString(),acc.getTenDangNhap());
                                tk.refreshTableData(tableModel);
                                JOptionPane.showMessageDialog(formsuaTK, "Cập nhật thành công!");
                                formsuaTK.dispose();
                            }
                            else{
                                tk.editTaiKhoan(tenDNField.getText(), passwordField.getText(), trangThaiBox.getSelectedItem().toString(), vaiTroBox.getSelectedItem().toString(),acc.getTenDangNhap());
                                tableModel.setRowCount(0);
                                tableModel.addRow(new Object[]{tenDNField.getText(),passwordField.getText(),vaiTroBox.getSelectedItem(),trangThaiBox.getSelectedItem(),ngayTaoField.getText()});
                                JOptionPane.showMessageDialog(formsuaTK, "Cập nhật thành công!");
                                formsuaTK.dispose();
                            }}
                        else{
                            isValid = false;
                                nameErrorLabel.setToolTipText("Tên đăng nhập bị trùng");
                                nameErrorLabel.setVisible(true);
                        }
                }else{      
                            //Check vai tro
                            if(taiKhoanDTO.getVaiTro().equals("Admin")){
                                tk.editTaiKhoan(tenDNField.getText(), passwordField.getText(), trangThaiBox.getSelectedItem().toString(), vaiTroBox.getSelectedItem().toString(),acc.getTenDangNhap());
                                tk.refreshTableData(tableModel);
                                JOptionPane.showMessageDialog(formsuaTK, "Cập nhật thành công!");
                                formsuaTK.dispose();
                            }
                            else{
                                tk.editTaiKhoan(tenDNField.getText(), passwordField.getText(), trangThaiBox.getSelectedItem().toString(), vaiTroBox.getSelectedItem().toString(),acc.getTenDangNhap());
                                tableModel.setRowCount(0);
                                tableModel.addRow(new Object[]{tenDNField.getText(),passwordField.getText(),vaiTroBox.getSelectedItem(),trangThaiBox.getSelectedItem(),ngayTaoField.getText()});
                                JOptionPane.showMessageDialog(formsuaTK, "Cập nhật thành công!");
                                formsuaTK.dispose();
                            }
                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formsuaTK, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
    });       
        cancelButton.addActionListener(e->{
            formsuaTK.dispose();
        });
        suaTKfooter.add(saveButton);
        suaTKfooter.add(cancelButton);
        formsuaTK.add(suaTKfooter, BorderLayout.SOUTH);

        formsuaTK.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                editButton.setEnabled(true);
            }
        });
        formsuaTK.setVisible(true);
    }
}