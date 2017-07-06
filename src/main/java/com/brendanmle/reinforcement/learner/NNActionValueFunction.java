package com.brendanmle.reinforcement.learner;

import com.brendanmle.reinforcement.neuralnet.NeuralNetwork;

import java.util.List;

public class NNActionValueFunction<S extends State<A>, A extends Action>
        implements ActionValueFunction<S, A> {

  private double learningRate;
  private StateActionFactory<S, A> saFactory;
  private NeuralNetwork network;
  private Policy<S, A> greedyPolicy = new EpsilonGreedyPolicy<>(this, 0);

  public NNActionValueFunction(StateActionFactory<S, A> saFactory, double learningRate) {
    this();
    this.learningRate = learningRate;
    this.saFactory = saFactory;

    network = new NeuralNetwork(
            saFactory.getVectorSize(), 50, 50, 1);
  }

  private NNActionValueFunction() {}

  @Override
  public double getLearningRate() {
    return 1;
  }

  @Override
  public double getValue(S state, A action) {
    List<Double> inputStateAction = saFactory.create(state, action).toVector();
    return network.run(doubleListToArr(inputStateAction))[0];
  }

  @Override
  public void updateValue(S state, A action, double newValue) {

    // TODO: remove variable
    List<Double> inputStateAction = saFactory.create(state, action).toVector();
     double value = getValue(state, action);
    network.backprop(new double[]{newValue}, learningRate);
    // double newVal = getValue(state, action); // TODO: remove
  }

  @Override
  public Policy<S, A> getGreedyPolicy() {
    return greedyPolicy;
  }

  // TODO: Improve bad runtime
  private static double[] doubleListToArr(List<Double> list) {
    double[] arr = new double[list.size()];
    for (int i = 0; i < list.size(); i++) {
      arr[i] = list.get(i);
    }
    return arr;
  }
}
