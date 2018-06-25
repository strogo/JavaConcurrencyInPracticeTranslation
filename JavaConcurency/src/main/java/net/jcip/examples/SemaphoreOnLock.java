package net.jcip.examples;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SemaphoreOnLock
 * <p/>
 * Counting semaphore implemented using Lock
 * (Not really how java.util.concurrent.Semaphore is implemented)
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SemaphoreOnLock {
	private final Lock lock = new ReentrantLock();
	// CONDITION PREDICATE: permitsAvailable (permits > 0)
	private final Condition permitsAvailable = lock.newCondition();
	@GuardedBy("lock")
	private int permits;

	SemaphoreOnLock(int initialPermits) {
		lock.lock();
		try {
			permits = initialPermits;
		} finally {
			lock.unlock();
		}
	}

	// BLOCKS-UNTIL: permitsAvailable
	public void acquire() throws InterruptedException {
		lock.lock();
		try {
			while (permits <= 0)
				permitsAvailable.await();
			--permits;
		} finally {
			lock.unlock();
		}
	}

	public void release() {
		lock.lock();
		try {
			++permits;
			permitsAvailable.signal();
		} finally {
			lock.unlock();
		}
	}
}
