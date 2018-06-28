package net.jcip.examples.ch_2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 28.06.2018.
 */
public class UnsafeCountingFactorizerTest {

	@Test
	public void TestFacotorize() {
		final int MAX_ITERATIONS = 100000;

		// Эмуляция обращения к сервлету с состоянием
		UnsafeCountingFactorizer factorizer = new UnsafeCountingFactorizer();

		// Порождение потоков
		Thread th_1 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				factorizer.service(null, null);
		});

		Thread th_2 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				factorizer.service(null, null);
		});

		Thread th_3 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				factorizer.service(null, null);
		});

		th_1.start();
		th_2.start();
		th_3.start();

		// Ждём завершения потоков
		try {
			th_1.join();
			th_2.join();
			th_3.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(MAX_ITERATIONS * 3, factorizer.getCount());
	}
}
