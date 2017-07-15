package com.brendanmle.reinforcement.learner;


import ml.misc.WindowData;
import ml.ml.ExecutionModel;
import ml.ml.Model;
import ml.ml.NeuralNetwork;
import ml.optimizers.AdamOptimizer;
import ml.optimizers.GDOptimizer;
import ml.optimizers.MeanSquaredError;

import java.util.List;

public class NNActionValueFunction implements ActionValueFunction {

  private double learningRate;
  private ExecutionModel network;
  private WindowData wd = new WindowData(1);
  private Model model;

  public NNActionValueFunction(Environment environment, double learningRate) {
    this.learningRate = learningRate;
    model = new NeuralNetwork(
            environment.getVectorSize(), 30, 1);
    model.initNormalWeights();
    network = model.prepare();
  }

  @Override
  public double getValue(StateAction stateAction) {
    List<Double> inputStateAction = stateAction.toVector();
    return network.eval(doubleListToArr(inputStateAction))[0];
  }

  @Override
  public void backup(StateAction stateAction, double newValue) {

    double value = getValue(stateAction); // Needed for backprop. TODO: improve
    stateAction.toVector();

    network.backprop(doubleListToArr(stateAction.toVector()), new double[]{value + newValue},
            new MeanSquaredError(),
            new AdamOptimizer(learningRate));
//    network.backprop(new double[]{value + newValue}, learningRate);
  }

  // TODO: Improve bad runtime
  private static double[] doubleListToArr(List<Double> list) {
    double[] arr = new double[list.size()];
    for (int i = 0; i < list.size(); i++) {
      arr[i] = list.get(i);
    }
    return arr;
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
