package algos.lists;

import java.lang.reflect.Array;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.sf.oval.constraint.NotNull;

/**
 * @author vmurthy
 * 
 */
@Data(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@ToString(of = "value")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Element<T> {
	@NonNull T value;
	@NonFinal Element<T> left = this, right = this;

	public void insert(@NonNull @NotNull Element<T> existing) {
		insertBefore(existing);
	}

	public void insertBefore(@NonNull @NotNull Element<T> current) {
		Element<T> previous = current.left();
		// build right contacts
		previous.right(this);
		this.right(current);
		// build left contacts
		current.left(this);
		this.left(previous);
	}

	public void insertAfter(@NonNull @NotNull Element<T> current) {
		Element<T> next = current.right();
		// build right contacts
		current.right(this);
		this.right(next);
		// build left contacts
		next.left(this);
		this.left(current);

	}

	public void delete() {
		if (left != this && right != this) {
			left.right(right);
			right.left(left);
		} else if (left == this)
			right.left(right);
		else
			left.right(left);
		left = right = this;
	}

	public boolean hasLeft() {
		return left() != this;
	}

	public boolean hasRight() {
		return right() != this;
	}

	public boolean isZombie() {
		return left == this && right == this;
	}

	/**
	 * A method to return an array of elements.
	 * 
	 * @return
	 */
	 public static <T> Element<T>[] arrayOf(T... t) {
		@SuppressWarnings("unchecked") Element<T>[] array = (Element<T>[]) Array
				.newInstance(Element.class, t.length);
		for (int i = 0; i < t.length; i++)
			array[i] = of(t[i]);
		return array;
	}

}
