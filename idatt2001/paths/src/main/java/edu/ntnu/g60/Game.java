package edu.ntnu.g60;
import java.util.List;
import edu.ntnu.g60.goals.Goal;

/**
 * The Game class is the main class of the game. 
 * It contains the player, the story, and the goals.
 */
public class Game {

  // TODO: skal de være final?
  private Player player;
  private Story story;
  private List<Goal> goals;

  /**
   * Constructor for the Game class.
   * @param player The player of the game.
   * @param story The story of the game.
   * @param goals The goals of the game.
   */
  public Game(Player player, Story story, List<Goal> goals) throws IllegalArgumentException{
    if (player == null) throw new IllegalArgumentException("Player cannot be null.");
    if (story == null) throw new IllegalArgumentException("Story cannot be null.");
    if (goals == null) throw new IllegalArgumentException("Goals cannot be null.");
    this.player = player;
    this.story = story;
    this.goals = goals;
  }
  
  
  public Player getPlayer() {
    return this.player;
  }

  public Story getStory() {
    return this.story;
  }

  public List<Goal> getGoals() {
    return this.goals;
  }

  /**
   * Starts the game by returning the first passage of the story.
   * @return The first passage of the story.
   */
  public Passage begin() {
    return this.story.getOpeningPassage();
  }

  /**
   * Moves the player to the passage that the link points to.
   * @param link The link to the passage.
   * @return The passage that the link points to.
   */
  public Passage go(Link link) {
    return this.story.getPassage(link);
  }
}

  