package app.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class Promotion {

	private int promotionId;
	private String code; // ra kết quả - 4.1
	private String description;
	private boolean isPercent;
	private BigDecimal value;
	private int percent;
	private int quantity;
	private Date startDate;
	private Date expirationDate;
	private int brandId; // ứng 1 - 3.1
	private int categoryId; // ứng 2 - 3.2
	private int status;

	public Promotion() {
		this.promotionId = 0;
		this.code = "";
		this.description = "";
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

	public Promotion(int promotionId, String code, String description, boolean isPercent, int percent, BigDecimal value,
			int quantity,
			Date startDate, Date expirationDate,
			int brandId, int categoryId, int status) {
		this.promotionId = promotionId;
		this.code = code;
		this.description = description;
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

	public Promotion(String code, String description, boolean isPercent, int percent, BigDecimal value, int quantity,
			Date startDate, Date expirationDate,
			int brandId, int categoryId, int status) {
		this.code = code;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPercent() {
		return isPercent;
	}

	public void setIsPercent(boolean isPercent) {
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

	@Override
	public String toString() {
		return this.code + " - " + this.quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Promotion promotion = (Promotion) o;
		return this.promotionId == promotion.promotionId;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(this.promotionId);
	}

}