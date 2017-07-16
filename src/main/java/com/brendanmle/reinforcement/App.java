package com.brendanmle.reinforcement;

import com.brendanmle.reinforcement.dominion.DominionAgent;
import com.brendanmle.reinforcement.learner.HumanPolicy;

public class App {
  public static void main( String[] args ) {
    DominionAgent agent = new DominionAgent();

    agent.setLearningRate(0.002);
    agent.setEpsilon(0.2);
    agent.setTestAmount(1000);
    agent.setTestInterval(1000);

    agent.train(1000000);

    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);
  }
}
