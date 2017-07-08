package com.brendanmle.reinforcement.learner;

public interface Environment<S extends State<A>, A extends Action> {
  double performAction(A action);
  S getState();
  void setState(S state);
  boolean inTerminalState();
}
