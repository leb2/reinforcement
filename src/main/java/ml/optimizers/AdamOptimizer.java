package ml.optimizers;

import ml.arrays.Array;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.ml.FreeVariable;

/**
 * Created by henry on 7/14/17.
 */
public class AdamOptimizer implements Optimizer{
    private double learningRate;
    private final double B1 = 0.8;
    private final double B2 = 0.95;
    private double vt = 0;
    private DArray mt;
    private int time = 0;
    private final double e = 0.00000001;

    public AdamOptimizer(double learningRate) {
        this.learningRate = learningRate;
    }

    @Override
    public void step(Array<FreeVariable> vars) {
        time++;
        if (mt == null) {
            mt = new DBackedArray(vars.size());
            mt.fill(() -> 0.0);
        }

        DArray grad = new DBackedArray(vars.size());
        for(int i = 0;i < grad.size();i++) {
            grad.set(i, vars.get(i).getDerivative());
        }

        mt = mt.times(B1).add(grad.times(1 - B1));
        vt = B2 * vt + (1 - B2) * grad.dot(grad);

        double vthat = vt / (1 - Math.pow(B2, time));

        double factor = learningRate / (Math.sqrt(Math.sqrt(vthat)) + e) / (1 - Math.pow(B1, time));

        int s = vars.size();
        for(int i = 0; i < s; i++) {
            FreeVariable f = vars.get(i);
            f.set(f.get() + mt.get(i) * factor);
        }
    }
}
