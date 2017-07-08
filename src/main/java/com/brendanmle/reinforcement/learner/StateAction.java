package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface StateAction {
  List<Double> toVector();
}
