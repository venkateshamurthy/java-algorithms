package com.github.venkateshamurthy.algorithms.trees;

public interface Visitor<T extends Comparable<T>, R, C> {
	R visit(Visitable<T> visitable);
	C collection();
}
