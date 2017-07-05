package com.brendanmle.reinforcement.learner;

import com.brendanmle.reinforcement.neuralnet.NeuralNetwork;

public class NNActionValueFunction<S extends State<A>, A extends Action>
        implements ActionValueFunction<S, A> {

  private double learningRate;

  public NNActionValueFunction(double learningRate) {
    this();
    this.learningRate = learningRate;
  }

  private NNActionValueFunction() {}

  @Override
  public void backup(S state, A action, double reward, S statePrime) {

  }

  @Override
  public double getValue(S state, A action) {
    return 0;
  }
}
