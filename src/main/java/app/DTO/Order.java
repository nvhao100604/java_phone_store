package app.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private int orderId;
	private int accountId;
	private int customerId;
	private BigDecimal totalAmount;
	private Date purchaseDate;
	private String address;
	private Integer promotionCode;
	private int status;
	private int paymentId;
	private List<OrderDetail> orderDetails;

	public Order() {
		this.orderDetails = new ArrayList<>();
	}

	public Order(int orderId, int accountId, int customerId, BigDecimal totalAmount, Date purchaseDate, String address,
			Integer promotionCode, int status, int paymentId) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.customerId = customerId;
		this.totalAmount = totalAmount;
		this.purchaseDate = purchaseDate;
		this.address = address;
		this.promotionCode = promotionCode;
		this.status = status;
		this.paymentId = paymentId;
		this.orderDetails = new ArrayList<>();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(Integer promotionCode) {
		this.promotionCode = promotionCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}