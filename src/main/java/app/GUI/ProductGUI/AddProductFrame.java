package app.GUI.ProductGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import app.DTO.ProductDetail;

public class AddProductFrame extends JFrame {

    // --- Product Fields ---
    private JTextField productNameField;
    private JComboBox<String> brandComboBox; // Giả sử Brand là String hoặc DTO
    private JComboBox<String> categoryComboBox; // Giả sử Category là String hoặc DTO
    private JFormattedTextField importPriceField;
    private JFormattedTextField salePriceField;
    private JTextArea descriptionArea;

    // --- ProductDetail Table ---
    private DefaultTableModel detailTableModel;
    private JTable detailTable;
    private List<ProductDetail> productDetailsList;

    // Định dạng cho các trường tiền tệ
    private DecimalFormat currencyFormat;
    private NumberFormatter numberFormatter;

    public AddProductFrame(String title) {
        super(title);
        productDetailsList = new ArrayList<>();
        initializeFormatters();
        initializeUI();
        loadInitialData();
    }

    private void initializeFormatters() {
        // Định dạng tiền tệ: 1,234.56
        currencyFormat = new DecimalFormat("#,##0.00");
        numberFormatter = new NumberFormatter(currencyFormat);
        numberFormatter.setValueClass(BigDecimal.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(new BigDecimal("0.00"));
    }

    private void initializeUI() {
        // Cấu hình Dialog
        setSize(800, 700);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());

        // --- 1. PRODUCT INFO PANEL (Top) ---
        JPanel productInfoPanel = createProductInfoPanel();
        add(productInfoPanel, BorderLayout.NORTH);

        // --- 2. PRODUCT DETAILS TABLE (Center) ---
        JPanel detailsPanel = createProductDetailsPanel();
        add(detailsPanel, BorderLayout.CENTER);

        // --- 3. ACTION BUTTONS (South) ---
        JPanel actionPanel = createActionButtonPanel();
        add(actionPanel, BorderLayout.SOUTH);
    }

    // --- Hàm tạo phần thông tin cơ bản ---
    private JPanel createProductInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm cơ bản"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dùng để tạo khoảng trống giữa label và field
        Dimension labelSize = new Dimension(120, 30);

        // --- Hàng 1: Tên Sản phẩm & Loại ---
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

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel categoryLabel = new JLabel("Loại SP:");
        categoryLabel.setPreferredSize(labelSize);
        panel.add(categoryLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        categoryComboBox = new JComboBox<>();
        panel.add(categoryComboBox, gbc);

        // --- Hàng 2: Giá Nhập & Giá Bán ---
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
        importPriceField.setValue(BigDecimal.ZERO);
        panel.add(importPriceField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel saleLabel = new JLabel("Giá Bán:");
        saleLabel.setPreferredSize(labelSize);
        panel.add(saleLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        salePriceField = new JFormattedTextField(numberFormatter);
        salePriceField.setValue(BigDecimal.ZERO);
        panel.add(salePriceField, gbc);

        // --- Hàng 3: Hãng & Mô tả ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel brandLabel = new JLabel("Hãng SX:");
        brandLabel.setPreferredSize(labelSize);
        panel.add(brandLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        brandComboBox = new JComboBox<>();
        panel.add(brandComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel descLabel = new JLabel("Mô tả:");
        descLabel.setPreferredSize(labelSize);
        panel.add(descLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        descriptionArea = new JTextArea(3, 20);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        panel.add(descScrollPane, gbc);

        return panel;
    }

    // --- Hàm tạo phần chi tiết sản phẩm ---
    private JPanel createProductDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm (Màu, Dung lượng, Tồn kho)"));

        // Tạo Model và Table
        String[] columnNames = { "Màu", "Dung lượng", "Điều chỉnh Giá", "Tồn kho" };
        detailTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép chỉnh sửa tất cả các ô
                return true;
            }
        };
        detailTable = new JTable(detailTableModel);

        // Thêm một số button quản lý hàng
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

    // --- Hàm tạo nút hành động ---
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

    // --- Hàm tải dữ liệu Category/Brand ban đầu ---
    private void loadInitialData() {
        // Giả lập dữ liệu Category và Brand (thực tế nên load từ BUS)
        categoryComboBox.addItem("Điện thoại");
        categoryComboBox.addItem("Laptop");
        brandComboBox.addItem("Samsung");
        brandComboBox.addItem("Apple");
    }

    // --- Logic thêm hàng chi tiết vào bảng ---
    private void addDetailRow() {
        // Giá trị mặc định cho hàng mới: Màu, Dung lượng, Điều chỉnh giá (0.00), Tồn
        // kho (0)
        detailTableModel.addRow(new Object[] { "Xanh", "128GB", new BigDecimal("0.00"), 0 });
    }

    // --- Logic xóa hàng chi tiết đã chọn ---
    private void removeSelectedDetailRow() {
        int selectedRow = detailTable.getSelectedRow();
        if (selectedRow != -1) {
            detailTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xóa.", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // --- Logic lưu sản phẩm ---
    private void saveProduct() {
        // 1. Lấy thông tin Product cơ bản
        String name = productNameField.getText();
        BigDecimal importP = (BigDecimal) importPriceField.getValue();
        BigDecimal saleP = (BigDecimal) salePriceField.getValue();

        if (name.trim().isEmpty() || importP == null || saleP == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ thông tin cơ bản.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Lấy thông tin Product Details từ JTable
        productDetailsList.clear();
        for (int i = 0; i < detailTableModel.getRowCount(); i++) {
            try {
                String color = detailTableModel.getValueAt(i, 0).toString();
                String capacity = detailTableModel.getValueAt(i, 1).toString();
                // Giả định giá trị đã được định dạng đúng trong bảng
                BigDecimal priceAdj = new BigDecimal(detailTableModel.getValueAt(i, 2).toString());
                int stock = Integer.parseInt(detailTableModel.getValueAt(i, 3).toString());

                ProductDetail detail = new ProductDetail();
                // ... set các trường khác của ProductDetail ...
                detail.setColor(color);
                detail.setCapacity(capacity);
                detail.setPriceAdjustment(priceAdj);
                detail.setStock(stock);

                productDetailsList.add(detail);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng chi tiết sản phẩm ở hàng " + (i + 1), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 3. Gọi hàm BUS để lưu (Ví dụ)
        // Product newProduct = new Product();
        // ... newProduct.setProductName(name);
        // ... newProduct.setProductDetails(productDetailsList);
        // bus.addProduct(newProduct);

        JOptionPane.showMessageDialog(this, "Sản phẩm đã được lưu thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
