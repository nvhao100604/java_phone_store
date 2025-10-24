package app.GUI.CompanyGUI;

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

import app.BUS.CompanyBUS;
import app.DTO.Company;

public class UpdateCompanyFrame extends JFrame {
    
    private JTextField txtCompanyName;

    public Company updatedCompany;

    private CompanyBUS companyBUS;
    private int companyId;
    private CompanyGUI parentGUI;

    public UpdateCompanyFrame(String title, CompanyGUI parentGUI, int companyId) {
        super(title);
        this.companyBUS = new CompanyBUS();
        this.parentGUI = parentGUI;
        this.companyId = companyId;
        initializeUI();
        loadInitialData();
    }

    private void initializeUI() {
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel CompanyInfoPanel = createCompanyInfoPanel();
        add(CompanyInfoPanel, BorderLayout.NORTH);

        JPanel ActionButtonPanel = createActionButtonPanel();
        add(ActionButtonPanel, BorderLayout.SOUTH);
    }

        private JPanel createCompanyInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin nhà sản xuất"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 50);

        // Company Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblCompanyName = new JLabel("Tên nhà sản xuất:");
        lblCompanyName.setPreferredSize(labelSize);
        panel.add(lblCompanyName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;  
        gbc.weightx = 1;
        txtCompanyName = new JTextField(20);
        panel.add(txtCompanyName, gbc);

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
        updatedCompany = companyBUS.getCompanyById(companyId);
        if (updatedCompany != null) {
            txtCompanyName.setText(updatedCompany.getCompanyName());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhà sản xuất với ID đã cho.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void onSave() {
        if (updatedCompany == null) {
            JOptionPane.showMessageDialog(this, "Không có nhà sản xuất để cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newCompanyName = txtCompanyName.getText().trim();

        if (newCompanyName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà sản xuất.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        updatedCompany.setCompanyName(newCompanyName);
        int result = companyBUS.updateCompany(updatedCompany);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật nhà sản xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật nhà sản xuất thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}