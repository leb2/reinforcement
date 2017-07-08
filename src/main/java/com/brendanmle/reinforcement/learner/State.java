package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface State {
  List<Double> toVector();
}
