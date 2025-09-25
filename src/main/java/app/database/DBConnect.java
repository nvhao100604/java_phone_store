package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static Connection connection;
	private static String host = "jdbc:p6spy:mysql://localhost:3306/chdidongg";
	private static String userName = "root";
	private static String password = "";

	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(host, userName, password);
			System.out.print("Connect krồi \n");
			return connection;
		} catch (SQLException e) {
			e.getStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Connection cn = DBConnect.getConnection();
		System.out.print(cn);
	}
}
