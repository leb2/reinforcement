package ml.examples;

import ml.arrays.doubles.DArray;
import ml.ml.*;
import ml.optimizers.GDOptimizer;
import ml.optimizers.MeanSquaredError;

/**
 * Created by henry on 7/11/17.
 */
public class Recurent {
    public static void main(String[] args) {
        Model n = new NeuralNetwork(2, 5, 5, 1);
        n.loadModel("adder");
       // n.getFreeVariables().fill(() -> new FreeVariable(Math.random() * 2 - 1));
        ExecutionModel train = new RecurentExecutionModel(n, 4);


        double lr = 0.005;
        int steps = 200000;

        for(int i = 0;i < steps;i++){
            double p = 1.0 * i / steps;


            double[] x = new double[]{0, Math.random(), Math.random(), Math.random(), Math.random()};
            double y = 0;
            for(int a = 0; a < x.length;a++) {
                y += x[a];
            }
            train.backprop(x, new double[]{y}, new MeanSquaredError(), new GDOptimizer(0.1));
        }

        train = new RecurentExecutionModel(n, 4);

        System.out.println(
                train.eval(new double[]{0, 0.5, 0.3, 0.8, 0.4})[0] + ", " +
                        train.eval(new double[]{0, 0.1, 0.1, 0.1, 0.1})[0]
        );

        train = new RecurentExecutionModel(n, 5);

        System.out.println(
                train.eval(new double[]{0, 0.5, 0.3, 0.8, 0.4, 0.5})[0] + ", " +
                        train.eval(new double[]{0, 0.1, 0.1, 0.1, 0.1, 0.5})[0]
        );
    }
}
