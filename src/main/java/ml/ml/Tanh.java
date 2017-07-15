package ml.ml;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.arrays.doubles.DElementArray;

public class Tanh implements Model{
  @Override
  public Array<FreeVariable> getFreeVariables() {
    return new BackedArray<>(0);
  }

  @Override
  public int getInputNum() {
    return 1;
  }

  @Override
  public int getOutputNum() {
    return 1;
  }

  @Override
  public ExecutionModel prepare() {
    return new TanhExecutionModel(this);
  }
}


class TanhExecutionModel implements ExecutionModel{
  Tanh t;
  
  double input;
  double output;

  public TanhExecutionModel(Tanh tanh) {
    this.t = tanh;
  }

  @Override
  public DArray eval(DArray inputs) {
    input = inputs.get(0);
    output = 2 / (1 + Math.exp(-2 * input)) - 1;
    return new DElementArray(output);
  }

  @Override
  public DArray backprop(DArray derivatives, DArray inputDerivatives) {
    inputDerivatives.set(0,derivatives.get(0) * (1 - output * output));
    return inputDerivatives;
  }

  @Override
  public int getInputNum() {
    return 1;
  }

  @Override
  public int getOutputNum() {
    return 1;
  }

  @Override
  public Model getModel() {
    return t;
  }

  @Override
  public void resetDerivatives() {}
}
