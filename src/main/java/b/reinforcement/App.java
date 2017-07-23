package b.reinforcement;

import b.reinforcement.dominion.DominionAgent;

public class App {
  public static void main( String[] args ) {


//    TicTacToeAgent agent = new TicTacToeAgent();
//    agent.train(500000);
//    agent.playHuman();

    DominionAgent agent = new DominionAgent();
    agent.setLoadFile("last.mw");
    agent.setLearningRate(0.00001);
    agent.setEpsilon(0.9);
    agent.setEpsilonDecay(0.005);
    agent.setTestAmount(1000);
    agent.setTestInterval(500);
    agent.train(1000000);
//    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);

  }
}
