package net.jcip.examples.ch_3;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HolderTest {
	final int MAX_ITERATIONS = 1000;
	Holder holder;

	@Test()
	public void testHolder() {
		ExecutorService executor = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < MAX_ITERATIONS; i++) {
			Runnable worker = new HolderWorkerThread(i);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
	}

	/**
	 * Поток использующий холдер
	 */
	private class HolderWorkerThread implements Runnable {
		private int number;

		public HolderWorkerThread(int number) {
			this.number = number;
		}

		@Override
		public void run() {
			holder = new Holder(this.number);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			holder.assertSanity();
		}
	}
}
