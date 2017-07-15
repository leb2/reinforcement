package ml.optimizers;

import ml.arrays.doubles.DArray;

/**
 * Created by henry on 7/13/17.
 */
public interface Loss {
    double eval(DArray inputs, DArray target);
    DArray backprop(DArray inputs, DArray target);
}
