package app.GUI.PermissionGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.text.NumberFormatter;

import app.BUS.PermissionBUS;
import app.DTO.Permission;

public class UpdatePermissionFrame extends JFrame {
    
    private JTable table;
    private JTextField txtPermissionName;
    private JTextField salaryField;
    private DecimalFormat currencyFormat;
    private NumberFormatter numberFormatter;
    private JComboBox<String> statusComboBox;
    private DefaultTableModel model;

    public Permission updatedPermission;

    private PermissionBUS permissionBUS;

    public UpdatePermissionFrame(String title) {
        super(title);
        this.permissionBUS = new PermissionBUS();
        initializeFormatters();
        initializeUI();
        loadInitialData();
    }

    private void initializeFormatters() {
        currencyFormat = new DecimalFormat("#,##0.00");
        numberFormatter = new NumberFormatter(currencyFormat);
        numberFormatter.setValueClass(BigDecimal.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(new BigDecimal("0.00"));
    }

    private void initializeUI() {
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        // Bảng hiển thị danh sách quyền
        model = new DefaultTableModel(new Object[]{"ID", "Tên quyền", "Lương", "Trạng thái"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel permissionInfoPanel = createPermissionInfoPanel();
        add(permissionInfoPanel, BorderLayout.NORTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                loadSelectedPermission();
            }
        });


        JPanel actionButtonPanel = createActionButtonPanel();
        add(actionButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel createPermissionInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin quyền"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblFunctionName = new JLabel("Tên quyền:");
        lblFunctionName.setPreferredSize(labelSize);
        panel.add(lblFunctionName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtPermissionName = new JTextField(20);
        panel.add(txtPermissionName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel salaryLabel = new JLabel("Lương:");
        salaryLabel.setPreferredSize(labelSize);
        panel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        salaryField = new JTextField(20);
        panel.add(salaryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel statusLabel = new JLabel("Trạng thái:");
        statusLabel.setPreferredSize(labelSize);
        panel.add(statusLabel, gbc);    

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        statusComboBox = new JComboBox<>(new String[]{"Kích hoạt", "Vô hiệu hóa"});
        panel.add(statusComboBox, gbc);

        return panel;
    }

    private JPanel createActionButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnCancel = new JButton("Hủy");

        btnUpdate.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnUpdate);
        panel.add(btnCancel);
        return panel;
    }

    private void loadInitialData() {
        model.setRowCount(0);
        List<Permission> list = permissionBUS.getAll();
        for (Permission p : list) {
            model.addRow(new Object[]{
                p.getPermissionId(),
                p.getPermissionName(),
                p.getSalary(),
                p.getStatus() == 1 ? "Kích hoạt" : "Vô hiệu hóa"
            });
        }
    }

    private void loadSelectedPermission() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        txtPermissionName.setText(model.getValueAt(row, 1).toString());
        salaryField.setText(model.getValueAt(row, 2).toString());
        statusComboBox.setSelectedItem(model.getValueAt(row, 3).toString());

        updatedPermission = new Permission();
        updatedPermission.setPermissionId((int) model.getValueAt(row, 0));
        updatedPermission.setPermissionName(model.getValueAt(row, 1).toString());
        updatedPermission.setSalary(new BigDecimal(model.getValueAt(row, 2).toString()));
        updatedPermission.setStatus(model.getValueAt(row, 3).equals("Kích hoạt") ? 1 : 0);
    }

    private void onSave() {
        String permissionName = txtPermissionName.getText().trim();
        String salaryText = salaryField.getText().trim().replace(",", "");

        if (permissionName.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal salary;
        try {
            salary = new BigDecimal(salaryText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int status = statusComboBox.getSelectedItem().equals("Kích hoạt") ? 1 : 0;

        updatedPermission.setPermissionName(permissionName);
        updatedPermission.setSalary(salary);
        updatedPermission.setStatus(status);

        int result = permissionBUS.updateRole(updatedPermission);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật quyền thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật quyền thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}