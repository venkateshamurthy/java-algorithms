/**
 * 
 */
package algos.trees.visitors;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.math3.util.FastMath;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HeightVisitor<T extends Comparable<T>> implements
        BSTVisitor<T, Integer, Integer> {

    @Getter
    AtomicInteger runningHeight = new AtomicInteger(0);

    @Override
    public Integer visit(BSTNode<T> e) {

        log.debug(e.value() + " s=" + runningHeight.get());
        return runningHeight.addAndGet(1 + FastMath.max(
                e.hasLeft() ? visit(e.left()) : 0,
                e.hasRight() ? visit(e.right()) : 0));
    }

    @Override
    public Integer collection() {
        return runningHeight.get();
    }
}
