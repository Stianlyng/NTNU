package edu.ntnu.g60;

import java.util.ArrayList;
import java.util.List;

/**
 * The Passage class represents a passage in the story.
 * It contains the title, the content, and the links to other passages.
 */
public class Passage{

  private String title;
  private String content;
  private List<Link> links;
  // TODO: legg til params slik som background etc...

  /**
   * Constructor for the Passage class.
   * @param title The title of the passage.
   * @param content The content of the passage.
   * @param links The links to other passages from this passage.
   * @throws IllegalArgumentException if title or content is null or blank.
   */
  public Passage(String title, String content) throws IllegalArgumentException {
    if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be null or blank.");
    if (content == null || content.isBlank()) throw new IllegalArgumentException("Content cannot be null or blank.");
    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public List<Link> getLinks() {
    return this.links;
  }

  /**
   * Adds a link to the passage.
   * @param link The link to add.
   * @return True if the link was added, false otherwise.
   * @throws IllegalArgumentException if link is null.
   */
  public boolean addLink(Link link) throws IllegalArgumentException {
    if (link == null) throw new IllegalArgumentException("Link cannot be null.");
    return this.links.add(link);
  }

  /**
   * Checks if the passage has links.
   * @return True if the passage has links, false otherwise.
   */
  public boolean hasLinks() {
    return !this.links.isEmpty();
  }


  /**
   * Returns the title, content and number of links of the passage
   * @return A string representation of the passage.
   */
  @Override
  public String toString() {
    return "Passage: " + title + ", Content: " + content + ", Links to: " + links.size() + " passages.";
  }


  /**
   * Compares this passage to another object.
   * @param obj The object to compare with this passage.
   * @return True if the object is equal to this passage, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof Passage)) return false;
    Passage passage = (Passage) obj;
    // TODO: sjekk om disse er riktige
    return passage.getTitle().equals(this.title) && passage.getContent().equals(this.content) && passage.getLinks().equals(this.links);
  }
}
