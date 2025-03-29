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
import java.util.Objects;


public class QuanLiCaLamChamCongGUI extends RoundedPanel{
    private final DefaultTableModel tableModelCL = new DefaultTableModel();
    private CaLamChamCongBUS clccBUS;

    public QuanLiCaLamChamCongGUI(){
        super(50, 50, Color.decode("#F5ECE0"));
        initComponent();
    }

    private void initComponent(){
        try {
    // -------------------- PHẦN GIAO DIỆN CHỨC NĂNG ---------------------
            this.setLayout(new BorderLayout());

            JLabel title = new JLabel("Quản Lý Ca Làm", SwingConstants.CENTER);
            title.setFont(RobotoFont.getRobotoBold(24f));
            title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); // Padding
            this.add(title, BorderLayout.NORTH);

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

            RoundedButton addShiftButton = new RoundedButton("Thêm ca làm", 20, 20);
            addShiftButton.setPreferredSize(new Dimension(180, 40));
            addShiftButton.setBackground(Color.decode("#EC5228"));
            addShiftButton.setForeground(Color.WHITE);
            addShiftButton.setFont(RobotoFont.getRobotoBold(14f));
            addShiftButton.setFocusPainted(false);

            RoundedButton editShiftButton = new RoundedButton("Sửa thông tin ca làm", 20, 20);
            editShiftButton.setPreferredSize(new Dimension(180, 40));
            editShiftButton.setBackground(Color.decode("#EC5228"));
            editShiftButton.setForeground(Color.WHITE);
            editShiftButton.setFont(RobotoFont.getRobotoBold(14f));
            editShiftButton.setFocusPainted(false);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Horizontal gap = 10
            buttonPanel.setBackground(Color.WHITE);

            buttonPanel.add(editShiftButton);
            buttonPanel.add(addShiftButton);
            controlPanel.add(buttonPanel, BorderLayout.EAST);
            tablePanel.add(controlPanel, BorderLayout.NORTH);

            JPanel tableContainer = new JPanel(new BorderLayout());
            tableContainer.setOpaque(true);

            tableModelCL.setColumnIdentifiers(new Object[]{
                    "Mã ca", "Ca làm", "Giờ bắt đầu", "Giờ kết thúc", "Trạng thái"
            });

            JTable tableCL = new JTable(tableModelCL);
            tableCL.setRowHeight(35);
            tableCL.setFont(RobotoFont.getRobotoRegular(14f));
            tableCL.setShowGrid(false); // Remove grid lines
            tableCL.setIntercellSpacing(new Dimension(0, 0)); // No cell spacing
            tableCL.setFocusable(false);

            JTableHeader header = tableCL.getTableHeader();
            header.setFont(RobotoFont.getRobotoBold(14f));
            header.setBackground(Color.WHITE);
            header.setForeground(Color.BLACK);
            header.setBorder(BorderFactory.createEmptyBorder());

            // hai hàm căn giữa header table và các cột
            DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableCL.getTableHeader().getDefaultRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < tableCL.getColumnCount(); i++) {
                tableCL.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            JScrollPane scrollPane = new JScrollPane(tableCL);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for scroll pane
            scrollPane.setBackground(Color.WHITE);
            scrollPane.getViewport().setBackground(Color.WHITE); // Set viewport background
            tablePanel.add(scrollPane, BorderLayout.CENTER);
            this.add(tablePanel, BorderLayout.SOUTH);

            clccBUS = new CaLamChamCongBUS();
            clccBUS.loadDanhSachCaLamTable(tableModelCL);

    // -------------------- PHẦN XỬ LÝ SỰ KIỆN CHỨC NĂNG ---------------------
            // ------------ NÚT THÊM -----------
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
                ShowFormThemCaLam(addShiftButton);
            });

            // ------------ NÚT SỬA -----------
            editShiftButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    editShiftButton.setBackground(Color.decode("#C14600"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    editShiftButton.setBackground(Color.decode("#EC5228"));
                }
            });
            editShiftButton.addActionListener(e ->{
                editShiftButton.setEnabled(false);
                ShowFormSuaCaLam(editShiftButton, tableCL);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi trong initComponent: " + e.getMessage());
        }
    }

    // ------------------ FORM THÊM CA LÀM --------------------------
    private void ShowFormThemCaLam(JButton addShiftButton) {
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
        themCaLamCenter.setLayout(new GridLayout(8, 2, 10, 10)); // Rows, Columns, Horizontal Gap, Vertical Gap
        themCaLamCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input Fields
        String newMaCa = clccBUS.generateMaCaLam();
        JLabel maCaLamLabel = new JLabel("Mã Ca Làm:");
        JTextField maCaLamField = new JTextField(newMaCa); // Generate automatically
        maCaLamField.setEditable(false); // Disable editing
        maCaLamField.setBackground(Color.LIGHT_GRAY);

        JLabel moTaCaLamLabel = new JLabel("Mô tả:");
        JComboBox<String> moTaCaLamBox = new JComboBox<>(new String[]{"Sáng", "Trưa", "Tối"});

        JLabel gioBatDauLabel = new JLabel("Giờ bắt đầu:");
        JComboBox<String> gioBatDauBox = new JComboBox<>(new String[]{"08:00", "09:00", "10:00","11:00", "12:00", "13:00","14:00","15:00","16:00","17:00","18:00"});

        JLabel gioKetThucLabel = new JLabel("Giờ kết thúc:");
        JComboBox<String> gioKetThucBox = new JComboBox<>(new String[]{"12:00", "13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"});

        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JComboBox<String> trangThaiCaLamBox = new JComboBox<>(new String[]{"Hiệu lực", "Không hiệu lực"});

        // Add components to Center Panel
        themCaLamCenter.add(maCaLamLabel);
        themCaLamCenter.add(maCaLamField);
        themCaLamCenter.add(moTaCaLamLabel);
        themCaLamCenter.add(moTaCaLamBox);
        themCaLamCenter.add(gioBatDauLabel);
        themCaLamCenter.add(gioBatDauBox);
        themCaLamCenter.add(gioKetThucLabel);
        themCaLamCenter.add(gioKetThucBox);
        themCaLamCenter.add(trangThaiLabel);
        themCaLamCenter.add(trangThaiCaLamBox);

        formThemCaLam.add(themCaLamCenter, BorderLayout.CENTER);

        RoundedPanel themCaLamFooter = new RoundedPanel(30, 30, Color.WHITE);
        themCaLamFooter.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(RobotoFont.getRobotoBold(14f));
        themCaLamFooter.add(saveButton);
        formThemCaLam.add(themCaLamFooter, BorderLayout.SOUTH);


    // ------------------ XỬ LÝ SỰ KIỆN FORM --------------------------
        // COMBO BOX GIỜ BẮT ĐẦU & GIỜ KẾT THÚC
        gioBatDauBox.addActionListener(e -> {
            String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
            String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
            if (selectedGioBD != null && selectedGioKT != null) {
                int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]); // Lấy giờ từ "HH:MM"
                int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                if (gioBD >= 8 && gioKT <= 16) {
                    moTaCaLamBox.setSelectedItem("Sáng");
                } else if (gioBD >= 10 && gioKT <= 18) {
                    moTaCaLamBox.setSelectedItem("Trưa");
                } else if (gioBD >= 15 && gioKT <= 23) {
                    moTaCaLamBox.setSelectedItem("Tối");
                }
            }
        });

        gioKetThucBox.addActionListener(e -> {
            String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
            String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
            if (selectedGioBD != null && selectedGioKT != null) {
                int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]); // Lấy giờ từ "HH:MM"
                int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                if (gioBD >= 8 && gioKT <= 16) {
                    moTaCaLamBox.setSelectedItem("Sáng");
                } else if (gioBD >= 10 && gioKT <= 18) {
                    moTaCaLamBox.setSelectedItem("Trưa");
                } else if (gioBD >= 15 && gioKT <= 23) {
                    moTaCaLamBox.setSelectedItem("Tối");
                }
            }
        });
        // NÚT LƯU
        saveButton.addActionListener(e -> {
            CaLamDTO newCaLam = new CaLamDTO();
            newCaLam.setMaCa(newMaCa);
            newCaLam.setMoTa(moTaCaLamBox.getSelectedItem() + "");
            newCaLam.setGioBD(gioBatDauBox.getSelectedItem() + ""); // convert to string
            newCaLam.setGioKT(gioKetThucBox.getSelectedItem() + "");
            newCaLam.setTrangThai(true);

            // đóng form và reset dữ liệu bảng nếu add thành công
            if(clccBUS.add(newCaLam)){
                clccBUS = new CaLamChamCongBUS();
                clccBUS.loadDanhSachCaLamTable(tableModelCL);
                formThemCaLam.dispose();
            }

            addShiftButton.setEnabled(true);
        });

        formThemCaLam.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                addShiftButton.setEnabled(true);
            }
        });
        formThemCaLam.setVisible(true);
    }

    // ------------------ FORM SỬA CA LÀM --------------------------
    private void ShowFormSuaCaLam(JButton editShiftButton, JTable tableCL) {

        int selectedRow = tableCL.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn ca làm muốn sửa!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            editShiftButton.setEnabled(true);
            return; //
        }

        JFrame formSuaCaLam = new JFrame("Sửa Thông Tin Ca Làm");
        formSuaCaLam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSuaCaLam.setSize(800, 600);
        formSuaCaLam.setLayout(new BorderLayout());

        // Header panel
        RoundedPanel suaCaLamHeader = new RoundedPanel(30, 30, Color.WHITE);
        suaCaLamHeader.setLayout(new BorderLayout());
        JLabel suaCaLamTitle = new JLabel("Sửa thông tin ca làm", SwingConstants.CENTER);
        suaCaLamTitle.setFont(RobotoFont.getRobotoBold(24f));
        suaCaLamTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        suaCaLamHeader.add(suaCaLamTitle, BorderLayout.CENTER);
        formSuaCaLam.add(suaCaLamHeader, BorderLayout.NORTH);

        // Center panel for form inputs
        RoundedPanel suaCaLamCenter = new RoundedPanel(30, 30, Color.WHITE);
        suaCaLamCenter.setLayout(new GridLayout(8, 2, 10, 10));
        suaCaLamCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String maCa = (String) tableCL.getValueAt(selectedRow, 0);
        String moTa = (String) tableCL.getValueAt(selectedRow, 1); // cột "Mô tả"
        String gioBD = (String) tableCL.getValueAt(selectedRow, 2); // cột "Giờ bắt đầu"
        String gioKT = (String) tableCL.getValueAt(selectedRow, 3); // cột "Giờ kết thúc"
        String trangThai = (String) tableCL.getValueAt(selectedRow, 4); // cột "Trạng thái"

        // Input Fields
        JTextField maCaLamField = new JTextField(maCa);
        JComboBox<String> moTaCaLamBox = new JComboBox<>(new String[]{"Sáng", "Trưa", "Tối"});
        JComboBox<String> gioBatDauBox = new JComboBox<>(new String[]{"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});
        JComboBox<String> gioKetThucBox = new JComboBox<>(new String[]{"12:00", "13:00", "14:00 ", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"});
        JTextField trangThaiCaLamField = new JTextField(trangThai);
        maCaLamField.setEditable(false);
        trangThaiCaLamField.setEditable(false);

        moTaCaLamBox.setSelectedItem(moTa);
        gioBatDauBox.setSelectedItem(gioBD);
        gioKetThucBox.setSelectedItem(gioKT);

        JLabel maCaLamLabel = new JLabel("Mã ca:");
        JLabel moTaCaLamLabel = new JLabel("Mô tả:");
        JLabel gioBatDauLabel = new JLabel("Giờ bắt đầu:");
        JLabel gioKetThucLabel = new JLabel("Giờ kết thúc:");
        JLabel trangThaiLabel = new JLabel("Trạng thái:");

        suaCaLamCenter.add(maCaLamLabel);
        suaCaLamCenter.add(maCaLamField);
        suaCaLamCenter.add(moTaCaLamLabel);
        suaCaLamCenter.add(moTaCaLamBox);
        suaCaLamCenter.add(gioBatDauLabel);
        suaCaLamCenter.add(gioBatDauBox);
        suaCaLamCenter.add(gioKetThucLabel);
        suaCaLamCenter.add(gioKetThucBox);
        suaCaLamCenter.add(trangThaiLabel);
        suaCaLamCenter.add(trangThaiCaLamField);
        formSuaCaLam.add(suaCaLamCenter, BorderLayout.CENTER);

        RoundedPanel SuaCaLamFooter = new RoundedPanel(30, 30, Color.WHITE);
        SuaCaLamFooter.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(RobotoFont.getRobotoBold(14f));
        SuaCaLamFooter.add(saveButton);
        formSuaCaLam.add(SuaCaLamFooter, BorderLayout.SOUTH);


    // ------------------ XỬ LÝ SỰ KIỆN FORM --------------------------
        // COMBO BOX GIỜ BẮT ĐẦU & GIỜ KẾT THÚC
        gioBatDauBox.addActionListener(e -> {
            String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
            String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
            if (selectedGioBD != null && selectedGioKT != null) {
                int gioBDUpdate = Integer.parseInt(selectedGioBD.split(":")[0]); // Lấy giờ từ "HH:MM"
                int gioKTUpdate = Integer.parseInt(selectedGioKT.split(":")[0]);
                if (gioBDUpdate >= 8 && gioKTUpdate <= 16) {
                    moTaCaLamBox.setSelectedItem("Sáng");
                } else if (gioBDUpdate >= 10 && gioKTUpdate <= 18) {
                    moTaCaLamBox.setSelectedItem("Trưa");
                } else if (gioBDUpdate >= 15 && gioKTUpdate <= 23) {
                    moTaCaLamBox.setSelectedItem("Tối");
                }
            }
        });

        gioKetThucBox.addActionListener(e -> {
            String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
            String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
            if (selectedGioBD != null && selectedGioKT != null) {
                int gioBDUpdate = Integer.parseInt(selectedGioBD.split(":")[0]); // Lấy giờ từ "HH:MM"
                int gioKTUpdate = Integer.parseInt(selectedGioKT.split(":")[0]);
                if (gioBDUpdate >= 8 && gioKTUpdate <= 16) {
                    moTaCaLamBox.setSelectedItem("Sáng");
                } else if (gioBDUpdate >= 10 && gioKTUpdate <= 18) {
                    moTaCaLamBox.setSelectedItem("Trưa");
                } else if (gioBDUpdate >= 15 && gioKTUpdate <= 23) {
                    moTaCaLamBox.setSelectedItem("Tối");
                }
            }
        });

        // NÚT LƯU
        saveButton.addActionListener(e -> {
            CaLamDTO newCaLam = new CaLamDTO();
            newCaLam.setMaCa(maCaLamField.getText());
            newCaLam.setMoTa(moTaCaLamBox.getSelectedItem() + "");
            newCaLam.setGioBD(gioBatDauBox.getSelectedItem() + ""); // convert to string
            newCaLam.setGioKT(gioKetThucBox.getSelectedItem() + "");
            // đóng form và reset dữ liệu bảng nếu add thành công
            if(clccBUS.update(newCaLam)){
                clccBUS = new CaLamChamCongBUS();
                clccBUS.loadDanhSachCaLamTable(tableModelCL);
                formSuaCaLam.dispose();
            } else {
                System.out.println("Update failed for maCa: " + newCaLam.getMaCa());
            }
            editShiftButton.setEnabled(true);
        });

        formSuaCaLam.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                editShiftButton.setEnabled(true);
            }
        });
        formSuaCaLam.setVisible(true);
    }
}
