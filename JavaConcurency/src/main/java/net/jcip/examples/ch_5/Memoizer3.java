package net.jcip.examples.ch_5;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Memoizer3
 * <p/>
 * Memoizing wrapper using FutureTask
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer3<A, V> implements Computable<A, V> {
	private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> comp;

	public Memoizer3(Computable<A, V> comp) {
		this.comp = comp;
	}

	public V compute(final A arg) throws InterruptedException {
		Future<V> cachedVal = cache.get(arg);
		if (cachedVal == null) {
			Callable<V> eval = new Callable<V>() {
				public V call() throws InterruptedException {
					return comp.compute(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			cachedVal = ft;
			cache.put(arg, ft);
			ft.run(); // call to c.compute happens here
		}
		try {
			return cachedVal.get();
		} catch (ExecutionException e) {
			throw LaunderThrowable.launderThrowable(e.getCause());
		}
	}
}