package com.brendanmle.reinforcement.learner;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public class TableActionValueFunction<S extends State<A>, A extends Action>
  implements ActionValueFunction<S, A> {

  private Map<StateAction<S, A>, Double> actionValueMap = new HashMap<>();
  private Policy<S, A> greedyPolicy = new EpsilonGreedyPolicy<>(this, 0);
  private StateActionFactory<S, A> saFactory;
  private double learningRate = 0.2;

  public TableActionValueFunction(StateActionFactory<S, A> saFactory, double learningRate) {
    this.saFactory = saFactory;
    this.learningRate = learningRate;
  }

  public double getLearningRate() {
    return learningRate;
  }

  @Override
  public double getValue(S state, A action) {
    return actionValueMap.computeIfAbsent(saFactory.create(state, action), q -> 0.0);
  }

  @Override
  public void updateValue(S state, A action, double value) {
    actionValueMap.put(saFactory.create(state, action), value);
  }

  @Override
  public Policy getGreedyPolicy() {
    return greedyPolicy;
  }
}
