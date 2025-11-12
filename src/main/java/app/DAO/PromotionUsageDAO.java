package app.DAO;
import app.DTO.PromotionUsage;
import app.database.DBConnect;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
    public BigDecimal getPromotionValueByOrderId(int orderId)
    {
    	String sql = "SELECT SUM(GIATRI) FROM chitietkhuyenmai GROUP BY "+"orderId";
    	try (Connection con = DBConnect.getConnection()) {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
            	return rs.getBigDecimal(3);
            }
    	}
    	catch (Exception e) {
            e.printStackTrace();
        }
    	return BigDecimal.ZERO;
    }
    public List<PromotionUsage> getUsageByOrderId(int orderId)
    {
    	List<PromotionUsage> promotionUsages = new ArrayList<>();
    	String sql = "SELECT * FROM chitietkhuyenmai WHERE idHD = ?";
    	try (Connection con = DBConnect.getConnection()) {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,orderId);             
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
            	PromotionUsage promotionUsage = new PromotionUsage();
            	promotionUsage.setOrderId(rs.getInt(1));
            	promotionUsage.setPromotionId(rs.getInt(2));
            	promotionUsage.setValue(rs.getBigDecimal(3));
            	promotionUsages.add(promotionUsage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotionUsages;
    }
}

