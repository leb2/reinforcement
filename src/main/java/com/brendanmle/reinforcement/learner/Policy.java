package com.brendanmle.reinforcement.learner;

import java.util.List;

public interface Policy<S extends State<A>, A extends Action> {
  public A chooseAction(S state);
}
