package b.reinforcement.learner.core;

import java.util.List;

public interface StateAction {
  List<Double> toVector();
}
