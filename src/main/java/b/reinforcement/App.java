package b.reinforcement;

import b.reinforcement.dominion.DominionAgent;
import b.reinforcement.learner.HumanPolicy;

public class App {
  public static void main( String[] args ) {

    DominionAgent agent = new DominionAgent();
    agent.play("last.mw");
    agent.setLoadFile("last.mw");
    agent.setLearningRate(0.000001);
    agent.setEpsilon(0.2);
    agent.setTestAmount(1000);
    agent.setTestInterval(500);
    agent.train(1000000);

    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);
  }
}
