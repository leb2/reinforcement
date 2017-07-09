package com.brendanmle.reinforcement;

import com.brendanmle.reinforcement.dominion.DominionAgent;
import com.brendanmle.reinforcement.learner.HumanPolicy;

public class App {
  public static void main( String[] args ) {
    DominionAgent agent = new DominionAgent();
    agent.setTestAmount(200);
    agent.setTestInterval(1000);
    agent.train(5000);
    System.out.printf("Average Reward: %f\n", agent.test(agent.getGreedyPolicy(), 1000));

    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);
  }
}
