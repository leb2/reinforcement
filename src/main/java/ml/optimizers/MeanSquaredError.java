package ml.optimizers;

import ml.arrays.doubles.DArray;

/**
 * Created by henry on 7/13/17.
 */
public class MeanSquaredError implements Loss {
    @Override
    public double eval(DArray inputs, DArray target) {
        DArray diff = inputs.sub(target);
        return diff.dot(diff);
    }

    @Override
    public DArray backprop(DArray inputs, DArray target) {
        return target.sub(inputs).times(2);

    }
}
