package app.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class qltaikhoan_gdchinhsua {

	public JFrame frmThmTiKhon;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					qltaikhoan_gdchinhsua window = new qltaikhoan_gdchinhsua();
					window.frmThmTiKhon.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public qltaikhoan_gdchinhsua() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmThmTiKhon = new JFrame();
		frmThmTiKhon.setTitle("Chỉnh Sửa Tài Khoản");
		frmThmTiKhon.setResizable(false);
		frmThmTiKhon.setBounds(100, 100, 555, 280);
		frmThmTiKhon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmThmTiKhon.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("THÔNG TIN TÀI KHOẢN");
		lblNewLabel.setEnabled(true);
		lblNewLabel.setBounds(200, 10, 138, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên tài khoản:");
		lblNewLabel_1.setBounds(10, 48, 87, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(107, 45, 130, 19);
		frmThmTiKhon.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu:");
		lblNewLabel_2.setBounds(10, 84, 66, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(107, 81, 130, 19);
		frmThmTiKhon.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Mã quyền hạn:");
		lblNewLabel_3.setBounds(10, 125, 87, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(107, 122, 130, 19);
		frmThmTiKhon.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Họ và Tên:");
		lblNewLabel_4.setBounds(309, 48, 78, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setBounds(394, 45, 130, 19);
		frmThmTiKhon.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Email:");
		lblNewLabel_5.setBounds(309, 84, 49, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_5);
		
		textField_4 = new JTextField();
		textField_4.setBounds(394, 81, 130, 19);
		frmThmTiKhon.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Số điện thoại:");
		lblNewLabel_6.setBounds(309, 125, 78, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_6);
		
		textField_5 = new JTextField();
		textField_5.setBounds(394, 122, 130, 19);
		frmThmTiKhon.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnNewButton = new JButton("Xác nhận");
		btnNewButton.setBounds(44, 203, 101, 21);
		frmThmTiKhon.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Làm mới");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(213, 203, 85, 21);
		frmThmTiKhon.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Hủy");
		btnNewButton_2.setBounds(376, 203, 85, 21);
		frmThmTiKhon.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_7 = new JLabel("Trạng thái:");
		lblNewLabel_7.setBounds(10, 163, 66, 13);
		frmThmTiKhon.getContentPane().add(lblNewLabel_7);
		
		textField_6 = new JTextField();
		textField_6.setBounds(107, 160, 130, 19);
		frmThmTiKhon.getContentPane().add(textField_6);
		textField_6.setColumns(10);
	}
}
