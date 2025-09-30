package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.DTO.Function;
import app.database.DBConnect;

public class FunctionDAO {
    public List<Function> getAll() {
        List<Function> list = new ArrayList<>();
        String sql = "SELECT * FROM chucnang";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Function function = new Function(
                        rs.getInt("idCN"),
                        rs.getString("TENCN"),
                        rs.getString("ICON"),
                        rs.getInt("TRANGTHAI")
                    );
                    list.add(function);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public List<Function> getFunctionsForRole() {
        List<Function> list = new ArrayList<>();
        String sql = "SELECT c.* FROM phanquyen pq JOIN quyen q ON pq.idQUYEN = q.idQUYEN JOIN chucnang c ON pq.idCN = c.idCN WHERE q.idQUYEN = 0";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Function function = new Function(
                        rs.getInt("idCN"),
                        rs.getString("TENCN"),
                        rs.getString("ICON"),
                        rs.getInt("TRANGTHAI")
                    );
                    list.add(function);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    public List<Function> getFunction() {
        List<Function> list = new ArrayList<>();
        String sql = "SELECT idCN, TENCN FROM chucnang";
        try (Connection con = DBConnect.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Function function = new Function(
                        rs.getInt("idCN"),
                        rs.getString("TENCN")
                    );
                    list.add(function);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    // public static void main(String[] args) {
    // FunctionDAO dao = new FunctionDAO();
    // List<Function> functions = dao.getAll();
    // for (Function function : functions) {
    // System.out.println(function.getFunctionName());
    // }
    // }
}