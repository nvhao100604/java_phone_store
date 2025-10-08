package app.GUI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import app.PhoneStoreApplication;
import app.BUS.AccountBUS;
import app.DTO.Account;

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
		setPreferredSize(new Dimension(500, 300));
		// setMinimumSize(new Dimension(300, 600));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("ĐĂNG NHẬP");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(215, 24, 85, 21);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(71, 74, 64, 13);
		add(lblNewLabel_1);

		nameField = new JTextField();
		nameField.setBounds(145, 72, 265, 19);
		add(nameField);
		nameField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(70, 120, 64, 13);
		add(lblNewLabel_2);

		pwdField = new JTextField();
		pwdField.setBounds(145, 118, 265, 19);
		add(pwdField);
		pwdField.setColumns(10);

		JButton btnNewButton = new JButton("Quên mật khẩu?");
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setOpaque(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(299, 147, 137, 21);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Đăng nhập");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(131, 209, 105, 21);
		btnNewButton_1.addActionListener(e -> {
			HandleLogin();
		});
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Hủy");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBounds(285, 209, 85, 21);
		add(btnNewButton_2);
	}

	public void HandleLogin() {
		String username = nameField.getText();
		String password = pwdField.getText();
		Account responseAccount = bus.Login(username, password);

		if (responseAccount == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản");
			return;
		}

		JOptionPane.showMessageDialog(this, "Check name: " + responseAccount.getFullName());
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
