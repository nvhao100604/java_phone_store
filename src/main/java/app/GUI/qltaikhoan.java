package app.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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

public class qltaikhoan extends JPanel {

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
					window.setVisible(true);
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
		setLayout(new BorderLayout());
		setForeground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(0, 0));

		JPanel topContainer = new JPanel();
		topContainer.setLayout(new GridLayout(1, 2));
		add(topContainer, BorderLayout.NORTH);

		khungchucnang khungchucnang = new khungchucnang();
		topContainer.add(khungchucnang);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Tìm kiếm", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(0, 300));
		topContainer.add(panel_2, BorderLayout.NORTH);
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
		scrollPane.setPreferredSize(new Dimension(0, 500));
		add(scrollPane, BorderLayout.SOUTH);

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
		add(lblNewLabel_6);
	}
}
