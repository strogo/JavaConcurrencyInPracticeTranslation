package net.jcip.examples.ch_2;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 29.06.2018.
 */
public class LazyInitRaceTest {
	ExpensiveObject exObject_1;
	ExpensiveObject exObject_2;

	@Test
	public void testRaceConditionInLazyInit() {
		LazyInitRace lazy = new LazyInitRace();

		Thread th_1 = new Thread(() -> {
			exObject_1 = lazy.getInstance();
		});

		Thread th_2 = new Thread(() -> {
			exObject_2 = lazy.getInstance();
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

		assertEquals(exObject_1, exObject_2);
	}
}
