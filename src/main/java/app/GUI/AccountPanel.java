package app.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.utils.ConfirmDialog;

public class AccountPanel extends JPanel {

    private MainGUI mainGUI;
    private JLabel lblNewLabel_14;

    public AccountPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        lblNewLabel_14 = new JLabel();
        initialize();
    }

    public void initialize() {
        setBackground(null);
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 0));

        JLabel lblNewLabel_13 = new JLabel("");
        lblNewLabel_13.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon tài khoản ( hiển thị ).png"));
        lblNewLabel_13.setPreferredSize(new Dimension(50, 100));
        lblNewLabel_13.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        if (mainGUI.getAccount() == null) {
            lblNewLabel_14.setText("Username");
        } else {
            ChangeUserText();
        }
        lblNewLabel_14.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_14.setForeground(new Color(255, 255, 255));
        lblNewLabel_14.setPreferredSize(new Dimension(100, 50));
        lblNewLabel_14.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(lblNewLabel_14);
        // add(lblNewLabel_14);

        JButton btnNewButton_13 = new JButton("Đăng xuất");
        btnNewButton_13.setPreferredSize(new Dimension(100, 50));
        btnNewButton_13.setBackground(new Color(255, 0, 0));
        btnNewButton_13.setBorderPainted(false);
        // btnNewButton_13.setContentAreaFilled(false);
        // btnNewButton_13.setOpaque(false);
        btnNewButton_13.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewButton_13.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnNewButton_13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean response = ConfirmDialog.confirmDialog(
                        textPanel,
                        "Bạn có chắc muốn thoát chương trình?",
                        "Đăng xuất");
                if (response) {
                    HandleLogout();
                }
            }
        });

        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(btnNewButton_13);
        // btnNewButton_13.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // }
        // });
        btnNewButton_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnNewButton_13.setForeground(new Color(255, 255, 255));
        btnNewButton_13.setBounds(43, 724, 149, 21);
        // add(btnNewButton_13);
        add(lblNewLabel_13);
        add(Box.createHorizontalStrut(10));
        add(textPanel);
    }

    public void ChangeUserText() {
        lblNewLabel_14.setText(mainGUI.getAccount().getUsername());
    }

    public void HandleLogout() {
        mainGUI.Logout();
        mainGUI.getApplication().showLogin();
    }
}
