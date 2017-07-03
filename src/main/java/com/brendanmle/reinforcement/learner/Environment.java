package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface Environment<S extends State<A>, A extends Action> {
  double performAction(A action);
  S getState();
  void setState(S state);
  boolean inTerminalState();
}
