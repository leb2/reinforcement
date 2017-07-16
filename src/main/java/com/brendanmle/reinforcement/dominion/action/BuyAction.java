package com.brendanmle.reinforcement.dominion.action;

import com.brendanmle.reinforcement.dominion.DominionEnvironment;
import com.brendanmle.reinforcement.dominion.GameMode;
import com.brendanmle.reinforcement.dominion.Player;
import com.brendanmle.reinforcement.dominion.card.Card;
import com.brendanmle.reinforcement.learner.Environment;
import com.google.common.collect.Multiset;

import java.util.List;

public class BuyAction extends DominionAction {

  public BuyAction(Card target, int index) {
    super(GameMode.BUY, target, index);
  }

  @Override
  public void modifyResourceVector(List<Double> resourceVector, Player player) {
    // Buys
    resourceVector.set(1, resourceVector.get(1) - 1);

    // Treasures
    resourceVector.set(2, resourceVector.get(2) - target.getCost());
  }

  @Override
  public void modifyDeckVector(List<Double> deckVector, List<Card> cards) {
    int index = cards.indexOf(target);
    deckVector.set(index, deckVector.get(index) + 1);
  }

  @Override
  public void modifyPilesVector(List<Double> pilesVector, List<Card> cards) {
    int index = cards.indexOf(target);
    pilesVector.set(index, pilesVector.get(index) - 1);
  }

  @Override
  public String toString() {
    return "(Buy " + targetName() + ")";
  }

  @Override
  public void perform(DominionEnvironment environment) {
    super.perform(environment);

    Player player = environment.currentPlayer();
    Multiset<Card> piles = environment.getPiles();

    if (piles.count(target) == 0) {
      throw new IllegalStateException(
              "No card remaining in pile for " + targetName());
    } else if (environment.currentPlayer().getBuys() <= 0) {
      throw new IllegalStateException("No buys left");
    }
    piles.remove(target);
    player.obtainCard(target);
    player.incrementTreasure(-target.getCost());
    player.incrementBuys(-1);
  }
}
