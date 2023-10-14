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
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewProjectPageController implements Initializable
{

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

    private static final String jdbcUrl = "jdbc:sqlite:database.db";

 

    public void saveProject(ActionEvent event) throws Exception 
    {
        String projectName = projectNameField.getText();
        LocalDate projectDate = projectDatePicker.getValue();
        String projectDescription = projectDescriptionArea.getText();

        if (projectName.isEmpty() || projectDate == null) {
            // Handle validation or show an error message
        } else {
            // Insert the project into the database
            insertProject(projectName, projectDate, projectDescription);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            navigateToMainPage(event);
        }
    }

    @FXML
    public void cancelButton(ActionEvent event) throws Exception {
        navigateToMainPage(event);
    }

    private void navigateToMainPage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void createTable() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
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

    private void insertProject(String projectName, LocalDate projectDate, String projectDescription) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            String insertQuery = "INSERT INTO projects (project_name, project_date, project_description) VALUES (?, ?, ?)"; // Updated table name to "projects"
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, projectName);
                preparedStatement.setString(2, projectDate.toString()); // Store date as a string
                preparedStatement.setString(3, projectDescription);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // The data was successfully inserted
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Project data not saved.");
        }
    }
    
    // Initialize method to set the default value of the DatePicker
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		projectDatePicker.setValue(LocalDate.now()); // Set default date to today
        createTable(); // Call the createTable method when the application initializes
        projectDescriptionArea.setFocusTraversable(false); 
		
	}
}