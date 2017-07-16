package b.reinforcement.learner;

import java.util.List;

public class DefaultStateAction implements StateAction {
  private List<Double> vector;
  private List<Double> state;
  private Action action;

  public DefaultStateAction(List<Double> vector, List<Double> state, Action action) {
    this.vector = vector;
    this.action = action;
    this.state = state;
  }

  @Override
  public List<Double> toVector() {
    return vector;
  }

  @Override
  public List<Double> stateVector() {
    return state;
  }

  @Override
  public Action getAction() {
    return action;
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


