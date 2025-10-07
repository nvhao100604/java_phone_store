package app.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import app.BUS.PermissionBUS;
import app.DTO.Permission;
import app.DTO.Function;
import app.BUS.FunctionBUS;

public class PermissionGUI extends JPanel {

	public PermissionGUI() {
		setLayout(new BorderLayout());
		showPermissionTable();
	}

	public void showPermissionTable() {
		PermissionBUS bus = new PermissionBUS();
		FunctionBUS functionBus = new FunctionBUS();
		List<Permission> roles = bus.getRole();
		List<Function> functions = functionBus.getFunction();

		String[] columnNames = new String[roles.size() + 1];
		columnNames[0] = "Chức năng";
		for (int i = 0; i < roles.size(); i++) {
			columnNames[i + 1] = roles.get(i).getPermissionName();
		}

		Object[][] data = new Object[functions.size()][roles.size() + 1];
		for (int i = 0; i < functions.size(); i++) {
			data[i][0] = functions.get(i).getFunctionName();
			for (int j = 0; j < roles.size(); j++) {
				boolean hasPermission = bus.checkPermission(roles.get(j).getPermissionId(),
						functions.get(i).getFunctionId());
				data[i][j + 1] = hasPermission;
			}
		}

		// Model tùy chỉnh để hiển thị checkbox
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0)
					return String.class; // cột đầu là text
				return Boolean.class; // còn lại là checkbox
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column > 0; // chỉ cho phép tick vào các cột quyền
			}
		};

		model.addTableModelListener(e -> {
			int row = e.getFirstRow();
			int column = e.getColumn();

			// Bỏ qua cột chức năng (cột 0)
			if (column > 0) {
				Boolean isChecked = (Boolean) model.getValueAt(row, column);

				// Lấy roleId và functionId tương ứng
				int roleId = roles.get(column - 1).getPermissionId();
				int functionId = functions.get(row).getFunctionId();

				if (isChecked) {
					// Thêm quyền
					bus.addPermission(roleId, functionId);
				} else {
					// Xóa quyền
					bus.removePermission(roleId, functionId);
				}
			}
		});

		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}
}
