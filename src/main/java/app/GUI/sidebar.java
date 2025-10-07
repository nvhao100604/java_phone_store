package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
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
import app.DTO.Function;

public class sidebar extends JPanel {
	// JButton btnPhiuNhp, btnNewButton_11, btnNewButton_10, btnNewButton_8,
	// btnNewButton_3, btnNewButton_7,
	// btnNewButton_13, btnNewButton_14;
	JLabel lblNewLabel_13, lblNewLabel_14;
	private MainGUI mainGUI;
	private final FunctionBUS bus;

	public sidebar(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		bus = new FunctionBUS();
		initialize();
	}

	private void initialize() {
		setBackground(new Color(0, 64, 128));
		setBounds(0, 0, 230, 845);
		setLayout(null);

		List<Function> functions = bus.getAll();
		// System.out.println(functions.size());
		for (Function function : functions) {
			System.out.println("check function: " + function.getFunctionName());
			SideBarButton button = new SideBarButton(function.getFunctionName(),
					"src\\main\\resources\\Ảnh\\" + function.getIcon());
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainGUI.InitialGUI(function.getGuiClass());
					System.out.println("check function: " + function.getGuiClass());
				}
			});
			button.setBounds(10, (functions.indexOf(function) * 70), 180, 70);
			add(button);
		}
	}

	public class SideBarButton extends JButton {
		public SideBarButton(String text, String iconPath) {
			super(text);
			ImageIcon originalIcon = new ImageIcon(iconPath != "" ? iconPath : "");
			Image originalImage = originalIcon.getImage();
			int newWidth = 32;
			int newHeight = 32;
			Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			setHorizontalAlignment(SwingConstants.LEFT);
			setHorizontalTextPosition(SwingConstants.RIGHT);
			setIcon(scaledIcon);
			setForeground(new Color(255, 255, 255));
			setFont(new Font("Tahoma", Font.BOLD, 11));
			setBorderPainted(false);
			setContentAreaFilled(false);
			setOpaque(false);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			// setBounds(10, 73, 149, 76);
		}
	}
}
