package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.Policy;
import com.brendanmle.reinforcement.learner.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TicTacToeState implements State<TicTacToeAction> {
  private Policy opponent;
  private int[][] board = new int[3][3];

  TicTacToeState(int[][] board) {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        this.board[row][col] = board[row][col];
      }
    }
  }

  public int currentTurn() {
    int totalX = 0;
    int totalO = 0;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (board[row][col] == 1) {
          totalX += 1;
        } else if (board[row][col] == -1) {
          totalO += 1;
        }
      }
    }
    return totalX > totalO ? -1 : 1;
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

  public boolean isTerminalState() {
    boolean allSpacesFilled = true;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (board[row][col] == 0) {
          allSpacesFilled = false;
        }
      }
    }
    return allSpacesFilled || gameWon();
  }

  boolean gameWon() {
    for (int i = 0; i < 3; i++) {
      if (board[i][0] == board[i][1]
              && board[i][1] == board[i][2]
              && board[i][0] != 0) {
        return true;
      }
      if (board[0][i] == board[1][i]
              && board[1][i] == board[2][i]
              && board[0][i] != 0) {
        return true;
      }
    }
    if (board[0][0] == board[1][1]
            && board[1][1] == board[2][2]
            && board[0][0] != 0) {
      return true;
    }
    if (board[0][2] == board[1][1]
            && board[1][1] == board[2][0]
            && board[0][2] != 0) {
      return true;
    }

    return false;
  }

  TicTacToeState reversed() {
    int[][] reversedBoard = new int[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        reversedBoard[row][col] = -1 * board[row][col];
      }
    }

    return new TicTacToeState(reversedBoard);
  }

  public int getAtPosition(int row, int col) {
    return board[row][col];
  }

  @Override
  public List<TicTacToeAction> getActions() {
    if (isTerminalState()) {
      return Collections.emptyList();
    }

    List<TicTacToeAction> actions = new ArrayList<>();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (board[row][col] == 0) {
          actions.add(new TicTacToeAction(row, col));
        }
      }
    }
    return actions;
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

  @Override
  public String toString() {
    StringBuilder boardString = new StringBuilder();

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (board[row][col] == 1) {
          boardString.append("[X]");
        } else if (board[row][col] == -1) {
          boardString.append("[O]");
        } else {
          boardString.append("[ ]");
        }
      }
      boardString.append("\n");
    }
    return boardString.toString();
  }
}
