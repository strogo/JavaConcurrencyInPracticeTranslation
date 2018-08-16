package net.jcip.examples.ch_5;

import org.junit.Test;

import java.io.File;

public class ProducerConsumerTest {

	@Test
	public void testConsummer() {
		ProducerConsumer.startIndexing(new File[] { new File("\\D:\\_MUSIC")});
	}
}
