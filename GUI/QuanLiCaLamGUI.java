package GUI;

import BUS.CaLamBUS;
import BUS.LichLamBUS;
import BUS.NhanVienBUS;
import Custom.MyButton;
import Custom.MyLabel;
import Custom.RobotoFont;
import Custom.RoundedPanel;
import DTO.CaLamDTO;
import DTO.LichLamDTO;
import DTO.NhanVienDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class QuanLiCaLamGUI extends RoundedPanel {
    private final DefaultTableModel tableModelCL = new DefaultTableModel();
    private CaLamBUS clBUS;
    private final LichLamBUS llBUS;
    private NhanVienBUS nvBUS;
    private JPanel shiftPanel;        // panel "Quản lý ca làm"
    private JPanel schedulingPanel;   // panel "Xếp lịch làm"
    private CardLayout cardLayout;    // quản lý chuyển đổi giao diện
    private JPanel contentPanel;      // container chính
    private JPanel controlPanel;
    private MyButton addButton;
    private MyButton editButton;
    private MyButton deleteButton;
    private MyButton schedulingButton;
    private JTable tableLichLam;
    private DefaultTableModel tableModelLL;
    RoundedPanel centerPanel;
    SimpleDateFormat sdf;

    public QuanLiCaLamGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        llBUS = new LichLamBUS();
        clBUS = new CaLamBUS();
        initComponents();
    }

    private void initComponents() {
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

            ImageIcon addIcon = new ImageIcon("Resources\\Image\\AddIcon.png");
            ImageIcon editIcon = new ImageIcon("Resources\\Image\\EditIcon.png");
            ImageIcon deleteIcon = new ImageIcon("Resources\\Image\\DeleteIcon.png");
            ImageIcon schedulingIcon = new ImageIcon("Resources\\Image\\Scheduling.png");

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

            clBUS = new CaLamBUS();
            clBUS.loadDataTable(tableModelCL);

            setupButtonListeners(title);
        } catch (Exception e) {
            System.out.println("Lỗi trong initComponent: " + e.getMessage());
        }
    }


// ========================== FORM THÊM ================================
    private void ShowFormThem(JButton addButton, String mode) {
        try {
            JFrame formThem = new JFrame(mode.equals("CaLam") ? "Thêm Ca Làm" : "Thêm Lịch Làm");
            formThem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            formThem.setSize(800, 600);
            formThem.setLayout(new BorderLayout());

            RoundedPanel headerPanel = new RoundedPanel(30, 30, Color.WHITE);
            headerPanel.setLayout(new BorderLayout());
            JLabel titleLabel = new JLabel(mode.equals("CaLam") ? "Thêm Ca Làm" : "Thêm Lịch Làm", SwingConstants.CENTER);
            titleLabel.setFont(RobotoFont.getRobotoBold(24f));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            headerPanel.add(titleLabel, BorderLayout.CENTER);
            formThem.add(headerPanel, BorderLayout.NORTH);

            centerPanel = new RoundedPanel(30, 30, Color.WHITE);
            centerPanel.setLayout(new GridLayout(8, 2, 10, 10));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel maLabel = new JLabel(mode.equals("CaLam") ? "Mã Ca Làm:" : "Mã Lịch Làm:");
            JTextField maField = new JTextField();
            maField.setEditable(false);
            maField.setBackground(Color.LIGHT_GRAY);

            JLabel field1Label = new JLabel();
            JComboBox<String> field1Box = new JComboBox<>();

            JLabel field2Label = new JLabel();
            JComponent field2Component = null;

            JLabel field3Label = new JLabel();
            JComboBox<String> field3Box = new JComboBox<>();

            JLabel trangThaiLabel = new JLabel("Trạng thái:");
            JComboBox<String> trangThaiBox = new JComboBox<>(new String[]{"Hiệu lực", "Không hiệu lực"});
            trangThaiBox.setSelectedItem("Hiệu lực");
            trangThaiBox.setEnabled(false);

            if (mode.equals("CaLam")) {
                maField.setText(clBUS.generateID());
                field1Label.setText("Mô tả:");
                field1Box.setModel(new DefaultComboBoxModel<>(new String[]{"Sáng", "Trưa", "Tối"}));

                field2Label.setText("Giờ bắt đầu:");
                field2Component = new JComboBox<>(new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});

                field3Label.setText("Giờ kết thúc:");
                field3Box.setModel(new DefaultComboBoxModel<>(new String[]{"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}));
            }
            else {
                maField.setText(llBUS.generateID());
                field1Label.setText("Nhân viên:");

                field2Label.setText("Ngày làm việc:");
                field2Component = new JDateChooser();
                ((JDateChooser) field2Component).setDate(new Date());

                ArrayList<String> danhSachNhanVien = new ArrayList<>();
                NhanVienBUS nvBUS = new NhanVienBUS();

                for (NhanVienDTO nv : nvBUS.getData()) {
                    danhSachNhanVien.add(nv.getMaNV() + " - " + nv.getTenNV());
                }
                field1Box.setModel(new DefaultComboBoxModel<>(danhSachNhanVien.toArray(new String[0])));
                field3Label.setText("Ca làm:");

                ArrayList<String> danhSachCaLam = new ArrayList<>();
                for (CaLamDTO ca : clBUS.getData()) {
                    danhSachCaLam.add(ca.getMaCa() + ". " + ca.getGioBD() + " - " + ca.getGioKT());
                }
                field3Box.setModel(new DefaultComboBoxModel<>(danhSachCaLam.toArray(new String[0])));
            }

            centerPanel.add(maLabel);
            centerPanel.add(maField);
            centerPanel.add(field1Label);
            centerPanel.add(field1Box);
            centerPanel.add(field2Label);
            centerPanel.add(field2Component);
            centerPanel.add(field3Label);
            centerPanel.add(field3Box);
            centerPanel.add(trangThaiLabel);
            centerPanel.add(trangThaiBox);
            formThem.add(centerPanel, BorderLayout.CENTER);

            RoundedPanel footerPanel = new RoundedPanel(30, 30, Color.WHITE);
            footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            JButton saveButton = new JButton("Lưu");
            saveButton.setPreferredSize(new Dimension(100, 40));
            saveButton.setBackground(Color.decode("#EC5228"));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFont(RobotoFont.getRobotoBold(14f));
            footerPanel.add(saveButton);
            formThem.add(footerPanel, BorderLayout.SOUTH);

            if (mode.equals("CaLam")) {
                JComboBox<String> gioBatDauBox = (JComboBox<String>) field2Component;

                gioBatDauBox.addActionListener(_ -> {
                    String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
                    String selectedGioKT = (String) field3Box.getSelectedItem();
                    if (selectedGioBD != null && selectedGioKT != null) {
                        int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]);
                        int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                        if (gioBD >= 8 && gioBD <= 10 && gioKT >= 12 && gioKT <= 14) {
                            field1Box.setSelectedItem("Sáng");
                        } else if (gioBD >= 11 && gioBD <= 14 && gioKT >= 15 && gioKT <= 18) {
                            field1Box.setSelectedItem("Trưa");
                        } else if (gioBD >= 15 && gioBD <= 19 && gioKT >= 19 && gioKT <= 23) {
                            field1Box.setSelectedItem("Tối");
                        }
                    }
                });

                field3Box.addActionListener(_ -> {
                    String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
                    String selectedGioKT = (String) field3Box.getSelectedItem();
                    if (selectedGioBD != null && selectedGioKT != null) {
                        int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]);
                        int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                        if (gioBD >= 8 && gioBD <= 10 && gioKT >= 12 && gioKT <= 14) {
                            field1Box.setSelectedItem("Sáng");
                        } else if (gioBD >= 11 && gioBD <= 14 && gioKT >= 15 && gioKT <= 18) {
                            field1Box.setSelectedItem("Trưa");
                        } else if (gioBD >= 15 && gioBD <= 19 && gioKT >= 19 && gioKT <= 23) {
                            field1Box.setSelectedItem("Tối");
                        }
                    }
                });

                // Sự kiện nút Lưu cho Thêm Ca Làm
                saveButton.addActionListener(_ -> {
                    CaLamDTO newCaLam = new CaLamDTO();
                    newCaLam.setMaCa(maField.getText());
                    newCaLam.setMoTa(field1Box.getSelectedItem() + "");
                    newCaLam.setGioBD(gioBatDauBox.getSelectedItem() + "");
                    newCaLam.setGioKT(field3Box.getSelectedItem() + "");
                    newCaLam.setTrangThai(Objects.equals(trangThaiBox.getSelectedItem(), "Hiệu lực"));

                    if (clBUS.add(newCaLam)) {
                        clBUS.loadDataTable(tableModelCL);
                        formThem.dispose();
                    }
                    addButton.setEnabled(true);
                });
            } else {
                JComponent finalField2Component = field2Component;
                saveButton.addActionListener(_ -> {
                    try {
                        LichLamDTO newLichLam = new LichLamDTO();

                        newLichLam.setMaLichLam(maField.getText());

                        newLichLam.setNgayLam(((JDateChooser) finalField2Component).getDate());

                        String selectedNhanVien = (String) field1Box.getSelectedItem();
                        newLichLam.setMaNhanVien(selectedNhanVien.split(" - ")[0]);

                        String selectedCaLam = (String) field3Box.getSelectedItem();
                        newLichLam.setMaCaLam(selectedCaLam.split(" ")[0].replace(".",""));

                        newLichLam.setTrangThai(Objects.equals(trangThaiBox.getSelectedItem(), "Hiệu lực"));

                        if (llBUS.add(newLichLam)) {
                            Date selectedDate = ((JDateChooser) ((JPanel) schedulingPanel.getComponent(0)).getComponent(1)).getDate();
                            ArrayList<LichLamDTO> lichLamList = llBUS.getDataByDate(selectedDate);
                            llBUS.loadDataTable(tableModelLL, lichLamList);
                            formThem.dispose();
                        }
                        addButton.setEnabled(true);
                    } catch (Exception e) {
                        System.out.print("Lỗi: " + e.getMessage());
                        JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }

            formThem.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    addButton.setEnabled(true);
                }
            });
            formThem.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Lỗi " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

// ======================= FORM SỬA CA LÀM ========================
    private void ShowFormSua(JButton editButton, JTable table, String mode) {

        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn lịch làm muốn sửa!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            editButton.setEnabled(true);
            return; //
        }

        JFrame formSua = new JFrame(mode.equals("CaLam") ? "Sửa Ca Làm" : "Sửa Lịch Làm");
        formSua.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formSua.setSize(800, 600);
        formSua.setLayout(new BorderLayout());

        RoundedPanel headerPanel = new RoundedPanel(30, 30, Color.WHITE);
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(mode.equals("CaLam") ? "Sửa Ca Làm" : "Sửa Lịch Làm", SwingConstants.CENTER);
        titleLabel.setFont(RobotoFont.getRobotoBold(24f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        formSua.add(headerPanel, BorderLayout.NORTH);

        centerPanel = new RoundedPanel(30, 30, Color.WHITE);
        centerPanel.setLayout(new GridLayout(8, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel maLabel = new JLabel(mode.equals("CaLam") ? "Mã Ca Làm:" : "Mã Lịch Làm:");
        JTextField maField = new JTextField();
        maField.setEditable(false);
        maField.setBackground(Color.GRAY);

        JLabel field1Label = new JLabel();
        JComboBox<String> field1Box = new JComboBox<>();

        JLabel field2Label = new JLabel();
        JComponent field2Component = null;

        JLabel field3Label = new JLabel();
        JComboBox<String> field3Box = new JComboBox<>();

        JLabel trangThaiLabel = new JLabel("Trạng thái:");
        JComboBox<String> trangThaiBox = new JComboBox<>(new String[]{"Hiệu lực", "Không hiệu lực"});
        trangThaiBox.setSelectedItem("Hiệu lực");
        trangThaiBox.setEnabled(false);

        if (mode.equals("CaLam")) {
            String maCa = (String) table.getValueAt(selectedRow, 0);
            String moTa = (String) table.getValueAt(selectedRow, 1); // cột "Mô tả"
            String gioBD = (String) table.getValueAt(selectedRow, 2); // cột "Giờ bắt đầu"
            String gioKT = (String) table.getValueAt(selectedRow, 3); // cột "Giờ kết thúc"
            String trangThai = (String) table.getValueAt(selectedRow, 4);

            maField.setText(maCa);
            field1Label.setText("Mô tả:");
            field1Box.setModel(new DefaultComboBoxModel<>(new String[]{"Sáng", "Trưa", "Tối"}));
            field1Box.setSelectedItem(moTa);

            field2Label.setText("Giờ bắt đầu:");
            field2Component = new JComboBox<>(new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});
            ((JComboBox<?>) field2Component).setSelectedItem(gioBD);

            field3Label.setText("Giờ kết thúc:");
            field3Box.setModel(new DefaultComboBoxModel<>(new String[]{"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}));
            field3Box.setSelectedItem(gioKT);
        }
        else {
            String maLichLam = (String) table.getValueAt(selectedRow, 0);
            Date ngayLam = (Date) table.getValueAt(selectedRow, 1);
            String tenNhanVien = (String) table.getValueAt(selectedRow, 2);
            String gioLam = (String) table.getValueAt(selectedRow, 3);
            maField.setText(maLichLam);

            LichLamDTO updateLichLam = llBUS.getDataById(maLichLam);

            ArrayList<String> danhSachNhanVien = new ArrayList<>();
            nvBUS = new NhanVienBUS();

            field1Label.setText("Nhân viên:");
            for (NhanVienDTO nv : nvBUS.getData()) {
                danhSachNhanVien.add(nv.getMaNV() + " - " + nv.getTenNV());
            }
            field1Box.setModel(new DefaultComboBoxModel<>(danhSachNhanVien.toArray(new String[0])));
            field1Box.setSelectedItem(updateLichLam.getMaNhanVien() + " - " + tenNhanVien);

            sdf  = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(String.valueOf(ngayLam));
                field2Label.setText("Ngày làm việc:");
                field2Component = new JDateChooser();
                ((JDateChooser) field2Component).setDate(date);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            }

            field3Label.setText("Ca làm:");
            ArrayList<String> danhSachCaLam = new ArrayList<>();
            for (CaLamDTO ca : clBUS.getData()) {
                danhSachCaLam.add(ca.getMaCa() + ". " + ca.getGioBD() + " - " + ca.getGioKT());
            }
            field3Box.setModel(new DefaultComboBoxModel<>(danhSachCaLam.toArray(new String[0])));
            field3Box.setSelectedItem(updateLichLam.getMaCaLam() + ". " + gioLam);
        }

        centerPanel.add(maLabel);
        centerPanel.add(maField);
        centerPanel.add(field1Label);
        centerPanel.add(field1Box);
        centerPanel.add(field2Label);
        centerPanel.add(field2Component);
        centerPanel.add(field3Label);
        centerPanel.add(field3Box);
        centerPanel.add(trangThaiLabel);
        centerPanel.add(trangThaiBox);
        formSua.add(centerPanel, BorderLayout.CENTER);

        RoundedPanel footerPanel = new RoundedPanel(30, 30, Color.WHITE);
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = new JButton("Lưu");
        saveButton.setPreferredSize(new Dimension(100, 40));
        saveButton.setBackground(Color.decode("#EC5228"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(RobotoFont.getRobotoBold(14f));
        footerPanel.add(saveButton);
        formSua.add(footerPanel, BorderLayout.SOUTH);

        if (mode.equals("CaLam")) {
            JComboBox<String> gioBatDauBox = (JComboBox<String>) field2Component;

            gioBatDauBox.addActionListener(_ -> {
                String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
                String selectedGioKT = (String) field3Box.getSelectedItem();
                if (selectedGioBD != null && selectedGioKT != null) {
                    int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]);
                    int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                    if (gioBD >= 8 && gioBD <= 10 && gioKT >= 12 && gioKT <= 14) {
                        field1Box.setSelectedItem("Sáng");
                    } else if (gioBD >= 11 && gioBD <= 14 && gioKT >= 15 && gioKT <= 18) {
                        field1Box.setSelectedItem("Trưa");
                    } else if (gioBD >= 15 && gioBD <= 19 && gioKT >= 19 && gioKT <= 23) {
                        field1Box.setSelectedItem("Tối");
                    }
                }
            });

            field3Box.addActionListener(_ -> {
                String selectedGioBD = (String) gioBatDauBox.getSelectedItem();
                String selectedGioKT = (String) field3Box.getSelectedItem();
                if (selectedGioBD != null && selectedGioKT != null) {
                    int gioBD = Integer.parseInt(selectedGioBD.split(":")[0]);
                    int gioKT = Integer.parseInt(selectedGioKT.split(":")[0]);
                    if (gioBD >= 8 && gioBD <= 10 && gioKT >= 12 && gioKT <= 14) {
                        field1Box.setSelectedItem("Sáng");
                    } else if (gioBD >= 11 && gioBD <= 14 && gioKT >= 15 && gioKT <= 18) {
                        field1Box.setSelectedItem("Trưa");
                    } else if (gioBD >= 15 && gioBD <= 19 && gioKT >= 19 && gioKT <= 23) {
                        field1Box.setSelectedItem("Tối");
                    }
                }
            });

            // Sự kiện nút Lưu cho Sửa Ca Làm
            saveButton.addActionListener(_ -> {
                CaLamDTO newCaLam = new CaLamDTO();
                newCaLam.setMaCa(maField.getText());
                newCaLam.setMoTa(field1Box.getSelectedItem() + "");
                newCaLam.setGioBD(gioBatDauBox.getSelectedItem() + "");
                newCaLam.setGioKT(field3Box.getSelectedItem() + "");
                newCaLam.setTrangThai(Objects.equals(trangThaiBox.getSelectedItem(), "Hiệu lực"));

                if (clBUS.add(newCaLam)) {
                    clBUS.loadDataTable(tableModelCL);
                    formSua.dispose();
                }
                editButton.setEnabled(true);
            });
        } else {
            JComponent finalField2Component = field2Component;
            saveButton.addActionListener(_ -> {
                try {
                    LichLamDTO newLichLam = new LichLamDTO();

                    newLichLam.setMaLichLam(maField.getText());

                    newLichLam.setNgayLam(((JDateChooser) finalField2Component).getDate());

                    String selectedNhanVien = (String) field1Box.getSelectedItem();
                    newLichLam.setMaNhanVien(selectedNhanVien.split(" - ")[0]);

                    String selectedCaLam = (String) field3Box.getSelectedItem();
                    newLichLam.setMaCaLam(selectedCaLam.split(" ")[0].replace(".",""));

                    newLichLam.setTrangThai(Objects.equals(trangThaiBox.getSelectedItem(), "Hiệu lực"));

                    if (llBUS.update(newLichLam)) {
                        Date selectedDate = ((JDateChooser) ((JPanel) schedulingPanel.getComponent(0)).getComponent(1)).getDate();
                        ArrayList<LichLamDTO> lichLamList = llBUS.getDataByDate(selectedDate);
                        llBUS.loadDataTable(tableModelLL, lichLamList);
                        formSua.dispose();
                    }
                    editButton.setEnabled(true);
                } catch (Exception e) {
                    System.out.print("Lỗi: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

// ========================== XỬ LÝ SỰ KIỆN FORM =====================
        
        formSua.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                editButton.setEnabled(true);
            }
        });
        formSua.setVisible(true);
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

// ============================ GIAO DIỆN CHỨC NĂNG XẾP LỊCH LÀM ===================
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

        String[] columnNames = {"Mã lịch", "Ngày", "Tên Nhân Viên", "Ca làm", "Trạng Thái"};
        tableModelLL = new DefaultTableModel(columnNames, 0);
        tableLichLam = new JTable(tableModelLL);
        tableLichLam.setRowHeight(35);
        tableLichLam.setFont(RobotoFont.getRobotoRegular(14f));
        tableLichLam.setShowGrid(true);
        tableLichLam.setIntercellSpacing(new Dimension(0, 0));
        tableLichLam.setFocusable(false);

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

        dateNgayLam.getDateEditor().addPropertyChangeListener("date", _ -> {
            Date selectedDate = dateNgayLam.getDate();
            if (selectedDate != null) {
                ArrayList<LichLamDTO> lichLamList = llBUS.getDataByDate(selectedDate);
                llBUS.loadDataTable(tableModelLL, lichLamList);
            }
        });

        ArrayList<LichLamDTO> lichLamList = llBUS.getDataByDate(new Date());
        llBUS.loadDataTable(tableModelLL, lichLamList);
    }

// ============================ PHẦN XỬ LÝ SỰ KIỆN CHỨC NĂNG =================
    private void setupButtonListeners(MyLabel title) {
        ImageIcon shiftIcon = new ImageIcon("Resources\\Image\\Shift.png");
        ImageIcon schedulingIcon = new ImageIcon("Resources\\Image\\Scheduling.png");
        Image schedulingImg = schedulingIcon.getImage();
        Image shiftImg = shiftIcon.getImage();
        Image newSchedulingImg = schedulingImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image newShiftImg = shiftImg.getScaledInstance(30 ,30, Image.SCALE_SMOOTH);
        schedulingIcon = new ImageIcon(newSchedulingImg);
        shiftIcon = new ImageIcon(newShiftImg);

        addButton.addActionListener(_ -> {
            if (addButton.getText().equals("Thêm ca làm")) {
                ShowFormThem(addButton, "CaLam");
            } else if (addButton.getText().equals("Thêm lịch làm")) {
                ShowFormThem(addButton, "LichLam");
            }
            addButton.setEnabled(false);
        });

        editButton.addActionListener(_ -> {
            if (editButton.getText().equals("Sửa thông tin ca làm")) {
                editButton.setEnabled(false);
                JTable tableCL = (JTable) ((JScrollPane) shiftPanel.getComponent(0)).getViewport().getView();
                ShowFormSua(editButton, tableCL, "CaLam");
            } else if (editButton.getText().equals("Sửa lịch làm")) {
                // TODO: Thêm logic để mở form sửa lịch làm
                editButton.setEnabled(false);
                ShowFormSua(editButton, tableLichLam, "LichLam");
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
                        if (clBUS.delete(maCa)) {
                            clBUS.loadDataTable(tableModelCL);
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
                int selectedRow = tableLichLam.getSelectedRow();
                if (selectedRow != -1) {
                    String maLichLam = (String) tableLichLam.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc muốn xóa lịch làm này ? ",
                            "Xác nhận xóa",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (llBUS.delete(maLichLam)) {
                            ArrayList<LichLamDTO> lichLamList = llBUS.getDataByDate(new Date());
                            llBUS.loadDataTable(tableModelLL, lichLamList);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Vui lòng chọn một lịch làm để xóa!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ImageIcon finalShiftIcon = shiftIcon;
        ImageIcon finalSchedulingIcon = schedulingIcon;
        schedulingButton.addActionListener(_ -> {
            if (schedulingButton.getText().equals("Xếp lịch làm")) {
                title.setText("Lịch làm việc");
                addButton.setText("Thêm lịch làm");
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
