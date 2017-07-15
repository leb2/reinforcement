package ml.ml;

import ml.arrays.Array;
import ml.arrays.BackedArray;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.ml.FreeVariable;
import ml.ml.Model;
import ml.optimizers.Loss;
import ml.optimizers.Optimizer;

public interface ExecutionModel {
  default double backprop(double[] input, double[] output, Loss loss, Optimizer opt) {
    Array<FreeVariable> free = getModel().getFreeVariables();
    free.forEach(FreeVariable::resetDerivative);
    resetDerivatives();

    DArray inputDerivatives = new DBackedArray(getInputNum());
    inputDerivatives.fill(() -> 0.0);


    DArray target = new DBackedArray(output);
    DArray outputs = eval(new DBackedArray(input));
    double l = loss.eval(outputs, target);
    backprop(loss.backprop(outputs, target), inputDerivatives);

    opt.step(free);

    return l;
  }
  
  default double backpropKnown(double[] output, double[] target, Loss loss, Optimizer opt) {
    Array<FreeVariable> free = getModel().getFreeVariables();
    free.forEach(FreeVariable::resetDerivative);
    resetDerivatives();

    DArray inputDerivatives = new DBackedArray(getInputNum());
    inputDerivatives.fill(() -> 0.0);

    DArray targ = new DBackedArray(target);
    DArray outp = new DBackedArray(output);
    double l = loss.eval(outp, targ);
    backprop(loss.backprop(outp, targ), inputDerivatives);

    opt.step(free);

    return l;
  }
  
  default double backprop(double[][] input, double[][] output, Loss loss, Optimizer opt) {
    Array<FreeVariable> free = getModel().getFreeVariables();
    free.forEach(FreeVariable::resetDerivative);
    resetDerivatives();

    DArray inputDerivatives = new DBackedArray(getInputNum());
    inputDerivatives.fill(() -> 0.0);
    
    double lossSum = 0;

    for(int ind = 0;ind < input.length;ind++){
      DArray target = new DBackedArray(output[ind]);
      DArray outputs = eval(new DBackedArray(input[ind]));
      lossSum += loss.eval(outputs, target);
      backprop(loss.backprop(outputs, target), inputDerivatives);
    }

    free.forEach(f -> f.scaleDerivative(1.0 / input.length));
    opt.step(free);

    return lossSum / input.length;
  }

  default double backprop(double[][][] dat, Loss loss, Optimizer opt) {
    return backprop(dat[0], dat[1], loss, opt);
  }
  
//  default void backprop(double[][] inputData, double[][] outputData, double learningRate) {
//    Array<FreeVariable> free = getModel().getFreeVariables();
//    free.forEach(f -> f.resetDerivative());
//    resetDerivatives();
//    
//    Array<Derivative> outputDerivatives = new BackedArray<>(getOutputNum());
//    Array<Derivative> inputDerivatives = new BackedArray<>(getInputNum());
//    inputDerivatives.fill(() -> new Derivative(0));
//    
//    for(int dat = 0;dat < inputData.length;dat++){
//      double[] outputs = eval(inputData[dat]);
//      for(int i = 0;i < outputs.length;i++){
//        double diff = outputData[dat][i] - outputs[i];
//        outputDerivatives.set(i, new Derivative(diff));
//      }
//  
//      backprop(outputDerivatives, inputDerivatives);
//    }
//
//    double derivTot = 0;
//    int s = free.size();
//    for(int i = 0; i < s; i++) {
//      double deriv = free.get(i).derivative;
//      derivTot += deriv * deriv;
//    }
//    
//    if(derivTot > 0.0000001) {
//      derivTot = learningRate / Math.sqrt(derivTot);
//      for(int i = 0; i < s; i++) {
//        FreeVariable f = free.get(i);
//        f.val += f.derivative * derivTot;
//      }
//    }
//  }

  default double[] eval(double[] inputs) {
    DArray in = new DBackedArray(inputs);
    DArray out = eval(in);
    return out.toArray();
  }
  
  DArray eval(DArray inputs);
  DArray backprop(
          DArray derivatives,
          DArray inputDerivatives);
  int getInputNum();
  int getOutputNum();
  Model getModel();
  void resetDerivatives();
}
