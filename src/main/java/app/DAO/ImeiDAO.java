package app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import app.DTO.Imei;
import app.database.DBConnect;

public class ImeiDAO {

    public int updateImeiOrder(String imei, int newIdPN) {
        String sql = "UPDATE imei SET idPN = ? WHERE imei = ?";
        try (Connection connection = DBConnect.getConnection();
                PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, newIdPN);
            st.setString(2, imei);
            return st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int AddImei(Imei imei) {
        String sql = "INSERT INTO imei (idCTSP, imei, idPN)\n" + //
                "VALUES \n" + //
                "(?, ?, ?)";
        try (Connection connection = DBConnect.getConnection();
                PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, imei.getIdProductDetail());
            st.setString(2, imei.getImei());
            st.setInt(3, imei.getIdImport());
            return st.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return -1;
        }
    }
}
