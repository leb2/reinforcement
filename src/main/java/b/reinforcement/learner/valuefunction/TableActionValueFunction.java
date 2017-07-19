package b.reinforcement.learner.valuefunction;

import b.reinforcement.learner.core.Environment;
import b.reinforcement.learner.core.StateAction;
import b.reinforcement.learner.valuefunction.ActionValueFunction;

import java.util.HashMap;
import java.util.Map;

public class TableActionValueFunction implements ActionValueFunction {

  private Map<StateAction, Double> actionValueMap = new HashMap<>();
  private double learningRate = 0.2;

  public TableActionValueFunction(Environment environment, double learningRate) {
    this.learningRate = learningRate;
  }

  public void setLearningRate(double learningRate) {
    this.learningRate = learningRate;
  }

  @Override
  public double getValue(StateAction stateAction) {
    return actionValueMap.computeIfAbsent(stateAction, q -> 0.0);
  }

  @Override
  public void backup(StateAction stateAction, double value) {
    actionValueMap.put(stateAction, getValue(stateAction) + learningRate * value);
  }
}
