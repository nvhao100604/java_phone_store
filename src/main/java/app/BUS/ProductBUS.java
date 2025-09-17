package app.BUS;

import java.math.BigDecimal;
import java.util.List;

import app.DAO.ProductDAO;
import app.DTO.Product;

public class ProductBUS {
	private final ProductDAO dao;

	public ProductBUS() {
		dao = new ProductDAO();
	}

	public Product getProductById(int productId) {
		return dao.getProductById(productId);
	}

	public List<Product> getAll() {
		return dao.getAll();
	}

	public int AddProduct(Product product) {
		return dao.addProduct(product);
	}

	public int DeleteProductById(int productId) {
		return dao.deleteProductById(productId);
	}

	public int softDeleteProductById(int productId) {
		return dao.softDeleteProductById(productId);
	}

	public int UpdateProduct(Product product) {
		return dao.updateProduct(product);
	}

	public List<Product> searchProductsByName(String keyword) {
		return dao.searchProductsByName(keyword);
	}

	public List<Product> filterProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
		return dao.filterProductsByPriceRange(minPrice, maxPrice);
	}

	public List<Product> filterProductsByCategory(int categoryId) {
		return dao.filterProductsByCategory(categoryId);
	}

	public List<Product> filterProductsByBrand(int brandId) {
		return dao.filterProductsByBrand(brandId);
	}

	public List<Product> sortProductsByPrice(boolean ascending) {
		return dao.sortProductsByPrice(ascending);
	}

	public static void main(String[] args) {
		ProductBUS bus = new ProductBUS();
		// List<Product> list = bus.getAll();
		// for (Product product : list) {
		// System.out.println("Product check: " + product.getProductName());
		// }

		// Product myProduct = new Product(116, "Smartphone XYZ CAC CAC CAC", 1, new
		// BigDecimal("500.00"), 4,
		// "https://example.com/images/xyz.jpg",
		// "A high-end smartphone with a powerful processor and a stunning display.",
		// new BigDecimal("750.00"), 1);
		// // int id = bus.AddProduct(myProduct);
		// int id = bus.UpdateProduct(myProduct);
		// if (id > 0) {
		// System.out.println("Update thành công product với mã: " +
		// myProduct.getProductId());
		// } else {
		// System.out.println("Lỗi mày");
		// }
		// ;
		// int productId = 113;
		// if (bus.DeleteProductById(productId) > 0) {
		// System.out.printf("Deleted: %d", productId);
		// }
	}
}
