package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Employee;
import app.database.DBConnect;  

public class EmployeeDAO {
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, tk.SDT, tk.EMAIL, NV.NGAYSINH, tk.USERNAME, nv.DIACHI, q.LUONG, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getBigDecimal(9),
                        rs.getInt(10)
                    );
                    list.add(employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, tk.SDT, tk.EMAIL, NV.NGAYSINH, tk.USERNAME, nv.DIACHI, q.LUONG, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN WHERE nv.idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                st.setInt(1, employeeId);
                return new Employee(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getBigDecimal(9),
                    rs.getInt(10)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public int addEmployee(Employee employee) {
        String sql = "INSERT INTO nhanvien (GIOITINH, NGAYSINH, DIACHI, TINHTRANG) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                stmt.setString(1, employee.getGender());
                stmt.setDate(2, employee.getDateOfBirth() != null ? new java.sql.Date(employee.getDateOfBirth().getTime()) : null);
                stmt.setString(3, employee.getAddress());
                stmt.setInt(4, employee.getStatus());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("Inserted employee ID: " + generatedId);
                        return generatedId;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int updateEmployee(Employee employee) {
        String sql = "UPDATE nhanvien SET GIOITINH = ?, NGAYSINH = ?, DIACHI = ?, TINHTRANG = ? WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, employee.getGender());
                stmt.setDate(2, employee.getDateOfBirth() != null ? new java.sql.Date(employee.getDateOfBirth().getTime()) : null);
                stmt.setString(3, employee.getAddress());
                stmt.setInt(4, employee.getStatus());
                stmt.setInt(5, employee.getEmployeeId());
                return stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int deleteEmployee(int employeeId) {
        String sql = "DELETE FROM nhanvien WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, employeeId);
                int rowsAffected = st.executeUpdate();
                System.out.println("Deleted rows: " + rowsAffected);
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public List<Employee> searchEmployees(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, tk.SDT, tk.EMAIL, NV.NGAYSINH, tk.USERNAME, nv.DIACHI, q.LUONG, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN WHERE (tk.HOTEN LIKE ? OR tk.SDT LIKE ? OR tk.EMAIL LIKE ? OR tk.USERNAME LIKE ? OR nv.DIACHI LIKE ?) AND nv.TINHTRANG = 1";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                String likeKeyword = "%" + keyword + "%";
                for (int i = 1; i <= 5; i++) {
                    st.setString(i, likeKeyword);
                }
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Employee employee = new Employee();
                        employee.setEmployeeId(rs.getInt(1));
                        employee.setFullName(rs.getString(2));
                        employee.setGender(rs.getString(3));
                        employee.setPhoneNumber(rs.getString(4));
                        employee.setEmail(rs.getString(5));
                        employee.setDateOfBirth(rs.getDate(6));
                        employee.setUserName(rs.getString(7));
                        employee.setAddress(rs.getString(8));
                        employee.setSalary(rs.getBigDecimal(9));
                        employee.setStatus(rs.getInt(10));
                        employees.add(employee);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return employees;
    }

    public List<Employee> fillterEmployeesByStatus(int status) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, tk.SDT, tk.EMAIL, NV.NGAYSINH, tk.USERNAME, nv.DIACHI, q.LUONG, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN WHERE nv.TINHTRANG = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, status);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Employee employee = new Employee();
                        employee.setEmployeeId(rs.getInt(1));
                        employee.setFullName(rs.getString(2));
                        employee.setGender(rs.getString(3));
                        employee.setPhoneNumber(rs.getString(4));
                        employee.setEmail(rs.getString(5));
                        employee.setDateOfBirth(rs.getDate(6));
                        employee.setUserName(rs.getString(7));
                        employee.setAddress(rs.getString(8));
                        employee.setSalary(rs.getBigDecimal(9));
                        employee.setStatus(rs.getInt(10));
                        employees.add(employee);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
        }
        return employees;
    }
}