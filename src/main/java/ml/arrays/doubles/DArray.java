package ml.arrays.doubles;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface DArray {
  public double get(int index);
  public void set(int index, double t);
  public int size();
  default DArray fill(Supplier<Double> sup) {
    int s = size();
    for(int i = 0;i < s;i++){
      set(i, sup.get());
    }
    return this;
  }
  default void forEach(Consumer<Double> con) {
    int s = size();
    for(int i = 0;i < s;i++){
      con.accept(get(i));
    }
  }
  default DArray getA(int ind) {
    return new DReferenceArray(this, ind);
  }
  default double[] toArray(){
    int size = size();
    double[] d = new double[size];
    for (int i = 0; i < size; i++){
      d[i] = get(i);
    }
    return d;
  }


  
  default void add(int ind, double a) {
    set(ind, get(ind) + a);
  }
  
  default String toS(){
    return Arrays.toString(toArray());
  }

  default DArray subArray(int start, int end) {
    return new DSubArray(this, start, end);
  }

  default DArray join(DArray darr) {
    return new DJoinedArray(this, darr);
  }

  default DArray add(DArray other) {
    assert size() == other.size();

    double[] n = new double[size()];
    for (int i = 0;i < n.length;i++) {
      n[i] = get(i) + other.get(i);
    }

    return new DBackedArray(n);
  }

  default DArray sub(DArray other) {
    assert size() == other.size();

    double[] n = new double[size()];
    for (int i = 0;i < n.length;i++) {
      n[i] = get(i) - other.get(i);
    }

    return new DBackedArray(n);
  }

  default double dot(DArray other) {
    assert size() == other.size();
    int s = size();
    double sum = 0;
    for (int i = 0;i < s;i++) {
      sum += get(i) * other.get(i);
    }
    return sum;
  }

  default DArray times(double scalar) {
    double[] n = new double[size()];
    for (int i = 0;i < n.length;i++) {
      n[i] = get(i) * scalar;
    }
    return new DBackedArray(n);
  }

  default void print() {
    for (int i = 0;i < size();i++) {
      System.out.print(String.format("%.2f", get(i)) + '\t');
    }
    System.out.println();
  }
}
