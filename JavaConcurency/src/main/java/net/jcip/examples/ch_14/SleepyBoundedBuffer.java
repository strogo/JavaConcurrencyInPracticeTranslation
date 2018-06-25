package net.jcip.examples.ch_14;

import net.jcip.annotations.ThreadSafe;
import net.jcip.examples.ch_14.BaseBoundedBuffer;

/**
 * SleepyBoundedBuffer
 * <p/>
 * Bounded buffer using crude blocking
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
	int SLEEP_GRANULARITY = 60;

	public SleepyBoundedBuffer() {
		this(100);
	}

	public SleepyBoundedBuffer(int size) {
		super(size);
	}

	public void put(V v) throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isFull()) {
					doPut(v);
					return;
				}
			}
			Thread.sleep(SLEEP_GRANULARITY);
		}
	}

	public V take() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isEmpty())
					return doTake();
			}
			Thread.sleep(SLEEP_GRANULARITY);
		}
	}
}
