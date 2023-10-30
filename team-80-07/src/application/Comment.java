package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Comment {
    private String description;
    private String timestamp;

    /**
     * Constructs a new Comment with the given description.
     *
     * @param description The text content of the comment (required).
     */
    public Comment(String description, String time) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Comment description must not be empty.");
        }
        this.description = description;
        this.timestamp = time;
    }

    /**
     * Gets the text content of the comment.
     *
     * @return The comment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the TimeStamp when the comment was created.
     *
     * @return The comment TimeStamp.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a string representation of the Comment, including its description and TimeStamp.
     *
     * @return A string representation of the Comment.
     */
    @Override
    public String toString() {
        
        return "Comment Description: " + description + "\n" +
                "Timestamp: " + this.timestamp;
    }

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
