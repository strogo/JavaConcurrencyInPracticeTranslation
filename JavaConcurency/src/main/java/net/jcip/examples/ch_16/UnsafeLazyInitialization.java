package net.jcip.examples.ch_16;

import net.jcip.annotations.NotThreadSafe;

/**
 * UnsafeLazyInitialization
 * <p/>
 * Unsafe lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */
@NotThreadSafe
public class UnsafeLazyInitialization {
	private static Resource resource;

	public static Resource getInstance() {
		if (resource == null)
			resource = new Resource(); // unsafe publication
		return resource;
	}

	static class Resource {
	}
}
