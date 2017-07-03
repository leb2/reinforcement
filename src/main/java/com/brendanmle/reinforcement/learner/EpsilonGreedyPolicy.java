package com.brendanmle.reinforcement.learner;

import java.util.*;

public class EpsilonGreedyPolicy<
        S extends State<A>,
        A extends Action,
        SA extends StateAction<S, A>> implements Policy<S, A> {

  private ActionValueFunction<S, A> q;
  private double epsilon;

  public EpsilonGreedyPolicy(ActionValueFunction<S, A> q, double epsilon) {
    this.q = q;

    if (!(epsilon <= 1 && epsilon >= 0)) {
      throw new IllegalArgumentException("Epsilon must be between 0 and 1");
    }
    this.epsilon = epsilon;
  }

  public A chooseAction(S state) {
    Random random = new Random();
    List<A> actions = state.getActions();
    if (actions.size() == 0) {
      throw new IllegalStateException("No actions (must be in terminal state)");
    }

    // Probability to chooose a random action
    if (random.nextDouble() < epsilon) {
      return actions.get(random.nextInt(actions.size()));

    } else {
      A maxAction = Collections.max(actions, Comparator.comparing(
              action -> q.getValue(state, action)));
      double maxActionValue = q.getValue(state, maxAction);
      Map<A, Double> actionValues = new HashMap<>();
      for (int i = 0; i < actions.size(); i++) {
        actionValues.put(actions.get(i), q.getValue(state, actions.get(i)));
      }

      return maxAction;
    }
  }
}
