package b.reinforcement.dominion.action;

import b.reinforcement.dominion.DominionEnvironment;
import b.reinforcement.dominion.GameMode;
import b.reinforcement.dominion.card.Card;
import b.reinforcement.dominion.Player;
import lombok.Builder;

import java.util.List;

public class PlayCardAction extends DominionAction {

  public PlayCardAction(Card target) {
    super(GameMode.ACTION, target);
  }


  // TODO delegate to card
  @Override
  public void modifyResourceVector(List<Double> resourceVector, Player player) {
    // Actions
    resourceVector.set(0, resourceVector.get(0) + target.getActions() - 1);

    // Buys
    resourceVector.set(1, resourceVector.get(1) + target.getBuys());

    // Treasure
    resourceVector.set(2, resourceVector.get(2) + target.getTreasure());

    // Card Draws
    resourceVector.set(3, resourceVector.get(3) + target.getDraws());
  }

  @Override // TODO: Add modifyDeckVector to cards for more complex actions
  public void modifyDeckVector(List<Double> deckVector, List<Card> cards) {}

  @Override
  public void modifyHandVector(List<Double> handVector, List<Card> actionCards) {
    int index = actionCards.indexOf(target);
    handVector.set(index, handVector.get(index) - 1);
  }

  @Override
  public String toString() {
    return "(Play " + targetName() + ")";
  }

  @Override
  public void perform(DominionEnvironment environment) {
    super.perform(environment);
    target.play(environment.currentPlayer());
  }
}
