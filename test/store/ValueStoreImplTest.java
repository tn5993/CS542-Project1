package store;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import transactions.Transaction;

public class ValueStoreImplTest {
	private IValueStore valueStore;
	private static byte[] bigdata;
	private static byte[] bigdata2;
	private final static Integer fileSize = 134217728;
	private static final Integer fileSize2 = 6710886;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bigdata = RandomUtils.nextBytes(fileSize);// new byte[fileSize];									// //268435456 //256MB
		bigdata2 = RandomUtils.nextBytes(fileSize2);// new byte[fileSize2];
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void putGetAndRemoveBigData() {
		IValueStore valueStore = createValueStore();
		valueStore.put(1, bigdata);
		byte[] retrieveData = valueStore.get(1);
		assertEquals(retrieveData.length, 134217728);
		valueStore.remove(1);
		retrieveData = valueStore.get(1);
		assertEquals(1, retrieveData.length); // has nothing
		valueStore.close();
	}

	@Test
	public void testTransactionGetWhenTryingToRemove() {
		IValueStore valueStore = createValueStore();
		valueStore.put(1, bigdata);
		valueStore.close();
		
		Transaction transaction1 = new Transaction() {
			public void run() {
				IValueStore valueStore = new ValueStore();
				valueStore.remove(1);
				System.out.println("Transaction 1 remove ");
				valueStore.close();
			}
		};

		Transaction transaction2 = new Transaction() {
			public void run() {

				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);

				System.out.println("Transaction 2 read " + result.length);
				valueStore.close();
			}
		};

		Transaction transaction3 = new Transaction() {
			public void run() {
				try {
					IValueStore valueStore = new ValueStore();
					byte[] result = valueStore.get(1);
					Thread.sleep(1000);
					System.out.println("Transaction 3 read " + result.length);
					valueStore.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		transaction1.start();
		transaction2.start();
		transaction3.start();
	}
	
	//@Test
	public void testTransactionUpdateAndGet() {
		
		Transaction transaction1 = new Transaction() {
			public void run() {
				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Transaction 1 read " + result.length);
				valueStore.close();
			}
		};

		Transaction transaction2 = new Transaction() {
			public void run() {
				IValueStore valueStore = new ValueStore();
				valueStore.put(1, bigdata2);
				System.out.println("Transaction 2 put " + bigdata2.length);
				valueStore.close();
			}
		};

		Transaction transaction3 = new Transaction() {
			public void run() {
				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Transaction 3 read " + result.length);
				valueStore.close();
			}
		};
		
		transaction1.start();
		transaction2.start();
		transaction3.start();
	}

	public IValueStore createValueStore() {
		return new ValueStore();
	}

}
