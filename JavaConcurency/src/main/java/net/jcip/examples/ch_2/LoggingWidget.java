package net.jcip.examples.ch_2;

import net.jcip.examples.ch_4.Widget;

class LoggingWidget extends Widget {
	public synchronized void doSomething() {
		System.out.println(toString() + ": calling doSomething");
		super.doSomething();
	}
}
