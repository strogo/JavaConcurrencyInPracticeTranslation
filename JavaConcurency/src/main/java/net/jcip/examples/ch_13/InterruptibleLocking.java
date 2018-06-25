package net.jcip.examples.ch_13;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * InterruptibleLocking
 *
 * @author Brian Goetz and Tim Peierls
 */
public class InterruptibleLocking {
	private Lock lock = new ReentrantLock();

	public boolean sendOnSharedLine(String message)
			throws InterruptedException {
		lock.lockInterruptibly();
		try {
			return cancellableSendOnSharedLine(message);
		} finally {
			lock.unlock();
		}
	}

	private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
	    /* send something */
		return true;
	}

}
