package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ticket 
{
    private String projects; 
    private String ticketName;
    private String ticketDescription;
    private String ticketID;

    /**
     * Constructs a new Ticket with the given information.
     *
     * @param projects         The list of projects associated with the ticket.
     * @param ticketName       The name or title of the ticket.
     * @param ticketDescription The detailed description of the issue or request.
     *                         
     * @throws IllegalArgumentException If the ticket name is null or empty.
     */
    public Ticket(String projects, String ticketName, String ticketDescription, String ticketID) {
        if (ticketName == null || ticketName.isEmpty()) {
            throw new IllegalArgumentException("Ticket name must not be empty.");
        }

        this.projects = projects;
        this.ticketName = ticketName;
        this.ticketDescription = ticketDescription;
        this.ticketID = ticketID;
    }

    /**
     * Gets the list of projects associated with the ticket.
     *
     * @return The list of projects.
     */
    public String getProjects() {
        return projects;
    }

    /**
     * Gets the name or title of the ticket.
     *
     * @return The ticket name.
     */
    public String getTicketName() {
        return ticketName;
    }

    /**
     * Sets the name or title of the ticket.
     *
     * @param ticketName The new ticket name to set.
     */
    public void setTicketName(String ticketName) {
        if (ticketName == null || ticketName.isEmpty()) {
            throw new IllegalArgumentException("Ticket name must not be null or empty.");
        }
        this.ticketName = ticketName;
    }
    
    /**
     * Gets the detailed description of the issue or request.
     *
     * @return The ticket description.
     */
    public String getTicketDescription() {
        return ticketDescription;
    }
    
    /**
     * Sets the detailed description of the issue or request.
     *
     * @param ticketDescription The new ticket description to set.
     */
    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }


	/**
	 * Deletes a ticket from the database based on its UUID.
	 *
	 * @param uuid The unique identifier of the ticket to be deleted.
	 */
	public static void deleteTicket(String uuid) 
	{
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
	        String deleteQuery = "DELETE FROM tickets WHERE ticketID = ?";
	        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) 
	        {
	        	 // Set the parameter value
	            deleteStatement.setString(1, uuid);
	           
	            int rowsAffected = deleteStatement.executeUpdate();
	            
	            if (rowsAffected == 0) {
	                System.err.println("No ticket with ID " + uuid + " found for deletion.");
	            } else {
	                System.out.println("Ticket with ID " + uuid + " has been deleted.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error deleting ticket: " + e.getMessage());
	    }
	}

	/**
	 * Retrieves the unique identifier (ID) of the ticket.
	 *
	 * @return A string representing the ticket ID.
	 */
	public String getTicketID() 
	{
		return ticketID;
	}
	
	/**
	 * Returns a string representation of the ticket.
	 *
	 * @return A string representing the ticket's name.
	 */
    @Override
    public String toString() {

        return ticketName;
    }


}
