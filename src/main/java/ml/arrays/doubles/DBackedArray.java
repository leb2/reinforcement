package ml.arrays.doubles;

import java.util.function.Supplier;

public class DBackedArray implements DArray {
  private double[] backingArray; 
  
  public DBackedArray(int size){
    backingArray = new double[size];
  }
  
  public DBackedArray(double[] arr) {
    backingArray = arr;
  }
  
  @Override
  public double get(int index) {
    return backingArray[index];
  }

  @Override
  public void set(int index, double t) {
    backingArray[index] = t;
  }

  @Override
  public int size() {
    return backingArray.length;
  }
}
