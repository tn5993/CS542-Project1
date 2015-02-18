package store;

import java.io.Closeable;

/**
 * Requirement: http://cs542.wpi.datathinks.org/proj
 * 
 * A values store (a simplified version of a key-value store). The value store
 * has just three interfaces:
 *
 */
public interface IValueStore extends Closeable
{
	/* Store data under the given key*/
	void put(int key, byte[] data);

	/* Retrieves the data */
	byte[] get(int key);

	/* Deletes the key */
	void remove(int key);
	
	/* Close database connection in value store */
	void close();
}
