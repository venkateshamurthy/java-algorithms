package algos.trees;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.val;
import lombok.experimental.NonFinal;

/**
 * Red black tree
 */
class RBT<T extends Comparable<T>> implements Tree<T, RedBlack<T>> {

  AtomicInteger size=new AtomicInteger(0);
  NodeFactory<T, RedBlack<T>> factory;
  @NonFinal RedBlack<T> root;
  public int size() {
    return size.get();
  }
  
  public RBT(T rootValue) {
    this.factory=v->new RedBlack<>(v);
    root=factory.create(rootValue);
  }
  public RBT(NodeFactory<T, RedBlack<T>> factory) {
    this.factory=factory;
  }

  protected RedBlack<T> remove(RedBlack<T> h, T tValue) {
    RedBlack<T> t = factory.create(tValue);
    if (t.lt(h)) {
      h = moveRedLeft(h);
      h.left(remove(h.left(), tValue));
    } else {
      if (h.left().isRed())
        h = rotateRight(h);
      if (t.eq(h) && h.right() == h)
        return null;
      h = moveRedRight(h);
      if (t.eq(h)) {
        h = contains(h.right(), h.right().minimum());
        h = h.right().minimum();
        h.right(deleteMin(h.right()));
      } else
        h.right(remove(h.right(), tValue));
    }
    return fixUp(h);
  }

  public void deleteMin() {
    deleteMin(root);
    root.color(RedBlack.BLACK);
  }

  private RedBlack<T> deleteMin(RedBlack<T> h) {
    if (h.left() == null)
      return null;

    h = moveRedLeft(h);
    h.left(deleteMin(h.left()));
    return fixUp(h);
  }

  private RedBlack<T> fixUp(RedBlack<T> h) {
    if (h.right().isRed())
      h = rotateLeft(h);

    if (h.left().isRed() && h.left().left().isRed())
      h = rotateRight(h);

    if (h.left().isRed() && h.right().isRed())
      h.colorFlip();

    return h;// setN(h)
  }

  /**
   * Move Red Left
   * 
   * <pre>
   *                                                                                  Rz                 Rz
   *                                                                                 / \                / \
   *         R(h)               B(h)                 B(h)                           /   \              /   \
   *        / \    Flip color  / \      RRotate(x)  / \      Left Rotate(h)        /     \            /     \
   *       B   x(C)   ===>    R   x(!C) ========>  R   Rz    ===============>    Bh       x(!C) ==>  Rh      x(!C)
   *      /   / \            /   / \              /   / \                        / \     / \        / \     / \  
   *     B   Rz  y          B   Rz  y            B  <Rz  x(!C)                  R  <Rz >Rz         B  <Bz >Rz
   *        / \                / \                      / \                    /                  /
   *     <Rz  >Rz           <Rz  >Rz                 >Rz   y                  B                  B
   * 
   * 
   * </pre>
   * 
   * @param h
   * @return
   */
  private RedBlack<T> moveRedLeft(RedBlack<T> h) {
    if (h.isRed() && h.left().isBlack() && h.left().left().isBlack()) {
      h.colorFlip();
      val x = h.right();
      if (x.left().isRed()) {
        h.right(rotateRight(x));
        h = rotateLeft(h);
        h.colorFlip();
      }
    }
    return h;
  }

  /**
   * Move Red Right
   * 
   * <pre>
   * 
   * 
   *         R(h)               B(h)                !Cx                             
   *        / \    Flip color  / \      RRotate(h)  / \      
   *      Cx   B   ========>!Cx   R     ========>  R   B(h)     
   *      / \ / \            /   / \                  / \                      
   *     R       y          R   Bz  y            B       R                     
   *                           / \                      / \                  
   *                        <z   >z                        y
   * 
   * 
   * </pre>
   * 
   * @param h
   * @return
   */
  private RedBlack<T> moveRedRight(RedBlack<T> h) {
    if (h.isRed() && h.right().isBlack() && h.right().left().isBlack()) {
      h.colorFlip();
      if (h.left().left().isRed()) {
        h = rotateRight(h);
        h.colorFlip();
      }
    }
    return h;
  }

  protected RedBlack<T> recursiveAdd(RedBlack<T> e, RedBlack<T> t) {
    if (e.left().isRed() && e.right().isRed())
      e.colorFlip();
    add(e, t);
    if (e.right().isRed() && !e.left().isRed())
      e = rotateLeft(e);
    if (e.left().isRed() && e.left().left().isRed())
      e = rotateRight(e);

    return t;
  }

  /**
   * Rotate left of a
   * 
   * <pre>
   * 
   *     p                   p  
   *     |                   |
   *     a                   b
   *    / \     =====>      / \ 
   *  a)   b               a   (b
   *      / \             / \
   * (a,b)   (b         a)  (a,b)
   * 
   * </pre>
   * <p>
   * The illustration is about rotating a left around b. the round braces
   * a) indicate range excluding a and (b indicates everything beyond b.
   * The (a,b) indicates everything between a and b excluding a and b
   * itself.
   * 
   * <pre>
   * So first take     b = a.right;
   * Next    set a.right = b.left
   * Next    set b.left  = a (Apply the color)
   * Next    set     b.p = a.p
   * Next            a.p = b
   * </pre>
   * 
   * @param a
   * @return b
   */

  protected RedBlack<T> rotateLeft(RedBlack<T> a) {
    val b = a.right();
    a.right(b.left());
    b.left(a);
    b.color(a.color());
    a.color(RedBlack.RED);
    b.parent(a.parent());
    if (a.isLeftChildOfItsParent())
      a.parent().left(b);
    else
      a.parent().right(b);
    a.parent(b);
    return b;
  }

  /**
   * Rotate left of a
   * 
   * <pre>
   * 
   *      p              p       
   *      |              |     
   *      b              a     
   *     / \   ====>    / \     
   *    a   (b        a)   b   
   *   / \                / \  
   * a)   (a,b)      (a,b)   (b
   * 
   * </pre>
   * <p>
   * The illustration is about rotating a left around b. the round braces
   * a) indicate range excluding a and (b indicates everything beyond b.
   * The (a,b) indicates everything between a and b excluding a and b
   * itself.
   * 
   * <pre>
   * So first take     a = b.left;
   * Next    set  b.left = a.right;
   * Next    set  a.right= b (Apply the color)
   * Next    set     a.p = b.p
   * Next            b.p = a
   * </pre>
   * 
   * @param b
   * @return a
   */

  protected RedBlack<T> rotateRight(RedBlack<T> b) {
    val a = b.left();
    b.left(a.right());
    a.right(b);
    a.color(b.color());
    b.color(RedBlack.RED);
    a.parent(b.parent());
    if (b.isLeftChildOfItsParent())
      b.parent().left(a);
    else
      b.parent().right(a);
    b.parent(a);
    return a;
  }

  @Override
  public RedBlack<T> root() {
    return root;
  }

  @Override
  public RedBlack<T> add(T value) {
    return add(root,factory.create(value));
  }

  @Override
  public RedBlack<T> remove(T toBeReturned) {
    // TODO Auto-generated method stub
    return remove(root,toBeReturned);
  }


  @Override
  public void clear() {
    // TODO Auto-generated method stub
    size.set(0);
    root.becomeZombie();
    
  }

  @Override
  public RedBlack<T> contains(T value) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addAll(T[] t) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean validate(T min, T max) {
    // TODO Auto-generated method stub
    return false;
  }

}