package net.jcip.examples;

import net.jcip.examples.ch_4.Widget;

class LoggingWidget extends Widget {
	public synchronized void doSomething() {
		System.out.println(toString() + ": calling doSomething");
		super.doSomething();
	}
}
