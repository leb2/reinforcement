package ml.arrays;

public class ElementArray<T> implements Array<T>{
  
  T element;
  
  public ElementArray(T t) {
    this.element = t;
  }

  @Override
  public T get(int index) {
     if (index == 0){
       return element;
     } else {
       throw new IndexOutOfBoundsException();
     }
  }

  @Override
  public void set(int index, T t) {
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
