package b.reinforcement;

import b.reinforcement.dominion.DominionAgent;
import b.reinforcement.learner.policy.HumanPolicy;

public class App {
  public static void main( String[] args ) {


//    TicTacToeAgent agent = new TicTacToeAgent();
//    agent.train(500000);
//    agent.playHuman();

    DominionAgent agent = new DominionAgent();
    agent.setLearningRate(0.00001);
    agent.setEpsilon(1);
    agent.setEpsilonDecay(0.0002);
    agent.setTestAmount(2000);
    agent.setTestInterval(1000);
    agent.train(1000000);
  }
}
