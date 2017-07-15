package com.brendanmle.reinforcement;

import com.brendanmle.reinforcement.dominion.DominionAgent;
import com.brendanmle.reinforcement.learner.HumanPolicy;

public class App {
  public static void main( String[] args ) {
    DominionAgent agent = new DominionAgent();
    agent.play("last.mw");

//    agent.setTestAmount(1000);
//    agent.setTestInterval(5000);
//    agent.train(1000000);
////    System.out.printf("Average Reward: %f\n", agent.test(agent.getGreedyPolicy(), 500));
//
//    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);
  }
}
