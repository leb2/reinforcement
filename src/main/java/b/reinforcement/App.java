package b.reinforcement;

import b.reinforcement.tictactoe.TicTacToeAgent;

public class App {
  public static void main( String[] args ) {


    TicTacToeAgent agent = new TicTacToeAgent();
    agent.train(500000);
    agent.playHuman();



//    DominionAgent agent = new DominionAgent();
//    agent.play("lastgood.mw");
//    agent.setLoadFile("lastgood.mw");
//    agent.setLearningRate(0.001);
//    agent.setEpsilon(8);
//    agent.setTestAmount(1000);
//    agent.setTestInterval(500);
//    agent.train(1000000);
//    agent.play(new HumanPolicy(), agent.getGreedyPolicy(), 10);
  }
}
