package algos.backtracking;

interface BacktrackState<S extends BacktrackState<S>> {

  boolean search(Backtracker<S> backTracker);

  boolean tryReachNextGoal(Backtracker<S> backTracker);

  void print();

  S restoreMemento(S copy);

  S createMemento();

  boolean isGoal();

  void backTrack();

  void markAsPossible();

  boolean isValid();

}