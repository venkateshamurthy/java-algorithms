package basics;

import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue numbers = new PriorityQueue();
        numbers.add("Red");
        numbers.add("Green");
        System.out.println("numbers:"+numbers);
        numbers.offer("Blue");
        System.out.println("Updated:"+numbers);
        System.out.println("Peeked :"+numbers.peek());
        System.out.println("Polled :"+numbers.poll());
        System.out.println("numbers:"+numbers);
        System.out.println("Polled :"+numbers.poll());

    }

}
