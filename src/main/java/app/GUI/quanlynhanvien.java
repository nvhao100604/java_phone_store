package app.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;

import app.BUS.EmployeeBUS;
import app.DTO.Employee;
import app.DTO.Product;

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
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

public class quanlynhanvien extends JPanel {

	private EmployeeBUS bus;
	// private JFrame frmQunLKho;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					quanlynhanvien window = new quanlynhanvien();
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
	public quanlynhanvien() {
		this.bus = new EmployeeBUS();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frmQunLKho = new JFrame();
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(0, 64, 128));
		setLayout(new BorderLayout());

		JPanel topContainer = new JPanel();
		topContainer.setPreferredSize(new Dimension(0, 200));
		topContainer.setLayout(new GridLayout(1, 2));
		add(topContainer, BorderLayout.NORTH);

		khungchucnang khungchucnang = new khungchucnang();
		topContainer.add(khungchucnang);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Tìm kiếm", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(889, 0, 651, 142);
		topContainer.add(panel_2);
		panel_2.setLayout(null);

		JButton btnNewButton_6 = new JButton("Làm mới\r\n");
		btnNewButton_6.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon làm mới.jpg"));
		btnNewButton_6.setBounds(439, 34, 140, 55);
		panel_2.add(btnNewButton_6);

		JLabel lblNewLabel_10 = new JLabel("Mã nhân viên:");
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
		lblNewLabel_12.setBounds(224, 86, 20, 13);
		panel_2.add(lblNewLabel_12);

		textField_6 = new JTextField();
		textField_6.setBounds(248, 86, 96, 19);
		panel_2.add(textField_6);
		textField_6.setColumns(10);

		JButton btnNewButton_9 = new JButton("");
		btnNewButton_9.setBounds(190, 86, 68, 21);
		panel_2.add(btnNewButton_9);
		btnNewButton_9.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon chọn ngày.jpg"));
		btnNewButton_9.setBorderPainted(false);
		btnNewButton_9.setContentAreaFilled(false);
		btnNewButton_9.setOpaque(false);

		JButton btnNewButton_12 = new JButton("");
		btnNewButton_12.setBounds(322, 86, 85, 21);
		panel_2.add(btnNewButton_12);
		btnNewButton_12.setIcon(new ImageIcon("src\\main\\resources\\Ảnh\\icon chọn ngày.jpg"));
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
						null);
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
						null);
			}
		});

		JLabel lblNewLabel_6 = new JLabel("DANH SÁCH NHÂN VIÊN");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		// lblNewLabel_6.setBounds(812, 162, 195, 27);
		add(lblNewLabel_6, BorderLayout.CENTER);

		JPanel filterPanel = new JPanel();
		filterPanel.setPreferredSize(new Dimension(0, 100));
		filterPanel.setLayout(new GridLayout(3, 4));
		filterPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

		add(filterPanel, BorderLayout.CENTER);
		JLabel lblNewLabel_3 = new JLabel("Mã nhân viên:");
		// lblNewLabel_3.setBounds(262, 216, 111, 13);
		filterPanel.add(lblNewLabel_3);

		textField_1 = new JTextField();
		// textField_1.setEditable(false);
		// textField_1.setBounds(383, 213, 269, 19);
		// textField_1.setPreferredSize(new Dimension(0, 50));
		filterPanel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Tình trạng: ");
		// lblNewLabel_7.setBounds(262, 319, 60, 13);
		filterPanel.add(lblNewLabel_7);

		textField_2 = new JTextField();
		// textField_2.setEditable(false);
		// textField_2.setBounds(383, 263, 269, 19);
		// textField_2.setPreferredSize(new Dimension(0, 50));
		filterPanel.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("Ngày sinh");
		// lblNewLabel_8.setBounds(947, 216, 60, 13);
		filterPanel.add(lblNewLabel_8);

		textField_3 = new JTextField();
		// textField_3.setEditable(false);
		// textField_3.setPreferredSize(new Dimension(0, 50));
		// textField_3.setBounds(1058, 213, 230, 19);
		filterPanel.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Địa chỉ: ");
		// lblNewLabel_9.setBounds(947, 266, 101, 13);
		filterPanel.add(lblNewLabel_9);

		textField_4 = new JTextField();
		// textField_4.setEditable(false);
		// textField_4.setPreferredSize(new Dimension(0, 50));
		// textField_4.setBounds(1058, 263, 230, 19);
		filterPanel.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblNewLabel_7_1 = new JLabel("Giới tính:");
		// lblNewLabel_7_1.setBounds(262, 266, 60, 13);
		filterPanel.add(lblNewLabel_7_1);

		textField_7 = new JTextField();
		// textField_7.setEditable(false);
		textField_7.setColumns(10);
		// textField_7.setBounds(383, 316, 269, 19);
		// textField_7.setPreferredSize(new Dimension(0, 50));
		filterPanel.add(textField_7);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(0, 600));
		add(scrollPane, BorderLayout.SOUTH);

		String[] columnNames = { "Mã nhân viên", "Tên nhân viên", "SĐT", "Email", "Ngày sinh" };
		List<Employee> employeeList = bus.getAll();
		System.out.println("check list: " + employeeList.size());
		table = new JTable();
		table.setModel(new DefaultTableModel(
				employeeList.stream()
						.map(e -> new Object[] { e.getEmployeeId(), e.getFullName(),
								e.getPhoneNumber(), e.getEmail(), e.getDateOfBirth() })
						.toArray(Object[][]::new),
				columnNames) {
			boolean[] columnEditables = new boolean[] {
					true, true, false, false, false
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(94);
		table.getColumnModel().getColumn(1).setPreferredWidth(98);
		scrollPane.setViewportView(table);

	}
}
