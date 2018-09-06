package net.jcip.examples.ch_7;

import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeGeneratorTest {

	@Test
	public void primeGeneratorTest() {
		try {
			List<BigInteger> lst = PrimeGenerator.aSecondOfPrimes();
			lst.stream().forEach(val -> System.out.println(val));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
