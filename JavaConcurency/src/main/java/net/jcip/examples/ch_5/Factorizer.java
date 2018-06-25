package net.jcip.examples.ch_5;

import net.jcip.annotations.ThreadSafe;
import net.jcip.examples.ch_5.Computable;
import net.jcip.examples.ch_5.Memoizer;

import javax.servlet.*;
import java.math.BigInteger;

/**
 * Factorizer
 * <p/>
 * Factorizing servlet that caches results using Memoizer
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class Factorizer extends GenericServlet implements Servlet {
	private final Computable<BigInteger, BigInteger[]> c =
			new Computable<BigInteger, BigInteger[]>() {
				public BigInteger[] compute(BigInteger arg) {
					return factor(arg);
				}
			};
	private final Computable<BigInteger, BigInteger[]> cache
			= new Memoizer<BigInteger, BigInteger[]>(c);

	public void service(ServletRequest req,
	                    ServletResponse resp) {
		try {
			BigInteger i = extractFromRequest(req);
			encodeIntoResponse(resp, cache.compute(i));
		} catch (InterruptedException e) {
			encodeError(resp, "factorization interrupted");
		}
	}

	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	void encodeError(ServletResponse resp, String errorString) {
	}

	BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("7");
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[]{i};
	}
}
