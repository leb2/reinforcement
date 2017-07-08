package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.Policy;
import com.brendanmle.reinforcement.learner.State;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeState implements State {
  private Policy opponent;
  private int[][] board = new int[3][3];

  TicTacToeState(int[][] board) {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        this.board[row][col] = board[row][col];
      }
    }
  }

  @Override
  public List<Double> toVector() {
    List<Double> vector = new ArrayList<>();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        vector.add((double) board[row][col]);
      }
    }
    return vector;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof TicTacToeState)) {
      return false;
    }
    TicTacToeState otherState = (TicTacToeState) other;
    return toVector().equals(otherState.toVector());
  }

  @Override
  public int hashCode() {
    return toVector().hashCode();
  }

}
