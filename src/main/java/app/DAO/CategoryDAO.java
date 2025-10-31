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
        List<Category> category_list = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc WHERE TRANGTHAI = 1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                category_list.add(new Category(
                    rs.getInt(1),
                    rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category_list;
    }

    public String[] getToStringCategories() {
        String sql = "SELECT * FROM danhmuc";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            List<String> category_list = new ArrayList<>();
            while (rs.next()) {
                category_list.add(
                        new Category(
                                rs.getInt(1),
                                rs.getString(2)).toString());
            }
            return category_list.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[] { "(Không có dữ liệu)" };
        }
    }
}
