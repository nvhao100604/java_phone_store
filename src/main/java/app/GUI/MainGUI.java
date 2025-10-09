package app.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.PhoneStoreApplication;
import app.DTO.Account;
import app.GUI.CustomPanels.sidebar;
import app.GUI.interfaces.UserAware;

public class MainGUI extends JPanel {

	private PhoneStoreApplication application;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private sidebar navBar;
	private Account account;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */

	public void InitialGUI(Class<?> guiClass) {
		try {
			Object guiInstance = guiClass.getDeclaredConstructor().newInstance();
			if (guiInstance instanceof JPanel) {
				setContent((JPanel) guiInstance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setContent(JPanel newPanel) {
		removeAll();
		contentPane = newPanel;
		contentPane.setPreferredSize(new Dimension(0, 0));
		add(navBar, BorderLayout.WEST);
		add(contentPane, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public MainGUI(PhoneStoreApplication application) {
		this.application = application;
		initialize();
	}

	public MainGUI(PhoneStoreApplication application, sidebar sidebar) {
		this.application = application;
		this.navBar = sidebar;
		initialize();
	}

	public void initialize() {
		setPreferredSize(new Dimension(1600, 1000));
		setMinimumSize(new Dimension(1400, 800));
		setLayout(new BorderLayout());

		// navBar = new sidebar(this);
		// add(navBar, BorderLayout.WEST);

		contentPane = new ProductGUI();
		contentPane.setPreferredSize(new Dimension(0, 0));
		add(contentPane, BorderLayout.CENTER);
		setVisible(true);
	}

	public PhoneStoreApplication getApplication() {
		return this.application;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account newAccount) {
		this.account = newAccount;
	}

	public void SetSideBar(sidebar sidebar) {
		this.navBar = sidebar;
	}

	public void Logout() {
		this.account = null;
	}

	public void reloadSidebar(Account account) {
		System.out.println("Load sidebar");
		if (navBar != null) {
			remove(navBar);
		}
		navBar = new sidebar(this);
		navBar.setAccount(account);
		add(navBar, BorderLayout.WEST);
		revalidate();
		repaint();
	}
}
