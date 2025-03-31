package GUI;

import Custom.RoundedButton;
import Custom.RoundedPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class QuanLiNhanVienGUI extends RoundedPanel {
    private DefaultTableModel tableModel = new DefaultTableModel();

    public QuanLiNhanVienGUI() {
        super(50, 50, Color.decode("#F5ECE0")); // RoundedPanel with corner radius 50px
        this.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Quản Lý Nhân Viên", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); // Padding

        this.add(title, BorderLayout.NORTH);

        // Add spacing between title and content
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(0, 15)); // Spacer
        gapPanel.setOpaque(true); // Ensure it's not transparent
        gapPanel.setBackground(Color.decode("#F5ECE0"));
        this.add(gapPanel, BorderLayout.CENTER);

        // Panel to hold table content
        RoundedPanel nvtablePanel = new RoundedPanel(50, 50, Color.WHITE);
        nvtablePanel.setLayout(new BorderLayout());
        nvtablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around content
        nvtablePanel.setPreferredSize(new Dimension(0, 400)); // Maintain consistent height

        // Control panel for search field and buttons
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.WHITE); // Set background to white
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search field
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Nhập tên nhân viên để tìm kiếm...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Create an icon label
        JLabel searchIcon = new JLabel();
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

        // "Edit Employee" button
        RoundedButton editEmployeeButton = new RoundedButton("Sửa nhân viên", 20, 20);
        editEmployeeButton.setPreferredSize(new Dimension(180, 40));
        editEmployeeButton.setBackground(Color.decode("#EC5228"));
        editEmployeeButton.setForeground(Color.WHITE);
        editEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editEmployeeButton.setFocusPainted(false);

        // "Add Employee" button
        RoundedButton addEmployeeButton = new RoundedButton("Thêm nhân viên", 20, 20);
        addEmployeeButton.setPreferredSize(new Dimension(180, 40));
        addEmployeeButton.setBackground(Color.decode("#EC5228"));
        addEmployeeButton.setForeground(Color.WHITE);
        addEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addEmployeeButton.setFocusPainted(false);
        addEmployeeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addEmployeeButton.setBackground(Color.decode("#C14600"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addEmployeeButton.setBackground(Color.decode("#EC5228"));
            }
        });
        addEmployeeButton.addActionListener(e -> {
            addEmployeeButton.setEnabled(false);
            FormThemNhanVien(addEmployeeButton);
        });
    
        // Panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Horizontal gap = 10
        buttonPanel.setBackground(Color.WHITE);

        // Add buttons to the panel
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(addEmployeeButton);

        // Add the button panel to the control panel
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        nvtablePanel.add(controlPanel, BorderLayout.NORTH);

        // Panel containing the table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(true); // Ensure it's not transparent

        // Table content
        tableModel.setColumnIdentifiers(new Object[]{
                "Mã nhân viên", "Tên nhân viên", "Tên chức vụ", "Trạng thái"
        });

        // Connect to SQL and fetch data for table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fastfood", "root", "3182004Lam_"
            );
            String query = "SELECT nv.maNhanVien, nv.tenNhanVien, cv.tenChucVu, nv.trangThai " +
                    "FROM NhanVien nv INNER JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("maNhanVien"),
                        rs.getString("tenNhanVien"),
                        rs.getString("tenChucVu"),
                        rs.getString("trangThai")
                });
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Không tìm thấy driver JDBC!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
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
        scrollPane.getViewport().setBackground(Color.WHITE); // Set viewport background

        nvtablePanel.add(scrollPane, BorderLayout.CENTER);

        this.add(nvtablePanel, BorderLayout.SOUTH);
    }

    public void FormThemNhanVien(JButton addEmployeeButton) {
        JFrame formThemNV = new JFrame("Thêm Nhân Viên");
        formThemNV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formThemNV.setSize(800, 600);
        formThemNV.setLayout(new BorderLayout());
    
        // Header panel
        RoundedPanel themNVHeader = new RoundedPanel(30, 30, Color.WHITE);
        themNVHeader.setLayout(new BorderLayout());
        JLabel themNVTitle = new JLabel("Thêm Nhân Viên", SwingConstants.CENTER);
        themNVTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        themNVTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themNVHeader.add(themNVTitle, BorderLayout.CENTER);
        formThemNV.add(themNVHeader, BorderLayout.NORTH);
    
        // Center panel for form inputs
        RoundedPanel themNVCenter = new RoundedPanel(30, 30, Color.WHITE);
        themNVCenter.setLayout(new GridLayout(9, 2, 10, 10)); // Rows, Columns, Horizontal Gap, Vertical Gap
        themNVCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Input Fields
        JLabel maNVLabel = new JLabel("Mã nhân viên:");
        JTextField maNVField = new JTextField(generateMaNhanVien()); // Generate automatically
        maNVField.setEditable(false); // Disable editing
        maNVField.setBackground(Color.LIGHT_GRAY);
    
        JLabel tenNVLabel = new JLabel("Tên nhân viên:");
        JTextField tenNVField = new JTextField();
    
        JLabel sdtLabel = new JLabel("Số điện thoại:");
        JTextField sdtField = new JTextField();
    
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
    
        JLabel ngaySinhLabel = new JLabel("Ngày sinh (yyyy-mm-dd):");
        JTextField ngaySinhField = new JTextField();
    
        JLabel gioiTinhLabel = new JLabel("Giới tính:");
        JComboBox<String> gioiTinhBox = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
    
        JLabel diaChiLabel = new JLabel("Địa chỉ:");
        JTextField diaChiField = new JTextField();
    
        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JTextField trangThaiField = new JTextField();
    
        JLabel chucVuLabel = new JLabel("Chức vụ:");
        JComboBox<String> chucVuBox = new JComboBox<>();
        loadChucVuToBox(chucVuBox); // Load danh sách chức vụ từ database
    
        // Add components to Center Panel
        themNVCenter.add(maNVLabel);     themNVCenter.add(maNVField);
        themNVCenter.add(tenNVLabel);    themNVCenter.add(tenNVField);
        themNVCenter.add(sdtLabel);      themNVCenter.add(sdtField);
        themNVCenter.add(emailLabel);    themNVCenter.add(emailField);
        themNVCenter.add(ngaySinhLabel); themNVCenter.add(ngaySinhField);
        themNVCenter.add(gioiTinhLabel); themNVCenter.add(gioiTinhBox);
        themNVCenter.add(diaChiLabel);   themNVCenter.add(diaChiField);
        themNVCenter.add(trangThaiLabel); themNVCenter.add(trangThaiField);
        themNVCenter.add(chucVuLabel);   themNVCenter.add(chucVuBox);
    
        formThemNV.add(themNVCenter, BorderLayout.CENTER);
    
        // Footer panel with Save button
        RoundedPanel themNVFooter = new RoundedPanel(30, 30, Color.WHITE);
        themNVFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            // Save data to database
            insertNhanVien(
                maNVField.getText(),
                tenNVField.getText(),
                Integer.parseInt(sdtField.getText()),
                emailField.getText(),
                ngaySinhField.getText(),
                gioiTinhBox.getSelectedItem().toString(),
                diaChiField.getText(),
                trangThaiField.getText(),
                chucVuBox.getSelectedItem().toString()
            );
            refreshTableData(tableModel);
            JOptionPane.showMessageDialog(formThemNV, "Thêm nhân viên thành công!");
            formThemNV.dispose(); // Close form after saving
        });
        themNVFooter.add(saveButton);
        formThemNV.add(themNVFooter, BorderLayout.SOUTH);

        formThemNV.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addEmployeeButton.setEnabled(true);
            }
        });
    
        formThemNV.setVisible(true);
    }
    
    // Hàm tạo tự động mã nhân viên
    private String generateMaNhanVien() {
        // Tạo mã nhân viên tự động (ví dụ: NV001, NV002,...)
        int randomNum = (int)(Math.random() * 900) + 100; // Random số 3 chữ số
        return "NV" + randomNum;
    }

    private void refreshTableData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fastfood", "root", "3182004Lam_"
            );
            String query = "SELECT nv.maNhanVien, nv.tenNhanVien, cv.tenChucVu, nv.trangThai " +
                           "FROM NhanVien nv INNER JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
    
            while (rs.next()) {
                tableModel.addRow(new Object[] {
                        rs.getString("maNhanVien"),
                        rs.getString("tenNhanVien"),
                        rs.getString("tenChucVu"),
                        rs.getString("trangThai")
                });
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Không tìm thấy driver JDBC!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Hàm load danh sách chức vụ từ bảng ChucVu trong database vào JComboBox
    private void loadChucVuToBox(JComboBox<String> box) {
        String sql = "SELECT tenChucVu FROM ChucVu";
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fastfood?useSSL=false&serverTimezone=UTC", "root", "3182004Lam_");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                box.addItem(rs.getString("tenChucVu")); // Thêm từng chức vụ vào combo box
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Hàm thêm nhân viên vào bảng NhanVien

    private void insertNhanVien(String maNhanVien, String tenNhanVien, int sdt, String email, 
                            String ngaySinh, String gioiTinh, String diaChi, String trangThai, String tenChucVu) {
    String sqlInsert = "INSERT INTO NhanVien (maNhanVien, tenNhanVien, SDT, email, ngaySinh, gioiTinh, diaChi, trangThai, maChucVu) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String sqlSelectChucVu = "SELECT maChucVu, tenChucVu FROM ChucVu";
    
    try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/fastfood?useSSL=false&serverTimezone=UTC", "root", "3182004Lam_");
         PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
         PreparedStatement stmtSelect = conn.prepareStatement(sqlSelectChucVu);
         ResultSet rs = stmtSelect.executeQuery()) {

        // Retrieve all ChucVu records and map tenChucVu to maChucVu
        Map<String, String> chucVuMap = new HashMap<>();
        while (rs.next()) {
            chucVuMap.put(rs.getString("tenChucVu"), rs.getString("maChucVu"));
        }

        // Convert date format for ngaySinh
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dbFormat.format(inputFormat.parse(ngaySinh));
        
        // Get maChucVu from tenChucVu
        String maChucVu = chucVuMap.get(tenChucVu);
        if (maChucVu == null) {
            throw new IllegalArgumentException("Chức vụ không tồn tại: " + tenChucVu);
        }

        // Set the parameters for the insert query
        stmtInsert.setString(1, maNhanVien);
        stmtInsert.setString(2, tenNhanVien);
        stmtInsert.setInt(3, sdt);
        stmtInsert.setString(4, email);
        stmtInsert.setString(5, formattedDate); // Valid date format
        stmtInsert.setString(6, gioiTinh);
        stmtInsert.setString(7, diaChi);
        stmtInsert.setString(8, trangThai);
        stmtInsert.setString(9, maChucVu); // Replace tenChucVu with maChucVu
        
        stmtInsert.executeUpdate();
        System.out.println("Thêm nhân viên thành công!");
    } catch (Exception e) {
        e.printStackTrace(); // Handle exceptions such as SQL or date format errors
    }
}
}