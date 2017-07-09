package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.Action;
import com.brendanmle.reinforcement.learner.Environment;
import com.brendanmle.reinforcement.learner.Policy;
import com.brendanmle.reinforcement.learner.StateAction;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicTacToeEnvironment implements Environment {
  private Policy opponent;
  private int[][] board = new int[3][3];

  public void setOpponent(Policy opponent) {
    this.opponent = opponent;
  }

  public TicTacToeEnvironment(Policy opponent) {
    this();
    resetState();
    setOpponent(opponent);
  }

  public TicTacToeEnvironment() {}

  public void resetState() {
    board = new int[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        board[row][col] = 0;
      }
    }
  }

  @Override
  public StateAction getStateAction(Action a) {
    TicTacToeAction action = (TicTacToeAction) a;

    List<Double> stateVector = new ArrayList<>();
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        stateVector.add((double) board[row][col]);
      }
    }

    double turn = (double) currentTurn();
    stateVector.set(action.getRow() * 3 + action.getCol(), turn);

    // Flip depending on turn to make it easier on neural network to generalize.
    for (int i = 0; i < stateVector.size(); i++) {
      stateVector.set(i, turn * stateVector.get(i));
    }

    return new TicTacToeStateAction(stateVector);
  }


  @Override
  public double performAction(Action a) {
    TicTacToeAction action = (TicTacToeAction) a;
    if (opponent == null) {
      throw new UnsupportedOperationException("Opponent policy not initialized");
    }
    move(action.getRow(), action.getCol());

    // Reward is 1 if you move and win immediately.
    if (inTerminalState()) {
      return gameWon() ? 1 : 0;
    }

    // TODO: add reversed to help a function approximation

    // Reward is -1 if you move and then lose.
    TicTacToeAction opponentMove = (TicTacToeAction) opponent.chooseAction(this);
    move(opponentMove.getRow(), opponentMove.getCol());

    return gameWon() ? -1 : 0;
  }

  public void move(int row, int col) {
    if (board[row][col] != 0) {
      throw new IllegalArgumentException("Cannot move on occupied space");
    }
    board[row][col] = currentTurn();
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

  public boolean inTerminalState() {
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

  private boolean gameWon() {
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

  @Override
  public List<Action> getActions() {
    if (inTerminalState()) {
      return Collections.emptyList();
    }

    List<Action> actions = new ArrayList<>();
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

  @Override
  public int getVectorSize() {
    return 9;
  }
}
