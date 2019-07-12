package algos.trees.visitors;

import algos.trees.BSTNode;

public interface BSTVisitor<T extends Comparable<T>, R, C> extends Visitor<T, BSTNode<T>,R,C>{
  
}