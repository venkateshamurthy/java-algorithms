package algos.backtracking;

/** An abstraction for holding the mutable state of a backtracking flow. This is self ereferencing*/
interface BacktrackState<S extends BacktrackState<S>> {

  /**
   * Search this state using {@link Backtracker}
   * @param backTracker an instance of {@link Backtracker Backtracker&lt;S&gt;}
   * @param <S>
   * @return true if search succeeds
   */
  boolean search(Backtracker<S> backTracker);

  /**
   * Try the next possible goal in backtracking problem. This might check all the untried
   * children states in DFS manner.
   * @param backTracker an instance of {@link Backtracker Backtracker&lt;S&gt;}
   * @return true if tryReachNextGoal succeeds
   */
  boolean tryReachNextGoal(Backtracker<S> backTracker);

  /** Print method.*/
  void print();

  /** A memento restorer when the state variables could have muted that needs to be restored with a
   * passed in memento.
   * 
   * @param memento to be restored
   * @return this
   */
  S restoreMemento(S memento);

  /**
   * Create snapshot/memento.
   *
   * @return
   */
  S createMemento();

  /**
   * Is this state achieved the final goal.
   * 
   * @return true if goal achieved
   */
  boolean isGoal();

  /** Backtrack your steps in this state. */
  void backTrack();

  /** Mark this state as possible before starting. */
  void markAsPossible();

  /** Is this state valid or coming to this state leads to avalid situation.*/
  boolean isValid();

}