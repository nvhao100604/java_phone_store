package app;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.poi.ss.usermodel.Color;

import app.DTO.Account;
import app.GUI.LoginGUI;
import app.GUI.MainGUI;
import app.utils.FadeTransition;

public class PhoneStoreApplication extends JFrame {
	private CardLayout layout;
	private JPanel mainPanel;
	private LoginGUI login;
	private MainGUI main;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			com.formdev.flatlaf.FlatLaf.setup(null);
			UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize FlatLaf");
		}
		// DataTable.createExcelFolder();

		try {
			UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
			UIManager.put("Button.background", java.awt.Color.LIGHT_GRAY);
			UIManager.put("Table.showGrid", Boolean.TRUE);
			UIManager.put("Table.gridColor", java.awt.Color.GRAY);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Failed to initialize FlatLaf");
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhoneStoreApplication window = new PhoneStoreApplication();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PhoneStoreApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setMinimumSize(new Dimension(500, 400));
		layout = new CardLayout();
		mainPanel = new JPanel(layout);

		login = new LoginGUI(this);
		main = new MainGUI(this);

		mainPanel.add(login, "login");
		mainPanel.add(main, "main");

		add(mainPanel);
		setTitle("Đăng nhập");
		layout.show(mainPanel, "login");
		mainPanel.revalidate();
		mainPanel.repaint();
		setSize(login.getPreferredSize());
		setMinimumSize(login.getMinimumSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void showMain() {
		// layout.show(mainPanel, "main");
		// mainPanel.revalidate();
		// mainPanel.repaint();
		// setSize(main.getPreferredSize());
		// setLocationRelativeTo(null);
		setMinimumSize(main.getMinimumSize());
		SetTitle("Quản lý cửa hàng điện thoại");
		FadeTransition.switchPanel(mainPanel, login, main);
	}

	public void showLogin() {
		// layout.show(mainPanel, "login");
		// mainPanel.revalidate();
		// mainPanel.repaint();
		// setSize(login.getPreferredSize());
		// setLocationRelativeTo(null);
		SetTitle("Đăng nhập");
		setMinimumSize(login.getMinimumSize());
		FadeTransition.switchPanel(mainPanel, main, login);
	}

	public void setMainGUIAccount(Account account) {
		this.main.setAccount(account);
		main.reloadSidebar(account);
		System.out.println("check user: " + main.getAccount().getUsername());
	}

	public void SetTitle(String title) {
		setTitle(title);
	}
}
