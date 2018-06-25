package net.jcip.examples.ch_16;

import net.jcip.annotations.ThreadSafe;

/**
 * EagerInitialization
 * <p/>
 * Eager initialization
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class EagerInitialization {
	private static Resource resource = new Resource();

	public static Resource getResource() {
		return resource;
	}

	static class Resource {
	}
}
