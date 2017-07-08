package com.brendanmle.reinforcement.learner;

public interface Policy<S extends State<A>, A extends Action> {
  public A chooseAction(S state);
}
