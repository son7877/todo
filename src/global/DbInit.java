package global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbInit {
	private final static String url = "jdbc:mysql://localhost:3306/project_1";
	private final static String name = "encore";
	private final static String password = "1234";
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, name, password);
	}
}
