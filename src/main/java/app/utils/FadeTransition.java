package app.utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class FadeTransition {

    public static void switchPanel(JPanel container, JPanel currentPanel, JPanel nextPanel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(container);

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
        container.add(overlay, 0);

        Timer timer = new Timer(10, null);
        final float[] alpha = { 1.0f };

        timer.addActionListener((ActionEvent e) -> {
            alpha[0] -= 0.05f;
            overlay.repaint();

            if (alpha[0] <= 0f) {
                timer.stop();
                container.remove(overlay);

                container.remove(currentPanel);
                container.add(nextPanel);
                container.revalidate();
                container.repaint();

                SwingUtilities.invokeLater(() -> {
                    if (frame != null) {
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                    }
                });
            }
        });

        timer.start();
    }
}
