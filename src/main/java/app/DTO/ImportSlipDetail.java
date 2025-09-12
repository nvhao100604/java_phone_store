package app.DTO;

import java.math.BigDecimal;

public class ImportSlipDetail {

	private int importSlipId;
	private int productDetailId;
	private int quantity;
	private BigDecimal importPrice;
	private BigDecimal priceAdjustment;

	// Constructors
	public ImportSlipDetail() {
	}

	public ImportSlipDetail(int importSlipId, int productDetailId, int quantity, BigDecimal importPrice,
			BigDecimal priceAdjustment) {
		this.importSlipId = importSlipId;
		this.productDetailId = productDetailId;
		this.quantity = quantity;
		this.importPrice = importPrice;
		this.priceAdjustment = priceAdjustment;
	}

	// Getters and Setters
	public int getImportSlipId() {
		return importSlipId;
	}

	public void setImportSlipId(int importSlipId) {
		this.importSlipId = importSlipId;
	}

	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getImportPrice() {
		return importPrice;
	}

	public void setImportPrice(BigDecimal importPrice) {
		this.importPrice = importPrice;
	}

	public BigDecimal getPriceAdjustment() {
		return priceAdjustment;
	}

	public void setPriceAdjustment(BigDecimal priceAdjustment) {
		this.priceAdjustment = priceAdjustment;
	}
}
