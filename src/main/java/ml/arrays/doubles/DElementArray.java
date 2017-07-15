package ml.arrays.doubles;

public class DElementArray implements DArray{
  
  double element;
  
  public DElementArray(double t) {
    this.element = t;
  }

  @Override
  public double get(int index) {
     if (index == 0){
       return element;
     } else {
       throw new IndexOutOfBoundsException();
     }
  }

  @Override
  public void set(int index, double t) {
    if (index == 0){
      element = t;
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public int size() {
   return 1;
  }
}
