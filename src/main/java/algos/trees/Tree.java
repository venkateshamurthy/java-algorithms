package algos.trees;

import algos.trees.visitors.Visitor;

public interface Tree<T extends Comparable<T>> {

	public Element<T> root();

	public Element<T> add(T value);

	public Element<T> remove(T value);

	public int height();

	public void clear();

	public Element<T> contains(T value);

	public int size();

	public void addAll(T[] t, int start, int end);

	/**
	 * It doesnt matter for visit result type and collection type and hence
	 * bounds are opened to ?
	 * 
	 * @param visitor an instance of visitor
	 */
	public void accept(Visitor<T, ?, ?> visitor);

	/**
	 * @param min
	 * @param max
	 * @return
	 */
	boolean validate(T min, T max);

}
