package b.reinforcement.dominion;

import b.reinforcement.learner.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


// TODO: Make this a generic class
public class DominionAgent extends QLearnerAgent {
  private int testAmount = 1000;
  private int testInterval = 1000;
  private String loadFile = null;
  private double learningRate = 0.002;
  private ActionValueFunction q;

  public DominionAgent() {
    super(new DominionEnvironment(3));
    this.q = new NNActionValueFunction(environment, learningRate);
    this.setActionValueFunction(q);
    setEpsilon(0.2);
  }

  public void setLearningRate(double learningRate) {
    this.learningRate = learningRate;
    q.setLearningRate(learningRate);
  }

  public void setTestAmount(int testAmount) {
    this.testAmount = testAmount;
  }

  public void setTestInterval(int testInterval) {
    this.testInterval = testInterval;
  }

  public void setLoadFile(String loadFile) {
    this.loadFile = loadFile;
  }

  public void train(int numIterations) {
    double best = -999;
    if (loadFile != null && !loadFile.equals("")) {
      ((NNActionValueFunction) q).load(loadFile);
    }

    for (int i = 0; i < numIterations; i++) {
      if ((i + 1) % 1000 == 0) {
        System.out.println(i + 1);
      }
      if ((i + 1) % testInterval == 0) {
        double average = test(getGreedyPolicy(), testAmount);
        ((NNActionValueFunction) q).save("last.mw");

        System.out.printf("Average Reward: %f\n", average);
        if (average > best) {
          ((NNActionValueFunction) q).save("dominion.mw");
          best = average;
        }
      }

      environment.resetState();
      ((DominionEnvironment) environment).setOpponent(new BigMoneyPolicy());
      train();
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

    if (!debug) {
      try {
        Writer output = new BufferedWriter(new FileWriter("rewards.csv", true));
        output.append(totalReward / numIterations + "\n");
        output.close();

        Writer output2 = new BufferedWriter(new FileWriter("winRate.csv", true));
        output2.append(numWon / numIterations + "\n");
        output2.close();

        Writer output3 = new BufferedWriter(new FileWriter("score.csv", true));
        output3.append(totalScore / numIterations + "\n");
        output3.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return totalReward / numIterations;
  }

  public void play(String target) {
    NNActionValueFunction opponentQ = new NNActionValueFunction(environment, 0);
    opponentQ.load(target);

    play(new HumanPolicy(), new EpsilonGreedyPolicy(opponentQ, 0), 999);
  }

  public void test(String target) {
    NNActionValueFunction playerQ = new NNActionValueFunction(environment, 0);
    playerQ.load(target);

    test(new EpsilonGreedyPolicy(playerQ, 0), 10000);
  }

}
