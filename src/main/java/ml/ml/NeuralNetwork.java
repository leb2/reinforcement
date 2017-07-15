package ml.ml;
import java.util.ArrayList;
import java.util.List;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.JoinedArray;
import ml.arrays.SubArray;
import ml.ml.*;

public class NeuralNetwork implements Model {
  private Array<FreeVariable> weights;
  private FCLayer[] layers;
  
  public NeuralNetwork(int... ls) {
    layers = new FCLayer[ls.length - 1];
    for(int i = 0;i < ls.length - 1;i++){
      int wAdd = (ls[i] + 1) * ls[i + 1];
      layers[i] = new FCLayer(ls[i], ls[i + 1], 
          i == ls.length - 2 ?
                  Identity::new : Tanh::new);
    }
    
    Array<Array<FreeVariable>> subArrays = new BackedArray<>(layers.length);
    for(int i = 0;i < layers.length;i++){
      subArrays.set(i, layers[i].getFreeVariables());
    }
    weights = new JoinedArray<>(subArrays);
  }

  @Override
  public Array<FreeVariable> getFreeVariables() {
    return weights;
  }

  @Override
  public int getInputNum() {
    return layers[0].getInputNum();
  }

  @Override
  public int getOutputNum() {
    return layers[layers.length - 1].getOutputNum();
  }

  @Override
  public ExecutionModel prepare() {
    ExecutionModel[] models = new ExecutionModel[layers.length];
    for(int i = 0;i < layers.length;i++){
      models[i] = layers[i].prepare();
    }
    return new LinearExecutionModel(this, models);
  }
}
