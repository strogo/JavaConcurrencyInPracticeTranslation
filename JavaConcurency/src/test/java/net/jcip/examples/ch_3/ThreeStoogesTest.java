package net.jcip.examples.ch_3;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by asorokin on 17.07.2018.
 */
public class ThreeStoogesTest {
	@Test
	public  void testThreeStooges() {
		ThreeStooges threeStooges = new ThreeStooges();
//		System.out.println(threeStooges.getStoogeNames());
		assertTrue(threeStooges.isStooge("Moe"));
	}
}
