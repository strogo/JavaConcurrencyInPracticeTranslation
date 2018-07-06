package net.jcip.examples.ch_2;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * CachedFactorizer
 * <p/>
 * Servlet that caches its last request and result
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class CachedFactorizer extends GenericServlet implements Servlet {
	@GuardedBy("this")
	private BigInteger lastNumber;
	@GuardedBy("this")
	private BigInteger[] lastFactors;
	@GuardedBy("this")
	private long hits;
	@GuardedBy("this")
	private long cacheHits;

	public synchronized long getHits() {
		return hits;
	}

	public synchronized double getCacheHitRatio() {
		return (double) cacheHits / (double) hits;
	}

	public void service(UsrServletRequest req, ServletResponse resp) {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = null;
		synchronized (this) {
			++hits;
			if (i.equals(lastNumber)) {
				++cacheHits;
				factors = lastFactors.clone();
			}
		}
		if (factors == null) {
			factors = factor(i);
			synchronized (this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
		encodeIntoResponse(resp, factors);
	}

	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	BigInteger extractFromRequest(UsrServletRequest req) {
		return (BigInteger) req.getValue();
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[]{i};
	}

	public BigInteger[] getLastFactors() {
		return lastFactors;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	}
}
