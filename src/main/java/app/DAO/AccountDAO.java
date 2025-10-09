package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Account;
import app.database.DBConnect;

public class AccountDAO {

    public Account Login(String username, String password) {
        String sql = "SELECT * FROM taikhoan tk WHERE tk.USERNAME= ? AND tk.PASSWORD = ? ";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            // System.out.println("logged in");
            if (rs.next()) {
                return new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Loi may");
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAll() { // xuất hết dữ liệu tài khoản hiện có trong database
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8));
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Account getAccountById(int accountId) { // tìm kiếm tài khoản
        String sql = "SELECT * FROM taikhoan WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, accountId);
            ResultSet rs = st.executeQuery();
            return new Account(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getInt(8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addAccount(Account account) { // thêm 1 tài khoản mới
        String sql = "INSERT INTO taikhoan (USERNAME, PASSWORD, SDT, EMAIL, HOTEN, idQUYEN) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getPhoneNumber());
            stmt.setString(4, account.getEmail());
            stmt.setString(5, account.getFullName());
            stmt.setInt(6, account.getRoleId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Inserted account ID: " + generatedId);
                    return generatedId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateAccount(Account account) { // chỉnh sửa tài khoản hiện tại
        String sql = "UPDATE taikhoan SET USERNAME = ?, PASSWORD = ?, SDT = ?, EMAIL = ?, HOTEN = ?, idQUYEN = ?, TRANGTHAI = ? WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getPhoneNumber());
            stmt.setString(4, account.getEmail());
            stmt.setString(5, account.getFullName());
            stmt.setInt(6, account.getRoleId());
            stmt.setInt(7, account.getStatus());
            stmt.setInt(8, account.getAccountId());
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAccount(int accountId) { // xóa tài khoản hiện có
        String sql = "DELETE FROM taikhoan WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted rows: " + rowsAffected);
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Account> searchAccounts(String keyword) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan WHERE (USERNAME LIKE ? OR SDT LIKE ? OR EMAIL LIKE ? OR HOTEN LIKE ?) AND TRANGTHAI = 1";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                String likeKeyword = "%" + keyword + "%";
                for (int i = 1; i <= 4; i++) {
                    st.setString(i, likeKeyword);
                }
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Account account = new Account();
                        account.setAccountId(rs.getInt("idTK"));
                        account.setUsername(rs.getString("USERNAME"));
                        account.setPassword(rs.getString("PASSWORD"));
                        account.setPhoneNumber(rs.getString("SDT"));
                        account.setEmail(rs.getString("EMAIL"));
                        account.setFullName(rs.getString("HOTEN"));
                        account.setStatus(rs.getInt("TRANGTHAI"));
                        account.setRoleId(rs.getInt("idQUYEN"));
                        accounts.add(account);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return accounts;
    }

    public List<Account> fillterAccounts(int status) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan WHERE TRANGTHAI = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, status);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Account account = new Account();
                        account.setAccountId(rs.getInt("idTK"));
                        account.setUsername(rs.getString("USERNAME"));
                        account.setPassword(rs.getString("PASSWORD"));
                        account.setPhoneNumber(rs.getString("SDT"));
                        account.setEmail(rs.getString("EMAIL"));
                        account.setFullName(rs.getString("HOTEN"));
                        account.setRoleId(rs.getInt("idQUYEN"));
                        account.setStatus(rs.getInt("TRANGTHAI"));
                        accounts.add(account);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return accounts;
    }
  
    public static void main(String[] args) {
        String username = "admin";
        String password = "12345";

        AccountDAO dao = new AccountDAO();
        Account response = dao.Login(username, password);
        System.out.println("check name: " + response.getFullName());
    }
}