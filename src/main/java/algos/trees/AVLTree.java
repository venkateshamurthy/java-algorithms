package algos.trees;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.math3.util.FastMath;
@Slf4j
@Data
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PROTECTED)

class  AVLNode<T extends Comparable<T>> extends Element<T> {
    
	int balance;
    
    private AVLNode(T t){
    	super(null, t);
    }
    
    public AVLNode(Comparator<T> comparator, T t) {
    	super(comparator,t);
	}
    
	@SuppressWarnings("unchecked")
	public  AVLNode<T> left(){
		return super.<AVLNode<T>> left();
	}
	
	@SuppressWarnings("unchecked")
	public  AVLNode<T> right(){
		return super.< AVLNode<T>> right();
	}
	
	@SuppressWarnings("unchecked")
	public  AVLNode<T> parent(){
		return super.<AVLNode<T>> parent();
	}
	
	public  AVLNode<T> left(AVLNode<T> t){
		return (AVLNode<T>) super.left(t);
	}	
	
	public  AVLNode<T> right(AVLNode<T> t){
		return (AVLNode<T>) super.right(t);
	}
	
	public  AVLNode<T> parent(AVLNode<T> t){
		return (AVLNode<T>) super.parent(t);
	}
	
	public static class Factory<Y extends Comparable<Y>> extends AbstractElementFactory<Y,AVLNode<Y>>{
		@Override
		public AVLNode<Y> create(Y t) {
			return new AVLNode<Y>(t);
		}
	}
}
@Slf4j
@Data
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class AVLTree{
 
    AVLNode<Integer> root;
    final AVLNode.Factory<Integer> factory = new AVLNode.Factory<Integer>();
    public boolean insert(Integer keyInt) {
    	AVLNode<Integer> key=factory.create(keyInt);
        if (root.isZombie())
            root = key;
        else {
            AVLNode<Integer> n = root;
            AVLNode<Integer> parent;
            while (true) {
                if (n.eq(key))
                    return false;
 
                parent = n;
 
                int cmp=key.compare(n);
                
                n = n.getChild(cmp);
 
                if (n.isZombie()) {
                	parent.setChild(cmp,factory.create(keyInt));
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }
 
    public void delete(Integer delKey) {
        if (root.isZombie())
            return;
        AVLNode<Integer> n = root;
        AVLNode<Integer> parent = root;
        AVLNode<Integer> delAVLNode = null;
        AVLNode<Integer> child = root;
        AVLNode<Integer> delKeyNode=factory.create(delKey);
        while (!child.isZombie()) {
            parent = n;
            n = child;
            child = delKeyNode.ge(n) ? n.right() : n.left();
            
            if (delKeyNode.eq(n))
                delAVLNode = n;
        }
 
        if (!delAVLNode.isZombie()) {
            delAVLNode.value(n.value);
 
            child = n.getOnlyChild();
 
            if (root.eq(delKeyNode)) {
                root(child);
            } else {
                if (parent.left == n) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
                rebalance(parent);
            }
        }
    }
 
    private void rebalance(AVLNode<Integer> n) {
        setBalance(n);
 
        if (n.balance == -2) {
            if (height(n.left().left()) >= height(n.left().right()))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);
 
        } else if (n.balance == 2) {
            if (height(n.right().right()) >= height(n.right().left()))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }
 
        if (n.parent != null) {
            rebalance(n.parent());
        } else {
            root = n;
        }
    }
 
    private AVLNode<Integer> rotateLeft(AVLNode<Integer> a) {
 
        AVLNode<Integer> b = a.right();
        b.parent(a.parent);
 
        a.right(b.left);
 
        if (!a.right.isZombie())
            a.right.parent = a;
 
        b.left = a;
        a.parent = b;
 
        if (!b.parent.isZombie()) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private AVLNode<Integer> rotateRight(AVLNode<Integer> a) {
 
        AVLNode<Integer> b = a.left();
        b.parent = a.parent;
 
        a.left = b.right;
 
        if (a.left != null)
            a.left.parent = a;
 
        b.right = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private AVLNode<Integer> rotateLeftThenRight(AVLNode<Integer> n) {
        n.left = rotateLeft(n.left());
        return rotateRight(n);
    }
 
    private AVLNode<Integer> rotateRightThenLeft(AVLNode<Integer> n) {
        n.right = rotateRight(n.right());
        return rotateLeft(n);
    }
 
    private int height(AVLNode<Integer> n) {
        if (n == null)
            return -1;
        return 1 + FastMath.max(height(n.left()), height(n.right()));
    }
 
    private void setBalance(AVLNode<Integer>... nodes) {
        for (AVLNode<Integer> n : nodes)
            n.balance = height(n.right()) - height(n.left());
    }
 
    public void printBalance() {
        printBalance(root);
    }
 
    private void printBalance(AVLNode<Integer> n) {
        if (n != null) {
            printBalance(n.left());
            System.out.printf("%s ", n.balance);
            printBalance(n.right());
        }
    }
 
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
 
        log.info("Inserting values 1 to 10");
        for (int i = 1; i < 10; i++)
            tree.insert(i);
 
        System.out.print("Printing balance: ");
        tree.printBalance();
    }
}