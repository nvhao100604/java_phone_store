package app.DAO;

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

}
