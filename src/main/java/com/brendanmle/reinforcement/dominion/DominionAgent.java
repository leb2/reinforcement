package com.brendanmle.reinforcement.dominion;

import com.brendanmle.reinforcement.learner.*;

// TODO: Make this a generic class
public class DominionAgent extends QLearnerAgent {
  private int testAmount = 100;
  private int testInterval = 5000;

  public DominionAgent() {
    super(new DominionEnvironment(3));
    this.setActionValueFunction(new NNActionValueFunction(environment, 0.005));
  }

  public void setTestAmount(int testAmount) {
    this.testAmount = testAmount;
  }

  public void setTestInterval(int testInterval) {
    this.testInterval = testInterval;
  }

  public void train(int numIterations) {

    ((DominionEnvironment) environment).setOpponent(getGreedyPolicy());
    for (int i = 0; i < numIterations; i++) {
      if (i % 100 == 0) {
        System.out.println(i);
      }
      if (i % testInterval == 0) {
        System.out.println();
        System.out.printf("Average Reward: %f\n", test(getGreedyPolicy(), testAmount));
      }

      environment.resetState();
      train();
    }
  }

  public double test(Policy policy, int numIterations) {
    // Test against random policy
    return play(policy, new EpsilonGreedyPolicy(q, 1), numIterations);
  }

  public double play(Policy policy1, Policy policy2, double numIterations) {
    // TODO: debug
    boolean debug = policy1 instanceof HumanPolicy;

    double totalReward = 0;
    ((DominionEnvironment) environment).setOpponent(policy2);

    for (int i = 0; i < numIterations; i++) {
      environment.resetState();

      while (!environment.inTerminalState()) {
        if (debug) {
          System.out.println(environment.getActions());
          System.out.println("Best action: " + policy2.chooseAction(environment));
          System.out.println("Best action value: "
                  + q.getValue(environment.getStateAction(policy.chooseAction(environment))));
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
    }

    return totalReward / numIterations;
  }
}
