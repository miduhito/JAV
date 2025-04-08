package GUI;

import DAO.TaiKhoanDAO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import Custom.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.TabExpander;

import com.mysql.cj.xdevapi.Table;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class QuanLiTaiKhoanGUI extends RoundedPanel {

    public QuanLiTaiKhoanGUI() {
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
        searchField.setToolTipText("Nhập tên nhân viên để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

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
        controlPanel.add(searchPanel, BorderLayout.WEST);
        
        //button image
        ImageIcon addIcon = Utilities.loadAndResizeIcon("Resources\\Image\\AddIcon.png", 30, 30);
        ImageIcon editIcon = Utilities.loadAndResizeIcon("Resources\\Image\\EditIcon.png", 30, 30);
        ImageIcon deleteIcon =Utilities.loadAndResizeIcon("Resources\\Image\\DeleteIcon.png", 30, 30);

        // "Edit Employee" button
        RoundedButton editEmployeeButton = new RoundedButton("Sửa tài khoản", 20, 20);
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
        RoundedButton deleteEmployeeButton = new RoundedButton("Xóa tài khoản", 20, 20);
        deleteEmployeeButton.setPreferredSize(new Dimension(180, 40));
        deleteEmployeeButton.setBackground(Color.decode("#EC5228"));
        deleteEmployeeButton.setForeground(Color.WHITE);
        deleteEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteEmployeeButton.setFocusPainted(false);
        //Button effect
        deleteEmployeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteEmployeeButton.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                deleteEmployeeButton.setBackground(Color.decode("#EC5228"));
            }
        });
        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Horizontal gap = 10
        buttonPanel.setBackground(Color.WHITE);

        // Add buttons to the panel
        buttonPanel.add(addAccountButton);
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);

        // Add the button panel to the control panel
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        nvtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Panel containing the table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(true); // Ensure it's not transparent
        // Table content
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{
                "Tên đăng nhập", "Mật khẩu", "Vai trò", "Trạng thái", "Ngày tạo"
        });
        TaiKhoanDAO t = new TaiKhoanDAO();
        ArrayList<TaiKhoanDTO> list = new ArrayList<>();
        list = t.getdata();
        for(TaiKhoanDTO tk : list){
            tableModel.addRow(new Object[]{tk.getTenDangNhap(),tk.getMatKhau(),tk.getVaiTro(),tk.getTrangThai(),tk.getNgayTao()});
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
    }
        //Bảng nhân viên chưa có tài khoảnkhoản
    private void TableNV(JButton browse,JTextField field){
        JFrame TableFrame = new JFrame();
        JButton SelectButton = new JButton("Select");
        JPanel footerpanel = new JPanel();
        footerpanel.setLayout(new FlowLayout());
        TableFrame.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        JTable nvTable = new JTable(model);
        model.addColumn("Mã nhân viên");
        model.addColumn("Tên nhân viên");
        ArrayList<NhanVienDTO> DS = new ArrayList<NhanVienDTO>();
        DS = BUS.TaiKhoanBUS.Kotendangnhap();
        for(NhanVienDTO nv : DS){
            model.addRow(new Object[]{nv.getMaNV(),nv.getTenNV()});
        }
        footerpanel.add(SelectButton);
        TableFrame.add(footerpanel,BorderLayout.SOUTH);
        TableFrame.add(nvTable,BorderLayout.CENTER);
        TableFrame.setVisible(true);
        TableFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                browse.setEnabled(true);
            }
        });
        nvTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                mouseClicked(e);
            }
        });
        SelectButton.addActionListener(e -> {
            int i = nvTable.getSelectedRow(); // Lấy chỉ số hàng đã chọn tại thời điểm nhấn nút
            if (i != -1) { // Kiểm tra xem có hàng nào được chọn không
                field.setText(model.getValueAt(i, 0).toString());
                TableFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(TableFrame, "Vui lòng chọn một nhân viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        TableFrame.setSize(600,400);
        TableFrame.setLocationRelativeTo(null);
        TableFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    private void FormThemTaiKhoan(JButton addAccountButton) {
        JFrame formThemTK = new JFrame("Thêm Nhân Viên");
        formThemTK.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Set the close operation correctly
        formThemTK.setSize(800, 600); // Set the window size
        formThemTK.setLayout(new BorderLayout()); // Set the layout

        // Header
        RoundedPanel themNVHeader = new RoundedPanel(30, 30, Color.WHITE);
        themNVHeader.setLayout(new BorderLayout());
        JLabel themNVTitle = new JLabel("Thêm Nhân Viên", SwingConstants.CENTER);
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
        JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new GridLayout(2,2,10,10));
        browsePanel.setBackground(Color.WHITE);
        RoundedButton Browse = new RoundedButton("Browse", 20, 20);
        JLabel emptylabel1 = new JLabel();
        JLabel emptylabel2 = new JLabel();
        browsePanel.add(emptylabel1);
        browsePanel.add(emptylabel2);
        browsePanel.add(Browse);
        Browse.setPreferredSize(new Dimension(130, 30));
        Browse.setBackground(Color.decode("#EC5228"));
        Browse.setForeground(Color.WHITE);
        Browse.setFont(new Font("Segoe UI", Font.BOLD, 14));
        Browse.setFocusPainted(false);
        Browse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Browse.setBackground(Color.decode("#C14600"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                Browse.setBackground(Color.decode("#EC5228"));
            }
        });
        Browse.addActionListener(e -> {
            Browse.setEnabled(false);
            TableNV(Browse,maNVField);
        });
        themNVCenter.add(maNVLabel);
        themNVCenter.add(maNVField);
        themNVCenter.add(browsePanel);

        // ----- Dòng 2: Tên đăng nhập -----
        JLabel tenDNLabel = new JLabel("Tên đăng nhập:");
        JTextField tenDNField = new JTextField();
        // Tạo icon lỗi cho tên nhân viên
        JLabel nameErrorLabel = new JLabel();
        nameErrorLabel.setPreferredSize(new Dimension(60, 60));
        nameErrorLabel.setIcon(new ImageIcon(scaledImage));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setVisible(false); // ẩn ban đầu
        nameErrorLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themNVCenter.add(tenDNLabel);
        themNVCenter.add(tenDNField);
        themNVCenter.add(nameErrorLabel);

        // ----- Dòng 3: Mật khẩu -----
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JTextField passwordField = new JTextField();
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
        JLabel ngayTaoLabel = new JLabel("Ngày sinh (dd/MM/yyyy):");
        JTextField ngayTaoField = new JTextField();
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
        JComboBox<String> trangThaiField = new JComboBox<>(new String[]{"Active", "Inactive"});
        JLabel emptyLabel4 = new JLabel();
        emptyLabel4.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(trangThaiLabel);
        themNVCenter.add(trangThaiField);
        themNVCenter.add(emptyLabel4);

        // ----- Dòng 6: Vai trò -----
        JLabel vaiTroLabel = new JLabel("Vai trò:");
        JComboBox<String> vaiTroBox = new JComboBox<>(new String[]{"Admin","Quản lí","Nhân viên"});
        JLabel emptyLabel5 = new JLabel();
        emptyLabel5.setPreferredSize(new Dimension(60, 60));
        themNVCenter.add(vaiTroLabel);
        themNVCenter.add(vaiTroBox);
        themNVCenter.add(emptyLabel5);

        formThemTK.add(themNVCenter, BorderLayout.CENTER);

        // Footer chứa nút Lưu
        RoundedPanel themNVFooter = new RoundedPanel(30, 30, Color.WHITE);
        themNVFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            // RESET các label lỗi: ẩn chúng đi trước khi kiểm tra
            nameErrorLabel.setVisible(false);
            passwordErrorLabel.setVisible(false);
            
            boolean isValid = true;

            // Validate Tên đăng nhập: không được để trống
            String name = tenDNField.getText().trim();
            if(name.isEmpty()){
                isValid = false;
                nameErrorLabel.setToolTipText("Tên nhân viên không được để trống.");
                nameErrorLabel.setVisible(true);
            }


            // Validate Password: không được để trống
            String pass = passwordField.getText().trim();
            if(pass.isEmpty()){
                isValid= false;
                passwordErrorLabel.setToolTipText("Mật khẩu không được để trống.");
                passwordErrorLabel.setVisible(true);
            } 
            if(isValid) {
                try {
                    // Gọi hàm thêm nhân viên của BUS.
                    // Lưu ý: Bạn cần đảm bảo rằng hàm insertNhanVien() của BUS chấp nhận kiểu dữ liệu đúng.
                    TaiKhoanBUS.insertTaiKhoan(
                            tenDNField.getText(),
                            passwordField.getText(),
                            ngayTaoField.getTet
                            ngaySinhField.getText(),
                            gioiTinhBox.getSelectedItem().toString(),
                            diaChiField.getText(),
                            trangThaiField.getSelectedItem().toString(),
                            chucVuBox.getSelectedItem().toString()
                    );
                    nvBUS.refreshTableData(tableModel);
                    JOptionPane.showMessageDialog(formThemTK, "Thêm nhân viên thành công!");
                    formThemTK.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formThemTK, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });       
        
        themNVFooter.add(saveButton);
        formThemTK.add(themNVFooter, BorderLayout.SOUTH);

        formThemTK.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addAccountButton.setEnabled(true);
            }
        });
        formThemTK.setVisible(true);
    }
}