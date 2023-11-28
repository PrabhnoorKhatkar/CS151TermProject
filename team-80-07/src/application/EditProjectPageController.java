package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class EditProjectPageController {

    @FXML
    private TextField projectNameField;

    @FXML
    private DatePicker projectDatePicker;

    @FXML
    private TextArea projectDescriptionArea;

    @FXML
    private Button saveProjectButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    private Project storedProject;

    /**
     * This method handles the action when the "Save" button is clicked.
     * Creates a new project and saves it to the database.
     * 
     * @param event The action event.
     * @throws Exception If saving the project or navigation fails.
     */
    public void saveProject(ActionEvent event) throws Exception {
        String projectName = projectNameField.getText();
        LocalDate projectDate = projectDatePicker.getValue();
        String projectDescription = projectDescriptionArea.getText();

        if (projectName.isEmpty() || projectDate == null) {
            // Handle validation or show an error message
        } else if ((projectName.equals(storedProject.getProjectName()))) {
            // Insert the project into the database
            editProject(projectName, projectDate, projectDescription);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            navigateToProjectPage(event);
        } else if (!(projectName.equals(storedProject.getProjectName()))) {

            // Insert the project into the database
            editProjectDiffName(storedProject.getProjectName(), projectName, projectDate, projectDescription);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            navigateToProjectPage(event);

        }
    }

    /**
     * This method handles the action when the "Cancel" button is clicked.
     * Navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void cancelButton(ActionEvent event) throws Exception {
        navigateToProjectPage(event);
    }

    /**
     * This method navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToProjectPage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        loader.setLocation(getClass().getResource("ShowProjectPage2.fxml"));
        root = loader.load();

        ShowProjectPageController2 controller = loader.getController();
        controller.initData(storedProject);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates the "projects" table in the database if it doesn't exist.
     * The table includes columns for project ID, project name, project date, and
     * project description.
     */
    private void createTable() {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS projects (" + // Updated table name to "projects"
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "project_name TEXT NOT NULL," +
                    "project_date TEXT NOT NULL," +
                    "project_description TEXT" +
                    ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating the table: " + e.getMessage());
        }
    }

    /**
     * Edits a project in the database by either updating an existing project or
     * creating a new one with the updated information.
     *
     * @param projectName        The new name of the project.
     * @param projectDate        The new date of the project.
     * @param projectDescription The new description of the project.
     */
    private void editProject(String projectName, LocalDate projectDate, String projectDescription) {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            // Update the project information
            String updateQuery = "UPDATE projects SET project_date = ?, project_description = ? WHERE project_name = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, projectDate.toString());
                updateStatement.setString(2, projectDescription);
                updateStatement.setString(3, projectName);

                int rowsUpdated = updateStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    // The data was successfully updated
                    storedProject.setProjectDate(projectDate);
                    storedProject.setProjectDescription(projectDescription);
                    storedProject.setProjectName(projectName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Project data not saved.");
        }
    }

    /**
     * Edits a project in the database by either updating an existing project or
     * creating a new one with the updated information.
     *
     * @param projectName        The new name of the project.
     * @param projectDate        The new date of the project.
     * @param projectDescription The new description of the project.
     */
    private void editProjectDiffName(String oldProjectName, String newProjectName, LocalDate projectDate,
                                 String projectDescription) {
    try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) 
    {
        connection.setAutoCommit(false); // Start a transaction
        
        try {
            // Check if the new project name already exists
            String selectQuery = "SELECT * FROM projects WHERE project_name = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, newProjectName);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    // Project with new name already exists, handle accordingly
                    // You can update UI or inform the user about the name conflict
                } else {
                    // Update the project with the new name
                    String updateQuery = "UPDATE projects SET project_name = ?, project_date = ?, project_description = ? WHERE project_name = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, newProjectName);
                        updateStatement.setString(2, projectDate.toString());
                        updateStatement.setString(3, projectDescription);
                        updateStatement.setString(4, oldProjectName);

                        int rowsUpdated = updateStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            // The data was successfully updated with the new name
                            storedProject.setProjectDate(projectDate);
                            storedProject.setProjectDescription(projectDescription);
                            storedProject.setProjectName(newProjectName);
                            
                            // Update tickets associated with the old project name
                            String updateTicketsQuery = "UPDATE tickets SET project_name = ? WHERE project_name = ?";
                            try (PreparedStatement updateTicketsStatement = connection.prepareStatement(updateTicketsQuery)) {
                                updateTicketsStatement.setString(1, newProjectName);
                                updateTicketsStatement.setString(2, oldProjectName);
                                updateTicketsStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
            
            connection.commit(); // Commit the transaction
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Project data not saved.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Database connection error: " + e.getMessage());
    }
}


    /**
     * This method handles the action when the "Clear" button is clicked.
     * Clears the input fields and sets the date to the default value.
     * 
     * @param event The action event.
     * @throws Exception If clearing the fields fails.
     */
    @FXML
    public void clearButton(ActionEvent event) throws Exception {
        // TODO Auto-generated method stub
        projectNameField.setText(storedProject.getName());
        projectNameField.setFocusTraversable(false);

        projectDatePicker.setValue(storedProject.getProjectDate()); // Set date to first date made

        projectDescriptionArea.setText(storedProject.getProjectDescription());
        projectDescriptionArea.setFocusTraversable(false);

    }

    /**
     * Initializes the user interface with data from the provided project.
     * If the "projects" table does not exist in the database, it is created.
     *
     * @param passedInProject The project containing data to initialize the UI.
     */
    public void initData(Project passedInProject) {
        createTable();
        storedProject = passedInProject;
        // TODO Auto-generated method stub
        projectNameField.setText(passedInProject.getName());
        projectNameField.setFocusTraversable(false);

        projectDatePicker.setValue(passedInProject.getProjectDate()); // Set date to first date made

        projectDescriptionArea.setText(passedInProject.getProjectDescription());
        projectDescriptionArea.setFocusTraversable(false);

    }

}