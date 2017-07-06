package com.brendanmle.reinforcement.tictactoe;

import com.brendanmle.reinforcement.learner.*;

import java.util.Scanner;

public class TicTacToeAgent extends QLearnerAgent<TicTacToeState, TicTacToeAction> {
  private TicTacToeEnvironment ticTacToeEnvironment = new TicTacToeEnvironment();

  public TicTacToeAgent() {
    super(new TicTacToeEnvironment(),
            new NNActionValueFunction<>(new TicTacToeStateActionFactory(), 0.005));
    ticTacToeEnvironment = (TicTacToeEnvironment) environment;

    ticTacToeEnvironment.setOpponent(getGreedyPolicy());
  }

  public void train(int numIterations) {
    for (int i = 0; i < numIterations; i++) {
      if (i % 5000 == 0) {
        System.out.println(i);
      }
      if (i % 20000 == 0) {
        System.out.printf("Average Reward: %f\n", test(getGreedyPolicy(), 4000));
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

  public double test(Policy<TicTacToeState, TicTacToeAction> policy, int numIterations) {
    return play(policy, new EpsilonGreedyPolicy<>(q, 1), numIterations);
  }

  private double play(Policy<TicTacToeState, TicTacToeAction> policy1,
                    Policy<TicTacToeState, TicTacToeAction> policy2,
                     double numIterations) {

    double totalReward = 0;

    ticTacToeEnvironment.setOpponent(policy2);
    for (int i = 0; i < numIterations; i++) {
      environment.setState(TicTacToeEnvironment.emptyState());

      if (i % 2 == 0) {
        TicTacToeAction action = policy2.chooseAction(environment.getState());
        ticTacToeEnvironment.move(action.getRow(), action.getCol());
      }

      while (!environment.inTerminalState()) {
        TicTacToeAction action = policy1.chooseAction(environment.getState());
        totalReward += environment.performAction(action);
      }
    }

    return totalReward / numIterations;
  }

  public void playHuman() {
    Scanner scanner = new Scanner(System.in);
    ticTacToeEnvironment.setOpponent(getGreedyPolicy());

    int i = 0;
    while (true) {
      i += 1;
      environment.setState(TicTacToeEnvironment.emptyState());

      // Half of the time go second.
      if (i % 2 == 0) {
        TicTacToeAction action = getPolicy().chooseAction(environment.getState());
        ticTacToeEnvironment.move(action.getRow(), action.getCol());
      }

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
