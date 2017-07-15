package ml.ml;
import java.util.function.Function;
import java.util.function.Supplier;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.JoinedArray;
import ml.arrays.SubArray;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.ml.Model;

public class FCLayer implements Model {
  WeightedAddition[] additions;
  Model[] outputs;
  
  Array<FreeVariable> free;
  int inputSize;
  int outputSize;
  
  public FCLayer(
      int inputNum,
      int outputNum,
      Supplier<Model> activationProducer) {
    this.inputSize = inputNum;
    this.outputSize = outputNum;
    this.additions = new WeightedAddition[outputSize];
    this.outputs = new Model[outputSize];
    
    Array<Array<FreeVariable>> sub = new BackedArray<>(outputSize);
    
    for(int i = 0; i < outputSize; i++) {
      additions[i] = new WeightedAddition(inputSize);
      sub.set(i, additions[i].getFreeVariables());
      outputs[i] = activationProducer.get();
    }
    
    free = new JoinedArray<>(sub);
  }

  @Override
  public Array<FreeVariable> getFreeVariables() {
    return free;
  }

  @Override
  public int getInputNum() {
    return inputSize;
  }

  @Override
  public int getOutputNum() {
    return outputSize;
  }

  @Override
  public ExecutionModel prepare() {
    return new FCLayerExecutionModel(this);
  }
}

class FCLayerExecutionModel implements ExecutionModel {
  ExecutionModel[] additions;
  ExecutionModel[] outputs;
  FCLayer m;
  
  public FCLayerExecutionModel(FCLayer m) {
    this.m = m;
    additions = new ExecutionModel[m.outputSize];
    outputs = new ExecutionModel[m.outputSize];
    for (int i = 0;i < m.outputSize; i++){
      additions[i] = m.additions[i].prepare();
      outputs[i] = m.outputs[i].prepare();
    }
  }
  
  @Override
  public DArray eval(DArray inputs) {
    DArray out = new DBackedArray(getOutputNum());
    for (int i = 0; i < m.outputSize; i++){
      DArray inter = additions[i].eval(inputs);
      out.set(i, outputs[i].eval(inter).get(0));
    }
    return out;
  }
  @Override
  public DArray backprop(DArray derivatives, DArray inputDerivatives) {
    DArray aderivs = new DBackedArray(getOutputNum());
    for (int i = 0; i < m.outputSize; i++){
      outputs[i].backprop(derivatives.getA(i), aderivs.getA(i));
      additions[i].backprop(aderivs.getA(i), inputDerivatives);
    }
    return inputDerivatives;
  }
  @Override
  public int getInputNum() {
    return m.inputSize;
  }
  @Override
  public int getOutputNum() {
    return m.outputSize;
  }
  @Override
  public Model getModel() {
    return m;
  }
  @Override
  public void resetDerivatives() { }
}