package net.jcip.examples.ch_12;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Semaphore;

/**
 * BoundedBuffer
 * <p/>
 * Bounded buffer using \Semaphore
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SemaphoreBoundedBuffer<E> {
	/** Количество элементов, которое может быть удалено из буфера */
	private final Semaphore availableItems;
	/** Количество элементов, которое может быть добавлено в буфер, */
	private final Semaphore  availableSpaces;

	@GuardedBy("this")
	private final E[] items;
	@GuardedBy("this")
	private int putPosition = 0, takePosition = 0;

	public SemaphoreBoundedBuffer(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException();
		availableItems = new Semaphore(0);
		availableSpaces = new Semaphore(capacity);
		items = (E[]) new Object[capacity];
	}

	public boolean isEmpty() {
		return availableItems.availablePermits() == 0;
	}

	public boolean isFull() {
		return availableSpaces.availablePermits() == 0;
	}

	public void put(E x) throws InterruptedException {
		availableSpaces.acquire();/*доб*/
		doInsert(x);
		availableItems.release();/*уд*/
	}

	public E take() throws InterruptedException {
		availableItems.acquire();/*уд*/
		E item = doExtract();
		availableSpaces.release();/*доб*/
		return item;
	}

	private synchronized void doInsert(E x) {
		int i = putPosition;
		items[i] = x;
		putPosition = (++i == items.length) ? 0 : i;
	}

	private synchronized E doExtract() {
		int i = takePosition;
		E x = items[i];
		items[i] = null;
		takePosition = (++i == items.length) ? 0 : i;
		return x;
	}
}
