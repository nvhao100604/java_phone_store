package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Company;
import app.database.DBConnect;

public class CompanyDAO {
    public List<Company> getAll() {
        List<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM hang";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Company company = new Company(
                        rs.getInt("idHANG"),
                        rs.getString("TENHANG"),
                        rs.getInt("TRANGTHAI")
                    );
                    list.add(company);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public Company getCompanyById(int companyId) {
        String sql = "SELECT * FROM hang WHERE idHANG = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, companyId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new Company(
                            rs.getInt("idHANG"),
                            rs.getString("TENHANG"),
                            rs.getInt("TRANGTHAI")
                        );
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    public int addCompany(Company company) {
        String sql = "INSERT INTO hang (TENHANG) VALUES (?)";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                stmt.setString(1, company.getCompanyName());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("Inserted company ID: " + generatedId);
                        return generatedId;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int updateCompany(Company company) {
        String sql = "UPDATE hang SET TENHANG = ? WHERE idHANG = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, company.getCompanyName());
                stmt.setInt(2, company.getCompanyId());
                int result = stmt.executeUpdate();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
    }

    public int deleteCompany(int companyId) {
        String sql = "DELETE FROM hang WHERE idHANG = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, companyId);
                int rowsAffected = stmt.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }

    public int softDeleteCompany(int companyId) {
        String sql = "UPDATE hang SET TRANGTHAI = 0 WHERE idHANG = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, companyId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return 0;
    }


    public int restoreCompany(int companyId) {
        String sql = "UPDATE hang SET TRANGTHAI = 1 WHERE idHANG = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, companyId);
                int rowsAffected = st.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return -1;
    }

    public List<Company> searchCompanies(String keyword) {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM hang WHERE (TENHANG LIKE ?) AND TRANGTHAI = 1";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, "%" + keyword + "%");
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Company company = new Company();
                        company.setCompanyId(rs.getInt("idHANG"));
                        company.setCompanyName(rs.getString("TENHANG"));
                        company.setCompanyStatus(rs.getInt("TRANGTHAI"));
                        companies.add(company);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return companies;
    }

    public List<Company> searchCompanies(String keyword, int status) {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM hang WHERE 1 = 1 ";
        if (status >= 0) {
            sql += " AND TRANGTHAI = ? ";
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND TENHANG LIKE ? ";
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
                        Company company = new Company();
                        company.setCompanyId(rs.getInt("idHANG"));
                        company.setCompanyName(rs.getString("TENHANG"));
                        company.setCompanyStatus(rs.getInt("TRANGTHAI"));
                        companies.add(company);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return companies;
    }

    public List<Company> fillterCompanies(int status) {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM hang WHERE TRANGTHAI = ?";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, status);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Company compnay = new Company();
                        compnay.setCompanyId(rs.getInt("idHANG"));
                        compnay.setCompanyName(rs.getString("TENHANG"));
                        compnay.setCompanyStatus(rs.getInt("TRANGTHAI"));
                        companies.add(compnay);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return companies;
    }
}