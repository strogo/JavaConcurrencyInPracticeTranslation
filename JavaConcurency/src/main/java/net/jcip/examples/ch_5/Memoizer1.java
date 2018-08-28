package net.jcip.examples.ch_5;

import net.jcip.annotations.GuardedBy;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Memoizer1
 * <p>
 * Initial cache attempt using HashMap and synchronization
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer1<A, V> implements Computable<A, V> {
	@GuardedBy("this")
	private final Map<A, V> cache = new HashMap<A, V>();
	private final Computable<A, V> comp;

	public Memoizer1(Computable<A, V> comp) {
		this.comp = comp;
	}

	public synchronized V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if (result == null) {
			result = comp.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
}

class ExpensiveFunction
		implements Computable<String, BigInteger> {
	public BigInteger compute(String arg) {
		// after deep thought...
		return new BigInteger(arg);
	}
}
