package app.BUS;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

	public Product getProductByName(String productName) // thêm
	{
		return dao.getProductByName(productName);
	}

	public int getQuantityById(int productId, String status) {
		return dao.getQuantityById(productId, status);
	}

	public int getQuantityByDetailId(int productDetailId, String status) {
		return detailBUS.getQuantityByDetailId(productDetailId, status);
	}

	public int getQuantityByName(String productName, String status) {
		return dao.getQuantityByName(productName, status);
	}

	public List<Product> getAll() {
		return dao.getAll();
	}

	public List<Product> getAllDesc() {
		return dao.getAllDesc();
	}

	public Map<String, Integer> getBestSellingProducts(String status) {
		return dao.getBestSellingProducts(status);
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

	public List<ProductDetail> getProductDetailsById(int productId) {
		return detailBUS.getProductDetailById(productId);
	}

	public int updateProductPriceByDetailId(int productDetailId, BigDecimal newSalePrice) {
		return detailBUS.updateProductPriceByDetailId(productDetailId, newSalePrice);
	}

	public int addProductDetail(int productId, String color, String capacity, BigDecimal priceAdjustment) {
		return detailBUS.addProductDetail(productId, color, capacity, priceAdjustment);
	}

	public int saveProductDetails(Product product) {
		int row = 0;
		for (ProductDetail detail : product.getProductDetails()) {
			int response = detail.getProductDetailId() == 0 ? detailBUS.addProductDetail(
					product.getProductId(),
					detail.getColor(),
					detail.getCapacity(),
					detail.getPriceAdjustment())
					: detailBUS.updateProductDetail(detail);
			row += response > 0 ? 1 : 0;
		}
		return row;
	}

	public boolean importDataFromExcel(String filePath) {
		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheetAt(0);

			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}

				Product newProduct = new Product(
						row.getCell(0).toString(),
						(int) row.getCell(1).getNumericCellValue(),
						new BigDecimal(row.getCell(2).getNumericCellValue()),
						(int) row.getCell(3).getNumericCellValue(),
						row.getCell(4).toString(),
						row.getCell(5).toString(),
						new BigDecimal(row.getCell(6).getNumericCellValue()));

				int id = dao.addProduct(newProduct);
				System.out.println("Đã thêm sản phẩm id = " + id);
			}
			return true;
		} catch (IOException e) {
			System.err.println("Lỗi đọc file Excel: " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.err.println("Lỗi xử lý dữ liệu: " + e.getMessage());
			return false;
		}
	}

	public ProductDetail getProductDetailByDetailId(int detailId) {
		return detailBUS.getProductDetailByDetailId(detailId);
	}

	public Product getProductByDetailId(int detailId) {
		return dao.getProductByDetailId(detailId);
	}

	public int getProductTypeByDetailId(int productDetailId) {
		return detailBUS.getProductTypeByDetailId(productDetailId);
	}

	// public static void main(String[] args) {
	// ProductBUS bus = new ProductBUS();
	// List<Product> products = bus.getAll();
	// for (Product p : products) {
	// System.out.println(p.getProductName());
	// }
	// }
}
