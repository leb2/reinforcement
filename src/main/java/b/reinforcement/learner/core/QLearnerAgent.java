package b.reinforcement.learner.core;

import b.reinforcement.learner.policy.EpsilonGreedyPolicy;
import b.reinforcement.learner.policy.Policy;
import b.reinforcement.learner.policy.SoftmaxPolicy;
import b.reinforcement.learner.valuefunction.ActionValueFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class QLearnerAgent {

  protected ActionValueFunction q;
  protected Environment environment;
  protected Policy policy;
  protected Policy trainedPolicy;
  private double epsilon = 0.2;
  private int index = 0;
  private double sumSquaredErrors = 0;

  public QLearnerAgent(ActionValueFunction q) {
    setActionValueFunction(q);
  }

  public QLearnerAgent(Environment environment) {
    this.environment = environment;
  }

  public void setEpsilon(double epsilon) {
    this.epsilon = epsilon;
    ((SoftmaxPolicy) policy).setTemperature(epsilon);
  }

  /**
   * Trains agent until the end of the episode.
   */
  public void train() {
    while (!environment.inTerminalState()) {

      Action action = policy.chooseAction(environment);
      StateAction stateAction = environment.getStateAction(action);

      double reward = environment.performAction(action);

      // Next state of environment is implicitly passed.
      backup(stateAction, reward);
    }
  }

  public void backup(
          StateAction stateAction,
          double reward) {

    double backupValue;
    if (environment.inTerminalState()) {
      backupValue = reward - q.getValue(stateAction);
    } else {
      Action greedyAction = getGreedyPolicy().chooseAction(environment);
      double greedyValue = q.getValue(environment.getStateAction(greedyAction));

      backupValue = reward + greedyValue - q.getValue(stateAction);
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
      System.out.println("Temperature: " + ((SoftmaxPolicy) policy).getTemperature());
      sumSquaredErrors = 0;
      ((SoftmaxPolicy) policy).incrementTemperature(0.02);
    }
  }

  public void setActionValueFunction(ActionValueFunction q) {
    this.q = q;
    policy = new SoftmaxPolicy(q, epsilon);//new EpsilonGreedyPolicy(q, epsilon);
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
