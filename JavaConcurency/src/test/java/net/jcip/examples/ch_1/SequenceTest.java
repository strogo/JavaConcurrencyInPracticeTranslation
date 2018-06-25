package net.jcip.examples.ch_1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by asorokin on 21.06.2018.
 */
public class SequenceTest {

	@Test
	public void getNext() {
		final int MAX_ITERATIONS = 100000;
		final Sequence sequence = new Sequence();

		Thread th_1 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				sequence.getNext();
		});

		Thread th_2 = new Thread(() -> {
			for (int k = 0; k < MAX_ITERATIONS; k++)
				sequence.getNext();
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

		assertEquals(MAX_ITERATIONS * 2, sequence.getValue());

	}
}
