package b.reinforcement.learner;

import java.util.List;

public interface StateAction {
  List<Double> toVector();
  List<Double> stateVector();
  Action getAction();
}
