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

public class TestStoryParser {

    @Before
    public void setUp() {
    /*
        * Noen ting som kan være greit å ha med i setUp:
        */
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

}
  
/* - For later..
Story story = StoryParser.parse("haunted_house");
List<Goal> goals = new ArrayList<Goal>();
goals.add(new HealthGoal(4));

Game game = new Game(new Player("Alice", 100, 0, 0), story, goals);
Passage currentPassage = game.begin();
System.out.println(story.getTitle());
System.out.println(story.getPassages());
System.out.println(currentPassage.getTitle());
System.out.println(currentPassage.getContent());
*/
