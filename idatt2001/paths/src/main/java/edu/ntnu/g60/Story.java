package edu.ntnu.g60;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * The Story class represents the story of the game.
 * It contains the title, the opening passage, and the passages of the story.
 */
public class Story {

  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  /**
   * Constructor for the Story class.
   * @param title The title of the story.
   * @param openingPassage The opening passage of the story.
   * @throws IllegalArgumentException if title is null or blank.
   * @throws IllegalArgumentException if openingPassage is null.
   */
  public Story(String title, Passage openingPassage) throws IllegalArgumentException {
    if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be null or blank.");
    if (openingPassage == null) throw new IllegalArgumentException("Opening passage cannot be null.");
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<Link, Passage>();
  }

  
  /**
   * Adds a passage to the story.
   * @param passage The passage to add.
   * @throws IllegalArgumentException if passage is null.
   */
  public void addPassage(Passage passage) throws IllegalArgumentException {
    if (passage == null) throw new IllegalArgumentException("Passage cannot be null.");
    Link link = new Link(passage.getTitle(), passage.getContent());
    this.passages.put(link, passage);
  }
  
  public String getTitle() {
    return this.title;
  }

  public Passage getOpeningPassage() {
    return this.openingPassage;
  }
  
  public Passage getPassage(Link link) {
    return this.passages.get(link);
  }

  public Collection<Passage> getPassages() {
    return this.passages.values();
  }

}
