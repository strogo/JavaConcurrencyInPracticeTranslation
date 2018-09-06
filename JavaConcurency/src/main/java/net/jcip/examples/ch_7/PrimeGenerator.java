package net.jcip.examples.ch_7;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * PrimeGenerator
 * <p/>
 * Using a volatile field to hold cancellation state
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class PrimeGenerator implements Runnable {
	private static ExecutorService exec = Executors.newCachedThreadPool();

	@GuardedBy("this")
	private final List<BigInteger> primes = new ArrayList<BigInteger>();
	private volatile boolean cancelled;

	static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		exec.execute(generator);
		try {
			SECONDS.sleep(1);
		} finally {
			generator.cancel();
		}
		return generator.get();
	}

	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
}
