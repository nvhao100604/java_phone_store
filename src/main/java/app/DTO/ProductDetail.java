package app.DTO;

import java.math.BigDecimal;

public class ProductDetail {

	private int productDetailId;
	private int productId;
	private String color;
	private String capacity;
	private BigDecimal priceAdjustment;
	private int stock;
	private String imageUrl;

	// Constructors
	public ProductDetail() {
	}

	public ProductDetail(int productDetailId, int productId, String color, String capacity, BigDecimal priceAdjustment,
			int stock, String imageUrl) {
		this.productDetailId = productDetailId;
		this.productId = productId;
		this.color = color;
		this.capacity = capacity;
		this.priceAdjustment = priceAdjustment;
		this.stock = stock;
		this.imageUrl = imageUrl;
	}

	// Getters and Setters
	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getPriceAdjustment() {
		return priceAdjustment;
	}

	public void setPriceAdjustment(BigDecimal priceAdjustment) {
		this.priceAdjustment = priceAdjustment;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
