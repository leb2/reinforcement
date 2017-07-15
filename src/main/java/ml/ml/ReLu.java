package ml.ml;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.arrays.doubles.DElementArray;

public class ReLu implements Model{
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
    return new ReLuExecutionModel(this);
  }
}


class ReLuExecutionModel implements ExecutionModel{
  ReLu t;
  
  double input;
  double output;

  public ReLuExecutionModel(ReLu relu) {
    this.t = relu;
  }

  @Override
  public DArray eval(DArray inputs) {
    input = inputs.get(0);
    output = input > 0 ? input : 0;
    return new DElementArray(output);
  }

  @Override
  public DArray backprop(DArray derivatives, DArray inputDerivatives) {
    inputDerivatives.set(0, output > 0 ? derivatives.get(0) : 0);
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
