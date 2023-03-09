package edu.ntnu.g60;

import java.util.ArrayList;
import java.util.List;

import edu.ntnu.g60.goals.*;


public class TestPlayer {

  static Player player1 = new Player("Bjørn", 54, 0, 0);

  public static void main(String[] args) {
    System.out.println(player1);
    player1.addToInventory("Sword");
    player1.addToInventory("Stick");
    player1.addScore(100);
    player1.addGold(100);
    player1.addHealth(100);
    System.out.println(player1);

    List<String> list = new ArrayList<>();
    list.add("Sword");


    InventoryGoal invGoal1 = new InventoryGoal(list);
    if(invGoal1.isFulfilled(player1)){
      System.out.println("inv is fulfilled");
    }
  }
}
