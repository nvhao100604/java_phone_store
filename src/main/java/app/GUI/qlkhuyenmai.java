package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.math.BigDecimal;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;

import app.BUS.BrandBUS;
import app.BUS.CategoryBUS;
import app.BUS.PromotionBUS;
import app.DTO.Brand;
import app.DTO.Category;
import app.DTO.Promotion;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.ProductGUI.AddProductFrame;
import app.GUI.ProductGUI.UpdateProductFrame;
import app.GUI.interfaces.FunctionPanel;
import app.utils.ConfirmDialog;
import app.utils.DataTable;
import app.utils.DecimalFilter;
import app.utils.ImportExcel;

public class qlkhuyenmai extends JPanel implements FunctionPanel
{
	private int mainWidth;
	private int mainHeight;
	private PromotionBUS bus;
	private BrandBUS brandbus;
	private CategoryBUS categoryBus;
	private khungchucnang khung;
	private JTextField nameSearchField;
	private JTextField brandSearchField;
	private JComboBox<app.DTO.Category> categoryComboBox;
	private JTextField priceFromField;
	private JTextField priceToField;
	private JLabel title;
	private JLabel noResultLabel;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;

	public qlkhuyenmai() 
	{
		initialize();
	}

	public void initialize() {
		bus = new PromotionBUS();
		categoryBus = new CategoryBUS();
		brandbus =new BrandBUS();
		setLayout(new BorderLayout());

		mainWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		mainHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		// System.out.println("Main W + H = " + mainWidth + " + " + mainHeight);

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, mainHeight < 1200 ? mainHeight - 830 : mainHeight - 950));
		topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
		topPanel.setLayout(new BorderLayout());
		// topPanel.setBackground(new Color(0, 0, 0));
		add(topPanel, BorderLayout.NORTH);

		khung = new khungchucnang(this);
		topPanel.add(khung, BorderLayout.WEST);

		FilterPanel filterPanel = new FilterPanel();
		filterPanel.setLayout(new GridLayout(3, 4, 40, 10));
		// filterPanel.setBackground(new Color(255, 255, 0));
		topPanel.add(filterPanel, BorderLayout.CENTER);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		namePanel.setOpaque(false);
		namePanel.setBackground(null);
		namePanel.setBackground(new Color(255, 0, 0));
		namePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		// namePanel.setPreferredSize(new Dimension(200, 100));

		JLabel nameSearchLabel = new JLabel("Tên sản phẩm:");
		nameSearchLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameSearchLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		namePanel.add(nameSearchLabel, BorderLayout.WEST);

		nameSearchField = new JTextField(15);
		nameSearchField.setFont(new Font("Arial", Font.PLAIN, 20));
		nameSearchField.setBackground(new Color(240, 240, 240));
		nameSearchField.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
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

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new BorderLayout());
		typePanel.setOpaque(false);
		typePanel.setBackground(null);
		typePanel.setBackground(new Color(255, 0, 0));
		typePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		// namePanel.setPreferredSize(new Dimension(200, 100));

		JLabel typeLabel = new JLabel("Loại sản phẩm:");
		typeLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		typeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		typePanel.add(typeLabel, BorderLayout.WEST);

		categoryComboBox = new JComboBox<app.DTO.Category>();
		categoryComboBox.setEditable(false);
		categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
		// categoryComboBox.setBackground(new Color(240, 240, 240));
		categoryComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
		categoryComboBox.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
				new EmptyBorder(0, 10, 0, 10)));
		LoadComboBoxData();
		categoryComboBox.addActionListener(e -> {
			Object selectedItem = categoryComboBox.getSelectedItem();
			System.out.println("Giá trị được chọn: " + selectedItem);

			handleCategoryChange(selectedItem);
		});
		typePanel.add(categoryComboBox, BorderLayout.CENTER);

		JPanel brandPanel = new JPanel();
		brandPanel.setLayout(new BorderLayout());
		brandPanel.setOpaque(false);
		brandPanel.setBackground(null);
		brandPanel.setBackground(new Color(255, 0, 0));
		brandPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

		JLabel brandLabel = new JLabel("Hãng sản xuất:");
		brandLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		brandLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		brandPanel.add(brandLabel, BorderLayout.WEST);

		brandSearchField = new JTextField(15);
		brandSearchField.setFont(new Font("Arial", Font.PLAIN, 20));
		brandSearchField.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
				new EmptyBorder(5, 5, 5, 5)));
		brandSearchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				HandleBrandChange();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				HandleBrandChange();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				HandleBrandChange();
			}
		});
		brandSearchField.setBackground(new Color(240, 240, 240));
		brandPanel.add(brandSearchField, BorderLayout.CENTER);

		JPanel pricePanel = new JPanel(new GridBagLayout());
		pricePanel.setOpaque(false);
		pricePanel.setBackground(null);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 0, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		int col = 0;

		DocumentListener documentChangeListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				HandlePriceChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				HandlePriceChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				HandlePriceChange();
			}
		};

		DecimalFilter decimalFilter = new DecimalFilter();

		JLabel priceLabel = new JLabel("Giá:");
		priceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		priceLabel.setBorder(new EmptyBorder(0, 10, 0, 10));

		gbc.gridx = col++;
		gbc.weightx = 0;
		pricePanel.add(priceLabel, gbc);

		priceFromField = new JTextField(15);
		priceFromField.setFont(new Font("Arial", Font.PLAIN, 20));
		priceFromField.setBackground(new Color(240, 240, 240));
		priceFromField.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
				new EmptyBorder(5, 5, 5, 5)));
		((AbstractDocument) priceFromField.getDocument()).setDocumentFilter(decimalFilter);
		priceFromField.getDocument().addDocumentListener(documentChangeListener);

		gbc.gridx = col++;
		gbc.weightx = 0.5;
		pricePanel.add(priceFromField, gbc);

		JLabel line = new JLabel("-");
		line.setFont(new Font("Arial", Font.PLAIN, 20));
		line.setBorder(new EmptyBorder(0, 10, 0, 10));
		line.setHorizontalAlignment(SwingConstants.CENTER);

		gbc.gridx = col++;
		gbc.weightx = 0;
		pricePanel.add(line, gbc);

		priceToField = new JTextField(15);
		priceToField.setFont(new Font("Arial", Font.PLAIN, 20));
		priceToField.setBackground(new Color(240, 240, 240));
		priceToField.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
				new EmptyBorder(5, 5, 5, 5)));
		((AbstractDocument) priceToField.getDocument()).setDocumentFilter(decimalFilter);
		priceToField.getDocument().addDocumentListener(documentChangeListener);

		gbc.gridx = col++;
		gbc.weightx = 0.5;
		pricePanel.add(priceToField, gbc);

		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);

		JPanel processPanel = new JPanel();
		processPanel.setBackground(null);
		processPanel.setOpaque(false);
		processPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JButton filterButton = new JButton("Tìm kiếm");
		filterButton.setFont(new Font("Arial", Font.PLAIN, 20));
		filterButton.setBorder(new EmptyBorder(10, 15, 10, 15));
		filterButton.setBackground(Color.blue);
		filterButton.setForeground(Color.WHITE);
		filterButton.setVerticalAlignment(SwingConstants.CENTER);
		filterButton.addActionListener(e -> Search());
		processPanel.add(filterButton);

		JButton refreshButton = new JButton("Làm mới");
		refreshButton.setFont(new Font("Arial", Font.PLAIN, 20));
		refreshButton.setBorder(new EmptyBorder(10, 15, 10, 15));
		refreshButton.setBackground(new Color(173, 255, 47));
		refreshButton.setForeground(Color.BLACK);
		refreshButton.setVerticalAlignment(SwingConstants.CENTER);
		refreshButton.addActionListener(e -> Refresh());
		processPanel.add(refreshButton);

		filterPanel.add(namePanel);
		filterPanel.add(typePanel);
		filterPanel.add(brandPanel);
		filterPanel.add(pricePanel);
		filterPanel.add(emptyPanel);
		filterPanel.add(processPanel);

		title = new JLabel("Danh sách sản phẩm ()", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(0, 0));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(title, BorderLayout.CENTER);

		// Data table
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		scrollPane = new JScrollPane();
		String[] columnNames = { "Mã khuyến mãi", "Code Khuyến Mãi", "Giá Trị", "Số Lượng", "Ngày Áp Dụng", "Hạn Sử Dụng", "Hãng", "Danh Mục", "Trạng Thái"};
		List<Promotion> productList = bus.getAllPromotions();
		List<Brand> brandList = brandbus.getAllBrands();
		Map<Integer, String> brandMap = brandList.stream()
		        .collect(Collectors.toMap(Brand::getId, Brand::getBrandName));
		List<Category> categoryList = categoryBus.getAllCategories();
		Map<Integer, String> brandMap2 = categoryList.stream()
		        .collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
		SetTitleQuantity(productList.size());
		table = new JTable();
		tableModel = new DefaultTableModel(
				productList.stream()
						.map(p -> new Object[] { p.getPromotionId(), p.getCode(), p.isPercent()? p.getPercent()+"%" : p.getValue() +" VND", p.getQuantity(), p.getStartDate(),
								p.getExpirationDate(), brandMap.get(p.getBrandId()), brandMap2.get(p.getCategoryId()), p.getStatus() }) // add dữ liệu bên CSDL
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
		table.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200),
				2));

		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(0, 64, 128));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 16));
		header.setPreferredSize(new Dimension(header.getWidth(), 35));

		((DefaultTableCellRenderer) header.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer rightBoldRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel cell = (JLabel) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);

				cell.setHorizontalAlignment(SwingConstants.RIGHT);
				cell.setFont(cell.getFont().deriveFont(Font.BOLD));
				return cell;
			}
		};

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightBoldRenderer);

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);

		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(new Dimension(0, mainHeight - 600));
		listPanel.add(scrollPane, BorderLayout.NORTH);

		noResultLabel = new JLabel("Không tìm thấy sản phẩm");
		noResultLabel.setVisible(false);
		noResultLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		noResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noResultLabel.setVerticalAlignment(SwingConstants.NORTH);
		noResultLabel.setBackground(Color.BLUE);
		noResultLabel.setPreferredSize(new Dimension(0, mainHeight - 500));
		listPanel.add(noResultLabel, BorderLayout.CENTER);

		add(listPanel, BorderLayout.SOUTH);
	}
	
	private static String getValue(Promotion p) {
		if(p.isPercent()) {
			return p.getPercent();
		}
	}

	public void LoadComboBoxData() {
		List<app.DTO.Category> categories = categoryBus.getAllCategories();
		categoryComboBox.removeAllItems();
		categoryComboBox.addItem(new app.DTO.Category(0, "Tất cả"));
		for (app.DTO.Category category : categories) {
			categoryComboBox.addItem(category);
		}
		categoryComboBox.setSelectedIndex(0);
	}

	public void SetTitleQuantity(int quantity) {
		title.setText("Danh sách sản phẩm (" + quantity + ")");
	}

	public void HandleNameChange() {
		String name = nameSearchField.getText();
		System.out.println("Searching for name: " + name);
		if (name.isEmpty()) {
			HandleLoadAll();
			return;
		}
		//List<Promotion> filteredProducts = bus.searchProductsByName(name);
		//SetDataTable(filteredProducts);
	}

	public void HandleBrandChange() {
		String brandName = brandSearchField.getText();
		if (brandName.isEmpty()) {
			HandleLoadAll();
			return;
		}

		//List<Product> filteredProducts = bus.filterProductsByBrandName(brandName);
		//SetDataTable(filteredProducts);
	}

	public void handleCategoryChange(Object selectedItem) {
		if (selectedItem instanceof app.DTO.Category) {
			app.DTO.Category selectedCategory = (app.DTO.Category) selectedItem;
			int categoryId = selectedCategory.getCategoryId();
			System.out.println("Selected Category ID: " + categoryId);

			//List<Product> filteredProducts;
			if (categoryId == 0) {
				//filteredProducts = bus.getAll();
			} else {
				//filteredProducts = bus.filterProductsByCategory(categoryId);
			}
			//SetDataTable(filteredProducts);
		}
	}

	public void HandlePriceChange() {
		String fromText = priceFromField.getText().trim();
		String toText = priceToField.getText().trim();

		BigDecimal fromPrice = fromText.isEmpty() ? null : new BigDecimal(fromText);
		BigDecimal toPrice = toText.isEmpty() ? null : new BigDecimal(toText);

		if (fromPrice == null && toPrice == null || fromPrice.compareTo(toPrice) >= 0) {
			HandleLoadAll();
		}

		//List<Product> filteredProducts = bus.filterProductsByPriceRange(fromPrice, toPrice);
		//SetDataTable(filteredProducts);
	}

	public void Search() {
		System.out.println("Search product");
		String keyword = nameSearchField.getText().equals("") ? null : nameSearchField.getText();
		BigDecimal minPrice = priceFromField.getText().isEmpty() ? null
				: new BigDecimal(priceFromField.getText());
		BigDecimal maxPrice = priceToField.getText().isEmpty() ? null
				: new BigDecimal(priceToField.getText());
		int categoryId = categoryComboBox.getSelectedItem() instanceof app.DTO.Category
				? ((app.DTO.Category) categoryComboBox.getSelectedItem()).getCategoryId()
				: 0;
		String brandName = brandSearchField.getText().equals("") ? null : brandSearchField.getText();
		int sortByPriceAscending = 1;

		//List<Product> filteredProducts = bus.filterProducts(
			//	keyword,
			//	minPrice,
			//	maxPrice,
			//	categoryId,
			//	brandName,
			//	sortByPriceAscending);
		// SetDataTable(filteredProducts);
	}

	public void Refresh() {
		nameSearchField.setText("");
		brandSearchField.setText("");
		priceFromField.setText("");
		priceToField.setText("");
		categoryComboBox.setSelectedIndex(0);
		// List<Product> allProducts = bus.getAll();
		// SetDataTable(allProducts);
	}

	public void HandleLoadAll() {
		// List<Product> allProducts = bus.getAll();
		// SetDataTable(allProducts);
		return;
	}

	public void SetDataTable(List<Promotion> products) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		SetTitleQuantity(products.size());
		if (products.isEmpty()) {
			model.addRow(new Object[] { "", "", "", "", "" });
			noResultLabel.setVisible(true);
			scrollPane.setPreferredSize(new Dimension(0, mainHeight - 1000));
			revalidate();
			repaint();
			return;
		}

		if (noResultLabel != null) {
			noResultLabel.setVisible(false);
			scrollPane.setPreferredSize(new Dimension(0, mainHeight - 500));
			revalidate();
			repaint();
		}
		for (Promotion p : products) {
			// model.addRow(new Object[] { p.getProductId(), p.getProductName(),
				//	p.getCategory(), p.getBrand(), p.getSalePrice() });
		}
	}

	public void Add() {
		// System.out.println("Add product");
		AddProductFrame addFrame = new AddProductFrame("Thêm sản phẩm");
		addFrame.setVisible(true);
	}

	public void Delete() {
		// System.out.println("Delete product");
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			int productId = (int) DataTable.dataFromTable(selectedRow, tableModel)[0];
			System.out.println("check id: " + productId);
			ConfirmDelete(productId);
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng cần xóa.", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void ConfirmDelete(int productId) {
		boolean isConfirmed = ConfirmDialog.confirmDialog(this, "Bạn có chắc chắn xóa sản phẩm " + productId + " ?",
				"Xác nhận xóa sản phẩm");
		if (isConfirmed) {
			// bus.softDeleteProductById(productId);
			JOptionPane.showMessageDialog(this, "Xóa sản phẩm " + productId + " thành công", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
		HandleLoadAll();
	}

	public void Edit() {
		System.out.println("Edit product");
		UpdateProductFrame editFrame = new UpdateProductFrame("Cập nhật thông tin sản phẩm");
		editFrame.setVisible(true);
	}

	public void ImportExcel() {
		System.out.println("Import Excel product");
		String filePath = ImportExcel.chooseFile();
		boolean isConfirmed = ConfirmDialog.confirmDialog(this, "File sản phẩm tại  " + filePath + " ?",
				"Xác nhận thêm danh sách sản phẩm");
		if (isConfirmed) {
			// boolean result = bus.importDataFromExcel(filePath);
		//	if (result) {
				JOptionPane.showMessageDialog(this, "Thêm danh sách sản phẩm thành công", "Thông báo",
						JOptionPane.INFORMATION_MESSAGE);
			//	List<Product> allProducts = bus.getAllDesc();
			//	SetDataTable(allProducts);
			}
		}
//	}

	public void ExportExcel() {
		// System.out.println("Export Excel product");
		DataTable.exportDataToExcel(DataTable.directoryPath + "/export/productList.xlsx", table);
		JOptionPane.showMessageDialog(this, "Xuất file Excel thành công", "Thông báo",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
