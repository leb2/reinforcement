package com.brendanmle.reinforcement.learner;

import java.util.*;

public class EpsilonGreedyPolicy implements Policy {

  private ActionValueFunction q;
  private double epsilon;

  public EpsilonGreedyPolicy(ActionValueFunction q, double epsilon) {
    this.q = q;

    if (!(epsilon <= 1 && epsilon >= 0)) {
      throw new IllegalArgumentException("Epsilon must be between 0 and 1");
    }
    this.epsilon = epsilon;
  }

  public Action chooseAction(Environment environment) {
    Random random = new Random();
    List<Action> actions = environment.getActions();
    if (actions.size() == 0) {
      throw new IllegalStateException("No actions (must be in terminal state)");
    }

    // Probability to chooose a random action
    if (random.nextDouble() < epsilon) {
      return actions.get(random.nextInt(actions.size()));

    } else {
      Action maxAction = Collections.max(actions, Comparator.comparing(
              action -> q.getValue(environment.getStateAction(action))));

      // TODO: these are here for debugging
      double maxActionValue = q.getValue(environment.getStateAction(maxAction));
      Map<Action, Double> actionValues = new HashMap<>();
      for (int i = 0; i < actions.size(); i++) {
        actionValues.put(actions.get(i), q.getValue(environment.getStateAction(maxAction)));
      }

      return maxAction;
    }
  }
}
