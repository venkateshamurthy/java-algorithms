package algos.threads;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Thread111 extends Thread {
	private volatile boolean cancel = false;
	private static int i = 0;
	private int k;

	public Thread111() {
		start();
	}

	private synchronized void setKNotify(int k) throws InterruptedException {
		this.k = k;
		notify();
		Thread.sleep(1);
	}

	public void cancel() {
		cancel = true;
		interrupt();
	}

	public void run() {
		while (!(cancel))
			synchronized (this) {
				try {
					wait(10);
				} catch (InterruptedException e) {
					cancel = true;
					continue;
				}
				if (!cancel)
					System.out.printf(k + " ");
			}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread111[] ts = new Thread111[] { new Thread111(), new Thread111(),
				new Thread111() };
		for (; i < 10; i++) {
			for (Thread111 T : ts) {
				T.setKNotify(i);
			}
			Thread.sleep(1);
			log.info("{}","");
		}
		for (Thread111 T : ts) {
			T.cancel();
		}
	}
}