package ml.examples;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.org.apache.xpath.internal.operations.Mod;
import ml.ml.ExecutionModel;
import ml.ml.FreeVariable;
import ml.ml.Model;
import ml.ml.NeuralNetwork;
import ml.optimizers.AdamOptimizer;
import ml.optimizers.GDOptimizer;
import ml.optimizers.MeanSquaredError;

public class Xor {
  public static void main(String[] args) {
    Model n = new NeuralNetwork(2, 3, 1);
    n.initNormalWeights();

    double[][] X = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    double[][] Y = new double[][]{{0}, {1}, {1}, {0}};
    ExecutionModel train = n.prepare();

    double loss = Double.POSITIVE_INFINITY;
    int steps = 0;
    while (loss > 0.00000001) {
      steps++;
      loss = train.backprop(X, Y, new MeanSquaredError(), new GDOptimizer(0.3).decay(0.98));
    }

    System.out.println("XOR model trained to a loss of " + String.format("%.2e", loss) + " in " + steps + " epochs.");
    System.out.println("[0, 0] -> " + train.eval(new double[]{0, 0})[0]);
    System.out.println("[1, 0] -> " + train.eval(new double[]{1, 0})[0]);
    System.out.println("[0, 1] -> " + train.eval(new double[]{0, 1})[0]);
    System.out.println("[1, 1] -> " + train.eval(new double[]{1, 1})[0]);

    n.saveModel("xor.mw");
  }
}
