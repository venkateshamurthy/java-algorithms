package com.github.venkateshamurthy.algorithms.trees;

public interface Visitable<T extends Comparable<T>> {
	void accept(Visitor<T,?,?> visitor);
}
