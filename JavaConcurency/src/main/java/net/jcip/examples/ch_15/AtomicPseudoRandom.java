package net.jcip.examples.ch_15;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicPseudoRandom
 * <p/>
 * Random number generator using AtomicInteger
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom {
	private AtomicInteger seed;

	AtomicPseudoRandom(int seed) {
		this.seed = new AtomicInteger(seed);
	}

	public int nextInt(int n) {
		while (true) {
			int s = seed.get();
			int nextSeed = calculateNext(s);
			if (seed.compareAndSet(s, nextSeed)) {
				int remainder = s % n;
				return remainder > 0 ? remainder : remainder + n;
			}
		}
	}
}
