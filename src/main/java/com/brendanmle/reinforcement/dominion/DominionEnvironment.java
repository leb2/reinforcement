package com.brendanmle.reinforcement.dominion;

import com.brendanmle.reinforcement.dominion.card.Card;
import com.brendanmle.reinforcement.dominion.card.CardType;
import com.brendanmle.reinforcement.learner.*;

import java.util.List;

public class DominionEnvironment implements Environment {
  private List<Player> players;
  private Policy opponentPolicy;
  private int turn = 0;


  public DominionEnvironment(int numPlayers) {
    for (int i = 0; i < numPlayers; i++) {
      players.add(new Player(this));
    }

    Card copper = new Card("Copper", 0);
    copper.setTreasure(1).setType(CardType.TREASURE);

    Card silver = new Card("Silver", 3);
    silver.setTreasure(2).setType(CardType.TREASURE);

    Card gold = new Card("Gold", 6);
    gold.setTreasure(3).setType(CardType.TREASURE);

    Card market = new Card("Market", 5);
    market.setActions(1).setTreasure(1).setBuys(1).setDraws(1);

    Card woodcutter = new Card("Woodcutter", 4);
    woodcutter.setBuys(1).setTreasure(2);

    Card smithy = new Card("Smithy", 4);
    smithy.setDraws(3);

    Card estate = new Card("Estate", 2);
    estate.setPoints(1).setType(CardType.VICTORY);

    Card duchy = new Card("Duchy", 5);
    duchy.setPoints(3).setType(CardType.VICTORY);

    Card province = new Card("Province", 8);
    province.setPoints(6).setType(CardType.VICTORY);
  }

  public Player currentPlayer() {
    return players.get(turn % players.size());
  }

  public void setOpponents(Policy policy) {
    this.opponentPolicy = policy;
  }

  @Override
  public double performAction(Action action) {
    return 0;
  }

  @Override
  public State getState() {
    return null;
  }

  @Override
  public StateAction getStateAction(Action action) {
    return null;
  }

  @Override
  public List<Action> getActions() {
    return null;
  }

  @Override
  public int getVectorSize() {
    return 0;
  }

  @Override
  public boolean inTerminalState() {
    return false;
  }

  @Override
  public void resetState() {

  }
}
