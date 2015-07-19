package algos.threads;
import java.util.concurrent.SynchronousQueue;

/**
 * Java Program to solve Producer Consumer problem using SynchronousQueue. A
 * call to put() will block until there is a corresponding thread to take() that
 * element.
 *
 * @author Javin Paul
 */
public class SynchronousQueueDemo{
	static int event=0;
    public static void main(String args[]) {

        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        
        Thread producer = new Thread("PRODUCER") {
            public void run() {
                
                try {
                    queue.put(event++); // thread will block here
                    System.out.printf("[%s] published event : %s %n", Thread
                            .currentThread().getName(), event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        producer.start(); // starting publisher thread

        Thread even = new Thread("EVEN") {
            public void run() {
                try {
                	if(!queue.isEmpty() && queue.peek()%2==0)
                    System.out.printf("[%s] consumed event : %s %n", Thread
                            .currentThread().getName(), queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        even.start(); // starting consumer thread
        
        Thread odd = new Thread("EVEN") {
            public void run() {
                try {
                	if(!queue.isEmpty() && queue.peek()%2!=0)
                    System.out.printf("[%s] consumed event : %s %n", Thread
                            .currentThread().getName(), queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        odd.start();
    }

}
