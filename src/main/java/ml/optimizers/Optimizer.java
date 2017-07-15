package ml.optimizers;

import ml.arrays.Array;
import ml.ml.FreeVariable;

/**
 * Created by henry on 7/13/17.
 */
public interface Optimizer {
    void step(Array<FreeVariable> vars);
}
