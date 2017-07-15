package ml.ml;
import ml.arrays.Array;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public interface Model {
  Array<FreeVariable> getFreeVariables();
  int getInputNum();
  int getOutputNum();
  ExecutionModel prepare();

  default void saveModel(String filename){
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
      Array<FreeVariable> free = getFreeVariables();

      for(int i = 0;i < free.size();i++) {
        bw.write(Double.toString(free.get(i).get()) + '\n');
      }

      bw.close();
    } catch (IOException e) {
      System.out.println("Failed to save weights");
    }
  }

  default void loadModel(String filename){
    try {
      Scanner s = new Scanner(new FileInputStream(filename));
      Array<FreeVariable> free = getFreeVariables();

      for(int i = 0;i < free.size();i++) {
        free.set(i, new FreeVariable(s.nextDouble()));
      }
      s.close();
    } catch (IOException e) {
      System.out.println("Failed to save weights");
    }
  }

  default void initUniformWeights() {
    getFreeVariables().fill(() -> new FreeVariable(Math.random() * 2 - 1));
  }

  default void initNormalWeights() {
    Random r = new Random();
    getFreeVariables().fill(() -> new FreeVariable(r.nextGaussian()));
  }
}
