package com.brendanmle.reinforcement.learner;

import java.util.List;

public class StateAction<S extends State, A extends Action> {
  private S state;
  private A action;

  public StateAction(S state, A action) {
    this.state = state;
    this.action = action;
  }

  public S getState() {
    return state;
  }

  public A getAction() {
    return action;
  }

  public List<Double> toVector() {
    List<Double> vector = state.toVector();
    vector.addAll(action.toVector());
    return vector;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StateAction)) {
      return false;
    }

    StateAction other = (StateAction) obj;
    return toVector().equals(other.toVector());
  }

  @Override
  public int hashCode() {
    return toVector().hashCode();
  }

  @Override
  public String toString() {
    return state.toString() + ", " + action.toString();
  }
}
