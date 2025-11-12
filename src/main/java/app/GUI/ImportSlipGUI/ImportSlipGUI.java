package app.GUI.ImportSlipGUI;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
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

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.BUS.CategoryBUS;
import app.BUS.ImportSlipBUS;
import app.BUS.ProductBUS;
import app.BUS.SupplierBUS;
import app.DTO.ImportSlip;
import app.DTO.ImportSlipDetail;
import app.DTO.Product;
import app.GUI.MainGUI;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;
import app.utils.DecimalFilter;

import com.mysql.cj.result.Row;
import com.toedter.calendar.JDateChooser;

public class ImportSlipGUI extends JPanel implements FunctionPanel {
    private static final int mainWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int mainHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    private MainGUI mainGUI;
    private ImportSlipBUS importSlipBUS; // thêm biến này
    private SupplierBUS supplierBUS; // join để lấy tên nhà cung cấp thông qua mã nhà cung cấp
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

    public ImportSlipGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        initialize();
    }

    private void initialize() {
        categoryBus = new CategoryBUS();
        importSlipBUS = new ImportSlipBUS();
        supplierBUS = new SupplierBUS(); // join
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

        // Label để hiện thông báo khi không tìm thấy kết quả (nếu muốn hiển thị trong
        // giao diện)
        noResultLabel = new JLabel("");
        noResultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        noResultLabel.setForeground(Color.RED);
        processPanel.add(noResultLabel);

        // Khi người dùng nhập vào ô tìm kiếm "Thành tiền"
        priceFromField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleFilterChangeThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleFilterChangeThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleFilterChangeThanhTien();
            }
        });
        priceToField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleFilterChangeThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleFilterChangeThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleFilterChangeThanhTien();
            }
        });

        // Khi người dùng chọn "Ngày nhập"
        dateFrom.addPropertyChangeListener("date", evt -> handleFilterChangeNgaynhap());
        dateTo.addPropertyChangeListener("date", evt -> handleFilterChangeNgaynhap());

        // ===== ACTION: nút Tìm kiếm =====
        filterButton.addActionListener(e -> {
            try {
                // Lấy điều kiện từ UI
                String keyword = nameSearchField.getText().trim().toLowerCase(); // người nhập
                String fromText = priceFromField.getText().trim();
                String toText = priceToField.getText().trim();
                java.util.Date fromDate = dateFrom.getDate();
                java.util.Date toDate = dateTo.getDate();

                BigDecimal fromPrice = fromText.isEmpty() ? null : new BigDecimal(fromText);
                BigDecimal toPrice = toText.isEmpty() ? null : new BigDecimal(toText);

                // Lấy toàn bộ phiếu nhập từ BUS
                List<ImportSlip> allSlips = importSlipBUS.getAllImportSlips();

                // Lọc danh sách theo các điều kiện
                List<ImportSlip> filteredList = null;
                // List<ImportSlip> filteredList = allSlips.stream().filter(slip -> {
                // boolean match = true;

                // // 1) Lọc "Người nhập"
                // if (!keyword.isEmpty()) {
                // // Nếu DTO có trường tên người tạo: dùng tên, ngược lại dùng supplierId
                // (chuỗi)
                // String createdBy = "";
                // try {
                // // nếu ImportSlip có phương thức getCreatedBy() hoặc getEmployeeName(), dùng
                // nó
                // // kiểm tra reflection-like: (nếu bạn đã có getCreatedBy() trong DTO, thay
                // line
                // // dưới cho phù hợp)
                // createdBy = slip.getDetails() != null ? "" : ""; // giữ an toàn nếu DTO không
                // có tên
                // } catch (Exception ignore) {
                // }

                // // fallback: tìm theo supplierId nếu không có tên
                // if (createdBy == null || createdBy.isEmpty()) {
                // createdBy = String.valueOf(slip.getSupplierId());
                // }
                // if (!createdBy.toLowerCase().contains(keyword)) {
                // match = false;
                // }
                // }

                // // 2) Lọc theo tổng tiền
                // if (fromPrice != null && slip.getTotalAmount() != null
                // && slip.getTotalAmount().compareTo(fromPrice) < 0) {
                // match = false;
                // }
                // if (toPrice != null && slip.getTotalAmount() != null
                // && slip.getTotalAmount().compareTo(toPrice) > 0) {
                // match = false;
                // }

                // // 3) Lọc theo ngày nhập
                // if (fromDate != null && slip.getImportDate() != null) {
                // java.util.Date slipDate = new java.util.Date(slip.getImportDate().getTime());
                // if (slipDate.before(fromDate))
                // match = false;
                // }
                // if (toDate != null && slip.getImportDate() != null) {
                // java.util.Date slipDate = new java.util.Date(slip.getImportDate().getTime());
                // if (slipDate.after(toDate))
                // match = false;
                // }

                // return match;
                // });

                // Cập nhật UI
                updateTable(filteredList);
                if (filteredList.isEmpty()) {
                    noResultLabel.setText("Không tìm thấy phiếu nhập phù hợp!");
                } else {
                    noResultLabel.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá trị thành tiền không hợp lệ.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===== ACTION: nút Làm mới =====
        refreshButton.addActionListener(e -> {
            nameSearchField.setText("");
            priceFromField.setText("");
            priceToField.setText("");
            dateFrom.setDate(null);
            dateTo.setDate(null);
            noResultLabel.setText("");
            CreateTable();
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(processPanel, gbc);

        // ========== DANH SÁCH ==========
        title = new JLabel("Danh sách phiếu nhập ( )", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.CENTER);
        CreateTable();
    }

    public void SetTitleQuantity(int quantity) {
        title.setText("Danh sách phiếu nhập (" + quantity + ")");
    }

    public void CreateTable() {
        JPanel listPanel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane();
        String[] columnNames = { "Mã phiếu nhập", "Người nhập", "Ngày nhập", "Nhà cung cấp", "Thành tiền", "Lợi nhuận",
                "Trạng thái" };
        List<ImportSlip> importslipList = importSlipBUS.getAllActiveImportSlips();
        tableModel = new DefaultTableModel(importslipList.stream()
                .map(p -> new Object[] { p.getImportSlipId(), p.getEmployeeId(), (java.util.Date) p.getImportDate(),
                        supplierBUS.getSupplierNameById(p.getSupplierId()), p.getTotalAmount(), p.getProfit(),
                        p.getStatus() })
                .toArray(Object[][]::new), columnNames);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 15));

        SetTitleQuantity(importslipList.size());
        scrollPane.setViewportView(table);
        scrollPane.setPreferredSize(new Dimension(0, 500));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        listPanel.add(scrollPane, BorderLayout.NORTH);

        add(listPanel, BorderLayout.SOUTH);
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 64, 128));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void Add() {
        AddImportSlipFrame addImportSlipFrame = new AddImportSlipFrame(mainGUI.getAccount().getAccountId());
        addImportSlipFrame.setVisible(true);
    }

    private void updateTable(List<ImportSlip> list) {
        tableModel.setRowCount(0);
        for (ImportSlip slip : list) {
            tableModel.addRow(new Object[] {
                    slip.getImportSlipId(),
                    slip.getSupplierId(),
                    slip.getImportDate(),
                    slip.getTotalAmount(),
                    slip.getProfit(),
                    slip.getStatus() == 1 ? "Hoạt động" : "Không hoạt động"
            });
        }
    }

    public void Delete() {
    }

    // Chỉnh sửa
    @Override
    public void Edit() {
        int selectedRow = (int) table.getSelectedRow();
        int selectedId = (int) tableModel.getValueAt(selectedRow, 0);
        ImportSlip selectedSlip = importSlipBUS.getImportSlipById(selectedId);
        List<ImportSlipDetail> details = importSlipBUS.getDetailsByImportSlipId(selectedId);
        ViewImportSlipFrame viewImportSlipFrame = new ViewImportSlipFrame(
                this.mainGUI.getApplication(),
                selectedSlip, details);
        viewImportSlipFrame.setVisible(true);
    }

    // Import Excel
    @Override
    public void ImportExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        try {
            boolean success = importSlipBUS.importDataFromExcel(file.getAbsolutePath());
            if (success) {
                updateTable(importSlipBUS.getAllImportSlips());
                JOptionPane.showMessageDialog(this, "Nhập dữ liệu từ Excel thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Nhập dữ liệu thất bại! Vui lòng kiểm tra file Excel.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi nhập file Excel: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Export Excel
    @Override
    public void ExportExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
        int result = fileChooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook();
                FileOutputStream fos = new FileOutputStream(filePath)) {
            Sheet sheet = (Sheet) workbook.createSheet("Phiếu nhập");
            Row headerRow = (Row) ((XSSFSheet) sheet).createRow(0);
            String[] headers = { "Mã phiếu nhập", "Nhà cung cấp", "Ngày nhập", "Thành tiền", "Lợi nhuận",
                    "Trạng thái" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = ((XSSFRow) headerRow).createCell(i);
                cell.setCellValue(headers[i]);
            }

            List<ImportSlip> slips = importSlipBUS.getAllImportSlips();
            int rowNum = 1;
            for (ImportSlip slip : slips) {
                Row row = (Row) ((XSSFSheet) sheet).createRow(rowNum++);
                ((XSSFRow) row).createCell(0).setCellValue(slip.getImportSlipId());
                ((XSSFRow) row).createCell(1).setCellValue(slip.getSupplierId());
                ((XSSFRow) row).createCell(2).setCellValue(slip.getImportDate().toString());
                ((XSSFRow) row).createCell(3).setCellValue(slip.getTotalAmount().doubleValue());
                ((XSSFRow) row).createCell(4).setCellValue(slip.getProfit());
                ((XSSFRow) row).createCell(5).setCellValue(slip.getStatus());
            }

            for (int i = 0; i < headers.length; i++) {
                ((XSSFSheet) sheet).autoSizeColumn(i);
            }

            workbook.write(fos);
            JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa cứng
    public void deleteHard() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xóa!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int importSlipId = (int) tableModel.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa hoàn toàn phiếu nhập ID " + importSlipId
                        + "?\nLưu ý: Dữ liệu sẽ bị xóa vĩnh viễn!",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                int rowsAffected = importSlipBUS.deleteImportSlip(importSlipId);
                if (rowsAffected > 0) {
                    updateTable(importSlipBUS.getAllImportSlips());
                    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!");
                } else {
                    // Thêm kiểm tra chi tiết hơn
                    JOptionPane.showMessageDialog(this,
                            "Xóa thất bại! Vui lòng kiểm tra:\n- ID " + importSlipId
                                    + " có tồn tại.\n- Ràng buộc khóa ngoại (liên kết với bảng khác).",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa: " + ex.getMessage() + "\nVui lòng kiểm tra kết nối hoặc dữ liệu.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Khôi phục ?
    public void restore() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để khôi phục!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int importSlipId = (int) tableModel.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn khôi phục phiếu nhập ID " + importSlipId
                        + "?\nDữ liệu sẽ được đánh dấu hoạt động trở lại.",
                "Xác nhận khôi phục", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                int rowsAffected = importSlipBUS.restoreImportSlip(importSlipId);
                if (rowsAffected > 0) {
                    updateTable(importSlipBUS.getAllImportSlips());
                    JOptionPane.showMessageDialog(this, "Khôi phục phiếu nhập thành công!");
                } else {
                    // Thêm kiểm tra chi tiết hơn
                    JOptionPane.showMessageDialog(this,
                            "Khôi phục thất bại! Vui lòng kiểm tra:\n- ID " + importSlipId
                                    + " có tồn tại.\n- Trạng thái hiện tại có thể không cho phép khôi phục.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi khôi phục: " + ex.getMessage() + "\nVui lòng kiểm tra kết nối hoặc dữ liệu.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleFilterChangeThanhTien() {
        try {
            String fromText = priceFromField.getText().trim();
            String toText = priceToField.getText().trim();

            BigDecimal fromValue = fromText.isEmpty() ? null : new BigDecimal(fromText);
            BigDecimal toValue = toText.isEmpty() ? null : new BigDecimal(toText);

            if (fromValue != null && toValue != null) {
                List<ImportSlip> filteredList = importSlipBUS.filterImportSlipstotal(fromValue, toValue);
                loadTableData(filteredList);
            }

            java.util.Date utilFromDate = dateFrom.getDate();
            java.util.Date utilToDate = dateTo.getDate();

            if (utilFromDate != null && utilToDate != null) {
                java.sql.Date sqlFromDate = new java.sql.Date(utilFromDate.getTime());
                java.sql.Date sqlToDate = new java.sql.Date(utilToDate.getTime());

                List<ImportSlip> filteredList = importSlipBUS.filterImportSlips(sqlFromDate, sqlToDate);
                loadTableData(filteredList);
            }
        } catch (Exception ex) {
            System.err.println("Lỗi lọc phiếu nhập: " + ex.getMessage());
        }
    }

    private void handleFilterChangeNgaynhap() {
        try {
            java.util.Date utilFromDate = dateFrom.getDate();
            java.util.Date utilToDate = dateTo.getDate();

            if (utilFromDate != null && utilToDate != null) {
                java.sql.Date sqlFromDate = new java.sql.Date(utilFromDate.getTime());
                java.sql.Date sqlToDate = new java.sql.Date(utilToDate.getTime());

                List<ImportSlip> filteredList = importSlipBUS.filterImportSlips(sqlFromDate, sqlToDate);
                loadTableData(filteredList);
            }
        } catch (Exception ex) {
            System.err.println("Lỗi lọc phiếu nhập: " + ex.getMessage());
        }
    }

    private void loadTableData(List<ImportSlip> list) {
        tableModel.setRowCount(0);
        for (ImportSlip slip : list) {
            tableModel.addRow(new Object[] {
                    slip.getImportSlipId(),
                    null, // người nhập theo filter ( or )
                    slip.getImportDate(),
                    supplierBUS.getSupplierNameById(slip.getSupplierId()),
                    slip.getTotalAmount(),
                    slip.getProfit(),
                    slip.getStatus()
            });
        }
        SetTitleQuantity(list.size());
    }
}
