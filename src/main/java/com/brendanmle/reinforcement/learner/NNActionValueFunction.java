package com.brendanmle.reinforcement.learner;

import com.brendanmle.reinforcement.neuralnet.NeuralNetwork;

import java.util.List;

public class NNActionValueFunction implements ActionValueFunction {

  private NeuralNetwork network;
  private double learningRate;

  public NNActionValueFunction(Environment environment, double learningRate) {
    this.learningRate = learningRate;
    network = new NeuralNetwork(
            environment.getVectorSize(), 40, 40, 1);
  }

  @Override
  public double getValue(StateAction stateAction) {
    List<Double> inputStateAction = stateAction.toVector();
    return network.run(doubleListToArr(inputStateAction))[0];
  }

  @Override
  public void backup(StateAction stateAction, double newValue) {

    // TODO: remove variable
    getValue(stateAction); // Needed for backprop. TODO: improve
    network.backprop(new double[]{newValue}, learningRate);
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

  public void print() {
    network.print();
  }
}
