package b.reinforcement.tictactoe;
import b.reinforcement.learner.core.StateAction;

import java.util.List;

public class TicTacToeStateAction implements StateAction {
  private List<Double> vector;

  public TicTacToeStateAction(List<Double> vector) {
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
    if (!(other instanceof TicTacToeStateAction)) {
      return false;
    }
    return toVector().equals(((TicTacToeStateAction) other).toVector());
  }

  @Override
  public int hashCode() {
    return toVector().hashCode();
  }
}
