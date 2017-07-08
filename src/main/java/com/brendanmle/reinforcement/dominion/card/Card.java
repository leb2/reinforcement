package com.brendanmle.reinforcement.dominion.card;

import com.brendanmle.reinforcement.dominion.Player;

import java.util.Objects;

public class Card {

  private int cost;
  private String name;
  private CardType type = CardType.ACTION;

  private int treasure = 0;
  private int buys = 0;
  private int actions = 0;
  private int draws = 0;
  private int points = 0;

  public Card(String name, int cost) {
    this.cost = cost;
    this.name = name;
  }

  public int getCost() {
    return cost;
  }

  public void play(Player player) {
    if (player.getActions() <= 0) {
      throw new IllegalStateException("Player does not have enough actions");
    }
    player.incrementTreasure(treasure)
            .incrementActions(actions - 1)
            .incrementBuys(buys);
  }

  public Card duplicate() {
    Card duplicate = new Card(name, cost);
    duplicate.setTreasure(treasure)
             .setActions(actions)
             .setBuys(buys)
             .setDraws(draws)
             .setPoints(points)
             .setType(type);

    return duplicate;
  }

  // TODO: mirrorValues() helper method for duplicate

  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    return other instanceof Card && Objects.equals(name, ((Card) other).name);
  }

  public Card setTreasure(int treasure) {
    this.treasure = treasure;
    return this;
  }

  public Card setBuys(int buys) {
    this.buys = buys;
    return this;
  }

  public Card setActions(int actions) {
    this.actions = actions;
    return this;
  }

  public Card setDraws(int draws) {
    this.draws = draws;
    return this;
  }

  public Card setType(CardType type) {
    this.type = type;
    return this;
  }

  public Card setPoints(int points) {
    this.points = points;
    return this;
  }
}
