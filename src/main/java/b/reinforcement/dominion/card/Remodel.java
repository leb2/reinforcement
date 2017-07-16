package b.reinforcement.dominion.card;

import b.reinforcement.dominion.DominionEnvironment;
import b.reinforcement.dominion.Player;

public class Remodel extends Card {

  public Remodel() {
    super("Remodel", 4);
  }

  public void play(Player player) {
    super.play(player);
    DominionEnvironment environment = player.getEnvironment();
  }
}
