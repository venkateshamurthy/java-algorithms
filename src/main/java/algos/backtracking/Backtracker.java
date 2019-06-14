package algos.backtracking;

/**
 * An abstraction to deal with common backtracking flow in a backtracking state machine.
 *
 * @param <S> is a template of type {@link BacktrackState<S>} which is the state machine
 */
public interface Backtracker<S extends BacktrackState<S>> {
    /**
     * Print State and /or solution.
     */
    void print();

    /**
     * Start search backtracking machine and /or solution.
     */
    boolean search();

    /**
     * Search given state by first assessing possibilities of current state and try reach the goal slowly.
     *
     * @param state current state
     * @return true when current state is good.
     */
    default boolean search(S state) {
        if (state.isValid()) {
            state.markAsPossible();
            if (state.isGoal() || state.tryReachNextGoal(this)) {
                return true;
            }
            state.backTrack();
        }
        return false;
    }
}
