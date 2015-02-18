package store;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.ValueStoreException;

public class ValueStore implements IValueStore {
	private Connection connection;

	public ValueStore() {
		try {
			// Every IValueStoreImpl Instance will only create one database
			// connection
			DBHelper db = DBHelper.getInstance();
			connection = db.getConnection();

			// Disable auto commit
			// Every method will only commit once
			connection.setAutoCommit(false);

			// Set transaction isolation level as SERIALIZABLE
			connection
					.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e) {
			throw new ValueStoreException(e.getMessage(), e);
		}
	}

	// add a new row to database
	@Override
	public void put(int key, byte[] data) {
		String PUT = "INSERT INTO store(k, v) VALUES (?, ?) ON DUPLICATE KEY UPDATE k = ?, v = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(PUT);
			ps.setInt(1, key);
			ps.setBytes(2, data);//long blob
			ps.setInt(3, key);
			ps.setBytes(4, data);//long blob
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new ValueStoreException(String.format(
					"Failed to put into key %d \n", key), e);
		} finally {
			DBHelper.close(ps);
		}
	}

	// retrieve a row from database
	@Override
	public byte[] get(int key) {
		byte[] data = new byte[1];
		String GET = "SELECT v FROM store WHERE k=" + key;
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery(GET);
			if (result.next()) {
				Blob blob = result.getBlob("v");
				data = blob.getBytes(1L, (int) blob.length());
			}
			connection.commit();
		} catch (SQLException e) {
			throw new ValueStoreException(String.format(
					"Failed to get key %d \n", key), e);
		} finally {
			DBHelper.close(statement, result);
		}

		return data;
	}

	// delete a row from database
	@Override
	public void remove(int key) {
		String REMOVE = "DELETE FROM store WHERE k=" + key;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(REMOVE);
			connection.commit();
		} catch (SQLException e) {
			throw new ValueStoreException(String.format(
					"Failed to delete key %d \n", key), e);
		} finally {
			DBHelper.close(statement);
		}
	}

	public void close() {
		DBHelper.close(this.connection);
	}

	public static void main(String[] args) {
		ValueStore impl = new ValueStore();
		byte[] data = "THIS IS A TEST1".getBytes();
		impl.put(1, data);
		impl.put(2, data);

		byte[] result = impl.get(1);
		byte[] result2 = impl.get(1);
		System.out.println("r: " + new String(result));
		System.out.println("r: " + new String(result2));

		impl.remove(1);
		impl.remove(2);
		impl.close(); // Remember to close the database
		System.exit(0);
	}

}
