package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.StateActionFactory;

public class TicTacToeStateActionFactory
        implements StateActionFactory<TicTacToeState, TicTacToeAction>{

  @Override
  public TicTacToeStateAction create(TicTacToeState state, TicTacToeAction action) {
    return new TicTacToeStateAction(state, action);
  }
}
