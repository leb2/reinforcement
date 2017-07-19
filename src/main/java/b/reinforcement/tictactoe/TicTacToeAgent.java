package b.reinforcement.tictactoe;
import b.reinforcement.learner.core.QLearnerAgent;
import b.reinforcement.learner.core.StateAction;
import b.reinforcement.learner.policy.EpsilonGreedyPolicy;
import b.reinforcement.learner.policy.Policy;
import b.reinforcement.learner.policy.SoftmaxPolicy;
import b.reinforcement.learner.valuefunction.NNActionValueFunction;

import java.util.Scanner;

public class TicTacToeAgent extends QLearnerAgent {
  private TicTacToeEnvironment ticTacToeEnvironment;

  public TicTacToeAgent() {
    super(new TicTacToeEnvironment());
    this.setActionValueFunction(new NNActionValueFunction(environment, 0.005));
    ticTacToeEnvironment = (TicTacToeEnvironment) environment;
    ((SoftmaxPolicy) this.policy).setTemperature(0.01);
  }

  public void train(int numIterations) {
    for (int i = 0; i < numIterations; i++) {
      if (i % 1000 == 0) {
        System.out.println(i);
      }
      if (i % 5000 == 0) {
        System.out.printf("Average Reward: %f\n", test(getGreedyPolicy(), 4000));
      }

      environment.resetState();

      if (i % 2 == 0) {
        train();

      } else { // Go second half of the time.
        TicTacToeAction action = (TicTacToeAction) getPolicy().chooseAction(environment);
        ticTacToeEnvironment.move(action.getRow(), action.getCol());

        train();
      }
    }
  }

  public double test(Policy policy, int numIterations) {
    return play(policy, new EpsilonGreedyPolicy(q, 1), numIterations);
  }

  public double play(Policy policy1, Policy policy2, double numIterations) {

    double totalReward = 0;

    ticTacToeEnvironment.setOpponent(policy2);

    for (int i = 0; i < numIterations; i++) {
      environment.resetState();

      if (i % 2 == 0) {
        TicTacToeAction action = (TicTacToeAction) policy2.chooseAction(environment);
        ticTacToeEnvironment.move(action.getRow(), action.getCol());
      }

      while (!environment.inTerminalState()) {
        TicTacToeAction action = (TicTacToeAction) policy1.chooseAction(environment);
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
      environment.resetState();

      // Half of the time go second.
      if (i % 2 == 0) {
        TicTacToeAction action = (TicTacToeAction) getPolicy().chooseAction(environment);
        ticTacToeEnvironment.move(action.getRow(), action.getCol());
      }

      while (!environment.inTerminalState()) {
        System.out.println(environment);
        System.out.println("Best action in your scenario:");
        System.out.println(getGreedyPolicy().chooseAction(environment));

        try {
          int row = scanner.nextInt();
          int col = scanner.nextInt();

          TicTacToeAction action = new TicTacToeAction(row, col);
          StateAction stateAction = environment.getStateAction(action);
          environment.performAction(action);

          System.out.println("Value of this state and your action:");
          System.out.println(q.getValue(stateAction));

        } catch(Exception e) {
          System.out.println("invalid, retrying");
        }
      }

      System.out.println("Game finsihed");
    }
  }
}
