package ml.ml;
import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DElementArray;
import ml.ml.ExecutionModel;
import ml.ml.FreeVariable;
import ml.ml.Model;

public class WeightedAddition implements Model {
  int inputs;
  Array<FreeVariable> free;
  
  public WeightedAddition(int inputs) {
    this.inputs = inputs;
    free = new BackedArray<>(inputs + 1);
  }

  @Override
  public Array<FreeVariable> getFreeVariables() {
    return free;
  }

  @Override
  public int getInputNum() {
    return inputs;
  }

  @Override
  public int getOutputNum() {
    return 1;
  }

  @Override
  public ExecutionModel prepare() {
    return new WeightedAdditionExecutionModel(this);
  }
}

class WeightedAdditionExecutionModel implements ExecutionModel{
  WeightedAddition wa;
  DArray inputs;
  double output;

  public WeightedAdditionExecutionModel(WeightedAddition wa) {
    this.wa = wa;
  }
  
  @Override
  public DArray eval(DArray inputs) {
    this.inputs = inputs;
    output = wa.free.get(0).val;
    for (int i = 0;i < wa.inputs;i++){
      output += wa.free.get(i + 1).val * inputs.get(i);
    }
    return new DElementArray(output);
  }

  @Override
  public DArray backprop(DArray derivatives, DArray inputDerivatives) {
    double deriv = derivatives.get(0);
    wa.free.get(0).derivative += deriv;
    for (int i = 0; i < wa.inputs; i++) {
      wa.free.get(1 + i).derivative += deriv * inputs.get(i);
      inputDerivatives.add(i, wa.free.get(1 + i).val * deriv);
    }
    return inputDerivatives;
  }

  @Override
  public int getInputNum() {
    return wa.inputs;
  }

  @Override
  public int getOutputNum() {
    return 1;
  }

  @Override
  public Model getModel() {
    return wa;
  }

  @Override
  public void resetDerivatives() { }
}