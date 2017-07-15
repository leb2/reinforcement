package ml.misc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by henry on 7/14/17.
 */
public class WindowData {
    private int windowSize;

    List<double[]> data = new LinkedList<>();
    List<double[]> results = new LinkedList<>();

    public WindowData(int windowSize) {
        this.windowSize = windowSize;
    }

    public double[][][] getWindow(double[] x, double[] y){
        data.add(x);
        results.add(y);
        if (data.size() > windowSize) {
            data.remove(0);
            results.remove(0);
        }


        double[][] dat = new double[data.size()][];
        double[][] res = new double[data.size()][];
        for(int i = 0;i < dat.length;i++) {
            dat[i] = data.get(i);
            res[i] = results.get(i);
        }

        return new double[][][]{dat, res};
    }
}
