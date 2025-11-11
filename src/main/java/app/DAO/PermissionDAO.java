package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Permission;
import app.database.DBConnect;

public class PermissionDAO {
    public List<Permission> getAll() {
        List<Permission> list = new ArrayList<>();
        String sql = "SELECT * FROM quyen";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Permission permission = new Permission (
                        rs.getInt("idQUYEN"),
                        rs.getString("TENQUYEN"),
                        rs.getBigDecimal("LUONG"),
                        rs.getInt("TRANGTHAI")
                    );
                    list.add(permission);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public List<Permission> getRole() {
        List<Permission> list = new ArrayList<>();
        String sql = "SELECT idQUYEN, TENQUYEN FROM quyen WHERE TRANGTHAI = 1 AND idQUYEN <> 0";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Permission permission = new Permission (
                        rs.getInt("idQUYEN"),
                        rs.getString("TENQUYEN")
                    );
                    list.add(permission);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return list;
    }

    public boolean checkPermission(int roleId, int functionId) {
        String sql = "SELECT 1 FROM phanquyen WHERE idQUYEN = ? AND idCN = ?";
        try(Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, roleId);
                stmt.setInt(2, functionId);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

    public int addPermission(int roleId, int functionId) {
        if (checkPermission(roleId, functionId)) {
            return 0; // đã có rồi, không thêm
        }
        String sql = "INSERT INTO phanquyen(idQUYEN, idCN) VALUES (?, ?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.setInt(2, functionId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removePermission(int roleId, int functionId) {
        String sql = "DELETE FROM phanquyen WHERE idQUYEN = ? AND idCN = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.setInt(2, functionId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }   

    public int addNewRole(Permission permission) {
        String sql = "INSERT INTO quyen(TENQUYEN, LUONG) VALUES (?, ?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, permission.getPermissionName());
            stmt.setBigDecimal(2, permission.getSalary());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Generated ID: " + generatedId);
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateRole(Permission permission) {
        String sql = "UPDATE quyen SET TENQUYEN = ?, LUONG = ?, TRANGTHAI = ? WHERE idQUYEN = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, permission.getPermissionName());
            stmt.setBigDecimal(2,  permission.getSalary());
            stmt.setInt(3, permission.getStatus());
            stmt.setInt(4, permission.getPermissionId());
            int result = stmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}