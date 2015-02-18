package transactions;


public class Transaction implements Runnable {
	private Thread t;
	private static final String threadName = "Transaction 1";
	
	public Transaction() {
	}

	@Override
	public void run() {
		//need to override during unit test
	}

	public void start() {
		System.out.println("Starting transaction");
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
