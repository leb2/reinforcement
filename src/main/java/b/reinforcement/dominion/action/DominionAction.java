package b.reinforcement.dominion.action;

import b.reinforcement.dominion.DominionEnvironment;
import b.reinforcement.dominion.GameMode;
import b.reinforcement.dominion.card.Card;
import b.reinforcement.dominion.Player;
import b.reinforcement.learner.core.Action;

import java.util.List;

public abstract class DominionAction implements Action {

  // Mode of environment for which the action can act in.
  private GameMode mode;
  private GameMode targetMode;
  Card target;

  public DominionAction(GameMode mode) {
    this.mode = mode;
    this.targetMode = mode;
  }

  public DominionAction(GameMode mode, Card target) {
    this(mode);
    setTarget(target);
  }

  public void setTarget(Card target) {
    this.target = target;
  }

  public Card getTarget() {
    return target;
  }


  public GameMode getMode() {
    return mode;
  }

  public String targetName() {
    return target.getName();
  }

  public GameMode getTargetMode() {
    return targetMode;
  }

  public void setTargetMode(GameMode targetMode) {
    this.targetMode = targetMode;
  }

  public void perform(DominionEnvironment environment) {
    environment.setMode(getTargetMode());
  }

  public void modifyResourceVector(List<Double> resourceVector, Player player) {}

  public void modifyDeckVector(List<Double> deckVector, List<Card> cards) {}

  public void modifyPilesVector(List<Double> pilesVector, List<Card> cards) {}

  public void modifyModeVector(List<Double> modesVector, List<GameMode> modes) {}

  public void modifyHandVector(List<Double> handVector, List<Card> actionCards) {}

  @Override
  public List<Double> toVector() {
    return null;
  }

  @Override
  public String toString() {
    return mode.toString();
  }
}
