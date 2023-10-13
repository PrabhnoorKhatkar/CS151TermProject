package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class NewProjectPAgeController {
	
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
	    
	    /**
	     * This method handles the action event when the user clicks the "Save Project" button.
	     * You can add your logic here to save the project using the entered data.
	     *
	     * @param event The ActionEvent triggered by the user's interaction.
	     * @throws Exception If there is an error loading the FXML file for the main page.
	     */
	    public void saveProject(ActionEvent event) throws Exception {
	        String projectName = projectNameField.getText();
	        String projectDescription = projectDescriptionArea.getText();
	        String projectDateString = projectDatePicker.getEditor().getText();

	        if (projectName.isEmpty() || projectDateString.isEmpty()) {
	            // Handle validation or show an error message
	        } else {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	            LocalDate projectDate = LocalDate.parse(projectDateString, formatter);

	            // Create a Project object and save it to your data store
	            Project newProject = new Project(projectName, projectDate, projectDescription);

	            // Save the project to your data store or perform any other necessary actions
	            // Example: projectRepository.save(newProject);

	            // Provide user feedback that the project was saved successfully
	            // Example: showSuccessAlert("Project saved successfully.");

	            // After saving the project, you can navigate back to the main page
	            navigateToMainPage(event);
	        }
	    }

	    
	    /**
	     * This method handles the action event when the user clicks the "Cancel" button.
	     * It takes the user back to the main page.
	     *
	     * @param event The ActionEvent triggered by the user's interaction.
	     * @throws Exception If there is an error loading the FXML file for the main page.
	     */
	    @FXML
	    public void cancelButton(ActionEvent event) throws Exception {
	        navigateToMainPage(event);
	    }
	    
	    private void navigateToMainPage(ActionEvent event) throws Exception {
	        // Load the FXML file for the main page and navigate to it
	        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
	        Stage stage = (Stage) cancelButton.getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    }


}
