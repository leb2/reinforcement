package ml.ml;

public class FreeVariable {
  double val;
  double derivative;
  
  public FreeVariable(double val) {
    this.val = val;
    this.derivative = 0;
  }
  
  public double get() {
    return val;
  }
  
  public void set(double val) {
    this.val = val;
  }
  
  public void resetDerivative() {
    this.derivative = 0;
  }
  
  public void addDerivative(double d){
    this.derivative += d;
  }

  public double getDerivative() {
    return derivative;
  }

  public void scaleDerivative(double v) {
    derivative *= v;
  }
}
