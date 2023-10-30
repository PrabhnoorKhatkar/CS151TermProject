package application;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

import application.Ticket;
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

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class AddCommentPageController 
{
	
    @FXML
    private TextField autoPopulatedTicketName;
    
    @FXML
    private TextField timestampTextField;
    
    @FXML
    private TextArea commentDescriptionArea;
    
    @FXML
    private Button saveCommentButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private DatePicker projectDatePicker;
    
    @FXML
    private Button clearButton;
    
    private Ticket passedInProject;
    
    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";


    /**
     * initializes data from previous controller, passes in selectedTicket
     * @param selectedTicketOrProject
     */
	public void initData(Ticket selectedTicketOrProject) 
	{
	
		passedInProject = selectedTicketOrProject;
		autoPopulatedTicketName.setText(passedInProject.getTicketName()); 
		commentDescriptionArea.setFocusTraversable(false); 
		
		timestampTextField.setText(LocalDate.now().toString());
		
		
	}
	/**
     * When save button is pressed takes in parameters and stores them in ticket table sql
     * @param event
     * @throws Exception
     */
	public void saveComment(ActionEvent event) throws Exception 
	{
	    String timestamp = timestampTextField.getText();
    	String commentDesc = commentDescriptionArea.getText();
    	String ticketID = passedInProject.getTicketID();
    	
    	//TODO if empty dont SAVE
   	 if (commentDesc.isEmpty()) 
   	 {
            // Handle validation or show an error message
   		    //TODO
     } 
   	 else 
        {
            // Insert the project into the database
           // insertComment(ticketName, commentName, commentDesc, ticketID);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            navigateToTicketPage(event);
        }
    	
	}
	
	/**
     * Clears all enterable textfields in the AddCommentPage
     * @param event
     * @throws Exception
     */
	public void clearButton(ActionEvent event) throws Exception 
	{
		 commentDescriptionArea.clear();
	}
	
	  /**
     * This method handles the action when the "Cancel" button is clicked.
     * Navigates back to the main page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void cancelButton(ActionEvent event) throws Exception 
    {
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