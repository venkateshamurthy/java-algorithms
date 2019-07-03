package algos.backtracking;

import java.util.stream.Stream;

public interface BackTracker<S> {

    boolean isStateGoal(final S state);

    boolean isStateValid(final S state);

    void markSolution(final S state);

    void unmarkSolution(final S state);

    Stream<S> nextPossibleStates(final S state);

    default boolean search(final S state) {
        if (isStateValid(state)){
            markSolution(state);
            if (isStateGoal(state) || tryReachNextGoal(state)) {
                return true;
            }
            unmarkSolution(state);
        }
        return false;
    }

    default boolean tryReachNextGoal(final S state) {
        return nextPossibleStates(state).anyMatch(this::search);
    }

}
