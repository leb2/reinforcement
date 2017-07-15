package ml.arrays;

public class TestingArray {
  public static void main(String[] args) {
    Integer[] ints = {0,1,2,3,4,5,6,7,8,9};
    Array<Integer> backed = new BackedArray<>(ints);
    Array<Integer> sub1 = new SubArray<>(backed, 1, 4);
    Array<Integer> sub2 = new SubArray<>(backed, 7, 9);
    Array<Integer> join = new JoinedArray<>(sub1, sub2);
    String[] s = {"1", "2"};
    for(int i = 0;i < join.size();i++){
      System.out.println(join.get(i));
    }
  }
}
