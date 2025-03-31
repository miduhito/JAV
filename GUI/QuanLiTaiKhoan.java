package GUI;

import Custom.CustomScrollBarUI;
import Custom.RoundedButton;
import Custom.RoundedPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Vector;

public class QuanLiTaiKhoan extends RoundedPanel {

    public QuanLiTaiKhoan() {
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

        // "Edit Employee" button
        RoundedButton editEmployeeButton = new RoundedButton("Sửa nhân viên", 20, 20);
        editEmployeeButton.setPreferredSize(new Dimension(180, 40));
        editEmployeeButton.setBackground(Color.decode("#EC5228"));
        editEmployeeButton.setForeground(Color.WHITE);
        editEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editEmployeeButton.setFocusPainted(false);

        // "Add Employee" button
        RoundedButton addEmployeeButton = new RoundedButton("+ Thêm nhân viên", 20, 20);
        addEmployeeButton.setPreferredSize(new Dimension(180, 40));
        addEmployeeButton.setBackground(Color.decode("#EC5228"));
        addEmployeeButton.setForeground(Color.WHITE);
        addEmployeeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addEmployeeButton.setFocusPainted(false);

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
        Vector<String> v = new Vector<>();
        v.add("Admin");
        v.add("Quản lí");
        v.add("Nhân viên");
        JComboBox CongViec = new JComboBox<>(v);
        // Table content
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{
                "Mã nhân viên", "Tên nhân viên", "Tên chức vụ", "Trạng thái", "Hành động"
        });
        tableModel.addRow(new Object[]{"NV001", "Nguyễn Văn A", "Nhân viên", "Đang làm", CongViec});
        tableModel.addRow(new Object[]{"NV002", "Trần Thị B", "Quản lý", "Nghỉ việc", "Sửa/Xóa"});
        // Thêm 10 dòng dữ liệu mẫu
        tableModel.addRow(new Object[]{"NV003", "Lê Thị C", "Nhân viên", "Đang làm", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV004", "Phạm Văn D", "Quản lý", "Nghỉ việc", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV005", "Trần Quốc E", "Nhân viên", "Đang làm", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV006", "Nguyễn Thị F", "Nhân viên", "Đang làm", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV007", "Võ Minh G", "Nhân viên", "Đang làm", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV008", "Lý Hải H", "Quản lý", "Nghỉ việc", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV009", "Phạm Ngọc I", "Nhân viên", "Đang làm", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV010", "Đỗ Thị K", "Nhân viên", "Đang làm", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV011", "Bùi Xuân L", "Nhân viên", "Nghỉ việc", "Sửa/Xóa"});
        tableModel.addRow(new Object[]{"NV012", "Ngô Tấn Mu", "Quản lý", "Đang làm", "Sửa/Xóa"});
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
}