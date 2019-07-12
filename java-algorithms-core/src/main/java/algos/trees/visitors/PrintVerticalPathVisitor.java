/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;


/**
 * @author vmurthy
 * 
 */
@Slf4j
//@Data(staticConstructor="of")
@Accessors(fluent = true,chain=true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrintVerticalPathVisitor<T extends Comparable<T>> implements BSTVisitor<T, Void, List<List<T>>> {

	Deque<T> queue = new ArrayDeque<T>();
	
	//We need this collection Deque list for outside access so maintain same name
	List<List<T>> collection=new ArrayList<>();
	
	Boolean headToTail;
	@NonFinal static Void v;

	private PrintVerticalPathVisitor(boolean ht) {
	  headToTail=ht;
	}
	
	public static <T extends Comparable<T>> PrintVerticalPathVisitor<T> of(boolean ht){
	  return new PrintVerticalPathVisitor<T>(ht);
	}

	@Override public Void visit(BSTNode<T> e) {
		if (headToTail)
			queue.addLast(e.value());
		else
			queue.addFirst(e.value());
		if (e.isBachelor())
			printStack();
		else {
			if (e.hasLeft())
				visit(e.left());
			if (e.hasRight())
				visit(e.right());
		}
		if (headToTail)
			queue.removeLast();
		else
			queue.removeFirst();
		return v;
	}

	private void printStack() {
		collection.add(new ArrayList<>(queue));
		StringBuilder sb = new StringBuilder();
		for (val t : queue)
			sb.append(t).append(" ");
		log.debug(sb.toString());
	}

  @Override
  public List<List<T>> collection() {
    return collection;
  }

}
