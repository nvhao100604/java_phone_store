package app.BUS;

import java.math.BigDecimal;

import app.DAO.PromotionUsageDAO;
import app.DTO.PromotionUsage;

public class PromotionUsageBUS {
    private final PromotionUsageDAO dao;

    public PromotionUsageBUS() {
        dao = new PromotionUsageDAO();
    }

    public int addPromotionUsage(PromotionUsage promotionUsage) {
        return dao.addPromotionUsage(promotionUsage);
    }

    public BigDecimal getPromotionValueByOrderId(int orderId) {
        return dao.getPromotionValueByOrderId(orderId);
    }
}
