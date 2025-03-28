package GUI;

import BUS.CaLamChamCongBUS;
import Custom.RobotoFont;
import DTO.CaLamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class QuanLiCaLamChamCongGUI extends RoundedPanel{
    private final DefaultTableModel tableModelCL = new DefaultTableModel();
    private CaLamChamCongBUS clccBUS;

    public QuanLiCaLamChamCongGUI(){
        super(50, 50, Color.decode("#F5ECE0"));
        initComponent();
    }

    private void initComponent(){
        try {
            this.setLayout(new BorderLayout());
            // Title
            JLabel title = new JLabel("Quản Lý Ca Làm", SwingConstants.CENTER);
            title.setFont(RobotoFont.getRobotoBold(24f));
            title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); // Padding
            this.add(title, BorderLayout.NORTH);

            // Add spacing between title and content
            JPanel gapPanel = new JPanel();
            gapPanel.setPreferredSize(new Dimension(0, 15)); // Spacer
            gapPanel.setOpaque(true); // Ensure it's not transparent
            gapPanel.setBackground(Color.decode("#F5ECE0"));
            this.add(gapPanel, BorderLayout.CENTER);

            // Panel to hold table content
            RoundedPanel tablePanel = new RoundedPanel(50, 50, Color.WHITE);
            tablePanel.setLayout(new BorderLayout());
            tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around content
            tablePanel.setPreferredSize(new Dimension(0, 400)); // Maintain consistent height

            // Control panel for search field and buttons
            JPanel controlPanel = new JPanel(new BorderLayout());
            controlPanel.setBackground(Color.WHITE); // Set background to white
            controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            RoundedButton editShiftButton = new RoundedButton("Sửa thông tin ca làm", 20, 20);
            editShiftButton.setPreferredSize(new Dimension(180, 40));
            editShiftButton.setBackground(Color.decode("#EC5228"));
            editShiftButton.setForeground(Color.WHITE);
            editShiftButton.setFont(RobotoFont.getRobotoBold(14f));
            editShiftButton.setFocusPainted(false);

            RoundedButton addShiftButton = new RoundedButton("Thêm ca làm", 20, 20);
            addShiftButton.setPreferredSize(new Dimension(180, 40));
            addShiftButton.setBackground(Color.decode("#EC5228"));
            addShiftButton.setForeground(Color.WHITE);
            addShiftButton.setFont(RobotoFont.getRobotoBold(14f));
            addShiftButton.setFocusPainted(false);
            addShiftButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    addShiftButton.setBackground(Color.decode("#C14600"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    addShiftButton.setBackground(Color.decode("#EC5228"));
                }
            });
            addShiftButton.addActionListener(e -> {
                addShiftButton.setEnabled(false);
                FormThemCaLam(addShiftButton);
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Horizontal gap = 10
            buttonPanel.setBackground(Color.WHITE);

            // Add buttons to the panel
            buttonPanel.add(editShiftButton);
            buttonPanel.add(addShiftButton);

            controlPanel.add(buttonPanel, BorderLayout.EAST);

            tablePanel.add(controlPanel, BorderLayout.NORTH);

            JPanel tableContainer = new JPanel(new BorderLayout());
            tableContainer.setOpaque(true);

            tableModelCL.setColumnIdentifiers(new Object[]{
                    "Ca làm", "Giờ bắt đầu", "Giờ kết thúc", "Trạng thái"
            });

            JTable table = new JTable(tableModelCL);
            table.setRowHeight(35);
            table.setFont(RobotoFont.getRobotoRegular(14f));
            table.setShowGrid(false); // Remove grid lines
            table.setIntercellSpacing(new Dimension(0, 0)); // No cell spacing
            table.setFocusable(false);

            JTableHeader header = table.getTableHeader();
            header.setFont(RobotoFont.getRobotoBold(14f));
            header.setBackground(Color.WHITE);
            header.setForeground(Color.BLACK);
            header.setBorder(BorderFactory.createEmptyBorder());

            // Hai hàm căn giữa header table và các cột
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for scroll pane
            scrollPane.setBackground(Color.WHITE);
            scrollPane.getViewport().setBackground(Color.WHITE); // Set viewport background

            tablePanel.add(scrollPane, BorderLayout.CENTER);

            this.add(tablePanel, BorderLayout.SOUTH);

            loadDanhSachCaLamTable();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi trong initComponent: " + e.getMessage());
        }
    }

    public void FormThemCaLam(JButton addShiftButton) {
        JFrame formThemCaLam = new JFrame("Thêm Ca Làm");
        formThemCaLam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formThemCaLam.setSize(800, 600);
        formThemCaLam.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel themCaLamHeader = new RoundedPanel(30, 30, Color.WHITE);
        themCaLamHeader.setLayout(new BorderLayout());
        JLabel themCaLamTitle = new JLabel("Thêm Ca Làm", SwingConstants.CENTER);
        themCaLamTitle.setFont(RobotoFont.getRobotoBold(24f));
        themCaLamTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        themCaLamHeader.add(themCaLamTitle, BorderLayout.CENTER);
        formThemCaLam.add(themCaLamHeader, BorderLayout.NORTH);

        // Center panel for form inputs
        RoundedPanel themCaLamCenter = new RoundedPanel(30, 30, Color.WHITE);
        themCaLamCenter.setLayout(new GridLayout(9, 2, 10, 10)); // Rows, Columns, Horizontal Gap, Vertical Gap
        themCaLamCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input Fields
        JLabel maCaLamLabel = new JLabel("Mã Ca Làm:");
        JTextField maCaLamField = new JTextField(); // Generate automatically
        maCaLamField.setEditable(false); // Disable editing
        maCaLamField.setBackground(Color.LIGHT_GRAY);

        JLabel moTaCaLamLabel = new JLabel("Mô tả:");
        JTextField moTaCaLamField = new JTextField();

        JLabel gioBatDauLabel = new JLabel("Giờ bắt đầu:");
        JComboBox<String> gioBatDauBox = new JComboBox<>(new String[]{"8h", "9h", "10h","11h", "12h", "13h","14h","15h","16h","17h","18h"});

        JLabel gioKetThucLabel = new JLabel("Giờ kết thúc:");
        JComboBox<String> gioKetThucBox = new JComboBox<>(new String[]{"12h", "13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h"});

        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JComboBox<String> trangThaiCaLamBox = new JComboBox<>(new String[]{"Hiệu lực", "Không hiệu lực"});

        // Add components to Center Panel
        themCaLamCenter.add(maCaLamLabel);
        themCaLamCenter.add(maCaLamField);
        themCaLamCenter.add(moTaCaLamLabel);
        themCaLamCenter.add(moTaCaLamField);
        themCaLamCenter.add(gioBatDauLabel);
        themCaLamCenter.add(gioBatDauBox);
        themCaLamCenter.add(gioKetThucLabel);
        themCaLamCenter.add(gioKetThucBox);
        themCaLamCenter.add(trangThaiLabel);
        themCaLamCenter.add(trangThaiCaLamBox);

        formThemCaLam.add(themCaLamCenter, BorderLayout.CENTER);

        // Footer panel with Save button
        RoundedPanel themCaLamFooter = new RoundedPanel(30, 30, Color.WHITE);
        themCaLamFooter.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(RobotoFont.getRobotoBold(14f));
//        saveButton.addActionListener(e -> {
//            // Save data to database
//            insertCaLam(
//                    maCaLamField.getText(),
//                    moTaCaLamField.getText(),
//                    Integer.parseInt(sdtField.getText()),
//                    emailField.getText(),
//                    ngaySinhField.getText(),
//                    gioiTinhBox.getSelectedItem().toString(),
//                    diaChiField.getText(),
//                    trangThaiField.getText(),
//                    chucVuBox.getSelectedItem().toString()
//            );
//            refreshTableData(tableModel);
//            JOptionPane.showMessageDialog(formThemCaLam, "Thêm Ca Làm thành công!");
//            formThemCaLam.dispose(); // Close form after saving
//        });

        themCaLamFooter.add(saveButton);
        formThemCaLam.add(themCaLamFooter, BorderLayout.SOUTH);

        formThemCaLam.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addShiftButton.setEnabled(true);
            }
        });
        formThemCaLam.setVisible(true);
    }

    public void loadDanhSachCaLamTable(){
        tableModelCL.setRowCount(0); // Xóa dữ liêu cũ
        clccBUS = new CaLamChamCongBUS();

        ArrayList<CaLamDTO> danhSachCaLam = clccBUS.getDuLieuCaLam();
        for(CaLamDTO calam : danhSachCaLam){
            System.out.println(calam);
            Vector<Object> vec = new Vector<>();
            vec.add(calam.getMoTa());
            vec.add(calam.getGioBD());
            vec.add(calam.getGioKT());
            vec.add(calam.getTrangThai() ? "Hiệu lực" : "Không hiệu lực");

            tableModelCL.addRow(vec); // thêm thông tin thành 1 hàng vào bảng
        }
    }
}
