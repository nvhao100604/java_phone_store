package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Brand;
import app.database.DBConnect;

public class BrandDAO {

    public List<Brand> getAllBrands() {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM hang WHERE TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                brands.add(new Brand(
                        rs.getInt(1),
                        rs.getString(2)));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return brands;
    }
}
