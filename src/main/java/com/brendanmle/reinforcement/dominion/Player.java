package com.brendanmle.reinforcement.dominion;

public class Player {
  private int actions = 0;
  private int treasure = 0;
  private int buys = 0;
  private DominionEnvironment environment;

  public Player(DominionEnvironment environment) {
    this.environment = environment;
  }

  public Player incrementTreasure(int amount) {
    treasure += amount;
    return this;
  }

  public Player incrementActions(int amount) {
    actions += amount;
    return this;
  }

  public Player incrementBuys(int amount) {
    buys += amount;
    return this;
  }

  public DominionEnvironment getEnvironment() {
    return environment;
  }

  public int getActions() {
    return actions;
  }

  public void setActions(int actions) {
    this.actions = actions;
  }

  public int getTreasure() {
    return treasure;
  }

  public void setTreasure(int gold) {
    this.treasure = gold;
  }

  public int getBuys() {
    return buys;
  }

  public void setBuys(int buys) {
    this.buys = buys;
  }
}
