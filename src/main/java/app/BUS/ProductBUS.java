package app.BUS;

import java.math.BigDecimal;
import java.util.List;

import app.DAO.ProductDAO;
import app.DTO.Product;
import app.DTO.ProductDetail;

public class ProductBUS {
	private final ProductDAO dao;
	private final ProductDetailBUS detailBUS;

	public ProductBUS() {
		dao = new ProductDAO();
		this.detailBUS = new ProductDetailBUS();
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

	public int restoreProduct(int productId) {
		return dao.restoreProduct(productId);
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

	public List<Product> filterProductsByBrandName(String brandName) {
		return dao.filterProductsByBrandName(brandName);
	}

	public List<Product> sortProductsByPrice(boolean ascending) {
		return dao.sortProductsByPrice(ascending);
	}

	public List<Product> filterProducts(
			String keyword,
			BigDecimal minPrice,
			BigDecimal maxPrice,
			int categoryId,
			String brandName,
			int sortByPriceAscending) {
		return dao.filterProducts(
				keyword,
				minPrice,
				maxPrice,
				categoryId,
				brandName,
				sortByPriceAscending);
	}

	public int addProductDetail(int productId, String color, String capacity, BigDecimal priceAdjustment) {
		return detailBUS.addProductDetail(productId, color, capacity, priceAdjustment);
	}

	public int saveProductDetails(Product product) {
		int row = 0;
		for (ProductDetail detail : product.getProductDetails()) {
			int response = detailBUS.addProductDetail(
					product.getProductId(),
					detail.getColor(),
					detail.getCapacity(),
					detail.getPriceAdjustment());
			row += response > 0 ? 1 : 0;
		}
		return row;
	}

	// public static void main(String[] args) {
	// ProductBUS bus = new ProductBUS();
	// List<Product> products = bus.getAll();
	// for (Product p : products) {
	// System.out.println(p.getProductName());
	// }
	// }
}
