package b.reinforcement.learner.core;

import java.util.List;

public class Util {
  public static double[] doubleListToArr(List<Double> list) {
    double[] arr = new double[list.size()];
    for (int i = 0; i < list.size(); i++) {
      arr[i] = list.get(i);
    }
    return arr;
  }
}
