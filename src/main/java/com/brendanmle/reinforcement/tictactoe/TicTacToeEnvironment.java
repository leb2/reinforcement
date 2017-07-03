package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.Environment;
import com.brendanmle.reinforcement.learner.Policy;

public class TicTacToeEnvironment implements Environment<TicTacToeState, TicTacToeAction> {
  private Policy<TicTacToeState, TicTacToeAction> opponent;
  private int[][] board = new int[3][3];

  public void setOpponent(Policy<TicTacToeState, TicTacToeAction> opponent) {
    this.opponent = opponent;
  }

  private int currentTurn() {
    return getState().currentTurn();
  }

  public static TicTacToeState emptyState() {
    int[][] board = new int[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        board[row][col] = 0;
      }
    }
    return new TicTacToeState(board);
  }

  @Override
  public void setState(TicTacToeState state) {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        board[row][col] = state.getAtPosition(row, col);
      }
    }
  }

  @Override
  public double performAction(TicTacToeAction action) {
    if (opponent == null) {
      throw new UnsupportedOperationException("Opponent policy not initialized");
    }
    move(action.getRow(), action.getCol());

    // Reward is 1 if you move and win immediately.
    if (inTerminalState()) {
      return getState().gameWon() ? 1 : 0;
    }

    // TODO: add reversed to help a function approximation

    // Reward is -1 if you move and then lose.
    TicTacToeAction opponentMove = opponent.chooseAction(getState());
    move(opponentMove.getRow(), opponentMove.getCol());

    return getState().gameWon() ? -1 : 0;
  }

  public void move(int row, int col) {
    if (board[row][col] != 0) {
      throw new IllegalArgumentException("Cannot move on occupied space");
    }
    board[row][col] = currentTurn();
  }

  @Override
  public TicTacToeState getState() {
    return new TicTacToeState(board);
  }

  @Override
  public boolean inTerminalState() {
    return getState().isTerminalState();
  }

}
