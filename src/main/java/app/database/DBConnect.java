package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static Connection connection;
	private static String host = "jdbc:mysql://localhost:3306/chdidongg";
	private static String userName = "root";
	private static String password = "";

	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(host, userName, password);
			return connection;
		} catch (SQLException e) {
			e.getStackTrace();
			return null;
		}
	}
}
