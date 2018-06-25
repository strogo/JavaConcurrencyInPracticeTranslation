package net.jcip.examples.ch_4;

/**
 * NonreentrantDeadlock
 * <p/>
 * Code that would deadlock if intrinsic locks were not reentrant
 *
 * @author Brian Goetz and Tim Peierls
 */

public class Widget {
	public synchronized void doSomething() {
	}
}
