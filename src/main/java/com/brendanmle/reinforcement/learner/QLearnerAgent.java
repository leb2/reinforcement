package com.brendanmle.reinforcement.learner;

public class QLearnerAgent {

  protected ActionValueFunction q;
  protected Environment environment;
  protected Policy policy;
  protected Policy trainedPolicy;
  private double epsilon = 0.2;

  public QLearnerAgent(ActionValueFunction q) {
    setActionValueFunction(q);
  }

  public QLearnerAgent(Environment environment) {
    this.environment = environment;
  }

  public void setEpsilon(double epsilon) {
    this.epsilon = epsilon;
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
  }

  public void setActionValueFunction(ActionValueFunction q) {
    this.q = q;
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
