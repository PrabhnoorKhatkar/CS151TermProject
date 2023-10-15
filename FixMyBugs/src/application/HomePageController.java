package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController 
{
	
	@FXML
	private Button newProjectButton;
	
	@FXML
	private Button ShowProjectButton;
	
	
	private Stage HomepageStage;
	
	/**
	 * This method handles the action event when a user wants to go to the new project scene.
	 * It loads the "NewProjectPage.fxml" file, creates a new scene, and sets it as the current scene.
	 * It also updates the stage to display the new scene.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	
	public void goToNewProjectScene(ActionEvent event) throws IOException 
	{
		// Set the style and properties of the page into parent root
		Parent root = FXMLLoader.load(getClass().getResource("NewProjectPage.fxml")); 
		Node source = (Node) event.getSource();
		// Get the Stage from which the button was pressed in
	    HomepageStage = (Stage) source.getScene().getWindow();
	    // Create the scene from the "NewProjectPage.fxml"
		Scene newProjectScene = new Scene(root); 
		// Override the old scene and replace with new one
		HomepageStage.setScene(newProjectScene); 
		// Update the scene
		HomepageStage.show(); 
	
	}
	
	public void goToShowProjects(ActionEvent event) throws IOException 
	{
		// Set the style and properties of the page into parent root
		Parent root = FXMLLoader.load(getClass().getResource("ShowProjectPage.fxml")); 
		Node source = (Node) event.getSource();
		// Get the Stage from which the button was pressed in
	    HomepageStage = (Stage) source.getScene().getWindow();
	    // Create the scene from the "NewProjectPage.fxml"
		Scene newProjectScene = new Scene(root); 
		// Override the old scene and replace with new one
		HomepageStage.setScene(newProjectScene); 
		// Update the scene
		HomepageStage.show(); 
	
	}

	
	
	
	
	
	
}
