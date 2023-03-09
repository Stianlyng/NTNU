package edu.ntnu.g60;

import edu.ntnu.g60.Game;
import edu.ntnu.g60.Player;
import edu.ntnu.g60.Story;
import edu.ntnu.g60.goals.Goal;
import edu.ntnu.g60.goals.HealthGoal;
import edu.ntnu.g60.Passage;
import edu.ntnu.g60.Link;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This class is responsible for parsing a story from a file.
 * It is not part of the game engine, but is used to load stories
 * 
 * The format of the story files is as follows:
 * Title of the story
 * Empty line
 * Passage title (starting with ::)
 * Passage content (until link or empty line)
 * Passage link: [text](reference)
 * Fifth line: Empty
 */
public class StoryParser {
    
    private static final Pattern LINK_PATTERN = Pattern.compile("\\[(.+?)\\]\\((.+?)\\)");

    /*
     * Parse a story from a file
     * 
     * @param filename The name of the file to parse
     * @return The story
     * @throws IOException If the file cannot be read
     */
    public static Story parse(String filename) throws IOException {
        String path = "src/main/java/edu/ntnu/g60/resources/textFiles/";
        BufferedReader reader = new BufferedReader(new FileReader(path + filename + ".txt"));

        // Read the title
        String title = reader.readLine().trim();

        // Skip the empty line
        reader.readLine();

        // Read the opening passage
        Passage openingPassage = parsePassage(reader);

        // Create the story
        Story story = new Story(title, openingPassage);

        // Read the rest of the passages
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("::")) {
                Passage passage = parsePassage(reader);
                story.addPassage(passage);
            }
        }

        reader.close();

        return story;
    }

    /*
     * Parse a passage from a file
     * 
     * @param reader The reader to read the passage from
     * @return The passage
     * @throws IOException If the file cannot be read
     */
    private static Passage parsePassage(BufferedReader reader) throws IOException {
        String title = reader.readLine().substring(2).trim();

        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.trim().isEmpty() && !line.startsWith("[")) {
            contentBuilder.append(line).append("\n");
        }
        String content = contentBuilder.toString().trim();

        Passage passage = new Passage(title, content);

        // TODO; Slå sammen med while-løkka under...
        if (line.matches("\\[.+?\\]\\(.+?\\)")) {
            Link link = parseLink(line.trim());
            passage.addLink(link);
        }

        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            if (line.matches("\\[.+?\\]\\(.+?\\)")) {
                Link link = parseLink(line.trim());
                passage.addLink(link);
            }
        }

        return passage;
    }

    /*
     * Parse a link from a string
     * 
     * @param linkString The string to parse
     * @return The link
     */
    private static Link parseLink(String linkString) {
        String[] linkParts = linkString.split("\\]\\(");
        String linkText = linkParts[0].substring(1);
        String linkReference = linkParts[1].substring(0, linkParts[1].length() - 1);
        return new Link(linkText, linkReference);
    }

    
}

