package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Comment {

	private String description;
	private String timestamp;
	private String ticketID;

	/**
	 * Constructs a new Comment with the given description.
	 *
	 * @param description The text content of the comment (required).
	 * @param time        The timestamp of the comment.
	 * @param ticketID    The ID of the ticket associated with the comment.
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
	 * Gets the timestamp when the comment was created.
	 *
	 * @return The comment timestamp.
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the text content of the comment.
	 *
	 * @param description The new description to set.
	 */
	public void setDescripton(String description) {
		this.description = description;
	}

	/**
	 * Sets the timestamp of the comment.
	 *
	 * @param timestamp The new timestamp to set.
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns a string representation of the Comment, including its description and
	 * timestamp.
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

		return null;
	}

	/**
	 * Gets the ID of the ticket associated with the comment.
	 *
	 * @return The ticket ID.
	 */
	public String getTicketID() {
		return ticketID;
	}

	/**
	 * Sets the ID of the ticket associated with the comment.
	 *
	 * @param ticketID The new ticket ID to set.
	 */
	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	/**
	 * Deletes a comment from the database based on its timestamp.
	 *
	 * @param timestamp The timestamp of the comment to be deleted.
	 */
	public static void deleteComment(String timestamp) {
		try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
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
}
