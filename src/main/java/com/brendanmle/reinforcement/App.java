package com.brendanmle.reinforcement;

import com.brendanmle.reinforcement.tictactoe.*;

public class App {
  public static void main( String[] args ) {
    TicTacToeAgent agent = new TicTacToeAgent();
    agent.train(120000);
    System.out.printf("Average Reward: %f\n", agent.test(agent.getGreedyPolicy(), 10000));

    agent.playHuman();
  }
}
