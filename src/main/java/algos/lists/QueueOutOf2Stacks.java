/**
 * 
 */
package algos.lists;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

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
//Log4j Handle creator (from lombok)
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueueOutOf2Stacks {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	
	public static void main(String[] args) {
		queue<Integer> q=QueueStack.of();
		for(int i=0;i<10;i++)q.enque(i);
		for(int i=0;i<10;i++)log.debug(q.deque());
		stack<Integer> s=StackQueue.of();
		for(int i=0;i<10;i++)s.push(i);
		for(int i=0;i<10;i++)log.debug(s.pop());
	}
	interface queue<T>{
		void enque(T t);
		T deque();
	}
	
	@Data(staticConstructor="of")
	private static class QueueStack<T> implements queue<T>{
		Stack<T> in=new Stack<T>(),out=new Stack<T>();
		@Override public void enque(T t) {
			while(!out.isEmpty())
				in.push(out.pop());
			in.push(t);
		}

		@Override public T deque() {
			while(!in.isEmpty())
				out.push(in.pop());
			return out.isEmpty()?null:out.pop();
		}
		
	}
	
	interface stack<T>{
		void push(T t);
		T pop();
	}
	@Data(staticConstructor="of")
	private static class StackQueue<T> implements stack<T>{
		Deque<T> in= new ArrayDeque<T>(),out=new ArrayDeque<T>();
		
		@Override public void push(T t) {
			//in.add(t);
			out.add(t);
			while(!in.isEmpty())
				out.add(in.remove());
			swapInOutQueues();
		}

		@Override public T pop() {
			/*while(in.size()>1)
				out.add(in.remove());
			T t=in.remove();
			swapInOutQueues();
			return t;*/
			return in.remove();
		}

		/**
		 * 
		 */
		private void swapInOutQueues() {
			Deque<T> temp=in;
			in=out;
			out=temp;
		}
		
	}
}
