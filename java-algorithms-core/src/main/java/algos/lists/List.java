/**
 * 
 */
package algos.lists;

/**
 * @author vmurthy
 * 
 */
public interface List<T> extends Iterable<T> {
	void insert(int index, T t);

	void add(T t);

	T delete(int index);

	boolean delete(T t);

	void clear();

	int size();

	boolean isEmpty();

	boolean contains(T t);

	int indexOf(T t);

	T get(int index);

	T set(int index, T t);
}
