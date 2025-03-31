package GUI;

import BUS.KhuyenMaiBUS;
import Custom.MyButton;
import Custom.MyLabel;
import Custom.RobotoFont;
import Custom.RoundedPanel;
import DTO.KhuyenMaiDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;

public class QuanLiKhuyenMaiGUI extends RoundedPanel {
    private final KhuyenMaiBUS khuyenMaiBUS;
    private JTable khuyenMaiTable;
    private DefaultTableModel tableModel;

    public QuanLiKhuyenMaiGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        khuyenMaiBUS = new KhuyenMaiBUS();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        MyLabel titleLabel = new MyLabel("Quản lý khuyến mãi", 24f, "Bold");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        ImageIcon addIcon = new ImageIcon("Resources\\Image\\AddIcon.png");
        ImageIcon editIcon = new ImageIcon("Resources\\Image\\EditIcon.png");
        ImageIcon deleteIcon = new ImageIcon("Resources\\Image\\DeleteIcon.png");
        ImageIcon viewDetailIcon = new ImageIcon("Resources\\Image\\viewDetail.png");
        Image addImg = addIcon.getImage();
        Image editImg = editIcon.getImage();
        Image deleteImg = deleteIcon.getImage();
        Image viewDetailImg = viewDetailIcon.getImage();
        Image newAddImg = addImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image newEditImg = editImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image newDeleteImg = deleteImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image newViewDetailImg = viewDetailImg.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        addIcon = new ImageIcon(newAddImg);
        editIcon = new ImageIcon(newEditImg);
        deleteIcon = new ImageIcon(newDeleteImg);
        viewDetailIcon = new ImageIcon(newViewDetailImg);

        // các nút chức năng
        MyButton addButton = new MyButton("Thêm khuyến mãi", addIcon);
        MyButton updateButton = new MyButton("Sửa thông tin khuyến mãi", editIcon);
        MyButton deleteButton = new MyButton("Xóa khuyến mãi", deleteIcon);
        MyButton detailButton = new MyButton("Xem chi tiết khuyến mãi", viewDetailIcon);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(detailButton);
        add(buttonPanel, BorderLayout.CENTER);

        // bảng hiển thị khuyến mãi
        String[] columnNames = {"Mã khuyến mãi", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Điều kiện áp dụng"};
        tableModel = new DefaultTableModel(columnNames, 0);
        khuyenMaiTable = new JTable(tableModel);
        khuyenMaiTable.setRowHeight(25);
        khuyenMaiTable.setFont(RobotoFont.getRobotoRegular(12f));
        khuyenMaiTable.getTableHeader().setFont(RobotoFont.getRobotoBold(12f));

        JScrollPane scrollPane = new JScrollPane(khuyenMaiTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.SOUTH);

        khuyenMaiBUS.loadTableData(tableModel);
        formatTableUI();

        // ActionListener cho mấy nút
        addButton.addActionListener(e -> handleAddKhuyenMai());
        updateButton.addActionListener(e -> handleUpdateKhuyenMai());
        deleteButton.addActionListener(e -> handleDeleteKhuyenMai());
        detailButton.addActionListener(e -> handleViewDetailKhuyenMai());
    }

    private void handleAddKhuyenMai() {
        JTextField maKhuyenMaiField = new JTextField(10);
        JTextField tenKhuyenMaiField = new JTextField(20);
        JTextField ngayBatDauField = new JTextField(10);
        JTextField ngayKetThucField = new JTextField(10);
        JTextField dieuKienApDungField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Mã khuyến mãi:"));
        panel.add(maKhuyenMaiField);
        panel.add(new JLabel("Tên khuyến mãi:"));
        panel.add(tenKhuyenMaiField);
        panel.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):"));
        panel.add(ngayBatDauField);
        panel.add(new JLabel("Ngày kết thúc (yyyy-MM-dd):"));
        panel.add(ngayKetThucField);
        panel.add(new JLabel("Điều kiện áp dụng:"));
        panel.add(dieuKienApDungField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Thêm khuyến mãi", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO();
                khuyenMai.setMaKhuyenMai(maKhuyenMaiField.getText());
                khuyenMai.setTenKhuyenMai(tenKhuyenMaiField.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                khuyenMai.setNgayBatDau(dateFormat.parse(ngayBatDauField.getText()));
                khuyenMai.setNgayKetThuc(dateFormat.parse(ngayKetThucField.getText()));
                khuyenMai.setDieuKienApDung(dieuKienApDungField.getText());

                if (khuyenMaiBUS.add(khuyenMai)) {
                    JOptionPane.showMessageDialog(null, "Thêm khuyến mãi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    khuyenMaiBUS.loadTableData(tableModel); // Cập nhật lại bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm khuyến mãi thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Xử lý nút "Sửa thông tin khuyến mãi"
    private void handleUpdateKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khuyến mãi để sửa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getDataById(maKhuyenMai);
        if (khuyenMai == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy khuyến mãi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField tenKhuyenMaiField = new JTextField(khuyenMai.getTenKhuyenMai(), 20);
        JTextField ngayBatDauField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(khuyenMai.getNgayBatDau()), 10);
        JTextField ngayKetThucField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(khuyenMai.getNgayKetThuc()), 10);
        JTextField dieuKienApDungField = new JTextField(khuyenMai.getDieuKienApDung(), 20);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Tên khuyến mãi:"));
        panel.add(tenKhuyenMaiField);
        panel.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):"));
        panel.add(ngayBatDauField);
        panel.add(new JLabel("Ngày kết thúc (yyyy-MM-dd):"));
        panel.add(ngayKetThucField);
        panel.add(new JLabel("Điều kiện áp dụng:"));
        panel.add(dieuKienApDungField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Sửa thông tin khuyến mãi", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                khuyenMai.setTenKhuyenMai(tenKhuyenMaiField.getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                khuyenMai.setNgayBatDau(dateFormat.parse(ngayBatDauField.getText()));
                khuyenMai.setNgayKetThuc(dateFormat.parse(ngayKetThucField.getText()));
                khuyenMai.setDieuKienApDung(dieuKienApDungField.getText());

                if (khuyenMaiBUS.update(khuyenMai)) {
                    JOptionPane.showMessageDialog(null, "Sửa khuyến mãi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    khuyenMaiBUS.loadTableData(tableModel); // Cập nhật lại bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa khuyến mãi thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Xử lý nút "Xóa khuyến mãi"
    private void handleDeleteKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khuyến mãi để xóa!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa khuyến mãi " + maKhuyenMai + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (khuyenMaiBUS.delete(maKhuyenMai)) {
                JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                khuyenMaiBUS.loadTableData(tableModel); // Cập nhật lại bảng
            } else {
                JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Xử lý nút "Xem chi tiết khuyến mãi"
    private void handleViewDetailKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một khuyến mãi để xem chi tiết!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maKhuyenMai = (String) tableModel.getValueAt(selectedRow, 0);
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getDataById(maKhuyenMai);
        if (khuyenMai == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy khuyến mãi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String detail = "Mã khuyến mãi: " + khuyenMai.getMaKhuyenMai() + "\n" +
                "Tên khuyến mãi: " + khuyenMai.getTenKhuyenMai() + "\n" +
                "Ngày bắt đầu: " + dateFormat.format(khuyenMai.getNgayBatDau()) + "\n" +
                "Ngày kết thúc: " + dateFormat.format(khuyenMai.getNgayKetThuc()) + "\n" +
                "Điều kiện áp dụng: " + khuyenMai.getDieuKienApDung();
        JOptionPane.showMessageDialog(null, detail, "Chi tiết khuyến mãi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void formatTableUI(){
        JTableHeader header = khuyenMaiTable.getTableHeader();
        header.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < khuyenMaiTable.getColumnCount(); i++) {
            khuyenMaiTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) khuyenMaiTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        khuyenMaiTable.getTableHeader().setDefaultRenderer(headerRenderer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lí khuyến mãi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new QuanLiKhuyenMaiGUI());
            frame.setVisible(true);
        });
    }
}

