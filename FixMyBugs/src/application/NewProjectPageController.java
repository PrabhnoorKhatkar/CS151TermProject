package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewProjectPageController 
{
	
	@FXML
	private Button save;
	
	@FXML
	private Button cancel;
	
	@FXML
	private Button back;
	
	
	private Stage HomepageStage;
	
	/**
	 *
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	
	public void goToHomePage(ActionEvent event) throws IOException 
	{
		//TODO can only go back if save or cancel is pressed
		
		
		// Set the style and properties of the page into parent root
		Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml")); 
		Node source = (Node) event.getSource();
		// Get the Stage from which the button was pressed in
	    HomepageStage = (Stage) source.getScene().getWindow();
	    // Create the scene from the "NewProjectPage.fxml"
		Scene HomePage = new Scene(root); 
		// Override the old scene and replace with new one
		HomepageStage.setScene(HomePage); 
		// Update the scene
		HomepageStage.show(); 
		
					
	}
	
	public void save(ActionEvent event) throws IOException 
	{
		
	
	}
	
	public void cancel(ActionEvent event) throws IOException 
	{
		
	
	}

	
	
	
	
	
	
}
