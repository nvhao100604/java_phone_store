package app.DAO;

import java.math.BigDecimal;
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
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.TRANGTHAI=1 ORDER BY sp.idSP ASC";
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

	public List<Product> getAllDesc() {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.TRANGTHAI=1 ORDER BY sp.idSP DESC";
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
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.idSP= ? AND sp.TRANGTHAI=1";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, productId);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int addProduct(Product product) {
		String sql = "INSERT INTO sanpham (TENSP, HANG, GIANHAP, idDM, IMG, MOTA, GIABAN) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, product.getProductName());
			stmt.setInt(2, product.getBrandId());
			stmt.setBigDecimal(3, product.getImportPrice());
			stmt.setInt(4, product.getCategoryId());
			stmt.setString(5, product.getImageUrl());
			stmt.setString(6, product.getDescription());
			stmt.setBigDecimal(7, product.getSalePrice());
			stmt.executeUpdate();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					int generatedId = rs.getInt(1);
					System.out.println("Generated ID: " + generatedId);
					return generatedId;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateProduct(Product product) {
		if (product.getProductId() == 0) {
			return 0;
		}

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
			e.printStackTrace();
		}
		return 0;
	}

	public int softDeleteProductById(int productId) {
		String sql = "UPDATE sanpham SET TRANGTHAI=0 WHERE idSP= ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, productId);
			int rowsAffect = st.executeUpdate();
			return rowsAffect;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int restoreProduct(int productId) {
		String sql = "UPDATE sanpham SET TRANGTHAI=1 WHERE idSP= ?";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, productId);
			int rowsAffect = st.executeUpdate();
			return rowsAffect;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<Product> searchProductsByName(String keyword) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP, sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.TENSP LIKE ? AND sp.TRANGTHAI=1 ORDER BY sp.idSP ASC";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "%" + keyword + "%");
			ResultSet rs = st.executeQuery();
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

	public List<Product> filterProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP, sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.GIABAN BETWEEN ? AND ? AND sp.TRANGTHAI=1 ORDER BY sp.idSP ASC";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setBigDecimal(1, minPrice);
			st.setBigDecimal(2, maxPrice);
			ResultSet rs = st.executeQuery();
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

	public List<Product> filterProductsByCategory(int categoryId) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP, sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.idDM= ? AND sp.TRANGTHAI=1 ORDER BY sp.idSP ASC";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, categoryId);
			ResultSet rs = st.executeQuery();
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

	public List<Product> filterProductsByBrand(int brandId) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP, sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.HANG= ? AND sp.TRANGTHAI=1 ORDER BY sp.idSP ASC";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, brandId);
			ResultSet rs = st.executeQuery();
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

	public List<Product> filterProductsByBrandName(String brandName) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP, sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE h.TENHANG LIKE ? AND sp.TRANGTHAI=1 ORDER BY sp.idSP ASC";
		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "%" + brandName + "%");
			ResultSet rs = st.executeQuery();
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

	public List<Product> sortProductsByPrice(boolean ascending) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM WHERE sp.TRANGTHAI=1 ORDER BY sp.GIABAN "
				+ (ascending ? "ASC" : "DESC");
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

	public List<Product> filterProducts(
			String keyword,
			BigDecimal minPrice,
			BigDecimal maxPrice,
			int categoryId,
			String brandName,
			int sortByPriceAscending) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT sp.idSP, sp.TENSP, sp.HANG, h.TENHANG, sp.GIANHAP,sp.idDM, d.LOAISP, sp.IMG, sp.MOTA, sp.GIABAN, sp.TRANGTHAI from sanpham sp join hang h ON sp.HANG=h.idHANG join danhmuc d on sp.idDM=d.idDM";
		int index = 0;
		String conditionSql = " WHERE ";
		String orderSql = " sp.TRANGTHAI=1 ORDER BY sp.GIABAN " + (sortByPriceAscending == 1 ? "ASC" : "DESC");

		if (keyword != null) {
			index++;
			conditionSql += " sp.TENSP LIKE '%" + keyword + "%' ";
		}

		if (minPrice != null && maxPrice != null) {
			conditionSql += (index > 0 ? " AND " : "") + " sp.GIABAN BETWEEN " + minPrice + " AND " + maxPrice;
			index++;
		}

		if (categoryId > 0) {
			conditionSql += (index > 0 ? " AND " : "") + " sp.idDM= " + categoryId;
			index++;
		}

		if (brandName != null) {
			conditionSql += (index > 0 ? " AND " : "") + " h.TENHANG LIKE '%" + brandName + "%' ";
			index++;
		}

		orderSql = index > 0 ? " AND " + orderSql : orderSql;

		System.out.println("SQL check: " + sql + conditionSql + orderSql);

		try (Connection con = DBConnect.getConnection();
				PreparedStatement st = con.prepareStatement(sql + conditionSql + orderSql)) {
			ResultSet rs = st.executeQuery();
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
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	// public static void main(String[] args) {
	// ProductDAO dao = new ProductDAO();
	// List<Product> list = dao.getAll();
	// for (Product product : list) {
	// System.out.println("Product check: " + product.getProductName());
	// }
	// }
}
