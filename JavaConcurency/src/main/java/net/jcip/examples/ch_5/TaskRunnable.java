package net.jcip.examples.ch_5;

import java.util.concurrent.BlockingQueue;

/**
 * TaskRunnable
 * <p/>
 * Restoring the interrupted status so as not to swallow the interrupt
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TaskRunnable implements Runnable {
	BlockingQueue<Task> queue;

	public void run() {
		try {
			processTask(queue.take());
		} catch (InterruptedException e) {
			// restore interrupted status
			Thread.currentThread().interrupt();
		}
	}

	void processTask(Task task) {
		// Handle the task
	}

	interface Task {
	}
}
