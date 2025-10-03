package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.BUS.FunctionBUS;
import app.DTO.Function;

public class sidebar extends JPanel {
	// JButton btnPhiuNhp, btnNewButton_11, btnNewButton_10, btnNewButton_8,
	// btnNewButton_3, btnNewButton_7,
	// btnNewButton_13, btnNewButton_14;
	JLabel lblNewLabel_13, lblNewLabel_14;
	private MainGUI mainGUI;
	private final FunctionBUS bus;

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
		mainGUI.getContentPane().removeAll();
		mainGUI.add(this, BorderLayout.NORTH); // nhớ add lại navbar
		newPanel.setBounds(230, 0, 1354, 845);
		mainGUI.add(newPanel, BorderLayout.CENTER);
		mainGUI.revalidate();
		mainGUI.repaint();
	}

	public sidebar(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		bus = new FunctionBUS();
		initialize();
	}

	private void initialize() {
		setBackground(new Color(0, 64, 128));
		setBounds(0, 0, 230, 845);
		setLayout(null);

		// btnPhiuNhp = new JButton("PHIẾU NHẬP");
		// btnPhiuNhp.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon phiếu
		// nhập.jpg"));
		// btnPhiuNhp.setForeground(new Color(255, 255, 255));
		// btnPhiuNhp.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnPhiuNhp.setBounds(12, 255, 169, 60);
		// btnPhiuNhp.setBorderPainted(false);
		// btnPhiuNhp.setContentAreaFilled(false);
		// btnPhiuNhp.setOpaque(false);
		// btnPhiuNhp.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_11 = new JButton("SẢN PHẨM");
		// btnNewButton_11.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon sản phẩm.png"));
		// btnNewButton_11.setForeground(new Color(255, 255, 255));
		// btnNewButton_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnNewButton_11.setBorderPainted(false);
		// btnNewButton_11.setContentAreaFilled(false);
		// btnNewButton_11.setOpaque(false);
		// btnNewButton_11.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_11.setBounds(2, 135, 157, 76);
		// add(btnNewButton_11);
		// btnNewButton_10 = new JButton("NHÀ CUNG CẤP");
		// btnNewButton_10.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon nhà cung cấp.png"));
		// btnNewButton_10.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_10.setForeground(new Color(255, 255, 255));
		// btnNewButton_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnNewButton_10.setBounds(12, 188, 177, 83);
		// btnNewButton_10.setBorderPainted(false);
		// btnNewButton_10.setContentAreaFilled(false);
		// btnNewButton_10.setOpaque(false);
		// add(btnNewButton_10);
		// add(btnPhiuNhp);
		// btnNewButton_8 = new JButton("THỐNG KÊ");
		// btnNewButton_8.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_8.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon thống kê.png"));
		// btnNewButton_8.setForeground(new Color(255, 255, 255));
		// btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnNewButton_8.setBounds(2, 374, 167, 98);
		// btnNewButton_8.setBorderPainted(false);
		// btnNewButton_8.setContentAreaFilled(false);
		// btnNewButton_8.setOpaque(false);
		// add(btnNewButton_8);
		// btnNewButton_3 = new JButton("NHÂN VIÊN");
		// btnNewButton_3.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon nhân viên.jpg"));
		// btnNewButton_3.setForeground(new Color(255, 255, 255));
		// btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnNewButton_3.setBorderPainted(false);
		// btnNewButton_3.setContentAreaFilled(false);
		// btnNewButton_3.setOpaque(false);
		// btnNewButton_3.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_3.setBounds(10, 73, 149, 76);
		// add(btnNewButton_3);
		// btnNewButton_7 = new JButton("HÓA ĐƠN");
		// btnNewButton_7.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_7.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon hóa đơn.png"));
		// btnNewButton_7.setForeground(new Color(255, 255, 255));
		// btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnNewButton_7.setBounds(2, 315, 167, 70);
		// btnNewButton_7.setBorderPainted(false);
		// btnNewButton_7.setContentAreaFilled(false);
		// btnNewButton_7.setOpaque(false);
		// add(btnNewButton_7);
		// lblNewLabel_13 = new JLabel("");
		// lblNewLabel_13.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon tài khoản ( hiển thị ).png"));
		// lblNewLabel_13.setBounds(25, 691, 63, 55);
		// add(lblNewLabel_13);
		// lblNewLabel_14 = new JLabel("Admin");
		// lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
		// lblNewLabel_14.setForeground(new Color(255, 255, 255));
		// lblNewLabel_14.setBounds(90, 701, 45, 13);
		// add(lblNewLabel_14);
		// btnNewButton_13 = new JButton("Đăng xuất");
		// btnNewButton_13.setBorderPainted(false);
		// btnNewButton_13.setContentAreaFilled(false);
		// btnNewButton_13.setOpaque(false);
		// btnNewButton_13.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// }
		// });
		// btnNewButton_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		// btnNewButton_13.setForeground(new Color(255, 255, 255));
		// btnNewButton_13.setBounds(43, 724, 149, 21);
		// add(btnNewButton_13);
		// btnNewButton_14 = new JButton("TÀI KHOẢN");
		// btnNewButton_14.setForeground(new Color(255, 255, 255));
		// btnNewButton_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		// btnNewButton_14.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// try {
		// qltaikhoan giaodientaikhoan = new qltaikhoan();
		// giaodientaikhoan.frmQunTaikhoan.setVisible(true);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// }
		// });
		// btnNewButton_14.setIcon(new ImageIcon(
		// "src\\main\\resources\\Ảnh\\icon tài khoản ( chức năng ).png"));
		// btnNewButton_14.setBounds(2, 458, 158, 55);
		// btnNewButton_14.setBorderPainted(false);
		// btnNewButton_14.setContentAreaFilled(false);
		// btnNewButton_14.setOpaque(false);
		// add(btnNewButton_14);

		List<Function> functions = bus.getAll();
		// System.out.println(functions.size());
		for (Function function : functions) {
			System.out.println("check function: " + function.getFunctionName());
			SideBarButton button = new SideBarButton(function.getFunctionName(),
					"src\\main\\resources\\Ảnh\\" + function.getIcon());
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InitialGUI(function.getGuiClass());
					System.out.println("check function: " + function.getGuiClass());
				}
			});
			button.setBounds(10, (functions.indexOf(function) * 70), 149, 60);
			add(button);
		}
	}

	public class SideBarButton extends JButton {
		public SideBarButton(String text, String iconPath) {
			super(text);
			setIcon(new ImageIcon(iconPath != "" ? iconPath : ""));
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
