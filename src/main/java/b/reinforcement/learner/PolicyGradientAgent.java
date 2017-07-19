package b.reinforcement.learner;

import b.reinforcement.learner.core.Environment;
import ml.ml.ExecutionModel;
import ml.ml.Model;
import ml.ml.NeuralNetwork;

public class PolicyGradientAgent {
  protected double learningRate;
  protected ExecutionModel network;
  protected Model model;
  protected Environment environment;

  public PolicyGradientAgent() {
    model = new NeuralNetwork(environment.getVectorSize(), 15, 1);
    model.initNormalWeights();
    this.environment = environment;
    network = model.prepare();
  }
}
