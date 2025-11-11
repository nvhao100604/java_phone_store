package app.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.PhoneStoreApplication;
import app.DTO.Account;
import app.GUI.CustomPanels.sidebar;
import app.GUI.ImportSlipGUI.ImportSlipGUI;
import app.GUI.OrderGUI.OrderGUI;
import app.GUI.ProductGUI.ProductGUI;

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
			JPanel guiInstance = null;
			if (guiClass.equals(ImportSlipGUI.class)) {
				guiInstance = (JPanel) guiClass.getDeclaredConstructor(MainGUI.class).newInstance(this);
			} else if (guiClass.equals(OrderGUI.class)) {
				guiInstance = (JPanel) guiClass.getDeclaredConstructor(MainGUI.class).newInstance(this);
			} else {
				guiInstance = (JPanel) guiClass.getDeclaredConstructor().newInstance();
			}

			// Giữ nguyên phần setContent
			if (guiInstance != null) {
				setContent(guiInstance);
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
		application.SetTitle("Quản lý cửa hàng điện thoại");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension((int) screenSize.getWidth() - 100, (int) screenSize.getHeight() - 200));
		setMinimumSize(new Dimension(1200, 600));
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

	public void SetTitle(String title) {
		application.SetTitle(title);
	}
}
