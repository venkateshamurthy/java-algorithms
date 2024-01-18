package algos.lists;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 */
public class MaxHeap<E extends Comparable<E>> extends AbstractHeap<E> {
		
	public MaxHeap(List<E> heap) {
		super(heap,new MAX_COMPARATOR<E>());
	}

	public MaxHeap() {
		this(new ArrayList<E>());
	}
	
}
