package ml.arrays;

public class SubArray<T> implements Array<T> {
  Array<T> arr;
  int start;
  int end;
  
  public SubArray(Array<T> arr, int start, int end) {
    assert start >= 0;
    assert start < arr.size();
    assert end >= 0;
    assert end < arr.size();
    assert end >= start;
    
    this.arr = arr;
    this.start = start;
    this.end = end;
  }

  @Override
  public T get(int index) {
    return arr.get(index + start);
  }

  @Override
  public void set(int index, T t) {
    arr.set(index + start, t);
  }

  @Override
  public int size() {
    return end - start;
  }
}
