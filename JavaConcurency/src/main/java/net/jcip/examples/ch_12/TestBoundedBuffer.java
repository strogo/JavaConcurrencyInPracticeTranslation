package net.jcip.examples.ch_12;

import junit.framework.TestCase;
import net.jcip.examples.ch_12.SemaphoreBoundedBuffer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TestBoundedBuffer
 * <p/>
 * Basic unit tests for BoundedBuffer
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestBoundedBuffer extends TestCase {
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;
	private static final int CAPACITY = 10000;
	private static final int THRESHOLD = 10000;

	public void testIsEmptyWhenConstructed() {
		SemaphoreBoundedBuffer<Integer> bb = new SemaphoreBoundedBuffer<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}

	public void testIsFullAfterPuts() throws InterruptedException {
		SemaphoreBoundedBuffer<Integer> bb = new SemaphoreBoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++)
			bb.put(i);
		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}


	public void testTakeBlocksWhenEmpty() {
		final SemaphoreBoundedBuffer<Integer> bb = new SemaphoreBoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					fail(); // if we get here, it's an error
				} catch (InterruptedException success) {
				}
			}
		};
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}

	public void testLeak() throws InterruptedException {
		SemaphoreBoundedBuffer<Big> bb = new SemaphoreBoundedBuffer<Big>(CAPACITY);
		int heapSize1 = snapshotHeap();
		for (int i = 0; i < CAPACITY; i++)
			bb.put(new Big());
		for (int i = 0; i < CAPACITY; i++)
			bb.take();
		int heapSize2 = snapshotHeap();
		assertTrue(Math.abs(heapSize1 - heapSize2) < THRESHOLD);
	}

	private int snapshotHeap() {
	    /* Snapshot heap and return heap size */
		return 0;
	}

	class Big {
		double[] data = new double[100000];
	}

}
