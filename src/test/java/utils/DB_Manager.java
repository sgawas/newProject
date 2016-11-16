package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB_Manager {

	private static Connection connection = null;

	public static void setDBConnection() {

		try {
			Class.forName(Configs.getDRIVER());
			connection = DriverManager.getConnection(Configs.getDB_CONNECTION_URL(), Configs.getDB_USERNAME(),
					Configs.getDB_PASSWORD());
			if (!connection.isClosed()) {
				System.out.println("Successfully connected to db");
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public static List<String> getQuery(String query) throws SQLException{
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		List<String> result = new ArrayList<String>();
		while(resultSet.next()){
			result.add(resultSet.getString(1));
		}
		return result;
	}
}
