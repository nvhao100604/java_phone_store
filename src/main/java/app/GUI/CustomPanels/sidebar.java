package app.GUI.CustomPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.BUS.FunctionBUS;
import app.DTO.Account;
import app.DTO.Function;
import app.GUI.MainGUI;

public class sidebar extends JPanel {
	JLabel lblNewLabel_13, lblNewLabel_14;
	private MainGUI mainGUI;
	private final FunctionBUS bus;
	private JPanel navPanel;
	private AccountPanel accountPanel;
	private Account userACcount;

	public sidebar(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		bus = new FunctionBUS();
		accountPanel = new AccountPanel(mainGUI);
		navPanel = new JPanel();
		this.userACcount = mainGUI.getAccount();
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(250, 0));
		setBackground(new Color(0, 64, 128));
		setLayout(new BorderLayout());
	}

	public class SideBarButton extends JButton {
		public SideBarButton(String text, String iconPath) {
			super(text);
			ImageIcon originalIcon = new ImageIcon(iconPath != "" ? iconPath : "");
			Image originalImage = originalIcon.getImage();
			int newWidth = 20;
			int newHeight = 20;
			Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			setHorizontalAlignment(SwingConstants.LEFT);
			setHorizontalTextPosition(SwingConstants.RIGHT);
			setIcon(scaledIcon);
			setForeground(new Color(255, 255, 255));
			setFont(new Font("Tahoma", Font.BOLD, 15));
			setBorderPainted(false);
			setContentAreaFilled(false);
			// setOpaque(false);
		}
	}

	public void GenerateSideBar() {
		navPanel.setLayout(new GridLayout(0, 1, 0, 0));
		navPanel.setBackground(null);
		List<Function> functions = bus.getFunctionsForRole(userACcount.getRoleId());
		// System.out.println(functions.size());
		for (Function function : functions) {
			System.out.println("check function: " + function.getFunctionName());
			SideBarButton button = new SideBarButton(function.getFunctionName(),
					"src\\main\\resources\\Ảnh\\" + function.getIcon());
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainGUI.SetTitle("Quản lý cửa hàng điện thoại | " + function.getFunctionName());
					mainGUI.InitialGUI(function.getGuiClass());
					System.out.println("check function: " + function.getGuiClass());
				}
			});
			button.setPreferredSize(new Dimension(250, 40));
			navPanel.add(button);
		}
		add(navPanel, BorderLayout.NORTH);
		add(accountPanel, BorderLayout.SOUTH);
	}

	public void setAccount(Account userAccount) {
		this.userACcount = userAccount;
		this.accountPanel = new AccountPanel(mainGUI);
		removeAll();
		GenerateSideBar();
	}
}
