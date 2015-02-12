package databasemodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("No driver found");
		}
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "cory", "test");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("select * from demo_users");
				 rs = stmt.executeQuery();
				while (rs.next()) {
					String name = rs.getString("user_name");
					System.out.println(name);
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("No connection");
		}
		
	}

}
