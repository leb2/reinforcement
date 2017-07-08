package com.brendanmle.reinforcement.learner;

public interface ActionValueFunction {

  double getValue(StateAction stateAction);
  void backup(StateAction stateAction, double newValue);
}
