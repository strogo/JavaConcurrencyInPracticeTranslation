package net.jcip.examples.ch_3;

import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * OneValueCache
 * <p/>
 * Immutable holder for caching a number and its factors
 *
 * @author Brian Goetz and Tim Peierls
 */
@Immutable
public class OneValueCache {
	private final BigInteger lastNumber;
	private final BigInteger[] lastFactors;

	public OneValueCache(BigInteger number,
	                     BigInteger[] factors) {
		lastNumber = number;
		lastFactors = Arrays.copyOf(factors, factors.length);
	}

	public BigInteger[] getFactors(BigInteger number) {
		if (lastNumber == null || !lastNumber.equals(number))
			return null;
		else
			return Arrays.copyOf(lastFactors, lastFactors.length);
	}
}
