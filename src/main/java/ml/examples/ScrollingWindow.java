package ml.examples;

import ml.misc.WindowData;
import ml.ml.ExecutionModel;
import ml.ml.Model;
import ml.ml.NeuralNetwork;
import ml.optimizers.GDOptimizer;
import ml.optimizers.MeanSquaredError;

/**
 * Created by henry on 7/14/17.
 */
public class ScrollingWindow {
    public static void main(String[] args) {
        Model n = new NeuralNetwork(1, 2, 1);
        n.initNormalWeights();
        ExecutionModel train = n.prepare();

        WindowData wd = new WindowData(10);

        for (int i = 0;i < 20000;i++) {
            double a = Math.random();
            train.backprop(wd.getWindow(
                    new double[]{a},
                    new double[]{a}), new MeanSquaredError(), new GDOptimizer(0.2));
        }

        System.out.println(train.eval(new double[]{0.5})[0]);

        n.saveModel("same.mw");
    }
}
