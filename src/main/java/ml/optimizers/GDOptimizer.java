package ml.optimizers;

import ml.arrays.Array;
import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.ml.FreeVariable;

/**
 * Created by henry on 7/13/17.
 */
public class GDOptimizer implements Optimizer {
    private double learningRate;
    private double decay = 1;
    private double momentum = 0;


    public GDOptimizer(double learningRate) {
        this.learningRate = learningRate;
    }

    public GDOptimizer momentum(double m) {
        assert m > 0;
        assert m < 1;
        this.momentum = m;
        return this;
    }

    public GDOptimizer decay(double d) {
        this.decay = d;
        return this;
    }

    private DArray grad;
    private int time = 0;

    @Override
    public void step(Array<FreeVariable> vars) {
        time++;
        if (grad == null) {
            grad = new DBackedArray(vars.size());
            grad.fill(() -> 0.0);
        }

        learningRate *= decay;
        int s = vars.size();
        for(int i = 0; i < s; i++) {
            FreeVariable f = vars.get(i);
            grad.set(i, grad.get(i) * (momentum) + (1 - momentum) * f.getDerivative());
            f.set(f.get() + grad.get(i) * learningRate / (1 - Math.pow(momentum, time)));
        }
    }
}
