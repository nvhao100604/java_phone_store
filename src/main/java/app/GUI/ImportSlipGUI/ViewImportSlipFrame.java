package app.GUI.ImportSlipGUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.BUS.EmployeeBUS;
import app.BUS.ProductBUS;
import app.BUS.SupplierBUS;
import app.DTO.ImportSlip;
import app.DTO.ImportSlipDetail;
import app.DTO.Product;
import app.DTO.ProductDetail;
import app.utils.DecimalFilter;

public class ViewImportSlipFrame extends JDialog {

    private static final Color COLOR_BACKGROUND = new Color(245, 248, 251);
    private static final Color COLOR_WHITE = Color.WHITE;
    private static final Color COLOR_TEXT_DARK = new Color(50, 50, 50);
    private static final Color COLOR_TEXT_LIGHT = new Color(120, 120, 120);
    private static final Color COLOR_BORDER = new Color(220, 220, 220);
    private static final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private static final Color COLOR_FIELD_BG = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARY_DARK = new Color(0, 105, 217);

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    private JTable productTable;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;
    private JButton closeButton;

    private JLabel supplierDisplay, profitDisplay, employeeDisplay, dateDisplay;

    private ImportSlip slipToView;
    private List<ImportSlipDetail> slipDetails;
    private ProductBUS productBUS;
    private SupplierBUS supplierBUS;
    private EmployeeBUS employeeBUS;

    private final DecimalFormat formatter = DecimalFilter.PriceFormatter();

    public ViewImportSlipFrame(Frame owner, ImportSlip slipToView, List<ImportSlipDetail> slipDetails) {
        super(owner, "Chi tiết Phiếu Nhập #" + slipToView.getImportSlipId(), true); // true = Modal

        this.slipToView = slipToView;
        this.slipDetails = slipDetails;

        this.productBUS = new ProductBUS();
        this.supplierBUS = new SupplierBUS();
        this.employeeBUS = new EmployeeBUS();

        initialize();
        loadData();
    }

    private void initialize() {
        setTitle("Chi tiết Phiếu Nhập #" + slipToView.getImportSlipId());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(COLOR_BACKGROUND);

        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

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
        supplierDisplay = createStyledDisplayLabel();
        panel.add(supplierDisplay, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Lợi nhuận:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        profitDisplay = createStyledDisplayLabel();
        panel.add(profitDisplay, gbc);

        // --- Hàng 1: Người nhập & Ngày nhập ---
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Người nhập:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        employeeDisplay = createStyledDisplayLabel();
        panel.add(employeeDisplay, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(createStyledLabel("Ngày nhập:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        dateDisplay = createStyledDisplayLabel();
        panel.add(dateDisplay, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel listLabel = new JLabel("Danh sách sản phẩm đã nhập", SwingConstants.CENTER);
        listLabel.setFont(FONT_TITLE);
        listLabel.setForeground(COLOR_TEXT_DARK);
        listLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
        panel.add(listLabel, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Tên sản phẩm", "Phân loại", "Giá nhập", "Điều chỉnh", "Lợi nhuận", "Số lượng",
                "Thành tiền" };

        tableModel = new DefaultTableModel(columnNames, 0) {
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

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(new EmptyBorder(5, 30, 15, 30));

        totalLabel = new JLabel("Thành tiền: 0 VND");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        totalLabel.setForeground(COLOR_SUCCESS);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(totalLabel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setOpaque(false);

        closeButton = createStyledButton("Đóng", COLOR_TEXT_LIGHT, Color.GRAY);
        closeButton.setBackground(COLOR_WHITE);
        closeButton.setForeground(COLOR_TEXT_DARK);
        closeButton.addActionListener(e -> dispose());

        actionPanel.add(closeButton);
        panel.add(actionPanel, BorderLayout.EAST);

        return panel;
    }

    private void loadData() {
        try {
            String supplierName = supplierBUS.getSupplierNameById(slipToView.getSupplierId());
            String employeeName = employeeBUS.getEmployeeNameById(slipToView.getEmployeeId());

            supplierDisplay.setText(supplierName != null ? supplierName : "N/A");
            employeeDisplay.setText(employeeName != null ? employeeName : "N/A");
            dateDisplay.setText(slipToView.getImportDate().toString());
            profitDisplay.setText(slipToView.getProfit() + "%");

            totalLabel.setText("Thành tiền: " + formatter.format(slipToView.getTotalAmount()) + " VND");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải thông tin phiếu nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        tableModel.setRowCount(0);
        for (ImportSlipDetail detail : slipDetails) {
            try {
                ProductDetail pd = productBUS.getProductDetailByDetailId(detail.getProductDetailId());
                Product p = productBUS.getProductById(pd.getProductId()); // Giả sử pd có getProductId()

                BigDecimal rowTotal = detail.getImportPrice().add(detail.getPriceAdjustment())
                        .multiply(BigDecimal.valueOf(detail.getQuantity()));

                tableModel.addRow(new Object[] {
                        detail.getProductDetailId(),
                        p.getProductName(),
                        pd.toString(), // Dùng toString() của ProductDetail
                        formatter.format(detail.getImportPrice()),
                        formatter.format(detail.getPriceAdjustment()),
                        slipToView.getProfit() + "%", // Lấy lợi nhuận chung
                        detail.getQuantity(),
                        formatter.format(rowTotal)
                });
            } catch (Exception e) {
                e.printStackTrace();
                // Thêm hàng báo lỗi nếu tra cứu thất bại
                tableModel.addRow(new Object[] { detail.getProductDetailId(), "Lỗi tải Tên", "N/A", 0, 0, 0, 0, 0 });
            }
        }
    }

    // --- CÁC HÀM TIỆN ÍCH TẠO STYLE (Tái sử dụng) ---

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT_DARK);
        return label;
    }

    // Hàm mới để tạo JLabel hiển thị (dùng thay JTextField)
    private JLabel createStyledDisplayLabel() {
        JLabel label = new JLabel("...");
        label.setFont(FONT_FIELD);
        label.setBackground(COLOR_FIELD_BG);
        label.setForeground(COLOR_TEXT_DARK);
        label.setOpaque(true); // Phải là true để nền hiển thị
        label.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                new EmptyBorder(5, 8, 5, 8) // Padding bên trong
        ));
        return label;
    }

    private void styleTable(JTable table) {
        table.setFont(FONT_FIELD);
        table.setRowHeight(30);
        table.setGridColor(COLOR_BORDER);
        table.setSelectionBackground(COLOR_PRIMARY_DARK);
        table.setSelectionForeground(COLOR_WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_LABEL);
        header.setBackground(COLOR_WHITE);
        header.setForeground(COLOR_TEXT_DARK);
        header.setOpaque(false);
        header.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));

        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

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

    // (Hàm main() đã bị xóa, vì JDialog nên được gọi từ một JFrame khác)
}