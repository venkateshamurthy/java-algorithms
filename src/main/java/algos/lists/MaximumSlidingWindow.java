/**
 * 
 */
package algos.lists;

import java.util.ArrayDeque;
import java.util.Deque;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaximumSlidingWindow {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	int w = 3;
	int[] a = { 5, 6, 3, 2, 1, 3, 6, 7, -8, -9, 10, 15, -5, 6, 9, 13 };
	int[] b = new int[a.length - w];

	public MaximumSlidingWindow() {
		Deque<Integer> q = new ArrayDeque<Integer>(w);
		for (int i = 0; i < w; i++) {
			while (!q.isEmpty() && a[i] >= a[q.peekLast()])
				q.removeLast();
			q.addLast(i);
			log.debug(q + " ");
		}

		for (int i = w; i < a.length; i++) {
			b[i - w] = a[q.removeFirst()];
			while (!q.isEmpty() && a[i] >= a[q.peekLast()]) {
				log.debug("Removing:"+a[q.removeLast()]);
			}
			while (!q.isEmpty() && q.peekFirst() <= i - w)
				log.debug("Removing:"+a[q.removeFirst()]);
			q.addLast(i);
			log.debug(q + " i=" + i + " w=" + (i - w) + " " + b[i - w]);
		}
		b[a.length - 1 - w] = a[q.removeFirst()];
		log.debug(q + " " + b[a.length - 1 - w]);
	}

	public static void main(String[] args) {
		new MaximumSlidingWindow();
	}

}
