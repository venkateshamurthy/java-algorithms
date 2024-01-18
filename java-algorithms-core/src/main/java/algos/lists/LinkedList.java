/**
 * 
 */
package algos.lists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 * 
 */
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LinkedList<T> implements List<T> {
	//static final Logger log = LogManager.getLogger(StringFormatterMessageFactory.INSTANCE);
	@SuppressWarnings("rawtypes") Element sentinel = Element.of("sentinel");
	AtomicInteger counter = new AtomicInteger(0);
	AtomicInteger modCount = new AtomicInteger(0);
	@Getter @Setter @NonFinal Element<T> last = sentinel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new Iter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#insert(int, java.lang.Object)
	 */
	@Override public void insert(int index, T t) {
		navigateTill(index).insert(Element.of(t));
		counter.incrementAndGet();
		modCount.getAndIncrement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#add(java.lang.Object)
	 */
	@Override public void add(T t) {
		Element<T> newLastElement = Element.of(t);
		newLastElement.insertAfter(last);
		counter.incrementAndGet();
		modCount.getAndIncrement();
		last = newLastElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#delete(int)
	 */
	@Override public T delete(int index) {
		Element<T> e = navigateTill(index);
		if (e == last)
			last = e.left();
		e.delete();
		counter.decrementAndGet();
		modCount.getAndIncrement();
		return e.value();
	}
	
	public Element<T> elementAt(int index) {
		return navigateTill(index);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#delete(java.lang.Object)
	 */
	@Override public boolean delete(T t) {
		for (Element<T> e = sentinel.right(); e != null && e.hasRight(); e = e
				.right()) {
			if (e.value().equals(t)) {
				if (e == last)
					last = e.left();
				e.delete();
				counter.decrementAndGet();
				modCount.getAndIncrement();
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#clear()
	 */
	@Override public void clear() {
		for (Element<?> e = sentinel.right(); e != null && e.hasRight(); e = e
				.right()) {
			e.delete();
			counter.decrementAndGet();
			modCount.getAndIncrement();
			;
		}
		last = sentinel;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#size()
	 */
	@Override public int size() {
		return counter.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#isEmpty()
	 */
	@Override public boolean isEmpty() {
		return counter.get() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#contains(java.lang.Object)
	 */
	@Override public boolean contains(T t) {
		return indexOf(t) != -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#indexOf(java.lang.Object)
	 */
	@Override public int indexOf(T t) {
		int index = -1;
		boolean found = false;
		if (sentinel == last)
			return -1;
		for (Element<T> e = sentinel; !found && e.right() != sentinel; e = e
				.right(), index++)
			found = e.right().value().equals(t);
		return found ? index : -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#get(int)
	 */
	@Override public T get(int index) {
		Element<T> e = navigateTill(index);
		if(e==sentinel)
			throw new IndexOutOfBoundsException("Invalid Index out of bounds:"+index+" current size:"+size());
		return e.value();
	}

	/**
	 * @param index
	 * @return
	 */
	private Element<T> navigateTill(int index) {
		if (index < 0 || index > counter.get())
			throw new IndexOutOfBoundsException();
		int i = -1;
		Element<T> e = sentinel;
		while (e.right() != sentinel && i < index) {
			e = e.right();
			i++;
		}
		if (i > index)
			throw new IllegalStateException("Attempting for index =" + index
					+ " but stopped at i=" + i);
		return e;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.lists.List#set(int, java.lang.Object)
	 */
	@Override public T set(int index, T t) {
		Element<T> e = navigateTill(index);
		T oldT = e.value();
		if (!oldT.equals(t)) {
			Element<T> eNew = Element.of(t), l = e.left();
			eNew.insertAfter(l);
			if (e == last)
				last = e.left();
			e.delete();
			modCount.getAndIncrement();
		}
		return oldT;
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	private class Iter implements Iterator<T> {

		Element<T> e = sentinel.right();
		AtomicInteger expectedModCount = new AtomicInteger(modCount.get());
		
		private void checkCoModification() throws ConcurrentModificationException{
			if(!expectedModCount.equals(modCount))
				throw new ConcurrentModificationException();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override public boolean hasNext() {
			checkCoModification();
			return e.hasRight();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override public T next() {
			checkCoModification();
			if (e.hasRight())
				return e.right().value();
			else
				throw new NoSuchElementException();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override public void remove() {
			checkCoModification();
				e.delete();
		}

	}
	

}
