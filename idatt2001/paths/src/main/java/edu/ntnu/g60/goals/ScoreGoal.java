package edu.ntnu.g60.goals;
import edu.ntnu.g60.Player;

public class ScoreGoal implements Goal{
    int minimumPoints;

    public ScoreGoal(int minimumPoints){
        this.minimumPoints = minimumPoints;
    }

    public boolean isFulfilled(Player player){

        if (player.getScore() >= this.minimumPoints){
            return true;
        } else {
            return false;
        }
    }

}

