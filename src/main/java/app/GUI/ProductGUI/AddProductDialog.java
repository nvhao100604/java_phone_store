package app.GUI.ProductGUI;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import app.DTO.Product;
import app.DTO.ProductDetail;

public class AddProductDialog extends JDialog {

    private JTextField txtProductName, txtBrand, txtImportPrice, txtSalePrice, txtCategory, txtImageUrl, txtDescription;
    private JTable detailTable;
    private DefaultTableModel detailModel;
    private JButton btnAddDetail, btnRemoveDetail, btnSave, btnCancel;

    private Product resultProduct = null; // Trả về khi nhấn Lưu

    public AddProductDialog(Frame parent) {
        super(parent, "Thêm sản phẩm mới", true); // true = modal
        setSize(850, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(15, 10));

        // ==== Tiêu đề ====
        JLabel lblTitle = new JLabel("Thêm sản phẩm mới", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ==== Panel thông tin sản phẩm ====
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                "Thông tin sản phẩm",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtProductName = new JTextField(20);
        txtBrand = new JTextField(20);
        txtCategory = new JTextField(20);
        txtImportPrice = new JTextField(20);
        txtSalePrice = new JTextField(20);
        txtImageUrl = new JTextField(20);
        txtDescription = new JTextField(20);

        addRow(infoPanel, gbc, 0, "Tên sản phẩm:", txtProductName);
        addRow(infoPanel, gbc, 1, "Hãng:", txtBrand);
        addRow(infoPanel, gbc, 2, "Danh mục:", txtCategory);
        addRow(infoPanel, gbc, 3, "Giá nhập:", txtImportPrice);
        addRow(infoPanel, gbc, 4, "Giá bán:", txtSalePrice);
        addRow(infoPanel, gbc, 5, "Ảnh (URL):", txtImageUrl);
        addRow(infoPanel, gbc, 6, "Mô tả:", txtDescription);

        add(infoPanel, BorderLayout.NORTH);

        // ==== Bảng chi tiết sản phẩm ====
        JPanel detailPanel = new JPanel(new BorderLayout(10, 10));
        detailPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                "Chi tiết sản phẩm",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16)));

        String[] columns = { "Màu sắc", "Dung lượng", "Phụ thu giá", "Tồn kho", "Ảnh (URL)" };
        detailModel = new DefaultTableModel(columns, 0);
        detailTable = new JTable(detailModel);
        detailTable.setRowHeight(26);
        detailTable.setFont(new Font("Arial", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(detailTable);
        detailPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnDetailPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAddDetail = new JButton("Thêm dòng");
        btnRemoveDetail = new JButton("Xóa dòng");
        btnDetailPanel.add(btnAddDetail);
        btnDetailPanel.add(btnRemoveDetail);
        detailPanel.add(btnDetailPanel, BorderLayout.SOUTH);

        add(detailPanel, BorderLayout.CENTER);

        // ==== Nút hành động ====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        actionPanel.add(btnSave);
        actionPanel.add(btnCancel);
        add(actionPanel, BorderLayout.SOUTH);

        // ==== Sự kiện ====
        btnAddDetail.addActionListener(e -> detailModel.addRow(new Object[] { "", "", "", "", "" }));
        btnRemoveDetail.addActionListener(e -> {
            int selectedRow = detailTable.getSelectedRow();
            if (selectedRow != -1)
                detailModel.removeRow(selectedRow);
        });
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> handleSave());

        getRootPane().setDefaultButton(btnSave); // Enter = Lưu
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.2;
        panel.add(new JLabel(label, SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        panel.add(field, gbc);
    }

    private void handleSave() {
        try {
            Product product = new Product();
            product.setProductName(txtProductName.getText().trim());
            product.setBrand(txtBrand.getText().trim());
            // product.setCategoryName(txtCategory.getText().trim());
            product.setImportPrice(new BigDecimal(txtImportPrice.getText().trim()));
            product.setSalePrice(new BigDecimal(txtSalePrice.getText().trim()));
            product.setImageUrl(txtImageUrl.getText().trim());
            product.setDescription(txtDescription.getText().trim());

            List<ProductDetail> details = new ArrayList<>();
            for (int i = 0; i < detailModel.getRowCount(); i++) {
                ProductDetail d = new ProductDetail();
                d.setColor((String) detailModel.getValueAt(i, 0));
                d.setCapacity((String) detailModel.getValueAt(i, 1));

                String priceStr = (String) detailModel.getValueAt(i, 2);
                d.setPriceAdjustment(new BigDecimal(priceStr.isEmpty() ? "0" : priceStr));

                String stockStr = (String) detailModel.getValueAt(i, 3);
                d.setStock(stockStr.isEmpty() ? 0 : Integer.parseInt(stockStr));

                d.setImageUrl((String) detailModel.getValueAt(i, 4));
                details.add(d);
            }
            product.setProductDetails(details);

            resultProduct = product;
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu sản phẩm: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Product getResultProduct() {
        return resultProduct;
    }

    // Ví dụ dùng dialog
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddProductDialog dialog = new AddProductDialog(null);
            dialog.setVisible(true);

            Product result = dialog.getResultProduct();
            if (result != null) {
                System.out.println("Đã thêm sản phẩm: " + result.getProductName());
                System.out.println("Số chi tiết: " + result.getProductDetails().size());
            } else {
                System.out.println("Người dùng đã hủy.");
            }
        });
    }
}
