package ml.mlmodels;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.JoinedArray;
import ml.ml.ExecutionModel;
import ml.ml.FreeVariable;
import ml.ml.LinearExecutionModel;
import ml.ml.Model;

public class Pipeline implements Model{
  Model[] models;
  Array<FreeVariable> free;
  
  public Pipeline(Model... models) {
    this.models = models;
    Array<Array<FreeVariable>> freeS = new BackedArray<>(models.length);
    for(int i = 0;i < models.length;i++){
      freeS.set(i, models[i].getFreeVariables());
    }
    free = new JoinedArray<>(freeS);
  }

  @Override
  public Array<FreeVariable> getFreeVariables() {
    return free;
  }

  @Override
  public int getInputNum() {
    return models[0].getInputNum();
  }

  @Override
  public int getOutputNum() {
    // TODO Auto-generated method stub
    return models[models.length - 1].getOutputNum();
  }

  @Override
  public ExecutionModel prepare() {
    ExecutionModel[] executors = new ExecutionModel[models.length];
    for(int i = 0;i < models.length;i++){
      executors[i] = models[i].prepare();
    }
    return new LinearExecutionModel(this, executors);
  }

}
