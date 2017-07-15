package ml.arrays;

import java.util.function.Supplier;

public class BackedArray<T> implements Array<T> {
  private Object[] backingArray; 
  
  public BackedArray(int size){
    backingArray = new Object[size];
  }
  
  public BackedArray(T[] arr) {
    backingArray = arr;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public T get(int index) {
    return (T) backingArray[index];
  }

  @Override
  public void set(int index, T t) {
    backingArray[index] = t;
  }

  @Override
  public int size() {
    return backingArray.length;
  }
}
