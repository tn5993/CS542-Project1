package store;

import junit.framework.TestCase;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.apache.commons.lang3.RandomUtils;

public class ValueStoreImplTest extends TestCase {
	private static byte[] bigdata;
	private static byte[] bigdata2;
	private final static Integer fileSize = 134217728;
	private static final Integer fileSize2 = 6710886;

	/**
	 * Standard main() and suite() methods
	 */
	public static void main(String[] args) {
		String[] name = { ValueStoreImplTest.class.getName() };

		junit.textui.TestRunner.main(name);
	}

	public void testTransactionGetWhenTryingToRemove() throws Throwable {
		bigdata = RandomUtils.nextBytes(fileSize);
		bigdata2 = RandomUtils.nextBytes(fileSize2);
		IValueStore valueStore = createValueStore();
		valueStore.put(1, bigdata);
		valueStore.close();

		TestRunnable tr1 = new TestRunnable() {
			public void runTest() {
				IValueStore valueStore = new ValueStore();
				valueStore.remove(1);
				System.out.println("Transaction 1 remove ");
				valueStore.close();
			}
		};

		TestRunnable tr2 = new TestRunnable() {
			public void runTest() {

				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);

				System.out.println("Transaction 2 read " + result.length);
				valueStore.close();
			}
		};

		TestRunnable tr3 = new TestRunnable() {
			public void runTest() {
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
		TestRunnable[] trs = { tr1, tr2, tr3 };
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		mttr.runTestRunnables();
	}

	public void testTransactionUpdateAndGet() throws Throwable {
		bigdata = RandomUtils.nextBytes(fileSize);
		bigdata2 = RandomUtils.nextBytes(fileSize2);
		IValueStore valueStore = createValueStore();
		valueStore.put(1, bigdata);
		valueStore.close();
		
		TestRunnable tr1 = new TestRunnable() {
			public void runTest() {
				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);
				System.out.println("Transaction 1 read " + result.length);
				valueStore.close();
			}
		};

		TestRunnable tr2 = new TestRunnable() {
			public void runTest() {
				IValueStore valueStore = new ValueStore();
				valueStore.put(1, bigdata2);
				System.out.println("Transaction 2 put " + bigdata2.length);
				valueStore.close();
			}
		};

		TestRunnable tr3 = new TestRunnable() {
			public void runTest() {
				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				System.out.println("Transaction 3 read " + result.length);
				valueStore.close();
			}
		};
		
		TestRunnable tr4 = new TestRunnable() {
			public void runTest() {
				IValueStore valueStore = new ValueStore();
				byte[] result = valueStore.get(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				System.out.println("Transaction 4 read " + result.length);
				valueStore.close();
			}
		};

		TestRunnable[] trs = { tr1, tr2, tr3, tr4 };
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
		mttr.runTestRunnables();
	}

	public IValueStore createValueStore() {
		return new ValueStore();
	}

}
