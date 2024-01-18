package algos.backtracking;

import java.util.stream.Stream;

/**
 * A simple abstraction for modeling the backtracking problems
 * @param <S> represents the type of the backtracking state which could be as simple as indices of cell in a matrix
 */
public interface BackTracker<S> {
    /** Check if the stated goal is achieved with this pasded State.*/
    boolean isStateGoal(final S state);

    /** Check if the state is valid with this passed State.*/
    boolean isStateValid(final S state);

    /** Mark the solution as possible for this passed State.*/
    void markSolution(final S state);

    /** Unmark the solution as possible for this passed State.*/
    void unmarkSolution(final S state);

    /** Enumerate the next possible states that can originate from this passed State.*/
    Stream<S> nextPossibleStates(final S state);

    /** Start the search from the passed state and keep probing further as and when the current search result is good.*/
    default boolean search(final S state) {
        if (isStateValid(state)) {
            markSolution(state);
            if (isStateGoal(state) || tryReachNextGoal(state)) {
                return true;
            }
            unmarkSolution(state);
        }
        return false;
    }

    /** Try reach next goal from the given possible enumerated states.*/
    default boolean tryReachNextGoal(final S state) {
        return nextPossibleStates(state).anyMatch(this::search);
    }

}
