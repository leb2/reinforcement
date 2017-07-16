package com.brendanmle.reinforcement.learner;

import ml.ml.NeuralNetwork;
import ml.optimizers.AdamOptimizer;
import ml.optimizers.MeanSquaredError;

import java.util.ArrayList;
import java.util.List;

public class NNStateActionFunction extends NNActionValueFunction {

  public NNStateActionFunction(Environment environment, double learningRate) {
    super(environment, learningRate);

    model = new NeuralNetwork(
            environment.getStateSize(), 40, 10, environment.getOutputSize());
    model.initNormalWeights();
    network = model.prepare();
  }

  @Override
  public void backup(StateAction stateAction, double newValue) {
    double value = getValue(stateAction);
    stateAction.toVector();
    double[] outputs = new double[environment.getOutputSize()];
    outputs[stateAction.getAction().getIndex()] = value + newValue;

    network.backprop(
            doubleListToArr(stateAction.stateVector()),
            outputs,

            new MeanSquaredError(),
            new AdamOptimizer(learningRate));
  }

  public double getValue(StateAction stateAction) {
    int index = stateAction.getAction().getIndex();
    double[] result = network.eval(doubleListToArr(stateAction.stateVector()));
    return result[index];
  }

  public List<Double> getValues(List<Double> state) {
    List<Double> list = new ArrayList<>();
    double[] result = network.eval(doubleListToArr(state));
    for (int i = 0; i < environment.getOutputSize(); i++) {
      list.add(result[i]);
    }
    return list;
  }
}
