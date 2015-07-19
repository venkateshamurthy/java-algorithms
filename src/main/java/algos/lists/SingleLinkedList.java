package algos.lists;

import java.lang.reflect.Array;
import java.util.Iterator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.sf.oval.constraint.NotNull;

public class SingleLinkedList<E> implements List<E> {

	transient SingleLinkedElement<?> head = SingleLinkedElement.of("");

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(int index, E t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(E t) {
		// TODO Auto-generated method stub

	}

	@Override
	public E delete(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(E t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(E t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int indexOf(E t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(int index, E t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author vmurthy
	 * 
	 */
	@Data(staticConstructor = "of")
	@Accessors(fluent = true)
	@EqualsAndHashCode(callSuper = false)
	@ToString(of = "value")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	private static class SingleLinkedElement<T> {
		@NonNull
		T value;
		@NonFinal
		SingleLinkedElement<T> right = this;

		public void insert(@NonNull @NotNull SingleLinkedElement<T> existing) {
			insertAfter(existing);
		}

		public void insertAfter(@NonNull @NotNull SingleLinkedElement<T> current) {
			SingleLinkedElement<T> next = current.right();
			// build right contacts
			current.right(this);
			this.right(next);
		}

		public void delete() {

			right = this;
		}

		public boolean hasRight() {
			return right() != this;
		}

		public boolean isZombie() {
			return right == this;
		}

		public  SingleLinkedElement reverse(final SingleLinkedElement<T> head) {
			SingleLinkedElement tail = null;
			for (SingleLinkedElement current = head; current != null;) {
				SingleLinkedElement next = current.right();
				current.right = tail;
				tail = current; //advance tail to current
				current = next; //advance current to next
			}
			return tail;
		}
	}
}