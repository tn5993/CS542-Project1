package exception;

public class DatabaseHelperException extends RuntimeException{
	
	public DatabaseHelperException(String msg)
	{
		super(msg);
	}
	
	public DatabaseHelperException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
