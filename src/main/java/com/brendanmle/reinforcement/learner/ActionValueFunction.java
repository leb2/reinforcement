package com.brendanmle.reinforcement.learner;

public interface ActionValueFunction<S extends State<A>, A extends Action> {
  void backup(S state, A action, double reward, S statePrime);
  double getValue(S state, A action);
}
