package b.reinforcement.learner.valuefunction;

import java.util.List;

public interface ActionValueFunction {

  double getValue(List<Double> stateAction);
  void backup(List<Double> stateAction, double newValue);
  void setLearningRate(double learningRate);
}
