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

public class AddCompanyFrame extends JFrame {
    
    private JTextField txtCompanyName;

    public Company newCompany;

    private CompanyBUS companyBUS;

    public AddCompanyFrame(String title) {
        super(title);
        this.companyBUS = new CompanyBUS();
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 175);
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

        JButton btnAdd = new JButton("Thêm");
        JButton btnCancel = new JButton("Hủy");

        btnAdd.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());

        panel.add(btnAdd);
        panel.add(btnCancel);
        return panel;
    }

    private void onSave() {
        String companyname = txtCompanyName.getText().trim();

        if (companyname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà sản xuất.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        newCompany = new Company(companyname);
        int newCompanyId = companyBUS.addCompany(newCompany);
        if (newCompanyId != -1) {
            newCompany.setCompanyId(newCompanyId);
            JOptionPane.showMessageDialog(this, "Thêm nhà sản xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm nhà sản xuất thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            newCompany = null;
        }
        dispose();
    }
}