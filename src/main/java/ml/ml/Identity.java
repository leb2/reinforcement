package ml.ml;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.doubles.DArray;
import ml.ml.Model;

public class Identity implements Model {
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
    return new IdentityExecutionModel(this);
  }
}


class IdentityExecutionModel implements ExecutionModel{
  Identity l;
  
  public IdentityExecutionModel(Identity l) {
    this.l = l;
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
    return l;
  }
  @Override
  public void resetDerivatives() {}

  @Override
  public DArray eval(DArray inputs) {
    return inputs;
  }

  @Override
  public DArray backprop(DArray derivatives, DArray inputDerivatives) {
    int size = inputDerivatives.size();
    for (int i = 0;i < size;i++) {
      inputDerivatives.add(i, derivatives.get(i));
    }
    return inputDerivatives;
  }
}
