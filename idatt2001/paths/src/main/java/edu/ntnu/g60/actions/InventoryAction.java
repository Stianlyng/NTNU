package edu.ntnu.g60.actions;

import edu.ntnu.g60.Player;
import java.util.List;


public class InventoryAction implements Action{
    String inventory;

    public InventoryAction(String  inventory){
        this.inventory = inventory;
    }

    public void execute(Player player){
        player.addToInventory(inventory);
    }

}