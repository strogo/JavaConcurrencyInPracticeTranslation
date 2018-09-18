package net.jcip.examples.ch_7;

import net.jcip.annotations.GuardedBy;
import net.jcip.examples.ch_7.TrackingExecutor;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * WebCrawler
 * <p/>
 * Using TrackingExecutorService to save unfinished tasks for later execution
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class WebCrawler {
	private static final long TIMEOUT = 500;
	private static final TimeUnit UNIT = MILLISECONDS;
	@GuardedBy("this")
	private final Set<URL> urlsToCrawl = new HashSet<URL>();
	private final ConcurrentMap<URL, Boolean> seen = new ConcurrentHashMap<URL, Boolean>();
	private volatile TrackingExecutor exec;

	public WebCrawler(URL startUrl) {
		urlsToCrawl.add(startUrl);
	}

	public synchronized void start() {
		exec = new TrackingExecutor(Executors.newCachedThreadPool());
		for (URL url : urlsToCrawl)submitCrawlTask(url);
		urlsToCrawl.clear();
	}

	public synchronized void stop() throws InterruptedException {
		try {
			saveUncrawled(exec.shutdownNow());
			if (exec.awaitTermination(TIMEOUT, UNIT))
				saveUncrawled(exec.getCancelledTasks());
		} finally {
			exec = null;
		}
	}

	protected abstract List<URL> processPage(URL url);

	private void saveUncrawled(List<Runnable> uncrawled) {
		for (Runnable task : uncrawled)
			urlsToCrawl.add(((CrawlTask) task).getPage());
	}

	private void submitCrawlTask(URL u) {
		exec.execute(new CrawlTask(u));
	}

	private class CrawlTask implements Runnable {
		private final URL url;
		private int count = 1;

		CrawlTask(URL url) {
			this.url = url;
		}

		boolean alreadyCrawled() {
			return seen.putIfAbsent(url, true) != null;
		}

		void markUncrawled() {
			seen.remove(url);
			System.out.printf("marking %s uncrawled%n", url);
		}

		public void run() {
			for (URL link : processPage(url)) {
				if (Thread.currentThread().isInterrupted())
					return;
				submitCrawlTask(link);
			}
		}

		public URL getPage() {
			return url;
		}
	}
}
