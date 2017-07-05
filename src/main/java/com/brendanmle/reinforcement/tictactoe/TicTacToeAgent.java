package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.TableActionValueFunction;
import com.brendanmle.reinforcement.learner.EpsilonGreedyPolicy;
import com.brendanmle.reinforcement.learner.QLearnerAgent;

import java.util.Scanner;

public class TicTacToeAgent extends QLearnerAgent<TicTacToeState, TicTacToeAction> {
  private TicTacToeEnvironment ticTacToeEnvironment = new TicTacToeEnvironment();

  public TicTacToeAgent() {
    super();
    q = new TableActionValueFunction<>(new TicTacToeStateActionFactory());
    environment = ticTacToeEnvironment;
    policy = new EpsilonGreedyPolicy<>(q, 1);
    trainedPolicy = new EpsilonGreedyPolicy<>(q, 0);

    // Needs an opponent policy
    TicTacToeStateActionFactory saf = new TicTacToeStateActionFactory();

    ticTacToeEnvironment.setOpponent(getGreedyPolicy());
  }

  public void train(int numIterations) {
    for (int i = 0; i < numIterations; i++) {
      if (i % 1000 == 0) {
        System.out.println(i);
      }
      environment.setState(TicTacToeEnvironment.emptyState());

      if (i % 2 == 0) {
        train();

      } else { // Go second half of the time.
        TicTacToeAction action = getPolicy().chooseAction(environment.getState());
        TicTacToeEnvironment tEnvironment = (TicTacToeEnvironment) environment;
        tEnvironment.move(action.getRow(), action.getCol());

        train();
      }
    }
  }

  public void play() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      environment.setState(TicTacToeEnvironment.emptyState());

      while (!environment.inTerminalState()) {
        TicTacToeState state = environment.getState();
        System.out.println(state);
        System.out.println("Best action in your scenario:");
        System.out.println(getGreedyPolicy().chooseAction(state));

        try {
          int row = scanner.nextInt();
          int col = scanner.nextInt();
          environment.performAction(new TicTacToeAction(row, col));

          System.out.println("Value of this state and your action:");
          System.out.println(getValue(environment.getState(), new TicTacToeAction(row, col)));

        } catch(Exception E) {
          System.out.println("invalid, retrying");
        }
      }

      System.out.println("Game finsihed");
      System.out.println(environment.getState());
    }
  }
}
