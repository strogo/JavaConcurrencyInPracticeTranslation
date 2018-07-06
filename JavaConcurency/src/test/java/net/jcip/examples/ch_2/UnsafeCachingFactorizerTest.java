package net.jcip.examples.ch_2;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 02.07.2018.
 */
public class UnsafeCachingFactorizerTest {
	UnsafeCachingFactorizer cachingFactorizer = new UnsafeCachingFactorizer();
	final int MAX_ITERATIONS = 2;
	BigInteger[] cache_result_1;
	BigInteger[] cache_result_2;
	BigInteger[] cache_result_3;
	BigInteger[] cache_result_4;

	@Test
	public void testCachedFactorize() {
		// Передаём в "сервлет" значение 2
		Thread th_1 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++) {
				// Эмуляция вызова сервлета
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("2")), null);
				// Пауза, чтобы другой поток мог внести изменения в кэш
				sleep();
				// Запоминаем состояние кэша
				cache_result_1 = cachingFactorizer.getLastFactors();
				// Повторный вызов сервлета, с надеждой, что он сможет взять значение из кэша
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("2")), null);
				sleep();
				cache_result_2 = cachingFactorizer.getLastFactors();
			}
		});

		// Передаём в "сервлет" значение 4
		Thread th_2 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++) {
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("4")), null);
				sleep();
				cache_result_3 = cachingFactorizer.getLastFactors();
				cachingFactorizer.service(new UsrServletRequest(new BigInteger("4")), null);
				sleep();
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

		// Проверяем, как закэшировалось. На моей машине,
		// условие состояние гонки возникает примерно раз в 5-ть запусков.
		assertEquals(cache_result_1[0], cache_result_2[0]);
	}

	private void sleep() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
