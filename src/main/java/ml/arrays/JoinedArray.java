package ml.arrays;

import java.util.ArrayList;
import java.util.List;

public class JoinedArray<T> implements Array<T>{
  Array<Array<T>> arrays;
  int[] sizes;
  int size;
  
  public JoinedArray(Array<Array<T>> arrays) {
    size = 0;
    this.arrays = arrays;
    sizes = new int[arrays.size()];
    for (int i = 0;i < sizes.length;i++) {
      sizes[i] = arrays.get(i).size();
      size += sizes[i];
    }
  }
  
  public JoinedArray(Array<T> arr1, Array<T> arr2) {
    arrays = new BackedArray<>(2);
    arrays.set(0, arr1);
    arrays.set(1, arr2);
    size = 0;
    sizes = new int[arrays.size()];
    for (int i = 0;i < sizes.length;i++) {
      sizes[i] = arrays.get(i).size();
      size += sizes[i];
    }
  }
  
  

  @Override
  public T get(int index) {
    int i = 0;
    while (index >= sizes[i]) {
      index -= sizes[i];
      i++;
    }
    return arrays.get(i).get(index);
  }

  @Override
  public void set(int index, T t) {
    int i = 0;
    while (index >= sizes[i]) {
      index -= sizes[i];
      i++;
    }
    arrays.get(i).set(index, t);
  }

  @Override
  public int size() {
    return size;
  }
}
