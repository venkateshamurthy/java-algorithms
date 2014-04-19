package com.github.venkateshamurthy.algorithms.trees;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;

@Data(staticConstructor = "of")
@EqualsAndHashCode()
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BST<T extends Comparable<T>> implements Tree<T> {
	Element<T> root = null;
	final AtomicInteger treeSize = new AtomicInteger();

	@Override
	public void accept(Visitor<T, ?, ?> visitor) {
		visitor.visit(this);
	}

	@Override
	public Element<T> root() {
		return root;
	}

	@Override
	public Element<T> add(T data) {
		treeSize.incrementAndGet();
		return root.add(Element.of(data));
	}

	@Override
	public Element<T> remove(T data) {
		Element<T>removed=root.remove(Element.of(data));
		if (removed!=null)
			treeSize.decrementAndGet();
		return removed;
	}

	@Override
	public Element<T> get(T data) {
		return root.find(Element.of(data));
	}

	@Override
	public boolean contains(T data) {
		return get(data) != null;
	}

	@Override
	public int height() {
		return root.height();
	}

	@Override
	public int size() {
		return treeSize.get();
	}

	@Override
	public void clear() {
		root.becomeZombie();
	}

	@Override
	public boolean validate(T min, T max) {
		return root.validate(min, max);
	}

}
