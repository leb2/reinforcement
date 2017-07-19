package b.reinforcement.learner.core;

import java.util.List;

public interface Environment {
  double performAction(Action action);
  StateAction getStateAction(Action action);

  List<Action> getActions();

  int getVectorSize();

  boolean inTerminalState();

  void resetState();
}
