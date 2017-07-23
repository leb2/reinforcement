package b.reinforcement.learner;

import b.reinforcement.learner.core.*;
import b.reinforcement.learner.policy.NNPolicy;
import b.reinforcement.learner.policy.Policy;
import ml.ml.ExecutionModel;
import ml.ml.Model;
import ml.ml.NeuralNetwork;
import ml.optimizers.GDOptimizer;
import ml.optimizers.MeanSquaredError;

import java.util.List;
import java.util.Map;

public class PolicyGradientAgent {
  protected double learningRate;
  protected Model model;
  protected StateEnvironment environment;
  private ExecutionModel network;
  private NNPolicy policy;

  private List<List<Double>> states;
  private List<Integer> actionChoices;

  public PolicyGradientAgent(StateEnvironment environment) {
    model = new NeuralNetwork(
            environment.getVectorSize(), 15, environment.getOutputSize());
    model.initNormalWeights();

    this.environment = environment;
    network = model.prepare();
  }

  public void train() {
    double totalReward = 0;
    while (!environment.inTerminalState()) {
      List<Double> state = environment.stateVector();
      states.add(state);

      IndexedAction choice = (IndexedAction) policy.chooseAction(environment);
      actionChoices.add(choice.getIndex());

      totalReward += environment.performAction(choice);
    }

    for (int i = 0; i < states.size(); i++) {

      List<Double> state = states.get(i);
      int actionIndex = actionChoices.get(i);
      double[] targetOutput = new double[environment.getOutputSize()];
      targetOutput[actionIndex] = totalReward;

      network.backprop(
              Util.doubleListToArr(state),
              targetOutput,
              new MeanSquaredError(), // TODO change to logistic loss,
              new GDOptimizer(0.001)
      );
    }

    states.clear();
    actionChoices.clear();
  }
}
