package com.brendanmle.reinforcement;

import com.brendanmle.reinforcement.tictactoe.*;

public class App {
  public static void main( String[] args ) {
    TicTacToeAgent agent = new TicTacToeAgent();
    agent.train(1000);

    agent.play();
  }
}
