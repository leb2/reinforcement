package ml.arrays.doubles;

public class DSubArray implements DArray {
  DArray arr;
  int start;
  int end;
  
  public DSubArray(DArray arr, int start, int end) {
    this.arr = arr;
    this.start = start;
    this.end = end;
  }

  @Override
  public double get(int index) {
    return arr.get(index + start);
  }

  @Override
  public void set(int index, double t) {
    arr.set(index + start, t);
  }

  @Override
  public int size() {
    return end - start;
  }
}
