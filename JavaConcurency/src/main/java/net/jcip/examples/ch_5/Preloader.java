package net.jcip.examples.ch_5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Preloader
 * <p>
 * Using FutureTask to preload data that is needed later
 *
 * @author Brian Goetz and Tim Peierls
 */

public class Preloader {
	private final FutureTask<ProductInfo> future =
			new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
				public ProductInfo call() throws DataLoadException {
					return loadProductInfo();
				}
			});
	private final Thread thread = new Thread(future);

	ProductInfo loadProductInfo() throws DataLoadException {
		return null;
	}

	public void start() {
		thread.start();
	}

	public ProductInfo get()
			throws DataLoadException, InterruptedException {
		try {
			return future.get();
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof DataLoadException)
				throw (DataLoadException) cause;
			else
				throw LaunderThrowable.launderThrowable(cause);
		}
	}

	interface ProductInfo {
	}
}

class DataLoadException extends Exception {
}
