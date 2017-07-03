package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.StateAction;

import java.util.List;

public class TicTacToeStateAction extends StateAction<TicTacToeState, TicTacToeAction> {

  public TicTacToeStateAction(TicTacToeState state, TicTacToeAction action) {
    super(state, action);
  }

  @Override
  public List<Double> toVector() {
    List<Double> stateVector =  getState().toVector();
    double turn = (double) getState().currentTurn();

    // TODO: Remove
    TicTacToeAction action = getAction();


    stateVector.set(getAction().getRow() * 3 + getAction().getCol(), turn);
    return stateVector;
  }
}
