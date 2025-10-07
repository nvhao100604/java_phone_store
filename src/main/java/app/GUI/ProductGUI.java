package app.GUI;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.BUS.ProductBUS;
import app.DTO.Product;

public class ProductGUI extends JPanel {

    JTable table;
    ProductBUS bus;

    public ProductGUI() {
        // Constructor implementation
        initialize();
    }

    public void initialize() {
        bus = new ProductBUS();
        // Initialization code
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(231, 345, 1000, 72);
        add(scrollPane);

        String[] columnNames = { "Mã sản phẩm", "Tên sản phẩm", "Loại", "Hãng", "Giá bán" };
        List<Product> productList = bus.getAll();
        System.out.println("Size: " + productList.size());
        table = new JTable();
        table.setModel(new DefaultTableModel(
                productList.stream()
                        .map(p -> new Object[] { p.getProductId(), p.getProductName(),
                                p.getCategory(), p.getBrand(), p.getSalePrice() })
                        .toArray(Object[][]::new),
                columnNames));
        table.getColumnModel().getColumn(0).setPreferredWidth(94);
        table.getColumnModel().getColumn(1).setPreferredWidth(98);
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
