package edu.ntnu.g60.actions;

import edu.ntnu.g60.Player;

public class ScoreAction implements Action{
    int score;

    public ScoreAction(int score){
        this.score = score;
    }

    public void execute(Player player){
        player.addScore(score);
    }

}