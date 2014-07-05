/**
 * 
 */
package algos.trees.visitors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import algos.trees.Element;
import algos.trees.Tree;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MirroringVisitor<T extends Comparable<T>> implements
        Visitor<T, Void, Void> {
    public static <Y extends Comparable<Y>> MirroringVisitor<Y> of() {
        return new MirroringVisitor<Y>();
    }

    static final Logger log = LogManager
            .getLogger(StringFormatterMessageFactory.INSTANCE);
    @NonFinal
    static Void v;

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#visit(algos.trees.Tree)
     */
    @Override
    public Void visit(Tree<T> t) {
        visit(t.root());
        return v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#visit(algos.trees.Element)
     */
    @Override
    public Void visit(Element<T> e) {
        if (!e.isBachelor()) {
            e.swapChildrenPosition();
            if (e.hasLeft())
                visit(e.left());
            if (e.hasRight())
                visit(e.right());
        }
        return v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
     */
    @Override
    public Void doSomethingOnElement(Element<T> e) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see algos.trees.Visitor#collection()
     */
    @Override
    public Void collection() {
        return v;
    }

}
