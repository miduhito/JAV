package GUI;

import BUS.CaLamBUS;
import Custom.MyLabel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChamCongGUI extends RoundedPanel{
    private final DefaultTableModel tableModelCL = new DefaultTableModel();
    private CaLamBUS clccBUS;

    public ChamCongGUI() {
        super(50, 50, Color.decode("#F5ECE0"));
        initComponent();
    }

    private void initComponent() {
        try {
// ============================ GIAO DIỆN CHỨC NĂNG CHẤM CÔNG ===================

            this.setLayout(new BorderLayout());
            MyLabel LabelCaLam = new MyLabel("Chấm Công", 24f, "Bold");
            LabelCaLam.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15)); // Padding

            JPanel functionPanel = new JPanel();
            functionPanel.setLayout(new FlowLayout());
            functionPanel.setOpaque(true);
            functionPanel.add(LabelCaLam);
            this.add(functionPanel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println("Lỗi trong initComponent: " + e.getMessage());
        }
    }
}
