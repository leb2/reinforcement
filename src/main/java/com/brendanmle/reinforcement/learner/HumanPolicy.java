package com.brendanmle.reinforcement.learner;

import java.util.List;
import java.util.Scanner;

// TODO: untested
public class HumanPolicy implements Policy {
  private Scanner scanner = new Scanner(System.in);

  @Override
  public Action chooseAction(Environment environment) {
    List<Action> actions = environment.getActions();

    for (int i = 0; i < actions.size(); i++) {
      System.out.printf("%d: %s\n", i, actions.get(i).toString());
    }

    try {
      int choice = scanner.nextInt();
      return actions.get(choice);

    } catch (Exception e) {
      System.out.println("Invalid, trying again.");
      return chooseAction(environment);
    }
  }
}
