package app.GUI.PromotionGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import app.BUS.BrandBUS;
import app.BUS.CategoryBUS;
import app.BUS.PromotionBUS;
import app.DTO.Brand;
import app.DTO.Category;
import app.DTO.Promotion;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;
import app.utils.ConfirmDialog;
import app.utils.DataTable;

public class qlkhuyenmai extends JPanel implements FunctionPanel {
	private int mainWidth;
	private int mainHeight;

	private PromotionBUS bus;
	private BrandBUS brandbus;
	private CategoryBUS categoryBus;

	private khungchucnang khung;
	private JTextField nameSearchField;
	private JComboBox<app.DTO.Category> categoryComboBox;
	private JComboBox<app.DTO.Brand> brandComboBox;
	private JComboBox<String> statusComboBox;
	private JLabel noResultLabel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;

	private boolean isLoading = false;

	public qlkhuyenmai() {
		initialize();
	}

	public void initialize() {
		bus = new PromotionBUS();
		categoryBus = new CategoryBUS();
		brandbus = new BrandBUS();
		setLayout(new BorderLayout());

		mainWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		mainHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		// System.out.println("Main W + H = " + mainWidth + " + " + mainHeight);

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 250));
		topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
		topPanel.setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);

		khung = new khungchucnang(this);
		topPanel.add(khung, BorderLayout.WEST);

		FilterPanel filterPanel = new FilterPanel();
		filterPanel.setLayout(new GridLayout(3, 1, 0, 10));
		topPanel.add(filterPanel, BorderLayout.CENTER);

		// ----- Ô tìm kiếm theo tên -----
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

		// ----- Ô lọc + nút -----
		JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		typePanel.setOpaque(false);
		typePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		// ----- ComboBox Brand + Category -----
		JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		comboPanel.setOpaque(false);
		typePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		JLabel brandLabel = new JLabel("Hãng:");
		brandLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		brandComboBox = new JComboBox<app.DTO.Brand>();
		brandComboBox.setEditable(false);
		brandComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		brandComboBox.setBackground(new Color(240, 240, 240));
		brandComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
		brandComboBox.setBorder(new CompoundBorder(
			BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
				new EmptyBorder(0, 0, 0, 0)
			));

		// brandComboBox.addActionListener(e -> applyFilters());

		JLabel categoryLabel = new JLabel("Danh mục:");
		categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		categoryComboBox = new JComboBox<app.DTO.Category>();
		categoryComboBox.setEditable(false);
		categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		categoryComboBox.setBackground(new Color(240, 240, 240));
		categoryComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
		categoryComboBox.setBorder(new CompoundBorder(
			BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
			new EmptyBorder(0, 0, 0, 0)
		));

		// categoryComboBox.addActionListener(e -> applyFilters());

		// ----- Thêm vào comboPanel -----
		comboPanel.add(brandLabel);
		comboPanel.add(brandComboBox);
		comboPanel.add(categoryLabel);
		comboPanel.add(categoryComboBox);

		// ----- Thêm vào typePanel -----
		typePanel.add(comboPanel);

		// ----- StatusComboBox + Button -----
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		statusPanel.setOpaque(false);
		statusPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		JLabel statusLabel = new JLabel("Trạng thái:");
		statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		statusComboBox = new JComboBox<>(new String[] { "Tất cả", "Còn áp dụng", "Ngưng áp dụng" });
		statusComboBox.setEditable(false);
		statusComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		statusComboBox.setBackground(new Color(240, 240, 240));
		statusComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
		statusComboBox.setBorder(new CompoundBorder(
			BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
			new EmptyBorder(0, 0, 0, 0)
		));

		// statusComboBox.addActionListener(e -> applyFilters());

		JButton searchButton = new JButton("Tìm kiếm");
		searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
		searchButton.setBorder(new EmptyBorder(10, 15, 10, 15));
		searchButton.setBackground(new Color(70, 130, 180));
		searchButton.setForeground(Color.white);
		searchButton.addActionListener(e -> Search());

		JButton refreshButton = new JButton("Làm mới");
		refreshButton.setFont(new Font("Arial", Font.PLAIN, 16));
		refreshButton.setBorder(new EmptyBorder(10, 15, 10, 15));
		refreshButton.setBackground(new Color(173, 255, 47));
		refreshButton.setForeground(Color.black);
		refreshButton.addActionListener(e -> Refresh());

		statusPanel.add(statusLabel);
		statusPanel.add(statusComboBox);
		statusPanel.add(searchButton);
		statusPanel.add(refreshButton);

		// ----- Thêm vào filterPanel -----
		filterPanel.add(namePanel);
		filterPanel.add(typePanel);
		filterPanel.add(statusPanel);

		JLabel title = new JLabel("DANH SÁCH KHUYẾN MÃI", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(0, 0));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(title, BorderLayout.CENTER);

		// Data table
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		scrollPane = new JScrollPane();
		String[] columnNames = { "Mã khuyến mãi", "Code Khuyến Mãi", "Giá Trị", "Số Lượng", "Ngày Áp Dụng",
				"Hạn Sử Dụng", "Hãng", "Danh Mục", "Trạng Thái" };
		List<Promotion> productList = bus.getAllPromotions();
		List<Brand> brandList = brandbus.getAllBrands();
		List<Category> categoryList = categoryBus.getAllCategories();
		Map<Integer, String> brandMap = brandList.stream().collect(Collectors.toMap(Brand::getId, Brand::getBrandName));
		Map<Integer, String> brandMap2 = categoryList.stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
		table = new JTable();
		tableModel = new DefaultTableModel(
				productList.stream()
						.map(p -> new Object[] { p.getPromotionId(), p.getCode(),
								p.isPercent() ? p.getPercent() + "%" : p.getValue() + " VND", p.getQuantity(),
								p.getStartDate(),
								p.getExpirationDate(), brandMap.get(p.getBrandId()), brandMap2.get(p.getCategoryId()),
								statusToString(p.getStatus()) }) // add dữ liệu bên CSDL
						.toArray(Object[][]::new),
				columnNames);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(94);
		table.getColumnModel().getColumn(1).setPreferredWidth(98);
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
		LoadData();

		noResultLabel = new JLabel("Không tìm thấy sản phẩm");
		noResultLabel.setVisible(false);
		noResultLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		noResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noResultLabel.setVerticalAlignment(SwingConstants.NORTH);
		noResultLabel.setBackground(Color.BLUE);
		noResultLabel.setPreferredSize(new Dimension(0, 500));
		listPanel.add(noResultLabel, BorderLayout.CENTER);

		add(listPanel, BorderLayout.SOUTH);
	}

	private static int getValue(Promotion p) {
		if (p.isPercent()) {
			return p.getPercent();
		}
		return 0;
	}

	public void LoadData() {
		isLoading = true;
		LoadComboBoxData();
		LoadCategoryData();
		isLoading = false;
	}

	public void LoadComboBoxData() {
		List<app.DTO.Brand> brands = brandbus.getAllBrands();
		brandComboBox.removeAllItems();
		brandComboBox.addItem(new app.DTO.Brand(0, "Tất cả"));
		for (app.DTO.Brand brand : brands) {
			brandComboBox.addItem(brand);
		}
		brandComboBox.setSelectedIndex(0);
	}

	public void LoadCategoryData() {
		List<app.DTO.Category> categories = categoryBus.getAllCategories();
		categoryComboBox.removeAllItems();
		categoryComboBox.addItem(new app.DTO.Category(0, "Tất cả"));
		for (app.DTO.Category category : categories) {
			categoryComboBox.addItem(category);
		}
		categoryComboBox.setSelectedIndex(0);
	}

	public void HandleNameChange() {
		String name = nameSearchField.getText();
		System.out.println("Searching for name: " + name);
		if (name.isEmpty()) {
			HandleLoadAll();
			return;
		}
		List<Promotion> searchPromotions = bus.searchPromotions(name);
	}

	public void Search() {
		System.out.println("Search promotions");

		String keyword = nameSearchField.getText().trim().toLowerCase();
		Brand selectedBrand = (Brand) brandComboBox.getSelectedItem();
		Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
		String selectedStatus = (String) statusComboBox.getSelectedItem();

		int brandId = (selectedBrand != null) ? selectedBrand.getId() : 0;
		int categoryId = (selectedCategory != null) ? selectedCategory.getCategoryId() : 0;

		int status = -1;
		if (selectedStatus != null && !selectedStatus.toString().equals("Tất cả")) {
			switch (selectedStatus.toString()) {
				case "Còn áp dụng":
					status = 1;
					break;
				case "Ngưng áp dụng":
					status = 0;
					break;
				default:
					status = -1;
					break;
			}
		}

		System.out.println("Từ khóa: " + (keyword.isEmpty() ? "(không có)" : keyword));
		System.out.println("Hãng: " + (selectedBrand != null ? selectedBrand.getBrandName() : "Tất cả"));
		System.out.println("Danh mục: " + (selectedCategory != null ? selectedCategory.getCategoryName() : "Tất cả"));
		System.out.println("Trạng thái: " + (selectedStatus != null ? selectedStatus : "Tất cả"));
		
		List<Promotion> result = new ArrayList<>();

		if (!keyword.isEmpty() && brandId == 0 && categoryId == 0 && status == -1) {
			result = bus.searchPromotions(keyword);
		}
		else if (keyword.isEmpty() && (brandId != 0 || categoryId != 0 || status != -1)) {
			result = bus.filterPromotions(brandId, categoryId, status);
		}
		else if (!keyword.isEmpty()) {
			List<Promotion> searched = bus.searchPromotions(keyword);
			for (Promotion p : searched) {
				boolean match = true;
				if (brandId != 0 && p.getBrandId() != brandId) match = false;
				if (categoryId != 0 && p.getCategoryId() != categoryId) match = false;
				if (status != -1 && p.getStatus() != status) match = false;
				if (match) result.add(p);
			}
		}
		else {
			result = bus.getAllPromotions();
		}
		SetDataTable(result);
	}

	public void Refresh() {
		nameSearchField.setText("");
		brandComboBox.setSelectedIndex(0);
		categoryComboBox.setSelectedIndex(0);
		statusComboBox.setSelectedIndex(0);
		List<Promotion> allPromotions = bus.getAllPromotions();
		SetDataTable(allPromotions);
	}

	public void HandleLoadAll() {
		List<Promotion> allPromotions = bus.getAllPromotions();
		SetDataTable(allPromotions);
		return;
	}

	public void SetDataTable(List<Promotion> promotions) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		if (promotions.isEmpty()) {
			model.addRow(new Object[] { "", "", "", "", "" });
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

		for (Promotion p : promotions) {
			List<Brand> brandList = brandbus.getAllBrands();
			List<Category> categoryList = categoryBus.getAllCategories();
			Map<Integer, String> brandMap = brandList.stream().collect(Collectors.toMap(Brand::getId, Brand::getBrandName));
			Map<Integer, String> brandMap2 = categoryList.stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			model.addRow(
				new Object[] { 
					p.getPromotionId(), p.getCode(),
					p.isPercent() ? p.getPercent() + "%" : p.getValue() + " VND", p.getQuantity(),
					p.getStartDate(),
					p.getExpirationDate(), brandMap.get(p.getBrandId()), brandMap2.get(p.getCategoryId()),
					statusToString(p.getStatus()) 
				}
			);
		}
	}

	public String statusToString(int status) {
        switch (status) {
            case 1:
                return "Còn áp dụng";
            case 0:
                return "Ngưng áp dụng";
            default:
                return "Không xác định";
        }
    }

	public void Add() {
		AddPromotionFrame addFrame = new AddPromotionFrame("Thêm khuyến mãi mới");
		addFrame.setVisible(true);
	}

	public void Delete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			int promotionId = (int) DataTable.dataFromTable(selectedRow, tableModel)[0];
			String status = (String) DataTable.dataFromTable(selectedRow, tableModel)[8];

			if (status.equals("Còn áp dụng")) {
				boolean isConfirmed = ConfirmDialog.confirmDialog(this,
						"Khuyến mãi đang còn áp dụng. Bạn có chắc chắn muốn xóa khuyến mãi " + promotionId + " ?",
						"Xác nhận xóa khuyến mãi");
				if (isConfirmed) {
					bus.deactivatePromotion(promotionId);
					JOptionPane.showMessageDialog(this, "Ngưng áp dụng khuyến mãi " + promotionId + " thành công",
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (status.equals("Ngưng áp dụng")) {
				boolean isConfirmed = ConfirmDialog.confirmDialog(this,
						"Bạn có chắc muốn áp dụng lại khuyến mãi " + promotionId + " ?", "Xác nhận áp dụng lại khuyến mãi");
				if (isConfirmed) {
					int rows = bus.activatePromotion(promotionId);
					if (rows > 0) {
						JOptionPane.showMessageDialog(this, "Áp dụng lại khuyến mãi " + promotionId + " thành công",
								"Thông báo", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Áp dụng lại khuyến mãi " + promotionId + " thất bại",
								"Thông báo", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			HandleLoadAll();
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để xóa", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void ConfirmDelete(int promotionId) {
		boolean isConfirmed = ConfirmDialog.confirmDialog(this, "Bạn có chắc chắn xóa  " + promotionId + " ?",
				"Xác nhận xóa khuyến mãi");
		if (isConfirmed) {
			bus.deactivatePromotion(promotionId);
			JOptionPane.showMessageDialog(this, "Xóa khuyến mãi " + promotionId + " thành công", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
		HandleLoadAll();
	}

	public void Edit() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để chỉnh sửa", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int promotionId = (int) DataTable.dataFromTable(selectedRow, tableModel)[0];
		UpdatePromotionFrame updateFrame = new UpdatePromotionFrame("Chỉnh sửa khuyến mãi", this, promotionId);
		updateFrame.setVisible(true);
	}

	public void ImportExcel() {
		String filePath = DataTable.chooseFile();
        boolean isConfirmed = ConfirmDialog.confirmDialog(this, "File khuyến mãi tại  " + filePath + " ?",
                "Xác nhận thêm danh sách khuyến mãi từ Excel");
        if (isConfirmed) {
            boolean result = bus.importDataFromExcel(filePath);
            if (result) {
                JOptionPane.showMessageDialog(this, "Thêm danh sách khuyến mãi thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                List<Promotion> allPromotions = bus.getAllPromotions();
                SetDataTable(allPromotions);
            }
        }
	}

	public void ExportExcel() {
        String savePath = DataTable.chooseFolder(this, "promotion.xlsx");
        DataTable.exportDataToExcel(savePath, table);
        JOptionPane.showMessageDialog(this, "Xuất dữ liệu khuyến mãi ra Excel thành công!", "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
	}
}