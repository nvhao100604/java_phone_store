package app.GUI.CustomPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class FilterPanel extends JPanel {

    public FilterPanel() {
        initialize();
    }

    public void initialize() {
        setBorder(new TitledBorder(null, "Tìm kiếm", TitledBorder.LEFT, TitledBorder.TOP, null, null));
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(0, 0));
        setLayout(new FlowLayout());
    }
}
