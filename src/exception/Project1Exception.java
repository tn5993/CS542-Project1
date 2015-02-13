package exception;

public class Project1Exception extends RuntimeException{
	
	public Project1Exception(String msg)
	{
		super(msg);
	}
	
	public Project1Exception(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
