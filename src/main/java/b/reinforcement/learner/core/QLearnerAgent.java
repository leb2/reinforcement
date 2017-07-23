package b.reinforcement.learner.core;

import b.reinforcement.learner.policy.EpsilonGreedyPolicy;
import b.reinforcement.learner.policy.Policy;
import b.reinforcement.learner.policy.SoftmaxPolicy;
import b.reinforcement.learner.valuefunction.ActionValueFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class QLearnerAgent {

  protected ActionValueFunction q;
  protected Environment environment;
  protected Policy policy;

  private Policy trainedPolicy;
  private double epsilon = 0.2;
  private int index = 0;
  private double sumSquaredErrors = 0;
  private double epsilonDecay = 0;

  public QLearnerAgent(Environment environment) {
    this.environment = environment;
  }

  public void setEpsilon(double epsilon) {
    this.epsilon = epsilon;
//    ((SoftmaxPolicy) policy).setTemperature(epsilon);
    ((EpsilonGreedyPolicy) policy).setEpsilon(epsilon);
  }

  public void setEpsilonDecay(double epsilonDecay) {
    this.epsilonDecay = epsilonDecay;
  }

  /**
   * Trains agent until the end of the episode.
   */
  public void train() {
    while (!environment.inTerminalState()) {

      Action action = policy.chooseAction(environment);
      List<Double> stateAction = environment.getStateAction(action);

      double knownReward = environment.immediateReward(action);
      double totalReward = environment.performAction(action);
      double unknownReward = totalReward - knownReward;

      // Next state of environment is implicitly passed.
      backup(stateAction, unknownReward);
    }
  }

  public void backup(
          List<Double> stateAction,
          double unknownReward) {

    double backupValue;
    if (environment.inTerminalState()) {
      backupValue = unknownReward - q.getValue(stateAction);
    } else {
      Action greedyAction = getGreedyPolicy().chooseAction(environment);
      double greedyValue = q.getValue(environment.getStateAction(greedyAction));
      double nextKnownReward = environment.immediateReward(greedyAction);

      backupValue = unknownReward + nextKnownReward + greedyValue - q.getValue(stateAction);
    }
    if (Math.abs(backupValue) > 0) {
      q.backup(stateAction, backupValue);
    }
    index += 1;
    sumSquaredErrors += backupValue * backupValue;



    if ((index + 1) % 50000 == 0) {
      double rmse = Math.sqrt(sumSquaredErrors / 50000);
      try {
        Writer output = new BufferedWriter(new FileWriter("rmse.csv", true));
        output.append(rmse + "\n");
        output.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("RMSE: " + rmse);
//      System.out.println("Temperature: " + ((SoftmaxPolicy) policy).getTemperature());
      System.out.println("Epsilon: " + ((EpsilonGreedyPolicy) policy).getEpsilon());
      sumSquaredErrors = 0;
//      ((SoftmaxPolicy) policy).incrementTemperature(0.02);
      setEpsilon(epsilon * (1 - epsilonDecay));
    }
  }

  public void setActionValueFunction(ActionValueFunction q) {
    this.q = q;
//    policy = new SoftmaxPolicy(q, epsilon);
    policy = new EpsilonGreedyPolicy(q, epsilon);
    trainedPolicy = new EpsilonGreedyPolicy(q, 0);
  }

  public ActionValueFunction getActionValueFunction() {
    return q;
  }

  public Policy getGreedyPolicy() {
    return trainedPolicy;
  }

  public Policy getPolicy() {
    return policy;
  }
}
