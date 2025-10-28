package app.GUI.OrderGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.xml.soap.Detail;

import app.BUS.ProductBUS;
import app.DTO.OrderDetail;
import app.DTO.Product;
import app.DTO.ProductDetail;
import app.utils.DecimalFilter;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddOrderFrame extends JFrame {

    // ... (Các hằng số màu và font giữ nguyên) ...
    private static final Color BG_LIGHT_GRAY = new Color(248, 248, 248);
    private static final Color BG_WHITE = Color.WHITE;
    private static final Color BORDER_GRAY = new Color(220, 220, 220);
    private static final Color TEXT_DARK = new Color(50, 50, 50);
    private static final Color TEXT_LIGHT = new Color(100, 100, 100);

    private static final Color PRIMARY_BLUE = new Color(52, 152, 219);
    private static final Color PRIMARY_BLUE_DARK = new Color(41, 128, 185);

    private static final Color SUCCESS_GREEN = new Color(46, 204, 113);
    private static final Color SUCCESS_GREEN_DARK = new Color(39, 174, 96);

    private static final Color DANGER_RED = new Color(231, 76, 60);
    private static final Color DANGER_RED_DARK = new Color(192, 57, 43);

    private static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONT_INPUT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    private static final Font FONT_TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Font FONT_TOTAL = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    // private Map<String, BigDecimal> productPrices;
    private ProductBUS productBUS;
    private List<OrderDetail> detailList;

    private JTextField customerNameField;
    private JTextField customerAddressField;
    private JComboBox<Product> productComboBox;
    private JSpinner quantitySpinner;
    private JLabel lblPrice;
    private JButton addButton;
    private JButton submitButton;
    private JButton clearButton;
    private JButton removeButton;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    private NumberFormat currencyFormatter;
    private JComboBox<ProductDetail> variantComboBox;

    public AddOrderFrame() {
        this.productBUS = new ProductBUS();
        this.detailList = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        setTitle("Form Đặt hàng");
        setSize(780, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        getContentPane().setBackground(BG_LIGHT_GRAY);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel customerPanel = createCustomerPanel();
        JPanel productPanel = createProductPanel();
        JPanel cartPanel = createCartPanel();
        JPanel actionPanel = createActionPanel();

        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);
        topPanel.add(customerPanel, BorderLayout.NORTH);
        topPanel.add(productPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(15, 15));
        bottomPanel.setOpaque(false);
        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(actionPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(cartPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addListeners();
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_WHITE);

        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder,
                "Thông tin khách hàng",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_TITLE,
                TEXT_DARK);

        panel.setBorder(BorderFactory.createCompoundBorder(
                title,
                BorderFactory.createEmptyBorder(10, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel("Tên khách hàng:");
        nameLabel.setFont(FONT_LABEL);
        nameLabel.setForeground(TEXT_LIGHT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        customerNameField = new JTextField(20);
        customerNameField.setFont(FONT_INPUT);
        customerNameField.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        customerNameField.setBackground(new Color(250, 250, 250));
        panel.add(customerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setFont(FONT_LABEL);
        addressLabel.setForeground(TEXT_LIGHT);
        panel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        customerAddressField = new JTextField(20);
        customerAddressField.setFont(FONT_INPUT);
        customerAddressField.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        customerAddressField.setBackground(new Color(250, 250, 250));
        panel.add(customerAddressField, gbc);

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_WHITE);

        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder,
                "Chọn sản phẩm",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_TITLE,
                TEXT_DARK);

        panel.setBorder(BorderFactory.createCompoundBorder(
                title,
                BorderFactory.createEmptyBorder(10, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel productLabel = new JLabel("Sản phẩm:");
        productLabel.setFont(FONT_LABEL);
        productLabel.setForeground(TEXT_LIGHT);
        panel.add(productLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        List<Product> products = productBUS.getAll();
        productComboBox = new JComboBox<>(products.toArray(new Product[0]));
        if (!products.isEmpty()) {
            productComboBox.setSelectedIndex(0);
        }
        productComboBox.setFont(FONT_INPUT);
        productComboBox.setBackground(new Color(250, 250, 250));
        panel.add(productComboBox, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel variantLabel = new JLabel("Tùy chọn:");
        variantLabel.setFont(FONT_LABEL);
        variantLabel.setForeground(TEXT_LIGHT);
        panel.add(variantLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        variantComboBox = new JComboBox<>();
        variantComboBox.setFont(FONT_INPUT);
        variantComboBox.setBackground(new Color(250, 250, 250));
        panel.add(variantComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityLabel.setFont(FONT_LABEL);
        quantityLabel.setForeground(TEXT_LIGHT);
        panel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // Khai báo quantitySpinner nếu chưa
        quantitySpinner.setFont(FONT_INPUT);
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField()
                .setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setBackground(new Color(250, 250, 250));
        panel.add(quantitySpinner, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        lblPrice = new JLabel("Giá: 0", SwingConstants.LEFT);
        lblPrice.setFont(FONT_LABEL.deriveFont(Font.BOLD));
        lblPrice.setForeground(TEXT_DARK);
        lblPrice.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel.add(lblPrice, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        addButton = new JButton("Thêm vào giỏ");
        addButton.setFont(FONT_BUTTON);
        addButton.setBackground(PRIMARY_BLUE);
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(addButton, gbc);
        addHoverEffect(addButton, PRIMARY_BLUE, PRIMARY_BLUE_DARK);

        HandleChangeProduct();

        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);

        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder,
                "Giỏ hàng",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_TITLE,
                TEXT_DARK);
        panel.setBorder(title);

        String[] columnNames = { "Id", "Sản phẩm", "Tùy chọn", "Số lượng", "Đơn giá", "Thành tiền" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                if (column == 3) {
                    int quantity = DecimalFilter.PriceParser(aValue.toString()).intValue();
                    HandleUpdateQuantity(row, quantity);
                    ResetState();
                }
            }
        };
        cartTable = new JTable(tableModel);

        cartTable.setFont(FONT_INPUT);
        cartTable.setRowHeight(30);
        cartTable.setGridColor(BORDER_GRAY);
        cartTable.setSelectionBackground(new Color(200, 220, 255));
        cartTable.setSelectionForeground(TEXT_DARK);

        JTableHeader header = cartTable.getTableHeader();
        header.setFont(FONT_BUTTON);
        header.setBackground(new Color(230, 240, 250));
        header.setForeground(TEXT_DARK);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel("Tổng cộng: 0 VNĐ");
        totalLabel.setFont(FONT_TOTAL);
        totalLabel.setForeground(SUCCESS_GREEN);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setOpaque(false);

        submitButton = new JButton("Đặt hàng");
        clearButton = new JButton("Xóa giỏ hàng");
        removeButton = new JButton("Xóa");

        submitButton.setFont(FONT_BUTTON);
        submitButton.setBackground(SUCCESS_GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.setOpaque(true);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        clearButton.setFont(FONT_BUTTON);
        clearButton.setBackground(DANGER_RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        removeButton.setFont(FONT_BUTTON);
        removeButton.setBackground(Color.GRAY);
        removeButton.setForeground(Color.WHITE);
        removeButton.setOpaque(true);
        removeButton.setFocusPainted(false);
        removeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panel.add(removeButton);
        panel.add(clearButton);
        panel.add(submitButton);

        addHoverEffect(submitButton, SUCCESS_GREEN, SUCCESS_GREEN_DARK);
        addHoverEffect(clearButton, DANGER_RED, DANGER_RED_DARK);

        return panel;
    }

    // Phương thức trợ giúp hover (giữ nguyên)
    private void addHoverEffect(JButton button, Color defaultColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled())
                    button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled())
                    button.setBackground(defaultColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled())
                    button.setBackground(hoverColor.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    if (button.getBounds().contains(e.getPoint())) {
                        button.setBackground(hoverColor);
                    } else {
                        button.setBackground(defaultColor);
                    }
                }
            }
        });
    }

    private void addListeners() {
        addButton.addActionListener(e -> addProductToCart());
        clearButton.addActionListener(e -> clearCart());
        submitButton.addActionListener(e -> submitOrder());
        productComboBox.addActionListener(e -> HandleChangeProduct());
        variantComboBox.addActionListener(e -> CalculatePrice());
        removeButton.addActionListener(e -> RemoveFromList());
    }

    private void addProductToCart() {
        // <<< SỬA ĐỔI >>> Kiểm tra xem có sản phẩm nào không
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để thêm.", "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        ProductDetail selectedVariant = (ProductDetail) variantComboBox.getSelectedItem();
        int targetDetailId = selectedVariant.getProductDetailId();
        int addQuantity = (int) quantitySpinner.getValue();

        if (getRow(targetDetailId) > -1) {
            System.out.println("Check row: " + getRow(targetDetailId));
            cartTable.setValueAt(addQuantity, getRow(targetDetailId), 3);
            return;
        }
        Integer quantity = (Integer) quantitySpinner.getValue();
        BigDecimal uniPrice = selectedProduct.getSalePrice().add(selectedVariant.getPriceAdjustment());
        BigDecimal total = uniPrice.multiply(BigDecimal.valueOf(quantity));

        // System.out.println("Check total: " + total);
        tableModel.addRow(new Object[] {
                selectedVariant.getProductDetailId(),
                selectedProduct,
                selectedVariant,
                quantity,
                DecimalFilter.PriceFormatter().format(uniPrice),
                DecimalFilter.PriceFormatter().format(total)
        });
        AddToList(new OrderDetail(
                selectedVariant.getProductDetailId(),
                quantity,
                total));
        ResetState();
    }

    private void HandleUpdateQuantity(int row, int quantity) {
        int targetDetailId = (int) cartTable.getValueAt(row, 0);
        System.out.println("Check target id: " + targetDetailId);
        if (CheckExist(targetDetailId)) {
            System.out.println("Check exist");
            updateQuantityForDetail(targetDetailId, quantity);
            updateQuantityForTable(row);
        }
    }

    private void updateQuantityForDetail(int targetDetailId, int addQuantity) {
        Optional<OrderDetail> detailOptional = detailList.stream()
                .filter(detail -> detail.getProductId() == targetDetailId)
                .findFirst();

        detailOptional.ifPresent(detailToUpdate -> {
            System.out.println("Check quantity before update: " + detailToUpdate.getQuantity());
            detailToUpdate.setQuantity(addQuantity);
            System.out.println("Check quantity after update: " + detailToUpdate.getQuantity());
        });
    }

    private void updateQuantityForTable(int row) {
        int quantity = DecimalFilter.PriceParser(cartTable.getValueAt(row, 3).toString()).intValue();
        String price = cartTable.getValueAt(row, 4).toString();
        BigDecimal truePrice = new BigDecimal(DecimalFilter.PriceParser(price).toString());
        BigDecimal total = truePrice.multiply(new BigDecimal(quantity));

        cartTable.setValueAt(DecimalFilter.PriceFormatter().format(total), row, 5);
    }

    private boolean CheckExist(int targetDetailId) {
        boolean alreadyExists = detailList.stream()
                .anyMatch(orderDetail -> orderDetail.getProductId() == targetDetailId);

        return alreadyExists;
    }

    private int getRow(int targetDetailId) {
        System.out.println("Id check: " + targetDetailId);
        for (int row = 0; row < cartTable.getRowCount(); row++) {
            Object valueInCell = cartTable.getValueAt(row, 0);
            if (targetDetailId != 0
                    &&
                    targetDetailId == DecimalFilter.PriceParser(valueInCell.toString()).intValue()) {
                return row;
            } else if (targetDetailId == 0 && valueInCell == null) {
                return row;
            }
        }

        return -1;
    }

    private void ResetState() {
        updateTotal();
        quantitySpinner.setValue(1);
    }

    private void AddToList(OrderDetail detail) {
        if (detail == null)
            return;
        detailList.add(detail);
    }

    private void RemoveFromList() {
        int selectedRow = cartTable.getSelectedRow();
        int targetDetailId = (int) cartTable.getValueAt(selectedRow, 0);
        if (selectedRow != -1) {
            Optional<OrderDetail> alreadyExists = detailList.stream()
                    .filter(detail -> detail.getProductId() == targetDetailId)
                    .findFirst();

            if (alreadyExists.isPresent()) {
                System.out.println("Chi tiết sản phẩm với ID " + targetDetailId + " đã có trong danh sách.");
                detailList.remove(alreadyExists.get());
                tableModel.removeRow(selectedRow);
                ResetState();
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn một mặt hàng trong giỏ để xóa.",
                    "Chưa chọn hàng",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTotal() {
        System.out.println("Check");
        DefaultTableModel tableModel = (DefaultTableModel) cartTable.getModel();
        BigDecimal total = BigDecimal.ZERO;
        int totalColumnIndex = 5;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object cellValue = tableModel.getValueAt(i, totalColumnIndex);
            Number parsedValue = DecimalFilter.PriceParser(cellValue.toString());
            BigDecimal trueValue = new BigDecimal(parsedValue.toString());

            total = total.add(trueValue);
        }

        // for (int i = 0; i < tableModel.getRowCount(); i++) {
        // Object cellValue = tableModel.getValueAt(i, totalColumnIndex);
        // if (cellValue instanceof BigDecimal) {
        // tableModel.setValueAt(currencyFormatter.format((Double) ), i,
        // totalColumnIndex);
        // }
        // }
        totalLabel.setText("Tổng cộng: " + DecimalFilter.PriceFormatter().format(total));
    }

    private void clearCart() {
        tableModel.setRowCount(0);
        detailList.clear();
        updateTotal();
    }

    private void submitOrder() {
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();

        if (customerName.trim().isEmpty() || customerAddress.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ Tên và Địa chỉ khách hàng.",
                    "Lỗi thông tin",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Giỏ hàng đang trống. Vui lòng thêm sản phẩm.",
                    "Lỗi giỏ hàng",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (HandleSaveOrder()) {
            String totalText = totalLabel.getText();
            String message = String.format(
                    "Đơn hàng đã được đặt thành công!\n\nKhách hàng: %s\nĐịa chỉ: %s\n%s",
                    customerName, customerAddress, totalText);
            JOptionPane.showMessageDialog(this,
                    message,
                    "Đặt hàng thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            clearCart();
            detailList.clear();
            customerNameField.setText("");
            customerAddressField.setText("");
        }
    }

    private boolean HandleSaveOrder() {
        return false;
    }

    private void HandleSaveOrderDetail() {

    }

    private void HandleChangeProduct() {
        Product product = (Product) productComboBox.getSelectedItem();
        List<ProductDetail> details = productBUS.getProductDetailsById(product.getProductId());
        setDetailComboBox(details);
    }

    private void CalculatePrice() {
        Product product = (Product) productComboBox.getSelectedItem();
        ProductDetail detail = (ProductDetail) variantComboBox.getSelectedItem();

        updateDisplayedPrice(product, detail);
    }

    private void updateDisplayedPrice(Product product, ProductDetail detail) {
        BigDecimal productPrice = product != null ? product.getSalePrice() : BigDecimal.ZERO;
        BigDecimal detailPrice = detail != null ? detail.getPriceAdjustment() : BigDecimal.ZERO;
        BigDecimal price = productPrice.add(detailPrice);
        lblPrice.setText("Giá: " + DecimalFilter.PriceFormatter().format(price));
    }

    private void setDetailComboBox(List<ProductDetail> details) {
        if (variantComboBox == null) {
            System.err.println("Lỗi: variantComboBox chưa được khởi tạo!");
            return;
        }
        if (details == null) {
            System.err.println("Lỗi: Danh sách details là null!");
            variantComboBox.removeAllItems();
            return;
        }

        variantComboBox.removeAllItems();
        details.forEach(variantComboBox::addItem);

        if (!details.isEmpty()) {
            variantComboBox.setSelectedIndex(0);
            CalculatePrice();
        }
    }
}
