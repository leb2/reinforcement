package b.reinforcement.learner;

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
  public List<Double> stateVector() {
    return null; // TODO: Remove
  }

  @Override
  public Action getAction() {
    return null; // TODO: Remove
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


