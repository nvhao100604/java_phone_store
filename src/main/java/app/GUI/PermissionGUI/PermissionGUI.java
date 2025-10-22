package app.GUI.PermissionGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.List;
import app.BUS.PermissionBUS;
import app.DTO.Permission;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;
import app.utils.DataTable;
import app.DTO.Function;
import app.BUS.FunctionBUS;

public class PermissionGUI extends JPanel implements FunctionPanel {

	private JTable table;
	private PermissionBUS permissionBUS;
	private FunctionBUS functionBUS;
	private khungchucnang khung;
	private JLabel noResultLabel;

	public PermissionGUI() {
		initialize();
	}

	public void initialize() {
		permissionBUS = new PermissionBUS();
		functionBUS = new FunctionBUS();

		setLayout(new BorderLayout());
        noResultLabel = new JLabel("Không tìm thấy kết quả phù hợp.", SwingConstants.CENTER);
        noResultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        noResultLabel.setForeground(Color.RED);
        noResultLabel.setVisible(false);
        add(noResultLabel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, 250));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        topPanel.setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        khung = new khungchucnang(this);
        topPanel.add(khung, BorderLayout.WEST);

		// ----- Bảng dữ liệu -----
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		List<Permission> roles = permissionBUS.getRole();
		List<Function> functions = functionBUS.getFunction();

		String[] columnNames = new String[roles.size() + 1];
		columnNames[0] = "Chức năng";
		for (int i = 0; i < roles.size(); i++) {
			columnNames[i + 1] = roles.get(i).getPermissionName();
		}

		Object[][] data = new Object[functions.size()][roles.size() + 1];
		for (int i = 0; i < functions.size(); i++) {
			data[i][0] = functions.get(i).getFunctionName();
			for (int j = 0; j < roles.size(); j++) {
				boolean hasPermission = permissionBUS.checkPermission(
						roles.get(j).getPermissionId(),
						functions.get(i).getFunctionId());
				data[i][j + 1] = hasPermission;
			}
		}

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0)
					return String.class; 
				return Boolean.class; 
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column > 0; 
			}
		};

		model.addTableModelListener(e -> {
			int row = e.getFirstRow();
			int column = e.getColumn();

			if (column > 0) {
				Boolean isChecked = (Boolean) model.getValueAt(row, column);

				int roleId = roles.get(column - 1).getPermissionId();
				int functionId = functions.get(row).getFunctionId();

				if (isChecked) {
					permissionBUS.addPermission(roleId, functionId);
				} else {
					permissionBUS.removePermission(roleId, functionId);
				}
			}
		});

		JTable table = new JTable(model);
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

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		scrollPane.setPreferredSize(new Dimension(0, 500));

		listPanel.add(scrollPane, BorderLayout.CENTER);
		add(listPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	public void Add() {
		AddPermissionFrame addFrame = new AddPermissionFrame("Thêm chức năng mới");
		addFrame.setVisible(true);	
	}

	public void Edit() {
		UpdatePermissionFrame updateFrame = new UpdatePermissionFrame("Cập nhật chức vụ");
		updateFrame.setVisible(true);
	}

	public void ExportExcel() {
		String savePath = DataTable.chooseFolder(this, "Permission.xlsx");
		DataTable.exportDataToExcel(savePath, table);
		JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}

	public void Delete() {}
	public void ImportExcel() {}
}