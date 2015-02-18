package store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import exception.DatabaseHelperException;
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
	private final String url = "jdbc:mysql://localhost/test?maxAllowedPacket=1073741824";
	private final String user = "";
	private final String password = "";

	/* Singleton for this helper */
	private DBHelper() {
		try {
			DriverManager.registerDriver(
					//new oracle.jdbc.driver.OracleDriver()
					//or new org.postgresql.Driver()
					new com.mysql.jdbc.Driver()	
			);
		} catch (Exception cause) {
			throw new DatabaseHelperException(cause.getMessage(), cause);
		}
	}

	public synchronized static DBHelper getInstance() {
		if (helperInstance == null) {
			try {
				helperInstance = new DBHelper();
			} catch (Exception cause) {
				throw new DatabaseHelperException(cause.getMessage(), cause);
			}
		}
		return helperInstance;
	}

	public Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception cause) {
			throw new DatabaseHelperException(cause.getMessage(), cause);
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
			}
		}
	}
	
	private static void close(Connection connection) {
		try {
			connection.close();
		} catch (Exception cause) {
			throw new DatabaseHelperException(cause.getMessage(), cause);
		}
	}

	private static void close(Statement statement) {
		try {
			statement.close();
		} catch (Exception cause) {
			throw new DatabaseHelperException(cause.getMessage(), cause);
		}
	}

	private static void close(ResultSet resultset) {
		try {
			resultset.close();
		} catch (Exception cause) {
			throw new DatabaseHelperException(cause.getMessage(), cause);
		}
	}
}
