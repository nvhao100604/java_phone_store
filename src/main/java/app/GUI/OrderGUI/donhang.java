package app.GUI.OrderGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.database.DBConnect;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class donhang {

	private JFrame frame;

	// ... (Các hằng số màu và font giữ nguyên) ...
	private static final Color BG_LIGHT_GRAY = new Color(248, 248, 248);
	private static final Color BG_WHITE = Color.WHITE;
	private static final Color BORDER_GRAY = new Color(220, 220, 220);
	private static final Color TEXT_DARK = new Color(50, 50, 50);
	private static final Color TEXT_LIGHT = new Color(100, 100, 100);

	private static final Color PRIMARY_BLUE = new Color(52, 152, 219);
	private static final Color PRIMARY_BLUE_DARK = new Color(41, 128, 185);

	private static final Color SUCCESS_GREEN = new Color(46, 204, 113);
	private static final Color SUCCESS_GREEN_DARK = new Color(39, 174, 96);

	private static final Color DANGER_RED = new Color(231, 76, 60);
	private static final Color DANGER_RED_DARK = new Color(192, 57, 43);

	private static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
	private static final Font FONT_INPUT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
	private static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	private static final Font FONT_TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	private static final Font FONT_TOTAL = new Font(Font.SANS_SERIF, Font.BOLD, 18);

	private Map<String, Double> productPrices; // Dữ liệu sẽ được tải vào đây

	// ... (Các thành phần UI giữ nguyên) ...
	private JTextField customerNameField;
	private JTextField customerAddressField;
	private JComboBox<String> productComboBox;
	private JSpinner quantitySpinner;
	private JButton addButton;
	private JButton submitButton;
	private JButton clearButton;
	private JTable cartTable;
	private DefaultTableModel tableModel;
	private JLabel totalLabel;

	private NumberFormat currencyFormatter;
	private JComboBox<String> variantComboBox;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					donhang window = new donhang();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public donhang() {
		initialize();
	}

	private void initialize() {

		// <<< SỬA ĐỔI >>> Tải dữ liệu trước khi vẽ frame
		initializeProducts(); // Tải dữ liệu từ CSDL

		frame = new JFrame();
		frame.setTitle("Biểu mẫu Đặt hàng");
		frame.setSize(780, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout(15, 15));

		frame.getContentPane().setBackground(BG_LIGHT_GRAY);
		((JPanel) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

		// Các panel này giờ sẽ sử dụng dữ liệu đã tải
		JPanel customerPanel = createCustomerPanel();
		JPanel productPanel = createProductPanel();
		JPanel cartPanel = createCartPanel();
		JPanel actionPanel = createActionPanel();

		JPanel topPanel = new JPanel(new BorderLayout(15, 15));
		topPanel.setOpaque(false);
		topPanel.add(customerPanel, BorderLayout.NORTH);
		topPanel.add(productPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout(15, 15));
		bottomPanel.setOpaque(false);
		bottomPanel.add(totalLabel, BorderLayout.CENTER);
		bottomPanel.add(actionPanel, BorderLayout.EAST);

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(cartPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		addListeners();

		// <<< SỬA ĐỔI >>> Kiểm tra nếu tải CSDL thất bại
		if (productPrices.isEmpty()) {
			JOptionPane.showMessageDialog(frame,
					"Không thể tải danh sách sản phẩm từ CSDL.\nKiểm tra file 'store.db' và driver JDBC.",
					"Lỗi CSDL",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * <<< SỬA ĐỔI >>>
	 * Tải sản phẩm từ CSDL thay vì hard-code
	 */
	private void initializeProducts() {
		try {
			// Gọi lớp DatabaseConnector để tải dữ liệu
			productPrices = (Map<String, Double>) DBConnect.getConnection();
		} catch (Exception e) {
			// Nếu có lỗi, khởi tạo map rỗng và chuẩn bị báo lỗi
			productPrices = new HashMap<>();
			System.err.println("Lỗi khi tải sản phẩm: " + e.getMessage());
			e.printStackTrace();
			// JOptionPane sẽ hiển thị trong hàm initialize() sau khi 'frame' được tạo
		}
	}

	private JPanel createCustomerPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(BG_WHITE);

		Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
		TitledBorder title = BorderFactory.createTitledBorder(
				outerBorder,
				"Thông tin khách hàng",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				FONT_TITLE,
				TEXT_DARK);

		panel.setBorder(BorderFactory.createCompoundBorder(
				title,
				BorderFactory.createEmptyBorder(10, 15, 15, 15)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel nameLabel = new JLabel("Tên khách hàng:");
		nameLabel.setFont(FONT_LABEL);
		nameLabel.setForeground(TEXT_LIGHT);
		panel.add(nameLabel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 1.0;
		customerNameField = new JTextField(20);
		customerNameField.setFont(FONT_INPUT);
		customerNameField.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
		customerNameField.setBackground(new Color(250, 250, 250));
		panel.add(customerNameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		JLabel addressLabel = new JLabel("Địa chỉ:");
		addressLabel.setFont(FONT_LABEL);
		addressLabel.setForeground(TEXT_LIGHT);
		panel.add(addressLabel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 1.0;
		customerAddressField = new JTextField(20);
		customerAddressField.setFont(FONT_INPUT);
		customerAddressField.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
		customerAddressField.setBackground(new Color(250, 250, 250));
		panel.add(customerAddressField, gbc);

		return panel;
	}

	/**
	 * <<< SỬA ĐỔI >>>
	 * Tải tùy chọn (variants) từ CSDL
	 */
	private JPanel createProductPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(BG_WHITE);

		Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
		TitledBorder title = BorderFactory.createTitledBorder(
				outerBorder,
				"Chọn sản phẩm",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				FONT_TITLE,
				TEXT_DARK);

		panel.setBorder(BorderFactory.createCompoundBorder(
				title,
				BorderFactory.createEmptyBorder(10, 15, 15, 15)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.WEST;

		// Hàng 1: Sản phẩm (Tải từ productPrices đã được khởi tạo)
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel productLabel = new JLabel("Sản phẩm:");
		productLabel.setFont(FONT_LABEL);
		productLabel.setForeground(TEXT_LIGHT);
		panel.add(productLabel, gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		// Dữ liệu 'productPrices' đã được tải trong initializeProducts()
		productComboBox = new JComboBox<>(productPrices.keySet().toArray(new String[0]));
		productComboBox.setFont(FONT_INPUT);
		productComboBox.setBackground(new Color(250, 250, 250));
		panel.add(productComboBox, gbc);

		// <<< SỬA ĐỔI >>> Tải danh sách biến thể (Variant List) từ CSDL
		List<String> variants;
		try {
			variants = (List<String>) DBConnect.getConnection();
		} catch (Exception e) {
			variants = new ArrayList<>(); // Khởi tạo rỗng nếu lỗi
			System.err.println("Lỗi khi tải tùy chọn: " + e.getMessage());
			e.printStackTrace();
			// Sẽ báo lỗi chung ở cuối hàm initialize()
		}

		// Hàng 2: Tùy chọn (Màu + Dung lượng)
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		JLabel variantLabel = new JLabel("Tùy chọn:");
		variantLabel.setFont(FONT_LABEL);
		variantLabel.setForeground(TEXT_LIGHT);
		panel.add(variantLabel, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		// Dùng danh sách 'variants' vừa tải
		variantComboBox = new JComboBox<>(variants.toArray(new String[0]));
		variantComboBox.setFont(FONT_INPUT);
		variantComboBox.setBackground(new Color(250, 250, 250));
		panel.add(variantComboBox, gbc);

		// Hàng 3: Số lượng và Nút Thêm
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;

		gbc.gridx = 0;
		JLabel quantityLabel = new JLabel("Số lượng:");
		quantityLabel.setFont(FONT_LABEL);
		quantityLabel.setForeground(TEXT_LIGHT);
		panel.add(quantityLabel, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		quantitySpinner.setFont(FONT_INPUT);
		((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField()
				.setBorder(BorderFactory.createLineBorder(BORDER_GRAY));
		((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setBackground(new Color(250, 250, 250));
		panel.add(quantitySpinner, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.5;
		addButton = new JButton("Thêm vào giỏ");
		addButton.setFont(FONT_BUTTON);
		addButton.setBackground(PRIMARY_BLUE);
		addButton.setForeground(Color.WHITE);
		addButton.setOpaque(true); // Sửa lỗi nút trắng
		addButton.setFocusPainted(false);
		addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		panel.add(addButton, gbc);
		addHoverEffect(addButton, PRIMARY_BLUE, PRIMARY_BLUE_DARK);

		return panel;
	}

	private JPanel createCartPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(BG_WHITE);

		Border outerBorder = BorderFactory.createLineBorder(BORDER_GRAY, 1);
		TitledBorder title = BorderFactory.createTitledBorder(
				outerBorder,
				"Giỏ hàng",
				TitledBorder.LEFT,
				TitledBorder.TOP,
				FONT_TITLE,
				TEXT_DARK);
		panel.setBorder(title);

		String[] columnNames = { "Sản phẩm", "Tùy chọn", "Số lượng", "Đơn giá", "Thành tiền" };
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
		header.setResizingAllowed(true);

		JScrollPane scrollPane = new JScrollPane(cartTable);
		scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1));
		panel.add(scrollPane, BorderLayout.CENTER);

		totalLabel = new JLabel("Tổng cộng: 0 VNĐ");
		totalLabel.setFont(FONT_TOTAL);
		totalLabel.setForeground(SUCCESS_GREEN);
		totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		return panel;
	}

	private JPanel createActionPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panel.setOpaque(false);

		submitButton = new JButton("Đặt hàng");
		clearButton = new JButton("Xóa giỏ hàng");

		submitButton.setFont(FONT_BUTTON);
		submitButton.setBackground(SUCCESS_GREEN);
		submitButton.setForeground(Color.WHITE);
		submitButton.setOpaque(true); // Sửa lỗi nút trắng
		submitButton.setFocusPainted(false);
		submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		clearButton.setFont(FONT_BUTTON);
		clearButton.setBackground(DANGER_RED);
		clearButton.setForeground(Color.WHITE);
		clearButton.setOpaque(true); // Sửa lỗi nút trắng
		clearButton.setFocusPainted(false);
		clearButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		panel.add(clearButton);
		panel.add(submitButton);

		addHoverEffect(submitButton, SUCCESS_GREEN, SUCCESS_GREEN_DARK);
		addHoverEffect(clearButton, DANGER_RED, DANGER_RED_DARK);

		return panel;
	}

	// Phương thức trợ giúp hover (giữ nguyên)
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

			@Override
			public void mousePressed(MouseEvent e) {
				if (button.isEnabled())
					button.setBackground(hoverColor.darker());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (button.isEnabled()) {
					if (button.getBounds().contains(e.getPoint())) {
						button.setBackground(hoverColor);
					} else {
						button.setBackground(defaultColor);
					}
				}
			}
		});
	}

	private void addListeners() {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addProductToCart();
			}
		});

		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearCart();
			}
		});

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitOrder();
			}
		});
	}

	private void addProductToCart() {
		// <<< SỬA ĐỔI >>> Kiểm tra xem có sản phẩm nào không
		String selectedProduct = (String) productComboBox.getSelectedItem();
		if (selectedProduct == null || productPrices.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Không có sản phẩm nào để thêm.", "Lỗi", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String selectedVariant = (String) variantComboBox.getSelectedItem();
		if (selectedVariant == null) {
			JOptionPane.showMessageDialog(frame, "Vui lòng chọn một tùy chọn.", "Lỗi", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int quantity = (int) quantitySpinner.getValue();
		double unitPrice = productPrices.get(selectedProduct);
		double lineTotal = unitPrice * quantity;

		tableModel.addRow(new Object[] {
				selectedProduct,
				selectedVariant,
				quantity,
				currencyFormatter.format(unitPrice),
				lineTotal
		});

		updateTotal();

		quantitySpinner.setValue(1);
	}

	private void updateTotal() {
		double total = 0;
		int totalColumnIndex = 4;

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Object cellValue = tableModel.getValueAt(i, totalColumnIndex);
			if (cellValue instanceof Double) {
				total += (double) cellValue;
			}
		}

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Object cellValue = tableModel.getValueAt(i, totalColumnIndex);
			if (cellValue instanceof Double) {
				tableModel.setValueAt(currencyFormatter.format((Double) cellValue), i, totalColumnIndex);
			}
		}

		totalLabel.setText("Tổng cộng: " + currencyFormatter.format(total));
	}

	private void clearCart() {
		tableModel.setRowCount(0);
		updateTotal();
	}

	private void submitOrder() {
		String customerName = customerNameField.getText();
		String customerAddress = customerAddressField.getText();

		if (customerName.trim().isEmpty() || customerAddress.trim().isEmpty()) {
			JOptionPane.showMessageDialog(frame,
					"Vui lòng nhập đầy đủ Tên và Địa chỉ khách hàng.",
					"Lỗi thông tin",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(frame,
					"Giỏ hàng đang trống. Vui lòng thêm sản phẩm.",
					"Lỗi giỏ hàng",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String totalText = totalLabel.getText();
		String message = String.format(
				"Đơn hàng đã được đặt thành công!\n\nKhách hàng: %s\nĐịa chỉ: %s\n%s",
				customerName, customerAddress, totalText);

		JOptionPane.showMessageDialog(frame,
				message,
				"Đặt hàng thành công",
				JOptionPane.INFORMATION_MESSAGE);

		clearCart();
		customerNameField.setText("");
		customerAddressField.setText("");
	}
}