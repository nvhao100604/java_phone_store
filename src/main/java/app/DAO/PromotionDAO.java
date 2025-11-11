package app.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Promotion;
import app.database.DBConnect;

public class PromotionDAO {

    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setIsPercent(rs.getBoolean("IS_PERCENT"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setPercent(rs.getInt("PHANTRAM"));
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

    public Promotion getPromotionById(int promotionId) {
        String sql = "SELECT * FROM khuyenmai WHERE MAKHUYENMAI = ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setDescription(rs.getString("MOTA"));
                promotion.setIsPercent(rs.getBoolean("IS_PERCENT"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setPercent(rs.getInt("PHANTRAM"));
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
        String sql = "SELECT * FROM khuyenmai WHERE SOLUONG > 0 AND NGAYAPDUNG <= ? AND HANSUDUNG >= ? "
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
                promotion.setIsPercent(rs.getBoolean("IS_PERCENT"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setPercent(rs.getInt("PHANTRAM"));
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
        String sql = "INSERT INTO khuyenmai (CODE, MOTA, IS_PERCENT, GIATRI, PHANTRAM, SOLUONG, NGAYAPDUNG, HANSUDUNG, HANG, DANHMUC, TRANGTHAI) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, promotion.getCode());
            st.setString(2, promotion.getDescription());
            st.setBoolean(3, promotion.isPercent());

            if (promotion.isPercent()) {
                st.setBigDecimal(4, new java.math.BigDecimal(0));
                st.setInt(5, promotion.getPercent());
            } else {
                st.setBigDecimal(4, promotion.getValue());
                st.setInt(5, 0);
            }

            st.setInt(6, promotion.getQuantity());
            st.setDate(7, new Date(promotion.getStartDate().getTime()));
            st.setDate(8, new Date(promotion.getExpirationDate().getTime()));

            if (promotion.getBrandId() != 0) {
                st.setInt(9, promotion.getBrandId());
            } else {
                st.setNull(9, java.sql.Types.INTEGER);
            }

            if (promotion.getCategoryId() != 0) {
                st.setInt(10, promotion.getCategoryId());
            } else {
                st.setNull(10, java.sql.Types.INTEGER);
            }

            st.setInt(11, promotion.getStatus());

            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updatePromotion(Promotion promotion) {
        String sql = "UPDATE khuyenmai SET CODE = ?, MOTA = ?, IS_PERCENT = ?, GIATRI = ?, PHANTRAM = ?, SOLUONG = ?, "
                + "NGAYAPDUNG = ?, HANSUDUNG = ?, HANG = ?, DANHMUC = ?, TRANGTHAI = ? WHERE MAKHUYENMAI = ?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, promotion.getCode());
            st.setString(2, promotion.getDescription());
            st.setBoolean(3, promotion.isPercent());

            if (promotion.isPercent()) {
                st.setBigDecimal(4, new java.math.BigDecimal(0));
                st.setInt(5, promotion.getPercent());
            } else {
                st.setBigDecimal(4, promotion.getValue());
                st.setInt(5, 0);
            }

            st.setInt(6, promotion.getQuantity());
            st.setDate(7, new Date(promotion.getStartDate().getTime()));
            st.setDate(8, new Date(promotion.getExpirationDate().getTime()));

            if (promotion.getBrandId() != 0) {
                st.setInt(9, promotion.getBrandId());
            } else {
                st.setNull(9, java.sql.Types.INTEGER);
            }

            if (promotion.getCategoryId() != 0) {
                st.setInt(10, promotion.getCategoryId());
            } else {
                st.setNull(10, java.sql.Types.INTEGER);
            }

            st.setInt(11, promotion.getStatus());
            st.setInt(12, promotion.getPromotionId());

            return st.executeUpdate();
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

    public List<Promotion> searchPromotions(String keyword) {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai WHERE CODE LIKE ?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            st.setString(1, likeKeyword);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Promotion promotion = new Promotion();
                    promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                    promotion.setCode(rs.getString("CODE"));
                    promotion.setIsPercent(rs.getBoolean("IS_PERCENT"));
                    promotion.setValue(rs.getBigDecimal("GIATRI"));
                    promotion.setPercent(rs.getInt("PHANTRAM"));
                    promotion.setQuantity(rs.getInt("SOLUONG"));
                    promotion.setStartDate(rs.getDate("NGAYAPDUNG"));
                    promotion.setExpirationDate(rs.getDate("HANSUDUNG"));
                    promotion.setBrandId(rs.getInt("HANG"));
                    promotion.setCategoryId(rs.getInt("DANHMUC"));
                    promotion.setStatus(rs.getInt("TRANGTHAI"));
                    promotions.add(promotion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public List<Promotion> filterPromotions(int brandId, int categoryId, int status) {
        List<Promotion> promotions = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM khuyenmai WHERE 1=1");

        if (brandId != 0) {
            sqlBuilder.append(" AND HANG = ").append(brandId);
        }
        if (categoryId != 0) {
            sqlBuilder.append(" AND DANHMUC = ").append(categoryId);
        }
        if (status != -1) {
            sqlBuilder.append(" AND TRANGTHAI = ").append(status);
        }

        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sqlBuilder.toString());
                ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setIsPercent(rs.getBoolean("IS_PERCENT"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setPercent(rs.getInt("PHANTRAM"));
                promotion.setQuantity(rs.getInt("SOLUONG"));
                promotion.setStartDate(rs.getDate("NGAYAPDUNG"));
                promotion.setExpirationDate(rs.getDate("HANSUDUNG"));
                promotion.setBrandId(rs.getInt("HANG"));
                promotion.setCategoryId(rs.getInt("DANHMUC"));
                promotion.setStatus(rs.getInt("TRANGTHAI"));
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public Promotion getromotionByDate(Date ngaylapphieu) {
        String sql = "SELECT * FROM khuyenmai WHERE NGAYAPDUNG <= ? AND HANSUDUNG >= ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, ngaylapphieu);
            ps.setDate(2, ngaylapphieu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt("MAKHUYENMAI"));
                promotion.setCode(rs.getString("CODE"));
                promotion.setIsPercent(rs.getBoolean("IS_PERCENT"));
                promotion.setValue(rs.getBigDecimal("GIATRI"));
                promotion.setPercent(rs.getInt("PHANTRAM"));
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

    public int setPromotionByStatus1(Date currentDate) {
        String sql = "UPDATE khuyenmai SET TRANGTHAI = 1 WHERE SOLUONG > 0 AND NGAYAPDUNG <= ? "
                + "AND HANSUDUNG >= ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, new Date(currentDate.getTime()));
            ps.setDate(2, new Date(currentDate.getTime()));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int setPromotionByStatus0(Date currentDate) {
        String sql = "UPDATE khuyenmai SET TRANGTHAI = 0 WHERE NGAYAPDUNG > ? "
                + "AND HANSUDUNG < ?";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, new Date(currentDate.getTime()));
            ps.setDate(2, new Date(currentDate.getTime()));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int reducePromotionQuantity(int promotionId) {
        String sql = "UPDATE khuyenmai SET SOLUONG = SOLUONG - 1 WHERE MAKHUYENMAI = ? AND SOLUONG > 0";
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