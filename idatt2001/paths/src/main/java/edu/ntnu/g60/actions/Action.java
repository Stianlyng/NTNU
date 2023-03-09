package edu.ntnu.g60.actions;

import edu.ntnu.g60.Player;

@FunctionalInterface
public interface Action {
    public void execute(Player player);
}



