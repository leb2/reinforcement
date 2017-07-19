package b.reinforcement.learner.core;

import java.util.List;

public interface Environment {
  double performAction(Action action);
  int getVectorSize();
  boolean inTerminalState();
  void resetState();
  List<Double> getStateAction(Action action);
  List<Action> getActions();
}
