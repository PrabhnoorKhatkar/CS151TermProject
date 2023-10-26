package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class is representing a project with a name, date, and description.
 */
public class Project implements ProjectItem{
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

	@Override
	public String getName() {
		
		return projectName;
	}


}