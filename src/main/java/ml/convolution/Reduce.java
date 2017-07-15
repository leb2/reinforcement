package ml.convolution;//package convolution;
//
//import arrays.Array;
//import arrays.BackedArray;
//import ml.Derivative;
//import ml.ExecutionModel;
//import ml.FreeVariable;
//import ml.Model;
//
//public class Reduce implements Model{
//  int width;
//  int height;
//  int size;
//  
//  public Reduce(int width, int height, int size) {
//    this.width = width;
//    this.height = height;
//    this.size = size;
//  }
//
//  @Override
//  public Array<FreeVariable> getFreeVariables() {
//    return new BackedArray<FreeVariable>(0);
//  }
//
//  @Override
//  public int getInputNum() {
//    return width * height;
//  }
//
//  @Override
//  public int getOutputNum() {
//    return width * height / size / size;
//  }
//
//  @Override
//  public ExecutionModel prepare() {
//    return new ReduceExecutionModel(this);
//  }
//}
//
//class ReduceExecutionModel implements ExecutionModel{
//  Reduce r;
//  public ReduceExecutionModel(Reduce r) {
//    this.r = r;
//  }
//  @Override
//  public Array<Double> eval(Array<Double> inputs) {
//    Array<Double> output = new BackedArray<>(getOutputNum());
//    output.fill(() -> new Double(0));
//    
//    for(int i = 0;i < inputs.size();i++){
//      int x = i % r.width;
//      int y = i / r.width;
//      
//      x /= r.size;
//      y /= r.size;
//            
//      int ind = y * r.width / r.size + x;
//      if (inputs.get(i) > output.get(ind)) {
//        output.set(ind, inputs.get(i));
//      }
//    }
//    return output;
//  }
//  
//  
//  private double get(Array<Double> arr, int ind){
//    if (ind >= 0 && ind < arr.size()) {
//      return arr.get(ind);
//    } else {
//      return 0;
//    }
//  }
//  @Override
//  public Array<Derivative> backprop(Array<Derivative> derivatives, Array<Derivative> inputDerivatives) {
//    // TODO Auto-generated method stub
//    return null;
//  }
//  @Override
//  public int getInputNum() {
//    return r.getInputNum();
//  }
//  @Override
//  public int getOutputNum() {
//    return r.getOutputNum();
//  }
//  @Override
//  public Model getModel() {
//    return r;
//  }
//  @Override
//  public void resetDerivatives() {}
//}
