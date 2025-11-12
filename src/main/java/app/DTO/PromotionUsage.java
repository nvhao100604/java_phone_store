package app.DTO;

import java.math.BigDecimal;

public class PromotionUsage {
    private int orderId;            
    private int promotionId;     
    private BigDecimal value;
    public PromotionUsage()
    {
    	
    }
	public PromotionUsage(int orderId, int promotionId, BigDecimal value) {
		this.orderId = orderId;
		this.promotionId = promotionId;
		this.value = value;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
	@Override
	public String toString() {
		return "PromotionUsage [orderId=" + orderId + ", promotionId=" + promotionId + ", value=" + value + "]";
	}
}
