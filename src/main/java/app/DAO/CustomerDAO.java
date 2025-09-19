package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Customer;
import app.database.DBConnect;

public class CustomerDAO {
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer (
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                    );
                    list.add(customer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }   

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM khachhang WHERE idkh = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, customerId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new Customer(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public int addCustomer(Customer customer) {
        String sql = "INSERT INTO khachhang (hoten, sodt, diachi) VALUES (?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                st.setString(1, customer.getFullName());
                st.setString(2, customer.getPhoneNumber());
                st.setString(3, customer.getAddress());
                st.executeUpdate();
                try(ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("Inserted customer ID: " + generatedId);
                        return generatedId;
                    }
                } 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int updateCustomer(Customer customer) {
        String sql = "UPDATE khachhang SET hoten = ?, sodt = ?, diachi = ? WHERE idkh = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, customer.getFullName());
                st.setString(2, customer.getPhoneNumber());
                st.setString(3, customer.getAddress());
                st.setInt(4, customer.getCustomerId());
                return st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int deleteCustomer(int customerId) {
        String sql = "DELETE FROM khachhang WHERE idkh = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, customerId);
                int rowsAffected = st.executeUpdate();
                System.out.println("Deleted customer ID: " + customerId + ", Rows affected: " + rowsAffected);
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }
}