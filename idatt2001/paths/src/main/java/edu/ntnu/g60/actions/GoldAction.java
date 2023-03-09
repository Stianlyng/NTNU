package edu.ntnu.g60.actions;

import edu.ntnu.g60.Player;

public class GoldAction implements Action{
    int gold;

    public GoldAction(int gold){
        this.gold = gold;
    }

    public void execute(Player player){
        player.addGold(gold);
    }
}