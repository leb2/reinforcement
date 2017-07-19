package b.reinforcement.learner.valuefunction;

import b.reinforcement.learner.core.StateAction;

public interface ActionValueFunction {

  double getValue(StateAction stateAction);
  void backup(StateAction stateAction, double newValue);
  void setLearningRate(double learningRate);
}
