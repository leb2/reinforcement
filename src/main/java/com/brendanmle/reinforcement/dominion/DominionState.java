package com.brendanmle.reinforcement.dominion;

import com.brendanmle.reinforcement.learner.State;

import java.util.List;

public class DominionState implements State<DominionAction> {

  public DominionState() {
  }

  @Override
  public List<DominionAction> getActions() {
    return null;
  }

  @Override
  public List<Double> toVector() {
    return null;
  }

  @Override
  public boolean isTerminalState() {
    return false;
  }
}
