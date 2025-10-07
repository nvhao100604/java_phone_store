package app.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.Font;

public class qltaikhoan {

	public JFrame frmQunTaikhoan;
	private JTable table;
	private JTextField textField;
	private qlkho_phieunhap giaodienphieunhap;
	private qltaikhoan_gdchinhsua giaodienchinhsua;
	private qltaikhoan_gdthem giaodienthem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					qltaikhoan window = new qltaikhoan();
					window.frmQunTaikhoan.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public qltaikhoan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmQunTaikhoan = new JFrame();
		frmQunTaikhoan.setForeground(new Color(255, 255, 255));
		frmQunTaikhoan.setBackground(new Color(0, 64, 128));
		frmQunTaikhoan.setTitle("Quản lý Tài Khoản");
		frmQunTaikhoan.setBounds(100, 100, 1600, 1000);
		frmQunTaikhoan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmQunTaikhoan.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 64, 128));
		panel.setBounds(0, 0, 230, 845);
		frmQunTaikhoan.getContentPane().add(panel);

		JButton btnPhiuNhp = new JButton("PHIẾU NHẬP");
		btnPhiuNhp.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon phiếu nhập.jpg"));
		btnPhiuNhp.setForeground(new Color(255, 255, 255));
		btnPhiuNhp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPhiuNhp.setBounds(12, 255, 169, 60);
		btnPhiuNhp.setBorderPainted(false);
		btnPhiuNhp.setContentAreaFilled(false);
		btnPhiuNhp.setOpaque(false);
		btnPhiuNhp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					giaodienphieunhap = new qlkho_phieunhap();
					// giaodienphieunhap.frmQlkho.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.setLayout(null);

		JButton btnNewButton_11 = new JButton("SẢN PHẨM");
		btnNewButton_11.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon sản phẩm.png"));
		btnNewButton_11.setForeground(new Color(255, 255, 255));
		btnNewButton_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_11.setBorderPainted(false);
		btnNewButton_11.setContentAreaFilled(false);
		btnNewButton_11.setOpaque(false);
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_11.setBounds(2, 135, 157, 76);
		panel.add(btnNewButton_11);

		JButton btnNewButton_10 = new JButton("NHÀ CUNG CẤP");
		btnNewButton_10.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon nhà cung cấp.png"));
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_10.setForeground(new Color(255, 255, 255));
		btnNewButton_10.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_10.setBounds(12, 188, 177, 83);
		btnNewButton_10.setBorderPainted(false);
		btnNewButton_10.setContentAreaFilled(false);
		btnNewButton_10.setOpaque(false);
		panel.add(btnNewButton_10);
		panel.add(btnPhiuNhp);

		JButton btnNewButton_8 = new JButton("THỐNG KÊ");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_8.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon thống kê.png"));
		btnNewButton_8.setForeground(new Color(255, 255, 255));
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_8.setBounds(2, 374, 167, 98);
		btnNewButton_8.setBorderPainted(false);
		btnNewButton_8.setContentAreaFilled(false);
		btnNewButton_8.setOpaque(false);
		panel.add(btnNewButton_8);

		JButton btnNewButton_3 = new JButton("NHÂN VIÊN");
		btnNewButton_3.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon nhân viên.jpg"));
		btnNewButton_3.setForeground(new Color(255, 255, 255));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_3.setBorderPainted(false);
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setOpaque(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(10, 73, 149, 76);
		panel.add(btnNewButton_3);

		JButton btnNewButton_7 = new JButton("HÓA ĐƠN");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_7.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon hóa đơn.png"));
		btnNewButton_7.setForeground(new Color(255, 255, 255));
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_7.setBounds(2, 315, 167, 70);
		btnNewButton_7.setBorderPainted(false);
		btnNewButton_7.setContentAreaFilled(false);
		btnNewButton_7.setOpaque(false);
		panel.add(btnNewButton_7);

		JLabel lblNewLabel_13 = new JLabel("");
		lblNewLabel_13.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon tài khoản ( hiển thị ).png"));
		lblNewLabel_13.setBounds(25, 691, 63, 55);
		panel.add(lblNewLabel_13);

		JLabel lblNewLabel_14 = new JLabel("Admin");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_14.setForeground(new Color(255, 255, 255));
		lblNewLabel_14.setBounds(90, 701, 45, 13);
		panel.add(lblNewLabel_14);

		JButton btnNewButton_13 = new JButton("Đăng xuất");
		btnNewButton_13.setBorderPainted(false);
		btnNewButton_13.setContentAreaFilled(false);
		btnNewButton_13.setOpaque(false);
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_13.setForeground(new Color(255, 255, 255));
		btnNewButton_13.setBounds(43, 724, 149, 21);
		panel.add(btnNewButton_13);

		JButton btnNewButton_14 = new JButton("TÀI KHOẢN");
		btnNewButton_14.setForeground(new Color(255, 255, 255));
		btnNewButton_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_14.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon tài khoản ( chức năng ).png"));
		btnNewButton_14.setBounds(2, 458, 158, 55);
		btnNewButton_14.setBorderPainted(false);
		btnNewButton_14.setContentAreaFilled(false);
		btnNewButton_14.setOpaque(false);
		panel.add(btnNewButton_14);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(229, 0, 660, 142);
		frmQunTaikhoan.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("");
		btnNewButton.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon thêm.jpg"));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					giaodienthem = new qltaikhoan_gdthem();
					giaodienthem.frmThmTiKhon.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(20, 33, 55, 50);
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon xóa.jpg"));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(85, 33, 66, 50);
		panel_1.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon chỉnh sửa.jpg"));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					giaodienchinhsua = new qltaikhoan_gdchinhsua();
					giaodienchinhsua.frmThmTiKhon.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(171, 33, 66, 50);
		panel_1.add(btnNewButton_2);

		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon nhập excel.jpg"));
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBounds(260, 33, 55, 50);
		panel_1.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon xuất excel.jpg"));
		btnNewButton_5.setBorderPainted(false);
		btnNewButton_5.setContentAreaFilled(false);
		btnNewButton_5.setBounds(343, 33, 55, 50);
		panel_1.add(btnNewButton_5);

		JLabel lblNewLabel = new JLabel("Thêm");
		lblNewLabel.setBounds(30, 89, 33, 28);
		panel_1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Xóa");
		lblNewLabel_1.setBounds(106, 97, 25, 13);
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Chỉnh sửa");
		lblNewLabel_2.setBounds(171, 97, 83, 13);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("Nhập excel");
		lblNewLabel_4.setBounds(260, 97, 84, 13);
		panel_1.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Xuất excel");
		lblNewLabel_5.setBounds(343, 97, 73, 13);
		panel_1.add(lblNewLabel_5);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Tìm kiếm", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(889, 0, 651, 142);
		frmQunTaikhoan.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JButton btnNewButton_6 = new JButton("Làm mới\r\n");
		btnNewButton_6.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon làm mới.jpg"));
		btnNewButton_6.setBounds(412, 51, 140, 55);
		panel_2.add(btnNewButton_6);

		textField = new JTextField();
		textField.setBounds(180, 68, 222, 21);
		panel_2.add(textField);
		textField.setColumns(10);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "-- Chọn --", "mã tài khoản", "username",
				"số điện thoại", "email", "họ tên", "mã quyền hạn", "trạng thái" }));
		comboBox.setBounds(45, 68, 125, 21);
		panel_2.add(comboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(229, 199, 1311, 646);
		frmQunTaikhoan.getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"M\u00E3 t\u00E0i kho\u1EA3n", "T\u00EAn t\u00E0i kho\u1EA3n", "M\u1EADt kh\u1EA9u",
						"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "\u0110\u1ECBa ch\u1EC9 Email", "H\u1ECD v\u00E0 T\u00EAn",
						"M\u00E3 quy\u1EC1n h\u1EA1n", "Tr\u1EA1ng th\u00E1i"
				}) {
			boolean[] columnEditables = new boolean[] {
					true, true, false, false, false, false, false, true
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(1).setPreferredWidth(98);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_6 = new JLabel("DANH SÁCH TÀI KHOẢN");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(807, 162, 195, 27);
		frmQunTaikhoan.getContentPane().add(lblNewLabel_6);
	}
}
