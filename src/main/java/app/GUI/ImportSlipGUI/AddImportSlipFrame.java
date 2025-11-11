package app.GUI.ImportSlipGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import app.BUS.ImportSlipBUS;
import app.BUS.ProductBUS;
import app.BUS.SupplierBUS;
import app.DTO.Imei;
import app.DTO.ImportSlip;
import app.DTO.ImportSlipDetail;
import app.DTO.Product;
import app.DTO.ProductDetail;
import app.DTO.Supplier;
import app.GUI.MainGUI;
import app.GUI.interfaces.AddFrame;
import app.utils.DecimalFilter;
import app.utils.NumericRangeFilter;

public class AddImportSlipFrame extends JFrame implements AddFrame {

    private static final Color COLOR_BACKGROUND = new Color(245, 248, 251);
    private static final Color COLOR_WHITE = Color.WHITE;
    private static final Color COLOR_TEXT_DARK = new Color(50, 50, 50);
    private static final Color COLOR_TEXT_LIGHT = new Color(120, 120, 120);
    private static final Color COLOR_BORDER = new Color(220, 220, 220);
    private static final Color COLOR_PRIMARY = new Color(0, 123, 255);
    private static final Color COLOR_PRIMARY_DARK = new Color(0, 105, 217);
    private static final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private static final Color COLOR_SUCCESS_DARK = new Color(33, 136, 56);
    private static final Color COLOR_DANGER = new Color(220, 53, 69);
    private static final Color COLOR_DANGER_DARK = new Color(200, 35, 51);
    private static final Color COLOR_FIELD_BG = new Color(252, 252, 252);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    private JComboBox<Supplier> supplierBox;
    private JComboBox<Product> productBox;
    private JComboBox<ProductDetail> variantBox;
    private JTextField addPriceField, priceField;
    private JSpinner profitSpinner, quantitySpinner;
    private JButton confirmBtn, newProductBtn, backBtn, addBtn, deleteBtn;
    private JTable productTable;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;

    private int employeeId;
    private ProductBUS productBUS;
    private SupplierBUS supplierBUS;
    private ImportSlipBUS importSlipBUS;
    private final DecimalFormat formatter = DecimalFilter.PriceFormatter();

    public AddImportSlipFrame(int employeeId) {
        this.employeeId = employeeId;
        this.productBUS = new ProductBUS();
        this.supplierBUS = new SupplierBUS();
        this.importSlipBUS = new ImportSlipBUS();
        initialize();
    }

    private void initialize() {
        setTitle("Tạo Phiếu Nhập");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(COLOR_BACKGROUND);

        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        initializeData();
        addListener();
        setVisible(true);
    }

    /**
     * Tạo Panel chứa thông tin nhà cung cấp, sản phẩm và nút thêm vào bảng.
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // --- Hàng 0: Nhà cung cấp & Lợi nhuận ---
        gbc.gridy = 0;
        gbc.gridx = 0;
        panel.add(createStyledLabel("Nhà cung cấp:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        supplierBox = new JComboBox<Supplier>();
        styleComboBox(supplierBox);
        panel.add(supplierBox, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Lợi nhuận:"), gbc);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 0, 99, 1);
        profitSpinner = new JSpinner(model);
        profitSpinner.setFont(FONT_FIELD);

        JComponent editor = profitSpinner.getEditor();
        JTextField spinnerTextField = null;
        if (editor instanceof JSpinner.DefaultEditor) {
            spinnerTextField = ((JSpinner.DefaultEditor) editor).getTextField();
        } else {
            System.err.println("Editor không xác định, không thể áp dụng Filter.");
        }

        if (spinnerTextField != null) {
            ((AbstractDocument) spinnerTextField.getDocument()).setDocumentFilter(new NumericRangeFilter(0, 99));

            spinnerTextField.setBackground(COLOR_FIELD_BG);
            spinnerTextField.setForeground(COLOR_TEXT_DARK);
            spinnerTextField.setBorder(new CompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER),
                    new EmptyBorder(5, 8, 5, 8)));
            spinnerTextField.setHorizontalAlignment(JTextField.RIGHT);
        }

        gbc.gridx = 3; // Ô Lợi nhuận
        gbc.weightx = 0.5;
        panel.add(profitSpinner, gbc);

        // --- Hàng 1: Chọn sản phẩm ---
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Sản phẩm:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        productBox = new JComboBox<Product>();
        styleComboBox(productBox);
        panel.add(productBox, gbc);
        gbc.gridwidth = 1;

        // --- Hàng 2: Phân loại ---
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Phân loại:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        variantBox = new JComboBox<>();
        styleComboBox(variantBox);
        panel.add(variantBox, gbc);
        gbc.gridwidth = 1;

        // --- Hàng 3: Giá thêm & Giá nhập ---
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Giá thêm:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        addPriceField = createStyledTextField("0");
        panel.add(addPriceField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Giá nhập:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        priceField = createStyledTextField("19200000");
        panel.add(priceField, gbc);

        // --- Hàng 4: Số lượng & Nút ---
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Số lượng:"), gbc);

        SpinnerNumberModel quantityModel = new SpinnerNumberModel(1, -999, 999, 1);
        quantitySpinner = new JSpinner(quantityModel);
        quantitySpinner.setFont(FONT_FIELD);

        // 2. Lấy JTextField bên trong
        JComponent quantityEditor = quantitySpinner.getEditor();
        JTextField quantitySpinnerTextField = null;
        if (quantityEditor instanceof JSpinner.DefaultEditor) {
            quantitySpinnerTextField = ((JSpinner.DefaultEditor) quantityEditor).getTextField();
        }

        if (quantitySpinnerTextField != null) {
            // 3. Áp dụng NumericRangeFilter (đã tạo ở câu trước)
            ((AbstractDocument) quantitySpinnerTextField.getDocument())
                    .setDocumentFilter(new NumericRangeFilter(1, 9999));

            // 4. Áp dụng Style
            quantitySpinnerTextField.setBackground(COLOR_FIELD_BG);
            quantitySpinnerTextField.setForeground(COLOR_TEXT_DARK);
            quantitySpinnerTextField.setBorder(new CompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER),
                    new EmptyBorder(5, 8, 5, 8)));
            quantitySpinnerTextField.setHorizontalAlignment(JTextField.RIGHT);
        }

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(quantitySpinner, gbc);

        gbc.gridx = 2; // Panel cho 3 nút
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.EAST; // Căn phải
        gbc.fill = GridBagConstraints.NONE; // Không giãn ngang
        panel.add(createProductActionPanel(), gbc);
        gbc.gridwidth = 1;

        return panel;
    }

    /**
     * Tạo Panel chứa các nút (Xác nhận, Sản phẩm mới, Xóa).
     */
    private JPanel createProductActionPanel() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setOpaque(false);

        confirmBtn = createStyledButton("Xác nhận", COLOR_PRIMARY, COLOR_PRIMARY_DARK);
        newProductBtn = createStyledButton("Sản phẩm mới", COLOR_SUCCESS, COLOR_SUCCESS_DARK);
        deleteBtn = createStyledButton("Xóa", COLOR_DANGER, COLOR_DANGER_DARK);

        btnPanel.add(confirmBtn);
        btnPanel.add(newProductBtn);
        btnPanel.add(deleteBtn);
        return btnPanel;
    }

    /**
     * Tạo Panel chứa bảng JTable và tiêu đề danh sách.
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel listLabel = new JLabel("Danh sách sản phẩm nhập", SwingConstants.CENTER);
        listLabel.setFont(FONT_TITLE);
        listLabel.setForeground(COLOR_TEXT_DARK);
        listLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        panel.add(listLabel, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Tên sản phẩm", "Phân loại", "Giá nhập", "Điều chỉnh", "Lợi nhuận", "Số lượng",
                "Thành tiền" };
        Object[][] data = {
        };

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        styleTable(productTable);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Tạo Panel dưới cùng chứa tổng tiền và nút Thêm/Trở lại.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(new EmptyBorder(5, 30, 15, 30));

        int tongtien = 0;
        totalLabel = new JLabel("Thành tiền: " + tongtien + " VND");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalLabel.setForeground(COLOR_SUCCESS);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(totalLabel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setOpaque(false);

        backBtn = createStyledButton("Trở lại", COLOR_TEXT_LIGHT, Color.GRAY);
        backBtn.setBackground(COLOR_WHITE);
        backBtn.setForeground(COLOR_TEXT_DARK);

        addBtn = createStyledButton("Thêm Phiếu Nhập", COLOR_SUCCESS, COLOR_SUCCESS_DARK);

        actionPanel.add(backBtn);
        actionPanel.add(addBtn);
        panel.add(actionPanel, BorderLayout.EAST);

        return panel;
    }

    private void initializeData() {
        try {
            java.util.List<Supplier> suppliers = supplierBUS.getActiveSuppliers();
            supplierBox.removeAllItems();
            suppliers.forEach(supplierBox::addItem);

            if (!suppliers.isEmpty()) {
                supplierBox.setSelectedIndex(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi: Không thể tải danh sách nhà cung cấp.\n" + e.getMessage(),
                    "Lỗi Dữ liệu",
                    JOptionPane.ERROR_MESSAGE);
        }

        HandleLoadAll();
    }

    private void addListener() {
        productBox.addActionListener(e -> HandleChangeProduct());
        variantBox.addActionListener(e -> HandleChangeVariants());
        deleteBtn.addActionListener(e -> HandleDeleteRow());
        confirmBtn.addActionListener(e -> HandleAddRow());
        addBtn.addActionListener(e -> HandleAddImportSlip());
    }

    private void HandleAddImportSlip() {
        if (!ValidateForm()) {
            return;
        }
        int newImportSlipId = SaveImportSlip();
        int response = SaveImportSlipDetails(newImportSlipId);
        if (response > 0) {
            JOptionPane.showMessageDialog(this,
                    "Thêm phiếu nhập thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            ResetForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Thêm phiếu nhập thất bại!",
                    "Thất bại",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean ValidateForm() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Phiếu nhập phải có ít nhất một sản phẩm.\nVui lòng thêm sản phẩm vào bảng.",
                    "Lỗi Phiếu nhập rỗng",
                    JOptionPane.WARNING_MESSAGE);
            confirmBtn.requestFocus();
            return false;
        }

        int profit = (int) profitSpinner.getValue();
        if (profit < 0 || profit > 99) {
            JOptionPane.showMessageDialog(this,
                    "Lợi nhuận phải nằm trong khoảng từ 0 đến 99.",
                    "Lỗi Lợi nhuận",
                    JOptionPane.WARNING_MESSAGE);
            profitSpinner.requestFocus();
            return false;
        }

        try {
            BigDecimal importPrice = new BigDecimal(
                    DecimalFilter.PriceParser(priceField.getText()).toString());
            BigDecimal adjustPrice = new BigDecimal(
                    DecimalFilter.PriceParser(addPriceField.getText()).toString());

            if (importPrice.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this,
                        "Giá nhập không được là số âm.",
                        "Lỗi Giá trị",
                        JOptionPane.WARNING_MESSAGE);
                priceField.requestFocus();
                return false;
            }

            if (adjustPrice.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this,
                        "Giá thêm (điều chỉnh) không được là số âm.",
                        "Lỗi Giá trị",
                        JOptionPane.WARNING_MESSAGE);
                addPriceField.requestFocus();
                return false;
            }

        } catch (org.apache.xmlbeans.impl.regex.ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Giá nhập hoặc Giá thêm có định dạng không hợp lệ.",
                    "Lỗi Định dạng",
                    JOptionPane.ERROR_MESSAGE);
            priceField.requestFocus();
            return false;
        }

        return true;
    }

    private int SaveImportSlip() {
        ImportSlip importSlip = new ImportSlip();
        int employeeId = this.employeeId;
        int supplierId = ((Supplier) supplierBox.getSelectedItem()).getIdSupplier();
        BigDecimal totalAmount = TotalCalculate();
        int profit = (int) profitSpinner.getValue();

        importSlip.setEmployeeId(employeeId);
        importSlip.setSupplierId(supplierId);
        importSlip.setTotalAmount(totalAmount);
        importSlip.setProfit(profit);

        int response = importSlipBUS.addImportSlip(importSlip);
        System.out.println("Import id check: " + response);
        return response;
    }

    private int SaveImportSlipDetails(int importSlipId) {
        java.util.List<ImportSlipDetail> detailsList = new ArrayList<>();
        int rowCount = tableModel.getRowCount();
        int rowAffected = 0;

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this,
                    "Chưa có sản phẩm nào trong phiếu nhập để lưu.",
                    "Lỗi: Giỏ hàng rỗng",
                    JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        for (int i = 0; i < rowCount; i++) {
            try {
                int colId = 0;
                int colImportPrice = 3;
                int colAdjustPrice = 4;
                int colProfit = 5;
                int colQuantity = 6;

                int productDetailId = (int) tableModel.getValueAt(i, colId);
                int quantity = (int) tableModel.getValueAt(i, colQuantity);

                BigDecimal importPrice = DecimalFilter.parseToBigDecimal(tableModel.getValueAt(i, colImportPrice));
                BigDecimal priceAdj = DecimalFilter.parseToBigDecimal(tableModel.getValueAt(i, colAdjustPrice));
                int loiNhuan = (int) (tableModel.getValueAt(i, colProfit));
                BigDecimal salePrice = importPrice.multiply(new BigDecimal(100 + loiNhuan))
                        .divide(new BigDecimal(100));

                ImportSlipDetail detail = new ImportSlipDetail();
                detail.setImportSlipId(importSlipId);
                detail.setProductDetailId(productDetailId);
                detail.setQuantity(quantity);
                detail.setImportPrice(importPrice);
                detail.setPriceAdjustment(priceAdj);

                detailsList.add(detail);

                UpdateProductPrice(productDetailId, salePrice);
                int response = importSlipBUS.AddImportSlipDetail(detail);
                System.out.println("check response detail: " + response);
                if (response > 0)
                    SaveImeiNumbers(quantity, productDetailId, importSlipId);
                else
                    return -1;
                rowAffected += response;
            } catch (ParseException | NumberFormatException | ClassCastException e) {
                JOptionPane.showMessageDialog(this,
                        "Dữ liệu ở hàng " + (i + 1) + " không hợp lệ. Vui lòng kiểm tra lại.\nLỗi: " + e.getMessage(),
                        "Lỗi Định dạng",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return -1;
            }
        }
        return rowAffected;
    }

    private void UpdateProductPrice(int productDetailId, BigDecimal newSalePrice) {
        int response = productBUS.updateProductPriceByDetailId(productDetailId, newSalePrice);
        System.out.println("Update price response: " + response);
    }

    private void SaveImeiNumbers(int importQuantity, int productDetailId, int importSlipId) {
        String defaultImeiCode = "1234567890";
        Imei imei = new Imei();
        imei.setIdProductDetail(productDetailId);
        imei.setIdImport(importSlipId);
        for (int i = 0; i < importQuantity; i++) {
            String imeiNumber = JOptionPane.showInputDialog(this,
                    "Nhập số IMEI thứ " + (i + 1) + " cho sản phẩm (ID CTSP: " + imei.getIdProductDetail() + "):",
                    "Nhập Số IMEI",
                    JOptionPane.PLAIN_MESSAGE);
            String imeiCode = defaultImeiCode + imeiNumber;
            System.out.println("check imei: " + imeiCode.length());
            if (imeiNumber != null && !imeiNumber.trim().isEmpty() || imeiCode.length() != 15) {
                imei.setImei(imeiCode.trim());
                int response = importSlipBUS.AddImei(imei);
                System.out.println("Add IMEI response: " + response);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Số IMEI không được để trống. Vui lòng nhập lại.",
                        "Lỗi Nhập liệu",
                        JOptionPane.ERROR_MESSAGE);
                i--;
            }
        }
    }

    private void ResetForm() {
        if (productBox.getItemCount() > 0) {
            productBox.setSelectedIndex(0);
        } else {
            variantBox.removeAllItems();
            priceField.setText("0");
            addPriceField.setText("0");
        }

        profitSpinner.setValue(1);
        quantitySpinner.setValue(1);

        tableModel.setRowCount(0);
        updateTotalAmount();
    }

    private void HandleChangeProduct() {
        Product product = (Product) productBox.getSelectedItem();
        java.util.List<ProductDetail> details = productBUS.getProductDetailsById(product.getProductId());
        Product selectedProduct = (Product) productBox.getSelectedItem();
        BigDecimal baseImportPrice = BigDecimal.ZERO;

        if (selectedProduct != null) {
            baseImportPrice = selectedProduct.getImportPrice();
            if (baseImportPrice == null) {
                baseImportPrice = BigDecimal.ZERO;
            }
        }
        priceField.setText(baseImportPrice.toPlainString());
        setDetailComboBox(details);
    }

    private void setDetailComboBox(java.util.List<ProductDetail> details) {
        if (variantBox == null) {
            System.err.println("Lỗi: variantComboBox chưa được khởi tạo!");
            return;
        }
        if (details == null) {
            System.err.println("Lỗi: Danh sách details là null!");
            variantBox.removeAllItems();
            return;
        }

        variantBox.removeAllItems();
        details.forEach(variantBox::addItem);

        if (!details.isEmpty()) {
            variantBox.setSelectedIndex(0);
            HandleChangeVariants();
        }
    }

    private void HandleChangeVariants() {
        ProductDetail selectedVariant = (ProductDetail) variantBox.getSelectedItem();
        BigDecimal priceAdjustment = BigDecimal.ZERO;

        if (selectedVariant != null) {
            priceAdjustment = selectedVariant.getPriceAdjustment();
            if (priceAdjustment == null) {
                priceAdjustment = BigDecimal.ZERO;
            }
        }
        addPriceField.setText(priceAdjustment.toPlainString());
    }

    private void HandleAddRow() {
        // 1. Lấy đối tượng đã chọn
        Product selectedProduct = (Product) productBox.getSelectedItem();
        ProductDetail selectedVariant = (ProductDetail) variantBox.getSelectedItem();

        // 2. Kiểm tra (Validate)
        if (selectedProduct == null || selectedVariant == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn đầy đủ Sản phẩm và Phân loại.",
                    "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 3. Lấy các giá trị khác
            int targetDetailId = selectedVariant.getProductDetailId();
            int addQuantity = (int) quantitySpinner.getValue();
            int profitPercent = (int) profitSpinner.getValue();

            BigDecimal importPrice = new BigDecimal(
                    DecimalFilter.PriceParser(priceField.getText()).toString());
            BigDecimal adjustPrice = new BigDecimal(
                    DecimalFilter.PriceParser(addPriceField.getText()).toString());
            BigDecimal total = importPrice.add(adjustPrice).multiply(new BigDecimal(addQuantity));
            int existingRowIndex = findRowInTable(targetDetailId, 0);
            System.out.println("Total check: " + total);
            if (existingRowIndex != -1) {
                int currentQuantity = (int) tableModel.getValueAt(existingRowIndex, 6);
                int newQuantity = currentQuantity + addQuantity;
                BigDecimal newTotal = importPrice.add(adjustPrice).multiply(new BigDecimal(newQuantity));

                tableModel.setValueAt(newQuantity, existingRowIndex, 6);
                tableModel.setValueAt(formatter.format(newTotal), existingRowIndex, 7);
            } else {
                String productName = selectedProduct.toString();
                String variantName = selectedVariant.toString();
                tableModel.addRow(new Object[] {
                        targetDetailId,
                        productName,
                        variantName,
                        formatter.format(importPrice),
                        formatter.format(adjustPrice),
                        profitPercent,
                        addQuantity,
                        formatter.format(total)
                });
            }
            updateTotalAmount();

        } catch (com.formdev.flatlaf.json.ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Giá nhập hoặc Giá thêm không hợp lệ. Vui lòng kiểm tra lại.",
                    "Lỗi định dạng số", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private int findRowInTable(int targetId, int idColumnIndex) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            Object cellValue = tableModel.getValueAt(row, idColumnIndex);
            try {
                int cellId = Integer.parseInt(cellValue.toString());
                if (cellId == targetId) {
                    return row;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ", "Cánh báo", JOptionPane.WARNING_MESSAGE);
            }
        }
        return -1;
    }

    private void updateTotalAmount() {
        BigDecimal grandTotal = TotalCalculate();
        totalLabel.setText("Thành tiền: " + formatter.format(grandTotal) + " VND");
    }

    private BigDecimal TotalCalculate() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        int colTotal = 7;

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            try {
                Object total = tableModel.getValueAt(row, colTotal);
                BigDecimal rowTotal = new BigDecimal(
                        DecimalFilter.PriceParser(total.toString()).toString());
                grandTotal = grandTotal.add(rowTotal);

            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi tính tổng hàng " + row + ": " + e.getMessage());
                // Có thể hiển thị lỗi nếu cần
            }
        }

        return grandTotal;
    }

    private void HandleDeleteRow() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // --- CÁC HÀM TIỆN ÍCH TẠO STYLE ---

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT_DARK);
        return label;
    }

    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text, 10);
        field.setFont(FONT_FIELD);
        field.setBackground(COLOR_FIELD_BG);
        field.setForeground(COLOR_TEXT_DARK);
        field.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                new EmptyBorder(5, 8, 5, 8)));
        return field;
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(FONT_FIELD);
        comboBox.setBackground(COLOR_FIELD_BG);
        comboBox.setForeground(COLOR_TEXT_DARK);
        // Để tùy chỉnh sâu hơn (như border, mũi tên), bạn cần dùng setUI()
    }

    private void styleTable(JTable table) {
        table.setFont(FONT_FIELD);
        table.setRowHeight(30); // Tăng chiều cao hàng
        table.setGridColor(COLOR_BORDER); // Màu đường kẻ
        table.setSelectionBackground(COLOR_PRIMARY_DARK);
        table.setSelectionForeground(COLOR_WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_LABEL); // Dùng font label cho header
        header.setBackground(COLOR_WHITE);
        header.setForeground(COLOR_TEXT_DARK);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        // Căn giữa header
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Thêm padding cho các ô
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    private JButton createStyledButton(String text, Color background, Color hover) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Hiệu ứng Hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }
        });
        return button;
    }

    @Override
    public void HandleLoadAll() {
        try {
            java.util.List<Product> products = productBUS.getAll();
            productBox.removeAllItems();
            products.forEach(productBox::addItem);

            if (!products.isEmpty()) {
                productBox.setSelectedIndex(0);
            } else {
                variantBox.removeAllItems();
                priceField.setText("0");
                addPriceField.setText("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi: Không thể tải lại danh sách sản phẩm.\n" + e.getMessage(),
                    "Lỗi Dữ liệu",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Sử dụng Look and Feel của hệ thống để giao diện đẹp hơn (đặc biệt trên
        // Windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new AddImportSlipFrame(1));
    }
}