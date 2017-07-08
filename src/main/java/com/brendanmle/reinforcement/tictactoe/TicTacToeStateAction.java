package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.StateAction;
import java.util.List;

public class TicTacToeStateAction implements StateAction {
  private List<Double> vector;

  public TicTacToeStateAction(List<Double> vector) {
    this.vector = vector;
  }

  @Override
  public List<Double> toVector() {
    return vector;

//    List<Double> stateVectorExtended = new ArrayList<>();
//    for (Double aStateVector : stateVector) {
//      stateVectorExtended.add(aStateVector == 1 ? 1.0 : 0.0);
//    }
//    for (Double aStateVector : stateVector) {
//      stateVectorExtended.add(aStateVector == -1 ? 1.0 : 0.0);
//    }
//    return stateVectorExtended;
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
