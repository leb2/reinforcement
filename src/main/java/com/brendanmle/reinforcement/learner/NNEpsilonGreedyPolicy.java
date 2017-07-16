package com.brendanmle.reinforcement.learner;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NNEpsilonGreedyPolicy extends EpsilonGreedyPolicy {

  public NNEpsilonGreedyPolicy(ActionValueFunction q, double epsilon) {
    super(q, epsilon);
  }

  @Override
  public Action chooseAction(Environment environment) {
    List<Action> actions = environment.getActions();
    if (actions.size() == 0) {
      throw new IllegalStateException("No actions (must be in terminal state)");
    }

    // Probability to chooose a random action
    if (random.nextDouble() < epsilon) {
      return actions.get(random.nextInt(actions.size()));

    } else {
      List<Double> values = ((NNStateActionFunction) q).getValues(environment.getState());
      List<Double> debug = environment.getState();

      Action maxAction = Collections.max(actions, Comparator.comparing(
              action -> values.get(action.getIndex())));
      return maxAction;
    }
  }
}
