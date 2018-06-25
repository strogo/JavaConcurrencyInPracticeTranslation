package net.jcip.examples.ch_1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 20.06.2018.
 */
public class UnsafeSequenceTest {

	@Test
	public void justAnExample() {
		System.out.println("This test method should be run");
	}

	@Test
	public void getNext() {
		final int MAX_ITERATIONS = 100000;
		final UnsafeSequence unsafeSequence = new UnsafeSequence();

		Thread th_1 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				unsafeSequence.getNext();
		});

		Thread th_2 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				unsafeSequence.getNext();
		});

		try {
			th_1.start();
			th_2.start();
			th_1.join();
			th_2.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals("Пример нарушения потоками порядка перезаписи переменной",MAX_ITERATIONS * 2, unsafeSequence.getValue());
	}
}
