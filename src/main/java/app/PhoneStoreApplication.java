package app;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		layout = new CardLayout();
		mainPanel = new JPanel(layout);

		login = new LoginGUI(this);
		main = new MainGUI(this);

		mainPanel.add(login, "login");
		mainPanel.add(main, "main");

		add(mainPanel);
		// showLogin();
		layout.show(mainPanel, "login");
		mainPanel.revalidate();
		mainPanel.repaint();
		setSize(login.getPreferredSize());
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

		FadeTransition.switchPanel(mainPanel, login, main);
	}

	public void showLogin() {
		// layout.show(mainPanel, "login");
		// mainPanel.revalidate();
		// mainPanel.repaint();
		// setSize(login.getPreferredSize());
		// setLocationRelativeTo(null);

		FadeTransition.switchPanel(mainPanel, main, login);
	}
}
