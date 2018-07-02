package net.jcip.examples.ch_2;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.*;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * UnsafeCachingFactorizer
 * <p>
 * Servlet that attempts to cache its last result without adequate atomicity
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeCachingFactorizer extends GenericServlet implements Servlet {
	private final AtomicReference<BigInteger> lastNumber
			= new AtomicReference<BigInteger>();
	private final AtomicReference<BigInteger[]> lastFactors
			= new AtomicReference<BigInteger[]>();

	// Число, переданное в сервлет для факторизации
	private final AtomicReference<BigInteger> fromReqNumber
			= new AtomicReference<BigInteger>();

	public void service(ServletRequest req, ServletResponse resp) {
		BigInteger i = extractFromRequest(req);
		if (i.equals(lastNumber.get()))
			encodeIntoResponse(resp, lastFactors.get());
		else {
			BigInteger[] factors = factor(i);
			lastNumber.set(i);
			lastFactors.set(factors);
			encodeIntoResponse(resp, factors);
		}
	}

	void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
	}

	BigInteger extractFromRequest(ServletRequest req) {
		return fromReqNumber.get();
	}

	BigInteger[] factor(BigInteger i) {
		// Doesn't really factor
		return new BigInteger[]{i};
	}

	/** Эмуляция передачи значения на факторизацию в сервлет */
	public void setFromReqNumber(BigInteger fromReqNumber) {
		this.fromReqNumber.set(fromReqNumber);
	}

	public BigInteger[] getLastFactors() {
		return lastFactors.get();
	}
}

