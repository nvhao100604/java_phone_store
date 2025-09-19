package app.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Promotion;
import app.database.DBConnect;

public class PromotionDAO {

    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai WHERE TRANGTHAI = 1";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setQuantity(rs.getInt("SOLUONG"));
                promotion.setStartDate(rs.getDate("NGAYAPDUNG"));
                promotion.setExpirationDate(rs.getDate("HANSUDUNG"));
                promotion.setBrandId(rs.getInt("HANG"));
                promotion.setCategoryId(rs.getInt("DANHMUC"));
                promotion.setStatus(rs.getInt("TRANGTHAI"));
                promotions.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public List<Promotion> getActivePromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai WHERE TRANGTHAI = 1 AND HANSUDUNG >= CURDATE()";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setQuantity(rs.getInt("SOLUONG"));
                promotion.setStartDate(rs.getDate("NGAYAPDUNG"));
                promotion.setExpirationDate(rs.getDate("HANSUDUNG"));
                promotion.setBrandId(rs.getInt("HANG"));
                promotion.setCategoryId(rs.getInt("DANHMUC"));
                promotion.setStatus(rs.getInt("TRANGTHAI"));
                promotions.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public List<Promotion> getValidPromotions(int brandId, int categoryId, Date currentDate) {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai WHERE TRANGTHAI = 1 AND SOLUONG > 0 AND NGAYAPDUNG <= ? AND HANSUDUNG >= ? "
                + "AND (HANG = ? OR DANHMUC = ? OR (HANG IS NULL AND DANHMUC IS NULL))";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, new Date(currentDate.getTime()));
            ps.setDate(2, new Date(currentDate.getTime()));
            ps.setInt(3, brandId);
            ps.setInt(4, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setQuantity(rs.getInt("SOLUONG"));
                promotion.setStartDate(rs.getDate("NGAYAPDUNG"));
                promotion.setExpirationDate(rs.getDate("HANSUDUNG"));
                promotion.setBrandId(rs.getInt("HANG"));
                promotion.setCategoryId(rs.getInt("DANHMUC"));
                promotion.setStatus(rs.getInt("TRANGTHAI"));
                promotions.add(promotion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public Promotion getPromotionByCode(String code) {
        String sql = "SELECT * FROM khuyenmai WHERE CODE = ? AND TRANGTHAI = 1";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setQuantity(rs.getInt("SOLUONG"));
                promotion.setStartDate(rs.getDate("NGAYAPDUNG"));
                promotion.setExpirationDate(rs.getDate("HANSUDUNG"));
                promotion.setBrandId(rs.getInt("HANG"));
                promotion.setCategoryId(rs.getInt("DANHMUC"));
                promotion.setStatus(rs.getInt("TRANGTHAI"));
                return promotion;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addPromotion(Promotion promotion) {
        String sql = "INSERT INTO khuyenmai (CODE, GIATRI, SOLUONG, NGAYAPDUNG, HANSUDUNG, HANG, DANHMUC, TRANGTHAI) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, promotion.getCode());
            ps.setBigDecimal(2, promotion.getValue());
            ps.setInt(3, promotion.getQuantity());
            ps.setDate(4, new Date(promotion.getStartDate().getTime()));
            ps.setDate(5, new Date(promotion.getExpirationDate().getTime()));
            if (promotion.getBrandId() != 0) {
                ps.setInt(6, promotion.getBrandId());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            if (promotion.getCategoryId() != 0) {
                ps.setInt(7, promotion.getCategoryId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            ps.setInt(8, promotion.getStatus());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updatePromotionQuantity(int promotionId, int newQuantity) {
        String sql = "UPDATE khuyenmai SET SOLUONG = ? WHERE MAKHUYENMAI = ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newQuantity);
            ps.setInt(2, promotionId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deactivatePromotion(int promotionId) {
        String sql = "UPDATE khuyenmai SET TRANGTHAI = 0 WHERE MAKHUYENMAI = ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, promotionId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int activatePromotion(int promotionId) {
        String sql = "UPDATE khuyenmai SET TRANGTHAI = 1 WHERE MAKHUYENMAI = ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, promotionId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deletePromotion(int promotionId) {
        String sql = "DELETE FROM khuyenmai WHERE MAKHUYENMAI = ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, promotionId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
