package app.GUI.ImportSlipGUI;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import app.BUS.CategoryBUS;
import app.BUS.ProductBUS;
import app.DTO.Product;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;
import app.utils.DecimalFilter;
import com.toedter.calendar.JDateChooser;

public class ImportSlipGUI extends JPanel implements FunctionPanel {
    private static final int mainWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int mainHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private ProductBUS bus;
    private CategoryBUS categoryBus;
    private khungchucnang khung;
    private JTextField nameSearchField;
    private JTextField priceFromField;
    private JTextField priceToField;
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JLabel title;
    private JLabel noResultLabel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;

    public ImportSlipGUI() {
        initialize();
    }

    private void initialize() {
        bus = new ProductBUS();
        categoryBus = new CategoryBUS();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, mainHeight < 1200 ? mainHeight - 580 : mainHeight - 950));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        topPanel.setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        // Panel chức năng bên trái
        khung = new khungchucnang(this);
        topPanel.add(khung, BorderLayout.WEST);

        // Panel lọc (Filter)
        FilterPanel filterPanel = new FilterPanel();
        filterPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(filterPanel, BorderLayout.CENTER);

        // ========== HÀNG 1 ==========
        // Người nhập
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Người nhập:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        nameSearchField = new JTextField(15);
        nameSearchField.setFont(new Font("Arial", Font.PLAIN, 20));
        nameSearchField.setBackground(new Color(240, 240, 240));
        nameSearchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                new EmptyBorder(5, 5, 5, 5)));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameSearchField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_END;
        filterPanel.add(namePanel, gbc);

        // Thành tiền
        JPanel pricePanel = new JPanel(new GridBagLayout());
        pricePanel.setOpaque(false);
        JLabel priceLabel = new JLabel("Thành tiền:");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        GridBagConstraints gbcPrice = new GridBagConstraints();
        gbcPrice.insets = new Insets(0, 5, 0, 5);

        gbcPrice.gridx = 0;
        pricePanel.add(priceLabel, gbcPrice);

        DecimalFilter decimalFilter = new DecimalFilter();
        
     // ===== Ô Thành tiền từ =====
        priceFromField = new JTextField(20); // tăng từ 15 → 20 (rộng hơn)
        priceFromField.setFont(new Font("Arial", Font.PLAIN, 20));
        priceFromField.setBackground(new Color(240, 240, 240));
        priceFromField.setPreferredSize(new Dimension(200, 35)); // tăng kích thước ưa thích
        priceFromField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                new EmptyBorder(5, 8, 5, 8)));
        ((AbstractDocument) priceFromField.getDocument()).setDocumentFilter(decimalFilter);
        gbcPrice.gridx = 1;
        gbcPrice.weightx = 0.5; // cho phép giãn ngang
        gbcPrice.fill = GridBagConstraints.HORIZONTAL;
        pricePanel.add(priceFromField, gbcPrice);

        // Dấu gạch nối
        JLabel dash1 = new JLabel("-");
        dash1.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcPrice.gridx = 2;
        gbcPrice.weightx = 0;
        pricePanel.add(dash1, gbcPrice);

        // ===== Ô Thành tiền đến =====
        priceToField = new JTextField(20);
        priceToField.setFont(new Font("Arial", Font.PLAIN, 20));
        priceToField.setBackground(new Color(240, 240, 240));
        priceToField.setPreferredSize(new Dimension(200, 35));
        priceToField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                new EmptyBorder(5, 8, 5, 8)));
        ((AbstractDocument) priceToField.getDocument()).setDocumentFilter(decimalFilter);
        gbcPrice.gridx = 3;
        gbcPrice.weightx = 0.5;
        gbcPrice.fill = GridBagConstraints.HORIZONTAL;
        pricePanel.add(priceToField, gbcPrice);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.LINE_START;
        filterPanel.add(pricePanel, gbc);

        // ========== HÀNG 2 ==========
        JPanel datePanel = new JPanel(new GridBagLayout());
        datePanel.setOpaque(false);
        GridBagConstraints gbcDate = new GridBagConstraints();
        gbcDate.insets = new Insets(0, 5, 0, 5);

        JLabel dateLabel = new JLabel("Ngày nhập:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcDate.gridx = 0;
        gbcDate.anchor = GridBagConstraints.EAST;
        datePanel.add(dateLabel, gbcDate);
        
        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setFont(new Font("Arial", Font.PLAIN, 20));
        dateFrom.setBackground(new Color(240, 240, 240));
        dateFrom.setPreferredSize(new Dimension(250, 60));
        dateFrom.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                new EmptyBorder(0, 0, 0, 0)));
        gbcDate.gridx = 1;
        gbcDate.weightx = 0.5;
        gbcDate.fill = GridBagConstraints.HORIZONTAL;
        datePanel.add(dateFrom, gbcDate);

        JLabel dash2 = new JLabel("-");
        dash2.setFont(new Font("Arial", Font.PLAIN, 20));
        gbcDate.gridx = 2;
        gbcDate.weightx = 0;
        datePanel.add(dash2, gbcDate);

        dateTo = new JDateChooser();
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setFont(new Font("Arial", Font.PLAIN, 20));
        dateTo.setBackground(new Color(240, 240, 240));
        dateTo.setPreferredSize(new Dimension(250, 60));
        dateTo.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                new EmptyBorder(0, 0, 0, 0)));
        gbcDate.gridx = 3;
        gbcDate.weightx = 0.5;
        gbcDate.fill = GridBagConstraints.HORIZONTAL;
        datePanel.add(dateTo, gbcDate);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(datePanel, gbc);

        // ========== HÀNG 3 ==========
        JPanel processPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        processPanel.setOpaque(false);
        JButton filterButton = new JButton("Tìm kiếm");
        filterButton.setFont(new Font("Arial", Font.PLAIN, 20));
        filterButton.setBackground(Color.BLUE);
        filterButton.setForeground(Color.WHITE);
        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 20));
        refreshButton.setBackground(new Color(173, 255, 47));
        processPanel.add(filterButton);
        processPanel.add(refreshButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(processPanel, gbc);

        // ========== DANH SÁCH ==========
        title = new JLabel("Danh sách phiếu nhập ()", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.CENTER);

        JPanel listPanel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane();
        String[] columnNames = {"Mã phiếu nhập", "Người nhập", "Ngày nhập", "Nhà cung cấp", "Thành tiền", "Lợi nhuận", "Trạng thái"};
        List<Product> productList = bus.getAll();
        tableModel = new DefaultTableModel(productList.stream()
                .map(p -> new Object[]{})
                .toArray(Object[][]::new), columnNames);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 15));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 64, 128));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        scrollPane.setViewportView(table);
        listPanel.add(scrollPane, BorderLayout.NORTH);

        add(listPanel, BorderLayout.SOUTH);
    }
    public void Add() {}
    public void Delete() {}
    public void Edit() {}
    public void ImportExcel() {}
    public void ExportExcel() {}
}