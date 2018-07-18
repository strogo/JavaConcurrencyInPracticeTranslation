package net.jcip.examples.ch_3;

import net.jcip.annotations.ThreadSafe;
import net.jcip.examples.ch_3.OneValueCache;

import javax.servlet.*;
import java.math.BigInteger;

/**
 * VolatileCachedFactorizer
 * <p/>
 * Caching the last result using a volatile reference to an immutable holder object
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class VolatileCachedFactorizer extends GenericServlet implements Servlet {
	private volatile OneValueCache cache = new OneValueCache(null, null);

	public void service(ServletRequest req, ServletResponse resp) {
		BigInteger number = extractFromRequest(req);
		BigInteger[] factors = cache.getFactors(number);
		if (factors == null) {
			factors = factor(number);
			cache = new OneValueCache(number, factors);
		}
		encodeIntoResponse(resp, factors);
	}

	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("7");
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[]{i};
	}
}

