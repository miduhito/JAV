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
    private JComboBox<String> thoiGianComboBox;
    private JDateChooser startDateChooser;  // Thay thế JTextField bằng JDateChooser
    private JDateChooser endDateChooser;    // Thay thế JTextField bằng JDateChooser
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

        thoiGianComboBox = new JComboBox<>(new String[]{"Ngày", "Tuần", "Tháng", "Năm"});
        searchPanel.add(new JLabel("Hiển thị theo:"));
        searchPanel.add(thoiGianComboBox);

        searchPanel.add(new JLabel("Từ ngày:"));
        startDateChooser = new JDateChooser();  // Khởi tạo JDateChooser cho ngày bắt đầu
        startDateChooser.setDateFormatString("yyyy-MM-dd");  // Định dạng ngày
        ((JTextField) startDateChooser.getDateEditor().getUiComponent()).setEditable(false);
        searchPanel.add(startDateChooser);

        searchPanel.add(new JLabel("Đến ngày:"));
        endDateChooser = new JDateChooser();  // Khởi tạo JDateChooser cho ngày kết thúc
        endDateChooser.setDateFormatString("yyyy-MM-dd");  // Định dạng ngày
        ((JTextField) endDateChooser.getDateEditor().getUiComponent()).setEditable(false);
        searchPanel.add(endDateChooser);

        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(e -> {
            // Lấy giá trị ngày từ JDateChooser
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();
        
            // Kiểm tra nếu startDate hoặc endDate là null
            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Kiểm tra nếu ngày bắt đầu lớn hơn ngày kết thúc
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được lớn hơn ngày kết thúc. Yêu cầu nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                // Gọi hàm loadStatistics nếu kiểm tra hợp lệ
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
        String thoiGian = (String) thoiGianComboBox.getSelectedItem();

        // Chuyển giá trị từ JDateChooser thành chuỗi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = (startDateChooser.getDate() != null) ? dateFormat.format(startDateChooser.getDate()) : null;
        String endDate = (endDateChooser.getDate() != null) ? dateFormat.format(endDateChooser.getDate()) : null;

        List<ThongKeDTO> thongKeData = thongKeBUS.getThongKeData(loaiThongKe, thoiGian, startDate, endDate);
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