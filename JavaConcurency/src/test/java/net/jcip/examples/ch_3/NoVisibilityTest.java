package net.jcip.examples.ch_3;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 10.07.2018.
 */
public class NoVisibilityTest {
	private static boolean ready;
	private static int number;

	@Test(timeout = 2000)
	public void testMain() {
		Thread[] threads = new Thread[100];
		for (int k = 0; k < threads.length; k++)
			threads[k] = new ReaderThread();

		for (int k = 0; k < threads.length; k++)
			threads[k].start();

		number = 42;
		ready = true;
	}

	private class ReaderThread extends Thread {
		public void run() {
			while (!ready)
				Thread.yield();
			assertEquals(42, number);
			System.out.println(number);
		}
	}
}
