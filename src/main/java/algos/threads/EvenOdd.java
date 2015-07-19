package algos.threads;

import lombok.extern.java.Log;

@Log
public class EvenOdd extends Thread {
	
	//the count till to go
	private static final int CNT = 10;
	// #threads
	private static final int MAX = 2;
	// lock
	private static final Object lock = new Object();
	//running count
	private static volatile int i = 0;
	//thread index
	protected final int index;
	
	/**
	 * constructor
	 * @param index of thread
	 * @throws InterruptedException
	 */
	private EvenOdd(int index) throws InterruptedException {
		super(index + "-thread");
		this.index = index;
		log.info(getName() + " is starting..");
		start();
	}

	public void run() {
		synchronized (lock) {
			//till running count reaches CNT
			while (i < CNT) {
				//spin lock (take care of negatives) if i is not of thread
				while ((i >= 0 ? i : -i) % MAX != index) {
					try {
						lock.wait(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if(i<CNT)
				log.info(i++ + " - " + getName());
				//notify
				lock.notify();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		EvenOdd[] evenOdds = new EvenOdd[MAX];
		for (int i = 0; i < MAX; i++)
			evenOdds[i] = new EvenOdd(i);
	}
}