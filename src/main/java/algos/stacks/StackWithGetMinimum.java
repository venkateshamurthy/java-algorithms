package algos.stacks;

import java.util.Stack;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class StackWithGetMinimum <E extends Comparable<E>>{
	
	Stack<E> original = new Stack<E>();
	Stack<E> minStack = new Stack<E>();
	public void push(E e){
		original.push(e);
		if(minStack.isEmpty()||e.compareTo(minStack.peek())<=0){
			minStack.push(e);
		}
			
		log.debug("PUSH:MinStack:{}",minStack.peek());
	}
	
	public E pop(){
		E popped=original.pop();
		if(popped.equals(minStack.peek()))
			minStack.pop();
		if(!minStack.isEmpty())
			log.debug("POP:MinStack::{}",minStack.peek());
		return popped;
	}
	
	public E peekMin(){
		return minStack.peek();
	}
	public boolean isEmpty(){
		return original.isEmpty();
	}
	
	public static void main(String[] args){
		Integer[] a={8,7,6,5,4,3,2,1};
		StackWithGetMinimum<Integer> stack = new StackWithGetMinimum<Integer>();
		for (Integer i:a)
			stack.push(i);
		while(!stack.isEmpty())
			stack.pop();
	}
}
