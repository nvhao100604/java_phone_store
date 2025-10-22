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

public class UpdateSupplierFrame extends JFrame {

    private JTextField txtSupplierName;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;

    public Supplier updatedSupplier;

    private SupplierBUS supplierBUS;
    private int supplierId;
    private SupplierGUI parentGUI;

    public UpdateSupplierFrame(String title, SupplierGUI parentGUI, int supplierId) {
        super(title);
        this.supplierBUS = new SupplierBUS();
        this.parentGUI = parentGUI;
        this.supplierId = supplierId;
        initializeUI();
        loadInitialData();
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

        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnCancel = new JButton("Hủy");

        btnUpdate.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnUpdate);
        panel.add(btnCancel);
        return panel;
    }

    private void loadInitialData() {
        updatedSupplier = supplierBUS.getSupplierById(supplierId);
        if (updatedSupplier != null) {
            txtSupplierName.setText(updatedSupplier.getNameSupplier());
            txtPhoneNumber.setText(updatedSupplier.getPhoneNumber());
            txtAddress.setText(updatedSupplier.getAddress());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp với ID đã cho.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void onSave() {
        if (updatedSupplier == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newSupplierName = txtSupplierName.getText().trim();
        String newPhoneNumber = txtPhoneNumber.getText().trim();
        String newAddress = txtAddress.getText().trim();

        if (newSupplierName.isEmpty() || newPhoneNumber.isEmpty() || newAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPhoneNumber.length() !=10 || !newPhoneNumber.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "SĐT không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        updatedSupplier.setNameSupplier(newSupplierName);
        updatedSupplier.setPhoneNumber(newPhoneNumber);
        updatedSupplier.setAddress(newAddress);

        int result = supplierBUS.updateSupplier(updatedSupplier);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
}