package com.brendanmle.reinforcement.dominion.action;

import com.brendanmle.reinforcement.dominion.DominionEnvironment;
import com.brendanmle.reinforcement.dominion.GameMode;
import com.brendanmle.reinforcement.dominion.Player;
import com.brendanmle.reinforcement.dominion.card.Card;
import com.brendanmle.reinforcement.dominion.card.CardType;

import java.util.List;

// TODO: Move all action code to action class!!!!
public class EndPhaseAction extends DominionAction {

  public EndPhaseAction(GameMode gameMode) {
    super(gameMode);

    if (getMode() == GameMode.ACTION) {
      setTargetMode(GameMode.BUY);
    } else if (getMode() == GameMode.BUY) {
      setTargetMode(GameMode.TURN_FINISH);
    } else {
      throw new IllegalStateException("Not valid state to end phase on");
    }
  }

  @Override
  public void modifyModeVector(List<Double> modesVector, List<GameMode> modes) {
    int index = modes.indexOf(getTargetMode());
    for (int i = 0; i < modesVector.size(); i++) {
      modesVector.set(i, i == index ? 1.0 : 0.0);
    }
  }

  @Override
  public void modifyResourceVector(List<Double> resourceVector, Player player) {
    if (getTargetMode() == GameMode.BUY) {
      resourceVector.set(0, 1.0); // Action
      resourceVector.set(1, 1.0); // Buy

      List<Card> hand = player.getHand();
      for (Card card : hand) {
        if (card.getType() == CardType.TREASURE) {
          resourceVector.set(2, resourceVector.get(2) + card.getTreasure());
        }
      }
    }
  }

  @Override
  public void perform(DominionEnvironment environment) {
    super.perform(environment);
    Player player = environment.currentPlayer();
    if (getMode() == GameMode.ACTION) {
      List<Card> hand = player.getHand();

      for (Card card : hand) {
        if (card.getType() == CardType.TREASURE) {
          player.incrementTreasure(card.getTreasure());
        }
      }
    }
  }

  @Override
  public String toString() {
    return "(End " + getMode().toString().toLowerCase() + " phase)";
  }
}