package GUI;

import BUS.CaLamBUS;
import BUS.ChamCongBUS;
import Custom.EmployeeData;
import Custom.MyLabel;
import Custom.RobotoFont;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ChamCongGUI extends RoundedPanel{
    private final DefaultTableModel tableModelCL = new DefaultTableModel();
    private CaLamBUS clBUS;
    private static ChamCongBUS ccBUS;
    private JComboBox<String> employeeComboBox;
    private JComboBox<String> monthComboBox, yearComboBox;
    private JButton calculateButton;
    private JTable attendanceTable;

    public ChamCongGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        ccBUS = new ChamCongBUS();
        initComponent();
    }

    private void initComponent() {
        try {
            this.setLayout(new BorderLayout());

            // ============================ NORTH Panel ===================
            MyLabel labelCaLam = new MyLabel("Chấm Công", 24f, "Bold");
            labelCaLam.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); // Padding

            JPanel functionPanel = new JPanel();
            functionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            functionPanel.setOpaque(true);
            functionPanel.setBackground(Color.decode("#F5ECE0"));

            JLabel employeeLabel = new JLabel("Nhân viên: ");
            String[] employees = {"Tất cả", "NV001 - Nguyễn Văn A", "NV002 - Trần Thị B", "NV003 - Lê Văn C"}; // Sample data
            employeeComboBox = new JComboBox<>(employees);
            employeeComboBox.setPreferredSize(new Dimension(170, 30));

            JLabel monthLabel = new JLabel("Tháng: ");
            String[] months = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                    "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
            monthComboBox = new JComboBox<>(months);
            monthComboBox.setPreferredSize(new Dimension(100, 30));

            JLabel yearLabel = new JLabel("Năm: ");
            String[] years = {"Năm 2022", "Năm 2023", "Năm 2024", "Năm 2025"};
            yearComboBox = new JComboBox<>(years);
            yearComboBox.setPreferredSize(new Dimension(100, 30));

            // đặt tháng và năm mặc định
            LocalDate currentDate = LocalDate.now();
            int currentMonth = currentDate.getMonthValue();
            int currentYear = currentDate.getYear();
            monthComboBox.setSelectedIndex(currentMonth - 1);
            yearComboBox.setSelectedItem("Năm " + currentYear);

            calculateButton = new JButton("Tính Chấm Công");
            calculateButton.setPreferredSize(new Dimension(150, 30));
            calculateButton.addActionListener(e -> updateAttendanceTable());

            functionPanel.add(labelCaLam);
            functionPanel.add(employeeLabel);
            functionPanel.add(employeeComboBox);
            functionPanel.add(monthLabel);
            functionPanel.add(monthComboBox);
            functionPanel.add(yearLabel);
            functionPanel.add(yearComboBox);
            functionPanel.add(calculateButton);

            this.add(functionPanel, BorderLayout.NORTH);

            // ============================ CENTER Table ===================
            // define initial table columns (fixed columns + placeholder for days)
            String[] fixedColumns = {"STT", "Mã NV", "Tên NV", "Chức vụ"};
            tableModelCL.setColumnIdentifiers(fixedColumns);

            // create table
            attendanceTable = new JTable(tableModelCL);
            attendanceTable.setRowHeight(30);
            attendanceTable.setGridColor(Color.LIGHT_GRAY);
            attendanceTable.setShowGrid(true);
            attendanceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            JScrollPane scrollPane = new JScrollPane(attendanceTable,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            this.add(scrollPane, BorderLayout.CENTER);

            // render data table
            updateAttendanceTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAttendanceTable() {
        String maNhanVien = (String) employeeComboBox.getSelectedItem();
        if (!maNhanVien.equals("Tất cả")) {
            maNhanVien = maNhanVien.split(" - ")[0];
        }

        String thangLuaChon = (String) monthComboBox.getSelectedItem();
        int thangChamCong = Integer.parseInt(thangLuaChon.replace("Tháng ", ""));

        String namLuaChon = (String) yearComboBox.getSelectedItem();
        int namChamCong = Integer.parseInt(namLuaChon.replace("Năm ", ""));

        // xóa các cột và hàng cũ
        tableModelCL.setColumnCount(0);
        tableModelCL.setRowCount(0);

        // thêm mấy cột cố định
        String[] fixedColumns = {"STT", "Mã NV", "Tên NV", "Chức vụ"};
        for (String column : fixedColumns) {
            tableModelCL.addColumn(column);
        }

        // thêm cột cho từng ngày trong tháng
        YearMonth yearMonth = YearMonth.of(namChamCong, thangChamCong); // mặc định là 2025
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            tableModelCL.addColumn(String.valueOf(day) + "/" + thangChamCong);
        }

        // thêm cột Tổng giờ công
        tableModelCL.addColumn("Tổng giờ công");
        tableModelCL.addColumn("Lương theo giờ");
        tableModelCL.addColumn("Tổng thu nhập");

        // get dữ liệu
        Map<String, EmployeeData> employeeMap = new HashMap<>();
        String maNhanVienChamCong = Objects.equals(maNhanVien, "Tất cả") ? null : maNhanVien;
        ArrayList<Object[]> EmployeeData = ccBUS.tinhChamCong( maNhanVienChamCong, thangChamCong, namChamCong);

        // xử lý dữ liệu get được
        for (Object[] row : EmployeeData) {
            String maNV = (String) row[1];
            String tenNV = (String) row[2];
            String chucVu = (String) row[3];
            String ngayLam = String.valueOf(row[4]).split("-")[2];
            int gioCong = (int) row[5];
            double luongTheoGio = (double) row[6];

            if (employeeMap.containsKey(maNV)) {
                EmployeeData data = employeeMap.get(maNV);
                data.addWorkingDay(ngayLam);
                data.addWorkingHours(gioCong);
                data.addDayWorkingHours(gioCong);
            } else {
                EmployeeData data = new EmployeeData(maNV, tenNV, chucVu, luongTheoGio);
                data.addWorkingDay(ngayLam);
                data.addWorkingHours(gioCong);
                data.addDayWorkingHours(gioCong);
                employeeMap.put(maNV, data);
            }
        }

        // đổ dữ liệu vào bảng
        int stt = 1;
        for (EmployeeData data : employeeMap.values()) {
            // tạo hàng bằng số cột
            Object[] rowData = new Object[fixedColumns.length + daysInMonth + 3];
            // +3 cho cột tổng giờ công,lương theo giờ, tổng thu nhập

            rowData[0] = stt++; // STT
            rowData[1] = data.maNV; // Mã NV
            rowData[2] = data.tenNV; // Tên NV
            rowData[3] = data.chucVu; // Chức vụ

            // giờ công vào các cột ngày
            for (int i = 0; i < daysInMonth; i++) {
                rowData[i + 4] = ""; // ko thì thoi
            }
            for (int i = 0; i < data.ngayLam.size(); i++) {
                int day = Integer.parseInt(data.ngayLam.get(i));
                rowData[day + 3] = data.gioCong.get(i); // +3 vì có 4 cột cố định
            }

            // tổng giờ công
            rowData[rowData.length - 3] = data.tongGioCong;
            // lương theo giờ
            rowData[rowData.length - 2] = data.luongTheoGio;
            // tổng thu nhập
            rowData[rowData.length - 1] = data.luongTheoGio * data.tongGioCong;

            // thêm hàng vào bảng
            tableModelCL.addRow(rowData);
        }
        formatTableUI();
    }

    // hàm format bảng center
    private void formatTableUI(){
        JTableHeader header = attendanceTable.getTableHeader();
        header.setFont(RobotoFont.getRobotoBold(12f));

        attendanceTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        for (int i = 4; i < attendanceTable.getColumnCount() - 1; i++) {
            attendanceTable.getColumnModel().getColumn(i).setPreferredWidth(40);
        }

        attendanceTable.getColumnModel().getColumn(attendanceTable.getColumnCount() - 3).setPreferredWidth(90);
        attendanceTable.getColumnModel().getColumn(attendanceTable.getColumnCount() - 2).setPreferredWidth(90);
        attendanceTable.getColumnModel().getColumn(attendanceTable.getColumnCount() - 1).setPreferredWidth(110);
        attendanceTable.getTableHeader().setPreferredSize(new Dimension(attendanceTable.getWidth(), 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < attendanceTable.getColumnCount(); i++) {
            attendanceTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) attendanceTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        attendanceTable.getTableHeader().setDefaultRenderer(headerRenderer);
    }
}

