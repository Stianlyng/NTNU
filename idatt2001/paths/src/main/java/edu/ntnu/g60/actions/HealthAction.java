package edu.ntnu.g60.actions;

import edu.ntnu.g60.Player;

public class HealthAction implements Action{
    int health;

    public HealthAction(int health){
        this.health = health;
    }

    public void execute(Player player){
        player.addHealth(health);
    }

}