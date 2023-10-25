package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class is representing a project with a name, date, and description.
 */
public class ProjectsAndTickets {
    private String projectName;
    private LocalDate projectDate;
    private String projectDescription;
    private String projects; // List of projects associated with this ticket
    private String ticketName;
    private String ticketDescription;

    /**
     * Constructs a new project with the given attributes.
     * @param projectName The name of the project.
     * @param projectDate The date of the project.
     * @param projectDescription The description of the project.
     */
    public ProjectsAndTickets(String projectName, LocalDate projectDate, String projectDescription, String projects, String ticketName, String ticketDescription) 
    {
        this.projectName = projectName;
        this.projectDate = projectDate;
        this.projectDescription = projectDescription;

        this.projects = projects;
        this.ticketName = ticketName;
        this.ticketDescription = ticketDescription;
    }

 
 
}