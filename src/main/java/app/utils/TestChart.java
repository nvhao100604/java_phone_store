package app.utils;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class TestChart extends JPanel {
    private List<BigDecimal> revenue = Arrays.asList(
            new BigDecimal("100"), new BigDecimal("200"), new BigDecimal("150"),
            new BigDecimal("300"), new BigDecimal("250"), new BigDecimal("400"));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill nền trắng
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int barWidth = 40;
        int barSpacing = 60;
        int startX = 100;
        int chartBottomY = 400;
        int maxBarHeight = 250;

        BigDecimal maxValue = revenue.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ONE);
        if (maxValue.compareTo(BigDecimal.ZERO) == 0)
            maxValue = BigDecimal.ONE;

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.BLACK);
        String title = "Doanh thu theo tháng (test)";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (getWidth() - titleWidth) / 2, 40);

        for (int i = 0; i < revenue.size(); i++) {
            BigDecimal value = revenue.get(i);
            int barHeight = value.multiply(BigDecimal.valueOf(maxBarHeight))
                    .divide(maxValue, RoundingMode.HALF_UP)
                    .intValue();

            int x = startX + i * barSpacing;
            int y = chartBottomY - barHeight;

            g2.setColor(new Color(46, 134, 222)); // màu xanh
            g2.fillRect(x, y, barWidth, barHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, barWidth, barHeight);
            g2.drawString("T" + (i + 1), x + 10, chartBottomY + 20);
        }

        g2.setColor(Color.GRAY);
        g2.drawLine(startX - 10, chartBottomY, startX + revenue.size() * barSpacing, chartBottomY);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Test Chart");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.add(new TestChart());
        f.setVisible(true);
    }
}
