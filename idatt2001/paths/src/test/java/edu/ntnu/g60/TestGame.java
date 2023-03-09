package edu.ntnu.g60;


import edu.ntnu.g60.goals.*;
import edu.ntnu.g60.actions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.Before;

public class TestGame {

  private Player player;
  private Story story;
  private List<Goal> goals;

  @Before
  public void setUp() {
    goals = new ArrayList<>();
    goals.add(new HealthGoal(4));
    
    player = new Player("Elon", 10, 10, 10);
    Passage openingPassage = new Passage("Level 1", "You are in a dark room.");
    story = new Story("The Dark World", openingPassage);
  }

    // main method
    
  /* For later....
  Player player = new Player("Bobby", 10, 10, 10);
  Passage openingPassage = new Passage("Level 1", "You are in a dark room.");
  Story story = new Story("The Dark World", openingPassage);
  
  //Game game = new Game(player, story);
  
  System.out.println("Welcome to " + story.getTitle());
  System.out.println("You are " + player);
  System.out.println("You are in " + openingPassage.getTitle());
  */
 
  }
  
  