package b.reinforcement.learner.policy;

import b.reinforcement.learner.valuefunction.ActionValueFunction;
import b.reinforcement.learner.core.Action;
import b.reinforcement.learner.core.Environment;

import java.util.*;

public class EpsilonGreedyPolicy implements Policy {

  protected ActionValueFunction q;
  protected double epsilon;
  protected Random random = new Random();

  public EpsilonGreedyPolicy(ActionValueFunction q, double epsilon) {
    this.q = q;

    if (!(epsilon <= 1 && epsilon >= 0)) {
      throw new IllegalArgumentException("Epsilon must be between 0 and 1");
    }
    this.epsilon = epsilon;
  }

  public void setEpsilon(double epsilon) {
    this.epsilon = epsilon;
  }

  public double getEpsilon() {
    return epsilon;
  }

  public Action chooseAction(Environment environment) {
    List<Action> actions = environment.getActions();
    if (actions.size() == 0) {
      throw new IllegalStateException("No actions (must be in terminal state)");
    }

    // Probability to chooose a random action
    if (random.nextDouble() < epsilon) {
      return actions.get(random.nextInt(actions.size()));

    } else {
      Action maxAction = Collections.max(actions, Comparator.comparing(
              action ->
                      q.getValue(environment.getStateAction(action))
                              + environment.immediateReward(action)));

      // TODO: debug
      double maxActionValue = q.getValue(environment.getStateAction(maxAction));
      Map<Action, Double> valueMap = new HashMap<>();
      for (Action action : actions) {
        valueMap.put(action, q.getValue(environment.getStateAction(action)));
      }

      return maxAction;
    }
  }
}
