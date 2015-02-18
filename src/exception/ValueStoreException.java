package exception;

public class ValueStoreException extends RuntimeException{
	
	public ValueStoreException(String msg)
	{
		super(msg);
	}
	
	public ValueStoreException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
