package edu.ntnu.g60.goals;

import edu.ntnu.g60.Player;

@FunctionalInterface
public interface Goal{
  public boolean isFulfilled(Player player);
}




