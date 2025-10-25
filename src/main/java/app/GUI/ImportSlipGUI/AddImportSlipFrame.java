package app.GUI.ImportSlipGUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AddImportSlipFrame extends JFrame {

    private JComboBox<String> supplierBox, productBox, variantBox;
    private JTextField profitField, addPriceField, priceField, quantityField;
    private JButton confirmBtn, newProductBtn, backBtn, addBtn, deleteBtn;
    private JTable productTable;
    private JLabel nhacc, loinhuan, chonsanpham, phanloai, giathem, gianhap, soluong, phantram, totalLabel;
    private DefaultTableModel tableModel;

    public AddImportSlipFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Thêm phiếu nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 30, 15, 30));
        add(mainPanel, BorderLayout.CENTER);

        // ========== PHẦN NHẬP THÔNG TIN PHIẾU ==========
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.PLAIN, 26);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        // Nhà cung cấp
        gbc.gridx = 0; gbc.gridy = 0;
        nhacc = new JLabel("Nhà cung cấp:");
        nhacc.setFont(labelFont);
        infoPanel.add(nhacc, gbc);

        supplierBox = new JComboBox<>(new String[]{"Công ty A", "Công ty B"});
        supplierBox.setFont(fieldFont);
        gbc.gridx = 1;
        infoPanel.add(supplierBox, gbc);

        // Lợi nhuận
        gbc.gridx = 2;
        loinhuan = new JLabel("Lợi nhuận:");
        loinhuan.setFont(labelFont);
        infoPanel.add(loinhuan, gbc);

        profitField = new JTextField("1", 5);
        profitField.setFont(fieldFont);
        gbc.gridx = 3;
        infoPanel.add(profitField, gbc);

        gbc.gridx = 4;
        phantram = new JLabel("(%)");
        phantram.setFont(labelFont);
        infoPanel.add(phantram, gbc);

        // Chọn sản phẩm
        gbc.gridx = 0; gbc.gridy = 1;
        chonsanpham = new JLabel("Chọn sản phẩm:");
        chonsanpham.setFont(labelFont);
        infoPanel.add(chonsanpham, gbc);

        productBox = new JComboBox<>(new String[]{"iPhone 16", "Samsung S24"});
        productBox.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridwidth = 3;
        infoPanel.add(productBox, gbc);
        gbc.gridwidth = 1;

        // Phân loại
        gbc.gridx = 0; gbc.gridy = 2;
        phanloai = new JLabel("Phân loại:");
        phanloai.setFont(labelFont);
        infoPanel.add(phanloai, gbc);

        variantBox = new JComboBox<>(new String[]{
                "Đen - 128GB", "Đen - 256GB",
                "Trắng - 128GB", "Trắng - 256GB",
                "Xanh - 512GB"
        });
        variantBox.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridwidth = 3;
        infoPanel.add(variantBox, gbc);
        gbc.gridwidth = 1;

        // Giá thêm
        gbc.gridx = 0; gbc.gridy = 3;
        giathem = new JLabel("Giá thêm:");
        giathem.setFont(labelFont);
        infoPanel.add(giathem, gbc);

        addPriceField = new JTextField("2000");
        addPriceField.setFont(fieldFont);
        gbc.gridx = 1;
        infoPanel.add(addPriceField, gbc);

        // Giá nhập
        gbc.gridx = 2;
        gianhap = new JLabel("Giá nhập:");
        gianhap.setFont(labelFont);
        infoPanel.add(gianhap, gbc);

        priceField = new JTextField("19200000");
        priceField.setFont(fieldFont);
        gbc.gridx = 3;
        infoPanel.add(priceField, gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 4;
        soluong = new JLabel("Số lượng:");
        soluong.setFont(labelFont);
        infoPanel.add(soluong, gbc);

        quantityField = new JTextField("1");
        quantityField.setFont(fieldFont);
        gbc.gridx = 1;
        infoPanel.add(quantityField, gbc);

        // --- Nút Xác nhận + Sản phẩm mới + Xóa ---
        confirmBtn = new JButton("Xác nhận");
        confirmBtn.setFont(fieldFont);

        newProductBtn = new JButton("Sản phẩm mới");
        newProductBtn.setFont(fieldFont);

        deleteBtn = new JButton("Xóa");
        deleteBtn.setFont(fieldFont);
        deleteBtn.setBackground(new Color(220, 53, 69)); // đỏ
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            }
        });

        JPanel btnPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        btnPanel1.setOpaque(false);
        btnPanel1.add(confirmBtn);
        btnPanel1.add(newProductBtn);
        btnPanel1.add(deleteBtn);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        infoPanel.add(btnPanel1, gbc);
        gbc.gridwidth = 1;

        mainPanel.add(infoPanel);

        // ========== DANH SÁCH SẢN PHẨM ==========
        JLabel listLabel = new JLabel("Danh sách sản phẩm", SwingConstants.CENTER);
        listLabel.setFont(new Font("Arial", Font.BOLD, 20));
        listLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        listLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(listLabel);

        // Model dữ liệu
        String[] columnNames = {"Tên sản phẩm", "Phân loại", "Giá cũ", "Giá mới", "Lợi nhuận", "Số lượng"};
        Object[][] data = {
                {"iPhone 16", "Đen - 128GB", "19200000", "19200000", "1%", "1"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 16));
        productTable.setRowHeight(28);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(700, 150));
        mainPanel.add(scrollPane);

        // Thành tiền
        int tongtien = 0;
        totalLabel = new JLabel("Thành tiền: " + tongtien + " VND");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        totalPanel.setOpaque(false);
        totalPanel.add(totalLabel);
        mainPanel.add(totalPanel);

        // ========== NÚT DƯỚI CÙNG ==========
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        bottomPanel.setOpaque(false);
        backBtn = new JButton("Trở lại");
        backBtn.setFont(fieldFont);
        addBtn = new JButton("Thêm");
        addBtn.setFont(fieldFont);
        addBtn.setBackground(new Color(50, 205, 50));
        addBtn.setForeground(Color.WHITE);
        bottomPanel.add(backBtn);
        bottomPanel.add(addBtn);
        mainPanel.add(bottomPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AddImportSlipFrame();
    }
}