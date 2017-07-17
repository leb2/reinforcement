package b.reinforcement.dominion;

import b.reinforcement.dominion.card.Card;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
  private int actions = 0;
  private int treasure = 0;
  private int buys = 0;

  private List<Card> hand = new ArrayList<>();
  private List<Card> deck = new ArrayList<>();
  private Multiset<Card> deckCounts = HashMultiset.create();

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

  public void obtainCard(Card card, int quantity) {
    for (int i = 0; i < quantity; i++) {
      deck.add(card.duplicate());
    }
    deckCounts.add(card);
  }

  public void drawNewHand() {
    hand.clear();
    draw(5);
  }

  public void draw(int amount) {
    Random random = new Random();

    for (int i = 0; i < amount; i++) {
      Card card = deck.get(random.nextInt(deck.size()));
      hand.add(card); // TODO: duplicate not needed?
    }
  }

  public void setStartingResources() {
    actions = 1;
    buys = 1;
    treasure = 0;
  }

  // TODO: Calculate total points
  public int totalPoints() {
    int total = 0;
    for (Card card : deck) {
      total += card.getPoints();
    }
    return total;
  }

  public List<Double> deckVector(List<Card> cards) {
    List<Double> vector = new ArrayList<>();
    for (Card card : cards) {
      vector.add((double) deckCounts.count(card));
    }
    return vector;
  }

  public void obtainCard(Card card) {
    obtainCard(card, 1);
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

  public int getHandSize() {
    return hand.size();
  }

  // TODO: immutable getter?
  public List<Card> getHand() {
    return hand;
  }

  public List<Card> getDeck() {
    return deck;
  }

  public Multiset<Card> deckMultiset() {
    Multiset<Card> multiset = HashMultiset.create();
    multiset.addAll(deck);
    return multiset;
  }
}
