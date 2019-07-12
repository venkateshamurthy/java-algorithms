/**
 * 
 */
package algos.lists;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaximumSlidingWindow {
	static {
		//System.setProperty("java.util.logging.config.file","logging.properties");
		System.setProperty("java.util.logging.SimpleFormatter.format","%1$tF %1$tT [%4$s]: %5$s %n");
	}
	int w = 3;
	int[] a = { 5, 6, 3, 2, 1, 3, 6, 7, -8, -9, 10, 15, -5, 6, 9, 13 };
	int[] b = new int[a.length - w];

	public MaximumSlidingWindow() {
		Deque<Integer> q = new ArrayDeque<Integer>(w);
		for (int i = 0; i < w; i++) {
			while (!q.isEmpty() && a[i] >= a[q.peekLast()])
				q.removeLast();
			q.addLast(i);
			
		}
		log.info(q + " ");
		for (int i = w; i < a.length; i++) {
			b[i - w] = a[q.removeFirst()];
			while (!q.isEmpty() && a[i] >= a[q.peekLast()]) 
				log.info("Removing:"+a[q.removeLast()]);
			
			while (!q.isEmpty() && q.peekFirst() <= i - w)
				log.info("Removing:"+a[q.removeFirst()]);
			q.addLast(i);
			log.info(q + " i=" + i + " w=" + (i - w) + " " + b[i - w]);
		}
		b[a.length - 1 - w] = a[q.removeFirst()];
		log.info( Arrays.toString(a));
		log.info( Arrays.toString(b));
	}

	public static void main(String[] args) {
		new MaximumSlidingWindow();
	}

}
