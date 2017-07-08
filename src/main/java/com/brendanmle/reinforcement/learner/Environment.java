package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface Environment {
  double performAction(Action action);
  State getState();
  StateAction getStateAction(Action action);

  List<Action> getActions();

  int getVectorSize();
  boolean inTerminalState();

  void resetState();
}
