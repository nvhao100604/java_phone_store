package app.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.*;
import java.util.List;

import app.BUS.ProductBUS;
import app.DTO.Category;
import app.DTO.Product;
import app.utils.DecimalFilter;
import app.BUS.CategoryBUS;
import app.BUS.EmployeeBUS;
import app.BUS.OrderBUS;

public class StatisticGUI extends JPanel {
    private JLabel lblTotalProduct, lblTotalRevenue, lblTotalEmployee, lblAOV;
    private JPanel chartPanel;

    private ProductBUS productBUS;
    private OrderBUS orderBUS;
    private EmployeeBUS employeeBUS;
    private CategoryBUS categoryBUS;

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
        categoryBUS = new CategoryBUS();

        allYear = orderBUS.getAllYear();
        currentYear = Year.now().getValue();

        add(createTopPanel(), BorderLayout.NORTH);

        JPanel chartPanel = new JPanel(new GridBagLayout());
        chartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.WHITE);
        chartContainer.setOpaque(true);
        chartContainer.setPreferredSize(new Dimension(600, 350));
        chartContainer.add(createChartPanel(), BorderLayout.CENTER);
        chartContainer.add(ControlPanel(), BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.weightx = 0.6;
        gbc.weighty = 0.5;
        chartPanel.add(chartContainer, gbc);

        Map<String, Integer> saleMap = productBUS.getBestSellingProducts("ĐÃ BÁN");
        JPanel piePanel = createTopProductPieChartPanel(saleMap);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.4;
        gbc.weighty = 0.5;
        chartPanel.add(piePanel, gbc);

        JPanel revenueByCategoryPanel = createRevenueByCategoryPanel();

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        // gbc.weightx = 1;
        // gbc.weighty = 0.5;
        chartPanel.add(revenueByCategoryPanel, gbc);

        add(chartPanel, BorderLayout.CENTER);

        loadData();
    }

    private JPanel ControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout());
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
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.setBackground(Color.WHITE);

        lblTotalProduct = createStatCard("Tổng số lượng Sản phẩm", "0");
        lblTotalRevenue = createStatCard("Tổng Doanh thu", "0 đ");
        lblTotalEmployee = createStatCard("Tổng số lượng Nhân viên", "0");
        lblAOV = createStatCard("Giá trị trung bình đơn hàng", "0");

        topPanel.add(lblTotalProduct);
        topPanel.add(lblTotalRevenue);
        topPanel.add(lblAOV);
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
        chartPanel.setPreferredSize(new Dimension(500, 350));
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
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

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

        chartPanel.setPreferredSize(new Dimension(500, 400));
        // chartPanel.setOpaque(true);
        chartPanel.setBackground(Color.WHITE);

        return chartPanel;
    }

    private static JPanel createBottomProductPieChartPanel(Map<String, Integer> data) {
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
                    int labelRadius = diameter / 3;
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

    private JPanel createRevenueByCategoryPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Chọn danh mục: ");
        JComboBox<Category> categoryCombo = new JComboBox<>();
        List<Category> categories = categoryBUS.getAllCategories();
        for (Category category : categories) {
            categoryCombo.addItem(category);
        }
        categoryCombo.setSelectedIndex(0);

        topPanel.add(label);
        topPanel.add(categoryCombo);

        String[] columns = { "Tên sản phẩm", "Số lượng bán", "Tổng doanh thu" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(false);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(200, 100));

        categoryCombo.addActionListener(e -> {
            Category selectedCategory = (Category) categoryCombo.getSelectedItem();
            if (selectedCategory != null) {
                updateRevenueTable(tableModel, selectedCategory);
            }
        });

        if (categoryCombo.getItemCount() > 0) {
            updateRevenueTable(tableModel, (Category) categoryCombo.getSelectedItem());
        }

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private void updateRevenueTable(DefaultTableModel model, Category category) {
        model.setRowCount(0);
        Map<String, BigDecimal> products = orderBUS.getRevenueByCategory(category.getCategoryId());

        for (Map.Entry<String, BigDecimal> p : products.entrySet()) {
            int soldQuantity = productBUS.getQuantityByName(p.getKey(), "ĐÃ BÁN");
            BigDecimal totalRevenue = p.getValue();
            // System.out.println("check " + p.getValue());
            model.addRow(new Object[] {
                    p.getKey(),
                    soldQuantity,
                    DecimalFilter.PriceFormatter().format(totalRevenue == null ? 0 : totalRevenue)
            });
        }

        if (products.isEmpty()) {
            model.addRow(new Object[] { "-", "Không có sản phẩm", "-", "-" });
        }
    }

    private void loadData() {
        int totalProduct = productBUS.getAll().size();
        BigDecimal totalRevenue = orderBUS.getTotalRevenue();
        BigDecimal aov = orderBUS.getAverageOrderValue();
        int totalEmployee = employeeBUS.getAll().size();

        lblTotalProduct.setText(
                "<html><div style='text-align:center;'><h3>Tổng số lượng sản phẩm</h3><h2>" + totalProduct
                        + "</h2></div></html>");
        lblTotalRevenue.setText(
                "<html><div style='text-align:center;'><h3>Tổng Doanh thu</h3><h2>"
                        + DecimalFilter.PriceFormatter().format(totalRevenue)
                        + " VNĐ</h2></div></html>");
        lblAOV.setText(
                "<html><div style='text-align:center;'><h3>Giá trị trung bình đơn hàng</h3><h2>"
                        + DecimalFilter.PriceFormatter().format(aov)
                        + " VNĐ</h2></div></html>");
        lblTotalEmployee.setText(
                "<html><div style='text-align:center;'><h3>Tổng số lượng Nhân viên</h3><h2>" + totalEmployee
                        + "</h2></div></html>");
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
