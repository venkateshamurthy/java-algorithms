package algos.trees;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.Pair;

import algos.trees.visitors.Visitor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

public interface Tree<T extends Comparable<T>, N extends BaseNode<N> & ComparableNode<T>> {
  AtomicInteger size=new AtomicInteger(0);
  public N root();

  public N add(T value);
  
  public N remove(T toBeReturned);

  boolean validate(T min, T max);

  public void clear();

  public N contains(T value);
  
  default N remove(N toBeReturned) {
    N t = contains(toBeReturned.value());
    if (t == null)  return null;
    if (t.isBachelor()) t.becomeZombie();
    else {
      N successor = t.hasBoth() ? t.successor() : t;
      t.setValue(successor.value());
      successor.attachTheOnlyChildToItsGranny();
      if (successor.isBachelor() && successor != t) successor.becomeZombie();
    }
    size.decrementAndGet();
    return toBeReturned;
  }

  default int height() {
    return height(root());
  }
  
  default int height(N e) {
    return 1 + Math.max(e.hasLeft() ? height(e.left()) : 0, e.hasRight() ? height(e.right()) : 0);
  }
  
  default boolean sameNodes(N thisNode, N thatNode) {
    boolean result = Objects.equals(thisNode.value(), thatNode.value());
    boolean leftResult= result &&
        (!thisNode.hasLeft() && !thatNode.hasLeft() ||
        thisNode.hasLeft() && thatNode.hasLeft() && sameNodes(thisNode.left,thatNode.left));
    boolean rightResult= result  && 
        (!thisNode.hasRight() && !thatNode.hasRight() ||
        thisNode.hasRight() && thatNode.hasRight() && sameNodes(thisNode.right,thatNode.right));
    return result = result && leftResult && rightResult;
  }

  default N contains(N e, N t){
    if (e == null)
      return null;
    int cmp = t.compare(e);
    if (cmp == 0)
      return e;
    else if (cmp < 0 && e.hasLeft())
      return contains(e.left(), t);
    else if (cmp > 0 && e.hasRight())
      return contains(e.right(), t);
    else
      return null;
  }

  default int size() {
    return size.get();//size(root())
  }
  
  default int size(N e) {
    return 1 + (e.hasLeft() ? size(e.left()) : 0) + (e.hasRight() ? size(e.right()) : 0);
  }

  public void addAll(T[] t);
  
  default N addAll(T[] t, int start, int end) {
    N temp = null;
    if (start <= end) {
      int mid = start + (end - start) / 2;
      temp = add(t[mid]);
      N branch = addAll(t, start, mid - 1);
      temp.left = branch == null ? temp : branch;
      branch = addAll(t, mid + 1, end);
      temp.right = branch == null ? temp : branch;
    }
    return temp;
  }
  
  default void accept(Visitor<T, N, ?, ?> visitor) {
    visitor.visit(this);
  }

  default boolean validate(N e, N MIN, N MAX) {
    boolean result = true;
    if (result && e.hasLeft())
      result = e.left().gt(MIN)  && validate(e.left(), MIN, e);
    if (result && e.hasRight())
      result = e.right().lt(MAX) && validate(e.right(), e, MAX);
    return result;
  }
  
  default N add(N e, N t){
    if (e != null) {
      int cmp = 0;
      cmp = t.compare(e);
      if (cmp > 0 && e.hasRight())
        return add(e.right(), t);
      else if (cmp < 0 && e.hasLeft())
        return add(e.left(), t);
      e.setChild(cmp, t);
      t.parent(e);
      size.incrementAndGet();
    }
    return t;
  }

}

@Slf4j
class IterativeBinSearchTree<T extends Comparable<T>> implements Tree<T, BSTNode<T>> {

  BSTNode<T> root;
  
  IterativeBinSearchTree(){}

  IterativeBinSearchTree(T rootValue) {
    root = new BSTNode<>(rootValue);
  }

  @Override
  public BSTNode<T> root() {
    return root;
  }

  @Override
  public BSTNode<T> add(final T value) {
    
    ArrayUtils.nullToEmpty(new Object[0]);
    final BSTNode<T> e = new BSTNode(value);
    if(root==null) {
      return root = e;
    }
    BSTNode<T> temp, x = root;
    int cmp = 0;
    do {
      temp = x; //store it before u navigate
      cmp = e.compare(x);
      if (cmp > 0 && x.hasRight())     x = x.right();
      else if (cmp < 0 && x.hasLeft()) x = x.left();
    } while (cmp != 0 && x != temp);
    x.setChild(cmp, e);
    return e;
  }

  @Override
  public BSTNode<T> remove(T value) {
    throw new UnsupportedOperationException("remove operation is not implemented");
  }

  @Override
  public int height() {
    BSTNode<T> NULL=(null),x=null;
    int height=0;
    Queue<BSTNode<T>> q = new LinkedList<BSTNode<T>>();
    q.add(root);
    q.add(NULL);
    while(!q.isEmpty()) {
      if((x=q.remove())==NULL) {//means u reached the end of alevel
         if(!q.isEmpty()) q.add(NULL); //once u ensure some nodes u add a NULL for the next level end
         height++;
      } else {
        if(x.hasRight())  q.add(x.right());
        if(x.hasLeft())   q.add(x.left());
      }
    }
    return height;
  }

  @Override
  public void clear() {
    size.set(0);
    root.becomeZombie();
  }

  @Override
  public BSTNode<T> contains(T value) {
    final BSTNode<T> e = new BSTNode(value);
    BSTNode<T> temp, x = root;
    int cmp=Integer.MIN_VALUE;
    do {
      temp = x; //save before right/left navigation starts
      cmp = x.compare(e);
      if (cmp>0  && x.hasRight())     x = x.right();
      else if (cmp<0 && x.hasLeft())  x = x.left();
    } while (cmp!=0 && temp!=x);
    return cmp==0 && temp == x ? x : null;
  }

  @Override
  public int size() {
    int size=0;
    Queue<BSTNode<T>> q = new LinkedList<BSTNode<T>>();
    q.add(root);
    while(!q.isEmpty()) {
      BSTNode<T> x=q.poll();
      size++;
      if(x.hasLeft())  q.add(x.left());
      if(x.hasRight()) q.add(x.right());
    }
    return size;
  }
  
  public void addAll(T[] t) {
    addAll(t,0,t.length-1);
  }

  @Override
  public BSTNode<T> addAll(T[] t, int start, int end) {
    Arrays.sort(t);
    Queue<Pair<Integer,Integer>> q = new LinkedList<>();
    q.add(Pair.create(start,end)); //the pair represent the two end indexes whose center is the next element
    while(!q.isEmpty()) {
      Pair<Integer,Integer> indices = q.poll();
      if(indices.getFirst()<=indices.getSecond()) {
        int middle = indices.getFirst()+(indices.getSecond()-indices.getFirst())/2;
        this.add(t[middle]);
        q.add(Pair.create(indices.getFirst(), middle-1));
        q.add(Pair.create(middle+1, indices.getSecond()));
      }
    }
    log.info("height;{} size:{} toStrong={} root children: {} {}",
        height(),size(),toString(),root.left(),root.right());
    return root;
  }
  
  public String toString() {
    Queue<BSTNode<T>> q = new LinkedList<>();
    BSTNode<T> NULL = null;
    StringBuilder sb = new StringBuilder("\n");
    q.add(root);
    q.add(NULL);
    while(!q.isEmpty()) {
      BSTNode<T> e = q.poll();
      if(e!=NULL) {
        sb.append(e.value()).append("(").append(e.left().value())
          .append(",").append(e.right().value()).append(") ");
        if(e.hasLeft())q.add(e.left());
        if(e.hasRight())q.add(e.right());
      } else if(q.peek()!=NULL) {
        q.add(NULL);
        sb.append("\n");
      }
    }
    return sb.toString();
  }
  

  public static void main(String[] args){
    IterativeBinSearchTree<Integer> tree = new IterativeBinSearchTree<>();
    Integer[] array =new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
    tree.addAll(array,0,array.length-1);
  }
  
  @Data class VNode{final BSTNode<T> current,min,max; boolean valid() {return current.lt(max) && current.gt(min);}}
  @Override
  public boolean validate(T min, T max) {
    
    BSTNode<T> node = root, MIN = new BSTNode<>(min), MAX=new BSTNode<>(max);
    boolean result=true;
    Queue<VNode> q=new LinkedList<VNode>();
    q.add(new VNode(root,MIN,MAX));
    
    while(!q.isEmpty() && (result=result&&q.poll().valid())) {
      if(node.hasLeft()) {
        q.add(new VNode(node.left(),MIN,MAX=node));
      }
      if(node.hasRight()) {
        q.add(new VNode(node.right(),MIN=node,MAX));
      }
    }
    return result;
  }
}