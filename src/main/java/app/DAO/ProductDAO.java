package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Product;
import app.database.DBConnect;

public class ProductDAO {

	public List<Product> getAll() {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				Product product = new Product(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getBigDecimal(5),
						rs.getInt(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getBigDecimal(10),
						rs.getInt(11));
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Product getProductById(int productId) {
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.idSP= ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {
			st.setInt(1, productId);
			return new Product(
					rs.getInt(1),
					rs.getString(2),
					rs.getInt(3),
					rs.getString(4),
					rs.getBigDecimal(5),
					rs.getInt(6),
					rs.getString(7),
					rs.getString(8),
					rs.getString(9),
					rs.getBigDecimal(10),
					rs.getInt(11));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public int addProduct(Product product) {
		String sql = "INSERT INTO sanpham (TENSP, HANG, GIANHAP, idDM, IMG, MOTA, GIABAN, TRANGTHAI) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getBrandId());
			stmt.setBigDecimal(3, product.getImportPrice());
			stmt.setInt(4, product.getCategoryId());
			stmt.setString(5, product.getImageUrl());
			stmt.setString(6, product.getDescription());
			stmt.setBigDecimal(7, product.getSalePrice());
			stmt.setInt(8, product.getStatus());
			stmt.executeUpdate();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					int generatedId = rs.getInt(1);
					System.out.println("Generated ID: " + generatedId);
					return generatedId;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	public int updateProduct(Product product) {
		String sql = "UPDATE sanpham SET TENSP = ?, HANG = ?, GIANHAP = ?, idDM = ?, IMG = ?, MOTA = ?,  GIABAN = ?, TRANGTHAI = ? WHERE idSP = ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getBrandId());
			stmt.setBigDecimal(3, product.getImportPrice());
			stmt.setInt(4, product.getCategoryId());
			stmt.setString(5, product.getImageUrl());
			stmt.setString(6, product.getDescription());
			stmt.setBigDecimal(7, product.getSalePrice());
			stmt.setInt(8, product.getStatus());
			stmt.setInt(9, product.getProductId());
			return stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	public int deleteProductById(int productId) {
		String sql = "DELETE from sanpham WHERE idSP= ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, productId);
			int rowsAffect = st.executeUpdate();
			System.out.println("Rows: " + rowsAffect);
			return rowsAffect;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	// public static void main(String[] args) {
	// ProductDAO dao = new ProductDAO();
	// List<Product> list = dao.getAll();
	// for (Product product : list) {
	// System.out.println("Product check: " + product.getProductName());
	// }
	// }
}
