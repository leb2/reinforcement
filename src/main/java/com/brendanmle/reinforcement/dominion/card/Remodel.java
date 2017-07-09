package com.brendanmle.reinforcement.dominion.card;

import com.brendanmle.reinforcement.dominion.DominionEnvironment;
import com.brendanmle.reinforcement.dominion.Player;

public class Remodel extends Card {

  public Remodel() {
    super("Remodel", 4);
  }

  public void play(Player player) {
    super.play(player);
    DominionEnvironment environment = player.getEnvironment();
  }
}
