package com.brendanmle.reinforcement.neuralnet;


public class NeuralNetwork {
  Layer[] layers;
  double[] lastOutput;
  
  public NeuralNetwork(int... layerSizes){
    layers = new Layer[layerSizes.length - 1];
    for(int i = 0; i < layers.length - 1; i++){
      layers[i] = new Layer(
          layerSizes[i], 
          layerSizes[i + 1], 
          ActivationFunction.TANH);
    }
    layers[layers.length - 1] = new Layer(
        layerSizes[layers.length - 1], 
        layerSizes[layers.length], 
        ActivationFunction.LINEAR);
  }
  
  public double[] run(double[] input) {
    double[] data = input;
    for (Layer l : layers) {
      data = l.calc(data);
    }
    lastOutput = clone(data);
    return data;
  }
  
  public void backprop(double[] result, double learningRate) {
    double[] deriv = diff(result, lastOutput);
    double diffMag = mag(deriv);
    for (int i = layers.length - 1; i >= 0; i--) {
      Layer l = layers[i];
      deriv = l.backprop(deriv);
    }
    double dsq = 0;
    for (Layer l : layers){
      dsq += l.derivativeSqMag();
    }
    double d = Math.sqrt(dsq);
    for (Layer l : layers){
      l.updateWeights(d, learningRate * diffMag);
    }
  }
  
  private double[] clone(double[] a){
    double[] b = new double[a.length];
    for(int i = 0;i < a.length;i++){
      b[i] = a[i];
    }
    return b;
  }
  
  private double[] diff(double[] a, double[] b){
    double[] c = new double[a.length];
    for(int i = 0;i < a.length;i++){
      c[i] = a[i] - b[i];
    }
    return c;
  }
  
  private double mag(double[] a){
    double b = 0;
    for(int i = 0; i < a.length;i++){
      b += a[i] * a[i];
    }
    return Math.sqrt(b);
  }

  public void print() {
    for (int i = 0; i < layers.length; i++) {
      layers[i].print();
    }
  }
}
