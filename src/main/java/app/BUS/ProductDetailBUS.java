package app.BUS;

import java.math.BigDecimal;
import java.util.List;

import app.DAO.ProductDetailDAO;
import app.DTO.ProductDetail;

public class ProductDetailBUS {
	private ProductDetailDAO dao;

	public ProductDetailBUS() {
		dao = new ProductDetailDAO();
	}

	public List<ProductDetail> getProductDetailById(int productId) {
		return dao.getProductDetailById(productId);
	}

	public ProductDetail getProductDetailByDetailId(int productDetailId) {
		return dao.getProductDetailByDetailId(productDetailId);
	}

	public int getQuantityByDetailId(int productDetailId, String status) {
		return dao.getQuantityByDetailId(productDetailId, status);
	}

	public int addProductDetail(int productId, String color, String capacity, BigDecimal priceAdjustment) {
		return dao.addProductDetail(productId, color, capacity, priceAdjustment);
	}

	public int updateProductDetail(ProductDetail detail) {
		return dao.updateProductDetail(detail);
	}

	public int updateProductPriceByDetailId(int productDetailId, BigDecimal newSalePrice) {
		return dao.updateProductPriceByDetailId(productDetailId, newSalePrice);
	}

	public static void main(String[] args) {
		// ProductDetailBUS bus = new ProductDetailBUS();
		// List<ProductDetail> list = bus.getProductDetailById(1);
		// for (ProductDetail detail : list) {
		// System.out.println("Product Detail check: " + detail.getColor() + " - " +
		// detail.getCapacity());
		// }
	}
}
