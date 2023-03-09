package edu.ntnu.g60.goals;

import edu.ntnu.g60.Player;



public class GoldGoal implements Goal{
    
    int minimumGold;
    
    public GoldGoal(int minimumGold){
        this.minimumGold = minimumGold;
    }
    
    public boolean isFulfilled(Player player){
        return player.getGold() >= this.minimumGold;
    }
    
}


  


