package app.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.PhoneStoreApplication;
import app.DTO.Account;
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

	public void initialize() {
		setPreferredSize(new Dimension(1600, 1000));
		setLayout(new BorderLayout());

		navBar = new sidebar(this);
		contentPane = new ProductGUI();
		contentPane.setPreferredSize(new Dimension(0, 0));
		add(navBar, BorderLayout.WEST);
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
		this.navBar.setAccount(newAccount);
	}

	// public void setAccount(Account userAccount) {
	// System.out.println("Check user: " + account == null ? "" :
	// account.getUsername());
	// for (Component c : getComponents()) {
	// if (c instanceof UserAware) {
	// ((UserAware) c).onUserChanged(userAccount);
	// }
	// }
	// }
}
