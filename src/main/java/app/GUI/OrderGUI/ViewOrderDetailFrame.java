package app.GUI.OrderGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog; // Đổi JFrame thành JDialog
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.BUS.CustomerBUS;
import app.BUS.OrderBUS;
import app.BUS.ProductBUS;
import app.BUS.PromotionBUS;
import app.DTO.Customer;
import app.DTO.Order;
import app.DTO.OrderDetail;
import app.DTO.PaymentMethod;
import app.DTO.Product;
import app.DTO.ProductDetail;
import app.DTO.PromotionUsage; // Cần DTO này để lấy KM đã áp dụng
import app.utils.DecimalFilter; // Giữ lại để định dạng tiền

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
import java.text.NumberFormat;
import java.util.List;

public class ViewOrderDetailFrame extends JDialog {

    private static final Color BG_LIGHT_GRAY = new Color(248, 248, 248);
    private static final Color BG_WHITE = Color.WHITE;
    private static final Color BORDER_GRAY = new Color(220, 220, 220);
    private static final Color TEXT_DARK = new Color(50, 50, 50);
    private static final Color TEXT_LIGHT = new Color(100, 100, 100);
    private static final Color SUCCESS_GREEN = new Color(46, 204, 113);
    private static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONT_INPUT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    private static final Font FONT_TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Font FONT_TOTAL = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private static final Color PRIMARY_BLUE = new Color(52, 152, 219);
    private static final Color PRIMARY_BLUE_DARK = new Color(41, 128, 185);

    private ProductBUS productBUS;
    private PromotionBUS promotionBUS;
    private CustomerBUS customerBUS;
    private OrderBUS orderBUS;

    private JLabel customerPhoneDisplay;
    private JLabel customerNameDisplay;
    private JLabel paymentMethodDisplay;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel subtotalLabel;
    private JLabel promotionLabel;
    private JLabel totalLabel;
    private JButton closeButton;

    private NumberFormat currencyFormatter;
    private Order currentOrder;

    public ViewOrderDetailFrame(JFrame owner, int orderToViewId) {
        super(owner, "Chi tiết Đơn hàng #" + orderToViewId, true); // Modal
        this.productBUS = new ProductBUS();
        this.promotionBUS = new PromotionBUS();
        this.customerBUS = new CustomerBUS();
        this.orderBUS = new OrderBUS();
        this.currentOrder = orderBUS.getOrderById(orderToViewId);

        this.currencyFormatter = DecimalFilter.PriceFormatter();
        initialize();
        loadData();
    }

    private void initialize() {
        setTitle("Chi tiết Đơn hàng #" + currentOrder.getOrderId());
        setSize(780, 700);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(15, 15));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(BG_LIGHT_GRAY);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel customerPanel = createCustomerPanel();
        JPanel cartPanel = createCartPanel();
        JPanel bottomPanel = createBottomPanel();
        JPanel paymentPanel = createPaymentPanel();

        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);
        topPanel.add(customerPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(cartPanel);
        middlePanel.add(paymentPanel);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel.setOpaque(false);

        JLabel paymentLabel = new JLabel("Phương thức thanh toán:");
        paymentLabel.setFont(FONT_LABEL);
        paymentLabel.setForeground(TEXT_DARK);

        paymentMethodDisplay = new JLabel("Chưa xác định");
        paymentMethodDisplay.setFont(FONT_INPUT.deriveFont(Font.BOLD));
        paymentMethodDisplay.setForeground(TEXT_DARK);

        panel.add(paymentLabel);
        panel.add(paymentMethodDisplay);

        return panel;
    }

    // --- Panel Dưới cùng (Chỉ hiển thị Tổng tiền + Nút Đóng) ---
    private JPanel createBottomPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        closeButton = createStyledButton("Đóng", TEXT_LIGHT, Color.GRAY);
        closeButton.setBackground(Color.WHITE);
        closeButton.setForeground(Color.DARK_GRAY);
        closeButton.addActionListener(e -> dispose());
        actionPanel.add(closeButton);

        // Panel chính
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 0, 15);

        // Panel Tóm tắt (chứa 3 label tổng)
        JPanel summaryPanel = new JPanel();
        summaryPanel.setOpaque(false);
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));

        // Khởi tạo các label (Sẽ được load)
        subtotalLabel = new JLabel("Tổng cộng: 0 VNĐ");
        promotionLabel = new JLabel("Khuyến mãi: 0 VNĐ");
        totalLabel = new JLabel("Thành tiền: 0 VNĐ");

        // Style (giống như trong createCartPanel của bạn)
        styleSummaryLabel(subtotalLabel, TEXT_LIGHT, FONT_TOTAL.deriveFont(Font.PLAIN, 16f));
        styleSummaryLabel(promotionLabel, TEXT_LIGHT, FONT_TOTAL.deriveFont(Font.PLAIN, 16f));
        styleSummaryLabel(totalLabel, SUCCESS_GREEN, FONT_TOTAL);

        summaryPanel.add(subtotalLabel);
        summaryPanel.add(promotionLabel);
        summaryPanel.add(Box.createVerticalStrut(5));
        summaryPanel.add(totalLabel);

        // Thêm vào bottomPanel
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

    // Hàm trợ giúp style cho 3 label
    private void styleSummaryLabel(JLabel label, Color color, Font font) {
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái trong BoxLayout
        label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
    }

    // --- Panel Khách hàng (Chỉ hiển thị) ---
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_WHITE);

        // ... (Code TitledBorder giữ nguyên) ...
        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder, "Thông tin khách hàng", TitledBorder.LEFT,
                TitledBorder.TOP, FONT_TITLE, TEXT_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(title,
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Hàng 0: Số điện thoại
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setFont(FONT_LABEL);
        phoneLabel.setForeground(TEXT_LIGHT);
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        customerPhoneDisplay = new JLabel("Đang tải..."); // Dùng JLabel
        customerPhoneDisplay.setFont(FONT_INPUT);
        panel.add(customerPhoneDisplay, gbc);

        // Hàng 1: Tên khách hàng
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        JLabel nameLabel = new JLabel("Tên KH:");
        nameLabel.setFont(FONT_LABEL);
        nameLabel.setForeground(TEXT_LIGHT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        customerNameDisplay = new JLabel("Đang tải..."); // Dùng JLabel
        customerNameDisplay.setFont(FONT_INPUT.deriveFont(Font.BOLD));
        panel.add(customerNameDisplay, gbc);

        return panel;
    }

    // --- Panel Giỏ hàng (Read-Only) ---
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_WHITE);
        // ... (Code TitledBorder "Giỏ hàng" giữ nguyên) ...
        Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
        TitledBorder title = BorderFactory.createTitledBorder(
                outerBorder, "Giỏ hàng", TitledBorder.LEFT,
                TitledBorder.TOP, FONT_TITLE, TEXT_DARK);
        panel.setBorder(title);

        String[] columnNames = { "Id", "Sản phẩm", "Tùy chọn", "Số lượng", "Đơn giá", "Thành tiền" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        try {
            Customer customer = customerBUS.getCustomerById(currentOrder.getCustomerId());
            if (customer != null) {
                customerPhoneDisplay.setText(customer.getPhoneNumber());
                customerNameDisplay.setText(customer.getFullName());
            } else {
                customerPhoneDisplay.setText("(Không tìm thấy)");
                customerNameDisplay.setText("(Không tìm thấy)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            customerNameDisplay.setText("(Lỗi tải dữ liệu)");
        }

        try {
            List<OrderDetail> details = orderBUS.getDetailsByOrderId(currentOrder.getOrderId());
            tableModel.setRowCount(0);

            BigDecimal subtotal = BigDecimal.ZERO;

            for (OrderDetail od : details) {
                ProductDetail pd = productBUS.getProductDetailByDetailId(od.getProductId());
                Product p = productBUS.getProductById(pd.getProductId());

                BigDecimal totalRow = od.getPrice().multiply(BigDecimal.valueOf(od.getQuantity()));
                subtotal = subtotal.add(totalRow);

                tableModel.addRow(new Object[] {
                        od.getProductId(),
                        p.getProductName(),
                        pd.toString(),
                        od.getQuantity(),
                        currencyFormatter.format(od.getPrice()),
                        currencyFormatter.format(totalRow)
                });
            }

            BigDecimal promotionValue = BigDecimal.ZERO;
            List<PromotionUsage> usages = promotionBUS.getUsageByOrderId(currentOrder.getOrderId());
            if (usages != null) {
                for (PromotionUsage usage : usages) {
                    promotionValue = promotionValue.add(usage.getValue());
                }
            }

            subtotalLabel.setText("Tổng cộng: " + currencyFormatter.format(subtotal));
            promotionLabel.setText("Khuyến mãi: - " + currencyFormatter.format(promotionValue));
            totalLabel.setText("Thành tiền: " + currencyFormatter.format(currentOrder.getTotalAmount()));

            paymentMethodDisplay.setText(currentOrder.getPaymentId().toString());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết đơn hàng: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

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
        });
    }

    private JButton createStyledButton(String text, Color background, Color hover) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        addHoverEffect(button, background, hover);
        return button;
    }
}