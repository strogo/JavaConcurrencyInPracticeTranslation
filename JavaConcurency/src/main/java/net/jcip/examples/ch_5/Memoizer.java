package net.jcip.examples.ch_5;

import java.util.concurrent.*;

/**
 * Memoizer
 * <p/>
 * Final implementation of Memoizer
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer<A, V> implements Computable<A, V> {
	private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> comp;

	public Memoizer(Computable<A, V> comp) {
		this.comp = comp;
	}

	public V compute(final A arg) throws InterruptedException {
		while (true) {
			Future<V> cacheVal = cache.get(arg);
			if (cacheVal == null) {
				Callable<V> eval = new Callable<V>() {
					public V call() throws InterruptedException {
						return comp.compute(arg);
					}
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				cacheVal = cache.putIfAbsent(arg, ft);
				if (cacheVal == null) {
					cacheVal = ft;
					ft.run();
				}
			}
			try {
				return cacheVal.get();
			} catch (CancellationException e) {
				cache.remove(arg, cacheVal);
			} catch (ExecutionException e) {
				throw LaunderThrowable.launderThrowable(e.getCause());
			}
		}
	}
}