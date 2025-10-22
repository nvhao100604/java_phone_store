package app.GUI.ProductTypeGUI;

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

import app.BUS.ProductTypeBUS;
import app.DTO.ProductType;

public class AddProductTypeFrame extends JFrame {
    
    private JTextField txtProductTypeName;

    public ProductType newProductType;

    private ProductTypeBUS productTypeBUS;

    public AddProductTypeFrame(String title) {
        super(title);
        this.productTypeBUS = new ProductTypeBUS();
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 175);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel ProductTypeInfoPanel = createProductTypeInfoPanel();
        add(ProductTypeInfoPanel, BorderLayout.NORTH);

        JPanel ActionButtonPanel = createActionButtonPanel();
        add(ActionButtonPanel, BorderLayout.SOUTH);
    }

    private JPanel createProductTypeInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin loại sản phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(100, 50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblProductTypeName = new JLabel("Tên loại sản phẩm:");
        lblProductTypeName.setPreferredSize(labelSize);
        panel.add(lblProductTypeName, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;  
        gbc.weightx = 1;
        txtProductTypeName = new JTextField(20);
        panel.add(txtProductTypeName, gbc);

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
        String productTypeName = txtProductTypeName.getText().trim();

        if (productTypeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        newProductType = new ProductType(productTypeName);
        int newProductTypeId = productTypeBUS.addProductType(newProductType);
        if (newProductTypeId != -1) {
            newProductType.setProductTypeId(newProductTypeId);
            JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Thêm loại sản phẩm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            newProductType = null;
        }
        dispose();
    }
}
