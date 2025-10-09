package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Category;
import app.database.DBConnect;

public class CategoryDAO {

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM danhmuc";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            List<Category> category_list = new ArrayList<>();
            while (rs.next()) {
                category_list.add(
                        new Category(
                                rs.getInt(1),
                                rs.getString(2)));
            }
            return category_list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
