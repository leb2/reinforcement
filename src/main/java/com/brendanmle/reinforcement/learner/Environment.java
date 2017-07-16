package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface Environment {
  double performAction(Action action);
  StateAction getStateAction(Action action);

  List<Action> getActions();

  int getVectorSize();
  int getStateSize();
  int getOutputSize();

  boolean inTerminalState();
  List<Double> getState();

  void resetState();
}
