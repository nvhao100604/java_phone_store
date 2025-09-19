package app.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
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

public class qlkho_phieunhap {

	private JFrame frmQunLKho;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					qlkho_phieunhap window = new qlkho_phieunhap();
					window.frmQunLKho.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public qlkho_phieunhap() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmQunLKho = new JFrame();
		frmQunLKho.setForeground(new Color(255, 255, 255));
		frmQunLKho.setBackground(new Color(0, 64, 128));
		frmQunLKho.setTitle("Quản lý phiếu nhập");
		frmQunLKho.setBounds(100, 100, 1600, 1000);
		frmQunLKho.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmQunLKho.getContentPane().setLayout(null);

		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 64, 128));
		panel.setBounds(0, 0, 230, 845);
		frmQunLKho.getContentPane().add(panel);
		
		JButton btnPhiuNhp = new JButton("PHIẾU NHẬP");
		btnPhiuNhp.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon phiếu nhập.jpg"));
		btnPhiuNhp.setForeground(new Color(255, 255, 255));
		btnPhiuNhp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPhiuNhp.setBounds(12, 255, 169, 60);
		btnPhiuNhp.setBorderPainted(false); 
		btnPhiuNhp.setContentAreaFilled(false);  
		btnPhiuNhp.setOpaque(false);
		btnPhiuNhp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.setLayout(null);
		
		JButton btnNewButton_11 = new JButton("SẢN PHẨM");
		btnNewButton_11.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon sản phẩm.png"));
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
		btnNewButton_10.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon nhà cung cấp.png"));
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
		btnNewButton_8.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon thống kê.png"));
		btnNewButton_8.setForeground(new Color(255, 255, 255));
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_8.setBounds(2, 374, 167, 98);
		btnNewButton_8.setBorderPainted(false); 
		btnNewButton_8.setContentAreaFilled(false);  
		btnNewButton_8.setOpaque(false);
		panel.add(btnNewButton_8);
		
		JButton btnNewButton_3 = new JButton("NHÂN VIÊN");
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon nhân viên.jpg"));
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
		btnNewButton_7.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon hóa đơn.png"));
		btnNewButton_7.setForeground(new Color(255, 255, 255));
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_7.setBounds(2, 315, 167, 70);
		btnNewButton_7.setBorderPainted(false); 
		btnNewButton_7.setContentAreaFilled(false);  
		btnNewButton_7.setOpaque(false);
		panel.add(btnNewButton_7);
		
		JLabel lblNewLabel_13 = new JLabel("");
		lblNewLabel_13.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon tài khoản ( hiển thị ).png"));
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
		btnNewButton_14.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon tài khoản ( chức năng ).png"));
		btnNewButton_14.setBounds(2, 458, 158, 55);
		btnNewButton_14.setBorderPainted(false); 
		btnNewButton_14.setContentAreaFilled(false);  
		btnNewButton_14.setOpaque(false);
		panel.add(btnNewButton_14);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(229, 0, 660, 142);
		frmQunLKho.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon thêm.jpg"));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(20, 33, 55, 50);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon xóa.jpg"));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(85, 33, 66, 50);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon chỉnh sửa.jpg"));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBounds(171, 33, 66, 50);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon nhập excel.jpg"));
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBounds(260, 33, 55, 50);
		panel_1.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon xuất excel.jpg"));
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
		frmQunLKho.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton_6 = new JButton("Làm mới\r\n");
		btnNewButton_6.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon làm mới.jpg"));
		btnNewButton_6.setBounds(439, 34, 140, 55);
		panel_2.add(btnNewButton_6);
		
		JLabel lblNewLabel_10 = new JLabel("Mã phiếu nhập:");
		lblNewLabel_10.setBounds(39, 34, 100, 13);
		panel_2.add(lblNewLabel_10);
		
		textField = new JTextField();
		textField.setBounds(127, 34, 163, 19);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_11 = new JLabel("Thời gian:");
		lblNewLabel_11.setBounds(39, 89, 70, 13);
		panel_2.add(lblNewLabel_11);
		
		textField_5 = new JTextField();
		textField_5.setBounds(111, 86, 96, 19);
		panel_2.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("-");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_12.setBounds(238, 86, 20, 13);
		panel_2.add(lblNewLabel_12);
		
		textField_6 = new JTextField();
		textField_6.setBounds(248, 86, 96, 19);
		panel_2.add(textField_6);
		textField_6.setColumns(10);
		
		JButton btnNewButton_9 = new JButton("");
		btnNewButton_9.setBounds(190, 86, 68, 21);
		panel_2.add(btnNewButton_9);
		btnNewButton_9.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon chọn ngày.jpg"));
		btnNewButton_9.setBorderPainted(false); 
		btnNewButton_9.setContentAreaFilled(false);  
		btnNewButton_9.setOpaque(false);
		
		JButton btnNewButton_12 = new JButton("");
		btnNewButton_12.setBounds(322, 86, 85, 21);
		panel_2.add(btnNewButton_12);
		btnNewButton_12.setIcon(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\Ngôn ngữ lập trình Java\\Đồ án Nhóm\\Source code ( tổng hợp )\\java_phone_store-main\\src\\main\\resources\\Ảnh\\icon chọn ngày.jpg"));
		btnNewButton_12.setBorderPainted(false); 
		btnNewButton_12.setContentAreaFilled(false);  
		btnNewButton_12.setOpaque(false);
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpinnerDateModel model = new SpinnerDateModel();
				JSpinner dateSpinner = new JSpinner(model);
				dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

				int option = JOptionPane.showOptionDialog(
				    null,
				    dateSpinner,
				    "Chọn thời gian",
				    JOptionPane.OK_CANCEL_OPTION,
				    JOptionPane.PLAIN_MESSAGE,
				    null,
				    null,
				    null
				);
			}
		});
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpinnerDateModel model = new SpinnerDateModel();
				JSpinner dateSpinner = new JSpinner(model);
				dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

				int option = JOptionPane.showOptionDialog(
				    null,
				    dateSpinner,
				    "Chọn thời gian",
				    JOptionPane.OK_CANCEL_OPTION,
				    JOptionPane.PLAIN_MESSAGE,
				    null,
				    null,
				    null
				);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(229, 345, 1311, 72);
		frmQunLKho.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"M\u00E3 phi\u1EBFu nh\u1EADp", "Ng\u00E0y nh\u1EADp phi\u1EBFu", "Ng\u01B0\u1EDDi nh\u1EADp", "Nh\u00E0 cung c\u1EA5p", "Th\u00E0nh ti\u1EC1n", "L\u1EE3i nhu\u1EADn", "Tr\u1EA1ng th\u00E1i"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(94);
		table.getColumnModel().getColumn(1).setPreferredWidth(98);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_6 = new JLabel("DANH SÁCH PHIẾU NHẬP");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(812, 162, 195, 27);
		frmQunLKho.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_3 = new JLabel("Mã phiếu:");
		lblNewLabel_3.setBounds(262, 216, 60, 13);
		frmQunLKho.getContentPane().add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(333, 213, 269, 19);
		frmQunLKho.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Ngày lập:");
		lblNewLabel_7.setBounds(262, 266, 60, 13);
		frmQunLKho.getContentPane().add(lblNewLabel_7);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(333, 263, 269, 19);
		frmQunLKho.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Người lập:");
		lblNewLabel_8.setBounds(947, 216, 60, 13);
		frmQunLKho.getContentPane().add(lblNewLabel_8);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(1058, 213, 230, 19);
		frmQunLKho.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Nhà cung cấp:");
		lblNewLabel_9.setBounds(947, 266, 101, 13);
		frmQunLKho.getContentPane().add(lblNewLabel_9);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(1058, 263, 230, 19);
		frmQunLKho.getContentPane().add(textField_4);
		textField_4.setColumns(10);
	}
}
