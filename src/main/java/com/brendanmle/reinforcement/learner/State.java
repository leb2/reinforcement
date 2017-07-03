package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface State<A extends Action> {
  List<A> getActions();
  List<Double> toVector();
  boolean isTerminalState();
}
