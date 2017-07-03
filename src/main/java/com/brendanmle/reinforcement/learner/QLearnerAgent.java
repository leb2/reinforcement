package com.brendanmle.reinforcement.learner;

public class QLearnerAgent<
        S extends State<A>,
        A extends Action> {

  private ActionValueFunction<S, A> q;
  private Environment<S, A> environment;
  private Policy<S, A> policy;
  private Policy<S, A> trainedPolicy;

  public QLearnerAgent(Environment<S, A> environment, StateActionFactory<S, A> saFactory) {
    this.environment = environment;
    q = new ActionValueFunction<>(saFactory);
    policy = new EpsilonGreedyPolicy<>(q, 1);
    trainedPolicy = new EpsilonGreedyPolicy<>(q, 0);
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
