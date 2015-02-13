package store;

import java.sql.*;

import exception.Project1Exception;
/**
 * This is a database helper for getting connection from database. 
 *  Usage
 *	DBHelper db = DBHelper.getInstance()
 *  Connection conn = db.getConnection()
 *  ...
 *  
 */
public class DBHelper {
	private static DBHelper helperInstance;
	private final String url = "";
	private final String user = "";
	private final String password = "";

	/* Singleton for this helper */
	private DBHelper() {
		try {
			DriverManager.registerDriver(
					new oracle.jdbc.driver.OracleDriver()
					//or new org.postgresql.Driver()
					//or new com.mysql.jdbc.Driver()	
			);
		} catch (Exception cause) {
			throw new Project1Exception(cause.getMessage(), cause);
		}
	}

	public synchronized static DBHelper getInstance() {
		if (helperInstance == null) {
			try {
				helperInstance = new DBHelper();
			} catch (Exception cause) {
				throw new Project1Exception(cause.getMessage(), cause);
			}
		}
		return helperInstance;
	}

	public Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception cause) {
			throw new Project1Exception(cause.getMessage(), cause);
		}

		return conn;
	}
	
	/* For closing Connection, ResultSet, and Statement*/
	public static void close(Object... args) {
		for (Object arg : args) {
			if (arg instanceof Connection) {
				DBHelper.close((Connection) arg);
			} else if (arg instanceof Statement) {
				DBHelper.close((Statement) arg);
			} else if (arg instanceof ResultSet) {
				DBHelper.close((ResultSet) arg);
			} else {
				throw new Project1Exception("Unsupported type");
			}
		}
	}
	
	private static void close(Connection connection) {
		try {
			connection.close();
		} catch (Exception cause) {
			throw new Project1Exception(cause.getMessage(), cause);
		}
	}

	private static void close(Statement statement) {
		try {
			statement.close();
		} catch (Exception cause) {
			throw new Project1Exception(cause.getMessage(), cause);
		}
	}

	private static void close(ResultSet resultset) {
		try {
			resultset.close();
		} catch (Exception cause) {
			throw new Project1Exception(cause.getMessage(), cause);
		}
	}
	
	public static void main(String[] args) {
		
		/**
		 * This is an example for querying
		 */
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			DBHelper helper = DBHelper.getInstance();
			conn = helper.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM TableName");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, statement, resultSet);
		}
	}

}
