package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Product;
import app.database.DBConnect;

public class ImportSlipDetailDAO {

    public List<Product> getProductsByImportSlipId(int importSlipId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT sp.idSP, sp.TENSP,(sp.GIANHAP + ctsp.DIEUCHINHGIA) AS GIANHAP, ctsp.idCTSP, ctsp.MAUSAC, ctsp.DUNGLUONG, COUNT(i.imei) AS SOLUONG FROM chitietsanpham ctsp JOIN chitietphieunhap ct ON ctsp.idCTSP = ct.idCTSP JOIN sanpham sp ON ctsp.idSP=sp.idSP LEFT JOIN imei i ON i.idCTSP=ctsp.idCTSP  WHERE ct.idPN = ? GROUP BY ctsp.idCTSP";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, importSlipId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBigDecimal(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public int AddImportSlipDetail(app.DTO.ImportSlipDetail importSlipDetail) {
        String sql = "INSERT INTO chitietphieunhap (idPN, idCTSP, SOLUONG, GIANHAP, DIEUCHINHGIA) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnect.getConnection();
                PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, importSlipDetail.getImportSlipId());
            st.setInt(2, importSlipDetail.getProductDetailId());
            st.setInt(3, importSlipDetail.getQuantity());
            st.setBigDecimal(4, importSlipDetail.getImportPrice());
            st.setBigDecimal(5, importSlipDetail.getPriceAdjustment());
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
