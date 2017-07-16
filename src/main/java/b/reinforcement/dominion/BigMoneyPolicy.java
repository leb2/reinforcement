package b.reinforcement.dominion;

import b.reinforcement.dominion.action.BuyAction;
import b.reinforcement.dominion.action.EndPhaseAction;
import b.reinforcement.learner.Environment;
import b.reinforcement.learner.Policy;
import b.reinforcement.learner.Action;

import java.util.List;

public class BigMoneyPolicy implements Policy {
  @Override
  public Action chooseAction(Environment e) {
    DominionEnvironment environment = (DominionEnvironment) e;
    List<Action> actions = environment.getActions();

    EndPhaseAction endPhaseAction = null;
    BuyAction buyGold = null;
    BuyAction buySilver = null;
    BuyAction buyProvince = null;

    for (Action a : actions) {
      if (a instanceof BuyAction) {
        String name = ((BuyAction) a).targetName();
        if (name.equals("gold")) {
          buyGold = (BuyAction) a;
        }
        if (name.equals("silver")) {
          buySilver = (BuyAction) a;
        }
        if (name.equals("province")) {
          buyProvince = (BuyAction) a;
        }

      } else if (a instanceof EndPhaseAction) {
        endPhaseAction = (EndPhaseAction) a;
      }
    }

    if (buyProvince != null) {
      return buyProvince;

    } else if (buyGold != null) {
      return buyGold;

    } else if (buySilver != null) {
      return buySilver;

    } else {
      return endPhaseAction;
    }
  }
}
