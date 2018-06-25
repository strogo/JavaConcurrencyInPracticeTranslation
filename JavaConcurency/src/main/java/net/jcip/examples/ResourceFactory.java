package net.jcip.examples;

import net.jcip.annotations.ThreadSafe;

/**
 * ResourceFactory
 * <p/>
 * Lazy initialization holder class idiom
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class ResourceFactory {
	public static Resource getResource() {
		return ResourceFactory.ResourceHolder.resource;
	}

	private static class ResourceHolder {
		public static Resource resource = new Resource();
	}

	static class Resource {
	}
}
