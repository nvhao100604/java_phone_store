package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.ProductType;
import app.database.DBConnect;

public class ProductTypeDAO {
    public List<ProductType> getAll() {
        List<ProductType> list = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ProductType productType = new ProductType(
                        rs.getInt("idDM"),
                        rs.getString("LOAISP"),
                        rs.getInt("TRANGTHAI")
                    );
                    list.add(productType);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public ProductType getProductTypeById(int productTypeId) {
        String sql = "SELECT * FROM danhmuc WHERE idDM = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, productTypeId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new ProductType(
                            rs.getInt("idDM"),
                            rs.getString("LOAISP"),
                            rs.getInt("TRANGTHAI")
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public int addProductType(ProductType productType) {
        String sql = "INSERT INTO danhmuc (LOAISP) VALUES (?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                stmt.setString(1, productType.getProductTypeName());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("Inserted product type ID: " + generatedId);
                        return generatedId;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int updateProductType(ProductType productType) {
        String sql = "UPDATE danhmuc SET LOAISP = ? WHERE idDM = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, productType.getProductTypeName());
                stmt.setInt(2, productType.getProductTypeId());
                int result = stmt.executeUpdate();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
    }

    public int deleteProductType(int productTypeId) {
        String sql = "DELETE FROM danhmuc WHERE idDM = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, productTypeId);
                int rowsAffected = stmt.executeUpdate();
                System.out.println("Deleted rows: " + rowsAffected);
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int softDeleteProductType(int productTypeId) {
        String sql = "UPDATE danhmuc SET TRANGTHAI = 0 WHERE idDM = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) { 
                st.setInt(1, productTypeId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int restoreProductType(int productTypeId) {
        String sql = "UPDATE danhmuc SET TRANGTHAI = 1 WHERE idDM = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, productTypeId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return -1;
    }


    public List<ProductType> searchProductTypes(String keyword) {
        List<ProductType> productTypes = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc WHERE (LOAISP LIKE ?) AND TRANGTHAI = 1";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, "%" + keyword + "%");
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProductType productType = new ProductType();
                        productType.setProductTypeId(rs.getInt("idDM"));    
                        productType.setProductTypeName(rs.getString("LOAISP"));
                        productType.setProductTypeStatus(rs.getInt("TRANGTHAI"));
                        productTypes.add(productType);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return productTypes;
    }

    public List<ProductType> searchProductTypes(String keyword, int status) {
        List<ProductType> productTypes = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc WHERE 1 = 1 ";
        if (status >= 0) {
            sql += " AND TRANGTHAI = ? ";
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND LOAISP LIKE ? ";
        }
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                int paramIndex = 1;

                if (status >= 0) {
                    st.setInt(paramIndex++, status);
                }

                if (keyword != null && !keyword.trim().isEmpty()) {
                    String likeKeyword = "%" + keyword + "%";
                    st.setString(paramIndex++, likeKeyword);
                }
                
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProductType productType = new ProductType();
                        productType.setProductTypeId(rs.getInt("idDM"));
                        productType.setProductTypeName(rs.getString("LOAISP"));
                        productType.setProductTypeStatus(rs.getInt("TRANGTHAI"));
                        productTypes.add(productType);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return productTypes;
    }

    public List<ProductType> fillterProductTypes(int status) {
        List<ProductType> productTypes = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc WHERE TRANGTHAI = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, status);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProductType productType = new ProductType();
                        productType.setProductTypeId(rs.getInt("idDM"));    
                        productType.setProductTypeName(rs.getString("LOAISP"));
                        productType.setProductTypeStatus(rs.getInt("TRANGTHAI"));
                        productTypes.add(productType);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return productTypes;
    }
}