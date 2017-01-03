package algos.dp;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;

class LIS2 {
	public List<Integer> findLis(int[] nums) {
		if (ArrayUtils.isEmpty(nums))
			return Collections.emptyList();
		List<Integer> list = new ArrayList<Integer>();
		for (int num : nums) {
			int k = Collections.binarySearch(list, num);
			k = k < 0 ? ~k : k;
			// Add if binary search returns size
			if (k == list.size())
				list.add(k, num);
			else {
				//else simply set the kth index
				list.set(k, num);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[] da = new int[] { 0, 8, 4, 12, 2, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		System.out.println(new LIS2().findLis(da));
		Integer[] daI = new Integer[] { 0, 8, 4, 12, 2, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		System.out.println(new LIS(daI));

	}
}

/**
 * A List based implementation to compute Longest Increasing Subsequence.
 * <p>
 * The addition to this list is sophisticated in terms of binary search to place
 * an element. The element might be replacing an existing element or getting
 * appended to the list based on binary search result.
 * <p>
 * This class also does some thing special in that; while the elements that are
 * added as we know are also linked with back link to easily trace the longest
 * sequence from the tail to head, it is easier to update/adjust the list to
 * reflect only the linked children nodes. Thus this behavior deviates from list
 * contract typically.
 *
 * @author vemurthy
 *
 * @param <E>
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true, of = "internalList")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LIS<E extends Comparable<? super E>> extends AbstractList<E> {
	/** An internal list of type Node<E> */
	List<Node<E>> internalList = new ArrayList<>();

	/**
	 * A constructor that receives argument as Array E[].
	 * 
	 * @param es
	 *            array of element type <E>
	 */
	public LIS(E[] es) {
		super();
		for (E e : es)
			addWithoutReAdjust(e);
		reAdjustListWithLinkedElements();
	}

	/**
	 * A constructor receives argument as an Iterable representing all Iterable
	 * based collections
	 * 
	 * @param iterable
	 */
	public LIS(Iterable<? extends E> iterable) {
		super();
		addAndReAdjust(iterable);
	}

	/**
	 * This method sets those elements in to the list that are traceable by the
	 * back links from the tail.
	 */
	protected void reAdjustListWithLinkedElements() {
		Node<E> node = internalList.get(size() - 1);
		for (int i = size() - 1; i >= 0; i--, node = node.pointer) {
			if (get(i) != node)
				internalList.set(i, node);
		}
	}

	/**
	 * Adds an element to the list either by adding or setting. The return value
	 * will be true if only added.It will be false ; when the element is
	 * actually set to an existing element
	 * 
	 * @param e
	 *            to be added or set
	 * @return true iff added to the end else false
	 */
	protected boolean addWithoutReAdjust(final E e) {
		Node<E> node = new Node<E>(e);
		boolean added = false;
		int i = Collections.binarySearch(this, e);

		int index = i < 0 ? ~i : i;

		if (added = index == size())
			internalList.add(index, node);
		// The back edge will be set here as-is typical of dynamic programming
		node.pointer = index > 0 ? internalList.get(index - 1) : node;
		internalList.set(index, node);

		return added;
	}

	/**
	 * addAndReAdjust will first add to the internal list but additionally
	 * adjusts the linked elements back to list.
	 * 
	 * @param iterable
	 * @return
	 */
	protected boolean addAndReAdjust(Iterable<? extends E> iterable) {
		boolean modified = false;
		for (E e : iterable)
			modified = addWithoutReAdjust(e) || modified;

		reAdjustListWithLinkedElements();
		return modified;
	}

	/**
	 * {@inheritDoc}. This in addition over rides super implementation by adding
	 * the adjustment behavior.
	 */
	public boolean addAll(Collection<? extends E> c) {
		return addAndReAdjust(c);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * In addition this method re-adjusts the linked elements of a longest
	 * sequence into the list.
	 */
	@Override
	public boolean add(E e) {
		boolean added = addWithoutReAdjust(e);
		reAdjustListWithLinkedElements(); // here we call this every add as we
											// dont know when the next add may
											// come
		return added;
	}

	public String toString() {
		return ArrayUtils.toString(internalList);
	}

	@Override
	public void add(int position, E e) {
		internalList.add(position, new Node<E>(e));
	}

	@Override
	public E get(int i) {
		return internalList.get(i).value;
	}

	@Override
	public E set(int index, E element) {
		return internalList.set(index, new Node<E>(element)).value;
	}

	@Override
	public int size() {
		return internalList.size();
	}

	public boolean isEmpty() {
		return internalList.isEmpty();
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {
			final Iterator<Node<E>> internalIter = internalList.iterator();

			@Override
			public boolean hasNext() {
				return internalIter.hasNext();
			}

			@Override
			public E next() {
				return internalIter.next().value;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public ListIterator<E> listIterator() {
		return new ListIterator<E>() {
			final ListIterator<Node<E>> internalIter = internalList.listIterator();

			@Override
			public boolean hasNext() {
				return internalIter.hasNext();
			}

			@Override
			public E next() {
				return internalIter.next().value;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean hasPrevious() {
				return internalIter.hasPrevious();
			}

			@Override
			public E previous() {
				return internalIter.previous().value;
			}

			@Override
			public int nextIndex() {
				return internalIter.nextIndex();
			}

			@Override
			public int previousIndex() {
				return internalIter.previousIndex();
			}

			@Override
			public void set(E e) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void add(E e) {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Data
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@EqualsAndHashCode(callSuper = false, of = "value")
	private static class Node<E extends Comparable<? super E>> implements Comparable<Node<E>> {
		E value;
		@NonFinal
		Node<E> pointer = this;

		public Node(E x) {
			value = x;
		}

		public int compareTo(Node<E> y) {
			return value.compareTo(y.value);
		}

		public String toString() {
			return value.toString();
		}
	}

	public static void main(String[] args) {
		Integer[] da = new Integer[] { 3, 2, 6, 4, 5, 1, 0 };
		log.info(ArrayUtils.toString(da) + ":->" + new LIS(da));

		da = new Integer[] { 0, 8, 4, 12, 2, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };
		log.info(ArrayUtils.toString(da) + ":->" + new LIS(da));

		da = new Integer[] { 7, 2, 8, 1, 3, 4, 10, 6, 9, 5 };
		log.info(ArrayUtils.toString(da) + ":->" + new LIS(da));
	}
}