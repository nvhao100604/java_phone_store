package app.GUI;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.*;
import java.util.List;

import app.BUS.ProductBUS;
import app.utils.DecimalFilter;
import app.BUS.EmployeeBUS;
import app.BUS.OrderBUS;

public class StatisticGUI extends JPanel {
    private JLabel lblTotalProduct, lblTotalRevenue, lblTotalEmployee;
    private JPanel chartPanel;

    private ProductBUS productBUS;
    private OrderBUS orderBUS;
    private EmployeeBUS employeeBUS;

    private List<BigDecimal> revenue;
    private List<Integer> allYear;
    private int currentYear;

    public StatisticGUI() {
        initialize();
    }

    public void initialize() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        productBUS = new ProductBUS();
        orderBUS = new OrderBUS();
        employeeBUS = new EmployeeBUS();

        allYear = orderBUS.getAllYear();
        currentYear = Year.now().getValue();

        add(createTopPanel(), BorderLayout.NORTH);

        JPanel chartPanel = new JPanel(new GridBagLayout());
        chartPanel.setPreferredSize(new Dimension(0, 0));
        // chartPanel.setOpaque(false);
        chartPanel.setBackground(Color.YELLOW);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setOpaque(false);
        chartContainer.add(createChartPanel(), BorderLayout.NORTH);
        chartContainer.add(ControlPanel(), BorderLayout.SOUTH);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        // gbc.weighty = 1;
        chartPanel.add(chartContainer, gbc);

        Map<String, Integer> saleMap = productBUS.getBestSellingProducts("ĐÃ BÁN");
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        // gbc.weighty = 1;
        chartPanel.add(createTopProductPieChartPanel(saleMap), gbc);

        add(chartPanel, BorderLayout.CENTER);

        loadData();
    }

    private JPanel ControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setOpaque(false);
        controlPanel.setBackground(null);
        JButton prevButton = new JButton("<<");
        prevButton.addActionListener(e -> DecreaseYear());
        JButton followingButton = new JButton(">>");
        followingButton.addActionListener(e -> IncreaseYear());

        controlPanel.add(prevButton, BorderLayout.WEST);
        controlPanel.add(followingButton, BorderLayout.EAST);
        return controlPanel;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.setBackground(Color.WHITE);

        lblTotalProduct = createStatCard("Sản phẩm", "0");
        lblTotalRevenue = createStatCard("Doanh thu", "0 đ");
        lblTotalEmployee = createStatCard("Nhân viên", "0");

        topPanel.add(lblTotalProduct);
        topPanel.add(lblTotalRevenue);
        topPanel.add(lblTotalEmployee);

        return topPanel;
    }

    private JLabel createStatCard(String title, String value) {
        JLabel label = new JLabel("<html><div style='text-align:center;'>" +
                "<h3 style='margin:0;color:#333;'>" + title + "</h3>" +
                "<h2 style='margin:5;color:#2E86C1;'>" + value + "</h2>" +
                "</div></html>", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }

    private JPanel createChartPanel() {
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMockRevenueChart(g);
            }
        };
        chartPanel.setPreferredSize(new Dimension(0, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    private void drawMockRevenueChart(Graphics g) {
        revenue = orderBUS.getTotalRevenueByMonth(currentYear);

        int barWidth = 40;
        int barSpacing = 50;
        int startX = 120;
        int chartBottomY = 450;
        int maxBarHeight = 300;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Doanh thu theo tháng (" + currentYear + ")", startX, 50);

        BigDecimal maxValue = revenue.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ONE);
        maxValue = maxValue.multiply(new BigDecimal(5));
        if (maxValue.compareTo(BigDecimal.ZERO) == 0)
            maxValue = BigDecimal.ONE;

        for (int i = 0; i < revenue.size(); i++) {
            BigDecimal value = revenue.get(i);

            int barHeight = value.multiply(BigDecimal.valueOf(maxBarHeight))
                    .divide(maxValue, RoundingMode.HALF_UP)
                    .intValue();

            int x = startX + i * barSpacing;
            int y = chartBottomY - barHeight;

            g2.setColor(new Color(46, 134, 222));
            g2.fillRect(x, y, barWidth, barHeight);

            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, barWidth, barHeight);

            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            g2.setColor(Color.BLACK);
            String valueText = DecimalFilter.PriceFormatter().format(value);
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(valueText);
            g2.drawString(valueText, x + (barWidth - textWidth) / 2, y - 5);

            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("T" + (i + 1), x + 10, chartBottomY + 20);
        }

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(startX - 10, chartBottomY, startX + revenue.size() * barSpacing, chartBottomY);

        g2.drawLine(startX - 10, chartBottomY, startX - 10, chartBottomY - maxBarHeight - 20);

        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        for (int i = 0; i <= 5; i++) {
            int yPos = chartBottomY - (maxBarHeight * i / 5);
            BigDecimal scaleValue = maxValue.multiply(BigDecimal.valueOf(i)).divide(BigDecimal.valueOf(5),
                    RoundingMode.HALF_UP);

            g2.drawLine(startX - 15, yPos, startX - 5, yPos);

            String scaleText = DecimalFilter.PriceFormatter().format(scaleValue);
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(scaleText);
            g2.drawString(scaleText, startX - textWidth - 20, yPos + 4);
        }
    }

    private static JPanel createTopProductPieChartPanel(Map<String, Integer> data) {
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (data == null || data.isEmpty())
                    return;

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int total = data.values().stream().mapToInt(Integer::intValue).sum();
                int startAngle = 0;
                int x = 50, y = 50, diameter = 300;
                int centerX = x + diameter / 2;
                int centerY = y + diameter / 2;

                Color[] colors = {
                        new Color(52, 152, 219),
                        new Color(46, 204, 113),
                        new Color(231, 76, 60),
                        new Color(241, 196, 15),
                        new Color(155, 89, 182)
                };

                int i = 0;
                for (Map.Entry<String, Integer> entry : data.entrySet()) {
                    int value = entry.getValue();
                    int angle = (int) Math.round(360.0 * value / total);
                    double percent = (value * 100.0) / total;

                    g2.setColor(colors[i % colors.length]);
                    g2.fillArc(x, y, diameter, diameter, startAngle, angle);

                    double midAngle = Math.toRadians(startAngle + angle / 2.0);
                    int labelRadius = diameter / 3; // Khoảng cách từ tâm đến label
                    int labelX = centerX + (int) (labelRadius * Math.cos(-midAngle));
                    int labelY = centerY + (int) (labelRadius * Math.sin(-midAngle));

                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 16));
                    String percentText = String.format("%.1f%%", percent);
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(percentText);
                    int textHeight = fm.getHeight();
                    g2.drawString(percentText, labelX - textWidth / 2, labelY + textHeight / 4);

                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.PLAIN, 14));
                    g2.drawString(String.format("%s (%d - %.1f%%)", entry.getKey(), value, percent),
                            380, 80 + i * 20);

                    startAngle += angle;
                    i++;
                }

                g2.setFont(new Font("Arial", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                String title = "Top 10 sản phẩm bán chạy nhất";
                int titleWidth = fm.stringWidth(title);
                g2.drawString(title, (getWidth() - titleWidth) / 2, 30);
            }
        };

        chartPanel.setPreferredSize(new Dimension(550, 400));
        chartPanel.setOpaque(true);
        chartPanel.setBackground(Color.WHITE);

        return chartPanel;
    }

    private void loadData() {
        int totalProduct = productBUS.getAll().size();
        BigDecimal totalRevenue = orderBUS.getTotalRevenue();
        int totalEmployee = employeeBUS.getAll().size();

        lblTotalProduct.setText(
                "<html><div style='text-align:center;'><h3>Sản phẩm</h3><h2>" + totalProduct + "</h2></div></html>");
        lblTotalRevenue.setText(
                "<html><div style='text-align:center;'><h3>Doanh thu</h3><h2>"
                        + DecimalFilter.PriceFormatter().format(totalRevenue)
                        + " VNĐ</h2></div></html>");
        lblTotalEmployee.setText(
                "<html><div style='text-align:center;'><h3>Nhân viên</h3><h2>" + totalEmployee + "</h2></div></html>");
    }

    public void IncreaseYear() {
        System.out.println("All year check" + allYear.size());
        if (allYear.size() == 0)
            return;
        currentYear = allYear.contains(currentYear + 1) ? currentYear + 1 : currentYear;
        revalidate();
        repaint();
    }

    public void DecreaseYear() {
        System.out.println("All year check" + allYear.size());
        if (allYear.size() == 0)
            return;
        currentYear = allYear.contains(currentYear - 1) ? currentYear - 1 : currentYear;
        revalidate();
        repaint();
    }
}
