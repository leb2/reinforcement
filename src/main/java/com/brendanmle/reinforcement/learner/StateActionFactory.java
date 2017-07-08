package com.brendanmle.reinforcement.learner;

public interface StateActionFactory {

  StateAction create(State state, Action action);

  // To help neural network determine input and output sizes
  int getVectorSize();
}
