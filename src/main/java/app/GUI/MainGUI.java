package app.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private sidebar navBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// public void LoadGUI(String gui_name) {
	// switch (gui_name) {
	// case "":

	// break;

	// default:
	// break;
	// }
	// }

	/**
	 * Create the frame.
	 */

	public static MainGUI getInstance() {
		return new MainGUI();
	}

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
		getContentPane().removeAll();
		contentPane = newPanel;
		contentPane.setPreferredSize(new Dimension(0, 0));
		getContentPane().add(navBar, BorderLayout.WEST);
		getContentPane().add(contentPane, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public MainGUI() {
		setTitle("Giao diện chính");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);
		setLayout(new BorderLayout());

		navBar = new sidebar(this);
		contentPane = new ProductGUI();
		contentPane.setPreferredSize(new Dimension(0, 0));
		add(navBar, BorderLayout.WEST);
		add(contentPane, BorderLayout.CENTER);
		setVisible(true);
	}
}
