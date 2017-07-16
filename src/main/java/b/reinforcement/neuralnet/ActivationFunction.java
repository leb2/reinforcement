package b.reinforcement.neuralnet;

public class ActivationFunction {

  public static ActivationFunction SIGMOID =
      new ActivationFunction(
          (x) -> 1/(1 + Math.exp(-x)), 
          (x,y) -> y*(1-y));

  public static ActivationFunction TANH =
      new ActivationFunction(
          (x) -> {
            double a = Math.exp(x);
            double b = Math.exp(-x);
            return (a-b)/(a+b);
          }, 
          (x,y) -> 1-y*y);

  public static ActivationFunction LINEAR =
      new ActivationFunction((x) -> x, (x,y) -> 1);

  public static ActivationFunction RELU =
      new ActivationFunction(
          (x) -> x > 0 ? x : 0, 
          (x,y) -> x > 0 ? 1 : 0);
  
  F1Func eval;
  F2Func deriv;
  
  public ActivationFunction(F1Func eval, F2Func deriv){
    this.eval = eval;
    this.deriv = deriv;
  }
}
