package algos.sort;

import java.util.*;
import java.util.stream.Collectors;

public class SortLists {
    //@ToString(of="value", includeFieldNames = false)
    static class Data {
        final int index;
        final int ptr;
        final int value;

        Data(int index, int ptr, int value) {
            this.index = index;
            this.ptr = ptr;
            this.value = value;
        }

        public static Data of(int i, int j, Integer k) {
            return new Data(i, j, k);
        }

        public String toString() {
            return value + "";
        }

        public int value() {
            return value;
        }
    }

    static ArrayList<Integer> sort(ArrayList<ArrayList<Integer>> lists) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        PriorityQueue<Data> heap = new PriorityQueue<>(Comparator.comparingInt(Data::value));
        for (int i = 0; i < lists.size(); i++) {
            if (!lists.get(i).isEmpty()) {
                heap.add(Data.of(i, 0, lists.get(i).get(0)));
            }
        }
        while (!heap.isEmpty()) {
            Data dmin = heap.poll();
            result.add(dmin.value);
            ArrayList<Integer> listToProbe = lists.get(dmin.index);
            if (dmin.ptr < listToProbe.size() - 1) {
                Data newData = Data.of(dmin.index, dmin.ptr + 1, listToProbe.get(dmin.ptr + 1));
                heap.add(newData);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Random r = new Random();
        /*
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        lists.add((ArrayList) r.ints(20, 30, 39)
                .boxed().distinct().sorted().collect(Collectors.toList()));
        lists.add((ArrayList) r.ints(20, 40, 49)
                .boxed().distinct().sorted().collect(Collectors.toList()));
        lists.add((ArrayList) r.ints(20, 10, 19)
                .boxed().distinct().sorted().collect(Collectors.toList()));
        lists.add((ArrayList) r.ints(20, 20, 29)
                .boxed().distinct().sorted().collect(Collectors.toList()));
        System.out.println(lists);
        var result = sort(lists);
        System.out.println(result);
*/
        ArrayList<LinkedListNode> lists = new ArrayList<>();
        lists.add(r.ints(20, 320, 3900)
                .boxed().distinct().sorted(Comparator.comparingInt(Integer::intValue).reversed()).map(LinkedListNode::new).reduce((a,b)->{b.next=a; return b;}).get());
        lists.add(r.ints(20, 45, 490)
                .boxed().distinct().sorted(Comparator.comparingInt(Integer::intValue).reversed()).map(LinkedListNode::new).reduce((a,b)->{b.next=a; return b;}).get());
        lists.add(r.ints(20, 100, 19000)
                .boxed().distinct().sorted(Comparator.comparingInt(Integer::intValue).reversed()).map(LinkedListNode::new).reduce((a,b)->{b.next=a; return b;}).get());
        lists.add(r.ints(20, 23, 290)
                .boxed().distinct().sorted(Comparator.comparingInt(Integer::intValue).reversed()).map(LinkedListNode::new).reduce((a,b)->{b.next=a; return b;}).get());
        System.out.println(lists);
        var result = merge_k_lists(lists);
        System.out.println(result);
    }

    static class LinkedListNode {
        Integer value;
        LinkedListNode next;

        LinkedListNode(Integer value) {
            this.value = value;
            this.next = null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            LinkedListNode p = this;
            do {
                sb.append(p.value).append(",");
            } while ((p = p.next) != null);
            return sb.toString();
        }
    }

    static class Node implements Comparable<Node>{
        final int value;
        final LinkedListNode lln;

        Node(int value, LinkedListNode lln) {
            this.value = value;
            this.lln = lln;
        }

        public int compareTo(Node o) {
            return Integer.compare(value, o.value);
        }
    }

    static LinkedListNode merge_k_lists(ArrayList<LinkedListNode> lists) {
        LinkedListNode result = new LinkedListNode(Integer.MIN_VALUE), tail = result;
        PriorityQueue<Node> q = new PriorityQueue<>();
        lists.stream().filter(Objects::nonNull).map(lln -> new Node(lln.value, lln)).forEach(q::add);
        while (!q.isEmpty()) {
            Node nMin = q.poll();
            LinkedListNode currentLLN = nMin.lln;
            LinkedListNode nextLLN  = nMin.lln.next;
            tail.next = currentLLN;
            tail = tail.next;
            tail.next=null;
            if (nextLLN != null) {
                q.add(new Node(nextLLN.value, nextLLN));
            }
        }
        return result.next;
    }

}
