//package com.brendanmle.reinforcement.tictactoe;
//
//import com.brendanmle.reinforcement.learner.Action;
//import com.google.common.collect.ImmutableList;
//
//import java.util.List;
//
//public class TicTacToeAction implements Action {
//  private final int row;
//  private final int col;
//
//  public TicTacToeAction(int row, int col) {
//    this.row = row;
//    this.col = col;
//  }
//
//  public int getRow() {
//    return row;
//  }
//
//  public int getCol() {
//    return col;
//  }
//
//  @Override
//  public List<Double> toVector() {
//    return ImmutableList.of((double) row, (double) col);
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (obj == this) {
//      return true;
//    }
//    if (!(obj instanceof TicTacToeAction)) {
//      return false;
//    }
//    TicTacToeAction other = (TicTacToeAction) obj;
//    return this.row == other.row && this.col == other.col;
//  }
//
//  @Override
//  public String toString() {
//    return String.format("<Action %d, %d>", row, col);
//  }
//}
