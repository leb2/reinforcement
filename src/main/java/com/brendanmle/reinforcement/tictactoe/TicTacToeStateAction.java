package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.StateAction;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeStateAction extends StateAction<TicTacToeState, TicTacToeAction> {

  public TicTacToeStateAction(TicTacToeState state, TicTacToeAction action) {
    super(state, action);
  }

  @Override
  public List<Double> toVector() {
    List<Double> stateVector =  getState().toVector();
    double turn = (double) getState().currentTurn();

    stateVector.set(getAction().getRow() * 3 + getAction().getCol(), turn);

    // Flip depending on turn to make it easier on neural network to generalize.
    for (int i = 0; i < stateVector.size(); i++) {
      stateVector.set(i, turn * stateVector.get(i));
    }

    return stateVector;

//    List<Double> stateVectorExtended = new ArrayList<>();
//    for (Double aStateVector : stateVector) {
//      stateVectorExtended.add(aStateVector == 1 ? 1.0 : 0.0);
//    }
//    for (Double aStateVector : stateVector) {
//      stateVectorExtended.add(aStateVector == -1 ? 1.0 : 0.0);
//    }
//    return stateVectorExtended;
  }
}
