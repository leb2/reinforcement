package b.reinforcement.learner.core;

import java.util.List;

public interface Action {
  List<Double> toVector();
}
