package net.jcip.examples.ch_5;

/**
 * Created by asorokin on 25.06.2018.
 */
interface Computable<A, V> {
	V compute(A arg) throws InterruptedException;
}
