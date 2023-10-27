package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class AddCommentPageController 
{
	
    @FXML
    private TextField autoPopulatedTicketName;

    @FXML
    private TextArea commentDescriptionArea;
    
    @FXML
    private TextField commentName;

    @FXML
    private Button saveCommentButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button clearButton;
    
    
    private Ticket passedInProject;
    
    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";



	public void initData(Ticket selectedTicketOrProject) 
	{
		// TODO Auto-generated method stub
		System.out.println("TEMP");
		
		passedInProject = selectedTicketOrProject;
		autoPopulatedTicketName.setText(passedInProject.getTicketName()); 
		commentDescriptionArea.setFocusTraversable(false); 
		
		
		
		
	}
	
	public void saveComment(ActionEvent event) throws Exception 
	{
	
	   	String ticketName = autoPopulatedTicketName.getText();
    	String commentName = this.commentName.getText();
    	String commentDesc = commentDescriptionArea.getText();
    	
    	//TODO if empty dont SAVE
   	 if (commentName.isEmpty() || commentDesc.isEmpty()) {
            // Handle validation or show an error message
   		 //TODO
        } 
   	 else 
        {
            // Insert the project into the database
           // insertComment(ticketName, commentName, commentDesc);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            navigateToTicketPage(event);
        }
    	
	}
	
	
	public void clearButton(ActionEvent event) throws Exception 
	{
		
		 commentName.clear();
		 commentDescriptionArea.clear();
	}
	
	  /**
     * This method handles the action when the "Cancel" button is clicked.
     * Navigates back to the main page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void cancelButton(ActionEvent event) throws Exception {
        navigateToTicketPage(event);
    }

    /**
     * This method navigates back to the main page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToTicketPage(ActionEvent event) throws Exception 
    {
    	FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        
        loader.setLocation(getClass().getResource("ShowTicketPage.fxml"));
        root = loader.load();
        ShowTicketPageController controller = loader.getController();
        controller.initData((Ticket) passedInProject);
        
        
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
	
	
	
 

    
		
	
}