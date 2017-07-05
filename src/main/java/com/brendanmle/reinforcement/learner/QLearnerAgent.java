package com.brendanmle.reinforcement.learner;

public class QLearnerAgent<
        S extends State<A>,
        A extends Action> {

  protected ActionValueFunction<S, A> q;
  protected Environment<S, A> environment;
  protected Policy<S, A> policy;
  protected Policy<S, A> trainedPolicy;

  public QLearnerAgent() {}

  public QLearnerAgent(Environment<S, A> environment, ActionValueFunction q) {
    this();
    setActionValueFunction(q);
    this.environment = environment;
  }

  /**
   * Trains agent until the end of the episode.
   */
  public void train() {
    while (!environment.inTerminalState()) {

      S state = environment.getState();
      A action = policy.chooseAction(state);

      double reward = environment.performAction(action);
      S statePrime = environment.getState();

      q.backup(state, action, reward, statePrime);
    }
  }

  public void setActionValueFunction(ActionValueFunction q) {
    this.q = q;
    policy = new EpsilonGreedyPolicy<S, A>(q, 1);
    trainedPolicy = new EpsilonGreedyPolicy<S, A>(q, 0);
  }

  public Policy<S, A> getGreedyPolicy() {
    return trainedPolicy;
  }

  public Policy<S, A> getPolicy() {
    return policy;
  }

  public double getValue(S state, A action) {
    return q.getValue(state, action);
  }
}
