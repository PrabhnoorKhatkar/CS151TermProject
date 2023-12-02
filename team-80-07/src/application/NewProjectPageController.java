package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * This class handles creating and saving new projects to a SQLite database.
 */
public class NewProjectPageController implements Initializable {

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
            showAlert("Validation Error", "Project name and project date are required fields.");
        } else {
            // Insert the project into the database
            insertProject(projectName, projectDate, projectDescription);

            System.out.print("Project saved successfully.");

            navigateToMainPage(event);
        }
    }

    /**
     * Displays an error alert with the specified title and message.
     *
     * @param title   The title of the error alert.
     * @param message The detailed error message to be displayed.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        navigateToMainPage(event);
    }

    /**
     * This method navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToMainPage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
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
     * This method inserts a new project into the database.
     * 
     * @param projectName        The name of the project.
     * @param projectDate        The date of the project.
     * @param projectDescription The description of the project.
     */
    private void insertProject(String projectName, LocalDate projectDate, String projectDescription) {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            String insertQuery = "INSERT INTO projects (project_name, project_date, project_description) VALUES (?, ?, ?)"; 
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, projectName);
                preparedStatement.setString(2, projectDate.toString()); 
                preparedStatement.setString(3, projectDescription);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                  
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Project data not saved.");
        }
    }

    /**
     * This method initializes the New Project Page by setting the default date and
     * creating the database table.
     * 
     * @param location  The location used to resolve the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectDatePicker.setValue(LocalDate.now()); 
        createTable(); 
        projectDescriptionArea.setFocusTraversable(false);

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
        projectNameField.clear();
        projectDatePicker.setValue(LocalDate.now()); 
        projectDescriptionArea.clear();

    }

}