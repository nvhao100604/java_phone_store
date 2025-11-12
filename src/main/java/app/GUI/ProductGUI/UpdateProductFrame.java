package app.GUI.ProductGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import app.BUS.BrandBUS;
import app.BUS.CategoryBUS;
import app.BUS.ProductBUS;
import app.DTO.Brand;
import app.DTO.Category;
import app.DTO.Product;
import app.DTO.ProductDetail;
import app.utils.ImportImage;

public class UpdateProductFrame extends JFrame {
    private JTextField productNameField;
    private JComboBox<Brand> brandComboBox;
    private JComboBox<Category> categoryComboBox;
    private JFormattedTextField importPriceField;
    private JFormattedTextField salePriceField;
    private JTextArea descriptionArea;
    private JButton selectImageButton;
    private JLabel imagePreviewLabel;
    private File selectedImageFile;

    private DefaultTableModel detailTableModel;
    private JTable detailTable;
    private List<ProductDetail> productDetailsList;

    private DecimalFormat currencyFormat;
    private NumberFormatter numberFormatter;

    private ProductBUS productBUS;
    private CategoryBUS categoryBUS;
    private BrandBUS brandBUS;
    private int productId;
    private Product instanceProduct;

    public UpdateProductFrame(String title, int productId) {
        super(title);
        this.categoryBUS = new CategoryBUS();
        this.brandBUS = new BrandBUS();
        this.productBUS = new ProductBUS();
        this.productId = productId;
        initializeFormatters();
        initializeUI();
        loadInitialData();
    }

    private void initializeFormatters() {
        currencyFormat = new DecimalFormat("#,##0.00");
        numberFormatter = new NumberFormatter(currencyFormat);
        numberFormatter.setValueClass(BigDecimal.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(new BigDecimal("0.00"));
    }

    private void initializeUI() {
        setSize(800, 800);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        JPanel productInfoPanel = createProductInfoPanel();
        add(productInfoPanel, BorderLayout.NORTH);

        JPanel detailsPanel = createProductDetailsPanel();
        add(detailsPanel, BorderLayout.CENTER);

        JPanel actionPanel = createActionButtonPanel();
        add(actionPanel, BorderLayout.SOUTH);
    }

    private JPanel createProductInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm cơ bản"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Dimension labelSize = new Dimension(120, 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel nameLabel = new JLabel("Tên SP:");
        nameLabel.setPreferredSize(labelSize);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        productNameField = new JTextField(20);
        panel.add(productNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel importLabel = new JLabel("Giá Nhập:");
        importLabel.setPreferredSize(labelSize);
        panel.add(importLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        importPriceField = new JFormattedTextField(numberFormatter);
        importPriceField.setEditable(false);
        importPriceField.setValue(BigDecimal.ZERO);
        panel.add(importPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel saleLabel = new JLabel("Giá Bán:");
        saleLabel.setPreferredSize(labelSize);
        panel.add(saleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        salePriceField = new JFormattedTextField(numberFormatter);
        salePriceField.setValue(BigDecimal.ZERO);
        panel.add(salePriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        JLabel brandLabel = new JLabel("Hãng SX:");
        brandLabel.setPreferredSize(labelSize);
        panel.add(brandLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        brandComboBox = new JComboBox<>();
        panel.add(brandComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        JLabel categoryLabel = new JLabel("Loại SP:");
        categoryLabel.setPreferredSize(labelSize);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        categoryComboBox = new JComboBox<>();
        panel.add(categoryComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        JLabel descLabel = new JLabel("Mô tả:");
        descLabel.setPreferredSize(labelSize);
        panel.add(descLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        panel.add(descScrollPane, gbc);

        // Ảnh sản phẩm
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(new JLabel("Ảnh sản phẩm:", SwingConstants.LEFT), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.5;
        JPanel imagePanel = new JPanel(new BorderLayout(5, 5));
        selectImageButton = new JButton("Chọn ảnh...");
        selectImageButton.addActionListener(e -> {
            selectedImageFile = ImportImage.chooseImage(imagePreviewLabel);
        });
        imagePanel.add(selectImageButton, BorderLayout.EAST);
        panel.add(imagePanel, gbc);

        // Preview ảnh
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridheight = 4;
        imagePreviewLabel = new JLabel("Xem trước ảnh", SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(200, 200));
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
        panel.add(imagePreviewLabel, gbc);

        return panel;
    }

    private JPanel createProductDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm (Màu, Dung lượng)"));

        String[] columnNames = { "Id", "Màu", "Dung lượng", "Điều chỉnh Giá", "Số lượng" };
        detailTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 4;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                // Object productId = getValueAt(row, 0);
                // String columnName = getColumnName(column);

                // System.out.println("Cập nhật: ID " + productId + ", Cột " + columnName + ",
                // Giá trị: " + aValue);
                super.setValueAt(aValue, row, column);
            }
        };
        detailTable = new JTable(detailTableModel);

        JPanel detailActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Thêm Chi tiết");
        JButton removeButton = new JButton("Xóa Hàng chọn");

        addButton.addActionListener(e -> addDetailRow());
        removeButton.addActionListener(e -> removeSelectedDetailRow());

        detailActions.add(addButton);
        detailActions.add(removeButton);

        panel.add(detailActions, BorderLayout.NORTH);
        panel.add(new JScrollPane(detailTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActionButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton saveButton = new JButton("Lưu Sản phẩm");
        JButton cancelButton = new JButton("Hủy");

        saveButton.addActionListener(e -> saveProduct());
        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        panel.add(cancelButton);
        return panel;
    }

    private void loadInitialData() {
        instanceProduct = productBUS.getProductById(productId);
        productDetailsList = productBUS.getProductDetailsById(productId);
        // Set data
        productNameField.setText(instanceProduct.getProductName());
        importPriceField.setValue(instanceProduct.getImportPrice());
        salePriceField.setValue(instanceProduct.getSalePrice());

        List<Brand> brands = brandBUS.getAllBrands();
        for (Brand b : brands) {
            brandComboBox.addItem(b);
        }
        brandComboBox.setSelectedItem(new Brand(instanceProduct.getBrandId(), instanceProduct.getBrand()));

        List<Category> categories = categoryBUS.getAllCategories();
        for (Category c : categories) {
            categoryComboBox.addItem(c);
        }
        categoryComboBox.setSelectedItem(new Category(instanceProduct.getCategoryId(), instanceProduct.getCategory()));

        descriptionArea.setText(instanceProduct.getDescription());
        ImportImage.setPreviewImage(
                "src/main/resources/images/products/" + instanceProduct.getImageUrl(),
                imagePreviewLabel);
        // instanceProduct.setProductDetails(productDetailsList);
        loadInitialDetail();
    }

    public void loadInitialDetail() {
        List<ProductDetail> details = productDetailsList;
        DefaultTableModel model = (DefaultTableModel) detailTable.getModel();
        model.setRowCount(0);

        if (productDetailsList == null) {
            return;
        }
        for (ProductDetail productDetail : details) {
            model.addRow(new Object[] {
                    productDetail.getProductDetailId(),
                    productDetail.getColor(),
                    productDetail.getCapacity(),
                    productDetail.getPriceAdjustment(),
                    productDetail.getStock()
            });
        }
    }

    private void addDetailRow() {
        detailTableModel.addRow(new Object[] { 0, "Xanh", "128GB", new BigDecimal("0.00"), "Hết hàng" });
    }

    private void removeSelectedDetailRow() {
        int selectedRow = detailTable.getSelectedRow();
        if (selectedRow != -1) {
            detailTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xóa.", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveProduct() {
        String name = productNameField.getText();
        int brandId = brandComboBox.getSelectedItem() != null ? ((Brand) brandComboBox.getSelectedItem()).getId() : -1;
        int categoryId = categoryComboBox.getSelectedItem() != null
                ? ((Category) categoryComboBox.getSelectedItem()).getCategoryId()
                : -1;
        BigDecimal importP = (BigDecimal) importPriceField.getValue();
        BigDecimal saleP = (BigDecimal) salePriceField.getValue();
        String description = descriptionArea.getText();
        String imageUrl = selectedImageFile != null ? selectedImageFile.getName() : "";
        int status = 1;

        if (name.trim().isEmpty() || importP == null || saleP == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin cơ bản.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Product newProduct = new Product(instanceProduct.getProductId(), name, brandId, importP, categoryId, imageUrl,
                description, saleP, status);
        int isUpdated = productBUS.UpdateProduct(newProduct);
        if (isUpdated > 0) {
            saveProductDetails();

            JOptionPane.showMessageDialog(this, "Sản phẩm đã được lưu thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    public void saveProductDetails() {
        productDetailsList.clear();
        DefaultTableModel detailTableModelNew = (DefaultTableModel) detailTable.getModel();
        for (int i = 0; i < detailTableModel.getRowCount(); i++) {
            try {
                int detailId = (int) detailTableModelNew.getValueAt(i, 0);
                String color = detailTableModelNew.getValueAt(i, 1).toString();
                String capacity = detailTableModelNew.getValueAt(i, 2).toString();
                BigDecimal priceAdj = new BigDecimal(detailTableModelNew.getValueAt(i, 3).toString());
                int stock = detailTableModelNew.getValueAt(i, 4).equals("Hết hàng") ? 0
                        : (int) detailTableModelNew.getValueAt(i, 4);
                ProductDetail detail = new ProductDetail();
                detail.setProductDetailId(detailId);
                detail.setProductId(instanceProduct.getProductId());
                detail.setColor(color);
                detail.setCapacity(capacity);
                detail.setPriceAdjustment(priceAdj);
                detail.setStock(stock);

                // System.out.println("Check detail: " + detail.getProductDetailId() +
                // detail.getColor());

                productDetailsList.add(detail);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng chi tiết sản phẩm ở hàng " + (i + 1), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (productDetailsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một chi tiết sản phẩm.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        instanceProduct.setProductDetails(productDetailsList);
        productBUS.saveProductDetails(instanceProduct);
    }
}
