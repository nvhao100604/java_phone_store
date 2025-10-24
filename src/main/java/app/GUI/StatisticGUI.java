package app.GUI;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.*;
import java.util.List;

import app.BUS.ProductBUS;
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
        // chartPanel.setOpaque(false);
        chartPanel.setBackground(Color.YELLOW);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setOpaque(false);
        chartContainer.add(createChartPanel(), BorderLayout.CENTER);
        chartContainer.add(ControlPanel(), BorderLayout.SOUTH);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        chartPanel.add(chartContainer, gbc);

        JPanel pieChartPanel = new JPanel(new BorderLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        chartPanel.add(pieChartPanel, gbc);

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
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.BLUE);
        return chartPanel;
    }

    private void drawMockRevenueChart(Graphics g) {
        revenue = orderBUS.getTotalRevenueByMonth(currentYear);

        int barWidth = 40;
        int barSpacing = 50;
        int startX = 120;
        int chartBottomY = 400;
        int maxBarHeight = 250;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Doanh thu theo tháng (" + currentYear + ")", startX, 50);

        BigDecimal maxValue = revenue.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ONE);

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

            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.drawString(value.toString(), x, y - 5);

            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(i + 1), x + 8, chartBottomY + 20);
        }

        g2.setColor(Color.GRAY);
        g2.drawLine(startX - 10, chartBottomY, startX + revenue.size() * barSpacing, chartBottomY);
    }

    private void loadData() {
        int totalProduct = productBUS.getAll().size();
        BigDecimal totalRevenue = orderBUS.getTotalRevenue();
        int totalEmployee = employeeBUS.getAll().size();

        lblTotalProduct.setText(
                "<html><div style='text-align:center;'><h3>Sản phẩm</h3><h2>" + totalProduct + "</h2></div></html>");
        lblTotalRevenue.setText(
                "<html><div style='text-align:center;'><h3>Doanh thu</h3><h2>" + totalRevenue
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
