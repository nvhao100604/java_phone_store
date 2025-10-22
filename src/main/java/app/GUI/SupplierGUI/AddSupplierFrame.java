package app.GUI.SupplierGUI;

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

import app.BUS.SupplierBUS;
import app.DTO.Supplier;

public class AddSupplierFrame extends JFrame {
    
    private JTextField txtSupplierName;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;

    public Supplier newSupplier;

    private SupplierBUS supplierBUS;

    public AddSupplierFrame(String title) {
        super(title);
        this.supplierBUS = new SupplierBUS();
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel SupplierInfoPanel = createSupplierInfoPanel();
        add(SupplierInfoPanel, BorderLayout.NORTH);

        JPanel ActionButtonPanel = createActionButtonPanel();
        add(ActionButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel createSupplierInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà cung cấp"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 50);

        // Supplier Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblSupplierName = new JLabel("Tên nhà cung cấp:");
        lblSupplierName.setPreferredSize(labelSize);
        panel.add(lblSupplierName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        txtSupplierName = new JTextField();
        panel.add(txtSupplierName, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblPhoneNumber = new JLabel("SĐT:");
        lblPhoneNumber.setPreferredSize(labelSize);
        panel.add(lblPhoneNumber, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        txtPhoneNumber = new JTextField();
        panel.add(txtPhoneNumber, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblAddress = new JLabel("Địa chỉ:");
        lblAddress.setPreferredSize(labelSize);
        panel.add(lblAddress, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        txtAddress = new JTextField();
        panel.add(txtAddress, gbc);

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
        String name = txtSupplierName.getText();
        String phone = txtPhoneNumber.getText();
        String address = txtAddress.getText();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d+") || phone.length() != 10) {
            JOptionPane.showMessageDialog(this, "SĐT không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        newSupplier = new Supplier(name, phone, address);
        supplierBUS.addSupplier(newSupplier);
        JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}