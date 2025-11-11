package app.DAO;

import app.DTO.PromotionUsage;
import app.database.DBConnect;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PromotionUsageDAO {

    public int addPromotionUsage(PromotionUsage usage) {
        String sql = "INSERT INTO chitietkhuyenmai (idHD,MAKHUYENMAI,GIATRI) VALUES (?, ?, ?)";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, usage.getOrderId());
            st.setInt(2, usage.getPromotionId());
            st.setBigDecimal(3, usage.getValue());
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public BigDecimal getPromotionValueByOrderId(int orderId) {
        String sql = "SELECT SUM(GIATRI) FROM khuyenmai GROUP BY " + "orderId";
        try (Connection con = DBConnect.getConnection()) {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}