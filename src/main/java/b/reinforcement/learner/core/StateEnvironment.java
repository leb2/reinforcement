package b.reinforcement.learner.core;

import java.util.List;

public interface StateEnvironment extends Environment {
  int getOutputSize();
  List<Double> stateVector();
}
