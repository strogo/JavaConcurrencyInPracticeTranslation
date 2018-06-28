package net.jcip.examples.ch_2;

/**
 * Created by asorokin on 28.06.2018.
 * Поток запуска факторизатора
 */
public class FactorizerWorkerThread implements Runnable {
	UnsafeCountingFactorizer factorizer;

	public FactorizerWorkerThread(UnsafeCountingFactorizer factorizer) {
		this.factorizer = factorizer;
	}

	@Override
	public void run() {
		this.factorizer.service(null, null);
	}
}