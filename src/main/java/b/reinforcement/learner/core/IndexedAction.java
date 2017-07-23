package b.reinforcement.learner.core;

/**
 * Used in policy gradient so a specific action always has the same position
 * in the output vector of the neural network.
 */
public interface IndexedAction extends Action {
  public int getIndex();
}
