package store;

/**
 * Requirement: http://cs542.wpi.datathinks.org/proj
 * 
 * A values store (a simplified version of a key-value store). The value store
 * has just three interfaces:
 *
 */
public interface IValueStore 
{
	/* Store data under the given key*/
	void Put(int key, byte[] data);

	/* Retrieves the data */
	byte[] Get(int key);

	/* Deletes the key */
	void Remove(int key);
}
