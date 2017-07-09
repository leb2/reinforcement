package com.brendanmle.reinforcement.dominion.action;

import com.brendanmle.reinforcement.dominion.DominionEnvironment;
import com.brendanmle.reinforcement.dominion.GameMode;
import com.brendanmle.reinforcement.dominion.Player;
import com.brendanmle.reinforcement.dominion.card.Card;

import java.util.List;

public class PlayCardAction extends DominionAction {

  public PlayCardAction(Card target) {
    super(GameMode.ACTION, target);
  }


  // TODO delegate to card
  @Override
  public void modifyResourceVector(List<Double> resourceVector, Player player) {
    // Actions
    resourceVector.set(0, resourceVector.get(0) - target.getActions() - 1);

    // Buys
    resourceVector.set(1, resourceVector.get(1) - target.getBuys());

    // Treasure
    resourceVector.set(2, resourceVector.get(2) - target.getTreasure());

    // Hand Size
    resourceVector.set(3, resourceVector.get(3) - target.getDraws() - 1);
  }

  @Override // TODO: Add modifyDeckVector to cards for more complex actions
  public void modifyDeckVector(List<Double> deckVector, List<Card> cards) {}

  @Override
  public String toString() {
    return "(Play " + targetName() + ")";
  }

  @Override
  public void perform(DominionEnvironment environment) {
    super.perform(environment);
    target.play(environment.currentPlayer());
    environment.currentPlayer().getHand().remove(target);
  }
}
