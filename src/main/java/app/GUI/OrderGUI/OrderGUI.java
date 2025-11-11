package app.GUI.OrderGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import com.toedter.calendar.JDateChooser;

import app.BUS.CustomerBUS;
import app.BUS.EmployeeBUS;
import app.BUS.OrderBUS;

import app.DTO.Order;
import app.DTO.PaymentMethod;
import app.GUI.MainGUI;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;
import app.utils.DecimalFilter;

public class OrderGUI extends JPanel implements FunctionPanel {
    private MainGUI mainGUI;
    private static final int mainWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int mainHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    private OrderBUS orderBUS;
    private CustomerBUS customerBUS;
    private EmployeeBUS employeeBUS;

    private khungchucnang khung;
    private JTextField nameSearchField;
    private JTextField emSearchField;
    private JTextField priceFromField;
    private JTextField priceToField;
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JComboBox<PaymentMethod> paymentComboBox;

    private JLabel title;
    private JLabel noResultLabel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;

    public OrderGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        initialize();
    }

    private void initialize() {
        orderBUS = new OrderBUS();
        customerBUS = new CustomerBUS();
        employeeBUS = new EmployeeBUS();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, mainHeight < 1200 ? mainHeight - 580 : mainHeight - 950));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        topPanel.setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        khung = new khungchucnang(this);
        topPanel.add(khung, BorderLayout.WEST);

        FilterPanel filterPanel = new FilterPanel();
        filterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(filterPanel, BorderLayout.CENTER);

        JPanel namePanel = new JPanel(new BorderLayout(5, 0));
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Khách hàng:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        nameSearchField = new JTextField(15);
        nameSearchField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameSearchField.setBackground(new Color(240, 240, 240));
        nameSearchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 5, 5, 5)));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameSearchField, BorderLayout.CENTER);

        JPanel employeePanel = new JPanel(new BorderLayout(5, 0));
        employeePanel.setOpaque(false);
        JLabel emLabel = new JLabel("Nhân viên:");
        emLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        emSearchField = new JTextField(15);
        emSearchField.setFont(new Font("Arial", Font.PLAIN, 18));
        emSearchField.setBackground(new Color(240, 240, 240));
        emSearchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 5, 5, 5)));
        employeePanel.add(emLabel, BorderLayout.WEST);
        employeePanel.add(emSearchField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        filterPanel.add(namePanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        filterPanel.add(employeePanel, gbc);

        JPanel pricePanel = new JPanel(new GridBagLayout());
        pricePanel.setOpaque(false);
        JLabel priceLabel = new JLabel("Thành tiền:");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        GridBagConstraints gbcPrice = new GridBagConstraints();
        gbcPrice.insets = new Insets(0, 5, 0, 5);

        gbcPrice.gridx = 0;
        gbcPrice.weightx = 0;
        gbcPrice.fill = GridBagConstraints.NONE;
        pricePanel.add(priceLabel, gbcPrice);

        DecimalFilter decimalFilter = new DecimalFilter();

        priceFromField = new JTextField(10);
        priceFromField.setFont(new Font("Arial", Font.PLAIN, 18));
        priceFromField.setBackground(new Color(240, 240, 240));
        priceFromField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 5, 5, 5)));
        ((AbstractDocument) priceFromField.getDocument()).setDocumentFilter(decimalFilter);
        gbcPrice.gridx = 1;
        gbcPrice.weightx = 0.5;
        gbcPrice.fill = GridBagConstraints.HORIZONTAL;
        pricePanel.add(priceFromField, gbcPrice);

        JLabel dash1 = new JLabel("-");
        dash1.setFont(new Font("Arial", Font.PLAIN, 18));
        gbcPrice.gridx = 2;
        gbcPrice.weightx = 0;
        gbcPrice.fill = GridBagConstraints.NONE;
        pricePanel.add(dash1, gbcPrice);

        priceToField = new JTextField(10);
        priceToField.setFont(new Font("Arial", Font.PLAIN, 18));
        priceToField.setBackground(new Color(240, 240, 240));
        priceToField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 5, 5, 5)));
        ((AbstractDocument) priceToField.getDocument()).setDocumentFilter(decimalFilter);
        gbcPrice.gridx = 3;
        gbcPrice.weightx = 0.5;
        gbcPrice.fill = GridBagConstraints.HORIZONTAL;
        pricePanel.add(priceToField, gbcPrice);

        JPanel datePanel = new JPanel(new GridBagLayout());
        datePanel.setOpaque(false);
        GridBagConstraints gbcDate = new GridBagConstraints();
        gbcDate.insets = new Insets(0, 5, 0, 5);

        JLabel dateLabel = new JLabel("Ngày lập:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbcDate.gridx = 0;
        gbcDate.weightx = 0;
        gbcDate.fill = GridBagConstraints.NONE;
        datePanel.add(dateLabel, gbcDate);

        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setFont(new Font("Arial", Font.PLAIN, 16));
        dateFrom.setBackground(new Color(240, 240, 240));
        dateFrom.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Border đơn giản
        gbcDate.gridx = 1;
        gbcDate.weightx = 0.5;
        gbcDate.fill = GridBagConstraints.HORIZONTAL;
        datePanel.add(dateFrom, gbcDate);

        JLabel dash2 = new JLabel("-");
        dash2.setFont(new Font("Arial", Font.PLAIN, 18));
        gbcDate.gridx = 2;
        gbcDate.weightx = 0;
        gbcDate.fill = GridBagConstraints.NONE;
        datePanel.add(dash2, gbcDate);

        dateTo = new JDateChooser();
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setFont(new Font("Arial", Font.PLAIN, 16));
        dateTo.setBackground(new Color(240, 240, 240));
        dateTo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbcDate.gridx = 3;
        gbcDate.weightx = 0.5;
        gbcDate.fill = GridBagConstraints.HORIZONTAL;
        datePanel.add(dateTo, gbcDate);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        filterPanel.add(pricePanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        filterPanel.add(datePanel, gbc);

        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        paymentPanel.setOpaque(false);
        JLabel paymentLabel = new JLabel("Thanh toán:");
        paymentLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        paymentComboBox = new JComboBox<>(PaymentMethod.values());
        paymentComboBox.insertItemAt(null, 0);
        paymentComboBox.setSelectedIndex(0);
        paymentComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        paymentComboBox.setBackground(new Color(240, 240, 240));
        paymentComboBox.setPreferredSize(new Dimension(180, 30));

        paymentComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Tất cả");
                } else if (value instanceof PaymentMethod) {
                    setText(((PaymentMethod) value).toString());
                }
                return this;
            }
        });

        paymentPanel.add(paymentLabel);
        paymentPanel.add(paymentComboBox);

        JPanel processPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        processPanel.setOpaque(false);
        JButton filterButton = new JButton("Tìm kiếm");
        filterButton.setFont(new Font("Arial", Font.PLAIN, 18));
        filterButton.setBackground(Color.BLUE);
        filterButton.setForeground(Color.WHITE);
        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 18));
        refreshButton.setBackground(new Color(173, 255, 47));
        processPanel.add(filterButton);
        processPanel.add(refreshButton);

        filterButton.addActionListener(e -> FilterOrder());
        refreshButton.addActionListener(e -> Refresh());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.LINE_START;
        filterPanel.add(paymentPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(processPanel, gbc);

        List<Order> orderList = orderBUS.getAllOrders();
        int soluongdulieu = orderList.size();
        title = new JLabel("Danh sách Đơn hàng ( " + soluongdulieu + " )", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.CENTER);

        JPanel listPanel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane();
        String[] columnNames = { "Mã ĐH", "Người lập (NV)", "Khách hàng", "Ngày mua", "Thành tiền", "Phương thức",
                "Trạng thái" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setIntercellSpacing(new Dimension(1, 1));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 64, 128));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(0, 500));
        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(0, 600));
        listPanel.add(scrollPane, BorderLayout.NORTH);

        noResultLabel = new JLabel("Không tìm thấy đơn hàng");
        noResultLabel.setVisible(false);
        noResultLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        noResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noResultLabel.setVerticalAlignment(SwingConstants.NORTH);
        noResultLabel.setPreferredSize(new Dimension(0, 500));
        listPanel.add(noResultLabel, BorderLayout.CENTER);

        add(listPanel, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        updateTable(orderList);
    }

    @Override
    public void Add() {
        AddOrderFrame addOrderFrame = new AddOrderFrame(mainGUI.getAccount().getAccountId());
        addOrderFrame.setVisible(true);
    }

    public void Delete() {
    }

    @Override
    public void Edit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để chỉnh sửa!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    @Override
    public void ImportExcel() {
    }

    @Override
    public void ExportExcel() {
    }

    public void restore() {

    }

    private void FilterOrder() {
        try {
            String keyword = nameSearchField.getText().trim();
            String emString = emSearchField.getText().trim();
            String fromText = priceFromField.getText().trim();
            String toText = priceToField.getText();
            java.util.Date fromDate = dateFrom.getDate();
            java.util.Date toDate = dateTo.getDate();

            BigDecimal fromPrice = fromText.isEmpty() ? null : new BigDecimal(fromText);
            BigDecimal toPrice = toText.isEmpty() ? null : new BigDecimal(toText);

            PaymentMethod payment = (PaymentMethod) paymentComboBox.getSelectedItem();

            List<Order> filteredList = orderBUS.filterOrder(
                    keyword,
                    emString,
                    fromDate,
                    toDate,
                    fromPrice,
                    toPrice,
                    payment,
                    true);
            // Cập nhật UI
            updateTable(filteredList);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá trị thành tiền không hợp lệ.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Refresh() {
        nameSearchField.setText("");
        priceFromField.setText("");
        priceToField.setText("");
        dateFrom.setDate(null);
        dateTo.setDate(null);
        updateTable(orderBUS.getAllOrders());
    }

    public void Clear() {
        tableModel.setRowCount(0);
        noResultLabel.setVisible(true);
        noResultLabel.setPreferredSize(new Dimension(0, 400));
        revalidate();
        repaint();
    }

    private void updateTable(List<Order> list) {
        tableModel.setRowCount(0);
        SetTitleQuantity(list.size());
        if (list.isEmpty()) {
            tableModel.addRow(new Object[] { "", "", "", "", "", "", "", "" });
            noResultLabel.setVisible(true);
            scrollPane.setPreferredSize(new Dimension(0, 100));
            revalidate();
            repaint();
            return;
        }

        if (noResultLabel != null) {
            noResultLabel.setVisible(false);
            scrollPane.setPreferredSize(new Dimension(0, 450));
            revalidate();
            repaint();
        }
        for (Order order : list) {
            tableModel.addRow(new Object[] {
                    order.getOrderId(),
                    order.getAccountId(),
                    order.getCustomerId(),
                    order.getPurchaseDate(),
                    order.getTotalAmount(),
                    order.getPaymentId(),
                    order.getStatus()
            });
        }
    }

    public void SetTitleQuantity(int quantity) {
        title.setText("Danh sách đơn hàng (" + quantity + ")");
    }
}
