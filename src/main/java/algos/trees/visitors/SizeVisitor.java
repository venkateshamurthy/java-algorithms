/**
 * 
 */
package algos.trees.visitors;

import java.util.concurrent.atomic.AtomicInteger;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Slf4j
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeVisitor<T extends Comparable<T>> implements BSTVisitor<T, Integer, Integer> {
    @Getter
    AtomicInteger runningSize = new AtomicInteger(0);

    @Override
    public Integer visit(BSTNode<T> e) {
        log.debug(e.value() + " s=" + runningSize.get());
        runningSize.incrementAndGet();
        if (e.hasLeft()) visit(e.left());
        if (e.hasRight()) visit(e.right());
        return runningSize.get();
    }

    @Override
    public Integer collection() {
        return runningSize.get();
    }
}
