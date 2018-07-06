package net.jcip.examples.ch_2;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 06.07.2018.
 */
public class CachedFactorizerTest {
	CachedFactorizer cachingFactorizer = new CachedFactorizer();
	final int MAX_ITERATIONS = 2;
	BigInteger[] cache_result_1;
	BigInteger[] cache_result_2;
	BigInteger[] cache_result_3;
	BigInteger[] cache_result_4;

	@Test
	public void testCachedFactorizer() throws Exception {
		// Передаём в "сервлет" значение 3
		Thread th_1 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++) {
				// Эмуляция вызова сервлета
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("3")), null);
				// Запоминаем состояние кэша
				cache_result_1 = cachingFactorizer.getLastFactors();
				// Пауза, чтобы другой поток мог внести изменения в кэш
				FactorizerUtils.sleep(1);
				// Повторный вызов сервлета, с надеждой, что он сможет взять значение из кэша
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("3")), null);
				cache_result_2 = cachingFactorizer.getLastFactors();
			}
		});

		// Передаём в "сервлет" значение 5
		Thread th_2 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++) {
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("5")), null);
				cache_result_3 = cachingFactorizer.getLastFactors();
				FactorizerUtils.sleep(1);
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("5")), null);
				cache_result_4 = cachingFactorizer.getLastFactors();
			}
		});

		th_1.start();
		th_2.start();

		try {
			th_1.join();
			th_2.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Значения кэшей, собранных между вызовами должно совпасть.
		assertEquals(cache_result_1[0], cache_result_2[0]);
	}
}
