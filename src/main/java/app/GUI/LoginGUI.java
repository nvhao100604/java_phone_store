package app.GUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.PhoneStoreApplication;
import app.BUS.AccountBUS;
import app.DTO.Account;
import app.utils.UIUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JPanel {

	private PhoneStoreApplication application;
	private AccountBUS bus;
	private JTextField nameField;
	private JTextField pwdField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public LoginGUI(PhoneStoreApplication application) {
		this.application = application;
		this.bus = new AccountBUS();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		application.SetTitle("Đăng nhập");
		setPreferredSize(new Dimension(600, 500));
		setMinimumSize(new Dimension(600, 500));
		// setMinimumSize(new Dimension(300, 600));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(40, 60, 40, 60));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblNewLabel = new JLabel("ĐĂNG NHẬP");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setPreferredSize(new Dimension(0, 60));
		lblNewLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		add(lblNewLabel, BorderLayout.NORTH);

		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(0, 300));
		inputPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		inputPanel.setLayout(new GridBagLayout());
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		inputPanel.add(lblNewLabel_1, gbc);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameField.setBorder(new EmptyBorder(10, 10, 10, 10));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.anchor = GridBagConstraints.CENTER;
		inputPanel.add(nameField, gbc);

		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.anchor = GridBagConstraints.CENTER;
		inputPanel.add(lblNewLabel_2, gbc);

		pwdField = new JTextField();
		pwdField.setColumns(10);
		pwdField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pwdField.setBorder(new EmptyBorder(10, 10, 10, 10));

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.anchor = GridBagConstraints.CENTER;
		inputPanel.add(pwdField, gbc);

		JButton btnNewButton = new JButton("Quên mật khẩu?");
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setOpaque(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		inputPanel.add(btnNewButton, gbc);
		add(inputPanel, BorderLayout.CENTER);

		JPanel processPanel = new JPanel();
		processPanel.setLayout(new FlowLayout());
		add(processPanel, BorderLayout.SOUTH);
		JButton btnNewButton_1 = new JButton("Đăng nhập");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.addActionListener(e -> {
			HandleLogin();
		});
		processPanel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Đăng Ký");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		processPanel.add(btnNewButton_2);

		application.getRootPane().setDefaultButton(btnNewButton_1);
	}

	public void HandleLogin() {
		// String username = nameField.getText();
		// String password = pwdField.getText();
		String username = "admin";
		String password = "12345";
		Account responseAccount = bus.Login(username, password);

		if (responseAccount == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản");
			return;
		}

		// JOptionPane.showMessageDialog(this, "Check name: " +
		// responseAccount.getFullName());
		application.setMainGUIAccount(responseAccount);
		ClearInput();
		Navigate();
	}

	public void ClearInput() {
		nameField.setText("");
		pwdField.setText("");
		revalidate();
		repaint();
	}

	public void Navigate() {
		// System.out.println();
		application.showMain();
	}
}
