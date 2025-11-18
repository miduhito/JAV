package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ThongKeGUI extends RoundedPanel {
    private JComboBox<String> loaiThongKeComboBox;
    // private JComboBox<String> thoiGianComboBox; // [ĐÃ XÓA] Bỏ ComboBox "Hiển thị theo"
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JPanel chartPanel;
    private ThongKeBUS thongKeBUS;

    public ThongKeGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        this.setLayout(new BorderLayout());
        thongKeBUS = new ThongKeBUS();

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Thống Kê", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        this.add(headerPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loaiThongKeComboBox = new JComboBox<>(new String[]{"Doanh thu", "Số lượng hóa đơn", "Số lượng khách hàng", "Sản phẩm bán chạy"});
        searchPanel.add(new JLabel("Loại thống kê:"));
        searchPanel.add(loaiThongKeComboBox);

        // [ĐÃ XÓA] Bỏ phần "Hiển thị theo"
        // thoiGianComboBox = new JComboBox<>(new String[]{"Ngày", "Tuần", "Tháng", "Năm"});
        // searchPanel.add(new JLabel("Hiển thị theo:"));
        // searchPanel.add(thoiGianComboBox);

        searchPanel.add(new JLabel("Từ ngày:"));
        startDateChooser = new JDateChooser();
        startDateChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) startDateChooser.getDateEditor().getUiComponent()).setEditable(false);
        searchPanel.add(startDateChooser);

        searchPanel.add(new JLabel("Đến ngày:"));
        endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextField) endDateChooser.getDateEditor().getUiComponent()).setEditable(false);
        searchPanel.add(endDateChooser);

        JButton searchButton = new JButton("Thống kê");
        searchButton.addActionListener(e -> {
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();
        
            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được lớn hơn ngày kết thúc. Yêu cầu nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                loadStatistics();
            }
        });
        searchPanel.add(searchButton);

        this.add(searchPanel, BorderLayout.NORTH);

        // Chart Panel
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ thống kê"));
        this.add(chartPanel, BorderLayout.CENTER);
    }

    private void loadStatistics() {
        String loaiThongKe = (String) loaiThongKeComboBox.getSelectedItem();
        // [ĐÃ XÓA] String thoiGian = (String) thoiGianComboBox.getSelectedItem();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = (startDateChooser.getDate() != null) ? dateFormat.format(startDateChooser.getDate()) : null;
        String endDate = (endDateChooser.getDate() != null) ? dateFormat.format(endDateChooser.getDate()) : null;

        // [SỬA] Chỉ truyền 3 tham số. BUS sẽ tự quyết định cách nhóm
        List<ThongKeDTO> thongKeData = thongKeBUS.getThongKeData(loaiThongKe, startDate, endDate);
        renderChart(thongKeData, loaiThongKe);
    }

    private void renderChart(List<ThongKeDTO> data, String loaiThongKe) {
        chartPanel.removeAll();

        // Tạo dataset cho biểu đồ dạng cột
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeDTO item : data) {
            dataset.addValue(item.getValue(), loaiThongKe, item.getLabel());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                loaiThongKe + " theo thời gian",
                "Thời gian",
                loaiThongKe,
                dataset
        );

        ChartPanel chartContainer = new ChartPanel(barChart);
        chartPanel.add(chartContainer, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            chartPanel.revalidate();
            chartPanel.repaint();
        });
    }
}