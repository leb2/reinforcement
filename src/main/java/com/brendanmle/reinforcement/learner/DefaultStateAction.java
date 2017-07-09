package com.brendanmle.reinforcement.learner;

import java.util.List;

public class DefaultStateAction implements StateAction {
  private List<Double> vector;

  public DefaultStateAction(List<Double> vector) {
    this.vector = vector;
  }

  @Override
  public List<Double> toVector() {
    return vector;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof DefaultStateAction)) {
      return false;
    }
    return toVector().equals(((DefaultStateAction) other).toVector());
  }

  @Override
  public int hashCode() {
    return toVector().hashCode();
  }
}


