package app.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class khungchucnang extends JPanel {
	JButton btnNewButton, btnNewButton_1, btnNewButton_2, btnNewButton_4, btnNewButton_5;
	JLabel lblNewLabel, lblNewLabel_1, lblNewLabel_2, lblNewLabel_4, lblNewLabel_5;

	public khungchucnang() {
		initialize();
	}

	private void initialize() {
		setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		setBackground(new Color(255, 255, 255));
		setBounds(229, 0, 660, 142);
		setLayout(null);
		btnNewButton = new JButton("");
		btnNewButton.setToolTipText("");
		btnNewButton.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon thêm.png"));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(20, 33, 55, 50);
		add(btnNewButton);
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon xóa.png"));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(85, 33, 66, 50);
		add(btnNewButton_1);
		btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon chỉnh sửa.png"));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(171, 33, 66, 50);
		add(btnNewButton_2);
		btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon nhập excel.png"));
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBounds(260, 33, 55, 50);
		add(btnNewButton_4);
		btnNewButton_5 = new JButton("");
		btnNewButton_5.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon xuất excel.png"));
		btnNewButton_5.setBorderPainted(false);
		btnNewButton_5.setContentAreaFilled(false);
		btnNewButton_5.setBounds(343, 33, 55, 50);
		add(btnNewButton_5);
		lblNewLabel = new JLabel("Thêm");
		lblNewLabel.setBounds(30, 89, 33, 28);
		add(lblNewLabel);
		lblNewLabel_1 = new JLabel("Xóa");
		lblNewLabel_1.setBounds(106, 97, 25, 13);
		add(lblNewLabel_1);
		lblNewLabel_2 = new JLabel("Chỉnh sửa");
		lblNewLabel_2.setBounds(171, 97, 83, 13);
		add(lblNewLabel_2);
		lblNewLabel_4 = new JLabel("Nhập excel");
		lblNewLabel_4.setBounds(260, 97, 84, 13);
		add(lblNewLabel_4);
		lblNewLabel_5 = new JLabel("Xuất excel");
		lblNewLabel_5.setBounds(343, 97, 73, 13);
		add(lblNewLabel_5);
	}
}
