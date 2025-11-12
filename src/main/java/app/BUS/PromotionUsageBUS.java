package app.BUS;

import java.math.BigDecimal;
import java.util.List;

import app.DAO.PromotionUsageDAO;
import app.DTO.PromotionUsage;

import java.math.BigDecimal;

import app.DAO.PromotionUsageDAO;
import app.DTO.PromotionUsage;

public class PromotionUsageBUS 
{
	private final PromotionUsageDAO dao;
	public PromotionUsageBUS()
	{
		dao = new PromotionUsageDAO();
	}
	public int addPromotion(PromotionUsage promotionusage) {
        return dao.addPromotionUsage(promotionusage);
    }
	public BigDecimal getPromotionValueByOrderId(int orderId)
    {
		return dao.getPromotionValueByOrderId(orderId);
    }
}
