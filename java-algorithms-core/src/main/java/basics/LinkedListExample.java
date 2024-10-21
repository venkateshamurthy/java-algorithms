package basics;

import java.util.*;
public class LinkedListExample {
public static void main(String [] args) {
    LinkedList link = new LinkedList();
    link.addFirst("January");
    link.addFirst("February");
    link.addFirst("March");
    link.addFirst("April");
    link.addLast("May");
    link.addLast("June");
    System.out.println("Elements:"+link);
    System.out.println("First element:"+link.getFirst());
    System.out.println("Last element:"+link.getLast());
    System.out.println("Remove First:"+link.removeFirst());
    for (Iterator i = link.listIterator(2);i.hasNext();) {
        System.out.println(i.next());
    }

}

}

