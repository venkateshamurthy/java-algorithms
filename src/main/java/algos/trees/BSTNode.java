package algos.trees;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Data//(staticConstructor = "of")
@EqualsAndHashCode(callSuper=false, of= {"value"})
@Accessors(fluent = true)
@ToString(of= {"value"})
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class BSTNode<T extends Comparable<T>> extends BaseNode<BSTNode<T>> implements ComparableNode<T>{
  Comparator<T> comparator= (o1, o2) -> o1.compareTo(o2);
  @NonFinal T value;
  public BSTNode(T tValue) {
    super();
    this.value=tValue;
  }
  public void setValue(T value) {
    value(value);
  }
  
  
  public BSTNode<T> left(T value) {
    return super.left=new BSTNode<T>(value);
  }
  public BSTNode<T> right(T value) {
    return super.right=new BSTNode<T>(value);
  }
  
}