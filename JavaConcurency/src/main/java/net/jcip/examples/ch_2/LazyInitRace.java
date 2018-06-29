package net.jcip.examples.ch_2;

import net.jcip.annotations.NotThreadSafe;

/**
 * LazyInitRace
 * <p>
 * Race condition in lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class LazyInitRace {
	private ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
		if (instance == null) {
			threadDelay();
			instance = new ExpensiveObject();
		}
		return instance;
	}

	/** Небольшая задержка, чтобы каждый поток успел создать экземпляр.
	 * На моей машинке хватает 1 миллисекунды, для включения эффекта "рандомного" прохождения теста.
	 */
	private void threadDelay() {
		try {
			Thread.sleep(1);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
