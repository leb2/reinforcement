package b.reinforcement;

import b.reinforcement.dominion.DominionAgent;
import b.reinforcement.learner.HumanPolicy;

public class App {
  public static void main( String[] args ) {
    DominionAgent agent = new DominionAgent();

    agent.setLearningRate(0.00003);
    agent.setEpsilon(0.2);
    agent.setTestAmount(500);
    agent.setTestInterval(1000);
    agent.train(1000000);

    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);
  }
}
