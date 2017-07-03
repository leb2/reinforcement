package com.brendanmle.reinforcement.learner;

import java.util.HashMap;
import java.util.Map;

public class ActionValueFunction<
        S extends State<A>,
        A extends Action> {

  private static final double LEARNING_RATE = 0.2;

  private Map<StateAction<S, A>, Double> actionValueMap = new HashMap<>();
  private Policy<S, A> greedyPolicy = new EpsilonGreedyPolicy<>(this, 0);
  private StateActionFactory<S, A> saFactory;

  public ActionValueFunction(StateActionFactory<S, A> saFactory) {
    this.saFactory = saFactory;
  }

  public void backup(S state, A action, double reward, S statePrime) {

    double currValue = getValue(state, action);

    double newValue;
    if (statePrime.isTerminalState()) {
      newValue = currValue + LEARNING_RATE * (reward - currValue);
    } else {
      A greedyAction = greedyPolicy.chooseAction(statePrime);
      double greedyValue = getValue(statePrime, greedyAction);
      newValue = currValue + LEARNING_RATE * (reward + greedyValue - currValue);
    }

    updateValue(state, action, newValue);
  }

  double getValue(S state, A action) {
    return actionValueMap.computeIfAbsent(saFactory.create(state, action), q -> 0.0);
  }

  private void updateValue(S state, A action, double value) {
    actionValueMap.put(saFactory.create(state, action), value);
  }
}