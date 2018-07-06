package net.jcip.examples.ch_2;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 02.07.2018.
 */
public class CountingFactorizerTest {
	@Test
	public void testFacotorize() {
		final int MAX_ITERATIONS = 1000000;

		// Эмуляция создания "сервлета с состоянием"
		CountingFactorizer factorizer = new CountingFactorizer();

		// Эмуляция обращения к "сервлету с состоянием" из 20 потоков
		ExecutorService executor = Executors.newFixedThreadPool(20);
		for (int i = 0; i < MAX_ITERATIONS; i++) {
			Runnable worker = new FactorizerWorkerThread(factorizer);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		// На моей машине тест гарантированно падает начиная с миллиона итераций
		assertEquals(MAX_ITERATIONS, factorizer.getCount());
	}

	/** Поток запуска факторизатора */
	private class FactorizerWorkerThread implements Runnable {
		CountingFactorizer factorizer;

		public FactorizerWorkerThread(CountingFactorizer factorizer) {
			this.factorizer = factorizer;
		}

		@Override
		public void run() {
			this.factorizer.service(null, null);
		}
	}
}
