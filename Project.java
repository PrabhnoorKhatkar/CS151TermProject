package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project {
    private String projectName;
    private LocalDate projectDate;
    private String projectDescription;

    public Project(String projectName, LocalDate projectDate, String projectDescription) {
        this.projectName = projectName;
        this.projectDate = projectDate;
        this.projectDescription = projectDescription;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(LocalDate projectDate) {
        this.projectDate = projectDate;
    }

    public String getFormattedProjectDate() {
        // Convert the LocalDate to a formatted String when needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern as needed
        return projectDate.format(formatter);
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", projectDate=" + projectDate +
                ", projectDescription='" + projectDescription + '\'' +
                '}';
    }
}