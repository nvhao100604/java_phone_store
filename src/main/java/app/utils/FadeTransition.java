package app.utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class FadeTransition {

    /**
     * Thực hiện hiệu ứng mờ dần giữa 2 panel (fade-out → fade-in)
     */
    public static void switchPanel(JPanel container, JPanel currentPanel, JPanel nextPanel) {
        // Lấy frame chứa container
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(container);

        // Tạo overlay để vẽ hiệu ứng
        JPanel overlay = new JPanel() {
            float alpha = 1.0f;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                currentPanel.paint(g2d);
                g2d.dispose();
            }
        };

        overlay.setOpaque(false);
        overlay.setBounds(0, 0, container.getWidth(), container.getHeight());
        container.add(overlay, 0); // Thêm overlay đè lên

        Timer timer = new Timer(10, null);
        final float[] alpha = { 1.0f };

        timer.addActionListener((ActionEvent e) -> {
            alpha[0] -= 0.05f;
            overlay.repaint();

            if (alpha[0] <= 0f) {
                timer.stop();
                container.remove(overlay);

                // Thay đổi panel thật sự
                container.remove(currentPanel);
                container.add(nextPanel);
                container.revalidate();
                container.repaint();

                // Sau khi đổi panel: gọi pack() và căn giữa frame
                SwingUtilities.invokeLater(() -> {
                    if (frame != null) {
                        frame.pack(); // Tự resize frame theo GUI mới
                        frame.setLocationRelativeTo(null); // Căn giữa màn hình
                    }
                });
            }
        });

        timer.start();
    }
}
