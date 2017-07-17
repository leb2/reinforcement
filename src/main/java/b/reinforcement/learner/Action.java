package b.reinforcement.learner;

import java.util.List;

public interface Action {
  List<Double> toVector();
}
