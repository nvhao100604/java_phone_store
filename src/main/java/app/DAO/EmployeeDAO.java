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
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, NV.NGAYSINH, tk.SDT, tk.EMAIL, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)
                    );
                    list.add(employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT tk.idTk, nv.GIOITINH, nv.NGAYSINH, nv.DIACHI, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK WHERE nv.idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, employeeId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new Employee(
                            rs.getInt("idTk"),
                            rs.getString("GIOITINH"),
                            rs.getDate("NGAYSINH"),
                            rs.getString("DIACHI"),
                            rs.getInt("TINHTRANG")
                        );
                    }
                } 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    public List<Employee> getEmployeeGenderList() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT DISTINCT GIOITINH FROM nhanvien WHERE GIOITINH IS NOT NULL";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setGender(rs.getString(1));
                    list.add(employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public int addEmployee(Employee employee) {
        String sql = "INSERT INTO nhanvien (idTK, GIOITINH, NGAYSINH, DIACHI) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                stmt.setInt(1, employee.getAccountId());
                stmt.setString(2, employee.getGender());
                stmt.setDate(3, employee.getDateOfBirth() != null ? new java.sql.Date(employee.getDateOfBirth().getTime()) : null);
                stmt.setString(4, employee.getAddress());
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
        String sql = "UPDATE nhanvien SET GIOITINH = ?, NGAYSINH = ?, DIACHI = ? WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, employee.getGender());
                st.setDate(2, (java.sql.Date) employee.getDateOfBirth());
                st.setString(3, employee.getAddress());
                st.setInt(4, employee.getAccountId());
                int result = st.executeUpdate();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
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

    public int softDeleteEmployee(int employeeId) {
        String sql = "UPDATE nhanvien SET TINHTRANG = 0 WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, employeeId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int restoreEmployee(int employeeId) {
        String sql = "UPDATE nhanvien SET TINHTRANG = 1 WHERE idTK = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, employeeId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return -1;
    }

    public List<Employee> searchEmployees(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, tk.SDT, tk.EMAIL, NV.NGAYSINH, tk.USERNAME, nv.DIACHI, q.LUONG, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN WHERE (tk.HOTEN LIKE ? OR tk.SDT LIKE ? OR tk.EMAIL LIKE ?) AND nv.TINHTRANG = 1";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                String likeKeyword = "%" + keyword + "%";
                for (int i = 1; i <= 3; i++) {
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

    public List<Employee> searchEmployees(String keyword, int status) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT nv.idTk, tk.HOTEN, nv.GIOITINH, tk.SDT, tk.EMAIL, nv.NGAYSINH, tk.USERNAME, nv.DIACHI, q.LUONG, nv.TINHTRANG FROM nhanvien nv JOIN taikhoan tk ON nv.idTK = tk.idTK JOIN quyen q ON tk.idQUYEN = q.idQUYEN WHERE 1 = 1";
        if (status >= 0) {
            sql += " AND nv.TINHTRANG = ?";
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND (tk.HOTEN LIKE ? OR tk.SDT LIKE ? OR tk.EMAIL LIKE ?)";
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
                st.setString(paramIndex++, likeKeyword);
                st.setString(paramIndex++, likeKeyword);
            }

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getInt("idTk"));
                    employee.setFullName(rs.getString("HOTEN"));
                    employee.setGender(rs.getString("GIOITINH"));
                    employee.setPhoneNumber(rs.getString("SDT"));
                    employee.setEmail(rs.getString("EMAIL"));
                    employee.setDateOfBirth(rs.getDate("NGAYSINH"));
                    employee.setUserName(rs.getString("USERNAME"));
                    employee.setAddress(rs.getString("DIACHI"));
                    employee.setSalary(rs.getBigDecimal("LUONG"));
                    employee.setStatus(rs.getInt("TINHTRANG"));
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