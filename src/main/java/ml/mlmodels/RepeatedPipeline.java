package ml.mlmodels;

import ml.arrays.Array;
import ml.ml.ExecutionModel;
import ml.ml.FreeVariable;
import ml.ml.LinearExecutionModel;
import ml.ml.Model;

public class RepeatedPipeline implements Model{
  private Model m;
   public RepeatedPipeline(Model m) {
     this.m = m;
   }
  @Override
  public Array<FreeVariable> getFreeVariables() {
    return m.getFreeVariables();
  }
  @Override
  public int getInputNum() {
    return m.getInputNum();
  }
  @Override
  public int getOutputNum() {
    return m.getOutputNum();
  }
  @Override
  public ExecutionModel prepare() {
    return new LinearExecutionModel(this, m.prepare(), m.prepare());
  }

}
