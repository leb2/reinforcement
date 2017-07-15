package com.brendanmle.reinforcement.dominion;

import com.brendanmle.reinforcement.learner.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


// TODO: Make this a generic class
public class DominionAgent extends QLearnerAgent {
  private int testAmount = 100;
  private int testInterval = 5000;

  public DominionAgent() {
    super(new DominionEnvironment(3));
    this.setActionValueFunction(new NNActionValueFunction(environment, 0.001));
    setEpsilon(0.2);
  }

  public void setTestAmount(int testAmount) {
    this.testAmount = testAmount;
  }

  public void setTestInterval(int testInterval) {
    this.testInterval = testInterval;
  }

  public void train(int numIterations) {
    double best = 0;
    ((NNActionValueFunction) q).load("last.mw");

    for (int i = 0; i < numIterations; i++) {

      if ((i + 1) % 5000 == 0) {
        ((NNActionValueFunction) q).save("last.mw");
      }
      if ((i + 1) % 500 == 0) {
        System.out.println(i + 1);
      }
      if ((i + 1) % testInterval == 0) {
        double average = test(getGreedyPolicy(), testAmount);

        try {
          Writer output = new BufferedWriter(new FileWriter("scores.csv", true));
          output.append(average + "\n");
          output.close();

        } catch (IOException e) {
          e.printStackTrace();
        }

        System.out.printf("Average Reward: %f\n", average);
        if (average > 30 && average > best) {
          ((NNActionValueFunction) q).save("dominion.mw");
          best = average;
        }
      }

      environment.resetState();
      ((DominionEnvironment) environment).setOpponent(new BigMoneyPolicy());
      train();
//      System.out.println("---");
//      for (Player player : ((DominionEnvironment) environment).getPlayers()) {
//        System.out.println(player.totalPoints());
//      }
//      System.out.println("---");
    }
  }

  public double test(Policy policy, int numIterations) {
    // Test against random policy
    return play(policy, new BigMoneyPolicy(), numIterations);
  }

  public double play(Policy policy1, Policy policy2, double numIterations) {
    // TODO: debug
    boolean debug = policy1 instanceof HumanPolicy;
    int numWon = 0;
    double totalReward = 0;
    double totalScore = 0;
    ((DominionEnvironment) environment).setOpponent(policy2);

    for (int i = 0; i < numIterations; i++) {
      environment.resetState();

      while (!environment.inTerminalState()) {
        if (debug) {
          System.out.println(environment.getActions());
          System.out.println("Best action: " + policy2.chooseAction(environment));
          System.out.println("Best action value: "
                  + q.getValue(environment.getStateAction(policy2.chooseAction(environment))));
        }

        Action action = policy1.chooseAction(environment);

        if (debug) {
          System.out.println(environment.getStateAction(action).toVector());
        }

        double reward = environment.performAction(action);
        if (debug) {
          System.out.println("Reward for performing action: " + reward);
        }

        totalReward += reward;
      }
      totalScore += ((DominionEnvironment) environment).playerOnePoints();
      boolean won = ((DominionEnvironment) environment).debug(i, totalReward);
      if (won) {
        numWon += 1;
      }
    }

    System.out.println("proportion won: " + numWon / numIterations);
    System.out.println("Average score: " + totalScore / numIterations);
    return totalReward / numIterations;
  }

  public void play(String target) {
    NNActionValueFunction opponentQ = new NNActionValueFunction(environment, 0);
    opponentQ.load(target);

    play(new HumanPolicy(), new EpsilonGreedyPolicy(opponentQ, 0), 999);
  }
}
