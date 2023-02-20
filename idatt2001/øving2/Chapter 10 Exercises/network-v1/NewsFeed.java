import java.util.ArrayList;

/**
 * The NewsFeed class stores news posts for the news feed in a social network 
 * application.
 * 
 * Display of the posts is currently simulated by printing the details to the
 * terminal. (Later, this should display in a browser.)
 * 
 * This version does not save the data to disk, and it does not provide any
 * search or ordering functions.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 0.1
 */
public class NewsFeed
{
    private ArrayList<MessagePost> messages;
    private ArrayList<PhotoPost> photos;

    /**
     * Construct an empty news feed.
     */
    public NewsFeed()
    {
        messages = new ArrayList<>();
        photos = new ArrayList<>();
    }

    /**
     * Add a text post to the news feed.
     * 
     * @param text  The text post to be added.
     */
    public void addMessagePost(MessagePost message)
    {
        messages.add(message);
    }

    /**
     * Add a photo post to the news feed.
     * 
     * @param photo  The photo post to be added.
     */
    public void addPhotoPost(PhotoPost photo)
    {
        photos.add(photo);
    }

    /**
     * Show the news feed. Currently: print the news feed details to the
     * terminal. (To do: replace this later with display in web browser.)
     */
    public void show()
    {
        // display all text posts
        for(MessagePost message : messages) {
            message.display();
            System.out.println();   // empty line between posts
        }

        // display all photos
        for(PhotoPost photo : photos) {
            photo.display();
            System.out.println();   // empty line between posts
        }
    }

    // main
    public static void main(String[] args)
    {
        NewsFeed feed = new NewsFeed();
        feed.addMessagePost(new MessagePost("Alice", "Hello!"));
        feed.addPhotoPost(new PhotoPost("Bob", "photo.jpg", "A photo"));
        feed.addMessagePost(new MessagePost("Charlie", "I'm in New York today! Anyone want to meet up?"));
        feed.addPhotoPost(new PhotoPost("David", "selfie.jpg", "Look at my new selfie!"));
        MessagePost msgPost = new MessagePost("Eve", "I'm so excited about the new Social Network course!");
        msgPost.like();
        msgPost.addComment("Great post bruh!");
        feed.addMessagePost(msgPost);
        feed.show();
    }
}
