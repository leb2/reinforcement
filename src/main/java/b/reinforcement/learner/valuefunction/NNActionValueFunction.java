package b.reinforcement.learner.valuefunction;

import b.reinforcement.learner.core.Environment;
import b.reinforcement.learner.core.Util;
import ml.misc.WindowData;
import ml.ml.ExecutionModel;
import ml.ml.Model;
import ml.ml.NeuralNetwork;
import ml.optimizers.AdamOptimizer;
import ml.optimizers.MeanSquaredError;

import java.util.List;

public class NNActionValueFunction implements ActionValueFunction {

  protected double learningRate;
  protected Model model;
  protected Environment environment;

  private ExecutionModel network;
  private WindowData window = new WindowData(10);

  public NNActionValueFunction(Environment environment, double learningRate) {
    this.learningRate = learningRate;
    model = new NeuralNetwork(environment.getVectorSize(), 20, 20, 1);
    model.initNormalWeights();
    this.environment = environment;
    network = model.prepare();
  }

  @Override
  public void setLearningRate(double learningRate) {
    this.learningRate = learningRate;
  }

  @Override
  public double getValue(List<Double> stateAction) {
    return network.eval(Util.doubleListToArr(stateAction))[0];
  }

  @Override
  public void backup(List<Double> stateAction, double newValue) {
    double value = getValue(stateAction);

    network.backprop(
            window.getWindow(
              Util.doubleListToArr(stateAction),
              new double[]{value + newValue}),
            new MeanSquaredError(),
            new AdamOptimizer(learningRate));
  }

  public String toString() {
    return network.toString();
  }

  public void save(String file) {
    model.saveModel(file);
    System.out.println("Saved to file " + file);
  }

  public void load(String file) {
    model.loadModel(file);
  }
}
