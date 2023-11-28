package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Comment {
    private String description;
    private String timestamp;
    private String ticketID;
    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";

    /**
     * Constructs a new Comment with the given description.
     *
     * @param description The text content of the comment (required).
     */
    public Comment(String description, String time, String ticketID) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Comment description must not be empty.");
        }
        this.description = description;
        this.timestamp = time;
        this.setTicketID(ticketID);
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
    
    public void setDescripton(String Description) 
    {
		this.description = Description;
	}
    public void setTimestamp(String timestamp) 
    {
		this.timestamp = timestamp;
	}

    /**
     * Returns a string representation of the Comment, including its description and
     * TimeStamp.
     *
     * @return A string representation of the Comment.
     */
    @Override
    public String toString() {

        return description + "\n                                    -" + this.timestamp;
    }

    /**
     * Retrieves the name associated with this object.
     *
     * @return A string representing the name of the object.
     */
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Deletes a comment from the database based on its timestamp.
     *
     * @param timestamp The timestamp of the comment to be deleted.
     */
    public static void deleteComment(String timestamp) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            String deleteQuery = "DELETE FROM comments WHERE timestamp = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, timestamp);

                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected == 0) {
                    System.err.println("No comment with timestamp " + timestamp + " found for deletion.");
                } else {
                    System.out.println("Comment with timestamp " + timestamp + " has been deleted.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting comment: " + e.getMessage());
        }
    }


	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}



}
