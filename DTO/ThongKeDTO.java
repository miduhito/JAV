package DTO;

public class ThongKeDTO {
    private String label; // Nhãn (ví dụ: ngày hoặc sản phẩm)
    private double value; // Giá trị thống kê

    public ThongKeDTO(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public double getValue() {
        return value;
    }
}