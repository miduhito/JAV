package GUI;

import BUS.CaLamBUS;
import Custom.MyButton;
import Custom.MyLabel;
import Custom.RobotoFont;
import DTO.CaLamDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Date;


public class QuanLiCaLamGUI extends RoundedPanel {
    private final DefaultTableModel tableModelCL = new DefaultTableModel();
    private CaLamBUS clccBUS;
    private JPanel shiftPanel;        // panel "Quản lý ca làm"
    private JPanel schedulingPanel;   // panel "Xếp lịch làm"
    private CardLayout cardLayout;    // quản lý chuyển đổi giao diện
    private JPanel contentPanel;      // container chính
    private JPanel controlPanel;
    private MyButton addButton;
    private MyButton editButton;
    private MyButton deleteButton;
    private MyButton schedulingButton;

    public QuanLiCaLamGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        initComponent();
    }

    private void initComponent() {
        try {
            this.setLayout(new BorderLayout());

            MyLabel title = new MyLabel("Quản Lý Ca Làm", 24f, "Bold");
            title.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

            JPanel functionPanel = new JPanel(new FlowLayout());
            functionPanel.setOpaque(true);
            functionPanel.setBackground(Color.decode("#F5ECE0"));
            functionPanel.add(title);
            this.add(functionPanel, BorderLayout.NORTH);

            controlPanel = new JPanel(new BorderLayout());
            controlPanel.setBackground(Color.WHITE);
            controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            ImageIcon addIcon = new ImageIcon("Image\\AddIcon.png");
            ImageIcon editIcon = new ImageIcon("Image\\EditIcon.png");
            ImageIcon deleteIcon = new ImageIcon("Image\\DeleteIcon.png");
            ImageIcon schedulingIcon = new ImageIcon("Image\\Scheduling.png");

            Image addImg = addIcon.getImage();
            Image editImg = editIcon.getImage();
            Image deleteImg = deleteIcon.getImage();
            Image schedulingImg = schedulingIcon.getImage();

            Image newAddImg = addImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            Image newEditImg = editImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            Image newDeleteImg = deleteImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            Image newSchedulingImg = schedulingImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

            addIcon = new ImageIcon(newAddImg);
            editIcon = new ImageIcon(newEditImg);
            deleteIcon = new ImageIcon(newDeleteImg);
            schedulingIcon = new ImageIcon(newSchedulingImg);

            addButton = new MyButton("Thêm ca làm", addIcon);
            addButton.setPreferredSize(new Dimension(180, 40));
            addButton.setBackground(Color.decode("#EC5228"));
            addButton.setForeground(Color.WHITE);
            addButton.setFont(RobotoFont.getRobotoBold(14f));
            addButton.setFocusPainted(false);

            editButton = new MyButton("Sửa thông tin ca làm", editIcon);
            editButton.setPreferredSize(new Dimension(220, 40));
            editButton.setBackground(Color.decode("#EC5228"));
            editButton.setForeground(Color.WHITE);
            editButton.setFont(RobotoFont.getRobotoBold(14f));
            editButton.setFocusPainted(false);

            deleteButton = new MyButton("Xóa ca làm", deleteIcon);
            deleteButton.setPreferredSize(new Dimension(180, 40));
            deleteButton.setBackground(Color.decode("#EC5228"));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFont(RobotoFont.getRobotoBold(14f));
            deleteButton.setFocusPainted(false);

            schedulingButton = new MyButton("Xếp lịch làm", schedulingIcon);
            schedulingButton.setPreferredSize(new Dimension(180, 40));
            schedulingButton.setBackground(Color.decode("#EC5228"));
            schedulingButton.setForeground(Color.WHITE);
            schedulingButton.setFont(RobotoFont.getRobotoBold(14f));
            schedulingButton.setFocusPainted(false);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(schedulingButton);
            controlPanel.add(buttonPanel, BorderLayout.WEST);
            this.add(controlPanel, BorderLayout.CENTER);

            contentPanel = new RoundedPanel(50, 50, Color.WHITE);
            contentPanel.setLayout(cardLayout = new CardLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            contentPanel.setPreferredSize(new Dimension(0, 400));
            this.add(contentPanel, BorderLayout.SOUTH);

            createShiftPanel();
            contentPanel.add(shiftPanel, "ShiftPanel");

            createSchedulingPanel();
            contentPanel.add(schedulingPanel, "SchedulingPanel");

            clccBUS = new CaLamBUS();
            clccBUS.loadDataTable(tableModelCL);

            setupButtonListeners(title);
        } catch (Exception e) {
            System.out.println("Lỗi trong initComponent: " + e.getMessage());
        }
    }


// ========================== FORM THÊM CA LÀM ================================
    private void ShowFormThemCaLam(JButton addButton) {
        try {
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
            themCaLamCenter.setLayout(new GridLayout(8, 2, 10, 10));
            themCaLamCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Input Fields
            String newMaCa = clccBUS.generateID(); // Generate automatically
            JLabel maCaLamLabel = new JLabel("Mã Ca Làm:");
            JTextField maCaLamField = new JTextField(newMaCa);
            maCaLamField.setEditable(false);
            maCaLamField.setBackground(Color.LIGHT_GRAY);

            JLabel moTaCaLamLabel = new JLabel("Mô tả:");
            JComboBox<String> moTaCaLamBox = new JComboBox<>(new String[]{"Sáng", "Trưa", "Tối"});

            JLabel gioBatDauLabel = new JLabel("Giờ bắt đầu:");
            JComboBox<String> gioBatDauBox = new JComboBox<>(new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});

            JLabel gioKetThucLabel = new JLabel("Giờ kết thúc:");
            JComboBox<String> gioKetThucBox = new JComboBox<>(new String[]{"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"});

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


// ====================================== XỬ LÝ SỰ KIỆN FORM ======================================
            // ---------------- COMBO BOX GIỜ BẮT ĐẦU & GIỜ KẾT THÚC ======================================
            gioBatDauBox.addActionListener(_ -> {
                String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
                String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
                if (selectedGioBD != null && selectedGioKT != null) {
                    int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]); // lấy giờ theo format
                    int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                    if (gioBD >= 8 && gioBD <= 10 && gioKT >= 12 && gioKT <= 14) {
                        moTaCaLamBox.setSelectedItem("Sáng");
                    } else if (gioBD >= 11 && gioBD <= 14 && gioKT >= 15 && gioKT <= 18) {
                        moTaCaLamBox.setSelectedItem("Trưa");
                    } else if (gioBD >= 15 && gioBD <= 19 && gioKT >= 19 && gioKT <= 23) {
                        moTaCaLamBox.setSelectedItem("Tối");
                    }
                }
            });

            gioKetThucBox.addActionListener(_ -> {
                String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
                String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
                if (selectedGioBD != null && selectedGioKT != null) {
                    int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]); // Lấy giờ từ "HH:MM"
                    int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                    if (gioBD >= 8 && gioBD <= 10 && gioKT >= 12 && gioKT <= 14) {
                        moTaCaLamBox.setSelectedItem("Sáng");
                    } else if (gioBD >= 11 && gioBD <= 14 && gioKT >= 15 && gioKT <= 18) {
                        moTaCaLamBox.setSelectedItem("Trưa");
                    } else if (gioBD >= 15 && gioBD <= 19 && gioKT >= 19 && gioKT <= 23) {
                        moTaCaLamBox.setSelectedItem("Tối");
                    }
                }
            });
            //=========================== NÚT LƯU =================================
            saveButton.addActionListener(_ -> {
                CaLamDTO newCaLam = new CaLamDTO();
                newCaLam.setMaCa(newMaCa);
                newCaLam.setMoTa(moTaCaLamBox.getSelectedItem() + "");
                newCaLam.setGioBD(gioBatDauBox.getSelectedItem() + ""); // convert to string
                newCaLam.setGioKT(gioKetThucBox.getSelectedItem() + "");
                newCaLam.setTrangThai(true);

                // đóng form và reset dữ liệu bảng nếu add thành công
                if (clccBUS.add(newCaLam)) {
                    clccBUS = new CaLamBUS();
                    clccBUS.loadDataTable(tableModelCL);
                    formThemCaLam.dispose();
                }

                addButton.setEnabled(true);
            });

            formThemCaLam.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    addButton.setEnabled(true);
                }
            });
            formThemCaLam.setVisible(true);
        } catch (Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Lỗi " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    
// ======================= FORM SỬA CA LÀM ========================
    private void ShowFormSuaCaLam(JButton editButton, JTable tableCL) {

        int selectedRow = tableCL.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn ca làm muốn sửa!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            editButton.setEnabled(true);
            return; //
        }

        JFrame formSuaCaLam = new JFrame("Sửa Thông Tin Ca Làm");
        formSuaCaLam.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSuaCaLam.setSize(800, 600);
        formSuaCaLam.setLayout(new BorderLayout());

        RoundedPanel suaCaLamHeader = new RoundedPanel(30, 30, Color.WHITE);
        suaCaLamHeader.setLayout(new BorderLayout());
        JLabel suaCaLamTitle = new JLabel("Sửa thông tin ca làm", SwingConstants.CENTER);
        suaCaLamTitle.setFont(RobotoFont.getRobotoBold(24f));
        suaCaLamTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        suaCaLamHeader.add(suaCaLamTitle, BorderLayout.CENTER);
        formSuaCaLam.add(suaCaLamHeader, BorderLayout.NORTH);

        RoundedPanel suaCaLamCenter = new RoundedPanel(30, 30, Color.WHITE);
        suaCaLamCenter.setLayout(new GridLayout(8, 2, 10, 10));
        suaCaLamCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String maCa = (String) tableCL.getValueAt(selectedRow, 0);
        String moTa = (String) tableCL.getValueAt(selectedRow, 1); // cột "Mô tả"
        String gioBD = (String) tableCL.getValueAt(selectedRow, 2); // cột "Giờ bắt đầu"
        String gioKT = (String) tableCL.getValueAt(selectedRow, 3); // cột "Giờ kết thúc"
        String trangThai = (String) tableCL.getValueAt(selectedRow, 4); // cột "Trạng thái"

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


// ========================== XỬ LÝ SỰ KIỆN FORM =====================
    // -------------- COMBO BOX GIỜ BẮT ĐẦU & GIỜ KẾT THÚC ----------------------
        gioBatDauBox.addActionListener(_ -> {
            String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
            String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
            if (selectedGioBD != null && selectedGioKT != null) {
                int gioBDUpdate = Integer.parseInt(selectedGioBD.split(":")[0]);
                int gioKTUpdate = Integer.parseInt(selectedGioKT.split(":")[0]);
                if (gioBDUpdate >= 8 && gioBDUpdate <= 10 && gioKTUpdate >= 12 && gioKTUpdate <= 14) {
                    moTaCaLamBox.setSelectedItem("Sáng");
                } else if (gioBDUpdate >= 11 && gioBDUpdate <= 14 && gioKTUpdate >= 15 && gioKTUpdate <= 18) {
                    moTaCaLamBox.setSelectedItem("Trưa");
                } else if (gioBDUpdate >= 15 && gioBDUpdate <= 19 && gioKTUpdate >= 19 && gioKTUpdate <= 23) {
                    moTaCaLamBox.setSelectedItem("Tối");
                }
            }
        });

        gioKetThucBox.addActionListener(_ -> {
            String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
            String selectedGioKT = (String) gioKetThucBox.getSelectedItem();
            if (selectedGioBD != null && selectedGioKT != null) {
                int gioBDUpdate = Integer.parseInt(selectedGioBD.split(":")[0]); // Lấy giờ từ "HH:MM"
                int gioKTUpdate = Integer.parseInt(selectedGioKT.split(":")[0]);
                if (gioBDUpdate >= 8 && gioBDUpdate <= 10 && gioKTUpdate >= 12 && gioKTUpdate <= 14) {
                    moTaCaLamBox.setSelectedItem("Sáng");
                } else if (gioBDUpdate >= 11 && gioBDUpdate <= 14 && gioKTUpdate >= 15 && gioKTUpdate <= 18) {
                    moTaCaLamBox.setSelectedItem("Trưa");
                } else if (gioBDUpdate >= 15 && gioBDUpdate <= 19 && gioKTUpdate >= 19 && gioKTUpdate <= 23) {
                    moTaCaLamBox.setSelectedItem("Tối");
                }
            }
        });

    // -------------------- NÚT LƯU ------------------------
        saveButton.addActionListener(_ -> {
            CaLamDTO newCaLam = new CaLamDTO();
            newCaLam.setMaCa(maCaLamField.getText());
            newCaLam.setMoTa(moTaCaLamBox.getSelectedItem() + "");
            newCaLam.setGioBD(gioBatDauBox.getSelectedItem() + ""); // convert to string
            newCaLam.setGioKT(gioKetThucBox.getSelectedItem() + "");
            // đóng form và reset dữ liệu bảng nếu add thành công
            if(clccBUS.update(newCaLam)){
                clccBUS = new CaLamBUS();
                clccBUS.loadDataTable(tableModelCL);
                formSuaCaLam.dispose();
            } else {
                System.out.println("Update failed for maCa: " + newCaLam.getMaCa());
            }
            editButton.setEnabled(true);
        });


        formSuaCaLam.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                editButton.setEnabled(true);
            }
        });
        formSuaCaLam.setVisible(true);
    }


    // ============================ GIAO DIỆN CHỨC NĂNG CA LÀM ===================
    private void createShiftPanel() {
        shiftPanel = new JPanel(new BorderLayout());
        shiftPanel.setBackground(Color.WHITE);

        tableModelCL.setColumnIdentifiers(new Object[]{
                "Mã ca", "Ca làm", "Giờ bắt đầu", "Giờ kết thúc", "Trạng thái"
        });
        JTable tableCL = new JTable(tableModelCL);
        tableCL.setRowHeight(35);
        tableCL.setFont(RobotoFont.getRobotoRegular(14f));
        tableCL.setShowGrid(true);
        tableCL.setIntercellSpacing(new Dimension(0, 0));
        tableCL.setFocusable(false);

        JTableHeader header = tableCL.getTableHeader();
        header.setFont(RobotoFont.getRobotoBold(14f));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableCL.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tableCL.getColumnCount(); i++) {
            tableCL.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane shiftScrollPane = new JScrollPane(tableCL);
        shiftScrollPane.setBorder(BorderFactory.createEmptyBorder());
        shiftScrollPane.setBackground(Color.WHITE);
        shiftScrollPane.getViewport().setBackground(Color.WHITE);
        shiftPanel.add(shiftScrollPane, BorderLayout.CENTER);
    }

    private void createSchedulingPanel() {
        schedulingPanel = new JPanel(new BorderLayout());
        schedulingPanel.setBackground(Color.WHITE);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(Color.WHITE);
        datePanel.add(new JLabel("📆 Chọn ngày: "));

        JDateChooser dateNgayLam = new JDateChooser();
        dateNgayLam.setPreferredSize(new Dimension(220, 40));
        dateNgayLam.setDate(new Date());
        datePanel.add(dateNgayLam);
        schedulingPanel.add(datePanel, BorderLayout.NORTH);

        String[] columnNames = {"Ngày", "Tên Nhân Viên", "Ca làm", "Trạng Thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable tableLichLam = new JTable(tableModel);
        tableLichLam.setRowHeight(35); // Chiều cao hàng giống tableCL
        tableLichLam.setFont(RobotoFont.getRobotoRegular(14f)); // Font chữ giống tableCL
        tableLichLam.setShowGrid(true); // Hiển thị lưới nhưng sẽ loại bỏ khoảng cách
        tableLichLam.setIntercellSpacing(new Dimension(0, 0)); // Loại bỏ khoảng cách giữa các ô
        tableLichLam.setFocusable(false); // Không cho phép focus

        JTableHeader header = tableLichLam.getTableHeader();
        header.setFont(RobotoFont.getRobotoBold(14f)); // Font đậm cho tiêu đề
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableLichLam.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tableLichLam.getColumnCount(); i++) {
            tableLichLam.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tableLichLam);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        schedulingPanel.add(scrollPane, BorderLayout.CENTER);
    }

// ============================ PHẦN XỬ LÝ SỰ KIỆN CHỨC NĂNG =================
    private void setupButtonListeners(MyLabel title) {
        ImageIcon shiftIcon = new ImageIcon("Image\\Shift.png");
        ImageIcon schedulingIcon = new ImageIcon("Image\\Scheduling.png");
        Image schedulingImg = schedulingIcon.getImage();
        Image shiftImg = shiftIcon.getImage();
        Image newSchedulingImg = schedulingImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image newShiftImg = shiftImg.getScaledInstance(30 ,30, Image.SCALE_SMOOTH);
        schedulingIcon = new ImageIcon(newSchedulingImg);
        shiftIcon = new ImageIcon(newShiftImg);

        addButton.addActionListener(_ -> {
            if (addButton.getText().equals("Thêm ca làm")) {
                addButton.setEnabled(false);
                ShowFormThemCaLam(addButton);
            } else if (addButton.getText().equals("Thêm lịch làm nhân viên")) {
                // TODO: Thêm logic để mở form thêm lịch làm nhân viên
                JOptionPane.showMessageDialog(null, "Chức năng thêm lịch làm nhân viên chưa được triển khai!");
            }
        });

        editButton.addActionListener(_ -> {
            if (editButton.getText().equals("Sửa thông tin ca làm")) {
                editButton.setEnabled(false);
                JTable tableCL = (JTable) ((JScrollPane) shiftPanel.getComponent(0)).getViewport().getView();
                ShowFormSuaCaLam(editButton, tableCL);
            } else if (editButton.getText().equals("Sửa lịch làm")) {
                // TODO: Thêm logic để mở form sửa lịch làm
                JOptionPane.showMessageDialog(null, "Chức năng sửa lịch làm chưa được triển khai!");
            }
        });

        deleteButton.addActionListener(_ -> {
            if (deleteButton.getText().equals("Xóa ca làm")) {
                JTable tableCL = (JTable) ((JScrollPane) shiftPanel.getComponent(0)).getViewport().getView();
                int selectedRow = tableCL.getSelectedRow();
                if (selectedRow != -1) {
                    String maCa = (String) tableCL.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc muốn xóa ca làm " + maCa + "?",
                            "Xác nhận xóa",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (clccBUS.delete(maCa)) {
                            clccBUS.loadDataTable(tableModelCL);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng chọn một ca làm để xóa!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (deleteButton.getText().equals("Xóa lịch làm")) {
                // TODO: Thêm logic để xóa lịch làm
                JOptionPane.showMessageDialog(null, "Chức năng xóa lịch làm chưa được triển khai!");
            }
        });

        ImageIcon finalShiftIcon = shiftIcon;
        ImageIcon finalSchedulingIcon = schedulingIcon;
        schedulingButton.addActionListener(_ -> {
            if (schedulingButton.getText().equals("Xếp lịch làm")) {
                title.setText("Lịch làm việc");
                addButton.setText("Thêm lịch làm nhân viên");
                editButton.setText("Sửa lịch làm");
                deleteButton.setText("Xóa lịch làm");
                schedulingButton.setText("Quản lý ca làm");
                schedulingButton.setIcon(finalShiftIcon);
                cardLayout.show(contentPanel, "SchedulingPanel");
            } else if (schedulingButton.getText().equals("Quản lý ca làm")) {
                title.setText("Quản lý ca làm");
                addButton.setText("Thêm ca làm");
                editButton.setText("Sửa thông tin ca làm");
                deleteButton.setText("Xóa ca làm");
                schedulingButton.setText("Xếp lịch làm");
                schedulingButton.setIcon(finalSchedulingIcon);
                cardLayout.show(contentPanel, "ShiftPanel");
            }
        });
    }
}
