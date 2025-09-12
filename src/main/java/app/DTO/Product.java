package app.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Product {

	private int productId;
	private String productName;
	private String brand;
	private BigDecimal importPrice;
	private int categoryId;
	private String imageUrl;
	private String description;
	private int status;
	private BigDecimal salePrice;
	private List<ProductDetail> productDetails;

	// Constructors
	public Product() {
		this.productDetails = new ArrayList<>();
	}

	public Product(int productId, String productName, String brand, BigDecimal importPrice, int categoryId,
			String imageUrl, String description, int status, BigDecimal salePrice) {
		this.productId = productId;
		this.productName = productName;
		this.brand = brand;
		this.importPrice = importPrice;
		this.categoryId = categoryId;
		this.imageUrl = imageUrl;
		this.description = description;
		this.status = status;
		this.salePrice = salePrice;
		this.productDetails = new ArrayList<>();
	}

	// Getters and Setters
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getImportPrice() {
		return importPrice;
	}

	public void setImportPrice(BigDecimal importPrice) {
		this.importPrice = importPrice;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	// Getter and Setter for the list of ProductDetails
	public List<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}
}