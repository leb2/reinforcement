package com.brendanmle.reinforcement.learner;

public interface ActionValueFunction<S extends State<A>, A extends Action> {

  default void backup(S state, A action, double reward, S statePrime) {
    double currValue = getValue(state, action);

    double newValue;
    if (statePrime.isTerminalState()) {
      newValue = currValue + getLearningRate() * (reward - currValue);
    } else {
      A greedyAction = getGreedyPolicy().chooseAction(statePrime);
      double greedyValue = getValue(statePrime, greedyAction);
      newValue = currValue + getLearningRate() * (reward + greedyValue - currValue);
    }

    updateValue(state, action, newValue);
  }

  double getValue(S state, A action);
  void updateValue(S state, A action, double newValue);
  double getLearningRate();
  Policy<S, A> getGreedyPolicy();
}
