package app.GUI.EmployeeGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import app.BUS.EmployeeBUS;
import app.BUS.AccountBUS;
import app.BUS.PermissionBUS;
import app.DTO.Employee;
import app.DTO.Account;
import app.DTO.Permission;

public class UpdateEmployeeFrame extends JFrame {

    private JTextField txtUsername;
    private JTextField txtPassword;
    private JComboBox<Permission> permissionComboBox;

    private JTextField txtFullName;
    private JComboBox<Employee> genderComboBox;
    private JFormattedTextField txtDOB;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;

    public Account updatedAccount;
    public Employee updatedEmployee;

    private EmployeeBUS employeeBUS;
    private AccountBUS accountBUS;
    private PermissionBUS permissionBUS;
    private int employeeId;
    private quanlynhanvien parentPanel;

    public UpdateEmployeeFrame(String title, quanlynhanvien parentPanel, int employeeId) {
        super(title);
        this.employeeId = employeeId;
        this.employeeBUS = new EmployeeBUS();
        this.parentPanel = parentPanel;
        this.accountBUS = new AccountBUS();
        this.permissionBUS = new PermissionBUS();
        initializeUI();
        loadInitialData();
    }


    private void initializeUI() {
        setSize(800, 300);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel AccountInfoPanel = updateAccountInfoPanel();
        add(AccountInfoPanel, BorderLayout.NORTH);

        JPanel EmployeeInfoPanel = updateEmployeeInfoPanel();
        add(EmployeeInfoPanel, BorderLayout.CENTER);

        JPanel ActionButtonPanel = updateActionButtonPanel();
        add(ActionButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel updateAccountInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 25);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setPreferredSize(labelSize);
        panel.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setPreferredSize(labelSize);
        panel.add(lblPassword, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtPassword = new JTextField(20);
        panel.add(txtPassword, gbc);

        // Permission
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblPermission = new JLabel("Quyền:");
        lblPermission.setPreferredSize(labelSize);
        panel.add(lblPermission, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1;
        permissionComboBox = new JComboBox<>();
        panel.add(permissionComboBox, gbc);

        return panel;
    }

    private JPanel updateEmployeeInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 25);

        // Full Name (1)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblFullName = new JLabel("Họ và tên:");
        lblFullName.setPreferredSize(labelSize);
        panel.add(lblFullName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtFullName = new JTextField(20);
        panel.add(txtFullName, gbc);

        // Gender (1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setPreferredSize(labelSize);
        panel.add(lblGender, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        genderComboBox = new JComboBox<>();
        panel.add(genderComboBox, gbc);

        // Address (1)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblAddress = new JLabel("Địa chỉ:");
        lblAddress.setPreferredSize(labelSize);
        panel.add(lblAddress, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        txtAddress = new JTextField(20);
        panel.add(txtAddress, gbc);

        // Date of Birth (2)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        JLabel lblDOB = new JLabel("Ngày sinh:");
        lblDOB.setPreferredSize(labelSize);
        panel.add(lblDOB, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(sdf);
        txtDOB = new JFormattedTextField(dateFormatter);
        txtDOB.setColumns(20);
        panel.add(txtDOB, gbc);

        // Phone (2)
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setPreferredSize(labelSize);
        panel.add(lblPhone, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1;
        txtPhone = new JTextField(20);
        panel.add(txtPhone, gbc);

        // Email (2)
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setPreferredSize(labelSize);   
        panel.add(lblEmail, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        return panel;
    }

    private JPanel updateActionButtonPanel() {
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
        List<Permission> permissions = permissionBUS.getAll();
        for (Permission perm : permissions) {
            permissionComboBox.addItem(perm);
        }

        List<Employee> genders = employeeBUS.getEmployeeGenderList();
        for (Employee gender : genders) {
            genderComboBox.addItem(gender);
        }

        updatedEmployee = employeeBUS.getEmployeeById(employeeId);
        if (updatedEmployee != null) {
            txtDOB.setValue(updatedEmployee.getDateOfBirth());
            txtAddress.setText(updatedEmployee.getAddress());

            for (int i = 0; i < genderComboBox.getItemCount(); i++) {
                Employee genderItem = genderComboBox.getItemAt(i);
                if (genderItem.getGender().equalsIgnoreCase(updatedEmployee.getGender())) {
                    genderComboBox.setSelectedIndex(i);
                    break;
                }
            }

            updatedAccount = accountBUS.getAccountById(updatedEmployee.getAccountId());
            if (updatedAccount != null) {
                txtUsername.setText(updatedAccount.getUsername());
                txtPassword.setText(updatedAccount.getPassword());
                txtFullName.setText(updatedAccount.getFullName());
                txtPhone.setText(updatedAccount.getPhoneNumber());
                txtEmail.setText(updatedAccount.getEmail());

                for (int i = 0; i < permissionComboBox.getItemCount(); i++) {
                    Permission permItem = permissionComboBox.getItemAt(i);
                    if (permItem.getPermissionId() == updatedAccount.getRoleId()) {
                        permissionComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void onSave() {
        if (updatedEmployee == null || updatedAccount == null) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newUsername = txtUsername.getText().trim();
        String newPassword = txtPassword.getText();
        String newFullName = txtFullName.getText().trim();
        String newPhone = txtPhone.getText().trim();
        String newEmail = txtEmail.getText().trim();
        Permission selectedPermission = (Permission) permissionComboBox.getSelectedItem();

        if (newUsername.isEmpty() || newPassword.isEmpty() || newFullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập, mật khẩu và Họ và tên không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        updatedAccount.setUsername(newUsername);
        updatedAccount.setPassword(newPassword);
        updatedAccount.setFullName(newFullName);
        updatedAccount.setPhoneNumber(newPhone);
        updatedAccount.setEmail(newEmail);
        if (selectedPermission != null) {
            updatedAccount.setRoleId(selectedPermission.getPermissionId());
        }

        String newAddress = txtAddress.getText().trim();
        String newGender = genderComboBox.getSelectedItem() != null ? ((Employee) genderComboBox.getSelectedItem()).getGender() : null;
        
        Date newDOB = null;
        try {
            Object dateValue = txtDOB.getValue();
            if (dateValue instanceof java.util.Date) {
                newDOB = new Date(((java.util.Date) dateValue).getTime());
            } else if (dateValue != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(dateValue.toString());
                newDOB = new Date(parsedDate.getTime());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (newAddress.isEmpty() || newDOB == null || newGender == null) {
            JOptionPane.showMessageDialog(this, "Địa chỉ, Ngày sinh và Giới tính không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            updatedEmployee.setDateOfBirth(newDOB);
            updatedEmployee.setAddress(newAddress);
            updatedEmployee.setGender(newGender);

            int accountResult = accountBUS.updateAccount(updatedAccount);
            int employeeResult = employeeBUS.updateEmployee(updatedEmployee);

            if (accountResult > 0 && employeeResult > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công! ✅", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật không thành công. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}