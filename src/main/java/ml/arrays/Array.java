package ml.arrays;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Array<T> {
  public T get(int index);
  public void set(int index, T t);
  public int size();
  default Array<T> fill(Supplier<T> sup) {
    int s = size();
    for(int i = 0;i < s;i++){
      set(i, sup.get());
    }
    return this;
  }
  default void forEach(Consumer<T> con) {
    int s = size();
    for(int i = 0;i < s;i++){
      con.accept(get(i));
    }
  }
  default Array<T> getA(int ind) {
    return new ElementArray<T>(get(ind));
  }
}
