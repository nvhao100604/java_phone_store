package app.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class Promotion {

	private int promotionId;
	private String code;
	private boolean isPercent;
	private BigDecimal value;
	private int percent;
	private int quantity;
	private Date startDate;
	private Date expirationDate;
	private int brandId;
	private int categoryId;
	private int status;

	public Promotion() {
		this.promotionId = 0;
		this.code = "";
		this.isPercent = false;
		this.value = new BigDecimal(0);
		this.percent = 0;
		this.quantity = 0;
		this.startDate = new Date();
		this.expirationDate = new Date();
		this.brandId = 0;
		this.categoryId = 0;
		this.status = 0;
	}

	public Promotion(int promotionId, String code, boolean isPercent, int percent, BigDecimal value, int quantity,
			Date startDate, Date expirationDate,
			int brandId, int categoryId, int status) {
		this.promotionId = promotionId;
		this.code = code;
		this.isPercent = isPercent;
		this.value = value;
		this.percent = percent;
		this.quantity = quantity;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.status = status;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isPercent() {
		return isPercent;
	}

	public void setPercent(boolean isPercent) {
		this.isPercent = isPercent;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}