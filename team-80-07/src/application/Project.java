package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class is representing a project with a name, date, and description.
 */
public class Project
{
    private String projectName;
    private LocalDate projectDate;
    private String projectDescription;

    /**
     * Constructs a new project with the given attributes.
     * @param projectName The name of the project.
     * @param projectDate The date of the project.
     * @param projectDescription The description of the project.
     */
    public Project(String projectName, LocalDate projectDate, String projectDescription) {
        this.projectName = projectName;
        this.projectDate = projectDate;
        this.projectDescription = projectDescription;
    }

    /**
     * Get the name of the project.
     * @return The project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Set the name of the project.
     * @param projectName The project name to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Get the date of the project in a formatted string.
     * @return The formatted project date.
     */
    public LocalDate getProjectDate() {
        return projectDate;
    }

    /**
     * Get the date of the project.
     * @return The project date.
     */
    public void setProjectDate(LocalDate projectDate) {
        this.projectDate = projectDate;
    }

    /**
     * Set the date of the project.
     * @param projectDate The project date to set.
     */
    public String getFormattedProjectDate() {
        // Convert the LocalDate to a formatted String when needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern as needed
        return projectDate.format(formatter);
    }

    /**
     * Get the description of the project.
     * @return The project description.
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * Set the description of the project.
     * @param projectDescription The project description to set.
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    
    /**
     * Returns a string representation of the project.
     * @return A string in the format "Project{projectName='...', projectDate=..., projectDescription='...'}".
     */
    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", projectDate=" + projectDate +
                ", projectDescription='" + projectDescription + '\'' +
                '}';
    }


    /**
     * Deletes a project from the database based on its project name.
     *
     * @param projectName The name of the project to be deleted.
     */
	public static void deleteProject(String projectName) 
	{
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
	        String deleteQuery = "DELETE FROM projects WHERE project_name = ?";
	        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
	            // Set the parameter value for project_name
	            deleteStatement.setString(1, projectName);

	            int rowsAffected = deleteStatement.executeUpdate();

	            if (rowsAffected == 0) {
	                System.err.println("No project " + projectName + " found for deletion.");
	            } else {
	                System.err.println("Project " + projectName + " has been deleted.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error deleting project: " + e.getMessage());
	    }
	    
	    
	    
	}
	
	 /**
     * Deletes a project and its associated tickets and comments.
     *
     * @param projectName The name of the project to be deleted.
     */
    public static void deleteProjectContent(String projectName) {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            // Delete associated comments
            String deleteCommentsQuery = "DELETE FROM comments WHERE ticketID IN (SELECT ticketID FROM tickets WHERE project_name = ?)";
            try (PreparedStatement deleteCommentsStatement = connection.prepareStatement(deleteCommentsQuery)) {
                deleteCommentsStatement.setString(1, projectName);
                deleteCommentsStatement.executeUpdate();
            }

            // Delete associated tickets
            String deleteTicketsQuery = "DELETE FROM tickets WHERE project_name = ?";
            try (PreparedStatement deleteTicketsStatement = connection.prepareStatement(deleteTicketsQuery)) {
                deleteTicketsStatement.setString(1, projectName);
                deleteTicketsStatement.executeUpdate();
            }

            // Delete the project
            String deleteProjectQuery = "DELETE FROM projects WHERE project_name = ?";
            try (PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectQuery)) {
                deleteProjectStatement.setString(1, projectName);
                deleteProjectStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting project and associated data: " + e.getMessage());
        }
    }



}