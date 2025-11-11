package app.GUI.OrderGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.BUS.CustomerBUS;
import app.BUS.ImeiBUS;
import app.BUS.OrderBUS;
import app.BUS.ProductBUS;
import app.BUS.PromotionBUS;
import app.DTO.Customer;
import app.DTO.Order;
import app.DTO.OrderDetail;
import app.DTO.PaymentMethod;
import app.DTO.Product;
import app.DTO.ProductDetail;
import app.DTO.Promotion;
import app.utils.DecimalFilter;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddOrderFrame extends JFrame {

    // ... (Các hằng số màu và font giữ nguyên) ...
    private static final Color BG_LIGHT_GRAY = new Color(248, 248, 248);
    private static final Color BG_WHITE = Color.WHITE;
    private static final Color BORDER_GRAY = new Color(220, 220, 220);
    private static final Color TEXT_DARK = new Color(50, 50, 50);
    private static final Color TEXT_LIGHT = new Color(100, 100, 100);

    private static final Color PRIMARY_BLUE = new Color(52, 152, 219);
    private static final Color PRIMARY_BLUE_DARK = new Color(41, 128, 185);
    private static final Color COLOR_PRIMARY = new Color(0, 123, 255);
    private static final Color COLOR_PRIMARY_DARK = new Color(0, 105, 217);

    private static final Color SUCCESS_GREEN = new Color(46, 204, 113);
    private static final Color SUCCESS_GREEN_DARK = new Color(39, 174, 96);

    private static final Color DANGER_RED = new Color(231, 76, 60);
    private static final Color DANGER_RED_DARK = new Color(192, 57, 43);
    private static final Color COLOR_FIELD_BG = new Color(252, 252, 252);

    private static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONT_INPUT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    private static final Font FONT_TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Font FONT_TOTAL = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    private static final String activeStatus = "CÒN HÀNG";
    private NumberFormat currencyFormatter;

    private ProductBUS productBUS;
    private PromotionBUS promotionBUS;
    private CustomerBUS customerBUS;
    private ImeiBUS imeiBUS;
    private OrderBUS orderBUS;
    private int employeeId;

    private JTextField customerPhoneField;
    private JLabel customerFoundNameLabel;
    private JButton addCustomerButton;
    private Customer selectedCustomer;

    private JComboBox<Product> productComboBox;
    private JSpinner quantitySpinner;
    private JLabel lblPrice;
    private JButton addButton;
    private JButton submitButton;
    private JButton clearButton;
    private JButton removeButton;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JComboBox<PaymentMethod> paymentComboBox;

    private JComboBox<ProductDetail> variantComboBox;
    private JComboBox<Promotion> comboPromoType;
    private List<Promotion> promotionList;
    private JLabel promoLabel1;
    private JLabel promoLabel2;
    private JLabel subtotalLabel;
    private JLabel promotionLabel;
    private JLabel totalLabel;

    public AddOrderFrame(int employeeId) {
        this.productBUS = new ProductBUS();
        this.promotionBUS = new PromotionBUS();
        this.customerBUS = new CustomerBUS();
        this.imeiBUS = new ImeiBUS();
        this.orderBUS = new OrderBUS();
        this.employeeId = employeeId;

        this.currencyFormatter = DecimalFilter.PriceFormatter();
        this.promotionList = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        setTitle("Form Đặt hàng");
        setSize(780, 810);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        getContentPane().setBackground(BG_LIGHT_GRAY);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel customerPanel = createCustomerPanel();
        JPanel productPanel = createProductPanel();
        JPanel promotionPanel = createPromotionPanel();
        JPanel cartPanel = createCartPanel();
        JPanel bottomPanel = createBottomPanel();
        JPanel paymentPanel = createPaymentPanel();

        // Khu vực trên cùng: Thông tin khách hàng + Sản phẩm
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);
        topPanel.add(customerPanel, BorderLayout.NORTH);
        topPanel.add(productPanel, BorderLayout.CENTER);

        // Khu vực giữa: Khuyến mãi + Giỏ hàng
        JPanel middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(promotionPanel);
        middlePanel.add(cartPanel);
        middlePanel.add(paymentPanel);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        HandleChangeProduct();
        addListeners();
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel.setOpaque(false);

        JLabel paymentLabel = new JLabel("Phương thức thanh toán:");
        paymentLabel.setFont(FONT_LABEL);
        paymentLabel.setForeground(TEXT_DARK);
        paymentComboBox = new JComboBox<>(PaymentMethod.values());

        paymentComboBox.setFont(FONT_INPUT);
        paymentComboBox.setBackground(COLOR_FIELD_BG);
        paymentComboBox.setPreferredSize(new Dimension(200, 30));

        panel.add(paymentLabel);
        panel.add(paymentComboBox);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel actionPanel = createActionPanel();
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 0, 15);

        JPanel summaryPanel = new JPanel();
        summaryPanel.setOpaque(false);
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));

        subtotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        promotionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        summaryPanel.add(subtotalLabel);
        summaryPanel.add(promotionLabel);
        summaryPanel.add(Box.createVerticalStrut(5));
        summaryPanel.add(totalLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        bottomPanel.add(summaryPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        bottomPanel.add(actionPanel, gbc);

        return bottomPanel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_WHITE);

        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder, "Thông tin khách hàng", TitledBorder.LEFT,
                TitledBorder.TOP, FONT_TITLE, TEXT_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
                title,
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setFont(FONT_LABEL);
        phoneLabel.setForeground(TEXT_LIGHT);
        phoneLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding nhỏ hơn
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        customerPhoneField = new JTextField(15);
        customerPhoneField.setFont(FONT_INPUT);
        customerPhoneField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(BORDER_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        customerPhoneField.setBackground(new Color(250, 250, 250));
        panel.add(customerPhoneField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        addCustomerButton = new JButton("Thêm KH");
        addCustomerButton.setFont(FONT_BUTTON);
        addCustomerButton.setBackground(COLOR_PRIMARY);
        addCustomerButton.setForeground(Color.WHITE);
        addCustomerButton.setOpaque(true);
        addCustomerButton.setFocusPainted(false);
        addCustomerButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding nhỏ hơn
        addHoverEffect(addCustomerButton, COLOR_PRIMARY, COLOR_PRIMARY_DARK);
        panel.add(addCustomerButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5);
        customerFoundNameLabel = new JLabel(" (Chưa tìm thấy khách hàng)");
        customerFoundNameLabel.setFont(FONT_LABEL.deriveFont(Font.ITALIC));
        customerFoundNameLabel.setForeground(TEXT_LIGHT);
        panel.add(customerFoundNameLabel, gbc);

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
            // tensanpham = products.get(0).getProductName();
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
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setFont(FONT_INPUT);
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField()
                .setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setBackground(new Color(250, 250, 250));
        panel.add(quantitySpinner, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        lblPrice = new JLabel("Giá: 0 | Tồn: 0", SwingConstants.CENTER);

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

    private JPanel createPromotionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_WHITE);

        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder,
                "Khuyến mãi",
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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel1.setOpaque(false);
        promoLabel1 = new JLabel(""); // để đó
        panel1.add(promoLabel1);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel2.setOpaque(false);
        promoLabel2 = new JLabel(""); // để đó
        panel2.add(promoLabel2);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel3.setOpaque(false);
        comboPromoType = new JComboBox<Promotion>();
        comboPromoType.setPreferredSize(new Dimension(160, 20));
        panel3.add(comboPromoType);

        // ===== Thêm các panel con vào panel chính theo hàng ngang =====
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(panel1, gbc);

        gbc.gridx = 1;
        panel.add(panel2, gbc);

        gbc.gridx = 2;
        panel.add(panel3, gbc);

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
                    ResetQuantitySpinner();
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

        subtotalLabel = new JLabel("Tổng cộng: 0 VNĐ");
        subtotalLabel.setFont(FONT_TOTAL.deriveFont(Font.PLAIN, 16f));
        subtotalLabel.setForeground(TEXT_LIGHT);
        subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        subtotalLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15)); // Padding

        promotionLabel = new JLabel("Khuyến mãi: 0 VNĐ");
        promotionLabel.setFont(FONT_TOTAL.deriveFont(Font.PLAIN, 16f));
        promotionLabel.setForeground(TEXT_LIGHT);
        promotionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        promotionLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));

        totalLabel = new JLabel("Thành tiền: 0 VNĐ");
        totalLabel.setFont(FONT_TOTAL);
        totalLabel.setForeground(SUCCESS_GREEN);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

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

    private void runPromotion() // chạy khuyến mãi
    {
    	// thay đổi hiển thị label CODE mỗi lần đổi tên sản phẩm ( cho trống cái trước )
    	label1.setText("");
    	label2.setText("");
    	
    	// thay đổi hiển thị label CODE mỗi lần đổi tên sản phẩm ( cho đen lại )
    	label1.setForeground(Color.BLACK);
    	label2.setForeground(Color.BLACK);
    	
    	Product selectedProduct = (Product) productComboBox.getSelectedItem();
        if (selectedProduct != null) 
        {
        	tensanpham = selectedProduct.getProductName(); // cập nhật giá trị
        }

        lblPrice.setText("Giá: " + DecimalFilter.PriceFormatter().format(price) +
                " | Tồn: " + stockQuantity);
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

    private void searchCustomer() {
        String phone = customerPhoneField.getText().trim();
        if (phone.length() >= 10) {
            Customer customer = customerBUS.getCustomerByPhone(phone);

            if (customer != null) {
                customerFoundNameLabel.setText(customer.getFullName());
                customerFoundNameLabel.setForeground(COLOR_PRIMARY);
                this.selectedCustomer = customer;
            } else {
                customerFoundNameLabel.setText(" (Không tìm thấy. Thêm mới?)");
                customerFoundNameLabel.setForeground(DANGER_RED);
                this.selectedCustomer = null;
            }
        } else {
            customerFoundNameLabel.setText(" (Nhập SĐT để tìm...)");
            customerFoundNameLabel.setForeground(TEXT_LIGHT);
            this.selectedCustomer = null;
        }
    }

    private void openAddCustomerDialog() {
        AddCustomerFrame addCustomerFrame = new AddCustomerFrame(this);
        addCustomerFrame.setVisible(true);

        Customer newCustomer = addCustomerFrame.getNewCustomer();
        if (newCustomer != null) {
            this.selectedCustomer = newCustomer;
            customerPhoneField.setText(newCustomer.getPhoneNumber());
            customerFoundNameLabel.setText(newCustomer.getFullName());
            customerFoundNameLabel.setForeground(COLOR_PRIMARY);
        }
    }

    private void runPromotion() {
        // promotionLabel.setText("");
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);

        int response = promotionBUS.setPromotionByStatus1(sqlDate);
        System.out.println("1 check: " + response);
        int response2 = promotionBUS.setPromotionByStatus0(sqlDate);
        System.out.println("0 check: " + response2);

        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        promotionList = promotionBUS.getValidPromotions(selectedProduct.getBrandId(), selectedProduct.getCategoryId(),
                sqlDate);
        HandlePromotionOrder();
    }

    private void HandlePromotionOrder() {
        BigDecimal total = TotalCalculate();
        for (Promotion promotion : promotionList) {
            if (promotion.isPercent()) {
                promotion.setValue(
                        total.multiply(BigDecimal.valueOf(promotion.getPercent())
                                .divide(BigDecimal.valueOf(100))));
            }
        }
        promotionList.sort(Comparator.comparing(Promotion::getValue));

        comboPromoType.removeAllItems();
        for (Promotion promo : promotionList) {
            comboPromoType.addItem(promo);
            System.out.println("check promo:" + promo.getCode());
        }

        if (comboPromoType.getItemCount() > 0) {
            comboPromoType.setSelectedIndex(0);
        }
    }

    private BigDecimal getPromotionValue() {
        BigDecimal total = BigDecimal.ZERO;
        for (Promotion promo : promotionList) {
            total = total.add(promo.getValue());
        }
        return total;
    }

    private BigDecimal getTotalWithPromotion() {
        BigDecimal total = TotalCalculate();
        BigDecimal promotionValue = getPromotionValue();
        if (promotionValue != null) {
            return total.subtract(promotionValue);
        }
        return total;
    }

    private void addProductToCart() {
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
            String quantity = cartTable.getValueAt(getRow(targetDetailId), 3).toString();
            int currentQuantity = DecimalFilter.PriceParser(quantity).intValue();
            cartTable.setValueAt(currentQuantity + addQuantity, getRow(targetDetailId), 3);
            return;
        }
        BigDecimal uniPrice = selectedProduct.getSalePrice().add(selectedVariant.getPriceAdjustment());
        BigDecimal total = uniPrice.multiply(BigDecimal.valueOf(addQuantity));

        tableModel.addRow(new Object[] {
                selectedVariant.getProductDetailId(),
                selectedProduct,
                selectedVariant,
                addQuantity,
                DecimalFilter.PriceFormatter().format(uniPrice),
                DecimalFilter.PriceFormatter().format(total)
        });

        runPromotion();
        updateTotal();
        ResetQuantitySpinner();
    }

    private void HandleUpdateQuantity(int row, int quantity) {
        // int targetDetailId = (int) cartTable.getValueAt(row, 0);
        // System.out.println("Check target id: " + targetDetailId);
        updateQuantityForTable(row);
        updateTotal();
    }

    private void updateQuantityForTable(int row) {
        int quantity = DecimalFilter.PriceParser(cartTable.getValueAt(row, 3).toString()).intValue();
        String price = cartTable.getValueAt(row, 4).toString();
        BigDecimal truePrice = new BigDecimal(DecimalFilter.PriceParser(price).toString());
        BigDecimal total = truePrice.multiply(new BigDecimal(quantity));

        cartTable.setValueAt(DecimalFilter.PriceFormatter().format(total), row, 5);
    }

    private int getRow(int targetDetailId) {
        // System.out.println("Id check: " + targetDetailId);
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

    private BigDecimal TotalCalculate() {
        DefaultTableModel tableModel = (DefaultTableModel) cartTable.getModel();
        BigDecimal total = BigDecimal.ZERO;
        int totalColumnIndex = 5;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object cellValue = tableModel.getValueAt(i, totalColumnIndex);
            Number parsedValue = DecimalFilter.PriceParser(cellValue.toString());
            BigDecimal trueValue = new BigDecimal(parsedValue.toString());

            total = total.add(trueValue);
        }
        return total;
    }

    private void updateTotal() {
        BigDecimal subtotal = TotalCalculate();
        subtotalLabel.setText("Tổng cộng: " + currencyFormatter.format(subtotal));
        BigDecimal promotionValue = getPromotionValue();
        promotionLabel.setText("Khuyến mãi: " + currencyFormatter.format(promotionValue));
        BigDecimal total = getTotalWithPromotion();
        totalLabel.setText("Thành tiền: " + currencyFormatter.format(total));
    }

    private void RemoveFromList() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow >= -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sản phẩm để xóa",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
        }
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            ResetQuantitySpinner();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn một mặt hàng trong giỏ để xóa.",
                    "Chưa chọn hàng",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean ValidateOrder() {
        if (this.selectedCustomer == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng tìm và chọn một khách hàng hợp lệ (dựa trên SĐT).",
                    "Lỗi thông tin khách hàng",
                    JOptionPane.ERROR_MESSAGE);
            customerPhoneField.requestFocus();
            return false;
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Giỏ hàng đang trống. Vui lòng thêm sản phẩm.",
                    "Lỗi giỏ hàng",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void submitOrder() {
        if (!ValidateOrder())
            return;

        int saveOrderResponse = HandleSaveOrder();
        if (saveOrderResponse < 0) {
            JOptionPane.showMessageDialog(this,
                    "Đặt hàng thất bại. Vui lòng thử lại.",
                    "Lỗi đặt hàng",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int saveOrderDetailResponse = HandleSaveOrderDetail(saveOrderResponse);
        System.out.println("Saved " + saveOrderDetailResponse);

        if (saveOrderDetailResponse < 0) {
            JOptionPane.showMessageDialog(this,
                    "Lưu chi tiết đơn hàng thất bại. Vui lòng kiểm tra lại.",
                    "Lỗi lưu chi tiết đơn hàng",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        HandleSuccess();
    }

    private void HandleSuccess() {
        String customerName = selectedCustomer.getFullName();
        String totalText = totalLabel.getText();
        String message = String.format(
                "Đơn hàng đã được đặt thành công!\n\nKhách hàng: %s\n%s",
                customerName, totalText);
        JOptionPane.showMessageDialog(this,
                message,
                "Đặt hàng thành công",
                JOptionPane.INFORMATION_MESSAGE);
        ResetFrom();
    }

    private int HandleSaveOrder() {
        Order order = new Order();
        BigDecimal totalAmount = getTotalWithPromotion();
        PaymentMethod paymentMethod = (PaymentMethod) paymentComboBox.getSelectedItem();

        order.setAccountId(employeeId);
        order.setCustomerId(selectedCustomer.getCustomerId());
        order.setAddress(selectedCustomer.getAddress());
        order.setPurchaseDate(Date.valueOf(LocalDate.now()));
        order.setTotalAmount(totalAmount);
        order.setPaymentId(paymentMethod);

        int orderId = orderBUS.addOrder(order);
        if (orderId > 0) {
            return orderId;
        }
        return -1;
    }

    private int HandleSaveOrderDetail(int orderId) {
        int count = 0;
        if (orderId <= 0)
            return -1;
        for (int row = 0; row < cartTable.getRowCount(); row++) {
            int productDetailId = DecimalFilter.PriceParser(cartTable.getValueAt(row, 0).toString()).intValue();
            int quantity = DecimalFilter.PriceParser(cartTable.getValueAt(row, 3).toString()).intValue();
            BigDecimal price = new BigDecimal(
                    DecimalFilter.PriceParser(cartTable.getValueAt(row, 4).toString()).toString());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productDetailId);
            orderDetail.setQuantity(quantity);
            orderDetail.setPrice(price);

            int response = orderBUS.addOrderDetail(orderDetail);
            System.out.println("Check add order detail: " + response);

            if (response > 0) {
                int imeiResponse = AddImeiToOrder(orderId, productDetailId, quantity);
                System.out.println("Check imei response: " + imeiResponse);
                if (imeiResponse > 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private int AddImeiToOrder(int orderId, int productDetailId, int quantity) {
        int response = imeiBUS.UpdateImeiByOrderQuantity(orderId, productDetailId, quantity);
        System.out.println("Check add imei to order: " + response);
        return response;
    }

    private int SavePromotionUsage(int orderId, int promotionId) {
        // Promotion usage = new Promotion();
        // usage.set(orderId);
        // usage.setPromotionId(promotionId);

        // int response = promotionBUS.addPromotionUsage(usage);
        // return response;
        return 0;
    }

    private void ResetProductList() {
        List<Product> products = productBUS.getAll();
        System.out.println("Reset product list, total products: " + products.size());
        productComboBox.removeAllItems();
        products.forEach(productComboBox::addItem);
        if (!(productComboBox.getItemCount() > 0)) {
            productComboBox.setSelectedIndex(0);
        }
    }

    private void ResetQuantitySpinner() {
        updateTotal();
        quantitySpinner.setValue(1);
    }

    private void clearCart() {
        tableModel.setRowCount(0);
        updateTotal();
    }

    private void ResetFrom() {
        ResetProductList();
        clearCart();
        ResetQuantitySpinner();
        customerPhoneField.setText("");
        selectedCustomer = null;
        customerFoundNameLabel.setText(" (Chưa có khách hàng)");
    }

    public static void main(String[] args) {
        // Sử dụng Look and Feel của hệ thống để giao diện đẹp hơn (đặc biệt trên
        // Windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new AddOrderFrame(1).setVisible(true));
    }
}
