package ml.arrays.doubles;

public class DJoinedArray implements DArray{
  DArray[] arrays;
  int[] sizes;
  int size;
  
  public DJoinedArray(DArray... arrays) {
    size = 0;
    this.arrays = arrays;
    sizes = new int[arrays.length];
    for (int i = 0;i < sizes.length;i++) {
      sizes[i] = arrays[i].size();
      size += sizes[i];
    }
  }
  
  @Override
  public double get(int index) {
    int i = 0;
    while (index >= sizes[i]) {
      index -= sizes[i];
      i++;
    }
    return arrays[i].get(index);
  }

  @Override
  public void set(int index, double t) {
    int i = 0;
    while (index >= sizes[i]) {
      index -= sizes[i];
      i++;
    }
    arrays[i].set(index, t);
  }

  @Override
  public int size() {
    return size;
  }
}
