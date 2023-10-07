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
	
	private Stage HomepageStage;
	
	
	
	public void goToNewProjectScene(ActionEvent event) throws IOException 
	{
		Parent root = FXMLLoader.load(getClass().getResource("NewProjectPage.fxml")); // Set the style and properties of the page into parent root
		Node source = (Node) event.getSource();
	    HomepageStage = (Stage) source.getScene().getWindow(); // Get the Stage from which the button was pressed in
		Scene newProjectScene = new Scene(root); // Create the scene from the "NewProjectPage.fxml"
		HomepageStage.setScene(newProjectScene); // Override the old scene and replace with new one
		HomepageStage.show(); // Update the scene
	
	}

	
	
	
	
	
	
}
