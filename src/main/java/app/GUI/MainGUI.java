package app.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private qltaikhoan giaodientaikhoan;
	private qlkho_phieunhap giaodienphieunhap;
	private JPanel contentPane;

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

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Giao diện chính");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 1000);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel(); // biến cục này thành class
		panel.setBackground(new Color(0, 64, 128));
		panel.setBounds(0, 0, 230, 845);
		add(panel);
		
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
					giaodienphieunhap.frmQlkho.setVisible(true);
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
				try {
					giaodientaikhoan = new qltaikhoan();
					giaodientaikhoan.frmQunTaikhoan.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_14.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon tài khoản ( chức năng ).png"));
		btnNewButton_14.setBounds(2, 458, 158, 55);
		btnNewButton_14.setBorderPainted(false); 
		btnNewButton_14.setContentAreaFilled(false);  
		btnNewButton_14.setOpaque(false);
		panel.add(btnNewButton_14);

		// Button to open PermissionGUI
		JButton btnPermission = new JButton("PHÂN QUYỀN");
		btnPermission.setForeground(new Color(255, 255, 255));
		btnPermission.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPermission.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon phân quyền.png")); 
		btnPermission.setBounds(2, 520, 158, 55);
		btnPermission.setBorderPainted(false);
		btnPermission.setContentAreaFilled(false);
		btnPermission.setOpaque(false);
		btnPermission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PermissionGUI permissionGUI = new PermissionGUI();
					permissionGUI.showPermissionTable();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnPermission);
	}
}
