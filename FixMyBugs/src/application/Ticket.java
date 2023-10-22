package application;

import java.util.List;


public class Ticket {
    private List<Project> projects; // List of projects associated with this ticket
    private String ticketName;
    private String ticketDescription;

    /**
     * Constructs a new Ticket with the given information.
     *
     * @param projects         The list of projects associated with the ticket.
     * @param ticketName       The name or title of the ticket.
     * @param ticketDescription The detailed description of the issue or request.
     *                         
     * @throws IllegalArgumentException If the ticket name is null or empty.
     */
    public Ticket(List<Project> projects, String ticketName, String ticketDescription) {
        if (ticketName == null || ticketName.isEmpty()) {
            throw new IllegalArgumentException("Ticket name must not be empty.");
        }

        this.projects = projects;
        this.ticketName = ticketName;
        this.ticketDescription = ticketDescription;
    }

    /**
     * Gets the list of projects associated with the ticket.
     *
     * @return The list of projects.
     */
    public List<Project> getProjects() {
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
     * Returns a string representation of the Ticket, including its name, description,
     * and associated projects.
     *
     * @return A string representation of the Ticket.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket Name: ").append(ticketName).append("\n");
        sb.append("Ticket Description: ").append(ticketDescription).append("\n");

        if (projects != null && !projects.isEmpty()) {
            sb.append("Projects Associated with this Ticket: \n");
            for (Project project : projects) {
                sb.append("  - ").append(project.getProjectName()).append("\n");
            }
        }

        return sb.toString();
    }
}