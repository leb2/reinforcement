package b.reinforcement.learner;

public interface Policy {
  public Action chooseAction(Environment environment);
}
