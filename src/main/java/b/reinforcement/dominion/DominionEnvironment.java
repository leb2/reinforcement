package b.reinforcement.dominion;

import b.reinforcement.dominion.action.BuyAction;
import b.reinforcement.dominion.action.DominionAction;
import b.reinforcement.dominion.action.EndPhaseAction;
import b.reinforcement.dominion.card.Card;
import b.reinforcement.dominion.card.CardType;
import b.reinforcement.learner.*;
import b.reinforcement.learner.*;
import com.google.common.collect.*;
import java.util.*;

public class DominionEnvironment implements Environment {
  private List<Player> players;
  private Policy opponentPolicy;
  private int turn;
  private int numPlayers = 0;
  private GameMode gameMode;

  private Map<String, Card> cardNameMap;
  private Multiset<Card> piles;
  private List<Card> cards;

  public DominionEnvironment(int numPlayers) {
    this.numPlayers = numPlayers;
    resetState();
  }

  public Player currentPlayer() {
    return players.get(turn % players.size());
  }

  public void setOpponent(Policy policy) {
    this.opponentPolicy = policy;
  }

  public double playerOnePoints() {
    return players.get(0).totalPoints();
  }

  public boolean debug(int iteration, double reward) {
    List<Double> scores = new ArrayList<>();
    boolean won = true;
    double score = players.get(0).totalPoints();

    for (Player p : players) {
      scores.add((double) p.totalPoints());

      if (p.totalPoints() > score) {
        won = false;
      }
    }
    List<Card> deck = players.get(0).getDeck();
    double proportion = reward / iteration;
    return won;
  }

  @Override
  public int getOutputSize() {
    return cards.size() + 1;
  }

  @Override
  public double performAction(Action a) {
    DominionAction action = (DominionAction) a;
    Player player = currentPlayer();
    if (player != players.get(0)) {
      throw new IllegalStateException("Player must be first player");
    }
    double oldPoints = player.totalPoints();
    playAction(action);

    if (gameMode == GameMode.TURN_FINISH && !inTerminalState()) {
      incrementTurn();
      for (int i = 0; i < players.size() - 1; i++) {
        playTurn();
        if (inTerminalState()) {
          break;
        }
      }
    }

    if (inTerminalState()) {
      double score = player.totalPoints();
      boolean won = true;
      for (Player p : players) {
        if (p.totalPoints() > score) {
          won = false;
        }
      }
      double diff = score - oldPoints;
      return diff;
//      return diff + (won ? 100 : 0);
    } else {
      return (player.totalPoints() - oldPoints);
    }
  }

  private void playAction(DominionAction action) {
    if (action.getMode() != gameMode) {
      throw new IllegalStateException(
              "Game mode of action must match mode of game");
    }

    action.perform(this);
  }

  private void playTurn() {
    if (gameMode == GameMode.TURN_FINISH) {
      gameMode = GameMode.ACTION;
    }
    while(gameMode != GameMode.TURN_FINISH && !inTerminalState()) {
      DominionAction choice = (DominionAction) opponentPolicy.chooseAction(this);
      playAction(choice);
    }
    incrementTurn();
  }

  private void incrementTurn() {
    currentPlayer().drawNewHand();
    turn += 1;
    startTurn();
  }

  private void startTurn() {
    currentPlayer().setStartingResources();
    gameMode = GameMode.ACTION;
    handleActionPhase();
    EndPhaseAction endPhase = new EndPhaseAction(GameMode.ACTION, 0);
    endPhase.perform(this);
  }

  private void handleActionPhase() {
    List<Card> preferences = ImmutableList.of(
            cardNameMap.get("village"),
            cardNameMap.get("market"),
            cardNameMap.get("smithy"),
            cardNameMap.get("woodcutter"));

    boolean foundCard = true;
    while (foundCard) {
      if (currentPlayer().getActions() <= 0) {
        return;
      }
      foundCard = false;

      for (Card preference : preferences) {
        if (currentPlayer().getHand().indexOf(preference) != -1) {
          preference.play(currentPlayer());
          foundCard = true;
          break;
        }
      }
    }
  }

  public List<Double> getState() {
    Player player = players.get(0); //currentPlayer();
    List<Double> deckVector = player.deckVector(cards);
    List<Double> pilesVector = new ArrayList<>();
    for (Card card : cards) {
      pilesVector.add((double) piles.count(card));
    }
    List<Double> resourcesVector = new ArrayList<>();
    resourcesVector.add((double) player.getActions());
    resourcesVector.add((double) player.getBuys());
    resourcesVector.add((double) player.getTreasure());
    resourcesVector.add((double) player.getHandSize());
    List<Double> modeVector = new ArrayList<>();
    for (GameMode mode : GameMode.values()) {
      modeVector.add(gameMode == mode ? 1.0 : 0.0);
    }

    // ---- Create final vector ----
    List<Double> finalVector = new ArrayList<>();

    // Deck
    finalVector.addAll(deckVector);

    // Provinces remaining
    Card province = cardNameMap.get("province");
    finalVector.add(pilesVector.get(cards.indexOf(province)));

    // Buys and Gold
    finalVector.add(resourcesVector.get(1));
    finalVector.add(resourcesVector.get(2));

    // Turn
    finalVector.add((double) turn / players.size() + 1);

    return finalVector;
  }

  @Override
  public StateAction getStateAction(Action a) {
    DominionAction action = (DominionAction) a;
    Player player = players.get(0); //currentPlayer();

    // Deck vector
    List<Double> deckVector = player.deckVector(cards);
    action.modifyDeckVector(deckVector, cards);

    // Piles vector
    List<Double> pilesVector = new ArrayList<>();
    for (Card card : cards) {
      pilesVector.add((double) piles.count(card));
    }
    action.modifyPilesVector(pilesVector, cards);

    // Resource vector
    List<Double> resourcesVector = new ArrayList<>();
    resourcesVector.add((double) player.getActions());
    resourcesVector.add((double) player.getBuys());
    resourcesVector.add((double) player.getTreasure());
    resourcesVector.add((double) player.getHandSize());
    action.modifyResourceVector(resourcesVector, player);

    // Mode vector
    List<Double> modeVector = new ArrayList<>();
    for (GameMode mode : GameMode.values()) {
      modeVector.add(gameMode == mode ? 1.0 : 0.0);
    }
    action.modifyModeVector(modeVector, Arrays.asList(GameMode.values()));

    // ---- Create final vector ----
    List<Double> finalVector = new ArrayList<>();

    // Deck
    finalVector.addAll(deckVector);

    // Provinces remaining
    Card province = cardNameMap.get("province");
    finalVector.add(pilesVector.get(cards.indexOf(province)));

    // Buys and Gold
    finalVector.add(resourcesVector.get(1));
    finalVector.add(resourcesVector.get(2));

    // Points
//    finalVector.add((double) player.totalPoints());
//    finalVector.add((double) players.get(1).totalPoints());
//    finalVector.add((double) players.get(2).totalPoints());

    // Game mode
    finalVector.add(action.getTargetMode() == GameMode.TURN_FINISH ? 1.0 : 0.0);

    // Turn
    finalVector.add((double) turn / players.size() + 1);

    return new DefaultStateAction(finalVector, getState(), action);
  }

  @Override
  public List<Action> getActions() {
    if (inTerminalState()) {
      return Collections.emptyList();
    }
    List<Action> actions = new LinkedList<>();
    Player player = currentPlayer();

    actions.add(new EndPhaseAction(gameMode, 0));

    if (gameMode == GameMode.ACTION) {
      throw new IllegalStateException("Action state should not be actionable anymore");
    }

    if (gameMode == GameMode.BUY && player.getBuys() > 0) {

      for (int i = 0; i < cards.size(); i++) {
        Card card = cards.get(i);
        if (card.getCost() <= player.getTreasure() && piles.count(card) > 0) {
          actions.add(new BuyAction(card, i + 1));
        }
      }
    }
    return actions;
  }

  @Override
  public int getVectorSize() {
    return getStateAction(getActions().get(0)).toVector().size();
  }

  @Override
  public int getStateSize() {
    return getState().size();
  }

  @Override
  public boolean inTerminalState() {
    if (turn >= 200 || piles.count(cardNameMap.get("province")) == 0) {
      return true;
    }
    int pilesEmpty = 0;
    for (Card card : cards) {
      if (piles.count(card) == 0) {
        pilesEmpty += 1;
      }
    }
    return pilesEmpty >= 3;
  }

  @Override
  public void resetState() {
    initializePiles();
    gameMode = GameMode.ACTION;
    players = new ArrayList<>();
    turn = 0;

    for (int i = 0; i < numPlayers; i++) {
      Player player = new Player(this);
      players.add(player);
      player.obtainCard(cardNameMap.get("copper"), 7);
      piles.remove(cardNameMap.get("copper"), 7);

      player.obtainCard(cardNameMap.get("estate"), 3);
      piles.remove(cardNameMap.get("estate"), 3);
      player.drawNewHand();
    }

    startTurn();
  }

  private void initializePiles() {
    piles = HashMultiset.create();
    cardNameMap = new HashMap<>();
    cards = new ArrayList<>();

    Card copper = new Card("copper", 0);
    copper.setTreasure(1).setType(CardType.TREASURE);
    piles.setCount(copper, 60);

    Card silver = new Card("silver", 3);
    silver.setTreasure(2).setType(CardType.TREASURE);
    piles.setCount(silver, 40);

    Card gold = new Card("gold", 6);
    gold.setTreasure(3).setType(CardType.TREASURE);
    piles.setCount(gold, 30);

    Card market = new Card("market", 5);
    market.setActions(1).setTreasure(1).setBuys(1).setDraws(1);
    piles.setCount(market, 10);

    Card woodcutter = new Card("woodcutter", 4);
    woodcutter.setBuys(1).setTreasure(2);
    piles.setCount(woodcutter, 10);

    Card smithy = new Card("smithy", 4);
    smithy.setDraws(3);
    piles.setCount(smithy, 10);

    Card village = new Card("village", 3);
    village.setDraws(1).setActions(2);
    piles.setCount(village, 10);

    Card estate = new Card("estate", 2);
    estate.setPoints(1).setType(CardType.VICTORY);
    piles.setCount(estate, 24);

    Card duchy = new Card("duchy", 5);
    duchy.setPoints(3).setType(CardType.VICTORY);
    piles.setCount(duchy, 12);

    Card province = new Card("province", 8);
    province.setPoints(6).setType(CardType.VICTORY);
    piles.setCount(province, 12);

    for (Card card : piles.elementSet()) {
      cardNameMap.put(card.getName(), card);
      cards.add(card);
    }
  }

  @Override
  public String toString() {
    String representation = "\n/----------------------------------------\\\n";
    representation += gameMode + " Phase, player " + (turn % players.size()) + ", turn " + turn;

    representation += "\n\nPiles: \n";
    representation += piles.toString();

    representation += "\n\nHand: \n";
    representation += currentPlayer().getHand().toString();

    representation += "\n\nDeck: \n";
    representation += currentPlayer().deckMultiset();

    representation += "\n\nRESOURCES";
    representation += "\nActions: " + currentPlayer().getActions();
    representation += "\nBuys: " + currentPlayer().getBuys();
    representation += "\nTreasure: " + currentPlayer().getTreasure();

    representation += "\n\nPLAYERS: ";
    for (int i = 0; i < players.size(); i++) {
      representation += "\n" + i + ": " + players.get(i).totalPoints();
    }

    representation +=  "\n\\----------------------------------------/\n";
    return representation;
  }

  public Multiset<Card> getPiles() {
    return piles;
  }

  public void setMode(GameMode mode) {
    this.gameMode = mode;
  }
}
