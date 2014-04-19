package com.github.venkateshamurthy.algorithms.trees;

public interface Tree<T extends Comparable<T>> extends Visitable<T>{
	Element<T> root();

	Element<T> add(T data);

	Element<T> remove(T data);

	Element<T> get(T data);

	boolean contains(T data);

	int height();

	int size();

	void clear();

	boolean validate(T min, T max);
}
