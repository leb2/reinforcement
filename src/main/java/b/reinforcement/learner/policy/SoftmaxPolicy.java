package b.reinforcement.learner.policy;

import b.reinforcement.learner.valuefunction.ActionValueFunction;
import b.reinforcement.learner.core.Action;
import b.reinforcement.learner.core.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoftmaxPolicy implements Policy {
  private ActionValueFunction q;
  private double temperature = 1;
  protected Random random = new Random();

  public SoftmaxPolicy(ActionValueFunction q) {
    this.q = q;
  }

  public SoftmaxPolicy(ActionValueFunction q, double temperature) {
    this(q);
    this.temperature = temperature;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public void incrementTemperature(double increment) {
    this.temperature += increment;
  }

  @Override
  public Action chooseAction(Environment environment) {
    List<Action> actions = environment.getActions();
    List<Double> values = new ArrayList<>();
    for (Action action : actions) {
      List<Double> stateAction = environment.getStateAction(action);
      values.add(q.getValue(stateAction));
    }

    double sumExp = 0;
    for (double value : values) {
      sumExp += Math.exp(value / temperature);
    }
    double rand = random.nextDouble();

    double probSofar = 0;
    for (int i = 0; i < actions.size(); i++) {
      double probability = Math.exp(values.get(i) / temperature) / sumExp;
      probSofar += probability;
      if (probSofar >= rand) {
        return actions.get(i);
      }
    }
    throw new IllegalStateException("Faulty implementation. No action chosen");
  }
}
