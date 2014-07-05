/**
 * 
 */
package algos.trees.visitors;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.springframework.util.Assert;

import algos.trees.Element;
import algos.trees.Tree;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeVisitor<T extends Comparable<T>> implements
        Visitor<T, Integer, Integer> {
    public static <Y extends Comparable<Y>> SizeVisitor<Y> of(Tree<Y> tree) {
        return new SizeVisitor<Y>(tree);
    }

    static final Logger log = LogManager
            .getLogger(StringFormatterMessageFactory.INSTANCE);

    @Getter
    AtomicInteger runningSize = new AtomicInteger(0);


    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#visit(algos.trees.Tree)
     */
    @Override
    public Integer visit(Tree<T> t) {
        // TODO Auto-generated method stub
        return visit(t.root());
    }

    Tree<T> tree;

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#visit(algos.trees.Element)
     */
    @Override
    public Integer visit(Element<T> e) {

        log.debug(e.value() + " s=" + runningSize.get());
        return runningSize.addAndGet(1 + (e.hasLeft() ? visit(e.left()) : 0) +
                (e.hasRight() ? visit(e.right()) : 0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
     */
    @Override
    public Integer doSomethingOnElement(Element<T> e) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#getCollection()
     */
    @Override
    public Integer collection() {
        return runningSize.get();
    }
}
