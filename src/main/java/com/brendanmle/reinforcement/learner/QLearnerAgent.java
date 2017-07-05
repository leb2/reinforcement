package com.brendanmle.reinforcement.learner;

public class QLearnerAgent<
        S extends State<A>,
        A extends Action> {

  protected ActionValueFunction<S, A> q;
  protected Environment<S, A> environment;
  protected Policy<S, A> policy;
  protected Policy<S, A> trainedPolicy;

  public QLearnerAgent() {}

  public QLearnerAgent(Environment<S, A> environment, StateActionFactory<S, A> saFactory) {
    this();
    q = new TableActionValueFunction<>(saFactory);
    policy = new EpsilonGreedyPolicy<>(q, 1);
    trainedPolicy = new EpsilonGreedyPolicy<>(q, 0);
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
