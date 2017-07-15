package ml.arrays.doubles;

public class DReferenceArray extends DSubArray{

  public DReferenceArray(DArray arr, int ind) {
    super(arr, ind, ind + 1);
  }
}
