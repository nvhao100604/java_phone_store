package app.GUI.OrderGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import app.BUS.CustomerBUS;
import app.DTO.Customer;

public class AddCustomerFrame extends JDialog {

    private JTextField hotenField;
    private JTextField sdtField;
    private JTextField diachiField;
    private JButton saveButton;
    private JButton cancelButton;

    private CustomerBUS customerBUS;
    private Customer newCustomer = null;

    public AddCustomerFrame(Frame owner) {
        super(owner, "Thêm Khách hàng mới", true);
        this.customerBUS = new CustomerBUS();

        setSize(450, 280);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = (JPanel) getContentPane();
        contentPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Hàng 0: Họ tên ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Họ tên:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        hotenField = new JTextField(20);
        panel.add(hotenField, gbc);

        // --- Hàng 1: Số điện thoại ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Số điện thoại:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sdtField = new JTextField(20);
        panel.add(sdtField, gbc);

        // --- Hàng 2: Địa chỉ ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Địa chỉ:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        diachiField = new JTextField(20);
        panel.add(diachiField, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");

        panel.add(cancelButton);
        panel.add(saveButton);

        return panel;
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> handleSaveCustomer());
    }

    private void handleSaveCustomer() {
        String hoten = hotenField.getText().trim();
        String sdt = sdtField.getText().trim();
        String diachi = diachiField.getText().trim();

        if (hoten.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Họ tên và Số điện thoại là bắt buộc.",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer newCustomer = new Customer(hoten, sdt, diachi);
        try {
            int success = customerBUS.addCustomer(newCustomer);

            if (success > 0) {
                JOptionPane.showMessageDialog(this,
                        "Thêm khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                this.newCustomer = newCustomer;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm thất bại! (Có thể Số điện thoại đã tồn tại).",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu vào CSDL: " + e.getMessage(),
                    "Lỗi Hệ thống",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Customer getNewCustomer() {
        return this.newCustomer;
    }
}