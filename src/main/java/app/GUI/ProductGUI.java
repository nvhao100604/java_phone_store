package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import app.BUS.ProductBUS;
import app.DTO.Product;
import app.GUI.CustomPanels.FilterPanel;
import app.GUI.CustomPanels.khungchucnang;
import app.GUI.interfaces.FunctionPanel;

public class ProductGUI extends JPanel implements FunctionPanel {

	JTable table;
	ProductBUS bus;
	khungchucnang khung;

	public ProductGUI() {
		// Constructor implementation
		initialize();
	}

	public void initialize() {
		bus = new ProductBUS();
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 200));
		topPanel.setLayout(new BorderLayout());
		topPanel.setBackground(new Color(0, 0, 0));
		add(topPanel, BorderLayout.NORTH);

		khung = new khungchucnang(this);
		topPanel.add(khung, BorderLayout.WEST);

		FilterPanel filterPanel = new FilterPanel();
		topPanel.add(filterPanel, BorderLayout.CENTER);

		JLabel title = new JLabel("Danh sách sản phẩm", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 28));
		// title.setBackground(new Color(0, 64, 128));
		title.setOpaque(true);
		title.setPreferredSize(new Dimension(0, 80));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(title, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
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
				cell.setFont(cell.getFont().deriveFont(Font.BOLD)); // in đậm
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
		scrollPane.setPreferredSize(new Dimension(0, 600));
		add(scrollPane, BorderLayout.SOUTH);
	}

	public void Add() {
		System.out.println("Add product");
	}

	public void Delete() {
		System.out.println("Delete product");
	}

	public void Edit() {
		System.out.println("Edit product");
	}

	public void ImportExcel() {
		System.out.println("Import Excel product");
	}

	public void ExportExcel() {
		System.out.println("Export Excel product");
	}
}
