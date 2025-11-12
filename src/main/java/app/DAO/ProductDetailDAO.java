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
        String sql = "SELECT ct.*, COUNT(i.imei) AS SOLUONG from chitietsanpham ct LEFT JOIN imei i ON ct.idCTSP=i.idCTSP WHERE ct.idSP= ? GROUP BY ct.idCTSP";
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
                        rs.getInt(6)));
            }
            return detail_list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public ProductDetail getProductDetailByDetailId(int productDetailId) {
        String sql = "SELECT ct.*, COUNT(i.imei) AS SOLUONG from chitietsanpham ct LEFT JOIN imei i ON ct.idCTSP=i.idCTSP WHERE ct.idCTSP= ? GROUP BY ct.idCTSP";
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
                        rs.getInt(6));
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

    public int updateProductDetail(ProductDetail detail) {
        String sql = "UPDATE chitietsanpham SET " +
                "idSP = ?, " +
                "MAUSAC = ?, " +
                "DUNGLUONG = ?, " +
                "DIEUCHINHGIA = ? " +
                "WHERE idCTSP = ?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, detail.getProductId());
            st.setString(2, detail.getColor());
            st.setString(3, detail.getCapacity());
            st.setBigDecimal(4, detail.getPriceAdjustment());
            st.setInt(5, detail.getProductDetailId());
            return st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateProductPriceByDetailId(int productDetailId, BigDecimal newSalePrice) {
        String sql = "UPDATE sanpham s SET s.GIABAN = ?" +
                "\nWHERE s.idSP = (" +
                "\nSELECT s.idSP FROM sanpham s JOIN chitietsanpham ct ON s.idSP=ct.idSP " +
                "\nWHERE ct.idCTSP = ? GROUP BY s.idSP)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setBigDecimal(1, newSalePrice);
            st.setInt(2, productDetailId);
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // public boolean checkProductDetailId(ProductDetail detail) {
    // String sql = "SELECT * FROM `chitietsanpham` WHERE CTSP= ?";
    // try (Connection connection = DBConnect.getConnection();
    // PreparedStatement st = connection.prepareStatement(sql)) {
    // st.setInt(1, detail.getProductDetailId());

    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // }
}
