package com.brendanmle.reinforcement.learner;

import java.util.List;
import java.util.Scanner;

// TODO: untested
public class HumanPolicy<S extends State<A>, A extends Action> implements Policy<S, A> {
  private Scanner scanner = new Scanner(System.in);

  @Override
  public A chooseAction(S state) {
    List<A> actions = state.getActions();

    for (int i = 0; i < actions.size(); i++) {
      System.out.printf("%d: %s\n", i, actions.get(i).toString());
    }

    try {
      int choice = scanner.nextInt();
      return actions.get(choice);

    } catch (Exception e) {
      System.out.println("Invalid, trying again.");
      return chooseAction(state);
    }
  }
}
