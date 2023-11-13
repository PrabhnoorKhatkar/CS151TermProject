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
        } else {
            // Insert the project into the database
            editProject(projectName, projectDate, projectDescription);

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
     * Edits a project in the database by either updating an existing project or creating a new one with the updated information.
     *
     * @param projectName       The new name of the project.
     * @param projectDate       The new date of the project.
     * @param projectDescription The new description of the project.
     */
    private void editProject(String projectName, LocalDate projectDate, String projectDescription) {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            if (projectName.equals(storedProject.getName())) {
                // If the project name hasn't changed, update the existing project
                updateProject(projectName, projectDate, projectDescription, connection);
            } else {
                // If the project name has changed, check if the new name already exists
                if (projectExists(projectName, connection)) {
                    // The project with the new name already exists, handle accordingly
                    // For now, let's print an error message
                    System.err.println("Error: Project with the new name already exists.");
                } else {
                    // The project with the new name doesn't exist, update the existing project and remove the old one
                    insertProject(projectName, projectDate, projectDescription, connection);
                    deleteProject(storedProject.getName(), connection);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Project data not saved.");
        }
    }

    /**
     * Deletes a project from the database based on the project name.
     *
     * @param projectName The name of the project to be deleted.
     * @param connection  The database connection.
     * @throws SQLException If a database access error occurs.
     */
    private void deleteProject(String projectName, Connection connection) throws SQLException {
        String deleteQuery = "DELETE FROM projects WHERE project_name = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, projectName);
            int rowsDeleted = deleteStatement.executeUpdate();
            if (rowsDeleted > 0) {
                // The existing project was successfully deleted
            }
        }
    }

    /**
     * Checks if a project with the given name already exists in the database.
     *
     * @param projectName The name of the project to check for existence.
     * @param connection  The database connection.
     * @return True if a project with the given name already exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    private boolean projectExists(String projectName, Connection connection) throws SQLException {
        String selectQuery = "SELECT project_name FROM projects WHERE project_name = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, projectName);
            ResultSet resultSet = selectStatement.executeQuery();
            return resultSet.next(); // Returns true if a project with the given name already exists
        }
    }

    /**
     * Updates an existing project in the database with the new information.
     *
     * @param projectName       The name of the project.
     * @param projectDate       The new date of the project.
     * @param projectDescription The new description of the project.
     * @param connection        The database connection.
     * @throws SQLException If a database access error occurs.
     */
    private void updateProject(String projectName, LocalDate projectDate, String projectDescription, Connection connection) throws SQLException {
        String updateQuery = "UPDATE projects SET project_date = ?, project_description = ? WHERE project_name = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, projectDate.toString());
            updateStatement.setString(2, projectDescription);
            updateStatement.setString(3, projectName);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                // The data was successfully updated
            }
        }
    }

    /**
     * Inserts a new project into the database.
     *
     * @param projectName       The name of the new project.
     * @param projectDate       The date of the new project.
     * @param projectDescription The description of the new project.
     * @param connection        The database connection.
     * @throws SQLException If a database access error occurs.
     */
    private void insertProject(String projectName, LocalDate projectDate, String projectDescription, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO projects (project_name, project_date, project_description) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, projectName);
            insertStatement.setString(2, projectDate.toString());
            insertStatement.setString(3, projectDescription);

            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted > 0) {
                // The data was successfully inserted
            }
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