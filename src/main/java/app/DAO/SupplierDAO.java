package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Supplier;
import app.database.DBConnect;

public class SupplierDAO {

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap";

        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getInt("idNCC"),
                        rs.getString("TENNCC"),
                        rs.getString("SDT"),
                        rs.getString("DIACHI"),
                        rs.getInt("TRANGTHAI"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public List<Supplier> getActiveSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE TRANGTHAI = 1";

        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getInt("idNCC"),
                        rs.getString("TENNCC"),
                        rs.getString("SDT"),
                        rs.getString("DIACHI"),
                        rs.getInt("TRANGTHAI"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public Supplier getSupplierById(int supplierId) {
        String sql = "SELECT * FROM nhacungcap WHERE idNCC = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, supplierId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new Supplier(
                            rs.getInt("idNCC"),
                            rs.getString("TENNCC"),
                            rs.getString("SDT"),
                            rs.getString("DIACHI"),
                            rs.getInt("TRANGTHAI")
                        );
                    }
                } 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    public int addSupplier(Supplier supplier) {
        String sql = "INSERT INTO nhacungcap (TENNCC, SDT, DIACHI) VALUES (?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, supplier.getNameSupplier());
            ps.setString(2, supplier.getPhoneNumber());
            ps.setString(3, supplier.getAddress());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating supplier failed, no rows affected.");
            } else {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating supplier failed, no ID obtained.");
                    }
                }
            }
            // return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateSupplier(Supplier supplier) {
        String sql = "UPDATE nhacungcap SET TENNCC = ?, SDT = ?, DIACHI = ?, TRANGTHAI = ? WHERE idNCC = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supplier.getNameSupplier());
            ps.setString(2, supplier.getPhoneNumber());
            ps.setString(3, supplier.getAddress());
            ps.setInt(4, supplier.getStatus());
            ps.setInt(5, supplier.getIdSupplier());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteSupplier(int idSupplier) {
        String sql = "DELETE FROM nhacungcap WHERE idNCC = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSupplier);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int softDeleteSupplier(int idSupplier) {
        String sql = "UPDATE nhacungcap SET TRANGTHAI = 0 WHERE idNCC = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSupplier);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int restoreSupplier(int supplierId) {
        String sql = "UPDATE nhacungcap SET TRANGTHAI = 1 WHERE idNCC = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, supplierId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return -1;
    }

    public List<Supplier> searchSuppliers(String keyword) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE (TENNCC LIKE ? OR SDT LIKE ? OR DIACHI LIKE ?) AND TRANGTHAI = 1";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Supplier supplier = new Supplier(
                            rs.getInt("idNCC"),
                            rs.getString("TENNCC"),
                            rs.getString("SDT"),
                            rs.getString("DIACHI"),
                            rs.getInt("TRANGTHAI"));
                    suppliers.add(supplier);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public List<Supplier> searchSuppliers(String keyword, int status) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE 1 = 1 ";
        if (status >= 0) {
            sql += " AND TRANGTHAI = ? ";
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND TENNCC LIKE ? ";
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
                        Supplier supplier = new Supplier();
                        supplier.setIdSupplier(rs.getInt("idNCC"));
                        supplier.setNameSupplier(rs.getString("TENNCC"));
                        supplier.setPhoneNumber(rs.getString("SDT"));
                        supplier.setAddress(rs.getString("DIACHI"));
                        supplier.setStatus(rs.getInt("TRANGTHAI"));
                        suppliers.add(supplier);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return suppliers;
    }

    public List<Supplier> fillterSuppliers(int status) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE TRANGTHAI = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, status);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Supplier supplier = new Supplier();
                        supplier.setIdSupplier(rs.getInt("idNCC"));
                        supplier.setNameSupplier(rs.getString("TENNCC"));
                        supplier.setPhoneNumber(rs.getString("SDT"));
                        supplier.setAddress(rs.getString("DIACHI"));
                        supplier.setStatus(rs.getInt("TRANGTHAI"));
                        suppliers.add(supplier);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return suppliers;
    }
}
