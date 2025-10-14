package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.BUS.EmployeeBUS;
import app.DTO.Employee;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;

public class quanlynhanvien extends JPanel implements FunctionPanel {

	private JTable table;
	private EmployeeBUS bus;
	private khungchucnang khung;
	private JTextField nameSearchField;
	private JComboBox<String> statusComboBox;
	private JLabel noResultLabel;
	private JScrollPane scrollPane;

	public quanlynhanvien() {
		initialize();
	}
	
	private void initialize() {
		bus = new EmployeeBUS();
		setLayout(new BorderLayout());
		noResultLabel = new JLabel("Không tìm thấy kết quả phù hợp.", SwingConstants.CENTER);
		noResultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		noResultLabel.setForeground(Color.RED);
		noResultLabel.setVisible(false);
		add(noResultLabel, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 250));
		topPanel.setLayout(new BorderLayout());
		topPanel.setBackground(new Color(0, 0,0));
		add(topPanel, BorderLayout.NORTH);

		khung = new khungchucnang(this);
		khung.setBorder(new EmptyBorder(50, 0, 50, 0));
		topPanel.add(khung, BorderLayout.WEST);

		FilterPanel filterPanel = new FilterPanel();
		filterPanel.setLayout(new GridLayout(3, 4, 40, 10));
		topPanel.add(filterPanel, BorderLayout.CENTER);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		namePanel.setOpaque(false);
		namePanel.setBackground(null);
		namePanel.setBackground(new Color(255, 0, 0));
		namePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		JLabel nameSearchLabel = new JLabel("Tên nhân viên:");
		nameSearchLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		nameSearchLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		namePanel.add(nameSearchLabel, BorderLayout.WEST);

		nameSearchField = new JTextField(15);
		nameSearchField.setFont(new Font("Arial", Font.PLAIN, 16));
		nameSearchField.setBackground(new Color(240, 240, 240));
		nameSearchField.setBorder(new CompoundBorder(
			BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
			new EmptyBorder(5, 5, 5, 5)));
		nameSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				HandleNameChange();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				HandleNameChange();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				HandleNameChange();
			}
		});
		namePanel.add(nameSearchField, BorderLayout.CENTER);

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new BorderLayout());
		typePanel.setOpaque(false);
		typePanel.setBackground(null);
		typePanel.setBackground(new Color(255, 0, 0));
		typePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		JLabel typeLabel = new JLabel("Trạng thái:");
		typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		typeLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		typePanel.add(typeLabel, BorderLayout.WEST);

		statusComboBox = new JComboBox<>(new String[] { "Tất cả", "Đang làm việc", "Đã nghỉ việc" });
		statusComboBox.setEditable(false);
		statusComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		statusComboBox.setBackground(new Color(240, 240, 240));
		statusComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
		statusComboBox.setBorder(new CompoundBorder(
			BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
			new EmptyBorder(0, 10, 0, 10)));
		
		statusComboBox.addActionListener(e -> {
			Object selectedItem = statusComboBox.getSelectedItem();
			System.out.println("Selected status: " + selectedItem);

			HandleStatusChange(selectedItem);
		});
		typePanel.add(statusComboBox, BorderLayout.CENTER);

		JPanel processPanel = new JPanel();
		processPanel.setBackground(null);
		processPanel.setOpaque(false);
		processPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

	    JButton filterButton = new JButton("Tìm kiếm");
		filterButton.setFont(new Font("Arial", Font.PLAIN, 16));
		filterButton.setBorder(new EmptyBorder(10, 15, 10, 15));
		filterButton.setBackground(Color.blue);
		filterButton.setForeground(Color.white);
		filterButton.setVerticalAlignment(SwingConstants.CENTER);
		filterButton.addActionListener(e -> Search());
		processPanel.add(filterButton);

		JButton refreButton = new JButton("Làm mới");
		refreButton.setFont(new Font("Arial", Font.PLAIN, 16));
		refreButton.setBorder(new EmptyBorder(10, 15, 10, 15));
		refreButton.setBackground(new Color(173, 255, 47));
		refreButton.setForeground(Color.black);
		refreButton.setVerticalAlignment(SwingConstants.CENTER);
		refreButton.addActionListener(e -> Refresh());
		processPanel.add(refreButton);

		filterPanel.add(namePanel);
		filterPanel.add(typePanel);
		filterPanel.add(processPanel);

		JLabel title = new JLabel("DANH SÁCH NHÂN VIÊN", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(0, 10));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(title, BorderLayout.CENTER);

		// Data table 
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		scrollPane =  new JScrollPane();
		String[] columnNames = {"Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "SĐT", "Email", "Trạng thái"};
		List<Employee> employeeList = bus.getAll();
		table = new JTable();
		table.setModel(new DefaultTableModel(
				employeeList.stream()
						.map(e -> new Object[] { e.getEmployeeId(), e.getFullName(),
								e.getGender(), e.getDateOfBirth(), e.getPhoneNumber(), e.getEmail(), e.getStatus() })
						.toArray(Object[][]::new),
				columnNames));
		table.getColumnModel().getColumn(0).setPreferredWidth(94);
		table.getColumnModel().getColumn(1).setPreferredWidth(98);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		table.setIntercellSpacing(new Dimension(8, 4));
		table.setRowHeight(28);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(0, 64, 128));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 16));
		header.setPreferredSize(new Dimension(header.getWidth(), 35));

		((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// DefaultTableCellRenderer rightBoldRenderer = new DefaultTableCellRenderer() {
		// 	@Override
		// 	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		// 		JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		// 		cell.setHorizontalAlignment(SwingConstants.RIGHT);
		// 		cell.setFont(cell.getFont().deriveFont(Font.BOLD));
		// 		return cell;
		// 	}
		// };

		// table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		// table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		// table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		// table.getColumnModel().getColumn(4).setCellRenderer(rightBoldRenderer);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(new Dimension(0, 550));
		listPanel.add(scrollPane, BorderLayout.NORTH);

		add(listPanel, BorderLayout.SOUTH);
	}

	public void HandleNameChange() {
		String name = nameSearchField.getText();
		System.out.println("Searching for name: " + name);
		if (name.isEmpty()) {
			HandleNull();
		}
		List<Employee> filteredEmployees = bus.searchEmployees(name);
		SetDataTable(filteredEmployees);
	}

	public void HandleStatusChange(Object selectedItem) {
		if (selectedItem == null || selectedItem.toString().equals("Tất cả")) {
			HandleNull(); // hiển thị toàn bộ nhân viên
			return;
		}

		int status;
		switch (selectedItem.toString()) {
			case "Đang làm việc":
				status = 1;
				break;
			case "Đã nghỉ việc":
				status = 0;
				break;
			default:
				HandleNull();
				return;
		}

		System.out.println("Filtering by status (int): " + status);
		List<Employee> filteredEmployees = bus.fillterEmployeesByStatus(status);
		SetDataTable(filteredEmployees);
	}

	public void Search() {
		System.out.println("Search employees");
		String keyword = nameSearchField.getText().equals("") ? "" : nameSearchField.getText();
		Object selectedItem = statusComboBox.getSelectedItem();
		String status = (selectedItem == null || selectedItem.toString().equals("Tất cả")) ? "" : selectedItem.toString();
		System.out.println("Search keyword: " + keyword + ", status: " + status);
		List<Employee> filteredEmployees = bus.searchEmployees(keyword);
		if (!status.equals("")) {
			int statusInt;
			switch (status) {
				case "Đang làm việc":
					statusInt = 1;
					break;
				case "Đã nghỉ việc":
					statusInt = 0;
					break;
				default:
					HandleNull();
					return;
			}
			filteredEmployees = filteredEmployees.stream()
				.filter(e -> e.getStatus() == statusInt)
				.toList();
		}
	}

	public void Refresh() {
		nameSearchField.setText("");
		statusComboBox.setSelectedIndex(0);
		List<Employee> allEmployees = bus.getAll();
		SetDataTable(allEmployees);
	}

	public void HandleNull() {
		List<Employee> allEmployees = bus.getAll();
		SetDataTable(allEmployees);
		return;
	}

	public void SetDataTable(List<Employee> employees) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		if (employees.isEmpty()) {
			model.addRow(new Object[] {"", "", "", "", "", "", ""});
			noResultLabel.setVisible(true);
			scrollPane.setPreferredSize(new Dimension(0, 200));
			revalidate();
			repaint();
			return;
		}

		if (noResultLabel != null) {
			noResultLabel.setVisible(false);
			scrollPane.setPreferredSize(new Dimension(0, 600));
			revalidate();
			repaint();
		}

		for (Employee e : employees) {
			model.addRow(new Object[] { e.getEmployeeId(), e.getFullName(),
					e.getGender(), e.getDateOfBirth(), e.getPhoneNumber(), e.getEmail(), e.getStatus() });
		}
	}

	public void Add() {
		System.out.println("Add employee");
	}

	public void Delete() {
		System.out.println("Delete employee");
	}

	public void Edit() {
		System.out.println("Edit employee");
	}

	public void ImportExcel() {
		System.out.println("Import Excel employee");
	}

	public void ExportExcel() {
		System.out.println("Export Excel employee");
	}
}
