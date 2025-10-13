package app.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.DTO.ProductDetail;
import app.database.DBConnect;

public class ProductDetailDAO {
    public List<ProductDetail> getProductDetailById(int productId) {
        List<ProductDetail> detail_list = new ArrayList<>();
        String sql = "SELECT * from chitietsanpham WHERE idSP=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, productId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                detail_list.add(new ProductDetail(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getInt(6),
                        rs.getString(7)));
            }
            return detail_list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public ProductDetail getProductDetailByDetailId(int productDetailId) {
        String sql = "SELECT * from chitietsanpham WHERE idCTSP=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, productDetailId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new ProductDetail(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getInt(6),
                        rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addProductDetail(int productId, String color, String capacity, BigDecimal priceAdjustment) {
        String sql = "INSERT INTO chitietsanpham (idSP, MAUSAC, DUNGLUONG, DIEUCHINHGIA) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, productId);
            st.setString(2, color);
            st.setString(3, capacity);
            st.setBigDecimal(4, priceAdjustment);
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
