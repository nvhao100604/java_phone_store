package app.BUS;

import java.sql.Date;
import java.util.List;

import app.DAO.PromotionDAO;
import app.DTO.Promotion;

public class PromotionBUS {

    private final PromotionDAO dao;

    public PromotionBUS() {
        this.dao = new PromotionDAO();
    }

    public List<Promotion> getAllPromotions() {
        return dao.getAllPromotions();
    }

    public List<Promotion> getActivePromotions() {
        return dao.getActivePromotions();
    }

    public List<Promotion> getValidPromotions(int brandId, int categoryId, Date currentDate) {
        return dao.getValidPromotions(brandId, categoryId, currentDate);
    }

    public int addPromotion(Promotion promotion) {
        return dao.addPromotion(promotion);
    }

    public int updatePromotionQuantity(int quantity, int promotionId) {
        return dao.updatePromotionQuantity(promotionId, quantity);
    }

    public int deactivatePromotion(int promotionId) {
        return dao.deactivatePromotion(promotionId);
    }

    public int activatePromotion(int promotionId) {
        return dao.activatePromotion(promotionId);
    }

    public int deletePromotion(int promotionId) {
        return dao.deletePromotion(promotionId);
    }

    public Promotion getPromotionByCode(String code) {
        return dao.getPromotionByCode(code);
    }
}
