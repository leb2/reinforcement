package ml.ml;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.ml.ExecutionModel;
import ml.ml.Model;

public class LinearExecutionModel implements ExecutionModel {
  ExecutionModel[] models;
  Model m;
  
  public LinearExecutionModel(Model m, ExecutionModel... models) {
    this.models = models;
    this.m = m;
  }

  @Override
  public int getInputNum() {
    return models[0].getInputNum();
  }


  @Override
  public int getOutputNum() {
    return models[models.length - 1].getOutputNum();
  }


  @Override
  public Model getModel() {
    return m;
  }


  @Override
  public void resetDerivatives() {}


  @Override
  public DArray eval(DArray inputs) {
    DArray tick = inputs;
    for(int i = 0;i < models.length;i++){
      tick = models[i].eval(tick);
    }
    return tick;
  }


  @Override
  public DArray backprop(DArray derivatives, DArray inputDerivatives) {
    DArray derivs = derivatives;
    for(int i = models.length - 1;i > 0;i--){
      DArray inputDerivs = new DBackedArray(models[i].getInputNum()).fill(() -> 0.0);
      derivs = models[i].backprop(derivs, inputDerivs);
    }
    derivs = models[0].backprop(derivs, inputDerivatives);
    return inputDerivatives;
  }

}
