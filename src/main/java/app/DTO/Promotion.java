package app.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class Promotion {

	private int promotionId;
	private String code;
	private BigDecimal value;
	private int quantity;
	private Date startDate;
	private Date expirationDate;
	private int status;

	public Promotion() {
	}

	public Promotion(int promotionId, String code, BigDecimal value, int quantity, Date startDate, Date expirationDate,
			int status) {
		this.promotionId = promotionId;
		this.code = code;
		this.value = value;
		this.quantity = quantity;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}