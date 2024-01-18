package algos.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 */
//@Slf4j
public class MinHeap<E extends Comparable<E>> extends AbstractHeap<E> {

  public MinHeap() {
    super(new ArrayList<E>(), new MIN_COMPARATOR<E>());
  }

  public MinHeap(final Collection<E> heap) {
    this();
    addAll(heap);
  }

  public MinHeap(final Collection<E> heap, Comparator<E> comparator) {
    super(new ArrayList<E>(), comparator);
    addAll(heap);
  }

  /**
   * A testing program
   */
  public static void main(String[] args) {
    PriorityQueue<Employee> pq = new PriorityQueue<>(10, new MIN_COMPARATOR<Employee>());
    Heap<Employee> heap = new MinHeap<>();
    for (int i = 10; i > 0; i -= 2) {
      heap.add(new Employee("" + (i - 1), i - 1));
      heap.add(new Employee("" + (i), i));
      pq.add(new Employee("" + (i - 1), i - 1));
      pq.add(new Employee("" + (i), i));
    }
    // heap.build();
    //log.info("Java Priority Que:{}", pq);
    //log.info("{}", heap);

    while (!heap.isEmpty() && !pq.isEmpty()) {
      Employee heapEmployee, pqEmployee;
      //log.info("{} {}", heapEmployee = heap.poll(), pqEmployee = pq.poll());
      //Assert.isTrue(heapEmployee != null && heapEmployee.equals(pqEmployee),
      //    "Employees are different! heapEmployee=" + heapEmployee + " pqEmployee=" + pqEmployee);
    }
    Assert.isTrue(heap.isEmpty() && pq.isEmpty());
    java.util.List<Employee> list = new ArrayList<>();
    for (int i = 10; i > 0; i -= 2) {
      list.add(new Employee("" + (i - 1), i - 1));
      list.add(new Employee("" + (i), i));
    }
    heap.addAll(list);
    pq.addAll(list);
    // heap.build();
    //log.info("Java Priority Que:{}", pq);
    //log.info("{}", heap);
    // Change Key
    int index = 2;
    //log.info("Testing change key @index=" + index);
    heap.changeKey(index, new Employee("" + 3, 0));
    pq.remove(new Employee("" + 3, 1));
    pq.add(new Employee("" + 3, 0));
    Employee heapEmployee, pqEmployee;
    //log.info("Heap.Min={}, Java PQ.Min={}", heapEmployee = heap.poll(), pqEmployee = pq.poll());
    //Assert.isTrue(heapEmployee != null && heapEmployee.equals(pqEmployee),
     //   "Employees are different! heapEmployee=" + heapEmployee + " pqEmployee=" + pqEmployee);
    //log.info("Heap.Min={}, Java PQ.Min={}", heapEmployee = heap.poll(), pqEmployee = pq.poll());
    //Assert.isTrue(heapEmployee != null && heapEmployee.equals(pqEmployee),
    //    "Employees are different! heapEmployee=" + heapEmployee + " pqEmployee=" + pqEmployee);

    heap.changeKey(1, new Employee("" + 4, -1));
    pq.remove(new Employee("" + 4, 4));
    pq.add(new Employee("" + 4, -1));
    //log.info("Heap.Min={}, Java PQ.Min={}", heapEmployee = heap.poll(), pqEmployee = pq.poll());
    //Assert.isTrue(heapEmployee != null && heapEmployee.equals(pqEmployee),
     //   "Employees are different! heapEmployee=" + heapEmployee + " pqEmployee=" + pqEmployee);
  }
}

@Data
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Employee implements Comparable<Employee> {
  String name;
  Integer salary;

  @Override
  public int compareTo(Employee other) {
    return salary.compareTo(other.salary);
  }
}
