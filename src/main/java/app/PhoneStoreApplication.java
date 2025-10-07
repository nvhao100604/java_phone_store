package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import app.GUI.MainGUI;

public class PhoneStoreApplication {
	private MainGUI frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhoneStoreApplication window = new PhoneStoreApplication();
					window.frame.setVisible(true);
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
		frame = new MainGUI();
		// frame.setBounds(100, 100);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
