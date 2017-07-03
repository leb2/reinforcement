package com.brendanmle.reinforcement;

import com.brendanmle.reinforcement.learner.QLearnerAgent;
import com.brendanmle.reinforcement.tictactoe.*;

import java.util.Scanner;

public class App {
  public static void main( String[] args ) {
    System.out.println("Hello World!");

    // Needs an opponent policy
    TicTacToeEnvironment environment = new TicTacToeEnvironment();
    TicTacToeStateActionFactory saf = new TicTacToeStateActionFactory();

    QLearnerAgent<TicTacToeState, TicTacToeAction> agent
            = new QLearnerAgent<>(environment, saf);

    environment.setOpponent(agent.getGreedyPolicy());

    for (int i = 0; i < 50000; i++) {
      if (i % 1000 == 0) {
        System.out.println(i);
      }
      environment.setState(TicTacToeEnvironment.emptyState());

      if (i % 2 == 0) {
        agent.train();

      } else { // Go second half of the time.
        TicTacToeAction action = agent.getPolicy().chooseAction(environment.getState());
        environment.move(action.getRow(), action.getCol());

        agent.train();
      }
    }

    Scanner scanner = new Scanner(System.in);

    while (true) {
      environment.setState(TicTacToeEnvironment.emptyState());

      while (!environment.inTerminalState()) {
        TicTacToeState state = environment.getState();
        System.out.println(state);
        System.out.println("Best action in your scenario:");
        System.out.println(agent.getGreedyPolicy().chooseAction(state));

        try {
          int row = scanner.nextInt();
          int col = scanner.nextInt();
          environment.performAction(new TicTacToeAction(row, col));

          System.out.println("Value of this state and your action:");
          System.out.println(agent.getValue(environment.getState(), new TicTacToeAction(row, col)));

        } catch(Exception E) {
          System.out.println("invalid, retrying");
        }
      }

      System.out.println("Game finsihed");
      System.out.println(environment.getState());
    }
  }
}
