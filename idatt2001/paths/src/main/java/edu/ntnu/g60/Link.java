package edu.ntnu.g60;

import java.util.List;
import java.util.Objects;


import edu.ntnu.g60.actions.Action;

/**
 * The Link class represents a link between two passages.
 * It allows the player to move from one passage to another.
 */
public class Link {

  private String text;
  private String reference;
  private List<Action> actions; 

  /**
   * Constructor for the Link class.
   * @param text The text of the link.
   * @param reference The reference to the passage.
   */
  public Link(String text, String reference) throws IllegalArgumentException {
    if (text == null) throw new IllegalArgumentException("Text cannot be null.");
    if (reference == null) throw new IllegalArgumentException("Reference cannot be null.");
    // TODO: sjekk om det skal legges til en sjekk for om reference er en gyldig passage
    this.text = text;
    this.reference = reference;
  }

  public void addAction(Action action) {
    this.actions.add(action);
  }

  public List<Action> getActions() {
    return this.actions;
  }


  public String getText() {
    return this.text;
  }

  public String getReference() {
    return this.reference;
  }

  @Override
  public String toString() {
    return "Link: " + getText() + ", reference: " + getReference();
  }

  /**
   * Compares this link to another object.
   * @param obj The object to compare with this link.
   * @return True if the object is equal to this link, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || this.getClass() != obj.getClass()) return false;
    Link link = (Link) obj;
    return  Objects.equals(this.text, link.text) && 
            Objects.equals(this.reference, link.reference) &&
            Objects.equals(this.actions, link.actions);

  }

}
