/**
 * 
 */
package algos.trees.visitors;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MirroringVisitor<T extends Comparable<T>> implements BSTVisitor<T, Void, Void> {
    public static <Y extends Comparable<Y>> MirroringVisitor<Y> of() {
        return new MirroringVisitor<Y>();
    }

    @NonFinal
    static Void v;

    @Override
    public Void visit(BSTNode<T> e) {
        if (!e.isBachelor()) {
            e.swapChildrenPosition();
            if (e.hasLeft())
                visit(e.left());
            if (e.hasRight())
                visit(e.right());
        }
        return v;
    }

    @Override
    public Void collection() {
        return v;
    }

}
