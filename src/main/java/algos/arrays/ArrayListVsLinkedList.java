package algos.arrays;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ArrayListVsLinkedList {
    private static final int SZ = 10_000_000;
    private static final List<Integer> data = new Random().ints(SZ).boxed().collect(Collectors.toList());

    private static List<Integer> linkedList = new LinkedList<>();
    private static List<Integer> arrayList = new ArrayList<>(SZ);

    private static void testAddBulkToLinkedList() {
        linkedList.clear();
        final long t1 = System.nanoTime();
        linkedList.addAll(data);
        final long t2 = System.nanoTime() - t1;
        log.info("testAddBulkToLinkedList:duration(ns)={}", t2);
    }

    private static void testAddBulkToArrayList() {
        arrayList.clear();
        final long t1 = System.nanoTime();
        arrayList.addAll(data);
        final long t2 = System.nanoTime() - t1;
        log.info("testAddBulkToArrayList:duration(ns)= {}", t2);
    }

    private static void testIterateLinkedList() {
        final long t1 = System.nanoTime();
        int sum = 0;
        for (final int x : linkedList) {
            sum += x;
        }
        final long t2 = System.nanoTime() - t1;
        log.info("testIterateLinkedList:duration(ns)=  {}, sum = {}", t2, sum);
    }

    private static void testIterateArrayList() {
        final long t1 = System.nanoTime();
        int sum = 0;
        for (final int x : arrayList) {
            sum += x;
        }
        final long t2 = System.nanoTime() - t1;
        log.info("testIterateArrayList:duration(ns)=   {}, sum = {}", t2, sum);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            log.info("\n");
            testAddBulkToLinkedList();
            testAddBulkToArrayList();
            testIterateLinkedList();
            testIterateArrayList();
        }
    }
}
