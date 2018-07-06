package net.jcip.examples.ch_2;

/**
 * Created by asorokin on 06.07.2018.
 */
public class FactorizerUtils {
	public static void sleep() {
		sleep(5);
	}

	public static void sleep(int pause) {
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
