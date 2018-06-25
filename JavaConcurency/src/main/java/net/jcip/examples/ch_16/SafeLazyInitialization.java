package net.jcip.examples.ch_16;

import net.jcip.annotations.ThreadSafe;

/**
 * SafeLazyInitialization
 * <p/>
 * Thread-safe lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SafeLazyInitialization {
	private static Resource resource;

	public synchronized static Resource getInstance() {
		if (resource == null)
			resource = new Resource();
		return resource;
	}

	static class Resource {
	}
}
