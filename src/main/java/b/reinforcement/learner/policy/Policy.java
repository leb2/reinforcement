package b.reinforcement.learner.policy;

import b.reinforcement.learner.core.Action;
import b.reinforcement.learner.core.Environment;

public interface Policy {
  public Action chooseAction(Environment environment);
}
