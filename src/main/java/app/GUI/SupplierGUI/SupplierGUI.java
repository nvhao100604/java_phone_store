package app.GUI.SupplierGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

import app.BUS.SupplierBUS;
import app.DTO.Supplier;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;
import app.utils.ConfirmDialog;
import app.utils.DataTable;

public class SupplierGUI extends JPanel implements FunctionPanel {

    private JTable table;
    private SupplierBUS bus;
    private khungchucnang khung;
    private JTextField nameSearchField;
    private JComboBox<String> statusComboBox;
    private JLabel noResultLabel;
    private JScrollPane scrollPane;
    DefaultTableModel tableModel;

    public SupplierGUI() {
        initialize();
    }

    private void initialize() {
        bus = new SupplierBUS();
        setLayout(new BorderLayout());
        noResultLabel = new JLabel("Không tìm thấy kết quả phù hợp.", SwingConstants.CENTER);
        noResultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        noResultLabel.setForeground(Color.RED);
        noResultLabel.setVisible(false);
        add(noResultLabel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, 250));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        topPanel.setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        khung = new khungchucnang(this);
        topPanel.add(khung, BorderLayout.WEST);

        FilterPanel filterPanel = new FilterPanel();
        filterPanel.setLayout(new GridLayout(3, 4, 40, 10));
        topPanel.add(filterPanel, BorderLayout.CENTER);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout());
        namePanel.setOpaque(false);
        namePanel.setBackground(null);
        namePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel nameSearchLabel = new JLabel("Tìm kiếm:");
        nameSearchLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameSearchLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        namePanel.add(nameSearchLabel, BorderLayout.WEST);

        nameSearchField = new JTextField(15);
        nameSearchField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameSearchField.setBackground(new Color(240, 240, 240));
        nameSearchField.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 5, 5, 5)));
        nameSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                HandleNameChange();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                HandleNameChange();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                HandleNameChange();
            }
        });
        namePanel.add(nameSearchField, BorderLayout.CENTER);

        // ----- Ô lọc trạng thái + nút -----
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        typePanel.setOpaque(false);
        typePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel typeLabel = new JLabel("Trạng thái:");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        statusComboBox = new JComboBox<>(new String[] { "Tất cả", "Còn hợp tác", "Ngừng hợp tác" });
        statusComboBox.setEditable(false);
        statusComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        statusComboBox.setBackground(new Color(240, 240, 240));
        statusComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
        statusComboBox.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(0, 0, 0, 0)));

        statusComboBox.addActionListener(e -> {
            Object selectedItem = statusComboBox.getSelectedItem();
            System.out.println("Selected status: " + selectedItem);
            HandleStatusChange(selectedItem);
        });

        JButton filterButton = new JButton("Tìm kiếm");
        filterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        filterButton.setBorder(new EmptyBorder(10, 15, 10, 15));
        filterButton.setBackground(Color.blue);
        filterButton.setForeground(Color.white);
        filterButton.addActionListener(e -> Search());

        JButton refreButton = new JButton("Làm mới");
        refreButton.setFont(new Font("Arial", Font.PLAIN, 16));
        refreButton.setBorder(new EmptyBorder(10, 15, 10, 15));
        refreButton.setBackground(new Color(173, 255, 47));
        refreButton.setForeground(Color.black);
        refreButton.addActionListener(e -> Refresh());

        typePanel.add(typeLabel);
        typePanel.add(statusComboBox);
        typePanel.add(filterButton);
        typePanel.add(refreButton);

        // ----- Thêm vào filterPanel -----
        filterPanel.add(namePanel);
        filterPanel.add(typePanel);

        JLabel title = new JLabel("DANH SÁCH NHÀ CUNG CẤP", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setOpaque(true);
        title.setPreferredSize(new Dimension(0, 10));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(title, BorderLayout.CENTER);

        // ----- Bảng dữ liệu -----
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        scrollPane = new JScrollPane();
        String[] columnNames = { "Mã nhà cung cấp", "Tên nhà cung cấp", "SĐT", "Địa chỉ", "Trạng thái" };
        List<Supplier> supplierList = bus.getAllSuppliers();
        table = new JTable();
        tableModel = new DefaultTableModel(
                supplierList.stream()
                        .map(e -> new Object[] { e.getIdSupplier(), e.getNameSupplier(), e.getPhoneNumber(), e.getAddress(),
                                statusToString(e.getStatus()) })
                        .toArray(Object[][]::new),
                columnNames);
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setIntercellSpacing(new Dimension(8, 4));
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 64, 128));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(0, 500));
        listPanel.add(scrollPane, BorderLayout.NORTH);

        add(listPanel, BorderLayout.SOUTH);
    }

    public void HandleNameChange() {
        String name = nameSearchField.getText();
        System.out.println("Name changed: " + name);
        if (name.isEmpty()) {
            HandleNull();
        }
        List<Supplier> suppliers = bus.searchSuppliers(name);
        // SetDataTable(suppliers);
    }

    public void HandleStatusChange(Object selectedItem) {
        if (selectedItem == null || selectedItem.toString().equals("Tất cả")) {
            HandleNull();
            return;
        }

        int status;
        switch (selectedItem.toString()) {
            case "Còn hợp tác":
                status = 1;
                break;
            case "Ngừng hợp tác":
                status = 0;
                break;
            default:
                HandleNull();
                return;
        }

        System.out.println("Filtering by status (int): " + status);
        List<Supplier> filteredSuppliers = bus.fillterSuppliers(status);
        // SetDataTable(filteredSuppliers);
    }

    public void Search() {
        System.out.println("Search supplier");
        String keyword = nameSearchField.getText().equals("") ? "" : nameSearchField.getText();
        Object selectedItem = statusComboBox.getSelectedItem();
        int statusInt = -1;
        if (selectedItem != null && !selectedItem.toString().equals("Tất cả")) {
            switch (selectedItem.toString()) {
                case "Còn hợp tác":
                    statusInt = 1;
                    break;
                case "Ngừng hợp tác":
                    statusInt = 0;
                    break;
                default:
                    statusInt = -1;
                    break;
            }
        }
        System.out.println("Search keyword: " + keyword + ", status: " + statusInt);
        List<Supplier> filteredSuppliers = bus.searchSuppliers(keyword, statusInt);
        SetDataTable(filteredSuppliers);
    }

    public void Refresh() {
        nameSearchField.setText("");
        statusComboBox.setSelectedIndex(0);
        List<Supplier> allSuppliers = bus.getAllSuppliers();
        SetDataTable(allSuppliers);
    }

    public void HandleNull() {
        List<Supplier> allSuppliers = bus.getAllSuppliers();
        SetDataTable(allSuppliers);
    }

    public void SetDataTable(List<Supplier> suppliers) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (suppliers.isEmpty()) {
            model.addRow(new Object[] { "", "", "", "", "", "", "" });
            noResultLabel.setVisible(true);
            scrollPane.setPreferredSize(new Dimension(0, 500));
            revalidate();
            repaint();
            return;
        }

        if (noResultLabel != null) {
            noResultLabel.setVisible(false);
            scrollPane.setPreferredSize(new Dimension(0, 500));
            revalidate();
            repaint();
        }

        for (Supplier s : suppliers) {
            model.addRow(new Object[] { s.getIdSupplier(), s.getNameSupplier(), s.getPhoneNumber(), s.getAddress(), statusToString(s.getStatus()) });
        }
    }

    public String statusToString(int status) {
        switch (status) {
            case 1:
                return "Còn hợp tác";
            case 0:
                return "Ngừng hợp tác";
            default:
                return "Không xác định";
        }
    }

    public void Add() {
        AddSupplierFrame addFrame = new AddSupplierFrame("Thêm nhà cung cấp");
        addFrame.setVisible(true);
    }

    public void Delete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int supplierId = (int) DataTable.dataFromTable(selectedRow, tableModel)[0];
            String status = (String) DataTable.dataFromTable(selectedRow, tableModel)[4];

            if (status.equals("Còn hợp tác")) {
                boolean isConfirmed = ConfirmDialog.confirmDialog(this, "Xác nhận xóa",
                        "Bạn có chắc chắn muốn xóa nhà cung cấp này không?");
                if (isConfirmed) {
                    bus.softDeleteSupplier(supplierId);
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công.", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (status.equals("Ngừng hợp tác")) {
                boolean isConfirmed = ConfirmDialog.confirmDialog(this, "Xác nhận khôi phục",
                        "Bạn có chắc chắn muốn khôi phục nhà cung cấp này không?");
                if (isConfirmed) {
                    int rows = bus.restoreSupplier(supplierId);
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Khôi phục nhà cung cấp thành công.", "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Khôi phục nhà cung cấp thất bại.", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            HandleNull();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xóa/khôi phục.", "Chú ý",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void ConfirmDelete(int supplierId) {
        boolean isConfirmed = ConfirmDialog.confirmDialog(this, "Xác nhận xóa",
                "Bạn có chắc chắn muốn xóa nhà cung cấp này không?");
        if (isConfirmed) {
            bus.softDeleteSupplier(supplierId);
            JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công.", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        HandleNull();
    }

    public void Edit() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để chỉnh sửa.", "Chú ý",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int supplierId = (int) table.getValueAt(selectedRow, 0);
        UpdateSupplierFrame updateFrame = new UpdateSupplierFrame("Cập nhật nhà cung cấp", this, supplierId);
        updateFrame.setVisible(true);
    }

    public void ImportExcel() {
        String filePath = DataTable.chooseFile();
        boolean isConfirmed = ConfirmDialog.confirmDialog(this, "File nhà cung cấp tại  " + filePath + " ?",
                "Xác nhận thêm danh sách nhà cung cấp");
        if (isConfirmed) {
            boolean result = bus.importDataFromExcel(filePath);
            if (result) {
                JOptionPane.showMessageDialog(this, "Thêm danh sách nhà cung cấp thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                List<Supplier> allSuppliers = bus.getAllSuppliers();
                SetDataTable(allSuppliers);
            }
            }
        }

    public void ExportExcel() {
        String savePath = DataTable.chooseFolder(this, "suppliers.xlsx");
        DataTable.exportDataToExcel(savePath, table);
        JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }
}