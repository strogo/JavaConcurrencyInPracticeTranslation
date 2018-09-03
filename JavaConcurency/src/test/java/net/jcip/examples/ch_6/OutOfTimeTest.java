package net.jcip.examples.ch_6;

import org.junit.Test;


public class OutOfTimeTest {

	@Test(expected = IllegalStateException.class)
	public void outOfTimeTest() throws Exception {
		OutOfTime time = new OutOfTime();
		time.main(null);
	}
}
