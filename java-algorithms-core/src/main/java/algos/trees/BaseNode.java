package algos.trees;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseNode<N extends BaseNode<N>> {
  @SuppressWarnings("unchecked")
  N here=(N)this;
  @NonFinal @NonNull N parent = here, left = here, right = here;
  public void swapChildrenPosition()       { N temp=left; left=right; right=temp;}
  public boolean isZombie()                { return here==parent && here==left && left==right; }
  public boolean hasParent()               { return parent!=here; }
  public boolean isParent()                { return left != here || right != here; }
  public boolean hasLeft()                 { return left != here;}
  public boolean isBachelor()              { return left == here && right == here;}
  public boolean isLeaf()                  { return isBachelor();}
  public boolean hasRight()                { return right != here;}
  public boolean hasBoth()                 { return left  != here && right != here;}
  public boolean isLeftChildOfItsParent()  { return (parent != here) && parent.left  == here;}
  public boolean isRightChildOfItsParent() { return (parent != here) && parent.right == here; }
  public N getOnlyChild()                  { return (left != here ? left : right);}
  public N getChild(int cmp)               { return  cmp==0 ? here : (cmp < 0 ? left : right); }

  public void becomeZombie() {
    
    if(hasLeft()) {
      N leftHead=left;
      leftHead.parent(leftHead);
    }
    if(hasRight()) {
      N rightHead=right;
      rightHead.parent(rightHead);
    }
    if (isLeftChildOfItsParent())
      parent.left(parent);
    else
      parent.right(parent);
    this.parent = this.left = this.right = here;
  }
  
  public  N setChild(int cmp, N child) {
    if (cmp != 0) {
      val wing= cmp < 0 ? left(child) : right(child);
      child.parent(here);
    }
    return  here;
  }
  
  public  N minimum() {
    N node =  here;
    while (node.hasLeft())   node = node.left();
    return node;
  }
  
  public  N maximum() {
    N node =  here;
    while (node.hasRight())  node = node.right();
    return node;
  }

  public N successor() {
    if (hasRight())  return right.minimum();

    N node =  here;
    while (node.isRightChildOfItsParent()) node = node.parent();
    return node.parent();
  }

  public  N predecessor() {
    if (hasLeft())   return left.maximum();

    N node =  here;
    while (node.isLeftChildOfItsParent()) node = node.parent();
    return node.parent();
  }
  
  public void attachTheOnlyChildToItsGranny() {
    if (isBachelor())                return;
    if (isLeftChildOfItsParent())    parent.left(getOnlyChild());
    else                             parent.right(getOnlyChild());
    getOnlyChild().parent(parent);
  }
}

@Data//(staticConstructor = "of")
@Accessors(fluent = true, chain=true)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
class RedBlack<T extends Comparable<T>> extends BaseNode<RedBlack<T>> implements ComparableNode<T>{
  public static final Boolean RED = true, BLACK = false;
  @NonFinal Boolean color=RED;
  Comparator<T> comparator = Comparable::compareTo;
  @NonFinal T value;
  
  RedBlack(T value){
    super();
    this.value=value;
  }
  public boolean isRed()     {return parent!=here && color==RED;}
  public boolean isBlack()   {return parent!=here && color==BLACK;}
  public boolean colorFlip() {left.colorFlip();right.colorFlip();return colorFlip();}

  public void setValue(T value) {
   this.value(value);
  }
}

@Data
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal=true)
class  AVLNode<T extends Comparable<T>> extends BaseNode<AVLNode<T>> implements ComparableNode<T> {
  Comparator<T> comparator=(o1,o2)->o1.compareTo(o2);
  @NonFinal T value;
  @NonFinal int balance;
  protected AVLNode(T t) {
    super();
    value = t;
  }
  public void setValue(T value) {
    this.value=value;
   }
}

