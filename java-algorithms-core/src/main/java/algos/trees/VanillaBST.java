package algos.trees;

import lombok.Data;
import lombok.val;
import lombok.experimental.NonFinal;

@Data
public class VanillaBST<T extends Comparable<T>> implements Tree<T, BSTNode<T>>{
  
  NodeFactory<T, BSTNode<T>> factory;
  @NonFinal BSTNode<T> root;
  
  public VanillaBST() {
    this.factory=o->new BSTNode<>(o);
  }
  public VanillaBST(NodeFactory<T, BSTNode<T>> factory) {
    this.factory=factory;
  }
  public VanillaBST(T rootValue) {
    this();
    root=factory.create(rootValue);
  }

  @Override
  public BSTNode<T> root() {
    return root;
  }

  @Override
  public BSTNode<T> add(T value) {
    val temp= add(root, factory.create(value));
    if(root==null || root.isZombie()) {
      root=temp;
      size.incrementAndGet();
    }
    return temp;
  }
  
  public BSTNode<T> remove(T value) {
    return remove(factory.create(value));
  }
  
  @Override
  public void clear() {
    size.set(0);
    root.becomeZombie();
  }

  @Override
  public BSTNode<T> contains(T value) {
    return contains(root, factory.create(value));
  }

  @Override
  public void addAll(T[] t) {
    addAll(t,0,t.length-1);
  }
  

  @Override
  public boolean validate(T min, T max) {
    return validate(root, factory.create(min), factory.create(max));
  }
  
  
  public boolean equals(Object object) {
    if(object!=null && object instanceof VanillaBST) {
      Tree<T, BSTNode<T>> that=(Tree<T, BSTNode<T>>)object;
      return sameNodes(root, that.root());
    }
    return false;
  }
}