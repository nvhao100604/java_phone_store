package app.GUI.PermissionGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.text.NumberFormatter;

import app.BUS.PermissionBUS;
import app.DTO.Permission;

public class AddPermissionFrame extends JFrame {
    
    private JTextField txtPermissionName;
    private JTextField salaryField;
    private DecimalFormat currencyFormat;
    private NumberFormatter numberFormatter;

    public Permission newPermission;

    private PermissionBUS permissionBUS;

    public AddPermissionFrame(String title) {
        super(title);
        this.permissionBUS = new PermissionBUS();
        initializeFormatters();
        initializeUI();
    }

    private void initializeFormatters() {
        currencyFormat = new DecimalFormat("#,##0.00");
        numberFormatter = new NumberFormatter(currencyFormat);
        numberFormatter.setValueClass(BigDecimal.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(new BigDecimal("0.00"));
    }

    private void initializeUI() {
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel functionInfoPanel = createPermissionInfoPanel();
        add(functionInfoPanel, BorderLayout.NORTH);

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

        return panel;
    }
    
    private JPanel createActionButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAdd = new JButton("Thêm");
        JButton btnCancel = new JButton("Hủy");

        btnAdd.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnAdd);
        panel.add(btnCancel);
        return panel;
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
        newPermission = new Permission(permissionName, salary);
        int newPermissionId = permissionBUS.addNewRole(newPermission);
        if (newPermissionId != -1) {
            newPermission.setPermissionId(newPermissionId);
            JOptionPane.showMessageDialog(this, "Thêm chức vụ thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm chức vụ thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            newPermission = null;
        }
        dispose();
    }
}