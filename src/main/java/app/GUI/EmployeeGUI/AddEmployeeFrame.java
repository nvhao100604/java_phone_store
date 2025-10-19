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

import app.BUS.EmployeeBUS;
import app.BUS.PermissionBUS;
import app.BUS.AccountBUS;
import app.DTO.Employee;
import app.DTO.Permission;
import app.DTO.Account;


public class AddEmployeeFrame extends JFrame {

    private JTextField txtUsername;
    private JTextField txtPassword;
    private JComboBox<Permission> permissionComboBox;

    private JTextField txtFullName;
    private JComboBox<Employee> genderComboBox;
    private JFormattedTextField txtDOB;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;

    public Account newAccount;
    public Employee newEmployee;

    private EmployeeBUS employeeBUS;
    private PermissionBUS permissionBUS;
    private AccountBUS accountBUS;

    public AddEmployeeFrame(String title) {
        super(title);
        this.employeeBUS = new EmployeeBUS();
        this.permissionBUS = new PermissionBUS();
        this.accountBUS = new AccountBUS();
        initializeUI();
        loadInitialData();
    }

    private void initializeUI() {
        setSize(800, 300);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel AccountInfoPanel = createAccountInfoPanel();
        add(AccountInfoPanel, BorderLayout.NORTH);

        JPanel EmployeeInfoPanel = createEmployeeInfoPanel();
        add(EmployeeInfoPanel, BorderLayout.CENTER);

        JPanel ActionButtonPanel = createActionButtonPanel();
        add(ActionButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel createAccountInfoPanel() {
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

    private JPanel createEmployeeInfoPanel() {
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
        txtDOB = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
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

    private JPanel createActionButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnSave);
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
    }

    private void onSave() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        int permissionId = permissionComboBox.getSelectedItem() != null ? ((Permission) permissionComboBox.getSelectedItem()).getPermissionId() : -1;
        String fullName = txtFullName.getText();
        String gender = genderComboBox.getSelectedItem() != null ? ((Employee) genderComboBox.getSelectedItem()).getGender() : "";
        Date dob = Date.valueOf(txtDOB.getText());
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        if (username.isEmpty() || password.isEmpty() || permissionId == -1 || fullName.isEmpty() || gender.isEmpty() || dob == null || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        newAccount = new Account(username, password, phone, email, fullName, permissionId);
        int newAccountId = accountBUS.addAccount(newAccount);
        if (newAccountId > 0) {
            newAccount.setAccountId(newAccountId);
        }

        newEmployee = new Employee(newAccountId, gender, dob, address);
        int newEmployeeId = employeeBUS.addEmployee(newEmployee);
        if (newEmployeeId > 0) {
            newEmployee.setEmployeeId(newEmployeeId);
        }

        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}