package com.brendanmle.reinforcement.learner;

public interface StateActionFactory <
        S extends State<A>,
        A extends Action> {

  StateAction<S, A> create(S state, A action);
}
