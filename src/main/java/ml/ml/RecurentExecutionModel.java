package ml.ml;

import ml.arrays.doubles.DArray;
import ml.arrays.doubles.DBackedArray;
import ml.examples.Recurent;

/**
 * Created by henry on 7/12/17.
 */
public class RecurentExecutionModel implements ExecutionModel {
    ExecutionModel[] models;
    Model m;

    public RecurentExecutionModel(Model m, int occurences) {
        assert occurences > 0;
        assert m.getInputNum() > m.getOutputNum();

        this.models = new ExecutionModel[occurences];
        for (int i = 0;i < occurences; i++) {
            models[i] = m.prepare();
        }
        this.m = m;
    }

    @Override
    public DArray eval(DArray inputs) {
        int out = m.getOutputNum();
        int sub = m.getInputNum() - m.getOutputNum();

        DArray firstInput = inputs.subArray(0, out + sub);
        DArray output = models[0].eval(firstInput);
        for (int i = 1;i < models.length; i++) {
            output = models[i].eval(
                    inputs.subArray(out + sub * i, out + sub * (i + 1)).join(output));
        }
        return output;
    }

    @Override
    public DArray backprop(DArray derivatives, DArray inputDerivatives) {
        DArray derivs = derivatives;
        for(int i = models.length - 1;i > 0;i--){
            DArray inputDerivs = new DBackedArray(models[i].getInputNum()).fill(() -> 0.0);
            derivs = models[i].backprop(derivs, inputDerivs);
        }
        derivs = models[0].backprop(derivs, inputDerivatives);
        return inputDerivatives;
    }

    @Override
    public int getInputNum() {
        return m.getInputNum() * models.length - m.getOutputNum() * (models.length - 1);
    }

    @Override
    public int getOutputNum() {
        return m.getOutputNum();
    }

    @Override
    public Model getModel() {
        return m;
    }

    @Override
    public void resetDerivatives() {}
}
